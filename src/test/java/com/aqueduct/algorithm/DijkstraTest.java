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

import com.aqueduct.graph.DirectedText;
import com.aqueduct.graph.Graph;
import com.aqueduct.graph.Vertex;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;

/**
 * Test for {@link Dijkstra}.
 * @since 0.1
 */
@RunWith(JUnitParamsRunner.class)
public final class DijkstraTest {

    /**
     * Simple graph (4 vertices, 5 edges) filename.
     */
    private static final String SIMPLE = "dijkstra-simple";

    /**
     * Complex graph (200 verices, >3K edges) filename.
     */
    private static final String COMPLEX = "dijkstra-complex";

    /**
     * Junit rule for expected exceptions.
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Calculates shortest paths for a simple graph where
     * all vertices are reachable from starting vertex.
     * @param dest Destination vertex
     * @param cost Expected cost
     * @param path Expected path size
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    @Parameters({
        "1, 0, 1",
        "2, 1, 2",
        "3, 3, 3",
        "4, 6, 4"
    })
    public void calculatesAllReachable(final String dest, final double cost, final int path)
        throws IOException, URISyntaxException {
        final Graph graph = new DirectedText(
            Paths.get(ClassLoader.getSystemResource(DijkstraTest.SIMPLE).toURI())
        );
        final Dijkstra djk = new Dijkstra(graph, new Vertex("1"));
        final Vertex destination = new Vertex(dest);
        MatcherAssert.assertThat(djk.cost(destination), Matchers.is(cost));
        MatcherAssert.assertThat(djk.path(destination).size(), Matchers.is(path));
    }

    /**
     * Calculates shortest paths for a complex graph.
     * @param dest Destination vertex
     * @param cost Expected cost
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    @Parameters({
        "1, 0",
        "7, 2599",
        "37, 2610",
        "59, 2947",
        "82, 2052",
        "99, 2367",
        "115, 2399",
        "133, 2029",
        "165, 2442",
        "188, 2505",
        "197, 3068"
    })
    public void calculatesComplex(final String dest, final double cost)
        throws IOException, URISyntaxException {
        final Graph graph = new DirectedText(
            Paths.get(ClassLoader.getSystemResource(DijkstraTest.COMPLEX).toURI())
        );
        final Dijkstra djk = new Dijkstra(graph, new Vertex("1"));
        MatcherAssert.assertThat(djk.cost(new Vertex(dest)), Matchers.is(cost));
    }
}
