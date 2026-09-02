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

import org.vaadin.addons.componentfactory.visnetwork.options.SelfReference;
import org.vaadin.addons.componentfactory.visnetwork.options.Shadow;
import org.vaadin.addons.componentfactory.visnetwork.options.Smooth;
import org.vaadin.addons.componentfactory.visnetwork.util.Arrows;
import org.vaadin.addons.componentfactory.visnetwork.util.Color;
import org.vaadin.addons.componentfactory.visnetwork.util.Font;

/**
 * Created by roshans on 10/29/14.
 */
public class Edges {
    /*
            To draw an arrow with default settings a string can be supplied. For example: <code>arrows:'to, from,  middle'</code>
         or <code>'to;from'</code>, any combination with any seperating symbol is fine. If you want to control the size of
         the arrowheads, you can supply an object.
         */
    private Arrows arrows = new Arrows();
    private Color color;
    private boolean dashes = false;
    private Font font = new Font();
    private boolean hidden = false;
    private float hoverWidth = 0.5f;
    private String label;
    private boolean labelHighlightBold = true;
    /*
        * The physics simulation gives edges a spring length. This value can override the length of the spring in rest.*/
    private int length = 10;
    /*
    The physics simulation gives edges a spring length. This value can override the length of the spring in rest.
     */
   /* private float length = 100;*/
    /*
    When true, the edge is part of the physics simulation. When false, it will not act as a spring.
     */
    private boolean physics = true;
    /*
   If the value option is specified,
   the width of the edges will be scaled according to the
   properties in this object. Keep in mind that when using scaling,
   the width option is neglected.
    */
    /*private Scaling scaling = new Scaling();*/

    /*
        The selectionWidth determines the width of the edge when the edge is selected.
        If a number is supplied, this number will be added to the width.
        Because the width can be altered by the value and the scaling functions,
        a constant multiplier or added value may not give the best results.
        To solve this, you can supply a function. Example:

        var options: {
          edges: {
            selectionWidth: function (width) {return width*2;}
          }
        }
        It receives the Number width of the edge.
        In this simple example multiply the width by 2.
        You can taylor the logic in the function as long as it returns a Number.
        */
    private int selectionWidth = 1;
    /*
    When the to and from nodes are the same, a circle is drawn. The source had a flat
    selfReferenceSize, which vis-network moved into this object.
     */
    private SelfReference selfReference = new SelfReference();
    /*
    When true, the edge casts a shadow using the default settings.
    This can be further refined by supplying an object.
     */
    private Shadow shadow = new Shadow();
    /*
    When true, the edge is drawn as a dynamic quadratic bezier curve. The drawing of these curves takes longer than
    that of straight curves but it looks better.There is a difference between dynamic smooth curves and static smooth curves.
    The dynamic smooth curves have an invisible support node that takes part in the physics simulation. If you have a lot of edges,
    you may want to consider picking a different type of smooth curves then dynamic for better performance.
     */
    private Smooth smooth = new Smooth();


    /*
    The title is shown in a pop-up when the mouse moves over the edge.
     */
    private String title;
    /*
        When a value is set, the edges' width will be scaled using the options
        in the scaling object defined above.
         */
   /* private int value; */

    /*
        The width of the edge. If value is set, this is not used.
         */
    private float width = 0.8f;

    public Arrows getArrows() {
        return arrows;
    }

    public void setArrows(Arrows arrows) {
        this.arrows = arrows;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isDashes() {
        return dashes;
    }

    public void setDashes(boolean dashes) {
        this.dashes = dashes;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public float getHoverWidth() {
        return hoverWidth;
    }

    public void setHoverWidth(float hoverWidth) {
        this.hoverWidth = hoverWidth;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public boolean isLabelHighlightBold() {
        return labelHighlightBold;
    }

    public void setLabelHighlightBold(boolean labelHighlightBold) {
        this.labelHighlightBold = labelHighlightBold;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }


   /* public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }*/

    public boolean isPhysics() {
        return physics;
    }

    public void setPhysics(boolean physics) {
        this.physics = physics;
    }

  /*  public Scaling getScaling() {
        return scaling;
    }

    public void setScaling(Scaling scaling) {
        this.scaling = scaling;
    }*/

    public int getSelectionWidth() {
        return selectionWidth;
    }

    public void setSelectionWidth(int selectionWidth) {
        this.selectionWidth = selectionWidth;
    }

    public SelfReference getSelfReference() {
        return selfReference;
    }

    public void setSelfReference(SelfReference selfReference) {
        this.selfReference = selfReference;
    }

    /** @deprecated use {@link #getSelfReference()} and {@link SelfReference#getSize()}. */
    @Deprecated
    public Integer getSelfReferenceSize() {
        return selfReference.getSize();
    }

    /** @deprecated use {@link #getSelfReference()} and {@link SelfReference#setSize(Integer)}. */
    @Deprecated
    public void setSelfReferenceSize(Integer selfReferenceSize) {
        selfReference.setSize(selfReferenceSize);
    }

    public Shadow getShadow() {
        return shadow;
    }

    public void setShadow(Shadow shadow) {
        this.shadow = shadow;
    }

    public Smooth getSmooth() {
        return smooth;
    }

    public void setSmooth(Smooth smooth) {
        this.smooth = smooth;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /*public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }*/

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }
}
