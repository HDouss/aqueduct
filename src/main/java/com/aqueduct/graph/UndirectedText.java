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

import com.aqueduct.graph.util.TextToGraph;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Set;

/**
 * Represents an undirected graph built from text.
 * The text structure is as follows:<br>
 * <ul>
 * <li>The first line of the text denotes the number of vertices: n.</li>
 * <li>The graph vertices will have as label (name), the ordinal of the vertex as a string:
 * "1", "2", "3"... "n".</li>
 * <li>All the subsequent lines of the file (from the second line to the last line)
 * will represent edges and have the structure: Starting_Vertex Ending_Vertex Edge_Weight.
 * For example, the line: <pre>5 18 -6</pre> means there is an edge
 * from the vertex "5" to the vertex "18" with weight (or cost) -6.</li>
 * </ul>
 *
 * @since 0.1
 */
public final class UndirectedText implements Graph {

    /**
     * Underlying built graph.
     */
    private final Undirected graph;

    /**
     * Constructor. Builds a directed graph from a textual representation
     * described by the passed lines.
     * @param lines A list of lines representing the graph.
     *  The first line (element) is the string representing the number of vertices: n.
     *  All the subsequent lines (elements) will represent edges
     *  and have the structure: Starting_Vertex Ending_Vertex Edge_Weight.
     */
    public UndirectedText(final List<String> lines) {
        this.graph = UndirectedText.process(lines);
    }

    /**
     * Constructor. Builds an empty directed graph.
     * @param file The file containing the textual representation of the graph
     * @throws IOException If an I/O error occurs reading from the file
     *  or a malformed or unmappable byte sequence is read.
     */
    public UndirectedText(final Path file) throws IOException {
        this(Files.readAllLines(file));
    }

    @Override
    public void addVertices(final Vertex... vtx) {
        this.graph.addVertices(vtx);
    }

    @Override
    public void addEdge(final Edge edg) {
        this.graph.addEdge(edg);
    }

    @Override
    public void addEdge(final Vertex start, final Vertex end, final double cost) {
        this.graph.addEdge(start, end, cost);
    }

    @Override
    public Set<Vertex> vertices() {
        return this.graph.vertices();
    }

    @Override
    public Set<Edge> edges() {
        return this.graph.edges();
    }

    @Override
    public Set<Vertex> connected(final Vertex vtx) {
        return this.graph.connected(vtx);
    }

    @Override
    public Set<Edge> connectedEdges(final Vertex vtx) {
        return this.graph.connectedEdges(vtx);
    }

    /**
     * Process the list of lines to create vertices and edges.
     * @param lines List of strings representing graph representation
     * @return The resulting graph
     */
    private static Undirected process(final List<String> lines) {
        final Undirected graph = new Undirected();
        new TextToGraph(graph).process(lines);
        return graph;
    }
}
