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
package org.vaadin.addons.componentfactory.visnetwork;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.vaadin.addons.componentfactory.visnetwork.options.modules.Nodes;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by roshans on 10/10/14.
 */
public class Node extends Nodes {

    private String id;
    private transient List<Edge> edgeList = new ArrayList<>();
    private transient Map<String, Edge> edgeMap = new HashMap<>();

    @Override
    public boolean equals(Object obj) {
    	if (!(obj instanceof Node))
    		return false;
    	Node other = (Node)obj;
    	return other.id.equals(id);
    }
    
    @Override
    public int hashCode() {
    	return id.hashCode();
    }
    
    public Node(int id){
        this.id = Integer.toString(id);
    }

    public Node(String id){
        this.id = id;
    }
    public Node(int id, String label) {
        this.id = Integer.toString(id);
        setLabel(label);
    }

    public Node(String id, String label) {
        this.id = id;
        setLabel(label);
    }

    public Node(int id, String label, String image) {
        this.id = Integer.toString(id);
        setLabel(label);
        setImage(image);
        setShape(Shape.image);
    }

    public Node(String id, String label, String image) {
        this.id = id;
        setLabel(label);
        setImage(image);
        setShape(Shape.image);

    }

    public Node(int id, String label, Node.Shape shape, String group) {
        this.id = Integer.toString(id);
        setShape(shape);
        setLabel(label);
        setGroup(group);
    }

    public Node(String id, String label, Node.Shape shape, String group) {
        this.id = id;
        setLabel(label);
        setShape(shape);
        setGroup(group);
    }

    public Node(int id, String label, Node.Shape shape, String group, String image) {
        this.id = Integer.toString(id);
        setLabel(label);
        setShape(shape);
        setGroup(group);
        setImage(image);
        setShape(Shape.image);
    }

    public Node(String id, String label, Node.Shape shape, String group, String image) {
        this.id = id;
        setLabel(label);
        setShape(shape);
        setGroup(group);
        setImage(image);
        setShape(Shape.image);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Edge> getEdgeList() {
        return edgeList;
    }

    public void setEdgeList(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public List<Edge> getConnectedEdges() {
        return edgeList;
    }

    public void setConnectedEdges(List<Edge> edgeList) {
        this.edgeList = edgeList;
    }

    public void addEdgeToList(Edge edge) {
        edgeList.add(edge);
    }

    public void removeEdgeFromList(Edge edge) {
        edgeList.remove(edge);
    }

    public Map<String, Edge> getEdgeMap() {
        return edgeMap;
    }

    public void setEdgeMap(Map<String, Edge> edgeMap) {
        this.edgeMap = edgeMap;
    }

    public static enum Shape {
        @JsonProperty("ellipse")
        ellipse,
        @JsonProperty("circle")
        circle,
        @JsonProperty("database")
        database,
        @JsonProperty("box")
        box,
        @JsonProperty("text")
        text,
        @JsonProperty("image")
        image,
        @JsonProperty("circularImage")
        circularImage,
        @JsonProperty("diamond")
        diamond,
        @JsonProperty("dot")
        dot,
        @JsonProperty("star")
        star,
        @JsonProperty("triangle")
        triangle,
        @JsonProperty("triangleDown")
        triangleDown,
        @JsonProperty("square")
        square,
        @JsonProperty("icon")
        icon;
    }
}
