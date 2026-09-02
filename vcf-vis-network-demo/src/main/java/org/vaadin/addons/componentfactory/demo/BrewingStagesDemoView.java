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
import org.vaadin.addons.componentfactory.visnetwork.options.Shadow;
import org.vaadin.addons.componentfactory.visnetwork.options.modules.Layout.Direction;
import org.vaadin.addons.componentfactory.visnetwork.options.modules.Layout.SortMethod;
import org.vaadin.addons.componentfactory.visnetwork.util.Color;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.component.select.Select;

/**
 * The stages of a coffee roasting run, drawn with the hierarchical layout.
 * <p>
 * Every hierarchical property is set here, and the direction, the sort method
 * and whether the nodes carry a level can all be switched at runtime.
 */
@SuppressWarnings("serial")
@Route(value = "brewing-stages", layout = MainLayout.class)
public class BrewingStagesDemoView extends VerticalLayout {

    /** A stage: its level fixes the column the hierarchical layout puts it in. */
    private record Stage(String id, String label, int level, int batches,
            double minutes, String... feeds) {
    }

    private static final List<Stage> STAGES = Arrays.asList(
            new Stage("green", "Green beans", 0, 120, 0.0, "sort"),
            new Stage("sort", "Sorting", 1, 120, 4.5, "dry", "wash"),
            new Stage("dry", "Dry process", 2, 64, 31.0, "roast"),
            new Stage("wash", "Washed process", 2, 56, 27.5, "roast"),
            new Stage("roast", "Roasting", 3, 118, 12.25, "rest"),
            new Stage("rest", "Resting", 4, 118, 2880.0, "grind", "whole"),
            new Stage("grind", "Ground", 5, 70, 1.0),
            new Stage("whole", "Whole bean", 5, 48, 0.5));

    private final Div message = new Div();

    private final NetworkDiagram diagram;

    private Direction direction = Direction.LR;

    private SortMethod sortMethod = SortMethod.directed;

    /** Whether the nodes are sent with a level, or with none at all. */
    private boolean explicitLevels = true;

    public BrewingStagesDemoView() {
        setSizeFull();
        setSpacing(false);

        diagram = new NetworkDiagram(hierarchicalOptions());
        diagram.setWidthFull();
        // So that flex can shrink it below its content instead of overflowing.
        diagram.getStyle().set("min-height", "0");
        diagram.addNodeDoubleClickListener(event -> message.setText(
                "Double-clicked " + event.getNodeIds() + ". A real "
                        + "application would open the stage here"));
        diagram.addNodeSelectListener(event -> message
                .setText("Selected " + event.getNodeIds()));

        message.setId("brewing-stages-message");

        H2 title = new H2("Brewing stages");
        title.getStyle().set("margin", "0 0 var(--lumo-space-s) 0");

        add(title);
        add(new DemoDescription(
                "The hierarchical layout with every property the component "
                        + "exposes. Physics is off, since the layout places "
                        + "the nodes itself.",
                "Switch the direction and the sort method to re-run the "
                        + "layout.",
                "Clear \"Send explicit levels\" so that vis-network derives "
                        + "the hierarchy from the edges. That is what makes "
                        + "the sort method matter.",
                "With levels off, \"directed\" reads the edge directions and "
                        + "\"hubsize\" the edge count. They differ on purpose."));
        add(toolbar(), diagram, message);
        // The diagram takes the height left over. vis-network puts options.height
        // on a frame div of its own inside this element, so the 100% resolves
        // against whatever flex gives this one.
        setFlexGrow(1, diagram);
    }

