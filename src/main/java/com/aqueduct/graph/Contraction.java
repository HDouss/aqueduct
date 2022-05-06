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

import java.security.InvalidParameterException;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a directed graph built as a contraction of two vertices of another graph.
 * A contraction of two vertices in a graph is a replacement of this two vertices with a single
 * vertex. Every edge linking these two vertices is removed.
 *
 * @since 0.1
 */
public final class Contraction implements Graph {

    /**
     * Name pattern of the merged vertex.
     */
    private static final String MERGED_PATTERN = "%s -- %s";

    /**
     * Underlying built graph.
     */
    private final Directed graph;

    /**
     * Constructor. Builds a graph by contracting the passed two vertices of the given graph.
     * @param origin Original graph to contract
     * @param first First of the two vertices to contract
     * @param second Second of the two vertices to contract
     */
    public Contraction(final Graph origin, final Vertex first, final Vertex second) {
        this.graph = Contraction.contract(origin, first, second);
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
     * Builds a directed graph by contracting the passed two vertices of the given graph.
     * @param origin Original graph to contract
     * @param first First of the two vertices to contract
     * @param second Second of the two vertices to contract
     * @return A directed graph built by contracting the two vertices.
     */
    private static Directed contract(final Graph origin, final Vertex first, final Vertex second) {
        final Set<Vertex> vtxs = new HashSet<>(origin.vertices());
        if (!vtxs.remove(first) || !vtxs.remove(second)) {
            throw new InvalidParameterException(
                String.format(
                    "Contracted vertices %s and %s must be part of the graph %s",
                    first, second, origin
                )
            );
        }
        final Directed result = new Directed();
        result.addVertices(vtxs.toArray(new Vertex[0]));
        result.addVertices(
            new Vertex(String.format(Contraction.MERGED_PATTERN, first, second))
        );
        for (final Vertex vertex : origin.vertices()) {
            final Set<Edge> connected = origin.connectedEdges(vertex);
            if (connected == null) {
                continue;
            }
            for (final Edge edge : connected) {
                if (Contraction.self(first, second, edge)) {
                    continue;
                }
                Edge adding = edge;
                if (!edge.start().equals(vertex)) {
                    adding = new Edge(vertex, edge.start(), edge.cost());
                }
                adding = Contraction.edge(first, second, adding);
                result.addEdge(adding);
            }
        }
        return result;
    }

    /**
     * Calculates the edge to add by replacing contracted vertices if they are part of the edge.
     * @param first First of the two vertices to contract
     * @param second Second of the two vertices to contract
     * @param edge The edge to analyze
     * @return The edge to be added
     */
    private static Edge edge(final Vertex first, final Vertex second, final Edge edge) {
        Edge adding = edge;
        final Vertex merged = new Vertex(String.format(Contraction.MERGED_PATTERN, first, second));
        if (adding.end().equals(first) || adding.end().equals(second)) {
            adding = new Edge(adding.start(), merged, adding.cost());
        }
        if (adding.start().equals(first) || adding.start().equals(second)) {
            adding = new Edge(merged, adding.end(), adding.cost());
        }
        return adding;
    }

    /**
     * Checks if the edge is between the contracted vertices, so it should be removed.
     * @param first First contracted vertex
     * @param second Second contracted vertex
     * @param edge The edge to check
     * @return True if the edge is between the two contracted vertices
     */
    private static boolean self(final Vertex first, final Vertex second, final Edge edge) {
        return edge.start().equals(first) && edge.end().equals(second)
            || edge.start().equals(second) && edge.end().equals(first);
    }

}
