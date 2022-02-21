package com.aau.evaluation.structs;

public interface BloomFilter
{
    void add(Object obj);
    void clear();
    boolean lookup(Object obj);
}
