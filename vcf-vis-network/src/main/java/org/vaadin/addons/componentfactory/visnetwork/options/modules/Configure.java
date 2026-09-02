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

public class Configure {
    /*
    Toggle the configuration interface on or off. This is an optional parameter.
    If left undefined and any of the other properties of this object are defined, this will be set to true.
     */
    private boolean enabled = false;
    /*
    When a boolean, true gives you all options, false will not show any. If a string is supplied, any combination of the
    following is allowed: nodes, edges, layout, interaction, manipulation, physics, selection, renderer. Feel free to
    come up with a fun seperating character. Finally, when supplied an array of strings, any of the previously mentioned
    fields are accepted.When supplying a function, you receive two arguments. The option and the path of the option
    within the options object. If it returns true, the options will be shown in the configurator. Example:
     */
    private String filter = "nodes,edges";
    /*
    This allows you to put the configure list in another HTML container than below the network.
     */
    private String container;
    /*
    Show the generate options button at the bottom of the configurator.
     */
    private boolean showButton=true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }

    public boolean isShowButton() {
        return showButton;
    }

    public void setShowButton(boolean showButton) {
        this.showButton = showButton;
    }

}
