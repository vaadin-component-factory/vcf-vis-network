/*
 * Vis Network Add-on
 *
 * Copyright (C) 2026 Vaadin Ltd
 *
 * This file is derived from visjs-addon (network) by sameeraroshan,
 * https://github.com/sameeraroshan/visjs (branch visJs4+), and has been
 * modified: ported from Vaadin 7 to Vaadin Flow and updated for vis-network
 * 10.x.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.vaadin.addons.componentfactory.visnetwork;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.vaadin.addons.componentfactory.visnetwork.event.graph.ResizeEvent;
import org.vaadin.addons.componentfactory.visnetwork.event.graph.StabilizationIterationsDoneEvent;
import org.vaadin.addons.componentfactory.visnetwork.event.graph.StabilizationProgressEvent;
import org.vaadin.addons.componentfactory.visnetwork.event.graph.StabilizationStartEvent;
import org.vaadin.addons.componentfactory.visnetwork.event.graph.StabilizedEvent;
import org.vaadin.addons.componentfactory.visnetwork.event.graph.ZoomEvent;
import org.vaadin.addons.componentfactory.visnetwork.event.node.BlurEvent;
import org.vaadin.addons.componentfactory.visnetwork.event.node.ClickEvent;
import org.vaadin.addons.componentfactory.visnetwork.event.node.DoubleClickEvent;
import org.vaadin.addons.componentfactory.visnetwork.event.node.DragEndEvent;
import org.vaadin.addons.componentfactory.visnetwork.event.node.DragStartEvent;
import org.vaadin.addons.componentfactory.visnetwork.event.node.HoverEvent;
import org.vaadin.addons.componentfactory.visnetwork.event.node.SelectEvent;
import org.vaadin.addons.componentfactory.visnetwork.listener.BeforeClientResponseListener;
import org.vaadin.addons.componentfactory.visnetwork.options.Options;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.ClientCallable;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.HasSize;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.function.SerializableConsumer;
import com.vaadin.flow.shared.Registration;

import tools.jackson.core.json.JsonWriteFeature;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.databind.node.ObjectNode;

/**
 * Created by roshans on 10/10/14.
 * <p>
 * Vaadin Flow wrapper for the vis-network diagram. The connector installs
 * itself on the element as {@code $connector}: calls out go through it, calls
 * in arrive at the {@code @ClientCallable} methods below.
 */
@SuppressWarnings("serial")
@Tag(Tag.DIV)
@NpmPackage(value = "vis-network", version = "10.1.2")
@JsModule("./src/vcf-vis-network-diagram-connector.js")
@CssImport("vis-network/styles/vis-network.min.css")
public class NetworkDiagram extends Component implements HasSize, HasStyle {

    /**
     * A Jackson 3 {@code ObjectMapper} is immutable and thread safe once built,
     * so one shared instance serves every diagram instead of one per component.
     */
    private static final ObjectMapper MAPPER = createMapper();

    private final List<BeforeClientResponseListener> beforeClientResponseListeners = new ArrayList<>();

    private Options options;

    public NetworkDiagram() {
        this(null);
    }

    public NetworkDiagram(Options options) {
        this.options = options;
        // A styling hook, with no rules of its own. The stylesheet the source
        // shipped for it fought with HasSize.
        addClassName("vis-network-diagram");
        if (options != null) {
            setWidth(options.getWidth());
            setHeight(options.getHeight());
        }
        // Here rather than on attach: calls run in the order they were queued,
        // and callers add nodes right after construction, so $connector has to
        // exist first.
        initConnector();
    }

    /**
     * Builds the mapper used for everything sent to vis-network.
     * <p>
     * The source serialized with Gson, so the two defaults that differ are
     * pinned back to it: properties come from fields rather than getters, and
     * nulls are omitted, which is what makes nulling a module mean keep
     * vis-network's own. {@code NON_NULL} and not {@code NON_EMPTY}, so that a
     * blank label survives.
     */
    private static ObjectMapper createMapper() {
        return JsonMapper.builder()
                .changeDefaultPropertyInclusion(
                        incl -> incl.withValueInclusion(Include.NON_NULL))
                .changeDefaultVisibility(checker -> checker
                        .withFieldVisibility(Visibility.ANY)
                        .withGetterVisibility(Visibility.NONE)
                        .withIsGetterVisibility(Visibility.NONE)
                        .withSetterVisibility(Visibility.NONE))
                .enable(JsonWriteFeature.ESCAPE_NON_ASCII)
                .build();
    }

    @Override
    protected void onAttach(AttachEvent attachEvent) {
        super.onAttach(attachEvent);
        if (!attachEvent.isInitialAttach()) {
            initConnector();
        }
    }

