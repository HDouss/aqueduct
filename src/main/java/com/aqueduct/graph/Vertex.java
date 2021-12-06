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

/**
 * Represents a vertex in a graph.
 * @since 0.1
 */
public final class Vertex {

    /**
     * A label or an identifier for the vertex.
     */
    private final String identifier;

    /**
     * Constructor. Builds a vertex with its identifier.
     * @param name Vertex identifier
     */
    public Vertex(final String name) {
        super();
        this.identifier = name;
    }

    /**
     * Accessor for the vertex name.
     * @return The vertex identifier
     */
    public String name() {
        return this.identifier;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        int hash = 0;
        if (this.identifier != null) {
            hash = this.identifier.hashCode();
        }
        result = prime * result + hash;
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        boolean result = false;
        if (obj != null && getClass() == obj.getClass()) {
            final Vertex other = (Vertex) obj;
            if (this.identifier == null) {
                result = other.identifier == null;
            } else {
                if (this.identifier.equals(other.identifier)) {
                    result = true;
                }
            }
        }
        return result;
    }

    @Override
    public String toString() {
        return "Vertex [name=".concat(this.identifier).concat("]");
    }

}
