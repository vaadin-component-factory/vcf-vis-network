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
package org.vaadin.addons.componentfactory.visnetwork.event.node;

import org.vaadin.addons.componentfactory.visnetwork.NetworkDiagram;
import org.vaadin.addons.componentfactory.visnetwork.api.Event;

import tools.jackson.databind.node.ObjectNode;

/**
 * Created by roshans on 11/30/14.
 */
@SuppressWarnings("serial")
public class ClickEvent extends Event {

    private int DOMx = 0;
    private int DOMy = 0;
    private int canvasX = 0;
    private int canvasY = 0;

    /** @param properties the nodes and edges under the cursor, and where it was. */
    public ClickEvent(NetworkDiagram source, boolean fromClient, ObjectNode properties) {
        super(source, fromClient);
        readNodeIds(properties);
        readEdgeIds(properties);

        ObjectNode pointer = (ObjectNode) properties.get("pointer");
        if (pointer != null) {
            ObjectNode dom = (ObjectNode) pointer.get("DOM");
            ObjectNode canvas = (ObjectNode) pointer.get("canvas");
            if (dom != null) {
                DOMx = (int) dom.get("x").asDouble();
                DOMy = (int) dom.get("y").asDouble();
            }
            if (canvas != null) {
                canvasX = (int) canvas.get("x").asDouble();
                canvasY = (int) canvas.get("y").asDouble();
            }
        }
    }

    public int getDOMx() {
        return DOMx;
    }

    public void setDOMx(int DOMx) {
        this.DOMx = DOMx;
    }

    public int getDOMy() {
        return DOMy;
    }

    public void setDOMy(int DOMy) {
        this.DOMy = DOMy;
    }

    public int getCanvasX() {
        return canvasX;
    }

    public void setCanvasX(int canvasX) {
        this.canvasX = canvasX;
    }

    public int getCanvasY() {
        return canvasY;
    }

    public void setCanvasY(int canvasY) {
        this.canvasY = canvasY;
    }
}
