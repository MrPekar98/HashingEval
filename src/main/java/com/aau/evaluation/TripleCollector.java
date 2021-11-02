package com.aau.evaluation;

import org.apache.jena.graph.Triple;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class TripleCollector
{
    private final Collection<Triple> triples = new ArrayList<>();

    public abstract TripleCollector collect(URI datasetPath);

    protected void addTriples(Triple ... triples)
    {
        this.triples.addAll(List.of(triples));
    }

    public Collection<Triple> getTriples()
    {
        return this.triples;
    }
}
