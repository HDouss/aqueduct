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

/**
 * Represents a node in a heap tree.
 * @param <E> Node element type
 * @since 0.1
 */
public final class Node<E> {

    /**
     * The element associated to the node.
     */
    private final E elt;

    /**
     * The value associated to the element that is used for the heap ordering.
     */
    private double val;

    /**
     * Constructor. Builds the node with the passed element and value.
     * @param element Node element
     * @param value Node value
     */
    public Node(final E element, final double value) {
        this.elt = element;
        this.val = value;
    }

    /**
     * Updates element value.
     * @param value New element value
     */
    public void update(final double value) {
        this.val = value;
    }

    /**
     * Accessor to the element value.
     * @return Element value
     */
    public double value() {
        return this.val;
    }

    /**
     * Accessor to the element.
     * @return Node element
     */
    public E element() {
        return this.elt;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        int hash = 0;
        if (this.elt != null) {
            hash = this.elt.hashCode();
        }
        result = prime * result + hash;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        boolean result = false;
        if (obj != null && getClass() == obj.getClass()) {
            @SuppressWarnings("unchecked")
            final Node<E> other = (Node<E>) obj;
            if (this.elt == null) {
                result = other.elt == null;
            } else {
                if (this.elt.equals(other.elt)) {
                    result = true;
                }
            }
        }
        return result;
    }

}
