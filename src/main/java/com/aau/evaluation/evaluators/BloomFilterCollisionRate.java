package com.aau.evaluation.evaluators;

import com.aau.evaluation.structs.BloomFilter;
import com.aau.evaluation.structs.BloomFilter2;
import org.apache.jena.graph.Triple;

import java.util.Collection;

public class BloomFilterCollisionRate implements Evaluatable<Double>
{
    private static final String UNIT = "%";
    private String title;
    private double prog = 0;
    private Collection<Triple> triples;
    private BloomFilter filter;

    public BloomFilterCollisionRate(Collection<Triple> triples, String title)
    {
        this.title = title;
        this.triples = triples;
        this.filter = new BloomFilter2((int) ((double) triples.size() * 1.25));
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
    public double progress()
    {
        return this.prog;
    }

    @Override
    public Double eval()
    {
        int count = 0;

        for (Triple t : this.triples)
        {
            if (!this.filter.lookup(t))
                this.filter.add(t);

            else
                count++;
        }

        return ((double) count / this.triples.size()) * 100;
    }
}
