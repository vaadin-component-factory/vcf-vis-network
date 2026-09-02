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

import com.vaadin.flow.component.ComponentEvent;

/**
 * Created by roshans on 11/25/14.
 * <p>
 * Base class for the events that concern the diagram as a whole rather than a
 * single node or edge.
 */
@SuppressWarnings("serial")
public abstract class GraphEvent extends ComponentEvent<NetworkDiagram> {

    /**
     * @param source      the diagram the event came from
     * @param fromClient  whether the client fired it, as opposed to the server
     */
    public GraphEvent(NetworkDiagram source, boolean fromClient) {
        super(source, fromClient);
    }
}
