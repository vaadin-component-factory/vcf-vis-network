/**
 * Created by roshans on 10/10/14.
 *
 * Connector for the Vis Network add-on. Flow has none of the Vaadin 7
 * connector API, so this is an ES module that installs itself on the element
 * as `$connector` and calls the server through `$server`.
 */

import { DataSet, Network } from "vis-network/standalone";

window.Vaadin = window.Vaadin || {};
window.Vaadin.Flow = window.Vaadin.Flow || {};

window.Vaadin.Flow.visNetworkConnector = {
  /**
   * Installs the connector on `element` and builds the network.
   *
   * The server calls this on every attach, and a re-attach has to rebuild the
   * network, so an existing one is destroyed rather than left alone.
   */
  initLazy: function (element, optionsJson) {
    if (element.$connector) {
      element.$connector.init(optionsJson);
      return;
    }

    const connector = {
      network: undefined,
      nodes: new DataSet(),
      edges: new DataSet(),
      options: {},

      init: function (optionsJson) {
        const parsed = parseOptions(optionsJson);
        connector.options = parsed.options;
        connector.nodes = new DataSet(parsed.nodes);
        connector.edges = new DataSet(parsed.edges);
        createNetwork();
      },

      /** The source defined `updateOptions`, so this name never resolved. */
      setOptions: function (optionsJson) {
        const parsed = parseOptions(optionsJson);
        connector.options = parsed.options;
        if (connector.network) {
          connector.network.setOptions(connector.options);
        }
      },

      addNodes: function (json) {
        connector.nodes.add(JSON.parse(json));
      },

      addEdges: function (json) {
        connector.edges.add(JSON.parse(json));
      },

      removeNode: function (json) {
        connector.nodes.remove(JSON.parse(json));
      },

      removeEdge: function (json) {
        connector.edges.remove(JSON.parse(json));
      },

      updateNode: function (json) {
        connector.nodes.update(JSON.parse(json));
      },

      updateEdge: function (json) {
        connector.edges.update(JSON.parse(json));
      },

      clearNodes: function () {
        connector.nodes.clear();
      },

      clearEdges: function () {
        connector.edges.clear();
      },

      stabilize: function () {
        if (connector.network) {
          connector.network.stabilize();
        }
      },

      fitToScreen: function () {
        if (connector.network) {
          connector.network.fit();
        }
      },

      /**
       * The Java API has three repaint methods. `vis.Network` only has
       * `redraw()`, so all three map onto it.
       */
      reDraw: function () {
        if (connector.network) {
          connector.network.redraw();
        }
      },

      destroyNetwork: function () {
        if (connector.network) {
          connector.network.destroy();
          connector.network = undefined;
        }
      },

      getSeed: function () {
        return connector.network ? connector.network.getSeed() : undefined;
      },
    };

    connector.draw = connector.reDraw;
    connector.drawConnections = connector.reDraw;

    function createNetwork() {
      if (connector.network) {
        connector.network.destroy();
      }
      connector.network = new Network(
        element,
        { nodes: connector.nodes, edges: connector.edges },
        connector.options
      );
      bindEvents(element, connector.network);
    }

    element.$connector = connector;
    connector.init(optionsJson);
  },
};

/**
 * vis-network takes the nodes and edges separately from the options, so they
 * are split out of `data` and `data` itself is dropped.
 */
function parseOptions(optionsJson) {
  if (
    optionsJson === undefined ||
    optionsJson === null ||
    optionsJson === "null"
  ) {
    return { options: {}, nodes: [], edges: [] };
  }
  const options = JSON.parse(optionsJson);
  const data = options.data || {};
  const nodes = data.nodes || [];
  const edges = data.edges || [];
  delete options.data;
  return { options, nodes, edges };
}

/** The only fields of a selection payload the server reads. */
function selection(properties) {
  return {
    nodes: properties.nodes || [],
    edges: properties.edges || [],
  };
}

/** A selection payload plus the cursor position, for the click events. */
function withPointer(properties) {
  const pointer = properties.pointer || {};
  const dom = pointer.DOM || {};
  const canvas = pointer.canvas || {};
  return {
    nodes: properties.nodes || [],
    edges: properties.edges || [],
    pointer: {
      DOM: { x: dom.x || 0, y: dom.y || 0 },
      canvas: { x: canvas.x || 0, y: canvas.y || 0 },
    },
  };
}

/**
 * Wires the vis-network events to the server's `@ClientCallable` methods.
 *
 * No payload is passed through as vis-network produced it: they carry the
 * original Hammer event, whose DOM node references Flow refuses to serialize,
 * so each one is reduced to the fields its event class reads.
 *
 * Two names differ from the source because they were wrong there:
 * `startStabilizing`, not `startStabilization`, and nothing is bound for
 * `viewChanged`, which vis-network no longer emits.
 */
function bindEvents(element, network) {
  const server = () => element.$server;

  network.on("select", (properties) => server().onSelect(selection(properties)));
  network.on("click", (properties) => server().onClick(withPointer(properties)));
  network.on("doubleClick", (properties) =>
    server().onDoubleClick(withPointer(properties))
  );
  network.on("hoverNode", (properties) =>
    server().onHoverNode({ node: properties.node })
  );
  network.on("blurNode", (properties) =>
    server().onBlurNode({ node: properties.node })
  );
  network.on("dragStart", (properties) =>
    server().onDragStart(selection(properties))
  );
  network.on("dragEnd", (properties) =>
    server().onDragEnd(selection(properties))
  );
  network.on("stabilizationProgress", (properties) =>
    server().onStabilizationProgress({
      iterations: properties.iterations,
      total: properties.total,
    })
  );

  network.on("startStabilizing", () => server().onStartStabilization());
  network.on("stabilizationIterationsDone", () =>
    server().onStabilizationIterationsDone()
  );
  network.on("stabilized", (properties) =>
    server().onStabilized({ iterations: properties.iterations || 0 })
  );
  network.on("zoom", (properties) => {
    const pointer = properties.pointer || {};
    server().onZoom({
      direction: properties.direction || "",
      scale: properties.scale || 0,
      pointer: { x: pointer.x || 0, y: pointer.y || 0 },
    });
  });
  network.on("resize", (properties) =>
    server().onResize({
      width: properties.width || 0,
      height: properties.height || 0,
      oldWidth: properties.oldWidth || 0,
      oldHeight: properties.oldHeight || 0,
    })
  );

  // Nothing is bound to the server's `onReinit`, on purpose. `onAttach` covers
  // detach and re-attach, but not an element moved in the DOM or revealed
  // without Flow reporting an attach, which is what the source used this for.
  // The hook is kept for that, untriggered until a real application shows what
  // needs it. Calling `element.$server.onReinit()` rebuilds the network.
}