    /**
     * Creates the connector on the element and hands it the current options.
     * <p>
     * Through {@code executeJs} and not {@code callJsFunction}, since this is
     * what installs {@code $connector}.
     */
    private void initConnector() {
        // Serialized now, not inside the command: addNodes and friends mutate
        // the options, so serializing late would send those nodes twice.
        String optionsJson = toJson(options);
        runBeforeClientResponse(ui -> getElement().executeJs(
                "window.Vaadin.Flow.visNetworkConnector.initLazy(this, $0)",
                optionsJson));
    }

    /**
     * Re-creates the network on the client with the current options.
     * <p>
     * Under Vaadin 7 this also had to bump a shared-state counter to force the
     * client to pick the change up. Flow has no shared state and
     * {@code callJsFunction} always reaches the client, so the counter is gone.
     */
    public void reinitialize() {
        initConnector();
    }

    /**
     * Replaces the options and re-creates the network on the client with them.
     * <p>
     * Use this rather than {@link #updateOptions(Options)} to change the
     * hierarchical layout at runtime. Handing those options to a live network
     * makes vis-network re-run the layout against the graph as it stands,
     * support nodes included, and throw over their missing levels.
     *
     * @param options the options to build the network with
     */
    public void reinitialize(Options options) {
        this.options = options;
        initConnector();
    }

    public void reDraw() {
        callConnector("reDraw");
    }

    public void draw() {
        callConnector("draw");
    }

    public void stabilize() {
        callConnector("stabilize");
    }

    public void fitToScreen() {
        callConnector("fitToScreen");
    }

    public void destroyNetwork() {
        callConnector("destroyNetwork");
    }

    public void drawConnections() {
        callConnector("drawConnections");
    }

    public Options getOptions() {
        return options;
    }

    public void updateOptions(Options options) {
        this.options = options;
        callConnector("setOptions", toJson(options));
    }

    /**
     * @deprecated adding one by one for a large number of nodes makes the
     *             browser freeze; use {@link #addNodes(List)}.
     */
    @Deprecated
    public void addNode(Node... node) {
        if (options != null) {
            options.getData().nodes.addAll(Arrays.asList(node));
        }
        callConnector("addNodes", toJson(node));
    }

    public void addNodes(List<Node> nodes) {
        addNodes((Collection<Node>) nodes);
    }

    public void addNodes(Collection<Node> nodes) {
        if (options != null) {
            options.getData().nodes.addAll(nodes);
        }
        callConnector("addNodes", toJson(nodes));
    }

    /**
     * @deprecated adding one by one for a large number of edges makes the
     *             browser freeze; use {@link #addEdges(List)}.
     */
    @Deprecated
    public void addEdge(Edge... edges) {
        if (options != null) {
            options.getData().edges.addAll(Arrays.asList(edges));
        }
        callConnector("addEdges", toJson(edges));
    }

    public void addEdges(List<Edge> edges) {
        addEdges((Collection<Edge>) edges);
    }

    public void addEdges(Collection<Edge> edges) {
        if (options != null) {
            options.getData().edges.addAll(edges);
        }
        callConnector("addEdges", toJson(edges));
    }

    public void removeNode(Node... node) {
        if (options != null) {
            options.getData().nodes.removeAll(Arrays.asList(node));
        }
        callConnector("removeNode", toJson(node));
    }

    public void removeEdge(Edge... edges) {
        if (options != null) {
            options.getData().edges.removeAll(Arrays.asList(edges));
        }
        callConnector("removeEdge", toJson(edges));
    }

    public void updateNode(Node... node) {
        callConnector("updateNode", toJson(node));
    }

    public void updateNodes(List<Node> nodes) {
        callConnector("updateNode", toJson(nodes));
    }

    /** @deprecated use {@link #updateNodes(List)}. */
    @Deprecated
    public void updateNode(List<Node> nodes) {
        updateNodes(nodes);
    }

    public void updateEdge(Edge... edges) {
        callConnector("updateEdge", toJson(edges));
    }

    public void updateEdges(List<Edge> edges) {
        callConnector("updateEdge", toJson(edges));
    }

    /** @deprecated use {@link #updateEdges(List)}. */
    @Deprecated
    public void updateEdge(List<Edge> edges) {
        updateEdges(edges);
    }

    public void clearNodes() {
        if (options != null) {
            options.getData().nodes.clear();
        }
        callConnector("clearNodes");
    }

    public void clearEdges() {
        if (options != null) {
            options.getData().edges.clear();
        }
        callConnector("clearEdges");
    }

    public void clear() {
        clearEdges();
        clearNodes();
    }

    // ----------------------------------------------------- calls from client
    //
    // Each takes the object vis-network passed to its event, reduced by the
    // connector to the fields the matching event class reads.

    @ClientCallable
    public void onSelect(ObjectNode properties) {
        fireEvent(new SelectEvent(this, true, properties));
    }

    @ClientCallable
    public void onClick(ObjectNode properties) {
        fireEvent(new ClickEvent(this, true, properties));
    }

