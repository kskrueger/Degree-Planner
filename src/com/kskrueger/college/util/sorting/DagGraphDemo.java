package com.kskrueger.college.util.sorting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;

public class DagGraphDemo {

    public static void main(String[] args) {
        Node seven = new Node("7");
        Node five = new Node("5");
        Node three = new Node("3");
        Node eleven = new Node("11");
        Node eight = new Node("8");
        Node two = new Node("2");
        Node nine = new Node("9");
        Node ten = new Node("10");
        seven.addEdge(eleven).addEdge(eight);
        five.addEdge(eleven.addEdge(ten));
        three.addEdge(eight).addEdge(ten);
        eleven.addEdge(two).addEdge(nine).addEdge(ten);
        eight.addEdge(nine).addEdge(ten);
        two.addEdge(ten);
        nine.addEdge(ten);
        two.addEdge(nine);

        Node[] allNodes = {seven, five, three, eleven, eight, two, nine, ten};
        //L <- Empty list that will contain the sorted elements
        ArrayList<Node> L = new ArrayList<Node>();

        //S <- Set of all nodes with no incoming edges
        HashSet<Node> S = new HashSet<Node>();
        for(Node n : allNodes){
            if(n.inEdges.size() == 0){
                S.add(n);
            }
        }

        //while S is non-empty do
        while(!S.isEmpty()){
            //remove a node n from S
            Node n = S.iterator().next();
            S.remove(n);

            //insert n into L
            L.add(n);

            //for each node m with an edge e from n to m do
            for(Iterator<Edge> it = n.outEdges.iterator(); it.hasNext();){
                //remove edge e from the graph
                Edge e = it.next();
                Node m = e.to;
                it.remove();//Remove edge from n
                m.inEdges.remove(e);//Remove edge from m

                //if m has no other incoming edges then insert m into S
                if(m.inEdges.isEmpty()){
                    S.add(m);
                }
            }
        }
        //Check to see if all edges are removed
        boolean cycle = false;
        for(Node n : allNodes){
            if(!n.inEdges.isEmpty()){
                cycle = true;
                break;
            }
        }
        if(cycle){
            System.out.println("Cycle present, topological sort not possible");
        }else{
            System.out.println("Topological Sort: "+Arrays.toString(L.toArray()));
        }
    }
}