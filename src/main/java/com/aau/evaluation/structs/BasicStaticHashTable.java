package com.aau.evaluation.structs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public class BasicStaticHashTable extends BaseHashTable
{
    private Object[] objects;
    private int size = 0;

    public BasicStaticHashTable(int capacity)
    {
        this.objects = new Object[capacity];
        clean();
    }

    private void clean()
    {
        for (int i = 0; i < this.objects.length; i++)
        {
            this.objects[i] = null;
        }
    }

    private boolean doIfExists(Function<Object, Boolean> func, Object o)
    {
        if (this.objects[hash(o.hashCode(), this.objects.length)] == null)
            return func.apply(o);

        return false;
    }

    @Override
    public int size()
    {
        return this.size;
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
        return this.objects[hash(o.hashCode(), this.objects.length)] != null;
    }

    @Override
    protected Iterator<Object> baseIterator()
    {
        List<Object> objects = new ArrayList<>(this.objects.length);

        for (int i = 0; i < this.objects.length; i++)
        {
            if (this.objects[i] != null)
                objects.add(this.objects[i]);
        }

        return objects.iterator();
    }

    @Override
    protected boolean baseAdd(Object o)
    {
        return doIfExists((Object obj) -> {
            this.objects[hash(obj.hashCode(), this.objects.length)] = obj;
            this.size++;
            return true;
        }, o);
    }

    @Override
    protected boolean baseRemove(Object o)
    {
        return doIfExists((Object obj) -> {
            this.objects[hash(obj.hashCode(), this.objects.length)] = null;
            this.size--;
            return true;
        }, o);
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
        if (!(o instanceof BasicStaticHashTable))
            return false;

        BasicStaticHashTable table = (BasicStaticHashTable) o;

        if (table.size() != size())
            return false;

        Iterator<Object> iter1 = iterator(), iter2 = table.iterator();

        while (iter1.hasNext() && iter2.hasNext())
        {
            if (iter1.next().hashCode() != iter2.next().hashCode())
                return false;
        }

        return true;
    }
}
