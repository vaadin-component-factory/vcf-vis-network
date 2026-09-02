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
 
public class Locale {
    private String edit ="Edit";
    private String del= "Delete selected";
    private String back= "Back";
    private String addNode= "Add Node";
    private String addEdge= "Add Edge";
    private String editNode= "Edit Node";
    private String editEdge= "Edit Edge";
    private String addDescription= "Click in an empty space to place a new node.";
    private String edgeDescription= "Click on a node and drag the edge to another node to connect them.";
    private String editEdgeDescription= "Click on the control points and drag them to a node to connect to it.";
    private String createEdgeError= "Cannot link edges to a cluster.";
    private String deleteClusterError= "Clusters cannot be deleted.";
    private String editClusterError= "Clusters cannot be edited.";


    public String getEdit() {
        return edit;
    }

    public void setEdit(String edit) {
        this.edit = edit;
    }

    public String getDel() {
        return del;
    }

    public void setDel(String del) {
        this.del = del;
    }

    public String getBack() {
        return back;
    }

    public void setBack(String back) {
        this.back = back;
    }

    public String getAddNode() {
        return addNode;
    }

    public void setAddNode(String addNode) {
        this.addNode = addNode;
    }

    public String getAddEdge() {
        return addEdge;
    }

    public void setAddEdge(String addEdge) {
        this.addEdge = addEdge;
    }

    public String getEditNode() {
        return editNode;
    }

    public void setEditNode(String editNode) {
        this.editNode = editNode;
    }

    public String getEditEdge() {
        return editEdge;
    }

    public void setEditEdge(String editEdge) {
        this.editEdge = editEdge;
    }

    public String getAddDescription() {
        return addDescription;
    }

    public void setAddDescription(String addDescription) {
        this.addDescription = addDescription;
    }

    public String getEdgeDescription() {
        return edgeDescription;
    }

    public void setEdgeDescription(String edgeDescription) {
        this.edgeDescription = edgeDescription;
    }

    public String getEditEdgeDescription() {
        return editEdgeDescription;
    }

    public void setEditEdgeDescription(String editEdgeDescription) {
        this.editEdgeDescription = editEdgeDescription;
    }

    public String getCreateEdgeError() {
        return createEdgeError;
    }

    public void setCreateEdgeError(String createEdgeError) {
        this.createEdgeError = createEdgeError;
    }

    public String getDeleteClusterError() {
        return deleteClusterError;
    }

    public void setDeleteClusterError(String deleteClusterError) {
        this.deleteClusterError = deleteClusterError;
    }

    public String getEditClusterError() {
        return editClusterError;
    }

    public void setEditClusterError(String editClusterError) {
        this.editClusterError = editClusterError;
    }
}
