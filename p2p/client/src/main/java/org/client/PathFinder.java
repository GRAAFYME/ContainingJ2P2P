package org.client;

import com.jme3.cinematic.MotionPath;
import com.jme3.math.Vector3f;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by Remco on 12/12/13.
 */
class PathFinder
{
    //Little internal class because nested hashmaps in java aren't as awesome
    //as in clojure
    class PathNode
    {
        public PathNode(MotionPath nodeValue) { this.nodeValue = nodeValue; }
        //MAX_VALUE or any arbitrary high number will do
        public float distance = Float.MAX_VALUE;
        public float length;
        public boolean visited;
        public PathNode previous;
        public MotionPath nodeValue;
        public int index;
    }

    //Overload with connectedThreshold being 0, only considers MotionPaths with equal begin/end-points neighbours
    public MotionPath[] Find(MotionPath[] routes)
    {
        return find(routes, 0f);
    }

	//MotionPath[] routes: all the possibles MotionPaths on the route,
	//Presumes: begin(source) = routes[0]; end = routes[last]
	//returns: List of MotionPaths that lead to the shorters route
    //connectedThreshold:  threshold for considering other MotionPaths neighbours
	public MotionPath[] find(MotionPath[] routes, float connectedThreshold)
	{
        PathNode sourceNode = new PathNode(routes[0]);
        PathNode endNode = new PathNode(routes[routes.length]);
        List<PathNode> openNodes = new ArrayList<PathNode>();
        Stack<PathNode> nodesToCheck = new Stack<PathNode>();
        //Used by getNeighbours
        List<MotionPath> connectedNodes = new ArrayList<MotionPath>();

        //Initialize the list of all nodes yet to be checked
        for(MotionPath r : routes)
        {
            openNodes.add(new PathNode(r));
        }

        PathNode currentNode = sourceNode;

        while(openNodes.size() != 0)
        {
            for(PathNode p : getNeighbours(currentNode, openNodes, connectedThreshold))
            {
                float distance = currentNode.distance + p.nodeValue.getLength();

                if(distance < p.distance)
                {
                    p.distance = distance;
                    p.length = p.nodeValue.getLength();
                    p.visited = true;
                    p.previous = currentNode;
                    p.index = p.previous.index + 1;
                }
                nodesToCheck.add(p);
            }

            //All available neighbours have been checked, when searching for neighbours, don't consider this node
            openNodes.remove(currentNode);
            //Set next node
            currentNode = nodesToCheck.pop();
        }

        //We're done collecting distance information, backtrack from destination to begin
        //Because we're backtracking and don't want to end up with a reversed list we decrease i
        MotionPath[] shortestPath = new MotionPath[endNode.index + 1];

        PathNode nodeIterator = endNode;
        for(int i = endNode.index; i >= 0; i--)
        {
            shortestPath[i] = nodeIterator.nodeValue;
            nodeIterator = endNode.previous;
        }


        return shortestPath;
    }


    private List<PathNode> getNeighbours(PathNode node, List<PathNode> routes, float connectedThreshold)
    {
        Vector3f sourceEnd = node.nodeValue.getWayPoint(node.nodeValue.getNbWayPoints());
        List<PathNode> connectedNodes =  new ArrayList<PathNode>();

        for(int i = 0; i < routes.size(); i++)
        {
            PathNode r = routes.get(i);
            Vector3f destBegin = r.nodeValue.getWayPoint(0);

            //Is endpoint of source equal to begin of destnode? yes = connected
            if(sourceEnd.subtract(destBegin).distance(Vector3f.ZERO) < connectedThreshold)
            {
                connectedNodes.add(r);
            }
        }

        return connectedNodes;
    }
}

