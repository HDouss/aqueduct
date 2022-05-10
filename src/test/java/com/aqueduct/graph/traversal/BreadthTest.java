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

import com.aqueduct.graph.DirectedText;
import com.aqueduct.graph.Graph;
import com.aqueduct.graph.UndirectedText;
import com.aqueduct.graph.Vertex;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.NoSuchElementException;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Test for {@link Breadth}.
 * @since 0.1
 */
public final class BreadthTest {

    /**
     * Six vertex, zero edges graph filename.
     */
    private static final String ZERO_EDGE_FILE = "six-v-zero-e";

    /**
     * Six vertex, three edges graph filename.
     */
    private static final String THREE_EDGE_FILE = "six-v-three-e";

    /**
     * Eight vertex, eleven edges graph filename.
     */
    private static final String ELEVEN_EDGE_FILE = "eight-v-eleven-e";

    /**
     * Junit rule for expected exceptions.
     */
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    /**
     * Traverses simple directed graph.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void traversesSimpleDirected() throws IOException, URISyntaxException {
        final Graph graph = new DirectedText(
            Paths.get(ClassLoader.getSystemResource(BreadthTest.THREE_EDGE_FILE).toURI())
        );
        final Vertex start = new Vertex("1");
        final Breadth bfs = new Breadth(graph, start);
        MatcherAssert.assertThat(bfs.next(), Matchers.is(start));
        MatcherAssert.assertThat(bfs.hasNext(), Matchers.is(true));
        MatcherAssert.assertThat(bfs.next(), Matchers.is(new Vertex("3")));
        MatcherAssert.assertThat(bfs.hasNext(), Matchers.is(false));
    }

    /**
     * Traverses directed graph.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void traversesDirected() throws IOException, URISyntaxException {
        final Graph graph = new DirectedText(
            Paths.get(ClassLoader.getSystemResource(BreadthTest.ELEVEN_EDGE_FILE).toURI())
        );
        final Vertex start = new Vertex("1");
        final Breadth bfs = new Breadth(graph, start);
        MatcherAssert.assertThat(bfs.next(), Matchers.is(start));
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(Matchers.is(new Vertex("3")), Matchers.is(new Vertex("2")))
        );
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(Matchers.is(new Vertex("3")), Matchers.is(new Vertex("2")))
        );
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(Matchers.is(new Vertex("5")), Matchers.is(new Vertex("4")))
        );
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(Matchers.is(new Vertex("5")), Matchers.is(new Vertex("4")))
        );
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(
                Matchers.is(new Vertex("6")),
                Matchers.is(new Vertex("7")),
                Matchers.is(new Vertex("8"))
            )
        );
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(
                Matchers.is(new Vertex("6")),
                Matchers.is(new Vertex("7")),
                Matchers.is(new Vertex("8"))
            )
        );
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(
                Matchers.is(new Vertex("6")),
                Matchers.is(new Vertex("7")),
                Matchers.is(new Vertex("8"))
            )
        );
        MatcherAssert.assertThat(bfs.hasNext(), Matchers.is(false));
    }

    /**
     * Traverses simple undirected graph.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void traversesSimpleUndirected() throws IOException, URISyntaxException {
        final Graph graph = new UndirectedText(
            Paths.get(ClassLoader.getSystemResource(BreadthTest.THREE_EDGE_FILE).toURI())
        );
        final Vertex start = new Vertex("1");
        final Breadth bfs = new Breadth(graph, start);
        MatcherAssert.assertThat(bfs.next(), Matchers.is(start));
        MatcherAssert.assertThat(bfs.hasNext(), Matchers.is(true));
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(Matchers.is(new Vertex("3")), Matchers.is(new Vertex("2")))
        );
        MatcherAssert.assertThat(bfs.hasNext(), Matchers.is(true));
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(Matchers.is(new Vertex("3")), Matchers.is(new Vertex("2")))
        );
    }

    /**
     * Traverses undirected graph.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void traversesUndirected() throws IOException, URISyntaxException {
        final Graph graph = new UndirectedText(
            Paths.get(ClassLoader.getSystemResource(BreadthTest.ELEVEN_EDGE_FILE).toURI())
        );
        final Vertex start = new Vertex("1");
        final Breadth bfs = new Breadth(graph, start);
        MatcherAssert.assertThat(bfs.next(), Matchers.is(start));
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(Matchers.is(new Vertex("3")), Matchers.is(new Vertex("2")))
        );
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(Matchers.is(new Vertex("3")), Matchers.is(new Vertex("2")))
        );
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(
                Matchers.is(new Vertex("5")),
                Matchers.is(new Vertex("4")),
                Matchers.is(new Vertex("6"))
            )
        );
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(
                Matchers.is(new Vertex("5")),
                Matchers.is(new Vertex("4")),
                Matchers.is(new Vertex("6"))
            )
        );
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(
                Matchers.is(new Vertex("5")),
                Matchers.is(new Vertex("4")),
                Matchers.is(new Vertex("6"))
            )
        );
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(
                Matchers.is(new Vertex("7")),
                Matchers.is(new Vertex("8"))
            )
        );
        MatcherAssert.assertThat(
            bfs.next(),
            Matchers.anyOf(
                Matchers.is(new Vertex("7")),
                Matchers.is(new Vertex("8"))
            )
        );
        MatcherAssert.assertThat(bfs.hasNext(), Matchers.is(false));
    }

    /**
     * Throws an exception if trying to call next again after finishing traversal.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void errorsWhenFinish() throws IOException, URISyntaxException {
        final Graph graph = new DirectedText(
            Paths.get(ClassLoader.getSystemResource(BreadthTest.THREE_EDGE_FILE).toURI())
        );
        final Vertex start = new Vertex("1");
        final Breadth bfs = new Breadth(graph, start);
        bfs.next();
        bfs.next();
        this.thrown.expect(NoSuchElementException.class);
        bfs.next();
    }

    /**
     * Traverses no edge graph.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void traversesNoEdge() throws IOException, URISyntaxException {
        final Graph directed = new DirectedText(
            Paths.get(ClassLoader.getSystemResource(BreadthTest.ZERO_EDGE_FILE).toURI())
        );
        final Graph undirected = new UndirectedText(
            Paths.get(ClassLoader.getSystemResource(BreadthTest.ZERO_EDGE_FILE).toURI())
        );
        final Vertex start = new Vertex("1");
        final Breadth bfsd = new Breadth(directed, start);
        final Breadth bfsu = new Breadth(undirected, start);
        MatcherAssert.assertThat(bfsd.next(), Matchers.is(start));
        MatcherAssert.assertThat(bfsd.hasNext(), Matchers.is(false));
        MatcherAssert.assertThat(bfsu.next(), Matchers.is(start));
        MatcherAssert.assertThat(bfsu.hasNext(), Matchers.is(false));
    }
}
