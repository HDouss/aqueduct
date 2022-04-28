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
 * Test for {@link DirectedText}.
 * @since 0.1
 */
public final class DirectedTextTest {

    /**
     * Six vertex, zero edges graph filename.
     */
    private static final String ZERO_EDGE_FILE = "six-v-zero-e";

    /**
     * Six vertex, three edges graph filename.
     */
    private static final String THREE_EDGE_FILE = "six-v-three-e";

    /**
     * Builds graph from file.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void buildsFromFile() throws IOException, URISyntaxException {
        final Graph graph = new DirectedText(
            Paths.get(ClassLoader.getSystemResource(DirectedTextTest.THREE_EDGE_FILE).toURI())
        );
        final int vertices = 6;
        final int edges = 3;
        MatcherAssert.assertThat(graph.edges().size(), Matchers.equalTo(edges));
        MatcherAssert.assertThat(graph.vertices().size(), Matchers.equalTo(vertices));
    }

    /**
     * Builds from a file with no edges.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void acceptsNoEdges() throws IOException, URISyntaxException {
        final Graph graph = new DirectedText(
            Paths.get(ClassLoader.getSystemResource(DirectedTextTest.ZERO_EDGE_FILE).toURI())
        );
        final int expected = 6;
        MatcherAssert.assertThat(graph.edges().size(), Matchers.equalTo(0));
        MatcherAssert.assertThat(graph.vertices().size(), Matchers.equalTo(expected));
        for (Integer idx = 1; idx <= expected; ++idx) {
            MatcherAssert.assertThat(
                graph.vertices(),
                Matchers.hasItem(Matchers.equalTo(new Vertex(idx.toString())))
            );
        }
    }

    /**
     * Builds from a file and then accepts adding new edges and vertices.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void addsVerticesAndEdges() throws IOException, URISyntaxException {
        final Graph graph = new DirectedText(
            Paths.get(ClassLoader.getSystemResource(DirectedTextTest.ZERO_EDGE_FILE).toURI())
        );
        final int expected = 6;
        final Vertex vertex = new Vertex("new");
        final Vertex another = new Vertex("another");
        graph.addVertices(vertex);
        graph.addVertices(another);
        graph.addEdge(vertex, another, 1);
        graph.addEdge(new Edge(new Vertex("5"), new Vertex("6"), 1));
        MatcherAssert.assertThat(graph.edges().size(), Matchers.equalTo(2));
        MatcherAssert.assertThat(graph.vertices().size(), Matchers.equalTo(expected + 2));
    }

    /**
     * Considers the edge tip as a neighbor of the edge tail, but does not considers the edge tail
     * as a neighbor of the edge tip.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void ensuresEdgesAreDirected() throws IOException, URISyntaxException {
        final Graph graph = new DirectedText(
            Paths.get(ClassLoader.getSystemResource(DirectedTextTest.THREE_EDGE_FILE).toURI())
        );
        final Vertex one = new Vertex("1");
        final Vertex three = new Vertex("3");
        MatcherAssert.assertThat(
            graph.connected(one).size(), Matchers.equalTo(1)
        );
        MatcherAssert.assertThat(
            graph.connected(three), Matchers.is(IsNull.nullValue())
        );
        MatcherAssert.assertThat(
            graph.connectedEdges(one).size(), Matchers.equalTo(1)
        );
        MatcherAssert.assertThat(
            graph.connectedEdges(three), Matchers.is(IsNull.nullValue())
        );
    }
}
