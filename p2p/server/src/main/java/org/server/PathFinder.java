package org.server;

import org.protocol.MotionPathProtocol;

import javax.vecmath.Vector3f;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class PathFinder {
	// Little internal class because nested hashmaps in java aren't as awesome
	// as in clojure
	class PathProtocolNode {
		public PathProtocolNode(MotionPathProtocol nodeValue) {
			this.nodeValue = nodeValue;
		}

		// MAX_VALUE or any arbitrary high number will do
		public float distance = Float.MAX_VALUE;
		public float length;
		public boolean visited;
		public PathProtocolNode previous;
		public MotionPathProtocol nodeValue;
		public int index;
	}

	// Overload with connectedThreshold being 0, only considers
	// MotionPathProtocols with equal begin/end-points neighbours
	public MotionPathProtocol[] Find(MotionPathProtocol[] routes) {
		return find(routes, 0f);
	}

	// MotionPathProtocol[] routes: all the possibles MotionPathProtocols on the
	// route,
	// Presumes: begin(source) = routes[0]; end = routes[last]
	// returns: List of MotionPathProtocols that lead to the shorters route
	// connectedThreshold: threshold for considering other MotionPathProtocols
	// neighbours
	public MotionPathProtocol[] find(MotionPathProtocol[] routes,
			float connectedThreshold) {
		PathProtocolNode sourceNode = new PathProtocolNode(routes[0]);
		PathProtocolNode endNode = new PathProtocolNode(routes[routes.length]);
		List<PathProtocolNode> openNodes = new ArrayList<PathProtocolNode>();
		Stack<PathProtocolNode> nodesToCheck = new Stack<PathProtocolNode>();
		// Used by getNeighbours
		List<MotionPathProtocol> connectedNodes = new ArrayList<MotionPathProtocol>();

		// Initialize the list of all nodes yet to be checked
		for (MotionPathProtocol r : routes) {
			openNodes.add(new PathProtocolNode(r));
		}

		PathProtocolNode currentNode = sourceNode;

		while (openNodes.size() != 0) {
			for (PathProtocolNode p : getNeighbours(currentNode, openNodes,
					connectedThreshold)) {
				float distance = currentNode.distance + p.nodeValue.getLength();

				if (distance < p.distance) {
					p.distance = distance;
					p.length = p.nodeValue.getLength();
					p.visited = true;
					p.previous = currentNode;
					p.index = p.previous.index + 1;
				}
				nodesToCheck.add(p);
			}

			// All available neighbours have been checked, when searching for
			// neighbours, don't consider this node
			openNodes.remove(currentNode);
			// Set next node
			currentNode = nodesToCheck.pop();
		}

		// We're done collecting distance information, backtrack from
		// destination to begin
		// Because we're backtracking and don't want to end up with a reversed
		// list we decrease i
		MotionPathProtocol[] shortestPathProtocol = new MotionPathProtocol[endNode.index + 1];

		PathProtocolNode nodeIterator = endNode;
		for (int i = endNode.index; i >= 0; i--) {
			shortestPathProtocol[i] = nodeIterator.nodeValue;
			nodeIterator = endNode.previous;
		}

		return shortestPathProtocol;
	}

	private List<PathProtocolNode> getNeighbours(PathProtocolNode node,
			List<PathProtocolNode> routes, float connectedThreshold) {
		Vector3f sourceEnd = node.nodeValue.getWayPoint(node.nodeValue
				.getNbWayPoints());
		List<PathProtocolNode> connectedNodes = new ArrayList<PathProtocolNode>();

		for (int i = 0; i < routes.size(); i++) {
			PathProtocolNode r = routes.get(i);
			Vector3f destBegin = r.nodeValue.getWayPoint(0);
			Vector3f subtractResult = (Vector3f) sourceEnd.clone();
			subtractResult.sub(destBegin);

			// Is endpoint of source equal to begin of destnode? yes = connected
			// if(sourceEnd.subtract(destBegin).distance(Vector3f.ZERO) <
			// connectedThreshold)
			if (subtractResult.length() < connectedThreshold) {
				connectedNodes.add(r);
			}
		}

		return connectedNodes;
	}
}
