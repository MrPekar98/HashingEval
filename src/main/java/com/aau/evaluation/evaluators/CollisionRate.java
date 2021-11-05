package com.aau.evaluation.evaluators;

import org.apache.jena.graph.Triple;

import java.util.List;
import java.util.ArrayList;
import java.util.Collection;

public class CollisionRate implements Evaluatable
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
        //computeCollisions();
    }

    private void computeCollisions()
    {
        new Thread(() -> {
            synchronized (this.lock)
            {
                for (Triple t : this.triples)
                {
                    insertTriple(t);
                }
            }
        }).start();
    }

    private void insertTriple(Triple t)
    {
        boolean inserted = false;

        for (Collection<Triple> ct : this.collisions)
        {
            if (ct.size() > 0 && ct.stream().findFirst().get().hashCode() == t.hashCode())
            {
                ct.add(t);
                inserted = true;
                break;
            }
        }

        if (!inserted)
        {
            Collection<Triple> c = new ArrayList<>();
            c.add(t);
            this.collisions.add(c);
        }
    }

    @Override
    public String title()
    {
        return this.evaluationName;
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