    /** Builds the options from the current state of the three controls. */
    private Options hierarchicalOptions() {
        Options options = new Options();
        options.setAutoResize(false);
        options.setHeight("100%");
        options.setWidth("100%");
        // The layout places the nodes, so there is nothing to simulate.
        options.getPhysics().setEnabled(false);

        options.getLayout().setImprovedLayout(true);
        options.getLayout().setRandomSeed(0);
        options.getLayout().getHierarchical().setEnabled(true);
        options.getLayout().getHierarchical().setDirection(direction);
        options.getLayout().getHierarchical().setSortMethod(sortMethod);
        options.getLayout().getHierarchical().setLevelSeparation(200);
        options.getLayout().getHierarchical().setNodeSpacing(40);
        options.getLayout().getHierarchical().setTreeSpacing(40);
        options.getLayout().getHierarchical().setBlockShifting(false);
        options.getLayout().getHierarchical().setEdgeMinimization(true);
        options.getLayout().getHierarchical().setParentCentralization(true);

        options.getData().nodes.addAll(stageNodes());
        options.getData().edges.addAll(stageEdges());
        return options;
    }

    private HorizontalLayout toolbar() {
        Select<Direction> directions = new Select<>();
        directions.setLabel("Direction");
        directions.setItems(Direction.values());
        directions.setValue(direction);
        directions.addValueChangeListener(event -> {
            direction = event.getValue();
            reapplyLayout();
        });

        Select<SortMethod> sortMethods = new Select<>();
        sortMethods.setLabel("Sort method");
        sortMethods.setItems(SortMethod.values());
        sortMethods.setValue(sortMethod);
        sortMethods.addValueChangeListener(event -> {
            sortMethod = event.getValue();
            reapplyLayout();
        });

        // With no level on any node, the sort method is what decides the hierarchy.
        Checkbox levels = new Checkbox("Send explicit levels");
        levels.setValue(explicitLevels);
        levels.addValueChangeListener(event -> {
            explicitLevels = event.getValue();
            reapplyLayout();
        });

        Button fit = new Button("Fit to screen", event -> {
            diagram.fitToScreen();
            diagram.stabilize();
        });

        HorizontalLayout toolbar = new HorizontalLayout(directions, sortMethods,
                levels, fit);
        toolbar.setWidthFull();
        // The root layout has spacing off, so the gap is set here.
        toolbar.getStyle().set("margin-bottom", "var(--lumo-space-s)");
        toolbar.setAlignItems(Alignment.BASELINE);
        return toolbar;
    }

    /**
     * Rebuilds the network with fresh options. See
     * {@link NetworkDiagram#reinitialize(Options)} for why it is not
     * {@code updateOptions}.
     */
    private void reapplyLayout() {
        diagram.reinitialize(hierarchicalOptions());
        Notification.show(direction + " / " + sortMethod + " / "
                + (explicitLevels ? "explicit levels" : "levels inferred"));
    }

    private List<Node> stageNodes() {
        List<Node> nodes = new ArrayList<>();
        for (Stage stage : STAGES) {
            boolean terminal = stage.feeds().length == 0;

            Node node = new Node(stage.id());
            if (explicitLevels) {
                node.setLevel(stage.level());
            }
            node.setShape(terminal ? Node.Shape.ellipse : Node.Shape.box);
            node.setBorderWidth(2);
            // Without font.multi the tags show up literally in the label.
            node.getFont().setMulti("html");
            node.setLabel(String.format("%s\n<b>%d</b> kg / <i>%s</i>",
                    stage.label(), stage.batches(),
                    humanDuration(stage.minutes())));
            node.setTitle(explicitLevels
                    ? stage.label() + " sent at level " + stage.level()
                    : stage.label() + " sent with no level");

            Color color = new Color();
            color.setBackground(terminal ? "#6B3F8C" : "#3F6E8C");
            color.setBorder("#2B2B2B");
            node.setColor(color);

            Shadow shadow = new Shadow();
            shadow.setEnabled(true);
            node.setShadow(shadow);

            nodes.add(node);
        }
        return nodes;
    }

    private List<Edge> stageEdges() {
        List<Edge> edges = new ArrayList<>();
        for (Stage stage : STAGES) {
            for (String target : stage.feeds()) {
                edges.add(new Edge(stage.id(), target));
            }
        }
        return edges;
    }

    private static String humanDuration(double minutes) {
        if (minutes == 0.0) {
            return "no wait";
        }
        if (minutes >= 60.0) {
            return String.format("%.1f h", minutes / 60.0);
        }
        return String.format("%.2f min", minutes);
    }
}
