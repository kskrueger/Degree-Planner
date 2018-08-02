package com.kskrueger.college.util.sorting;

import com.kskrueger.college.util.data.maps.CourseMap;
import com.kskrueger.college.util.data.maps.NodeMap;
import com.kskrueger.college.util.data.maps.ReadMap;

import java.util.*;

public class DirectedAcrylicGraph {

    //public static Node[] allNodes;
    public static ArrayList<Node> allNodes = new ArrayList<>();

    public DirectedAcrylicGraph(/*Node[] nodes*/ ArrayList<Node> nodes) {
        allNodes = nodes;
    }

    public static ArrayList<Node> sort() {
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
        }
        return L;
    }

    public static void main(String[] args) {
        ReadMap courseMapReader = new ReadMap(); System.out.println("line 68");
        CourseMap coursemap = courseMapReader.readCourseMap("courseMap"); System.out.println("line 69");
        //ReadMap nodeMapReader = new ReadMap(); System.out.println("line 70");
        //NodeMap nodeMap = nodeMapReader.readNodeMap("nodeMap"); System.out.println("line 71");
        NodeMap nodeMap = new NodeMap();

        String[] courses = {"Com S 207","Com S 230","Com S 309","Cpr E 185","Math 165"}; System.out.println("line 73");

        for (String course : courses) {
            //coursemap.keySet()
            System.out.println(course+": "+coursemap.get(course).prereqs); System.out.println("line 76");

            Node node = nodeMap.get(course); System.out.println("line 78");

            if (!allNodes.contains(node)) { System.out.println("line 80");
                allNodes.add(node);
                System.out.println(course+": course: line 82: node added to allNodes: "+node);
            }

            for (List<String> list: coursemap.get(course).prereqs) {
                //Node nodeSub = new Node(list.get(0));
                //node.addEdge(nodeSub);
                //allNodes.add(nodeSub);

                Node subNode = nodeMap.get(list.get(0));
                System.out.println("line 91: list.get(0): "+list.get(0));

                if (!allNodes.contains(subNode)) {
                    allNodes.add(subNode);
                    System.out.println("line 95: subNode added to allNodes");
                }

                node.addEdge(subNode);
            }
        }

        System.out.println("Topological Sort: "+Arrays.toString(sort().toArray()));
    }
}
