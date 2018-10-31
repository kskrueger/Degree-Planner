package com.kskrueger.college.util.sorting;

import com.kskrueger.college.util.Course;
import com.kskrueger.college.util.data.maps.CourseMap;
import com.kskrueger.college.util.data.maps.NodeMap;
import com.kskrueger.college.util.data.maps.ReadMap;

import java.util.*;

public class DirectedAcyclicGraph {
    public static ArrayList<Node> allNodes = new ArrayList<>();

    public DirectedAcyclicGraph(ArrayList<Node> nodes) {
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
        //ReadMap courseMapReader = new ReadMap();
        //CourseMap coursemap = courseMapReader.readCourseMap("courseMap");
        ReadMap nodeMapReader = new ReadMap();
        NodeMap nodeMap = nodeMapReader.readNodeMap("fullCourseMap");
        //NodeMap nodeMap = new NodeMap();

        String[] courses = {"Math 265","Com S 227","Com S 230","Com S 228","Math 207","Com S 309","Com S 311"};

        for (String course : courses) {
            Node node = nodeMap.get(course.toUpperCase());

            if (!allNodes.contains(node)) {
                allNodes.add(node);
            }

            for (List<String> list: nodeMap.get(course.toUpperCase()).course.prereqs) {
                String courseHere = list.get(0);
                Node subNode = nodeMap.get(courseHere.toUpperCase());

                if (!allNodes.contains(subNode)) {
                    allNodes.add(subNode);
                }

                node.addEdge(subNode);
            }
        }

        System.out.println("Topological Sort: "+Arrays.toString(sort().toArray()));
    }
}
