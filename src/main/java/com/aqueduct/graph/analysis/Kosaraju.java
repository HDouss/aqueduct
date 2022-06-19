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
package com.aqueduct.graph.analysis;

import com.aqueduct.graph.Graph;
import com.aqueduct.graph.Reversed;
import com.aqueduct.graph.Vertex;
import com.aqueduct.graph.traversal.Depth;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Stack;

/**
 * Kosaraju's two-pass algorithm for calculating strongly connected components within a graph.
 * @since 0.1
 */
public final class Kosaraju implements Iterator<Set<Vertex>> {

    /**
     * Underlying buillt iterator.
     */
    private final Iterator<Set<Vertex>> origin;

    /**
     * Constructor. Builds a Kosaraju iterator that traverses through the graph SCC.
     * @param graph Graph to analyze
     */
    public Kosaraju(final Graph graph) {
        this.origin = Kosaraju.scc(graph).iterator();
    }

    @Override
    public boolean hasNext() {
        return this.origin.hasNext();
    }

    @Override
    public Set<Vertex> next() {
        return this.origin.next();
    }

    /**
     * Creates a List of vertices sets. Each element of the list is a set of vertices representing
     * a strongly connected component.
     * @param grp Graph to analyze
     * @return List of connected components
     */
    private static List<Set<Vertex>> scc(final Graph grp) {
        final List<Set<Vertex>> result = new ArrayList<>(grp.vertices().size());
        final Graph rev = new Reversed(grp);
        final List<Vertex> times = Kosaraju.finishing(rev);
        Set<Vertex> component = new HashSet<>();
        while (!times.isEmpty()) {
            final Iterator<Vertex> dfs = new Depth(grp, times.iterator().next());
            while (dfs.hasNext()) {
                final Vertex vtx = dfs.next();
                if (times.contains(vtx)) {
                    component.add(vtx);
                    times.remove(vtx);
                }
            }
            result.add(component);
            component = new HashSet<Vertex>();
        }
        return result;
    }

    /**
     * Operates DFS on the passed graph and orders graph vertices by decreasing finishing times.
     * @param grp Graph to traverse
     * @return Ordered list of vertices by decreasing finishing times.
     */
    private static List<Vertex> finishing(final Graph grp) {
        final Set<Vertex> visited = new HashSet<>();
        final Stack<Vertex> stk = new Stack<>();
        Stack<Vertex> visiting = new Stack<>();
        Set<Vertex> vertices = grp.vertices();
        final List<Vertex> result = new ArrayList<>(vertices.size());
        while (visited.size() != vertices.size()) {
            vertices.removeAll(visited);
            stk.push(vertices.iterator().next());
            while (!stk.isEmpty()) {
                final Vertex current = stk.pop();
                visited.add(current);
                final Set<Vertex> connected = grp.connected(current);
                connected.removeAll(visited);
                if (connected.isEmpty()) {
                    result.add(0, current);
                } else {
                    stk.addAll(connected);
                    visiting.add(current);
                }
            }
            while (!visiting.isEmpty()) {
                result.add(0, visiting.pop());
            }
            visiting = new Stack<>();
            vertices = grp.vertices();
        }
        return result;
    }

}
