/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import Utilities.Variables;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JOptionPane;

/**
 *
 * @author hiwa_cst
 */
public class Graph {

    private Node[] nodes;
    private int noOfNodes;
    private Edge[] edges;
    private int noOfEdges;
    ArrayList delayWithShortestPath = new ArrayList();

    public Graph(Edge[] edges) {
        this.edges = edges;
        this.noOfNodes = calculatenoOfNode(edges);
        this.nodes = new Node[this.noOfNodes];

        for (int i = 0; i < this.noOfNodes; i++) {
            this.nodes[i] = new Node();
        }

        this.noOfEdges = edges.length;

        for (int edgeToAdd = 0; edgeToAdd < this.noOfEdges; edgeToAdd++) {
            this.nodes[edges[edgeToAdd].getfromNodeIndex()].getEdges().add(edges[edgeToAdd]);
            this.nodes[edges[edgeToAdd].gettoNodeIndex()].getEdges().add(edges[edgeToAdd]);
        }

    }

    private int calculatenoOfNode(Edge[] edges) {
        int nOfNodes = 0;

        for (Edge e : edges) {
            if (e.gettoNodeIndex() > nOfNodes) {
                nOfNodes = e.gettoNodeIndex();
            }

            if (e.getfromNodeIndex() > nOfNodes) {
                nOfNodes = e.getfromNodeIndex();
            }
        }

        nOfNodes++;
        return nOfNodes;
    }

    // implementing Dijkstra
    public void calculateShortestDisance(int sourceNode) {
        // node 0 as source

        ArrayList<ArrayList> list = new ArrayList<ArrayList>();
        this.nodes[sourceNode].setdistanceFromSource(sourceNode, sourceNode, 0);
        int nextNode = sourceNode;

        // visit every node
        for (int i = 0; i < this.nodes.length; i++) {

// loop  around the edge of current node 
            ArrayList<Edge> currentNodeEdge = this.nodes[nextNode].getEdges();
            for (int joinedEdge = 0; joinedEdge < currentNodeEdge.size(); joinedEdge++) {
                ArrayList l2 = new ArrayList();

                int neighoubrIndex = currentNodeEdge.get(joinedEdge).getNeighbourIndex(nextNode);

                //only if note visited
                if (!this.nodes[neighoubrIndex].isVisited()) {
                    int tentative = this.nodes[nextNode].getdistanceFromSource() + currentNodeEdge.get(joinedEdge).getlength(); // + nextNode;
                    if (tentative < nodes[neighoubrIndex].getdistanceFromSource()) {
                        nodes[neighoubrIndex].setdistanceFromSource(tentative, nextNode, currentNodeEdge.get(joinedEdge).getlength());

                    }
                }
            }
            // all neighbour checked so node visited 
            nodes[nextNode].setVisited(true);

            // next node must be with the shortest distance
            nextNode = getNodeShortestDistanced();
        }
    }

    private int getNodeShortestDistanced() {
        int storedNodeIndex = 0;
        int storedDist = Integer.MAX_VALUE;
        for (int i = 0; i < this.nodes.length; i++) {
            int currentDist = this.nodes[i].getdistanceFromSource();
            if (!this.nodes[i].isVisited() && currentDist < storedDist) {
                storedDist = currentDist;
                storedNodeIndex = i;
            }
        }
        return storedNodeIndex;
    }

    public ArrayList printResult(int source, int distNode) {
        String output = "No of nodes = " + this.noOfNodes;
        output += "\nNo of edges = " + this.noOfEdges;
        int result = 0;
        //   JOptionPane.showMessageDialog(null, source);
        //  System.out.println(source);
        for (int i = 0; i < this.nodes.length; i++) {
//            ArrayList l = new ArrayList();
//            int r = Integer.parseInt(nodes[i].getdistanceFromSource()+"");
//            l.add(r);
//            l.add(source);
//            int x = r - source;
//            l.add("\t"+x);
//            Variables.temp.add(l);
            result = Integer.parseInt(nodes[i].getdistanceFromSource() + "") - source;
            //        double result = Double.parseDouble(nodes[i].getdistanceFromSource() + "") - (double)source;

            if (i == distNode) {
                delayWithShortestPath.add(source);    // @1 broker 
                delayWithShortestPath.add(distNode);  // @2 fog nodes ex: F0 
              //  if (result <= 0) {
              //      JOptionPane.showMessageDialog(null, "Result is negative");
              //  }
                delayWithShortestPath.add(result);   // @3  delay or energy    lerada delaya balam dwatr dakret am indexa u[date bkret ba energy 
                findPath(i, distNode);
            }
          //  JOptionPane.showMessageDialog(null, "source: " + source + "\ndist : " + distNode + "\nresult: " + result);

        }
        /*
          this list conatains the following for palcement the service ex:S0
          from broker(sourceNode):F0  to distNode
        insert:
        @1 source node
        @2 dist node
        @3 delay from source to dist
        @4 adding shortest path between broker to aother fog nodes in findPath() method
         */

        return delayWithShortestPath;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public int getNoOfNodes() {
        return noOfNodes;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public int getNoOfEdges() {
        return noOfEdges;
    }

    public void findPath(int x, int distNode) {
        for (int i = 0; i < nodes.length; i++) {
            int y = nodes[x].getPreviousNode();  //
            int z = nodes[x].getBandwidth();  // bandwidthy newan am node w away peshw
            if (x != y) //   System.out.println(y + "\t bandwidth between " + x + " and " + y + " : " + z);
            {
                delayWithShortestPath.add(y);   //  @4 adding shortest path between source to dist
            }
            x = y;
            if (y == 0) {
                break;
            }
        }

    }

}
