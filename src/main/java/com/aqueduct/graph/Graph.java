/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2021-2022, Hamdi Douss
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom
 * the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included
 * in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 * THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES
 * OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE
 * OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.aqueduct.graph;

import java.util.Set;

/**
 * Graph interface.
 * @since 0.1
 */
public interface Graph {

    /**
     * Adds vertices to the graph.
     * @param vtx Vertices array
     */
    void addVertices(Vertex... vtx);

    /**
     * Adds an edge to the graph.
     * @param edge The edge to add
     */
    void addEdge(Edge edge);

    /**
     * Adds an edge to the graph.
     * @param start Edge starting vertex
     * @param end Edge ending vertex
     * @param cost Edge cost
     */
    void addEdge(Vertex start, Vertex end, double cost);

    /**
     * Accessor for the graph vertices.
     * @return A set of the graph vertices
     */
    Set<Vertex> vertices();

    /**
     * Accessor for the graph edges.
     * @return A set of the graph edges
     */
    Set<Edge> edges();

    /**
     * Gets the connected vertices of a given vertex.
     * @param vtx Vertex to get neighbors for
     * @return The set of neighboring vertices
     */
    Set<Vertex> connected(Vertex vtx);

    /**
     * Gets the outgoing edges from a given vertex.
     * @param vtx Vertex to get edges for
     * @return The set of outgoing edges
     */
    Set<Edge> connectedEdges(Vertex vtx);

}
