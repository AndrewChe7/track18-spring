package ru.track.list;

import java.util.Arrays;
import java.util.NoSuchElementException;

/**
 * Должен наследовать List
 *
 * Должен иметь 2 конструктора
 * - без аргументов - создает внутренний массив дефолтного размера на ваш выбор
 * - с аргументом - начальный размер массива
 */
public class MyArrayList extends List {

    private int[] listArray;
    private int size;

    public MyArrayList() {
        this.listArray = new int[1];
        this.size = 0;
    }

    public MyArrayList(int capacity) {
        this.listArray = new int[capacity];
        this.size = 0;
    }

    @Override
    void add(int item) {
        if (this.listArray.length == size) {
            this.listArray = Arrays.copyOf(this.listArray, this.listArray.length + 10);
        }
        this.listArray[size] = item;
        size++;
    }

    @Override
    int remove(int idx) throws NoSuchElementException {
        if (idx > this.size - 1) {
            throw new NoSuchElementException();
        }
        int result = this.listArray[idx];
        for (int i = idx + 1; i < size; i++) {
            this.listArray[i - 1] = this.listArray[i];
        }
        size--;
        return result;
    }

    @Override
    int get(int idx) throws NoSuchElementException {
        if (idx > this.size - 1) {
            throw new NoSuchElementException();
        }
        return this.listArray[idx];
    }

    @Override
    int size() {
        return size;
    }
}
