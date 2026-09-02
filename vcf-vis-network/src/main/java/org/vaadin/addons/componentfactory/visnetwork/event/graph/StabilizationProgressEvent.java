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
package org.vaadin.addons.componentfactory.visnetwork.event.graph;

import org.vaadin.addons.componentfactory.visnetwork.NetworkDiagram;

import tools.jackson.databind.node.ObjectNode;

/**
 * Fired while vis-network stabilizes the physics simulation. Unlike the other
 * {@link GraphEvent}s this one was already wired under Vaadin 7, so it carries
 * its properties.
 */
@SuppressWarnings("serial")
public class StabilizationProgressEvent extends GraphEvent {

    private int iterations = 0;
    private int total = 0;

    /** @param properties the iterations done so far and the total. */
    public StabilizationProgressEvent(NetworkDiagram source, boolean fromClient,
            ObjectNode properties) {
        super(source, fromClient);
        if (properties != null) {
            if (properties.get("iterations") != null) {
                iterations = (int) properties.get("iterations").asDouble();
            }
            if (properties.get("total") != null) {
                total = (int) properties.get("total").asDouble();
            }
        }
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
