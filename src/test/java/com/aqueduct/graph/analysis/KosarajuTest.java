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

import com.aqueduct.graph.DirectedText;
import com.aqueduct.graph.Graph;
import com.aqueduct.graph.Vertex;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link Kosaraju}.
 * @since 0.1
 */
public final class KosarajuTest {

    /**
     * Nine vertex, eleven edges graph filename for kosaraju test case.
     */
    private static final String KOSARAJU_CASE_ONE = "kosaraju-one";

    /**
     * Traverses simple directed graph.
     * @throws URISyntaxException If error
     * @throws IOException If error
     */
    @Test
    public void traversesSimpleDirected() throws IOException, URISyntaxException {
        final Graph graph = new DirectedText(
            Paths.get(ClassLoader.getSystemResource(KosarajuTest.KOSARAJU_CASE_ONE).toURI())
        );
        final Iterator<Set<Vertex>> iterator = new Kosaraju(graph);
        final Set<Vertex> first = new HashSet<>();
        first.add(new Vertex("1"));
        first.add(new Vertex("4"));
        first.add(new Vertex("7"));
        final Set<Vertex> second = new HashSet<>();
        second.add(new Vertex("3"));
        second.add(new Vertex("6"));
        second.add(new Vertex("9"));
        final Set<Vertex> third = new HashSet<>();
        third.add(new Vertex("2"));
        third.add(new Vertex("5"));
        third.add(new Vertex("8"));
        MatcherAssert.assertThat(iterator.next(), Matchers.is(first));
        MatcherAssert.assertThat(iterator.next(), Matchers.is(second));
        MatcherAssert.assertThat(iterator.next(), Matchers.is(third));
        MatcherAssert.assertThat(iterator.hasNext(), Matchers.is(false));
    }
}
