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
 * Created by sameerawit on 2/16/2016.
 */
public class ForceAtlas2Based {

    private int gravitationalConstant = -50;
    private double centralGravity = 0.01;
    private int springLength = 100;
    private double springConstant = 0.08;
    private double damping = 0.4;
    private double avoidOverlap = 0;


    public int getGravitationalConstant() {
        return gravitationalConstant;
    }

    public void setGravitationalConstant(int gravitationalConstant) {
        this.gravitationalConstant = gravitationalConstant;
    }

    public double getCentralGravity() {
        return centralGravity;
    }

    public void setCentralGravity(double centralGravity) {
        this.centralGravity = centralGravity;
    }

    public int getSpringLength() {
        return springLength;
    }

    public void setSpringLength(int springLength) {
        this.springLength = springLength;
    }

    public double getSpringConstant() {
        return springConstant;
    }

    public void setSpringConstant(double springConstant) {
        this.springConstant = springConstant;
    }

    public double getDamping() {
        return damping;
    }

    public void setDamping(double damping) {
        this.damping = damping;
    }

    public double getAvoidOverlap() {
        return avoidOverlap;
    }

    public void setAvoidOverlap(double avoidOverlap) {
        this.avoidOverlap = avoidOverlap;
    }






}
