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

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.core.IsNull;
import org.junit.Test;

/**
 * Test for {@link Reversed}.
 * @since 0.1
 */
public final class ReversedTest {

    /**
     * Eight vertex, eleven edges graph filename.
     */
    private static final String ELEVEN_EDGE_FILE = "eight-v-eleven-e";

    /**
     * Builds reversed graph.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void buildsReversedGraph() throws IOException, URISyntaxException {
        final Graph graph = new Reversed(
            new DirectedText(
                Paths.get(ClassLoader.getSystemResource(ReversedTest.ELEVEN_EDGE_FILE).toURI())
            )
        );
        MatcherAssert.assertThat(
            graph.connected(new Vertex("1")), Matchers.is(IsNull.nullValue())
        );
        MatcherAssert.assertThat(graph.connected(new Vertex("3")).size(), Matchers.equalTo(2));
    }

    /**
     * Builds reversed graph from an undirected graph. Reversing an undirected graph has
     * no concrete effect. It gives a directed graph which is equivalent to the original
     * undirected graph.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void buildsFromUndirectedGraph() throws IOException, URISyntaxException {
        final Graph graph = new Reversed(
            new UndirectedText(
                Paths.get(ClassLoader.getSystemResource(ReversedTest.ELEVEN_EDGE_FILE).toURI())
            )
        );
        final int connected = 3;
        MatcherAssert.assertThat(graph.connected(new Vertex("1")).size(), Matchers.equalTo(2));
        MatcherAssert.assertThat(
            graph.connected(new Vertex("3")).size(), Matchers.equalTo(connected)
        );
    }

    /**
     * Gives access to vertices and edges.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void givesEdgesAndVerticesGraph() throws IOException, URISyntaxException {
        final Graph graph = new Reversed(
            new DirectedText(
                Paths.get(ClassLoader.getSystemResource(ReversedTest.ELEVEN_EDGE_FILE).toURI())
            )
        );
        graph.addVertices(new Vertex(""));
        final int vertices = 9;
        final int edges = 11;
        MatcherAssert.assertThat(graph.edges().size(), Matchers.equalTo(edges));
        MatcherAssert.assertThat(graph.vertices().size(), Matchers.equalTo(vertices));
    }

    /**
     * Considers the edge tip as a neighbor of the edge tail, but does not considers the edge tail
     * as a neighbor of the edge tip.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void ensuresEdgesAreDirected() throws IOException, URISyntaxException {
        final Graph graph = new Reversed(
            new DirectedText(
                Paths.get(ClassLoader.getSystemResource(ReversedTest.ELEVEN_EDGE_FILE).toURI())
            )
        );
        final Vertex one = new Vertex("1");
        final Vertex eight = new Vertex("8");
        final Vertex four = new Vertex("4");
        graph.addEdge(eight, one, 1.);
        graph.addEdge(new Edge(one, four, 1.));
        final int connected = 3;
        MatcherAssert.assertThat(
            graph.connected(one).size(), Matchers.equalTo(1)
        );
        MatcherAssert.assertThat(
            graph.connected(eight).size(), Matchers.equalTo(connected)
        );
        MatcherAssert.assertThat(
            graph.connectedEdges(one).size(), Matchers.equalTo(1)
        );
        MatcherAssert.assertThat(
            graph.connectedEdges(four).size(), Matchers.equalTo(1)
        );
        MatcherAssert.assertThat(
            graph.connectedEdges(eight).size(), Matchers.equalTo(connected)
        );
    }

    /**
     * Reversing a directed graph twice gives back the original graph.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void twiceIsIdentity() throws IOException, URISyntaxException {
        final DirectedText origin = new DirectedText(
            Paths.get(ClassLoader.getSystemResource(ReversedTest.ELEVEN_EDGE_FILE).toURI())
        );
        final Graph graph = new Reversed(new Reversed(origin));
        final Vertex one = new Vertex("1");
        final Vertex eight = new Vertex("8");
        final Vertex four = new Vertex("4");
        MatcherAssert.assertThat(
            graph.connected(one).size(), Matchers.equalTo(origin.connected(one).size())
        );
        MatcherAssert.assertThat(
            graph.connected(four).size(), Matchers.equalTo(origin.connected(four).size())
        );
        MatcherAssert.assertThat(
            graph.connected(eight).size(), Matchers.equalTo(origin.connected(eight).size())
        );
    }
}
