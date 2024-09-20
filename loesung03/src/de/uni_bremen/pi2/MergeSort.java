package de.uni_bremen.pi2;

/**
 * @author simonberg
 */
public class MergeSort
{

    /**
     * Sortiert eine einfach verkettete Liste durch Mischen
     * @param head ist der Anfangsknoten der zu sortierenden einfach verketteten Liste
     * @param <E> ist der generische Typ dieser Liste
     * @return die Referenz auf den ersten Knoten der sortierten Liste
     */
    public static <E extends Comparable<E>> Node<E> sort(final Node<E> head)
    {
        Node<E> merged;
        Node<E> temp = head;

        if(head == null || head.getNext() == null) {
            return head;

        }else {

            Node<E> left;
            Node<E> right;
            Node<E> middle;

            middle = getMiddleNode(temp);
            left = sort(temp);
            right = sort(middle);

            merged = merge(left, right);
        }


        return merged;

    }


    /**
     * Mischen zweier aufeinander folgender, bereits sortierter
     * Teilbereiche einer einfach verketteten Liste zu einer gemeinsamen, sortierten Liste
     *
     * @param headOne ist der Anfangsknoten der ersten Teilliste
     * @param headTwo ist der Anfangsknoten der zweiten Teilliste
     * @param <E> ist der generische Typ der Liste
     * @return die Referenz auf den Anfangsknoten der sortierten, zusammengefügten Liste
     */
    private static <E extends Comparable<E>> Node<E> merge(final Node<E> headOne, final Node<E> headTwo){

        Node<E> result = null;
        //später gegebenfalls löschen
        if(headOne == null) {
            return headTwo;
        }else if (headTwo == null) {
            return headOne;
        }


        if(headOne.getValue().compareTo(headTwo.getValue()) <= 0) {
            result = headOne;
            result.setNext(merge(headOne.getNext(), headTwo));

        }else {
            result = headTwo;
            result.setNext(merge(headOne, headTwo.getNext()));
        }

        return result;

    }


    /**
     * Teilt eine einfach verkettete Liste in 2 gleich große Teillisten
     * @param head ist die Referenz auf den Anfangsknoten der Ursprungsliste
     * @param <E> ist der generische Typ der Liste
     * @return die Referenz auf den Anfangsknoten der 2. Teilliste
     */
    private static <E extends Comparable<E>> Node<E> getMiddleNode(final Node<E> head) {
        Node<E> temp = head;
        int size = 0;

        while(temp != null) {
            size++;
            temp = temp.getNext();
        }

        temp = head;

        int middleIndex = size / 2;
        while(middleIndex > 1) {

            temp = temp.getNext();
            middleIndex = middleIndex - 1;
        }

        Node<E> head2 = temp.getNext();
        temp.setNext(null);


        return head2;
    }




}