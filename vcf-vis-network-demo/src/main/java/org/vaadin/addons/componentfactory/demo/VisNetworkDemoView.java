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

import java.util.Arrays;

import org.vaadin.addons.componentfactory.visnetwork.Edge;
import org.vaadin.addons.componentfactory.visnetwork.NetworkDiagram;
import org.vaadin.addons.componentfactory.visnetwork.Node;
import org.vaadin.addons.componentfactory.visnetwork.options.Options;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

/**
 * The same graph as vis-network's own
 * <a href="https://visjs.github.io/vis-network/examples/network/basicUsage.html">basic
 * usage</a> example, built through the Java API.
 */
@SuppressWarnings("serial")
@Route(value = "", layout = MainLayout.class)
public class VisNetworkDemoView extends VerticalLayout {

    public VisNetworkDemoView() {
        setSizeFull();
        setSpacing(false);

        H2 title = new H2("Basic network");
        title.getStyle().set("margin", "0 0 var(--lumo-space-s) 0");

        add(title);
        add(new DemoDescription(
                "vis-network's basic usage example built through the Java API: "
                        + "five nodes, five edges, nothing else configured.",
                "Drag a node: physics is on, so its neighbours follow.",
                "Scroll to zoom, drag the background to pan. Both switch off "
                        + "with interaction.zoomView and interaction.dragView.",
                "Node 3 has an edge to itself, drawn as a loop."));

        NetworkDiagram diagram = basicNetwork();
        add(diagram);
        // The diagram takes the height left over. vis-network puts
        // options.height on a frame div of its own inside this element, so the
        // 100% below resolves against whatever flex gives this one.
        setFlexGrow(1, diagram);
    }

    private NetworkDiagram basicNetwork() {
        Options options = new Options();
        options.setHeight("100%");
        options.setWidth("100%");

        options.getData().nodes.addAll(Arrays.asList(new Node("1", "Node 1"),
                new Node("2", "Node 2"), new Node("3", "Node 3"),
                new Node("4", "Node 4"), new Node("5", "Node 5")));
        options.getData().edges.addAll(Arrays.asList(new Edge("1", "3"),
                new Edge("1", "2"), new Edge("2", "4"), new Edge("2", "5"),
                new Edge("3", "3")));

        NetworkDiagram diagram = new NetworkDiagram(options);
        diagram.setId("basic-network");
        diagram.setWidthFull();
        // So that flex can shrink it below its content instead of overflowing.
        diagram.getStyle().set("min-height", "0");
        return diagram;
    }
}
