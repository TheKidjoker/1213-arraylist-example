package DataStructures;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Objects;

import ADTs.ListADT;

public class ArrayList<T> implements ListADT<T> {
    private static final int DEFAULT_CAPACITY = 10;
    private T[] buffer;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        this.buffer = (T[]) new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    // ---------------------------
    // Private Helper: grow buffer
    // ---------------------------
    @SuppressWarnings("unchecked")
    private void growIfNeeded() {
        if (this.size == this.buffer.length) {
            int newCapacity = (this.buffer.length == 0) ? DEFAULT_CAPACITY : this.buffer.length * 2;
            T[] newBuffer = (T[]) new Object[newCapacity];
            System.arraycopy(this.buffer, 0, newBuffer, 0, this.size);
            this.buffer = newBuffer;
        }
    }

    // -------------------
    // Add Methods
    // -------------------
    @Override
    public void addLast(T item) {
        if (item == null) throw new IllegalArgumentException();
        growIfNeeded();
        this.buffer[this.size++] = item;
    }

    @Override
    public void addFirst(T item) {
        add(0, item);
    }

    @Override
    public void add(int index, T item) {
        if (item == null) throw new IllegalArgumentException();
        if (index < 0 || index > this.size) throw new IndexOutOfBoundsException();
        growIfNeeded();
        System.arraycopy(this.buffer, index, this.buffer, index + 1, this.size - index);
        this.buffer[index] = item;
        this.size++;
    }

    @Override
    public boolean addAfter(T existing, T item) {
        if (existing == null || item == null) throw new IllegalArgumentException();
        int i = indexOf(existing);
        if (i == -1) return false;
        add(i + 1, item);
        return true;
    }

    // -------------------
    // Remove Methods
    // -------------------
    @Override
    public T removeFirst() {
        if (isEmpty()) throw new NoSuchElementException();
        return remove(0);
    }

    @Override
    public T removeLast() {
        if (isEmpty()) throw new NoSuchElementException();
        return remove(this.size - 1);
    }

    @Override
    public T remove(int index) {
        if (index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
        T removed = this.buffer[index];
        int numMoved = this.size - index - 1;
        if (numMoved > 0) {
            System.arraycopy(this.buffer, index + 1, this.buffer, index, numMoved);
        }
        this.buffer[--this.size] = null;
        return removed;
    }

    @Override
    public boolean remove(T item) {
        int i = indexOf(item);
        if (i == -1) return false;
        remove(i);
        return true;
    }

    // -------------------
    // Accessors & Queries
    // -------------------
    @Override
    public T get(int index) {
        if (index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
        return this.buffer[index];
    }

    @Override
    public T set(int index, T item) {
        if (item == null) throw new IllegalArgumentException();
        if (index < 0 || index >= this.size) throw new IndexOutOfBoundsException();
        T old = this.buffer[index];
        this.buffer[index] = item;
        return old;
    }

    @Override
    public T first() {
        if (isEmpty()) throw new NoSuchElementException();
        return get(0);
    }

    @Override
    public T last() {
        if (isEmpty()) throw new NoSuchElementException();
        return get(this.size - 1);
    }

    @Override
    public int indexOf(T item) {
        for (int i = 0; i < this.size; i++) {
            if (Objects.equals(item, this.buffer[i])) return i;
        }
        return -1;
    }

    @Override
    public boolean contains(T item) {
        return indexOf(item) != -1;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public void clear() {
        Arrays.fill(this.buffer, 0, this.size, null);
        this.size = 0;
    }

    // Optional: Detailed state for debugging
    public String toDetailedString() {
        return "ArrayList[Size=" + size + ", Capacity=" + (buffer != null ? buffer.length : 0) + "] " + Arrays.toString(Arrays.copyOf(buffer, size));
    }
}