package com.aau.evaluation.evaluators;

import org.apache.jena.graph.Triple;

import java.util.Collection;

public class HashSetCollisions implements Evaluatable
{
    private String title;
    private Collection<Triple> triples;
    private double prog = 0;
    private static final String UNIT = "%";

    public HashSetCollisions(Collection<Triple> triples, String evalTitle)
    {
        this.title = evalTitle;
        this.triples = triples;
    }

    // JDK 11 HashMap hash function including indexing.
    private static int hash(Object key, int slots)
    {
        int h;
        int hashSetHash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        return (slots - 1) & hashSetHash;
    }

    @Override
    public String title()
    {
        return this.title;
    }

    @Override
    public double eval()
    {
        int count = 0, i = 0;

        for (Triple t1 : this.triples)
        {
            for (Triple t2 : this.triples)
            {
                if (!t1.equals(t2))
                    count += hash(t1, this.triples.size()) == hash(t2, this.triples.size()) ? 1 : 0;
            }

            this.prog = ((double) i++ / this.triples.size()) * 100;
        }

        return ((double) count / this.triples.size()) * 100;
    }

    @Override
    public String unit()
    {
        return UNIT;
    }

    @Override
    public double progress()
    {
        return this.prog;
    }
}
