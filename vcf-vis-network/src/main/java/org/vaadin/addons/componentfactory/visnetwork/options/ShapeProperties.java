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

public class ShapeProperties {
    /*
    This property applies to all shapes that have borders.
    You set the dashes by supplying an Array. Array formart:
    [dash length, gap length]. You can also use a Boolean,
    false is disable and true is default [5,15].
     */
    boolean borderDashes;
    /*
    This property is used only for the box shape.
    It allows you to determine the roundness of the corners of the shape.
     */
    int borderRadius = 56;
    /*
    This property only applies to the image and circularImage shapes. When false,
    the size option is used, when true, the size of the image is used.
    Important: if this is set to true, the image cannot be scaled with the value option!
     */
    boolean useImageSize;
    /*
    This property only applies to the image shape. When true, the color object is used.
     A rectangle with the background color is drawn behind it and it has a border.
      This means all border options are taken into account.
     */
    boolean useBorderWithImage;

}
