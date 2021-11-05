package com.aau.evaluation.evaluators;

import org.apache.jena.graph.Triple;

import java.util.List;
import java.util.ArrayList;
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
        int count = 0, iteration = 0;
        List<Triple> triples = new ArrayList(this.triples);

        for (int i = 0; i < triples.size(); i++)
        {
            for (int j = i + 1; j < triples.size(); j++)
            {
                if (hash(triples.get(i), triples.size()) == hash(triples.get(j), triples.size()))
                {
                    count++;
                    break;
                }
            }

            this.prog = ((double) iteration++ / triples.size()) * 100;
        }

        return ((double) count / triples.size()) * 100;
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
