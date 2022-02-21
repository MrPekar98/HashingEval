package com.aau.evaluation.evaluators;

import org.apache.jena.graph.Triple;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HashSetMaxBucketSize implements Evaluatable<String>
{
    private String title;
    private Collection<Triple> triples;
    private double prog = 0;
    private static final String UNIT = "";

    public HashSetMaxBucketSize(Collection<Triple> triples, String evalTitle)
    {
        this.triples = triples;
        this.title = evalTitle;
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
    public String eval()
    {
        List<Integer> frequencies = new ArrayList<>(this.triples.size());
        Collections.fill(frequencies, 0);

        for (Triple t : this.triples)
        {
            int idx = hash(t, this.triples.size());
            frequencies.add(idx, frequencies.get(idx) + 1);
            this.prog += (double) 1 / this.triples.size();
        }

        return bucketSizesStr(frequencies);
    }

    // JDK 11 HashMap hash function including indexing.
    private static int hash(Object key, int slots)
    {
        int h;
        int hashSetHash = (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
        return (slots - 1) & hashSetHash;
    }

    private static String bucketSizesStr(List<Integer> frequencies)
    {
        String buckets = "";

        for (int i = 0; i < frequencies.size(); i++)
        {
            buckets += "Bucket " + (i + 1) + ": " + frequencies.get(i) + "\n";
        }

        return buckets;
    }
}
