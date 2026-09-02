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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by roshans on 10/29/14.
 */
public class Groups {
    /*
    If your nodes have groups defined that are not in the Groups module,
    the module loops over the groups it does have, allocating one for each unknown group.
    When all are used, it goes back to the first group. By setting this to false, the default groups will not be used in this cycle.
     */
    boolean useDefaultGroups = true;


    private Map<String, Group> groups = new HashMap<String, Group>();

    @Deprecated
    public void addGroup(Group group){
        addGroup("default",group);
    }

    public void addGroup(String name, Group group){
        groups.put(name, group);
    }

    @Deprecated
    public void removeGroup(Group group){
        groups.remove("default");
    }

    public void removeGroup(String name){
        groups.remove(name);
    }

    public void clearAll(){
        groups.clear();
    }

    @Deprecated
    public List<Group> getGroups(){
        return (List<Group>) groups.values();
    }

    public Map<String,Group> getGroupsMap(){
        return groups;
    }

}
