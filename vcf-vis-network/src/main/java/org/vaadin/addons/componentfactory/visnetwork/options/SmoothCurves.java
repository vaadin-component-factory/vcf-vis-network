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

/**
 * Created by roshans on 10/29/14.
 */
public class SmoothCurves {

    boolean dynamic = true;
    Type type = Type.continuous;
    float roundness = 0.5f;

    public static enum Type {
        @JsonProperty("continuous")
        continuous,
        @JsonProperty("discrete")
        discrete,
        @JsonProperty("straightCross")
        straightCross,
        @JsonProperty("horizontal")
        horizontal,
        @JsonProperty("vertical")
        vertical
    }

    public boolean isDynamic() {
        return dynamic;
    }

    public void setDynamic(boolean dynamic) {
        this.dynamic = dynamic;
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


}
