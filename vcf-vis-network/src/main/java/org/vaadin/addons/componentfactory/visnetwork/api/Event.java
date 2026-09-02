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
package org.vaadin.addons.componentfactory.visnetwork.api;

import java.util.ArrayList;
import java.util.List;

import org.vaadin.addons.componentfactory.visnetwork.NetworkDiagram;

import com.vaadin.flow.component.ComponentEvent;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.node.ObjectNode;

/**
 * Created by roshans on 11/30/14.
 * <p>
 * Base class for the events the client sends back for a node or edge
 * interaction, now a Flow {@link ComponentEvent}.
 */
@SuppressWarnings("serial")
public abstract class Event extends ComponentEvent<NetworkDiagram> {

    private List<String> nodeIds;
    private List<String> edgeIds;

    /**
     * @param source      the diagram the event came from
     * @param fromClient  whether the client fired it, as opposed to the server
     */
    public Event(NetworkDiagram source, boolean fromClient) {
        super(source, fromClient);
        nodeIds = new ArrayList<>();
        edgeIds = new ArrayList<>();
    }

    /** Reads the {@code nodes} array of the properties into {@link #getNodeIds()}. */
    protected void readNodeIds(ObjectNode properties) {
        addIds(properties.get("nodes"), nodeIds);
    }

    /** Reads the {@code edges} array of the properties into {@link #getEdgeIds()}. */
    protected void readEdgeIds(ObjectNode properties) {
        addIds(properties.get("edges"), edgeIds);
    }

    private static void addIds(JsonNode array, List<String> target) {
        if (array == null) {
            return;
        }
        for (int i = 0; i < array.size(); i++) {
            target.add(array.get(i).asString());
        }
    }

    public List<String> getNodeIds() {
        return nodeIds;
    }

    public void setNodeIds(List<String> nodeIds) {
        this.nodeIds = nodeIds;
    }

    public List<String> getEdgeIds() {
        return edgeIds;
    }

    public void setEdgeIds(List<String> edgeIds) {
        this.edgeIds = edgeIds;
    }
}
