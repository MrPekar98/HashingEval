package com.aau.evaluation.evaluators;

import org.apache.jena.graph.Triple;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class CollisionRate implements Evaluatable<Double>
{
    private Collection<Triple> triples;
    private final Collection<Collection<Triple>> collisions = new ArrayList<>();
    private static final String UNIT = "%";
    private double prog = 0;
    private String evaluationName;
    private final Object lock = new Object();

    public CollisionRate(Collection<Triple> triples, String evalName)
    {
        this.triples = triples;
        this.evaluationName = evalName;
    }

    @Override
    public String title()
    {
        return this.evaluationName;
    }

    @Override
    public Double eval()
    {
        int count = 0, iteration = 0;
        List<Triple> triples = new ArrayList(this.triples);

        for (int i = 0; i < triples.size(); i++)
        {
            for (int j = i + 1; j < triples.size(); j++)
            {
                if (triples.get(i).hashCode() == triples.get(j).hashCode())
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
    public synchronized double progress()
    {
        return this.prog;
    }
}
