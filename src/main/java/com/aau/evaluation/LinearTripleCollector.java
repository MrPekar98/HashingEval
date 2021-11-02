package com.aau.evaluation;

import org.apache.jena.graph.Triple;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;

import java.net.URI;
import java.util.Iterator;

public class LinearTripleCollector extends TripleCollector
{
    public static TripleCollector createCollector(URI datasetPath)
    {
        TripleCollector collector = new LinearTripleCollector();
        return collector.collect(datasetPath);
    }

    @Override
    public TripleCollector collect(URI datasetPath)
    {
        Model m = ModelFactory.createDefaultModel();
        m.read(datasetPath.getPath());

        Iterator<Triple> triples = m.getGraph().find();

        while (triples.hasNext())
        {
            super.addTriples(triples.next());
        }

        return this;
    }
}
