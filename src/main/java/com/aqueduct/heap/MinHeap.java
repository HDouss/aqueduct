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

import java.util.HashMap;
import java.util.Map;

/**
 * MinHeap (priority queue) data structure that supports value update for nodes.
 * @param <E> Node element type
 * @since 0.1
 */
public class MinHeap<E> {

    /**
     * Constant representing the first (popping) position in the array.
     */
    private static final int FRONT = 1;

    /**
     * Internal array to represent the heap.
     */
    private final Node<E>[] heap;

    /**
     * Actual size of the heap.
     */
    private int size;

    /**
     * Max size supported by the heap.
     */
    private final int max;

    /**
     * Map to keep track of nodes positions in the internal array.
     */
    private final Map<E, Integer> positions;

    /**
     * Constructor. Builds a heap with the passed maximum size.
     * @param maximal Maximum size supported by the heap
     */
    @SuppressWarnings("unchecked")
    public MinHeap(final int maximal) {
        this.max = maximal;
        this.size = 0;
        this.positions = new HashMap<>(maximal);
        this.heap = new Node[this.max + 1];
        this.heap[0] = new Node<>(null, Integer.MIN_VALUE);
    }

    /**
     * Recalculates node position in the tree. This method is typically if the node value changes.
     * @param elt The element held by the node that should be updated in the tree
     */
    public void update(final E elt) {
        final int pos = this.positions.get(elt);
        if (!this.minHeapify(pos)) {
            this.bubble(pos);
        }
    }

    /**
     * Returns the tree node holding the element.
     * @param elt Element
     * @return Node
     */
    public Node<E> node(final E elt) {
        return this.heap[this.positions.get(elt)];
    }

    /**
     * Inserts a node in the heap.
     * @param element Element to insert
     */
    public void insert(final Node<E> element) {
        if (this.size >= this.max) {
            return;
        }
        this.heap[++this.size] = element;
        this.positions.put(element.element(), this.size);
        this.bubble(this.size);
    }

    /**
     * Pops the minimum element.
     * @return The minimum element
     */
    public Node<E> pop() {
        Node<E> popped = null;
        if (this.size > 0) {
            popped = this.heap[MinHeap.FRONT];
            this.heap[MinHeap.FRONT] = this.heap[this.size];
            this.size = this.size - 1;
            this.positions.put(this.heap[MinHeap.FRONT].element(), MinHeap.FRONT);
            if (this.size > 0) {
                this.minHeapify(MinHeap.FRONT);
            }
        }
        return popped;
    }

    /**
     * Checks if the passed position is a leaf.
     * @param pos Position to check
     * @return Boolean indicating if the position is a leaf
     */
    private boolean leaf(final int pos) {
        return pos > (this.size / 2) && pos <= this.size;
    }

    /**
     * Swaps nodes residing in the passed positions.
     * @param fpos First position
     * @param spos Second position
     */
    private void swap(final int fpos, final int spos) {
        final Node<E> tmp = this.heap[fpos];
        this.heap[fpos] = this.heap[spos];
        this.heap[spos] = tmp;
        this.positions.put(tmp.element(), spos);
        this.positions.put(this.heap[fpos].element(), fpos);
    }

    /**
     * Bubbles the element up as long as its value is lower than its parent's value.
     * @param pos Element position
     */
    private void bubble(final int pos) {
        int current = pos;
        while (this.heap[current].value() < this.heap[MinHeap.getParent(current)].value()) {
            this.swap(current, MinHeap.getParent(current));
            current = MinHeap.getParent(current);
        }
    }

    /**
     * Heapifies (pushes downward the tree) the element in the passed position.
     * @param pos Position of the element to heapify
     * @return True if the element was actually pushed downward
     */
    private boolean minHeapify(final int pos) {
        final int leftp = MinHeap.getLeft(pos);
        final int rightp = MinHeap.getRight(pos);
        final double value = this.heap[pos].value();
        boolean pushed = false;
        if (!this.leaf(pos) && (value > this.heap[leftp].value() || value > this.heap[rightp]
            .value())) {
            pushed = true;
            if (this.heap[leftp].value() < this.heap[rightp].value()) {
                this.swap(pos, leftp);
                this.minHeapify(leftp);
            } else {
                this.swap(pos, rightp);
                this.minHeapify(rightp);
            }
        }
        return pushed;
    }

    /**
     * Returns the position of the parent.
     * @param pos Position of child
     * @return Position of parent
     */
    private static int getParent(final int pos) {
        return pos / 2;
    }

    /**
     * Returns the position of the left child.
     * @param pos Position of parent
     * @return Position of left child
     */
    private static int getLeft(final int pos) {
        return 2 * pos;
    }

    /**
     * Returns the position of the right child.
     * @param pos Position of parent
     * @return Position of right child
     */
    private static int getRight(final int pos) {
        return 2 * pos + 1;
    }
}
