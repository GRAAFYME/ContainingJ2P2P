package org.server;

import org.protocol.MotionPathProtocol;

import javax.vecmath.Vector2f;
import javax.vecmath.Vector3f;
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
    class PathProtocolNode
    {
        public PathProtocolNode(MotionPathProtocol nodeValue) { this.nodeValue = nodeValue; }
        public PathProtocolNode(Vector3f location)
        {
            List<Vector3f> locationList = new ArrayList<>();
            locationList.add(location);

            this.nodeValue = new MotionPathProtocol(locationList, Integer.MAX_VALUE, "");
        }

        //MAX_VALUE or any arbitrary high number will do
        public float distance = Float.MAX_VALUE;
        public float length;
        public boolean visited;
        public PathProtocolNode previous;
        public MotionPathProtocol nodeValue;
        public int index;
    }


	//MotionPathProtocol[] routes: all the possibles MotionPathProtocols on the route,
	//Presumes: begin(source) = routes[0]; end = routes[last]
	//returns: List of MotionPathProtocols that lead to the shorters route
    //connectedThreshold:  threshold for considering other MotionPathProtocols neighbours
	public MotionPathProtocol[] find(Vector3f begin, Vector3f end, MotionPathProtocol[] routes, float connectedThreshold)
	{
        PathProtocolNode sourceNode = new PathProtocolNode(begin);
        sourceNode.distance = 0;
        PathProtocolNode endNode = new PathProtocolNode(end);

        List<PathProtocolNode> openNodes = new ArrayList<PathProtocolNode>();
        Stack<PathProtocolNode> nodesToCheck = new Stack<PathProtocolNode>();
        //Used by getNeighbours
        List<MotionPathProtocol> connectedNodes = new ArrayList<MotionPathProtocol>();

        //Initialize the list of all nodes yet to be checked
        for(MotionPathProtocol r : routes)
        {
            openNodes.add(new PathProtocolNode(r));
        }

        PathProtocolNode currentNode = sourceNode;

        while(openNodes.size() != 0)
        {
            for(PathProtocolNode p : getNeighbours(currentNode, openNodes, connectedThreshold))
            {
                float distance = currentNode.distance + p.nodeValue.getLength();

                if(distance < p.distance)
                {
                    p.distance = distance;
                    p.length = p.nodeValue.getLength();
                    p.visited = true;
                    p.previous = currentNode;
                    p.index = p.previous.index + 1;

                    if(isConnected(endNode, p, 40f) != null && distance < endNode.distance)
                    {
                        endNode = p;
                    }

                    nodesToCheck.add(p);
                }
            }
            if(nodesToCheck.size() == 0)
            {
                break;
            }
            //All available neighbours have been checked, when searching for neighbours, don't consider this node
            openNodes.remove(currentNode);
            //Set next node
            if(nodesToCheck.size() != 0)
            {
                currentNode = nodesToCheck.pop();
            }
            //TODO: hack???
        }

        //We're done collecting distance information, backtrack from destination to begin
        //Because we're backtracking and don't want to end up with a reversed list we decrease i
        MotionPathProtocol[] shortestPathProtocol = new MotionPathProtocol[endNode.index + 1];

        PathProtocolNode nodeIterator = endNode;
        //i > 0 instead of i >= 0 because we want to ignore the source "debug" node we inserted
        for(int i = endNode.index; i > 0; i--)
        {
            shortestPathProtocol[i] = nodeIterator.nodeValue;
            nodeIterator = nodeIterator.previous;

        }


        return shortestPathProtocol;
    }

private List<PathProtocolNode> getNeighbours(PathProtocolNode node, List<PathProtocolNode> routes, float connectedThreshold)
    {
        Vector3f sourceEnd = node.nodeValue.getWayPoint(node.nodeValue.getNbWayPoints() - 1);
        List<PathProtocolNode> connectedNodes =  new ArrayList<PathProtocolNode>();

        for(int i = 0; i < routes.size(); i++)
        {
            Vector2f crossing = isConnected(node, routes.get(i), 40f);

            //Is endpoint of source equal to begin of destnode? yes = connected
            //if(sourceEnd.subtract(destBegin).distance(Vector3f.ZERO) < connectedThreshold)
            if(crossing != null)
            {
                routes.get(i).nodeValue.driveUntilWaypoint = (int)crossing.y;
                connectedNodes.add(routes.get(i));
            }
        }

        return connectedNodes;
    }

    private Vector2f isConnected(PathProtocolNode node1, PathProtocolNode node2, float connectedThreshold)
    {
        Vector3f temp = new Vector3f();

        float x = 0f, y = 0f;
        for(Vector3f v1 : node1.nodeValue.locationNodeList)
        {
            y = 0;
            for(Vector3f v2 : node2.nodeValue.locationNodeList)
            {
                temp = new Vector3f(v1.x, v1.y, v1.z);
                temp.sub(v2);

                if(temp.length() < connectedThreshold)
                {
                    return new Vector2f(x, y);
                }
                y++;
            }
            x++;
        }

        return null;
    }

    private float length(Vector3f v1, Vector3f v2)
    {
        Vector3f temp = new Vector3f(v1.x, v1.y, v1.z);
        temp.sub(v2);
        return temp.length();
    }
}

