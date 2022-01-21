package com.aau.evaluation.evaluators;

import org.apache.jena.graph.Triple;
import org.apache.jena.mem.HashedBunchMap;

import java.util.Collection;

public class JenaHashBunchCollisionRate implements Evaluatable<Double>
{
    private static final String UNIT = "%";
    private String title;
    private Collection<Triple> triples;
    private double prog = 0;
    private HashedBunchMap bunchMap = new HashedBunchMap();
    private Triple[] values;
    private Integer[] keys;
    private int collisionCount = 0;

    public JenaHashBunchCollisionRate(Collection<Triple> triples, String evalName)
    {
        this.triples = triples;
        this.title = evalName;
        this.values = new Triple[triples.size()];
        this.keys = new Integer[triples.size()];
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
    public Double eval()
    {
        this.collisionCount = 0;
        this.triples.forEach(this::put);
        return ((double) this.collisionCount / this.triples.size()) * 100;
    }

    public void put(Triple value)
    {
        int slot = findSlot(value.hashCode(), this.keys);

        if (slot < 0)
            this.values[~slot] = value;

        else
        {
            this.keys[slot] = value.hashCode();
            this.values[slot] = value;
        }
    }

    private int findSlot(Integer key, Integer[] keys)
    {
        int index = initialIndexFor(key, keys.length), newCount = this.collisionCount + 1;

        while(true)
        {
            Integer current = keys[index];

            if (current == null)
                return index;


            if (key.equals(current))
                return ~index;

            this.collisionCount = newCount;
            --index;

            if (index < 0)
                index += keys.length;
        }
    }

    private static int initialIndexFor(Integer key, int capacity)
    {
        return (improveHashCode(key.hashCode()) & 2147483647) % capacity;
    }

    private static int improveHashCode(int hashCode)
    {
        return hashCode * 127;
    }
}
