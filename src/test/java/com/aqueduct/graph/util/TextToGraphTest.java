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
package com.aqueduct.graph.util;

import com.aqueduct.graph.Directed;
import com.aqueduct.graph.Edge;
import com.aqueduct.graph.Graph;
import com.aqueduct.graph.Vertex;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link TextToGraph}.
 * @since 0.1
 */
public final class TextToGraphTest {

    /**
     * Max random integer.
     */
    private static final int INT_RANDOM = 10;

    /**
     * Puts the right number of vertices.
     */
    @Test
    public void populatesVertices() {
        final Graph graph = new Directed();
        final TextToGraph populate = new TextToGraph(graph);
        final Integer count = new Random().nextInt(TextToGraphTest.INT_RANDOM) + 1;
        populate.process(Arrays.asList(count.toString()));
        MatcherAssert.assertThat(graph.edges().size(), Matchers.equalTo(0));
        MatcherAssert.assertThat(graph.vertices().size(), Matchers.equalTo(count));
    }

    /**
     * Puts right number of edges.
     */
    @Test
    public void populatesMultipleEdges() {
        final Graph graph = new Directed();
        final TextToGraph populate = new TextToGraph(graph);
        final Random random = new Random();
        final Integer count = random.nextInt(TextToGraphTest.INT_RANDOM) + 1;
        final List<String> input = new ArrayList<>(count);
        input.add(count.toString());
        for (int idx = 0; idx < count; ++idx) {
            input.add(TextToGraphTest.edge(random, count));
        }
        populate.process(input);
        MatcherAssert.assertThat(graph.edges().size(), Matchers.equalTo(count));
        MatcherAssert.assertThat(graph.vertices().size(), Matchers.equalTo(count));
    }

    /**
     * Puts correct edge.
     */
    @Test
    public void populatesRightEdge() {
        final Graph graph = new Directed();
        final TextToGraph populate = new TextToGraph(graph);
        final Random random = new Random();
        final Integer count = random.nextInt(TextToGraphTest.INT_RANDOM) + 1;
        final List<String> input = new ArrayList<>(count);
        input.add(count.toString());
        final String generated = TextToGraphTest.edge(random, count);
        input.add(generated);
        populate.process(input);
        final Edge added = graph.edges().iterator().next();
        final String[] elts = generated.split("\\s");
        MatcherAssert.assertThat(added.start(), Matchers.equalTo(new Vertex(elts[0])));
        MatcherAssert.assertThat(added.end(), Matchers.equalTo(new Vertex(elts[1])));
        MatcherAssert.assertThat(added.cost(), Matchers.equalTo(Double.parseDouble(elts[2])));
        MatcherAssert.assertThat(graph.vertices().size(), Matchers.equalTo(count));
    }

    /**
     * Generates textual representation of a random edge.
     * @param random Random integer generator
     * @param count Vertices count
     * @return Textual representation of a random edge
     */
    private static String edge(final Random random, final Integer count) {
        return String.format(
            "%s %s %s",
            random.nextInt(count) + 1,
            random.nextInt(count) + 1,
            random.nextInt(TextToGraphTest.INT_RANDOM)
        );
    }
}
