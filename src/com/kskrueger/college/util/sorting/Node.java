package com.kskrueger.college.util.sorting;

import com.kskrueger.college.util.Course;

import java.util.HashSet;

public class Node {
        private String nameS;
        transient private Course name;
        final HashSet<Edge> inEdges;
        final HashSet<Edge> outEdges;
        private boolean courseMode = false;

        public Node(Course course) {
            this.name = course;
            this.nameS = course.getCourseNumber();
            inEdges = new HashSet<Edge>();
            outEdges = new HashSet<Edge>();
            courseMode = true;
        }

        public Node(String name) {
            this.nameS = name;
            inEdges = new HashSet<Edge>();
            outEdges = new HashSet<Edge>();
            courseMode = false;
        }

        public Node addEdge(Node node){
            Edge e = new Edge(this, node);
            outEdges.add(e);
            node.inEdges.add(e);
            return this;
        }
        @Override
        public String toString() {
            return nameS;
            /*if (courseMode) {
                return name.getCourseNumber();
            } else {
                return nameS;
            }*/
        }
}