    @ClientCallable
    public void onDoubleClick(ObjectNode properties) {
        fireEvent(new DoubleClickEvent(this, true, properties));
    }

    @ClientCallable
    public void onHoverNode(ObjectNode properties) {
        fireEvent(new HoverEvent(this, true, properties));
    }

    @ClientCallable
    public void onBlurNode(ObjectNode properties) {
        fireEvent(new BlurEvent(this, true, properties));
    }

    @ClientCallable
    public void onDragStart(ObjectNode properties) {
        fireEvent(new DragStartEvent(this, true, properties));
    }

    @ClientCallable
    public void onDragEnd(ObjectNode properties) {
        fireEvent(new DragEndEvent(this, true, properties));
    }

    @ClientCallable
    public void onStabilizationProgress(ObjectNode properties) {
        fireEvent(new StabilizationProgressEvent(this, true, properties));
    }

    @ClientCallable
    public void onStartStabilization() {
        fireEvent(new StabilizationStartEvent(this, true));
    }

    @ClientCallable
    public void onStabilizationIterationsDone() {
        fireEvent(new StabilizationIterationsDoneEvent(this, true));
    }

    @ClientCallable
    public void onStabilized(ObjectNode properties) {
        fireEvent(new StabilizedEvent(this, true, properties));
    }

    @ClientCallable
    public void onZoom(ObjectNode properties) {
        fireEvent(new ZoomEvent(this, true, properties));
    }

    @ClientCallable
    public void onResize(ObjectNode properties) {
        fireEvent(new ResizeEvent(this, true, properties));
    }

    /**
     * Called by the connector when the network has to be rebuilt, for instance
     * after the element is moved in the DOM or its tab is focused again.
     */
    @ClientCallable
    public void onReinit() {
        initConnector();
    }

    // ------------------------------------------------------------- listeners

    public Registration addNodeSelectListener(
            ComponentEventListener<SelectEvent> listener) {
        return addListener(SelectEvent.class, listener);
    }

    public Registration addNodeClickListener(
            ComponentEventListener<ClickEvent> listener) {
        return addListener(ClickEvent.class, listener);
    }

    public Registration addNodeDoubleClickListener(
            ComponentEventListener<DoubleClickEvent> listener) {
        return addListener(DoubleClickEvent.class, listener);
    }

    public Registration addNodeHoverListener(
            ComponentEventListener<HoverEvent> listener) {
        return addListener(HoverEvent.class, listener);
    }

    public Registration addNodeBlurListener(
            ComponentEventListener<BlurEvent> listener) {
        return addListener(BlurEvent.class, listener);
    }

    public Registration addNodeDragStartListener(
            ComponentEventListener<DragStartEvent> listener) {
        return addListener(DragStartEvent.class, listener);
    }

    public Registration addNodeDragEndListener(
            ComponentEventListener<DragEndEvent> listener) {
        return addListener(DragEndEvent.class, listener);
    }

    public Registration addResizeListener(
            ComponentEventListener<ResizeEvent> listener) {
        return addListener(ResizeEvent.class, listener);
    }

    public Registration addStabilizationStartListener(
            ComponentEventListener<StabilizationStartEvent> listener) {
        return addListener(StabilizationStartEvent.class, listener);
    }

    public Registration addStabilizationProgressListener(
            ComponentEventListener<StabilizationProgressEvent> listener) {
        return addListener(StabilizationProgressEvent.class, listener);
    }

    public Registration addStabilizationIterationsDoneListener(
            ComponentEventListener<StabilizationIterationsDoneEvent> listener) {
        return addListener(StabilizationIterationsDoneEvent.class, listener);
    }

    public Registration addStabilizedListener(
            ComponentEventListener<StabilizedEvent> listener) {
        return addListener(StabilizedEvent.class, listener);
    }

    public Registration addZoomListener(
            ComponentEventListener<ZoomEvent> listener) {
        return addListener(ZoomEvent.class, listener);
    }

    public Registration addBeforeClientResponseListener(
            BeforeClientResponseListener listener) {
        beforeClientResponseListeners.add(listener);
        return () -> beforeClientResponseListeners.remove(listener);
    }

    // --------------------------------------------------------------- helpers

    /**
     * Serializes anything bound for vis-network. Package visible so that the
     * tests can assert the JSON the client receives.
     */
    static String toJson(Object value) {
        return MAPPER.writeValueAsString(value);
    }

    private void callConnector(String function, Serializable... arguments) {
        runBeforeClientResponse(ui -> getElement()
                .callJsFunction("$connector." + function, arguments));
    }

    private void runBeforeClientResponse(SerializableConsumer<UI> command) {
        getElement().getNode().runWhenAttached(
                ui -> ui.beforeClientResponse(this, context -> {
                    beforeClientResponseListeners.forEach(
                            listener -> listener.beforeClientResponse(
                                    context.isClientSideInitialized()));
                    command.accept(ui);
                }));
    }
}
