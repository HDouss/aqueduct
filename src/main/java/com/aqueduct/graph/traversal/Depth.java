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
package com.aqueduct.graph.traversal;

import com.aqueduct.graph.Graph;
import com.aqueduct.graph.Vertex;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.Stack;

/**
 * Depth First Search graph traversal algorithm.
 * @since 0.1
 */
public final class Depth implements Iterator<Vertex> {

    /**
     * Graph to traverse.
     */
    private final Graph grp;

    /**
     * Internal stack.
     */
    private final Stack<Vertex> stk;

    /**
     * Visited vertices.
     */
    private final Set<Vertex> visited;

    /**
     * Constructor. Builds a BFS iterator.
     * @param graph Graph to traverse
     * @param start Starting vertex
     */
    public Depth(final Graph graph, final Vertex start) {
        this.grp = graph;
        this.stk = Depth.stack(start);
        this.visited = new HashSet<>();
    }

    @Override
    public boolean hasNext() {
        while (!this.stk.isEmpty() && this.visited.contains(this.stk.peek())) {
            this.stk.pop();
        }
        return !this.stk.isEmpty();
    }

    @Override
    public Vertex next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        final Vertex result = this.stk.pop();
        this.visited.add(result);
        final Set<Vertex> connected = this.grp.connected(result);
        if (connected != null) {
            connected.removeAll(this.visited);
            this.stk.addAll(connected);
        }
        return result;
    }

    /**
     * Creates a stack with a single element.
     * @param start Stack element
     * @return The stack
     */
    private static Stack<Vertex> stack(final Vertex start) {
        final Stack<Vertex> result = new Stack<>();
        result.push(start);
        return result;
    }

}
