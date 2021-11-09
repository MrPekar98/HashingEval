package com.aau.evaluation.structs;

import java.util.Iterator;

public class BasicStaticHashTable extends BaseHashTable
{
    private Object[] objects;

    public BasicStaticHashTable(int capacity)
    {
        this.objects = new Object[capacity];
    }

    @Override
    public int size()
    {

    }

    @Override
    public Object[] toArray()
    {
        Object[] copy = new Object[this.objects.length];
        System.arraycopy(this.objects, 0, copy, 0, this.objects.length);
        return copy;
    }

    @Override
    public Object[] toArray(Object[] a)
    {
        if (a.length < this.objects.length)
            return toArray();

        else
        {
            for (int i = 0; i < this.objects.length; i++)
            {
                a[i] = this.objects[i];
            }
        }

        if (a.length > this.objects.length)
        {
            for (int i = this.objects.length; i < a.length; i++)
            {
                a[i] = null;
            }
        }

        return a;
    }

    @Override
    protected boolean baseContains(Object o)
    {

    }

    @Override
    protected Iterator<Object> baseIterator()
    {

    }

    @Override
    protected boolean baseAdd(Object o)
    {

    }

    @Override
    protected boolean baseRemove(Object o)
    {

    }

    @Override
    protected void baseClear()
    {
        for (int i = 0; i < this.objects.length; i++)
        {
            this.objects[i] = null;
        }

        System.gc();
    }

    @Override
    protected boolean baseEquals(Object o)
    {

    }
}
