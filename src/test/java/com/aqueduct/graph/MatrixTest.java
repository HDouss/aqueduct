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
 * Test for {@link Matrix}.
 * @since 0.1
 */
public final class MatrixTest {

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
        final Matrix graph = MatrixTest.graph();
        MatcherAssert.assertThat(graph.edges().size(), Matchers.equalTo(2));
        MatcherAssert.assertThat(
            graph.vertices(),
            Matchers.allOf(
                Matchers.hasItem(Matchers.equalTo(MatrixTest.start())),
                Matchers.hasItem(Matchers.equalTo(MatrixTest.end())),
                Matchers.hasItem(Matchers.equalTo(MatrixTest.another()))
            )
        );
    }

    /**
     * Considers the edge tip as a neighbor of the edge tail, but does not considers the edge tail
     * as a neighbor of the edge tip.
     */
    @Test
    public void ensuresEdgesAreDirected() {
        final Matrix graph = MatrixTest.graph();
        MatcherAssert.assertThat(graph.connected(MatrixTest.start()).size(), Matchers.equalTo(2));
        MatcherAssert.assertThat(
            graph.connected(MatrixTest.end()).size(), Matchers.equalTo(0)
        );
        MatcherAssert.assertThat(
            graph.connectedEdges(MatrixTest.start()).size(), Matchers.equalTo(2)
        );
        MatcherAssert.assertThat(
            graph.connectedEdges(MatrixTest.end()).size(), Matchers.equalTo(0)
        );
    }

    /**
     * Adds vertices up to the max size.
     */
    @Test
    public void supportsMaxSize() {
        final Matrix graph = MatrixTest.graph();
        graph.addVertices(MatrixTest.start());
        this.thrown.expect(UnsupportedOperationException.class);
        graph.addVertices(new Vertex("three"));
    }

    /**
     * Builds a directed graph with 2 vertices. The graph has 2 edges that both start from the
     * "start" vertex and end in the "end" vertex.
     * @return A graph containing 2 vertices and 2 edges.
     */
    private static Matrix graph() {
        final Matrix result = new Matrix(3);
        final Vertex start = MatrixTest.start();
        final Vertex end = MatrixTest.end();
        final Vertex another = MatrixTest.another();
        result.addVertices(start, end, another);
        result.addEdge(new Edge(start, end, 1.));
        result.addEdge(start, end, 1.);
        result.addEdge(start, another, 2.);
        return result;
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

    /**
     * Builds a third vertex with name "another".
     * @return A vertex
     */
    private static Vertex another() {
        return new Vertex("another");
    }
}
