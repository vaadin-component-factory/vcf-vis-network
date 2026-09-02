/*
 * Vis Network Add-on
 *
 * Copyright (C) 2026 Vaadin Ltd
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
package org.vaadin.addons.componentfactory.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.vaadin.addons.componentfactory.visnetwork.Edge;
import org.vaadin.addons.componentfactory.visnetwork.NetworkDiagram;
import org.vaadin.addons.componentfactory.visnetwork.Node;
import org.vaadin.addons.componentfactory.visnetwork.options.Options;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.progressbar.ProgressBar;
import com.vaadin.flow.router.Route;

/**
 * The counterpart of vis-network's
 * <a href=
 * "https://visjs.github.io/vis-network/examples/network/events/physicsEvents.html">physics
 * events</a> example: the events the diagram reports while it settles a graph.
 * <p>
 * A run takes a fraction of a second, so every event goes to a log rather than
 * to a single status line.
 */
@SuppressWarnings("serial")
@Route(value = "physics-events", layout = MainLayout.class)
public class PhysicsEventsDemoView extends VerticalLayout {

    /** How many lines the log keeps before dropping the oldest. */
    private static final int LOG_LIMIT = 40;

    private final List<String> lines = new ArrayList<>();

    private final Div log = new Div();

    private final ProgressBar progress = new ProgressBar();

    private final NetworkDiagram diagram;

    public PhysicsEventsDemoView() {
        setSizeFull();
        setSpacing(false);

        diagram = new NetworkDiagram(eventfulOptions());
        diagram.setSizeFull();
        // So that flex can shrink it below its content instead of overflowing.
        diagram.getStyle().set("min-width", "0");

        diagram.addStabilizationStartListener(
                event -> record("stabilizationStart", "the run begins"));
        diagram.addStabilizationProgressListener(event -> {
            record("stabilizationProgress", event.getIterations() + " of "
                    + event.getTotal() + " iterations");
            progress.setMax(Math.max(event.getTotal(), 1));
            progress.setValue(Math.min(event.getIterations(),
                    event.getTotal()));
        });
        diagram.addStabilizationIterationsDoneListener(event -> {
            record("stabilizationIterationsDone", "no iterations left");
            progress.setValue(progress.getMax());
        });
        diagram.addStabilizedListener(event -> record("stabilized",
                "at rest after " + event.getIterations() + " iterations"));

        H2 title = new H2("Physics events");
        title.getStyle().set("margin", "0 0 var(--lumo-space-s) 0");

        add(title);
        add(new DemoDescription(
                "The four events reported while vis-network settles a graph, "
                        + "logged on the right in the order they arrive.",
                "A run takes a fraction of a second, so the log is what shows "
                        + "it happened.",
                "\"Stabilize\" runs it again on the graph on screen.",
                "Drag a node away and let go: settling from a drag reports "
                        + "only \"stabilized\"."));
        HorizontalLayout content = content();
        add(toolbar(), content);
        // The row takes the height left over, and the diagram fills it. vis-network
        // puts options.height on a frame div of its own inside the diagram, so the
        // 100% resolves against whatever flex gives this one.
        setFlexGrow(1, content);
    }

    /** The same graph as the vis-network example, with nothing configured. */
    private Options eventfulOptions() {
        Options options = new Options();
        options.setHeight("100%");
        options.setWidth("100%");

        options.getData().nodes.addAll(Arrays.asList(new Node("1", "Node 1"),
                new Node("2", "Node 2"), new Node("3", "Node 3"),
                new Node("4", "Node 4"), new Node("5", "Node 5")));
        options.getData().edges.addAll(Arrays.asList(new Edge("1", "3"),
                new Edge("1", "2"), new Edge("2", "4"), new Edge("2", "5")));
        return options;
    }

    /** Appends one event to the log, newest at the bottom. */
    private void record(String event, String detail) {
        lines.add(String.format("%2d. %-28s %s", lines.size() + 1, event,
                detail));
        while (lines.size() > LOG_LIMIT) {
            lines.remove(0);
        }
        log.setText(String.join("\n", lines));
    }

    private HorizontalLayout toolbar() {
            Button stabilize = new Button("Stabilize",
                            event -> diagram.stabilize());
            Button fit = new Button("Fit to screen",
                            event -> diagram.fitToScreen());
            Button clear = new Button("Clear log", event -> {
                    lines.clear();
                    log.setText("");
            });

            HorizontalLayout toolbar = new HorizontalLayout(stabilize, fit, clear);
            toolbar.setWidthFull();
            // The root layout has spacing off, so the gap is set here.
            toolbar.getStyle().set("margin-bottom", "var(--lumo-space-s)");
            return toolbar;
    }

    /** The diagram and the log side by side, so the log is in view. */
    private HorizontalLayout content() {
        HorizontalLayout content = new HorizontalLayout(diagram,
                statusPanel());
        content.setWidthFull();
        content.getStyle().set("min-height", "0");
        content.setFlexGrow(1, diagram);
        return content;
    }

    private VerticalLayout statusPanel() {
        progress.setWidthFull();

        Span caption = new Span("Events, oldest first");
        caption.getStyle().set("font-weight", "600")
                .set("font-size", "var(--lumo-font-size-s)");

        log.setId("physics-events-log");
        log.getStyle()
                .set("font-family", "var(--lumo-font-family-monospace)")
                .set("font-size", "var(--lumo-font-size-xs)")
                .set("white-space", "pre")
                .set("background", "var(--lumo-contrast-5pct)")
                .set("border-radius", "var(--lumo-border-radius-m)")
                .set("padding", "var(--lumo-space-s)")
                .set("flex", "1")
                .set("overflow", "auto")
                .set("box-sizing", "border-box");

        VerticalLayout panel = new VerticalLayout(progress, caption, log);
        panel.setPadding(false);
        panel.setSpacing(false);
        panel.setWidth("26em");
        panel.setHeightFull();
        panel.getStyle().set("flex-shrink", "0");
        return panel;
    }
}
