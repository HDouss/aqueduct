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
package com.aqueduct.heap;

import java.util.Random;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link Node}.
 * @since 0.1
 */
public class NodeTest {

    /**
     * Gives access to element and value.
     */
    @Test
    public void buildsNode() {
        final double value = new Random().nextDouble();
        final String element = "sampleelement";
        final Node<String> node = new Node<>(element, value);
        MatcherAssert.assertThat(node.element(), Matchers.equalTo(element));
        MatcherAssert.assertThat(node.value(), Matchers.equalTo(value));
    }

    /**
     * Gives ability to update the value.
     */
    @Test
    public void updatesValue() {
        final Random random = new Random();
        final double value = random.nextDouble();
        final Node<String> node = new Node<>("", value);
        final double updated = random.nextDouble();
        node.update(updated);
        MatcherAssert.assertThat(node.value(), Matchers.equalTo(updated));
    }

    /**
     * Relies on element for equals and hashcode.
     */
    @Test
    public void reliesOnElementForEquality() {
        final double value = new Random().nextDouble();
        final String element = "element";
        final String another = "another";
        final Node<String> node = new Node<>(element, value);
        final Node<String> same = new Node<>(element, value);
        final Node<String> other = new Node<>(another, value);
        final Node<String> empty = new Node<>(null, value);
        MatcherAssert.assertThat(node, Matchers.equalTo(same));
        MatcherAssert.assertThat(node, Matchers.not(other));
        MatcherAssert.assertThat(empty, Matchers.not(node));
        MatcherAssert.assertThat(
            node.hashCode(), Matchers.equalTo(same.hashCode())
        );
        MatcherAssert.assertThat(
            node.hashCode(), Matchers.not(other.hashCode())
        );
    }
}
