package ru.track.list;

import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 * Односвязный список
 */
public class MyLinkedList extends List {

    /**
     * private - используется для сокрытия этого класса от других.
     * Класс доступен только изнутри того, где он объявлен
     * <p>
     * static - позволяет использовать Node без создания экземпляра внешнего класса
     */
    private static class Node {
        Node prev;
        Node next;
        int val;

        Node(Node prev, Node next, int val) {
            this.prev = prev;
            this.next = next;
            this.val = val;
        }
    }

    private Node rootNode = new Node(null, null, 0);
    private int size = 0;

    @Override
    void add(int item) {
        Node node = this.rootNode;
        while (node.next != null) {
            node = node.next;
        }
        node.next = new Node(node, null, item);
        this.size++;
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if (idx > this.size - 1) {
            throw new NoSuchElementException();
        }
        Node node = this.rootNode;
        for (int i = -1; i < idx; i++) {
            node = node.next;
        }
        int result = node.val;
        node.prev.next = node.next;
        this.size--;
        return result;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if (idx > this.size - 1) {
            throw new NoSuchElementException();
        }
        Node node = this.rootNode;
        for (int i = -1; i < idx; i++) {
            node = node.next;
        }
        return node.val;
    }

    @Override
    int size() {
        return this.size;
    }
}
