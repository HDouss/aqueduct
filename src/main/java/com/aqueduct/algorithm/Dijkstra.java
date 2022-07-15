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
package com.aqueduct.algorithm;

import com.aqueduct.graph.Edge;
import com.aqueduct.graph.Graph;
import com.aqueduct.graph.Vertex;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Dijkstra algorithm graph. Assumes all edge weights are positive.
 * Calculates shortest path in a graph from a starting vertex to optionally an ending vertex.
 * Provides total cost as well as traversed vertices for the shortest path.
 *
 * @since 0.1
 */
public final class Dijkstra {

    /**
     * Starting vertex.
     */
    private final Vertex begin;

    /**
     * Map holding preceding edge in the shortest path to arrive to a vertex.
     */
    private final Map<Vertex, Edge> precedent;

    /**
     * Constructor. Builds a Dijkstra algorithm of a graph and stops when
     * the passed ending vertex is reached.
     * @param graph Graph to analyze
     * @param start Starting vertex
     * @param end Ending vertex
     */
    public Dijkstra(final Graph graph, final Vertex start, final Vertex end) {
        this(graph, start, Optional.ofNullable(end));
    }

    /**
     * Constructor. Builds a Dijkstra algorithm of a graph and stops when
     * all reachable vertices are reached.
     * @param graph Graph to analyze
     * @param start Starting vertex
     */
    public Dijkstra(final Graph graph, final Vertex start) {
        this(graph, start, Optional.empty());
    }

    /**
     * Constructor. Builds a Dijkstra algorithm of a graph and stops when
     * the passed ending vertex is reached if given.
     * @param graph Graph to analyze
     * @param start Starting vertex
     * @param end Optionally an ending vertex
     */
    private Dijkstra(final Graph graph, final Vertex start, final Optional<Vertex> end) {
        this.begin = start;
        this.precedent = Dijkstra.calculate(graph, start, end);
    }

    /**
     * Calculates the cost needed for the shortest path, to get from the starting vertex
     * to the passed vertex.
     * @param vtx Vertex to get to
     * @return Total cost of the shortest path
     */
    public double cost(final Vertex vtx) {
        double result = 0;
        if (this.precedent.containsKey(vtx)) {
            Vertex current = vtx;
            while (!current.equals(this.begin)) {
                final Edge edge = this.precedent.get(current);
                current = edge.start();
                result += edge.cost();
            }
        } else {
            if (this.begin.equals(vtx)) {
                result = 0;
            } else {
                result = -1;
            }
        }
        return result;
    }

    /**
     * Calculates the vertices forming the shortest path from the starting vertex
     * to the passed vertex.
     * @param vtx Vertex to get to
     * @return A list of vertices forming the shortest path
     */
    public List<Vertex> path(final Vertex vtx) {
        final List<Vertex> result = new ArrayList<>(this.precedent.size());
        if (this.begin.equals(vtx)) {
            result.add(vtx);
        }
        if (this.precedent.containsKey(vtx)) {
            result.add(vtx);
            Vertex current = vtx;
            while (!current.equals(this.begin)) {
                final Edge edge = this.precedent.get(current);
                result.add(0, edge.start());
                current = edge.start();
            }
        }
        return result;
    }

    /**
     * Calculates the precedence map in which each vertex is associated to the edge leading to it
     * in the shortest path starting from starting vertex.
     * @param graph Graph to analyze
     * @param start Starting vertex
     * @param end Optionally an ending vertex where to stop
     * @return A map associating each vertex to the edge leading to it
     *  in the calculated shortest path.
     */
    private static Map<Vertex, Edge> calculate(final Graph graph, final Vertex start,
        final Optional<Vertex> end) {
        final Map<Vertex, Edge> result = new HashMap<>();
        final Map<Vertex, Double> visited = new HashMap<>();
        visited.put(start, 0d);
        Optional<Edge> edge = Dijkstra.optimal(graph, visited);
        while (edge.isPresent()) {
            final Edge optimal = edge.get();
            final Vertex added = optimal.end();
            visited.put(added, optimal.cost() + visited.get(optimal.start()));
            result.put(added, optimal);
            if (added.equals(end.orElse(null))) {
                break;
            }
            edge = Dijkstra.optimal(graph, visited);
        }
        return result;
    }

    /**
     * Calculates the optimal edge for the current step giving the visited vertices.
     * @param graph Graph to analyze
     * @param visited Visited vertices
     * @return Optimal edge
     */
    private static Optional<Edge> optimal(final Graph graph, final Map<Vertex, Double> visited) {
        Optional<Edge> edge = Optional.empty();
        Double min = 0d;
        for (final Map.Entry<Vertex, Double> source : visited.entrySet()) {
            final Set<Edge> edges = graph.connectedEdges(source.getKey());
            if (edges == null) {
                continue;
            }
            for (final Edge edg : edges) {
                if (!visited.containsKey(edg.end())
                    && (!edge.isPresent() || edg.cost() + source.getValue() < min)) {
                    edge = Optional.of(edg);
                    min = edg.cost() + source.getValue();
                }
            }
        }
        return edge;
    }
}
