package com.aau.evaluation.evaluators;

import com.aau.evaluation.structs.BasicStaticHashTable;
import org.apache.jena.graph.Triple;

import java.util.Collection;

public class BasicStaticHashTableCollisionRate implements Evaluatable<Double>
{
    private static final String UNIT = "%";
    private double prog = 0;
    private String evaluationName;
    private Collection<Triple> triples;
    private BasicStaticHashTable hashTable;

    public BasicStaticHashTableCollisionRate(Collection<Triple> triples, String evalName)
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
    public String unit()
    {
        return UNIT;
    }

    @Override
    public double progress()
    {
        return this.prog;
    }

    @Override
    public Double eval()
    {
        this.hashTable = new BasicStaticHashTable(this.triples.toArray());
        return (1 - ((double) this.hashTable.size() / this.triples.size())) * 100;
    }
}
