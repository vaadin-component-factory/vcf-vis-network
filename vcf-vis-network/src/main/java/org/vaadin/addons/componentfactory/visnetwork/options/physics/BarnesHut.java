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
package org.vaadin.addons.componentfactory.visnetwork.options.physics;

/**
 * Created by roshans on 10/29/14.
 */
public class BarnesHut {

    int gravitationalConstant =  -2000;
    float centralGravity =  0.3f;
    int springLength =  95;
    float damping =  0.09f;
    float springConstant =  0.04f;
    /*
    Accepted range: [0 .. 1]. When larger than 0, the size of the node is taken into account.
    The distance will be calculated from the radius of the encompassing circle of the node for both the gravity model.
     Value 1 is maximum overlap avoidance.

    Source: 0.5. Anything above 0 keeps the network jittering under vis-network 10.
     */
    double avoidOverlap = 0;

    public int getGravitationalConstant() {
        return gravitationalConstant;
    }

    public void setGravitationalConstant(int gravitationalConstant) {
        this.gravitationalConstant = gravitationalConstant;
    }

    public int getSpringLength() {
        return springLength;
    }

    public void setSpringLength(int springLength) {
        this.springLength = springLength;
    }

    public float getCentralGravity() {
        return centralGravity;
    }

    public void setCentralGravity(float centralGravity) {
        this.centralGravity = centralGravity;
    }

    public float getSpringConstant() {
        return springConstant;
    }

    public void setSpringConstant(float springConstant) {
        this.springConstant = springConstant;
    }

    public float getDamping() {
        return damping;
    }

    public void setDamping(float damping) {
        this.damping = damping;
    }

    public double getAvoidOverlap() {
        return avoidOverlap;
    }

    public void setAvoidOverlap(double avoidOverlap) {
        this.avoidOverlap = avoidOverlap;
    }

}
