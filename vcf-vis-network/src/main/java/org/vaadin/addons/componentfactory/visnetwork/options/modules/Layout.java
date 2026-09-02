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

import com.fasterxml.jackson.annotation.JsonProperty;

public class Layout {
    /*
        When NOT using the hierarchical layout, the nodes are randomly positioned initially.
        This means that the settled result is different every time. If you provide a random seed manually,
        the layout will be the same every time. Ideally you try with an undefined seed,
        reload until you are happy with the layout and use the getSeed() method to ascertain the seed.
         */
    Integer randomSeed;
    /*
    When enabled, the network will use the Kamada Kawai algorithm for initial layout.
    For networks larger than 100 nodes, clustering will be performed automatically to reduce the amount of nodes.
    This can greatly improve the stabilization times.
    If the network is very interconnected (no or few leaf nodes),
     this may not work and it will revert back to the old method.
    Performance will be improved in the future.
     */
    boolean improvedLayout = true;

    /*
        When true, the layout engine positions the nodes in a hierarchical fashion using default settings.
        For customization you can supply an object.
         */
    public Hierarchical hierarchical = new Hierarchical();

    public class Hierarchical {
        /*
                Toggle the usage of the hierarchical layout system.
                If this option is not defined, it is set to true if any of the properties in this object are defined.
                 */
        boolean enabled = false;
        /*
        The distance between the different levels.
         */
        int levelSeparation = 150;
        /*
        The direction of the hierarchical layout. The available options are:
        UD, DU, LR, RL. To simplify: up-down, down-up, left-right, right-left.
         */
        Direction direction = Direction.UD;
        /*
        The algorithm used to ascertain the levels of the nodes based on the data.
        The possible options are: hubsize, directed.Hubsize takes the nodes with the most edges and puts them at the top.
        From that the rest of the hierarchy is evaluated. Directed adheres to the to and from data of the edges.
         A --> B so B is a level lower than A.
         */
        SortMethod sortMethod = SortMethod.hubsize;

        /*
        Not in the source, which never sent them, so they carry vis-network's defaults.
         */
        int nodeSpacing = 100;

        private int treeSpacing = 200;

        private boolean edgeMinimization = true;
        private boolean blockShifting = true;
        private boolean parentCentralization = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public int getLevelSeparation() {
            return levelSeparation;
        }

        public void setLevelSeparation(int levelSeparation) {
            this.levelSeparation = levelSeparation;
        }

        public Direction getDirection() {
            return direction;
        }

        public void setDirection(Direction direction) {
            this.direction = direction;
        }

        public SortMethod getSortMethod() {
            return sortMethod;
        }

        public void setSortMethod(SortMethod sortMethod) {
            this.sortMethod = sortMethod;
        }

		public int getNodeSpacing() {
			return nodeSpacing;
		}

		public void setNodeSpacing(int nodeSpacing) {
			this.nodeSpacing = nodeSpacing;
		}

		public boolean isEdgeMinimization() {
			return edgeMinimization;
		}

		public void setEdgeMinimization(boolean edgeMinimization) {
			this.edgeMinimization = edgeMinimization;
		}

		public boolean isBlockShifting() {
			return blockShifting;
		}

		public void setBlockShifting(boolean blockShifting) {
			this.blockShifting = blockShifting;
		}

		public int getTreeSpacing() {
			return treeSpacing;
		}

		public void setTreeSpacing(int treeSpacing) {
			this.treeSpacing = treeSpacing;
		}

		public boolean isParentCentralization() {
			return parentCentralization;
		}

		public void setParentCentralization(boolean parentCentralization) {
			this.parentCentralization = parentCentralization;
		}

    }

    public Integer getRandomSeed() {
        return randomSeed;
    }

    public void setRandomSeed(int randomSeed) {
        this.randomSeed = randomSeed;
    }

    public boolean isImprovedLayout() {
        return improvedLayout;
    }

    public void setImprovedLayout(boolean improvedLayout) {
        this.improvedLayout = improvedLayout;
    }

    public Hierarchical getHierarchical() {
        return hierarchical;
    }

    public void setHierarchical(Hierarchical hierarchical) {
        this.hierarchical = hierarchical;
    }

    /*
    The direction of the hierarchical layout.
    The available options are: UD, DU, LR, RL.
    To simplify: up-down, down-up, left-right, right-left.
     */

    public enum Direction {
        @JsonProperty("UD")
        UD,
        @JsonProperty("DU")
        DU,
        @JsonProperty("LR")
        LR,
        @JsonProperty("RL")
        RL;
    }

    public enum SortMethod {
        @JsonProperty("hubsize")
        hubsize,
        @JsonProperty("directed")
        directed;
    }


}
