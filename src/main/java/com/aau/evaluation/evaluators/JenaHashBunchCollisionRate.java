package com.aau.evaluation.evaluators;

import org.apache.jena.graph.Triple;
import org.apache.jena.mem.ArrayBunch;
import org.apache.jena.mem.HashedBunchMap;

import java.util.Collection;

public class JenaHashBunchCollisionRate implements Evaluatable
{
    private static final String UNIT = "%";
    private String title;
    private Collection<Triple> triples;
    private double prog = 0;
    private HashedBunchMap bunchMap = new HashedBunchMap();

    public JenaHashBunchCollisionRate(Collection<Triple> triples, String evalName)
    {
        this.triples = triples;
        this.title = evalName;
    }

    @Override
    public String unit()
    {
        return UNIT;
    }

    @Override
    public String title()
    {
        return this.title;
    }

    @Override
    public synchronized double progress()
    {
        return this.prog;
    }

    @Override
    public double eval()
    {
        int count = 0, iteration = 0;

        for (Triple t : this.triples)
        {
            if (this.bunchMap.get(t.hashCode()) != null)
                count++;

            else
            {
                ArrayBunch ab = new ArrayBunch();
                ab.add(t);
                this.bunchMap.put(t.hashCode(), ab);
            }

            this.prog = ((double) iteration++ / this.triples.size()) * 100;
        }

        return ((double) count / this.triples.size()) * 100;
    }
}
