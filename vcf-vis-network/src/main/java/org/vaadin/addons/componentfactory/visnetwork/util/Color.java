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
package org.vaadin.addons.componentfactory.visnetwork.util;

/**
 * Created by roshans on 10/22/14.
 */
public class Color {

    private String hover = "#848484";
    private String highlight = "#D2E5FF";
    private String border = "#2B7CE9";
    private String background = "#97C2FC";
    private String color = "#df6b1d";
    private float opacity = 1.0f;
    private Object inherit= "from";

    public Color() {
    }

    public Color(String backgroundColor) {
        this.background = backgroundColor;
    }

    public Color(String backgroundColor, String highlightColor) {
        this.background = backgroundColor;
        this.highlight = highlightColor;
    }

    public Color(String backgroundColor, String hoverColor, String highlightColor) {
        this.background = backgroundColor;
        this.highlight = highlightColor;
        this.hover = hoverColor;
    }

    public Color(String backgroundColor, String hoverColor, String highlightColor, String borderColor) {
        this.background = backgroundColor;
        this.hover = hoverColor;
        this.highlight = highlightColor;
        this.border = borderColor;
    }

    public String getHoverColor() {
        return hover;
    }

    public void setHoverColor(String hover) {
        this.hover = hover;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlightColor(String highlight) {
        this.highlight = highlight;
    }

    public String getBorderColor() {
        return border;
    }

    public void setBorderColor(String border) {
        this.border = border;
    }

    public String getBackgroundColor() {
        return background;
    }

    public void setBackgroundColor(String background) {
        this.background = background;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public float getOpacity() {
        return opacity;
    }

    public void setOpacity(float opacity) {
        this.opacity = opacity;
    }

    public Object getInherit() {
        return inherit;
    }

    public void setInherit(Object inherit) {
        this.inherit = inherit;
    }

    public String getHover() {
        return hover;
    }

    public void setHover(String hover) {
        this.hover = hover;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public String getBorder() {
        return border;
    }

    public void setBorder(String border) {
        this.border = border;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }
}
