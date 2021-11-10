package com.aau.struct;

import com.aau.evaluation.structs.BasicStaticHashTable;
import org.junit.Before;
import org.junit.Test ;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class BasicHashTableTest
{
    private BasicStaticHashTable hashTable;
    private static final List<Integer> INTS = List.of(4324, 2355, 2122, 6543, 2987, 2332, 1093, 9873, 4009, 8011);

    @Before
    public void before()
    {
        this.hashTable = new BasicStaticHashTable(INTS.toArray());
    }

    @Test
    public void testSize()
    {
        assertTrue(this.hashTable.size() <= 10);
    }

    @Test
    public void testContains()
    {
        for (Integer i : INTS)
        {
            assertTrue(this.hashTable.contains(i));
        }
    }

    @Test
    public void testRemove()
    {
        int oldSize = this.hashTable.size();
        this.hashTable.remove(INTS.get(0));
        this.hashTable.remove(INTS.get(4));
        assertEquals(oldSize - 2, this.hashTable.size());
    }

    @Test
    public void testContainsAll()
    {
        assertTrue(this.hashTable.containsAll(INTS));
    }
}
