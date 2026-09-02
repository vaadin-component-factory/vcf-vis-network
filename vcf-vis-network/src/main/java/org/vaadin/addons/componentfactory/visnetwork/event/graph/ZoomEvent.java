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

/** Fired when the view is zoomed, whether by the user or by the application. */
@SuppressWarnings("serial")
public class ZoomEvent extends GraphEvent {

    private String direction = "";
    private double scale = 0;
    private int pointerX = 0;
    private int pointerY = 0;

    /** @param properties the direction, the resulting scale and the point zoomed on. */
    public ZoomEvent(NetworkDiagram source, boolean fromClient,
            ObjectNode properties) {
        super(source, fromClient);
        if (properties != null) {
            if (properties.get("direction") != null) {
                direction = properties.get("direction").asString();
            }
            if (properties.get("scale") != null) {
                scale = properties.get("scale").asDouble();
            }
            ObjectNode pointer = (ObjectNode) properties.get("pointer");
            if (pointer != null) {
                if (pointer.get("x") != null) {
                    pointerX = (int) pointer.get("x").asDouble();
                }
                if (pointer.get("y") != null) {
                    pointerY = (int) pointer.get("y").asDouble();
                }
            }
        }
    }

    /** @return vis-network's own notation, {@code "+"} or {@code "-"}. */
    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    /** @return true when zooming in, so that callers need not read the sign. */
    public boolean isZoomingIn() {
        return "+".equals(direction);
    }

    /** @return the scale the view is at after the zoom. */
    public double getScale() {
        return scale;
    }

    public void setScale(double scale) {
        this.scale = scale;
    }

    /** @return the x the zoom was centred on, in DOM coordinates. */
    public int getPointerX() {
        return pointerX;
    }

    public void setPointerX(int pointerX) {
        this.pointerX = pointerX;
    }

    /** @return the y the zoom was centred on, in DOM coordinates. */
    public int getPointerY() {
        return pointerY;
    }

    public void setPointerY(int pointerY) {
        this.pointerY = pointerY;
    }
}
