package com.aau.evaluation.structs;

import java.util.Collection;
import java.util.Iterator;

// hashcode() should be implemented for element type.
public abstract class BaseHashTable implements Collection<Object>
{
    protected static int hash(int hashCode, int n)
    {
        return Math.abs(hashCode) % n;
    }

    @Override
    public boolean isEmpty()
    {
        return size() == 0;
    }

    @Override
    public boolean contains(Object o)
    {
        return baseContains(o);
    }

    // Objects are of type Integer
    @Override
    public Iterator<Object> iterator()
    {
        return baseIterator();
    }

    @Override
    public boolean add(Object e)
    {
        return baseAdd(e);
    }

    @Override
    public boolean remove(Object o)
    {
        return baseRemove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c)
    {
        Iterator<?> iter = c.iterator();

        while (iter.hasNext())
        {
            if (!contains(iter.next()))
                return false;
        }

        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Object> c)
    {
        Iterator<? extends Object> iter = c.iterator();

        while (iter.hasNext())
        {
            add(iter.next());
        }

        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c)
    {
        boolean allDeleted = true;
        Iterator<?> iter = c.iterator();

        while (iter.hasNext())
        {
            if (!remove(iter.next()))
                allDeleted = false;
        }

        return allDeleted;
    }

    @Override
    public boolean retainAll(Collection<?> c)
    {
        throw new UnsupportedOperationException("Unsupported operation");
    }

    @Override
    public void clear()
    {
        baseClear();
    }

    @Override
    public boolean equals(Object o)
    {
        return baseEquals(o);
    }

    @Override
    public int hashCode()
    {
        Iterator<Object> hashes = iterator();
        int hashSum = iterator().hashCode();

        while (hashes.hasNext())
        {
            hashSum += hashes.next().hashCode();
        }

        return hashSum;
    }

    protected abstract boolean baseContains(Object o);
    protected abstract Iterator<Object> baseIterator();
    protected abstract boolean baseAdd(Object o);
    protected abstract boolean baseRemove(Object o);
    protected abstract void baseClear();
    protected abstract boolean baseEquals(Object o);
}
