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

/**
 * Represents an edge in a graph.
 * @since 0.1
 */
public final class Edge {

    /**
     * Edge starting vertex (edge tail).
     */
    private final Vertex strt;

    /**
     * Edge ending vertex (edge tip).
     */
    private final Vertex ending;

    /**
     * Edge cost or weight.
     */
    private final double weight;

    /**
     * Constructor.
     * @param start Edge starting vertex
     * @param end Edge ending vertex
     * @param cost Edge cost
     */
    public Edge(final Vertex start, final Vertex end, final double cost) {
        super();
        this.strt = start;
        this.ending = end;
        this.weight = cost;
    }

    /**
     * Accessor for the edge starting vertex.
     * @return The starting vertex
     */
    public Vertex start() {
        return this.strt;
    }

    /**
     * Accessor for the edge ending vertex.
     * @return The ending vertex
     */
    public Vertex end() {
        return this.ending;
    }

    /**
     * Accessor for the edge cost.
     * @return The cost
     */
    public double cost() {
        return this.weight;
    }

    @Override
    public String toString() {
        return "Edge [start=".concat(this.strt.toString()).concat(", end=")
            .concat(this.ending.toString()).concat(", cost=").concat(Double.toString(this.weight))
            .concat("]");
    }

}
