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
package org.vaadin.addons.componentfactory.visnetwork.options;

/**
 * The circle drawn when an edge starts and ends at the same node. The source
 * had this as a flat {@code edges.selfReferenceSize}.
 */
public class SelfReference {

    /*
    Boxed: as an int the source sent 0 on every edge, and since Edge extends Edges that won
    over any module-level size, so the circle had no radius and was never drawn.
     */
    private Integer size;
    /*
    Not in the source, so these two carry vis-network's defaults.
     */
    private double angle = Math.PI / 4;
    private boolean renderBehindTheNode = true;

    /** @return the radius, or {@code null} when not set. */
    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public boolean isRenderBehindTheNode() {
        return renderBehindTheNode;
    }

    public void setRenderBehindTheNode(boolean renderBehindTheNode) {
        this.renderBehindTheNode = renderBehindTheNode;
    }
}
