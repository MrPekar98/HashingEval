package com.aau.evaluation.structs;

import org.apache.commons.codec.digest.MurmurHash3;

public class BloomFilter2 extends BloomFilter1 implements BloomFilter
{
    public BloomFilter2(int capacity)
    {
        super(capacity);
    }

    @Override
    public void add(Object obj)
    {
        super.add(obj);
        super.add(hash(obj, super.capacity));
    }

    @Override
    public boolean lookup(Object obj)
    {
        return super.lookup(obj) && super.lookup(hash(obj, super.capacity));
    }

    private static int hash(Object obj, int len)
    {
        return MurmurHash3.hash32(obj.hashCode()) % len;
    }
}
