package com.aau.evaluation.structs;

import java.util.Arrays;

public class BloomFilter1 implements BloomFilter
{
    protected int capacity;
    private byte[] seq;
    private static int DEFAULT_CAPACITY = 10000;

    public BloomFilter1(int capacity)
    {
        this.capacity = capacity > 0 ? capacity : DEFAULT_CAPACITY;
        this.seq = new byte[(int) Math.ceil((double) this.capacity / 8)];
        Arrays.fill(this.seq, (byte) 0);
    }

    public int getCapacity()
    {
        return this.capacity;
    }

    @Override
    public void add(Object obj)
    {
        int bit = hash(obj, this.capacity);
        int byteIdx = bit / 8;
        this.seq[byteIdx] = flipBit(this.seq[byteIdx], bit - 8 * byteIdx);
    }

    @Override
    public void clear()
    {
        Arrays.fill(this.seq, (byte) 0);
    }

    @Override
    public boolean lookup(Object obj)
    {
        int bit = hash(obj, this.capacity);
        int byteIdx = bit / 8;
        byte checkBit = flipBit((byte) 0, bit - 8 * byteIdx);

        return (this.seq[byteIdx] & checkBit) != 0;
    }

    private static int hash(Object obj, int len)
    {
        return Math.abs(obj.hashCode() % len);
    }

    protected static byte flipBit(byte b, int idx)
    {
        if (idx >= 8)
            throw new IllegalArgumentException("Bit index must be in the range 0-7");

        byte flipper = 1;
        flipper = (byte) (flipper << (byte) idx);
        return (byte) (b | flipper);
    }
}
