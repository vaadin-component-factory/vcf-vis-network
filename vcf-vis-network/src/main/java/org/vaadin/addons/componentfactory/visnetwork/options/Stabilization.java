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
package org.vaadin.addons.componentfactory.visnetwork.options;


public class Stabilization {
    /*
    Toggle the stabilization. This is an optional property. If undefined, it is automatically set to true when any of
    the properties of this object are defined.
         */
    boolean enabled = true;
    /*
    The physics module tries to stabilize the network on load up til a maximum number of iterations defined here.
    If the network stabilized with less, you are finished before the maximum number.
     */
    int iterations = 1000;
    /*
    When stabilizing, the DOM can freeze.
    You can chop the stabilization up into pieces to show a loading bar for instance.
    The interval determines after how many iterations the stabilizationProgress event is triggered.
     */
    int updateInterval = 50;
    /*
    If you have predefined the position of all nodes and only want to stabilize the dynamic smooth edges,
    et this to true.It freezes all nodes except the invisible dynamic smooth curve support nodes.
    If you want the visible nodes to move and stabilize, do not use this.
     */
    boolean onlyDynamicEdges = false;
    /*
    Toggle whether or not you want the view to zoom to fit all nodes when the stabilization is finished.
     */
    boolean fit = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getIterations() {
        return iterations;
    }

    public void setIterations(int iterations) {
        this.iterations = iterations;
    }

    public int getUpdateInterval() {
        return updateInterval;
    }

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }

    public boolean isOnlyDynamicEdges() {
        return onlyDynamicEdges;
    }

    public void setOnlyDynamicEdges(boolean onlyDynamicEdges) {
        this.onlyDynamicEdges = onlyDynamicEdges;
    }

    public boolean isFit() {
        return fit;
    }

    public void setFit(boolean fit) {
        this.fit = fit;
    }
}
