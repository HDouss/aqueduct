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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a directed graph based on an adjacency Matrix. This implementation does not support
 * multiple (parallel) edges starting from a vertex A to another vertex B. Adding vertices upon
 * construction is not supported.
 * @since 0.1
 */
public final class Matrix implements Graph {

    /**
     * Graph vertices to indices.
     */
    private final Map<Vertex, Integer> names;

    /**
     * Graph indices to vertices.
     */
    private final Map<Integer, Vertex> indices;

    /**
     * Adjacency matrix.
     */
    private final Double[][] edgs;

    /**
     * Constructor. Builds an empty directed graph with the given number of vertices.
     * @param size Vertices count
     */
    public Matrix(final Integer size) {
        this.edgs = Matrix.matrix(size);
        this.names = new HashMap<>(size);
        this.indices = new HashMap<>(size);
    }

    @Override
    public void addVertices(final Vertex... vtx) {
        for (final Vertex vertex : vtx) {
            if (!this.names.containsKey(vertex)) {
                final int current = this.names.size();
                if (current < this.edgs.length) {
                    this.names.put(vertex, current);
                    this.indices.put(current, vertex);
                } else {
                    throw new UnsupportedOperationException(
                        String.format("Maximum of %d vertices can be added", this.edgs.length)
                    );
                }
            }
        }
    }

    @Override
    public void addEdge(final Edge edg) {
        this.addEdge(edg.start(), edg.end(), edg.cost());
    }

    @Override
    public void addEdge(final Vertex start, final Vertex end, final double cost) {
        this.addVertices(start, end);
        this.edgs[this.names.get(start)][this.names.get(end)] = cost;
    }

    @Override
    public Set<Vertex> vertices() {
        return this.names.keySet();
    }

    @Override
    public Set<Edge> edges() {
        final Set<Edge> result = new HashSet<>();
        for (int idx = 0; idx < this.edgs.length; ++idx) {
            for (int jdx = 0; jdx < this.edgs.length; ++jdx) {
                final Double cost = this.edgs[idx][jdx];
                if (cost != null) {
                    result.add(new Edge(this.indices.get(idx), this.indices.get(jdx), cost));
                }
            }
        }
        return result;
    }

    @Override
    public Set<Vertex> connected(final Vertex vtx) {
        final Set<Vertex> result = new HashSet<>();
        final int idx = this.names.get(vtx);
        for (int jdx = 0; jdx < this.edgs.length; ++jdx) {
            final Double cost = this.edgs[idx][jdx];
            if (cost != null) {
                result.add(this.indices.get(jdx));
            }
        }
        return result;
    }

    @Override
    public Set<Edge> connectedEdges(final Vertex vtx) {
        final Set<Edge> result = new HashSet<>();
        final int idx = this.names.get(vtx);
        for (int jdx = 0; jdx < this.edgs.length; ++jdx) {
            final Double cost = this.edgs[idx][jdx];
            if (cost != null) {
                result.add(new Edge(vtx, this.indices.get(jdx), cost));
            }
        }
        return result;
    }

    /**
     * Builds a square matrix of the given size.
     * @param size Size of the matrix
     * @return The matrix
     */
    private static Double[][] matrix(final Integer size) {
        final Double[][] result = new Double[size][];
        for (int idx = 0; idx < size; ++idx) {
            result[idx] = new Double[size];
        }
        return result;
    }
}
