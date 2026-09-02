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

import java.util.concurrent.atomic.AtomicInteger;

import org.vaadin.addons.componentfactory.visnetwork.options.modules.Edges;
import org.vaadin.addons.componentfactory.visnetwork.util.Color;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by roshans on 10/10/14.
 */
public class Edge extends Edges{
	private static final AtomicInteger counter = new AtomicInteger();
    private String id = Integer.toString(counter.getAndIncrement());
    private String from;
    private String to;

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
    	if (!(obj instanceof Edge))
    		return false;
    	Edge other = (Edge)obj;
    	if (other.id.equals(id))
    		return true;
    	if (other.from.equals(from) && other.to.equals(to))
    		return true;
    	
    	return false;
    }
    
    @Override
    public int hashCode() {
    	return id.hashCode();
    }
    
    public Edge(int from, int to) {
        this.from = Integer.toString(from);
        this.to =Integer.toString(to) ;
    }
    public Edge(String from, String to) {
        this.from =  from;
        this.to = to ;
    }

    public Edge(int from, int to , int width) {
        this.from = Integer.toString(from);
        this.to =Integer.toString(to) ;
        setWidth(width);
    }

    public Edge(String from, String to , int width) {
        this.from =  from;
        this.to = to ;
        setWidth(width);
    }

    public Edge(int from,int to,Color color){
        this.from = Integer.toString(from);
        this.to =Integer.toString(to) ;
        setColor(color);
    }

    public Edge(String from, String to,Color color){
        this.from =  from;
        this.to = to ;
        setColor(color);
    }

    public Edge(int from,int to,Color color,int width ){
        this.from = Integer.toString(from);
        this.to =Integer.toString(to) ;
        setColor(color);
        setWidth(width);
    }
    public Edge(String from, String to,Color color,int width ){
        this.from =  from;
        this.to = to ;
        setColor(color);
        setWidth(width);
    }

    public Edge(int from,int to,Edge.Style style){
        this.from = Integer.toString(from);
        this.to =Integer.toString(to) ;
    }

    public Edge(String from, String to,Edge.Style style){
        this.from =  from;
        this.to = to ;
    }

    public Edge(int from,int to,Edge.Style style, int width){
        this.from = Integer.toString(from);
        this.to =Integer.toString(to) ;
        setWidth(width);
    }
    public Edge(String from, String to,Edge.Style style, int width){
        this.from =  from;
        this.to = to ;
        setWidth(width);

    }

    public Edge(int from,int to,Edge.Style style,Color color){
        this.from = Integer.toString(from);
        this.to =Integer.toString(to) ;
        setColor(color);
    }

    public Edge(String from, String to,Edge.Style style,Color color){
        this.from =  from;
        this.to = to ;
        setColor(color);
    }

    public Edge(int from,int to,Edge.Style style,Color color, int width ){
        this.from = Integer.toString(from);
        this.to =Integer.toString(to) ;
        setColor(color);
        setWidth(width);
    }

    public Edge(String from, String to,Edge.Style style,Color color, int width ){
        this.from =  from;
        this.to = to ;
        setColor(color);
        setWidth(width);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public enum Style {
        @JsonProperty("line")
        line,
        @JsonProperty("arrow")
        arrow,
        @JsonProperty("arrow-center")
        arrowCenter,
        @JsonProperty("dash-line")
        dashLine;
    }
}
