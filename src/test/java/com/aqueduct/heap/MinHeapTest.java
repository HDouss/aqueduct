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

import java.util.ArrayList;
import java.util.List;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Test;

/**
 * Test for {@link MinHeap}.
 * @since 0.1
 */
public class MinHeapTest {

    /**
     * Pops elements in order.
     */
    @Test
    public void popsElementsInOrder() {
        final List<Node<Character>> elts = MinHeapTest.elements();
        final MinHeap<Character> heap = new MinHeap<>(10);
        for (final Node<Character> elt : elts) {
            heap.insert(elt);
        }
        MatcherAssert.assertThat(heap.pop().element(), Matchers.is('a'));
        MatcherAssert.assertThat(heap.pop().element(), Matchers.is('b'));
        MatcherAssert.assertThat(heap.pop().element(), Matchers.is('c'));
        MatcherAssert.assertThat(heap.pop().element(), Matchers.is('d'));
        MatcherAssert.assertThat(heap.pop().element(), Matchers.is('e'));
    }

    /**
     * Updates node position.
     */
    @Test
    public void updatesNodePosition() {
        final List<Node<Character>> elts = MinHeapTest.elements();
        final MinHeap<Character> heap = new MinHeap<>(10);
        for (final Node<Character> elt : elts) {
            heap.insert(elt);
        }
        final double value = 15;
        final char chr = 'b';
        heap.node(chr).update(value);
        heap.update(chr);
        MatcherAssert.assertThat(heap.pop().element(), Matchers.is('a'));
        MatcherAssert.assertThat(heap.pop().element(), Matchers.is('c'));
        MatcherAssert.assertThat(heap.pop().element(), Matchers.is('d'));
        MatcherAssert.assertThat(heap.pop().element(), Matchers.is('e'));
        MatcherAssert.assertThat(heap.pop().element(), Matchers.is(chr));
    }

    /**
     * Returns a list of nodes with characters elements from a to e with respective values
     * from 1 to 5. The nodes are in disorder.
     * @return Node list
     */
    private static List<Node<Character>> elements() {
        final int size = 5;
        final List<Node<Character>> result = new ArrayList<>(size);
        final char[] chars = {'a', 'e', 'b', 'd', 'c'};
        for (final char chr : chars) {
            result.add(new Node<>(chr, chr - 'a'));
        }
        return result;
    }
}
