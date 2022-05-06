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

import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test for {@link Contraction}.
 * @since 0.1
 */
public final class ContractionTest {

    /**
     * Junit rule for expected exceptions.
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Gives access to vertices and edges.
     */
    @Test
    public void buildsDirectedGraph() {
        final Graph graph = ContractionTest.graph();
        graph.addVertices(new Vertex(""));
        final int vertices = 5;
        final int edges = 8;
        MatcherAssert.assertThat(graph.edges().size(), Matchers.equalTo(edges));
        MatcherAssert.assertThat(graph.vertices().size(), Matchers.equalTo(vertices));
    }

    /**
     * Considers the edge tip as a neighbor of the edge tail, but does not considers the edge tail
     * as a neighbor of the edge tip.
     */
    @Test
    public void ensuresEdgesAreDirected() {
        final Graph graph = ContractionTest.graph();
        final int edges = 4;
        graph.addEdge(ContractionTest.start(), ContractionTest.end(), 1.);
        graph.addEdge(new Edge(ContractionTest.start(), ContractionTest.end(), 1.));
        MatcherAssert.assertThat(
            graph.connected(ContractionTest.start()).size(), Matchers.equalTo(1)
        );
        MatcherAssert.assertThat(
            graph.connected(ContractionTest.end()).size(), Matchers.equalTo(1)
        );
        MatcherAssert.assertThat(
            graph.connectedEdges(ContractionTest.start()).size(), Matchers.equalTo(edges)
        );
        MatcherAssert.assertThat(
            graph.connectedEdges(ContractionTest.end()).size(), Matchers.equalTo(2)
        );
    }

    /**
     * Throws an exception if contracted vertex does not belong to the graph.
     */
    @Test
    public void errorsIfVertexNotInGraph() {
        final Undirected result = new Undirected();
        result.addVertices(ContractionTest.start());
        this.thrown.expect(IllegalArgumentException.class);
        new Contraction(result, ContractionTest.start(), ContractionTest.end());
    }

    /**
     * Builds a graph after contracting 2 vertices from an undirected graph having 5 vertices
     * and 5 (undirected) edges.
     * The resulting graph after contraction has 4 vertices and 8 (directed) edges.
     * @return A graph containing 4 vertices and 8 edges.
     */
    private static Graph graph() {
        final Undirected result = new Undirected();
        final Vertex start = ContractionTest.start();
        final Vertex end = ContractionTest.end();
        final Vertex first = new Vertex("first");
        final Vertex second = new Vertex("second");
        final Vertex third = new Vertex("third");
        result.addVertices(start, end, first, second, third);
        result.addEdge(new Edge(start, end, 1.));
        result.addEdge(start, end, 1.);
        result.addEdge(first, second, 1.);
        result.addEdge(second, third, 1.);
        result.addEdge(first, third, 1.);
        return new Contraction(result, first, second);
    }

    /**
     * Builds ending vertex with name "end".
     * @return A vertex
     */
    private static Vertex end() {
        return new Vertex("end");
    }

    /**
     * Builds starting vertex with name "start".
     * @return A vertex
     */
    private static Vertex start() {
        return new Vertex("start");
    }
}
