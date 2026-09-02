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
package org.vaadin.addons.componentfactory.visnetwork;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import org.junit.Test;
import org.vaadin.addons.componentfactory.visnetwork.options.Options;
import org.vaadin.addons.componentfactory.visnetwork.options.modules.Layout;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;

/**
 * Pins down the JSON that reaches vis-network, since the Gson to Jackson move
 * can change it without breaking compilation.
 */
public class OptionsJsonTest {

    private Options optionsWithGraph() {
        Options options = new Options();
        options.getData().nodes.addAll(
                Arrays.asList(new Node("1", "one"), new Node("2", "two")));
        options.getData().edges.add(new Edge("1", "2"));
        return options;
    }

    @Test
    public void nullProperties_areOmitted() {
        // For vis-network an explicit null is not the same as an absent property.
        String json = NetworkDiagram.toJson(optionsWithGraph());

        assertFalse("no property may be serialized as null: " + json,
                json.contains(":null"));
    }

    @Test
    public void nodesAndEdges_travelUnderData() {
        // The connector splits options.data into the two DataSets.
        String json = NetworkDiagram.toJson(optionsWithGraph());

        assertTrue("expected a data member: " + json,
                json.contains("\"data\""));
        assertTrue("expected nodes under data: " + json,
                json.contains("\"nodes\""));
        assertTrue("expected edges under data: " + json,
                json.contains("\"edges\""));
        assertTrue("expected the node label: " + json,
                json.contains("\"one\""));
    }

    @Test
    public void enums_useTheirJsonPropertyName() {
        // Without @JsonProperty Jackson would emit the constant name.
        Node node = new Node("1", "one");
        node.setShape(Node.Shape.circularImage);

        String json = NetworkDiagram.toJson(node);

        assertTrue("expected the annotated enum value: " + json,
                json.contains("\"circularImage\""));
    }

    @Test
    public void propertyNames_comeFromFieldsNotGetters() {
        // On the parsed tree: "from" occurs twice, the endpoint and arrows.from.
        JsonNode edge = parse(NetworkDiagram.toJson(new Edge("1", "2")));

        assertEquals("1", edge.get("from").asString());
        assertEquals("2", edge.get("to").asString());
        assertTrue("arrows should stay a nested object",
                edge.get("arrows").isObject());
        assertTrue("the nested arrows.from is a different property",
                edge.get("arrows").get("from").isObject());
    }

    @Test
    public void optionsData_isShapedForTheConnector() {
        // The nesting and the array types are what the connector reads.
        JsonNode options = parse(NetworkDiagram.toJson(optionsWithGraph()));
        JsonNode data = options.get("data");

        assertTrue("data must be an object: " + options, data.isObject());
        assertTrue("nodes must be an array", data.get("nodes").isArray());
        assertTrue("edges must be an array", data.get("edges").isArray());
        assertEquals(2, data.get("nodes").size());
        assertEquals(1, data.get("edges").size());
        assertEquals("1", data.get("nodes").get(0).get("id").asString());
    }

    @Test
    public void nulledOutModules_disappearFromThePayload() {
        // Nulling a module is how an application keeps vis-network's defaults.
        Options options = new Options();
        options.setPhysics(null);
        options.setInteraction(null);
        options.setManipulation(null);
        options.setLocale(null);
        options.setLocales(null);

        JsonNode json = parse(NetworkDiagram.toJson(options));

        assertTrue("physics must not be sent", json.get("physics") == null);
        assertTrue("interaction must not be sent",
                json.get("interaction") == null);
        assertTrue("manipulation must not be sent",
                json.get("manipulation") == null);
        assertTrue("locale must not be sent", json.get("locale") == null);
        assertTrue("locales must not be sent", json.get("locales") == null);
    }

    @Test
    public void emptyString_survives() {
        // NON_EMPTY would drop it, which is why the mapper uses NON_NULL.
        Node node = new Node("1", "");

        JsonNode json = parse(NetworkDiagram.toJson(node));

        assertTrue("a blank label must still be sent",
                json.get("label") != null);
        assertEquals("", json.get("label").asString());
    }

    @Test
    public void hierarchicalLayout_serializesEveryProperty() {
        // Hierarchical is a non-static inner class, so watch for this$0.
        Options options = new Options();
        options.getLayout().setImprovedLayout(true);
        options.getLayout().setRandomSeed(0);
        options.getLayout().getHierarchical().setEnabled(true);
        options.getLayout().getHierarchical()
                .setSortMethod(Layout.SortMethod.directed);
        options.getLayout().getHierarchical()
                .setDirection(Layout.Direction.LR);
        options.getLayout().getHierarchical().setLevelSeparation(200);
        options.getLayout().getHierarchical().setNodeSpacing(40);
        options.getLayout().getHierarchical().setTreeSpacing(40);
        options.getLayout().getHierarchical().setBlockShifting(false);
        options.getLayout().getHierarchical().setEdgeMinimization(true);
        options.getLayout().getHierarchical().setParentCentralization(true);

        JsonNode hierarchical = parse(NetworkDiagram.toJson(options))
                .get("layout").get("hierarchical");

        assertTrue("no synthetic outer reference may leak: " + hierarchical,
                hierarchical.get("this$0") == null);
        assertTrue(hierarchical.get("enabled").asBoolean());
        assertEquals("directed", hierarchical.get("sortMethod").asString());
        assertEquals("LR", hierarchical.get("direction").asString());
        assertEquals(200, hierarchical.get("levelSeparation").asInt());
        assertEquals(40, hierarchical.get("nodeSpacing").asInt());
        assertEquals(40, hierarchical.get("treeSpacing").asInt());
        assertFalse(hierarchical.get("blockShifting").asBoolean());
        assertTrue(hierarchical.get("edgeMinimization").asBoolean());
        assertTrue(hierarchical.get("parentCentralization").asBoolean());
    }

