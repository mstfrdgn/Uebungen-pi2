package de.uni_bremen.pi2;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @author Mustafa Erdogan
 */

public class MergeSortTest {


    /**
     * Erzeugt eine Liste aus einer Folge von Werten.
     * Z.B. erzeugt asList(1, 2, 3) eine Liste mit den Werten 1, 2 und 3.
     * @param values Die Werte, aus denen die Liste erzeugt wird. Können einfach
     *         aufgezählt werden.
     * @param <E> Der Typ der Werte.
     * @return Die Liste, die die Werte in der Reihenfolge enthält, in der sie
     *         aufgezählt wurden.
     */

    // Hilfsmethode zur Erstellung einer Liste aus einem Array von Werten
    private <E extends Comparable<E>> Node<E> asList(final E... values) {
        Node<E> head = null;
        Node<E> current = null;
        Comparable[] var4 = values;
        int var5 = values.length;

        for(int var6 = 0; var6 < var5; ++var6) {
            E value = (E) var4[var6];
            if (head == null) {
                head = new Node(value);
                current = head;
            } else {
                current.setNext(new Node(value));
                current = current.getNext();
            }
        }

        return head;
    }


    /**
     * Überprüft, ob eine Liste einer bestimmten Folge von Werten entspricht.
     * Kann z.B. wie folgt genutzt werden:
     * <pre>
     * assertListEquals(MergeSort.sort(asList(3, 2, 1)), 1, 2, 3);
     * </pre>
     * @param head Der erste Knoten der Liste, deren Inhalt verglichen wird.
     * @param values Die Vergleichswerte. Können einfach aufgezählt werden.
     * @param <E> Der Typ der Werte, die verglichen werden.
     */

    // Hilfsmethode zur Überprüfung, ob die Liste mit dem erwarteten Wertefeld übereinstimmt
    private <E extends Comparable<E>> void assertListEquals(Node<E> head, E... values) {
        int index = 0;

        Node current;
        for(current = head; current != null && index < values.length; ++index) {
            Assertions.assertEquals(values[index], current.getValue(), "Werte sollten bei Index übereinstimmen " + index);
            current = current.getNext();
        }

        Assertions.assertNull(current, "Die Liste sollte hier enden");
        Assertions.assertEquals(values.length, index, "Alle Werte sollten geprüft werden");
    }

    // Testfall für eine leere Liste
    @Test
    public void testSortEmptyList() {
        Assertions.assertNull(MergeSort.sort((Node)null), "Das Sortieren einer leeren Liste sollte null ergeben.");
    }

    // Testfall für eine Liste mit einem einzigen Element
    @Test
    public void testSortSingleElement() {
        Node<Integer> single = new Node(1);
        Node<Integer> sorted = MergeSort.sort(single);
        this.assertListEquals(sorted, 1);
    }

    // Testfall für eine Liste mit mehreren Elementen
    @Test
    public void testSortMultipleElements() {
        Node<Integer> list = this.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);
        Node<Integer> sorted = MergeSort.sort(list);
        this.assertListEquals(sorted, 1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9);
    }

    // Testfall für eine sortierte Liste
    @Test
    public void testSortAlreadySorted() {
        Node<Integer> list = this.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);
        Node<Integer> sorted = MergeSort.sort(list);
        this.assertListEquals(sorted, 1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9);
    }

    // Testfall für eine umgekehrt sortierte Liste
    @Test
    public void testSortReverseSorted() {
        Node<Integer> list = this.asList(3, 1, 4, 1, 5, 9, 2, 6, 5, 3, 5);
        Node<Integer> sorted = MergeSort.sort(list);
        this.assertListEquals(sorted, 1, 1, 2, 3, 3, 4, 5, 5, 5, 6, 9);
    }
}