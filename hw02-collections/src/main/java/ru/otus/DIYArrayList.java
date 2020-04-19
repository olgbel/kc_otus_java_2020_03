package ru.otus;

import java.util.*;

public class DIYArrayList<T> implements List<T> {
    private Object[] elementData;
    private int size;
    private static final int DEFAULT_CAPACITY = 10;
    private static final Object[] EMPTY_ARRAY = {};

    public DIYArrayList() {
        this.elementData = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public DIYArrayList(int capacity) {
        if (capacity > 0) {
            elementData = new Object[capacity];
            size = 0;
        } else if (capacity == 0) {
            elementData = EMPTY_ARRAY;
            size = 0;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: " +
                    capacity);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<T> iterator() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(T t) {
        if (elementData.length == size) {
            extend();
        }
        elementData[size++] = t;

        return true;
    }

    private void extend() {
        Object[] supportArr = new Object[size << 1];
        System.arraycopy(elementData, 0, supportArr, 0, elementData.length);

        elementData = supportArr;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public T get(int index) {
        if (index >= 0 && index < this.size) {
            return (T) elementData[index];
        } else {
            throw new ArrayIndexOutOfBoundsException("The element index is out of arrays bound.");
        }
    }

    @Override
    public T set(int index, T element) {
        T oldValue = get(index);
        elementData[index] = element;

        return oldValue;
    }

    @Override
    public void add(int index, T element) {
        throw new UnsupportedOperationException();
    }

    @Override
    public T remove(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int indexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int lastIndexOf(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ListIterator<T> listIterator() {
        return new DIYListIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        throw new UnsupportedOperationException();
    }

    private class DIYListIterator implements ListIterator<T> {
        private int cursor;

        DIYListIterator() {
            cursor = -1;
        }

        @Override
        public boolean hasNext() {
            return cursor != size;
        }

        @Override
        public T next() {
            int next = cursor + 1;
            if (next > size) {
                throw new IndexOutOfBoundsException("Cannot get element with index " + next +
                        ". List contains only " + size + " elements.");
            }
            cursor = next;
            return (T) elementData[cursor];
        }

        @Override
        public boolean hasPrevious() {
            return cursor > 0;
        }

        @Override
        public T previous() {
            int prev = cursor - 1;
            if (prev < 0) {
                throw new NoSuchElementException("There is no such element.");
            }
            cursor = prev;

            return (T) elementData[cursor];
        }

        @Override
        public int nextIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public int previousIndex() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public void set(T t) {
            DIYArrayList.this.set(cursor, t);
        }

        @Override
        public void add(T t) {
            DIYArrayList.this.add(t);
        }
    }
}
