/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import java.util.ArrayList;
import jdk.nashorn.internal.runtime.arrays.ArrayLikeIterator;
/**
 *
 * @author hiwa_cst
 */
public class Node {
    
    private int distanceFromSource = Integer.MAX_VALUE;
    private int previous_node;        // xom
    private int bandwidth;           // xom
    private boolean visted;
    private ArrayList<Edge> edges = new ArrayList<Edge>();  // create edge
    
    public int getdistanceFromSource()
    {
        return distanceFromSource;
    }

    public void setdistanceFromSource(int distanceFromSource,int previosNode,int bandwidth)
    {
        this.distanceFromSource = distanceFromSource;
        this.previous_node = previosNode;  //xom
        this.bandwidth = bandwidth;
    }  
    
    public int getPreviousNode()     // xom
    {
        return previous_node;
    }

    public int getBandwidth()     // xom
    {
        return bandwidth;
    }
    
    public boolean isVisited()
    {
        return visted;
    }

    public void setVisited(boolean visited)
    {
        this.visted = visited;
    }

    public ArrayList<Edge> getEdges() {
        return edges;
    }

    public void setEdges(ArrayList<Edge> edges) {
        this.edges = edges;
    }
    
    
    
}
