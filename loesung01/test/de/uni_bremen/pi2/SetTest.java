package de.uni_bremen.pi2;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

/**
 * Test-Klasse für die Klasse Set<E>.
 *
 */
public class SetTest
{
    // Tests für size, iterator, contains, add, intersect, union, diff.

    /**
     * Test-Klasse für size
     */
    @Test
    public void testSize() {
        Set<Integer> set = new Set<>();
        Assertions.assertEquals(0, set.size());
        set.add(0);
        set.add(1);
        set.add(2);
        Assertions.assertEquals(3, set.size());
    }

    /**
     * Test-Klasse für iterator
     */
    @Test
    public void testIterator(){
        Set<Integer> set = new Set<>();
        Assertions.assertFalse(set.iterator().hasNext());
        set.add(0);
        Assertions.assertTrue(set.iterator().hasNext());
        Assertions.assertEquals(0, set.iterator().next());
    }

    /**
     * Test-Klasse für contains
     */
    @Test
    public void testContains(){
        Set<Integer> set = new Set<>();
        set.add(0);
        Assertions.assertTrue(set.contains(0));
        set.add(1);
        Assertions.assertTrue(set.contains(1));
        Assertions.assertFalse(set.contains(10));
    }

    /**
     * Test-Klasse für add
     */
    @Test
    public void testAdd(){
        Set<Integer> set = new Set<>();
        set.add(5);
        set.add(5);
        set.add(5);
        Assertions.assertEquals(1, set.size());
    }

    /**
     * Test-Klasse für intersect
     */
    @Test
    public void testIntersect(){
        Set<Integer> set1 = new Set<>();
        Set<Integer> set2 = new Set<>();
        set1.add(0);
        set1.add(1);
        set1.add(2);
        set1.add(3);
        set2.add(4);
        set2.add(5);
        set2.add(0);
        set2.add(1);
        Set<Integer> intersect = set1.intersect(set2);
        Assertions.assertEquals(2, intersect.size());
        Assertions.assertTrue(intersect.contains(0));
        Assertions.assertTrue(intersect.contains(1));
    }

    /**
     * Test-Klasse für union
     */
    @Test
    public void testUnion(){
        Set<Integer> set1 = new Set<>();
        Set<Integer> set2 = new Set<>();
        set1.add(0);
        set1.add(1);
        set1.add(2);
        set1.add(3);
        set2.add(4);
        set2.add(5);
        set2.add(0);
        set2.add(1);
        Set<Integer> union = set1.union(set2);
        Assertions.assertEquals(6, union.size());
        Assertions.assertTrue(union.contains(0));
        Assertions.assertTrue(union.contains(1));
        Assertions.assertTrue(union.contains(2));
        Assertions.assertTrue(union.contains(3));
        Assertions.assertTrue(union.contains(4));
        Assertions.assertTrue(union.contains(5));
    }

    /**
     * Test-Klasse für diff
     */
    @Test
    public void testDiff(){
        Set<Integer> set1 = new Set<>();
        Set<Integer> set2 = new Set<>();
        set1.add(0);
        set1.add(1);
        set1.add(2);
        set2.add(1);
        set2.add(2);
        set2.add(3);
        set2.add(4);
        Set<Integer> diff1 = set1.diff(set2);
        Assertions.assertEquals(1, diff1.size());
        Assertions.assertTrue(diff1.contains(0));
        Set<Integer> diff2 = set2.diff(set1);
        Assertions.assertEquals(2, diff2.size());
        Assertions.assertTrue(diff2.contains(3));
        Assertions.assertTrue(diff2.contains(4));
    }
}
