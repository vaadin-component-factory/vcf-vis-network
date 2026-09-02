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
package org.vaadin.addons.componentfactory.visnetwork.util;


public class Arrows {

    ArrowOptions to = new ArrowOptions();
    ArrowOptions middle = new ArrowOptions();
    ArrowOptions from = new ArrowOptions();

    public class ArrowOptions {
        boolean enabled = false;
        float scaleFactor = 1;

        public ArrowOptions(boolean enabled, float scaleFactor) {
            this.enabled = enabled;
            this.scaleFactor = scaleFactor;
        }

        public ArrowOptions() {

        }

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public float getScaleFactor() {
            return scaleFactor;
        }

        public void setScaleFactor(float scaleFactor) {
            this.scaleFactor = scaleFactor;
        }

    }


    public ArrowOptions getMiddle() {
        return middle;
    }

    public void setMiddle(ArrowOptions middle) {
        this.middle = middle;
    }

    public ArrowOptions getTo() {
        return to;
    }

    public void setTo(ArrowOptions to) {
        this.to = to;
    }

    public ArrowOptions getFrom() {
        return from;
    }

    public void setFrom(ArrowOptions from) {
        this.from = from;
    }
}
