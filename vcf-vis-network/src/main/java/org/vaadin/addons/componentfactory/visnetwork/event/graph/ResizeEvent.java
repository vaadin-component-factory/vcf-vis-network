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

/** Fired when the canvas changes size, carrying the old size and the new. */
@SuppressWarnings("serial")
public class ResizeEvent extends GraphEvent {

    private int width = 0;
    private int height = 0;
    private int oldWidth = 0;
    private int oldHeight = 0;

    /** @param properties the new canvas size and the old. */
    public ResizeEvent(NetworkDiagram source, boolean fromClient,
            ObjectNode properties) {
        super(source, fromClient);
        if (properties != null) {
            if (properties.get("width") != null) {
                width = (int) properties.get("width").asDouble();
            }
            if (properties.get("height") != null) {
                height = (int) properties.get("height").asDouble();
            }
            if (properties.get("oldWidth") != null) {
                oldWidth = (int) properties.get("oldWidth").asDouble();
            }
            if (properties.get("oldHeight") != null) {
                oldHeight = (int) properties.get("oldHeight").asDouble();
            }
        }
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getOldWidth() {
        return oldWidth;
    }

    public void setOldWidth(int oldWidth) {
        this.oldWidth = oldWidth;
    }

    public int getOldHeight() {
        return oldHeight;
    }

    public void setOldHeight(int oldHeight) {
        this.oldHeight = oldHeight;
    }
}
