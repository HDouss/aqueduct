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
 * Represents a graph built as a reverse of another graph.
 *
 * @since 0.1
 */
public final class Reversed implements Graph {

    /**
     * Underlying built graph.
     */
    private final Directed graph;

    /**
     * Constructor. Builds a graph by reversing the given graph.
     * @param origin Original graph to reverse
     */
    public Reversed(final Graph origin) {
        this.graph = Reversed.reverse(origin);
    }

    @Override
    public void addVertices(final Vertex... vtx) {
        this.graph.addVertices(vtx);
    }

    @Override
    public void addEdge(final Edge edg) {
        this.graph.addEdge(edg);
    }

    @Override
    public void addEdge(final Vertex start, final Vertex end, final double cost) {
        this.graph.addEdge(start, end, cost);
    }

    @Override
    public Set<Vertex> vertices() {
        return this.graph.vertices();
    }

    @Override
    public Set<Edge> edges() {
        return this.graph.edges();
    }

    @Override
    public Set<Vertex> connected(final Vertex vtx) {
        return this.graph.connected(vtx);
    }

    @Override
    public Set<Edge> connectedEdges(final Vertex vtx) {
        return this.graph.connectedEdges(vtx);
    }

    /**
     * Builds a directed graph by reversing the given graph.
     * @param origin Original graph to reverse
     * @return A directed graph built by reversing the graph.
     */
    private static Directed reverse(final Graph origin) {
        final Set<Vertex> vtxs = origin.vertices();
        final Directed result = new Directed();
        result.addVertices(vtxs.toArray(new Vertex[0]));
        for (final Vertex vertex : vtxs) {
            final Set<Edge> connected = origin.connectedEdges(vertex);
            if (connected == null) {
                continue;
            }
            for (final Edge edge : connected) {
                Edge adding = edge;
                if (edge.start().equals(vertex)) {
                    adding = new Edge(edge.end(), vertex, edge.cost());
                }
                result.addEdge(adding);
            }
        }
        return result;
    }
}