    @Test
    public void unconfiguredOptions_sendEveryModuleLikeTheSource() {
        // groups is the one module the source left unset.
        JsonNode json = parse(NetworkDiagram.toJson(new Options()));

        for (String module : new String[] { "physics", "layout", "nodes",
                "edges", "interaction", "manipulation", "configure", "locale",
                "locales" }) {
            assertTrue(module + " must be sent as the source sent it: " + json,
                    json.get(module) != null);
        }
        assertTrue("groups was unset in the source too: " + json,
                json.get("groups") == null);
    }

    @Test
    public void physics_shipsEverySolverLikeTheSource() {
        JsonNode physics = parse(NetworkDiagram
                .toJson(new Options())).get("physics");

        assertTrue("stabilization", physics.get("stabilization") != null);
        assertTrue("barnesHut", physics.get("barnesHut") != null);
        assertTrue("repulsion", physics.get("repulsion") != null);
        assertTrue("forceAtlas2Based",
                physics.get("forceAtlas2Based") != null);
        assertTrue("hierarchicalRepulsion",
                physics.get("hierarchicalRepulsion") != null);
    }

    @Test
    public void freshNode_carriesNoPosition() {
        // vis-network pins a node at any x it receives, zero included.
        JsonNode node = parse(NetworkDiagram.toJson(new Node("1", "one")));

        assertTrue("an unpositioned node must not carry x: " + node,
                node.get("x") == null);
        assertTrue("an unpositioned node must not carry y: " + node,
                node.get("y") == null);
    }

    @Test
    public void freshNode_sendsNoOptionItDidNotSet() {
        // A primitive cannot say "not set", so these were sent as 0 and false.
        JsonNode node = parse(NetworkDiagram.toJson(new Node("1", "one")));

        assertTrue("level must be left to vis-network: " + node,
                node.get("level") == null);
        assertTrue("borderWidthSelected must be left to vis-network: " + node,
                node.get("borderWidthSelected") == null);
        assertTrue("labelHighlightBold must be left to vis-network: " + node,
                node.get("labelHighlightBold") == null);
    }

    @Test
    public void nodeLevel_stillTravelsWhenSet() {
        Node node = new Node("roast", "Roasting");
        node.setLevel(3);

        assertEquals(3, parse(NetworkDiagram.toJson(node)).get("level").asInt());
    }

    @Test
    public void positionedNode_stillSendsItsPosition() {
        Node node = new Node("1", "one");
        node.setX(120);
        node.setY(-40);

        JsonNode json = parse(NetworkDiagram.toJson(node));

        assertEquals(120, json.get("x").asInt());
        assertEquals(-40, json.get("y").asInt());
    }

    @Test
    public void selfReference_travelsUnderItsCurrentName() {
        // vis-network moved the flat vis.js 4.x name into an object, deprecating it.
        JsonNode edges = parse(NetworkDiagram
                .toJson(new Options())).get("edges");

        assertTrue("the flat option is not accepted any more: " + edges,
                edges.get("selfReferenceSize") == null);
        assertTrue("selfReference must be an object: " + edges,
                edges.get("selfReference").isObject());
    }

    @Test
    public void freshEdge_carriesNoSelfReferenceSize() {
        // Edge extends Edges, so what an Edge carries wins over the module default.
        JsonNode edge = parse(NetworkDiagram.toJson(new Edge("3", "3")));

        assertTrue("an edge must not pin the size: " + edge,
                edge.get("selfReference").get("size") == null);
    }

    @Test
    @SuppressWarnings("deprecation")
    public void deprecatedSelfReferenceSize_stillReachesTheNewOption() {
        Options options = new Options();
        options.getEdges().setSelfReferenceSize(30);

        JsonNode edges = parse(NetworkDiagram.toJson(options)).get("edges");

        // intValue() so this is not ambiguous with assertEquals(Object, Object).
        assertEquals(30, options.getEdges().getSelfReferenceSize().intValue());
        assertEquals(30, edges.get("selfReference").get("size").asInt());
    }

    @Test
    public void physicsPrimitives_matchSourceExceptTheTwoVisNetworkForced() {
        // The source's values, apart from the two vis-network 10 forced.
        JsonNode physics = parse(NetworkDiagram
                .toJson(withPhysics())).get("physics");

        assertTrue(physics.get("enabled").asBoolean());
        assertEquals(50.0, physics.get("maxVelocity").asDouble(), 0.001);
        assertEquals("barnesHut", physics.get("solver").asString());
        assertEquals(0.5, physics.get("timestep").asDouble(), 0.001);
        assertTrue(physics.get("adaptiveTimestep").asBoolean());

        assertEquals("a lower threshold never comes to rest", 0.75,
                physics.get("minVelocity").asDouble(), 0.001);
        assertEquals("anything above 0 keeps the network jittering", 0.0,
                physics.get("barnesHut").get("avoidOverlap").asDouble(), 0.001);
    }

    private static Options withPhysics() {
        Options options = new Options();
        options.getPhysics();
        return options;
    }

    private static JsonNode parse(String json) {
        return JsonMapper.builder().build().readTree(json);
    }
}
