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
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.addons.componentfactory.visnetwork.Edge;
import org.vaadin.addons.componentfactory.visnetwork.NetworkDiagram;
import org.vaadin.addons.componentfactory.visnetwork.Node;
import org.vaadin.addons.componentfactory.visnetwork.options.Options;
import org.vaadin.addons.componentfactory.visnetwork.options.Shadow;
import org.vaadin.addons.componentfactory.visnetwork.util.Color;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * A package dependency graph laid out by vis-network's own physics.
 * <p>
 * No layout module is enabled, so the nodes are placed by simulation. It also
 * covers styling by category, adding to a live graph and updating it in place.
 */
@SuppressWarnings("serial")
@Route(value = "dependency-graph", layout = MainLayout.class)
public class DependencyGraphDemoView extends VerticalLayout {

    /** What a package is, which decides how it is drawn. */
    private enum Kind {
        RUNTIME("#3F6E8C", 6),
        FRAMEWORK("#6E8C3F", 4),
        LIBRARY("#8C5A3F", 1),
        APPLICATION("#6B3F8C", 1);

        private final String background;
        private final int mass;

        Kind(String background, int mass) {
            this.background = background;
            this.mass = mass;
        }
    }

    /** One row of the graph definition, so the wiring stays declarative. */
    private record Package(String id, String label, Kind kind,
            String... dependsOn) {
    }

    /** How many packages the page starts with, already stabilized. */
    private static final int INITIAL_PACKAGES = 3;

    private static final List<Package> CATALOG = Arrays.asList(
            new Package("jvm", "JVM", Kind.RUNTIME),
            new Package("collections", "core-collections", Kind.FRAMEWORK,
                    "jvm"),
            new Package("json", "json-codec", Kind.FRAMEWORK, "jvm"),
            new Package("http", "http-client", Kind.LIBRARY, "collections",
                    "json"),
            new Package("cache", "cache-store", Kind.LIBRARY, "collections"),
            new Package("report", "report-builder", Kind.LIBRARY, "json"),
            new Package("cli", "invoice-cli", Kind.APPLICATION, "http",
                    "cache"),
            new Package("web", "invoice-web", Kind.APPLICATION, "http",
                    "report"));

    private final Map<String, String> nodeDetails = new LinkedHashMap<>();

    private final Div message = new Div();

    private final NetworkDiagram diagram;

    private int revealed;

    /** Whether the packages on the graph are currently drawn thick-bordered. */
    private boolean thickBorders;

    public DependencyGraphDemoView() {
        setSizeFull();
        setSpacing(false);

        Options options = new Options();
        options.setAutoResize(true);
        options.setHeight("100%");
        options.setWidth("100%");
        // Stabilization settles the graph before it is shown, so it does not
        // drift on screen. Physics stays on so dragging a node moves its
        // neighbours.
        options.getPhysics().getStabilization().setEnabled(true);
        options.getPhysics().getStabilization().setFit(true);

        // The page does not drive these, so they go out as null and
        // vis-network keeps its own defaults for them.
        options.setManipulation(null);
        options.setConfigure(null);
        options.setLocale(null);
        options.setLocales(null);

        // hover is off by default, so the hover listener needs it turned on.
        options.getInteraction().setHover(true);

        CATALOG.stream().limit(INITIAL_PACKAGES).forEach(pkg -> {
            options.getData().nodes.add(node(pkg));
            Arrays.stream(pkg.dependsOn()).map(
                    target -> new Edge(pkg.id(), target))
                    .forEach(options.getData().edges::add);
            describe(pkg);
        });
        revealed = INITIAL_PACKAGES;

        diagram = new NetworkDiagram(options);
        diagram.setWidthFull();
        // So that flex can shrink it below its content instead of overflowing.
        diagram.getStyle().set("min-height", "0");
        diagram.addNodeDoubleClickListener(event -> event.getNodeIds().stream()
                .map(nodeDetails::get).filter(detail -> detail != null)
                .findFirst().ifPresent(message::setText));
        diagram.addNodeHoverListener(event -> message
                .setText("Hovering " + event.getNodeIds()));

        message.setId("dependency-graph-message");

        H2 title = new H2("Dependency graph");
        title.getStyle().set("margin", "0 0 var(--lumo-space-s) 0");

        add(title);
        add(new DemoDescription(
                "Packages and what they depend on, arranged by simulation with "
                        + "no layout configured. Colour and shape come from the "
                        + "kind of package.",
                "\"Reveal next\" adds one of eight packages to the graph in "
                        + "place, instead of rebuilding it.",
                "\"Thicken borders\" updates the packages already on screen, "
                        + "and reverts. Nothing moves while it does.",
                "Double-click a package for its details. Drag one to push its "
                        + "neighbours around."));
        add(toolbar(), diagram, message);
        // The diagram takes the height left over. vis-network puts options.height
        // on a frame div of its own inside this element, so the 100% resolves
        // against whatever flex gives this one.
        setFlexGrow(1, diagram);
    }

