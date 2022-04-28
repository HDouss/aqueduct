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

import com.aqueduct.graph.Graph;
import com.aqueduct.graph.Vertex;
import java.util.Iterator;
import java.util.List;

/**
 * A convenient class to populate a graph with vertices and edges,
 * from a text or a list of strings. The text structure is as follows:<br>
 * <ul>
 * <li>The first line of the text denotes the number of vertices: n.</li>
 * <li>The graph vertices will have as label (name), the ordinal of the vertex as a string:
 * "1", "2", "3"... "n".</li>
 * <li>All the subsequent lines of the file (from the second line to the last line)
 * will represent edges and have the structure: Starting_Vertex Ending_Vertex Edge_Weight.
 * For example, the line: <pre>5 18 -6</pre> means there is an edge
 * from the vertex "5" to the vertex "18" with weight (or cost) -6.</li>
 * </ul>
 * This class is mutable: it only has a reference to the passed graph to be populated.
 * @since 0.1
 */
public class TextToGraph {

    /**
     * Graph to populate.
     */
    private final Graph graph;

    /**
     * Constructor.
     * @param grph The graph to populate
     */
    public TextToGraph(final Graph grph) {
        this.graph = grph;
    }

    /**
     * Process a list of strings to populate the underlying graph with vertices and edges.
     * @param lines List of strings
     */
    public void process(final List<String> lines) {
        final Iterator<String> iterator = lines.iterator();
        final Long count = Long.parseLong(iterator.next());
        for (Long idx = 1L; idx <= count; ++idx) {
            this.graph.addVertices(new Vertex(idx.toString()));
        }
        while (iterator.hasNext()) {
            final String line = iterator.next();
            final String[] elts = line.split("\\s");
            this.graph.addEdge(
                new Vertex(elts[0]), new Vertex(elts[1]), Double.parseDouble(elts[2])
            );
        }
    }

}
