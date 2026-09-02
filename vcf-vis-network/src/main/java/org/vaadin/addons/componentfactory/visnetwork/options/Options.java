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

import org.vaadin.addons.componentfactory.visnetwork.options.modules.Configure;
import org.vaadin.addons.componentfactory.visnetwork.options.modules.Edges;
import org.vaadin.addons.componentfactory.visnetwork.options.modules.Interaction;
import org.vaadin.addons.componentfactory.visnetwork.options.modules.Layout;
import org.vaadin.addons.componentfactory.visnetwork.options.modules.Manipulation;
import org.vaadin.addons.componentfactory.visnetwork.options.modules.Nodes;
import org.vaadin.addons.componentfactory.visnetwork.options.modules.Physics;

/**
 * Created by roshans on 10/10/14.
 * <p>
 * The options handed to vis-network when the diagram is built. Every module is
 * sent, carrying the defaults the source sent, so that a migrated application
 * gets the options it already got. Setting one to {@code null} drops it.
 */
public class Options {
    private boolean autoResize = true;
    private String height = "100%";
    private String width = "100%";
    private String locale = "en";
    private Locales locales = new Locales();
    private boolean clickToUse = false;
    private Configure configure = new Configure();
    private Edges edges = new Edges();
    private Nodes nodes = new Nodes();
    private Groups groups;
    private Layout layout = new Layout();
    private Interaction interaction = new Interaction();
    private Manipulation manipulation = new Manipulation();
    private Physics physics = new Physics();
    private Data data = new Data();


    public boolean isAutoResize() {
        return autoResize;
    }

    public void setAutoResize(boolean autoResize) {
        this.autoResize = autoResize;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public Locales getLocales() {
        if (locales == null) {
            locales = new Locales();
        }
        return locales;
    }

    public void setLocales(Locales locales) {
        this.locales = locales;
    }

    public boolean isClickToUse() {
        return clickToUse;
    }

    public void setClickToUse(boolean clickToUse) {
        this.clickToUse = clickToUse;
    }

    public Configure getConfigure() {
        if (configure == null) {
            configure = new Configure();
        }
        return configure;
    }

    public void setConfigure(Configure configure) {
        this.configure = configure;
    }

    public Edges getEdges() {
        if (edges == null) {
            edges = new Edges();
        }
        return edges;
    }

    public void setEdges(Edges edges) {
        this.edges = edges;
    }

    public Nodes getNodes() {
        if (nodes == null) {
            nodes = new Nodes();
        }
        return nodes;
    }

    public void setNodes(Nodes nodes) {
        this.nodes = nodes;
    }

    public Groups getGroups() {
        if (groups == null) {
            groups = new Groups();
        }
        return groups;
    }

    public void setGroups(Groups groups) {
        this.groups = groups;
    }

    public Layout getLayout() {
        if (layout == null) {
            layout = new Layout();
        }
        return layout;
    }

    public void setLayout(Layout layout) {
        this.layout = layout;
    }

    public Interaction getInteraction() {
        if (interaction == null) {
            interaction = new Interaction();
        }
        return interaction;
    }

    public void setInteraction(Interaction interaction) {
        this.interaction = interaction;
    }

    public Manipulation getManipulation() {
        if (manipulation == null) {
            manipulation = new Manipulation();
        }
        return manipulation;
    }

    public void setManipulation(Manipulation manipulation) {
        this.manipulation = manipulation;
    }

    public Physics getPhysics() {
        if (physics == null) {
            physics = new Physics();
        }
        return physics;
    }

    public void setPhysics(Physics physics) {
        this.physics = physics;
    }


    public Data getData() {
        return data;
    }

}
