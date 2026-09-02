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

import com.fasterxml.jackson.annotation.JsonProperty;

/*
When true, the edge is drawn as a dynamic quadratic bezier curve. The drawing of these curves takes longer than
that of straight curves but it looks better.
There is a difference between dynamic smooth curves and static smooth curves. The dynamic smooth curves
have an invisible support node that takes part in the physics simulation. If you have a lot of edges,
you may want to consider picking a different type of smooth curves than dynamic for better performance.
 */
public class Smooth {

    private boolean enabled =  true;
    private Type type = Type.dynamic;
    /*
    Accepted range: 0 .. 1.0
    This parameter tweaks the roundness of the smooth curves for all types EXCEPT dynamic.
     */
    private float roundness =  0.5f;


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public float getRoundness() {
        return roundness;
    }

    public void setRoundness(float roundness) {
        this.roundness = roundness;
    }

    /*
    Possible options: 'dynamic',
     'continuous', 'discrete',
     'diagonalCross', 'straightCross',
     'horizontal', 'vertical', 'curvedCW',
     'curvedCCW', 'cubicBezier'.
      Take a look at VISjs example 26 to see what these look like and pick the one that you like best!
    When using dynamic, the edges will have an invisible support node guiding the shape.
    This node is part of the physics simulation.
     */
    public enum Type{
        @JsonProperty("dynamic")
        dynamic,
        @JsonProperty("continuous")
        continuous,
        @JsonProperty("discrete")
        discrete,
        @JsonProperty("diagonalCross")
        diagonalCross,
        @JsonProperty("straightCross")
        straightCross,
        @JsonProperty("horizontal")
        horizontal,
        @JsonProperty("vertical")
        vertical,
        @JsonProperty("curvedCW")
        curvedCW,
        @JsonProperty("curvedCCW")
        curvedCCW,
        @JsonProperty("cubicBezier")
        cubicBezier
    }
}
