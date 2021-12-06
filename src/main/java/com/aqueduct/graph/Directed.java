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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a directed graph.
 * @since 0.1
 */
public final class Directed implements Graph {

    /**
     * Graph vertices.
     */
    private final Set<Vertex> vertex;

    /**
     * Graph edges.
     */
    private final Set<Edge> edge;

    /**
     * A Map holding association between a vertex and its direct neighbors.
     */
    private final Map<Vertex, Set<Vertex>> neighbors;

    /**
     * A Map holding association between a vertex and its outgoing edges.
     */
    private final Map<Vertex, Set<Edge>> outgoing;

    /**
     * Constructor. Builds an empty directed graph.
     */
    public Directed() {
        this.vertex = new HashSet<Vertex>();
        this.edge = new HashSet<Edge>();
        this.neighbors = new HashMap<Vertex, Set<Vertex>>();
        this.outgoing = new HashMap<Vertex, Set<Edge>>();
    }

    @Override
    public void addVertices(final Vertex... vtx) {
        this.vertex.addAll(Arrays.asList(vtx));
    }

    @Override
    public void addEdge(final Edge edg) {
        this.edge.add(edg);
        final Vertex start = edg.start();
        final Vertex end = edg.end();
        this.connect(start, end);
        this.connect(edg, start);
    }

    @Override
    public void addEdge(final Vertex start, final Vertex end, final double cost) {
        this.addVertices(start, end);
        this.addEdge(new Edge(start, end, cost));
    }

    @Override
    public Set<Vertex> vertices() {
        return this.vertex;
    }

    @Override
    public Set<Edge> edges() {
        return this.edge;
    }

    @Override
    public Set<Vertex> connected(final Vertex vtx) {
        return this.neighbors.get(vtx);
    }

    @Override
    public Set<Edge> connectedEdges(final Vertex vtx) {
        return this.outgoing.get(vtx);
    }

    /**
     * Connects an edge as an outgoing edge for a vertex.
     * @param edg The edge
     * @param start The starting vertex
     */
    private void connect(final Edge edg, final Vertex start) {
        Set<Edge> connected = this.outgoing.get(start);
        if (connected == null) {
            connected = new HashSet<Edge>();
            this.outgoing.put(start, connected);
        }
        connected.add(edg);
    }

    /**
     * Connects a vertex as a neighbor for another vertex.
     * @param start Vertex to connect to
     * @param end Vertex to connect
     */
    private void connect(final Vertex start, final Vertex end) {
        Set<Vertex> connected = this.neighbors.get(start);
        if (connected == null) {
            connected = new HashSet<Vertex>();
            this.neighbors.put(start, connected);
        }
        connected.add(end);
    }
}
