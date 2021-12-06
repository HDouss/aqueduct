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
import org.junit.Test;

/**
 * Test for {@link Undirected}.
 * @since 0.1
 */
public final class UndirectedTest {

    /**
     * Gives access to vertices and edges.
     */
    @Test
    public void buildsDirectedGraph() {
        final Undirected graph = UndirectedTest.graph();
        MatcherAssert.assertThat(graph.edges().size(), Matchers.equalTo(2));
        MatcherAssert.assertThat(
            graph.vertices(),
            Matchers.allOf(
                Matchers.hasItem(Matchers.equalTo(UndirectedTest.start())),
                Matchers.hasItem(Matchers.equalTo(UndirectedTest.end()))
            )
        );
    }

    /**
     * Considers the edge tip as a neighbor of the edge tail, and the edge tail
     * as a neighbor of the edge tip.
     */
    @Test
    public void ensuresEdgesAreUndirected() {
        final Undirected graph = UndirectedTest.graph();
        MatcherAssert.assertThat(
            graph.connected(UndirectedTest.start()).size(), Matchers.equalTo(1)
        );
        MatcherAssert.assertThat(
            graph.connected(UndirectedTest.end()).size(), Matchers.equalTo(1)
        );
        MatcherAssert.assertThat(
            graph.connectedEdges(UndirectedTest.start()).size(), Matchers.equalTo(2)
        );
        MatcherAssert.assertThat(
            graph.connectedEdges(UndirectedTest.end()).size(), Matchers.equalTo(2)
        );
    }

    /**
     * Builds an undirected graph with 2 vertices. The graph has 2 edges that connects the vertices.
     * @return A graph containing 2 vertices and 2 edges.
     */
    private static Undirected graph() {
        final Undirected result = new Undirected();
        final Vertex start = UndirectedTest.start();
        final Vertex end = UndirectedTest.end();
        result.addVertices(start, end);
        result.addEdge(new Edge(start, end, 1.));
        result.addEdge(start, end, 1.);
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
}