    private HorizontalLayout toolbar() {
        Button reveal = new Button("Reveal next", event -> {
            if (revealNext()) {
                Notification.show("Added a package");
            } else {
                Notification.show("The whole catalog is on the graph");
            }
        });
        // A toggle, so the update path can be exercised more than once. It
        // pushes back the nodes already on the graph, instead of clearing and
        // re-adding them.
        Button highlight = new Button("Thicken borders");
        highlight.addClickListener(event -> {
            thickBorders = !thickBorders;
            List<Node> updated = new ArrayList<>();
            CATALOG.stream().limit(revealed).forEach(pkg -> {
                Node node = node(pkg);
                node.setBorderWidth(thickBorders ? 5 : 2);
                updated.add(node);
            });
            diagram.updateNodes(updated);
            highlight.setText(
                    thickBorders ? "Restore borders" : "Thicken borders");
        });
        Button fit = new Button("Fit to screen", event -> {
            diagram.fitToScreen();
            diagram.stabilize();
        });

        HorizontalLayout toolbar = new HorizontalLayout(reveal, highlight, fit);
        toolbar.setWidthFull();
        // The root layout has spacing off, so the gap is set here.
        toolbar.getStyle().set("margin-bottom", "var(--lumo-space-s)");
        return toolbar;
    }

    /**
     * Adds the next package of the catalog, with its edges.
     *
     * @return whether anything was added
     */
    private boolean revealNext() {
        if (revealed >= CATALOG.size()) {
            return false;
        }

        Package pkg = CATALOG.get(revealed++);
        diagram.addNodes(List.of(node(pkg)));

        List<Edge> edges = Arrays.stream(pkg.dependsOn())
                .map(target -> new Edge(pkg.id(), target)).toList();
        if (!edges.isEmpty()) {
            diagram.addEdges(edges);
        }

        describe(pkg);
        return true;
    }

    private void describe(Package pkg) {
        nodeDetails.put(pkg.id(),
                pkg.label() + " is a " + pkg.kind().name().toLowerCase()
                        + " package with " + pkg.dependsOn().length
                        + " direct dependencies");
    }

    private Node node(Package pkg) {
        Node node = new Node(pkg.id());
        node.setLabel(pkg.label());
        node.setTitle(pkg.kind().name().toLowerCase());
        node.setShape(pkg.kind() == Kind.RUNTIME ? Node.Shape.database
                : Node.Shape.box);
        node.setGroup(pkg.kind().name());
        node.setMass(pkg.kind().mass);
        node.setBorderWidth(2);

        Color color = new Color();
        color.setBackground(pkg.kind().background);
        color.setBorder("#2B2B2B");
        node.setColor(color);

        Shadow shadow = new Shadow();
        shadow.setEnabled(true);
        node.setShadow(shadow);

        return node;
    }
}
