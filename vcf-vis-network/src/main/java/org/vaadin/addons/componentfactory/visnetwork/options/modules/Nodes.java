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

import org.vaadin.addons.componentfactory.visnetwork.Node;
import org.vaadin.addons.componentfactory.visnetwork.options.Scaling;
import org.vaadin.addons.componentfactory.visnetwork.options.Shadow;
import org.vaadin.addons.componentfactory.visnetwork.options.ShapeProperties;
import org.vaadin.addons.componentfactory.visnetwork.util.Color;
import org.vaadin.addons.componentfactory.visnetwork.util.Fixed;
import org.vaadin.addons.componentfactory.visnetwork.util.Font;
import org.vaadin.addons.componentfactory.visnetwork.util.Icon;

/**
 * Created by roshans on 10/29/14.
 */
public class Nodes {

    int borderWidth = 1;
    /*
    Boxed: as an int the source sent 0 on every node, which flattened the selection border.
     */
    Integer borderWidthSelected;
    String brokenImage;
    Color color;
    Fixed fixed = new Fixed();
    Font font = new Font();
    private String group = "undefined";
    private boolean hidden = false;
    /*
    These options are only used when the shape is set to ICON
     */
    private Icon icon;
    private String image;
    private String label;
    /*
    Boxed: as a boolean the source sent false, against vis-network's true.
     */
    private Boolean labelHighlightBold;
    /*
    Boxed: as an int the source sent level 0 on every node, so vis-network could never work
    the hierarchy out from the edges.
     */
    private Integer level;
    private int mass = 1;
    private boolean physics = true;
    private Scaling scaling = new Scaling();
    private Shadow shadow = new Shadow();
    private Node.Shape shape = Node.Shape.ellipse;
    private ShapeProperties shapeProperties = new ShapeProperties();
    /*
    The size is used to determine the size of node shapes that do not have the label inside of them.
     These shapes are: image, circularImage, diamond, dot, star, triangle, triangleDown, square and icon
     */
    private int size = 25;
    /*
     Title to be displayed when the user hovers over the node.
     The title can be an HTML element or a string containing plain text or HTML.
     */
    private String title;
    /*
    When a value is set, the nodes will be scaled using the options in the scaling object defined above.
     */
    /*private int value;*/
    //private int x;
    //private int y;


    /** This gives a node an initial x position. When using the hierarchical layout, either the x or y position is set by
     * the layout engine depending on the type of view. The other value remains untouched. When using stabilization,
     * the stabilized position may be different from the initial one. To lock the node to that position use the physics
     * or fixed options.
    *
    * Boxed: vis-network pins a node at any x it receives, zero included, so as ints the source
    * sent x: 0, y: 0 on every node and updateNode moved the whole graph to the origin.
    * */
    private Integer x;

  /*  * This gives a node an initial y position. When using the hierarchical layout, either the x or y position is set by
     * the layout engine depending on the type of view. The other value remains untouched. When using stabilization, the
      * stabilized position may be different from the initial one. To lock the node to that position use the physics or
      * fixed options.
    * */
    private Integer y;

    public int getBorderWidth() {
        return borderWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderWidth = borderWidth;
    }

    /** @return the border width, or {@code null} when not set. */
    public Integer getBorderWidthSelected() {
        return borderWidthSelected;
    }

    public void setBorderWidthSelected(Integer borderWidthSelected) {
        this.borderWidthSelected = borderWidthSelected;
    }

    public String getBrokenImage() {
        return brokenImage;
    }

    public void setBrokenImage(String brokenImage) {
        this.brokenImage = brokenImage;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Fixed getFixed() {
        return fixed;
    }

    public void setFixed(Fixed fixed) {
        this.fixed = fixed;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        setShape(Node.Shape.image);
        this.image = image;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    /** @return whether the label bolds, or {@code null} when not set. */
    public Boolean isLabelHighlightBold() {
        return labelHighlightBold;
    }

    public void setLabelHighlightBold(Boolean labelHighlightBold) {
        this.labelHighlightBold = labelHighlightBold;
    }

    /** @return the level, or {@code null} to let vis-network work it out. */
    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public int getMass() {
        return mass;
    }

    public void setMass(int mass) {
        this.mass = mass;
    }

    public boolean isPhysics() {
        return physics;
    }

    public void setPhysics(boolean physics) {
        this.physics = physics;
    }

    public Scaling getScaling() {
        return scaling;
    }

    public void setScaling(Scaling scaling) {
        this.scaling = scaling;
    }

    public Shadow getShadow() {
        return shadow;
    }

    public void setShadow(Shadow shadow) {
        this.shadow = shadow;
    }

    public Node.Shape getShape() {
        return shape;
    }

    public void setShape(Node.Shape shape) {
        this.shape = shape;
    }

    public ShapeProperties getShapeProperties() {
        return shapeProperties;
    }

    public void setShapeProperties(ShapeProperties shapeProperties) {
        this.shapeProperties = shapeProperties;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

  /*  public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }*/

    /** @return the pinned y, or {@code null} when the node has none. */
    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    /** @return the pinned x, or {@code null} when the node has none. */
    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

}
