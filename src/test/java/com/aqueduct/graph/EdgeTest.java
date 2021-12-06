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

import java.util.Random;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link Edge}.
 * @since 0.1
 */
public final class EdgeTest {

    /**
     * Gives access to vertices and cost.
     */
    @Test
    public void buildsEdge() {
        final double cost = new Random().nextDouble();
        final Edge edge = new Edge(EdgeTest.start(), EdgeTest.end(), cost);
        MatcherAssert.assertThat(edge.start(), Matchers.equalTo(EdgeTest.start()));
        MatcherAssert.assertThat(edge.end(), Matchers.equalTo(EdgeTest.end()));
        MatcherAssert.assertThat(edge.cost(), Matchers.equalTo(cost));
    }

    /**
     * Prints vertices and cost with toString.
     */
    @Test
    public void printsVerticesAndCost() {
        final double cost = new Random().nextDouble();
        final String result = new Edge(EdgeTest.start(), EdgeTest.end(), cost).toString();
        MatcherAssert.assertThat(result, Matchers.containsString(Double.toString(cost)));
        MatcherAssert.assertThat(result, Matchers.containsString(EdgeTest.start().toString()));
        MatcherAssert.assertThat(result, Matchers.containsString(EdgeTest.end().toString()));
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
