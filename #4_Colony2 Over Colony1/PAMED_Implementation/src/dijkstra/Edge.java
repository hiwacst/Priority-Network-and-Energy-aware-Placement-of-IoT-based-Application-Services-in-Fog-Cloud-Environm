/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

/**
 *
 * @author hiwa_cst
 */
public class Edge {

    private int fromNodeIndex;
    private int toNodeIndex;
    private int length;

    public Edge(int fromNodeIndex, int toNodeIndex, int length) {

        this.fromNodeIndex = fromNodeIndex;
        this.toNodeIndex = toNodeIndex;
        this.length = length;
    }

    public int getfromNodeIndex() {
        return fromNodeIndex;
    }

    public int gettoNodeIndex() {
        return toNodeIndex;
    }

    public int getlength() {
        return length;
    }

    public int getNeighbourIndex(int nodeIndex) {
        if (this.fromNodeIndex == nodeIndex) {
            return this.toNodeIndex;
        } else {
            return this.fromNodeIndex;
        }
    }
}
