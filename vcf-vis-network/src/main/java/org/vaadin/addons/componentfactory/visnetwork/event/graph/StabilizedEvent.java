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
package org.vaadin.addons.componentfactory.visnetwork.event.graph;

import org.vaadin.addons.componentfactory.visnetwork.NetworkDiagram;

import tools.jackson.databind.node.ObjectNode;

/** Fired when the simulation comes to rest, carrying the iterations it took. */
@SuppressWarnings("serial")
public class StabilizedEvent extends GraphEvent {

    private int iterations = 0;

    /** @param properties the iterations the run took. */
    public StabilizedEvent(NetworkDiagram source, boolean fromClient,
            ObjectNode properties) {
        super(source, fromClient);
        if (properties != null && properties.get("iterations") != null) {
            iterations = (int) properties.get("iterations").asDouble();
        }
    }

    /** @return how many iterations the run took to settle. */
    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }
}
