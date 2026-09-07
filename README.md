# Vis Network Add-on for Vaadin Flow

Vis Network is a Vaadin Flow component for displaying and interacting with network graphs, based on the [vis-network](https://visjs.github.io/vis-network/docs/network/) library.

This component is part of Vaadin Component Factory.

## Source

This add-on is a port of the Vaadin 7 add-on [visjs-addon (network)](https://vaadin.com/directory/component/visjs-addon) by sameeraroshan. It was ported from the [`visJs4+` branch](https://github.com/sameeraroshan/visjs/tree/visJs4%2B) of that project, which targeted Vaadin 7.4+ and vis.js 4.x.
The Java API (`NetworkDiagram`, `Node`, `Edge` and the `Options` tree) is kept, so an application written against the source will recognise most of its code. From there the two diverge: this add-on differs in everything the move to Vaadin 25 required, and will likely keep adding fixes and features the source does not have.

`vis-network` is updated to 10.1.2 from the 4.x the source was written against. [vis-network-vaadin](https://vaadin.com/directory/component/vis-network-vaadin) was used as a reference for the Flow integration pattern.

## Requirements

* Vaadin 25.2
* Java 21 or newer

## Branches

| Branch | Vaadin version |
|--------|----------------|
| [main](https://github.com/vaadin-component-factory/vcf-vis-network/tree/main) | 25 |

## Features

* Nodes and edges with per-item shape, colour, border, shadow, icon, image, font and scaling, and HTML, multi-font or plain labels
* Physics-driven layouts with a choice of solver (Barnes-Hut, repulsion, hierarchical repulsion, ForceAtlas2)
* Hierarchical layout in all four directions, with configurable separation, spacing, block shifting, edge minimisation and parent centralisation
* Node groups, clustering options and locales
* Incremental updates: add, update and remove nodes and edges without rebuilding the graph
* Node events (select, click, double click, hover, blur, drag start, drag end) and diagram events (stabilization start, progress, iterations done and stabilized, plus zoom and resize)
* Programmatic redraw, stabilize and fit-to-screen

## Online demo

Go to https://vcf-demos.org/v25/vis-network

## Migrating from the source add-on

In application code there are two changes.

**The package** is now `org.vaadin.addons.componentfactory.visnetwork`.

**Listeners** are Flow `ComponentEventListener`s returning a `Registration`, and one listener receives the ids of every node and edge involved instead of one listener object per node:

```java
Map<String, Runnable> actions = ...;
diagram.addNodeDoubleClickListener(event -> event.getNodeIds().stream()
        .map(actions::get).filter(Objects::nonNull).forEach(Runnable::run));
```

`Node.NodeSelectListener` and friends, `listener.NodeListener`, `listener.GraphListener` and `EventGenerator` are gone, as are the `viewChanged` and `stabilizationDone` listeners, which waited for events vis-network does not emit. Use `stabilizationIterationsDone` or `stabilized` instead.

The rest compiles as it was. `NetworkDiagram` is a Flow `Component` with `HasSize`, so the sizing calls are unchanged, serialization moved from Gson to Jackson 3, and the connector is an ES module.

### Values that changed

The option classes keep the defaults the source sent, so a migrated application receives the options it already received. These could not be kept. The ones now left unset were primitives, and a primitive cannot express "not set".

| Option | Source | Here | Why |
|--------|--------|------|-----|
| `physics.minVelocity` | `0.1` | `0.75` | at `0.1` the network never comes to rest |
| `physics.barnesHut.avoidOverlap` | `0.5` | `0` | above `0` it keeps the network jittering |
| `edges.selfReferenceSize` | `0` on every edge | `selfReference.size`, unset | vis-network moved it into an object and deprecated the flat name. At `0` the circle has no radius, and since `Edge` extends `Edges` no module-level size could override it, so a self-referencing edge was never drawn. `setSelfReferenceSize` still works |
| `Node.x`, `Node.y` | `0` on every node | unset | vis-network pins a node at any position it receives, `0` included, so `updateNode` moved the whole graph to the origin |
| `Node.level`, `Node.borderWidthSelected`, `Node.labelHighlightBold` | `0`, `0` and `false` on every node | unset | `level: 0` stops vis-network deriving the hierarchy from the edges, `borderWidthSelected: 0` flattens the selection border, and `labelHighlightBold: false` overrides vis-network's `true` |

### Changing a hierarchical layout at runtime

Use `reinitialize(Options)` and not `updateOptions(Options)`. Handing hierarchical options to a live network makes vis-network re-run the layout against the graph as it stands, support nodes included, and throw over their missing levels.

## Running the component demo
Run from the command line:
- `mvn -pl vcf-vis-network-demo jetty:run`

Then navigate to `http://localhost:8080/`. The demo has four pages, reachable from the drawer:

| Page | Shows |
|------|-------|
| `/` | vis-network's own [basic usage](https://visjs.github.io/vis-network/examples/network/basicUsage.html) example built through the Java API, for comparing the two side by side |
| `/physics-events` | the counterpart of vis-network's [physics events](https://visjs.github.io/vis-network/examples/network/events/physicsEvents.html) example, reporting the four stabilization events as they arrive |
| `/dependency-graph` | physics layout with stabilization, styling by category, and adding to and updating a live graph |
| `/brewing-stages` | hierarchical layout with every property exposed, switchable direction and sort method, and a toggle between placing nodes by explicit level and letting vis-network derive the hierarchy from the edges |

## Installing the component
Run from the command line:
- `mvn clean install -DskipTests`

## Profiles
### Profile "directory"
This profile, when enabled, will create the zip file for uploading to Vaadin's directory

## Using the component in a Flow application
To use the component in an application using maven,
add the following dependency to your `pom.xml`:
```
<dependency>
    <groupId>org.vaadin.addons.componentfactory</groupId>
    <artifactId>vcf-vis-network</artifactId>
    <version>${component.version}</version>
</dependency>
```

## How to Use

```java
Options options = new Options();
options.setHeight("400px");
options.setWidth("100%");
options.getData().nodes.addAll(List.of(
        new Node("sun", "Sun"),
        new Node("earth", "Earth"),
        new Node("moon", "Moon")));
options.getData().edges.addAll(List.of(
        new Edge("sun", "earth"),
        new Edge("earth", "moon")));

NetworkDiagram diagram = new NetworkDiagram(options);
diagram.addNodeSelectListener(
        event -> Notification.show("Selected " + event.getNodeIds()));
add(diagram);
```

For a hierarchical layout, configure it on the options before constructing the diagram, and set `level` on the nodes to place them:

```java
options.getLayout().getHierarchical().setEnabled(true);
options.getLayout().getHierarchical().setDirection(Direction.LR);
options.getLayout().getHierarchical().setSortMethod(SortMethod.directed);

Node node = new Node("roast", "Roasting");
node.setLevel(3);
```

See the demo module for worked examples of both layouts.

## Flow documentation
Documentation for Vaadin Flow can be found in [Flow documentation](https://vaadin.com/docs/latest/flow).

## License
Distributed under Apache Licence 2.0.

### Sponsored development
Major pieces of development of this add-on has been sponsored by multiple customers of Vaadin. Read more about Expert on Demand at: [Support](https://vaadin.com/support) and [Pricing](https://vaadin.com/pricing).
