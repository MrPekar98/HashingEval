package com.aau.evaluation.evaluators;

import com.github.andrewoma.dexx.collection.HashSet;
import org.apache.jena.graph.Triple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HashSetAndrewOma implements Evaluatable
{
    private static final String UNIT = "%";
    private String title;
    private Collection<Triple> triples;
    private double prog = 0;
    private HashSet hashSet = HashSet.factory().newBuilder().build();

    public HashSetAndrewOma(Collection<Triple> triples, String evalName)
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
        List<Triple> triples = new ArrayList<>(this.triples);

        for (int i = 0; i < this.triples.size(); i++)
        {
            for (int j = i + 1; j < this.triples.size(); j++)
            {
                if (improve(triples.get(i).hashCode()) == improve(triples.get(j).hashCode()))
                    count++;
            }

            this.prog = ((double) iteration++ / triples.size()) * 100;
        }

        return ((double) count / triples.size()) * 100;
    }

    private final int improve(int hashCode)
    {
        int h = hashCode + ~(hashCode << 9);
        h ^= h >>> 14;
        h += h << 4;
        return h ^ h >>> 10;
    }
}
