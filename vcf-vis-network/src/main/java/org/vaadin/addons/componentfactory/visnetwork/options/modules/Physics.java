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
package org.vaadin.addons.componentfactory.visnetwork.options.modules;

import org.vaadin.addons.componentfactory.visnetwork.options.Stabilization;
import org.vaadin.addons.componentfactory.visnetwork.options.physics.BarnesHut;
import org.vaadin.addons.componentfactory.visnetwork.options.physics.ForceAtlas2Based;
import org.vaadin.addons.componentfactory.visnetwork.options.physics.HierarchicalRepulsion;
import org.vaadin.addons.componentfactory.visnetwork.options.physics.Repulsion;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by roshans on 10/29/14.
 */
public class Physics {
    /*
    Toggle the physics system on or off. This property is optional.
    If you define any of the options below and enabled is undefined, this will be set to true.
     */
    boolean enabled = true;
    /*
    BarnesHut is a quadtree based gravity model. This is the fastest, default and recommended solver for non-hierarchical layouts.
     */
    private BarnesHut barnesHut = new BarnesHut();
    /*
    Force Atlas 2 has been developed by <a href="http://journals.plos.org/plosone/article?id=10.1371/journal.pone.0098679"
    target="_blank">Jacomi <i>et al</i> (2014)</a> for use with Gephi.
    The forceAtlas2Based solver makes use of some of the equations provided
    by them and makes use of the barnesHut implementation in vis. The main differences are the central gravity model,
    which is here distance independent, and the repulsion being linear instead of quadratic. Finally, all node masses have a
    multiplier based on the amount of connected edges plus one.
     */

    private ForceAtlas2Based forceAtlas2Based = new ForceAtlas2Based();
    /*
    The repulsion model assumes nodes have a simplified repulsion field around them.
    It's force linearly decreases from 1 (at 0.5*nodeDistance and smaller) to 0 (at 2*nodeDistance).
     */
    private Repulsion repulsion = new Repulsion();
    /*
    This model is based on the repulsion solver but the levels are taken into account and the forces are normalized.
     */
    private HierarchicalRepulsion hierarchicalRepulsion = new HierarchicalRepulsion();
    /*
    The physics module limits the maximum velocity of the nodes to increase the time to stabilization. This is the maximium value.
     */
    float maxVelocity = 50;
    /*
    Once the minimum velocity is reached for all nodes, we assume the network has been stabilized and the simulation stops.

    Source: 0.1f. At that threshold the network never comes to rest under vis-network 10.
     */
    float minVelocity = 0.75f;
    /*
    You can select your own solver. Possible options: 'barnesHut', 'repulsion', 'hierarchicalRepulsion',
    'forceAtlas2Based'. When setting the hierarchical layout, the hierarchical repulsion solver is automaticaly selected,
     regardless of what you fill in here.
     */
    Solver solver = Solver.barnesHut;
    /*
    When true, the network is stabilized on load using default settings. If false, stabilization is disabled.
    To further customize this, you can supply an object.
     */
    Stabilization stabilization = new Stabilization();
    /*
   The physics simulation is discrete. This means we take a step in time, calculate the forces, move the nodes and take another step.
   If you increase this number the steps will be too large and the network can get unstable.
   If you see a lot of jittery movement in the network, you may want to reduce this value a little.
    */
    float timestep = 0.5f;
    /*
    If this is enabled, the timestep will intelligently be adapted (only during the stabilization stage if stabilization is enabled!)
    to greatly decrease stabilization times.The timestep configured above is taken as the minimum timestep.
    This can be further improved by using the improvedLayout algorithm.
     */
    boolean adaptiveTimestep = true;


    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public float getMaxVelocity() {
        return maxVelocity;
    }

    public void setMaxVelocity(float maxVelocity) {
        this.maxVelocity = maxVelocity;
    }

    public float getMinVelocity() {
        return minVelocity;
    }

    public void setMinVelocity(float minVelocity) {
        this.minVelocity = minVelocity;
    }

    public Solver getSolver() {
        return solver;
    }

    public void setSolver(Solver solver) {
        this.solver = solver;
    }

    public Stabilization getStabilization() {
        if (stabilization == null) {
            stabilization = new Stabilization();
        }
        return stabilization;
    }

    public void setStabilization(Stabilization stabilization) {
        this.stabilization = stabilization;
    }

    public float getTimestep() {
        return timestep;
    }

    public void setTimestep(float timestep) {
        this.timestep = timestep;
    }

    public boolean isAdaptiveTimestep() {
        return adaptiveTimestep;
    }

    public void setAdaptiveTimestep(boolean adaptiveTimestep) {
        this.adaptiveTimestep = adaptiveTimestep;
    }

    public BarnesHut getBarnesHut() {
        if (barnesHut == null) {
            barnesHut = new BarnesHut();
        }
        return barnesHut;
    }

    public void setBarnesHut(BarnesHut barnesHut) {
        this.barnesHut = barnesHut;
    }

    public ForceAtlas2Based getForceAtlas2Based() {
        if (forceAtlas2Based == null) {
            forceAtlas2Based = new ForceAtlas2Based();
        }
        return forceAtlas2Based;
    }

    public void setForceAtlas2Based(ForceAtlas2Based forceAtlas2Based) {
        this.forceAtlas2Based = forceAtlas2Based;
    }

    public Repulsion getRepulsion() {
        if (repulsion == null) {
            repulsion = new Repulsion();
        }
        return repulsion;
    }

    public void setRepulsion(Repulsion repulsion) {
        this.repulsion = repulsion;
    }

    public HierarchicalRepulsion getHierarchicalRepulsion() {
        if (hierarchicalRepulsion == null) {
            hierarchicalRepulsion = new HierarchicalRepulsion();
        }
        return hierarchicalRepulsion;
    }

    public void setHierarchicalRepulsion(HierarchicalRepulsion hierarchicalRepulsion) {
        this.hierarchicalRepulsion = hierarchicalRepulsion;
    }


    public enum Solver {
        @JsonProperty("barnesHut")
        barnesHut,
        @JsonProperty("repulsion")
        repulsion,
        @JsonProperty("hierarchicalRepulsion")
        hierarchicalRepulsion,
        @JsonProperty("forceAtlas2Based")
        forceAtlas2Based;
    }


}
