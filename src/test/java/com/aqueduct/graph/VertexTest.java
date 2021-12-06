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
 * Test for {@link Vertex}.
 * @since 0.1
 */
public final class VertexTest {

    /**
     * Gives access to name.
     */
    @Test
    public void buildsVertex() {
        final String name = "samplename";
        MatcherAssert.assertThat(new Vertex(name).name(), Matchers.equalTo(name));
    }

    /**
     * Prints vertex name.
     */
    @Test
    public void printsVertexName() {
        final String name = "anothername";
        final String result = new Vertex(name).toString();
        MatcherAssert.assertThat(result, Matchers.containsString(name));
    }

    /**
     * Relies on name for equals and hashcode.
     */
    @Test
    public void reliesOnNameForEquality() {
        MatcherAssert.assertThat(VertexTest.start(), Matchers.equalTo(VertexTest.start()));
        MatcherAssert.assertThat(VertexTest.start(), Matchers.not(VertexTest.end()));
        MatcherAssert.assertThat(
            VertexTest.start().hashCode(), Matchers.equalTo(VertexTest.start().hashCode())
        );
        MatcherAssert.assertThat(
            VertexTest.start().hashCode(), Matchers.not(VertexTest.end().hashCode())
        );
    }

    /**
     * Builds vertex with name "end".
     * @return A vertex
     */
    private static Vertex end() {
        return new Vertex("end");
    }

    /**
     * Builds vertex with name "start".
     * @return A vertex
     */
    private static Vertex start() {
        return new Vertex("start");
    }
}
