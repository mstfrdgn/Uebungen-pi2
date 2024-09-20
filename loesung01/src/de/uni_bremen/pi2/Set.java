package de.uni_bremen.pi2;
import java.util.Iterator;
import java.util.NoSuchElementException;



/**
 * Die Klasse implementiert eine Menge mit typischen Operationen.
 * @param <E> Der Typ der Elemente der Menge. Dieser muss "equals" sinnvoll
 *          implementieren.
 *
 * @author simonberg
 */
public class Set<E> implements Iterable<E> {

    /**
     * Puffer für Elemente vom Typ E mit Anfangsgröße 0
     * 
     */
    private E[] buffer;
    /**
     * Momentan der höchst beschriebene Index + 1, bzw. die aktuelle Größe des Puffers
     */
    private int currentSize;


    /**
     * Konstruktor für ein neues leeres Set mit der Größe 5
     */
    public Set(){
        this(5);
    }

    /**
     * Konstruktor für ein neues leeres Set mit der gegebenen Größe
     * @param capacity
     */
    public Set(int capacity) {
        buffer = (E[]) new Object[capacity];
        currentSize = 0;
    }





    /**
     * Aufgabe 1.1 Anzahl der Elemente
     * Liefert die Größe des Arrays bzw. gibt den höchst beschriebenen Index + 1 wieder
     *
     * @return Größe des Arrays
     */

    public int size() {

        return currentSize;

    }

    /**
     * Ändert die Größe des Puffers auf die angegebene Kapazität.
     *
     * @param newCapacity die neue Kapazität des Puffers
     *
     */
    private void resize(int newCapacity) {

        E[] newBuffer = (E[]) new Object[newCapacity];
        System.arraycopy(buffer, 0, newBuffer, 0, currentSize);
        buffer = newBuffer;


    }


    /**
     * Aufgabe 1.2 Aufzählen der Elemente
     *
     * Gibt einen Iterator über die Elemente in dieser Sammlung zurück.
     *
     * Die Methode hasNext() prüft, ob weitere Elemente zum Durchlaufen vorhanden sind, indem sie den Index mit der aktuellen Größe der Sammlung vergleicht.
     * Wenn der Index kleiner als currentSize ist, wird „true“ zurückgegeben was bedeutet, dass mehr Elemente zum Durchlaufen vorhanden sind.
     *
     * Die Methode next() gibt das nächste Element in der Iteration zurück.
     * Zunächst wird überprüft, ob der Index innerhalb der Grenzen der Sammlung liegt, indem er mit currentSize verglichen wird.
     * Ist dies nicht der Fall, wird eine IndexOutOfBoundsException ausgelöst.
     * Andernfalls wird das Element am aktuellen Index zurückgegeben und der Index für die nächste Iteration erhöht.
     *
     * @return einen Iterator über die Elemente in der Sammlung.
     */

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < currentSize;
            }
            @Override
            public E next() {
                if (index >= currentSize) {
                    throw new IndexOutOfBoundsException();
                }
                return buffer[index++];
            }
        };
    }


    /**
     * Aufgabe 1.3 Test auf Vorhandensein
     * Gibt true zurück, wenn die Menge des angebene Element enthält, anderfalls false
     * @param element das Element welches gesucht werden soll
     * @return true, wenn die Menge das angegebene Element enthält anderfalls false
     */
    public boolean contains(E element) {
        for (int i = 0; i < currentSize; i++) {
            if (element.equals(buffer[i])) {
                return true;
            }
        }
        return false;
    }


    /**
     * Aufgabe 1.4 Einfügen eines Elements
     * Die Methode fügt ein übergebenes Element in die Menge ein, vorausgesetzt
     * es ist nicht bereits bestandteil der Menge.
     * @param element das hinzufügende Element
     */
    public void add(E element) {

        if (element == null) {

            throw new IllegalArgumentException("Element darf nicht null sein");

        }
        if (!contains(element)) {
            if (currentSize == buffer.length) {
                resize(buffer.length * 2);
            }
            buffer[currentSize++] = element;
        }
    }


    /**
     * Aufgabe 1.5 Schnittmenge
     * Gibt eine neue Menge zurück, die alle Elemente enthält,
     * die sowohl in dieser Menge als auch in der anderen Menge enthalten sind.
     * @param other das andere Set
     * @return Schnittmenge (A ∩ B)
     */
    public Set<E> intersect(Set<E> other) {
        Set<E> result = new Set<>();
        for (E buffer : this) {
            if (other.contains(buffer)) {
                result.add(buffer);
            }
        }
        return result;
    }

    /**
     * Aufgabe 1.6 Vereinigungsmenge
     * Gibt eine neue Menge zurück, die alle Elemente enthält,
     * die in dieser Menge oder in der anderen Menge enthalten sind.
     * @param other das andere Set
     * @return Vereinigungsmenge (A ∪ B)
     */
    public Set<E> union(Set<E> other) {
        Set<E> result = new Set<>();
        for (E buffer : this) {
            result.add(buffer);
        }
        for (E buffer : other) {
            if (!result.contains(buffer)) {
                result.add(buffer);
            }
        }
        return result;
    }


    /**
     * Aufgabe 1.7 Differenzmenge
     * Gibt eine neue Menge zurück, die alle Elemente enthält,
     * die in dieser Menge, aber nicht in der anderen Menge enthalten sind.
     * @param other das andere Set
     * @return Differenzmenge (A \ B)
     */
    public Set<E> diff(Set<E> other) {
        Set<E> result = new Set<>();
        for (E buffer : this) {
            if (!other.contains(buffer)) {
                result.add(buffer);
            }
        }
        return result;
    }

}
