package com.aau.evaluation.evaluators;

import com.github.jsonldjava.shaded.com.google.common.collect.HashMultiset;
import org.apache.jena.graph.Triple;

import java.util.Collection;

public class HashMultisetCollisionRate implements Evaluatable
{
    private static final String UNIT = "%";
    private String title;
    private Collection<Triple> triples;
    private double prog = 0;
    private HashMultiset<Triple> hms = HashMultiset.create();

    public HashMultisetCollisionRate(Collection<Triple> triples, String evalName)
    {
        this.title = evalName;
        this.triples = triples;
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
            if (this.hms.count(t) > 0)
                count++;

            else
                this.hms.add(t);

            this.prog = ((double) iteration++ / this.triples.size()) * 100;
        }

        return ((double) count / this.triples.size()) * 100;
    }
}
