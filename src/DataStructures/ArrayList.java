package DataStructures;

import java.util.NoSuchElementException;

import ADTs.ListADT;

/**
 * Generic dynamic array implementation of ListADT.
 * Stores elements in a contiguous array and supports typical list operations.
 *
 * @param <T> the type of elements held in this list
 */
public class ArrayList<T> implements ListADT<T> {

  private static final int DEFAULT_CAPACITY = 10;
  private T[] buffer;
  private int size;

  @SuppressWarnings("unchecked")
  public ArrayList() {
    this.buffer = (T[]) new Object[DEFAULT_CAPACITY];
    this.size = 0;
  }

  /**
   * Adds an item at the specified index, shifting elements to the right.
   */
  @Override
  public void add(int index, T item) {
    if (index < 0 || index > size) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
    if (item == null) {
        throw new IllegalArgumentException("Item cannot be null.");
    }
    growIfNeeded();
    for (int i = size - 1; i >= index; i--) {
        buffer[i + 1] = buffer[i];
    }
    buffer[index] = item;
    size++;
  }

  @Override
  public void addFirst(T item) {
    add(0, item);
  }

  @Override
  public void addLast(T item) {
    add(size, item);
  }

  @Override
  public boolean addAfter(T existing, T item) {
    int index = indexOf(existing);
    if (index == -1) return false;
    add(index + 1, item);
    return true;
  }

  @Override
  public T removeFirst() {
    if (isEmpty()) throw new NoSuchElementException("List is empty.");
    return remove(0);
  }

  @Override
  public T removeLast() {
    if (isEmpty()) throw new NoSuchElementException("List is empty.");
    return remove(size - 1);
  }

  @Override
  public T remove(int index) {
    if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
    T removedItem = buffer[index];
    for (int i = index; i < size - 1; i++) {
        buffer[i] = buffer[i + 1];
    }
    buffer[size - 1] = null;
    size--;
    return removedItem;
  }

  @Override
  public boolean remove(T item) {
    int index = indexOf(item);
    if (index == -1) return false;
    remove(index);
    return true;
  }

  @Override
  public T first() {
    if (isEmpty()) throw new NoSuchElementException("List is empty.");
    return get(0);
  }

  @Override
  public T last() {
    if (isEmpty()) throw new NoSuchElementException("List is empty.");
    return get(size - 1);
  }

  @Override
  public T get(int index) {
    if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
    return buffer[index];
  }

  @Override
  public T set(int index, T item) {
    if (index < 0 || index >= size) {
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
    }
    if (item == null) {
        throw new IllegalArgumentException("Item cannot be null.");
    }
    T oldItem = buffer[index];
    buffer[index] = item;
    return oldItem;
  }

  @Override
  public int indexOf(T item) {
    for (int i = 0; i < size; i++) {
        if (buffer[i].equals(item)) {
            return i;
        }
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
    for (int i = 0; i < size; i++) {
        buffer[i] = null;
    }
    size = 0;
  }

  public String toDetailedString() {
    return "ArrayList[Size=" + size + ", Capacity=" + (buffer != null ? buffer.length : 0)
        + ", Contents=" + this.toString() + "]";
  }

  /**
   * Ensures the buffer has room for at least one more item.
   * Doubles capacity if needed.
   */
  @SuppressWarnings("unchecked")
  private void growIfNeeded() {
    if (this.size == this.buffer.length) {
      int oldCapacity = this.buffer.length;
      int newCapacity = (oldCapacity == 0) ? DEFAULT_CAPACITY : oldCapacity * 2;
      Object[] newBuffer = new Object[newCapacity];

      for (int i = 0; i < size; i++) {
        newBuffer[i] = buffer[i];
      }

      buffer = (T[]) newBuffer;
    }
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < size; i++) {
      sb.append(buffer[i]);
      if (i < size - 1) {
        sb.append(", ");
      }
    }
    sb.append("]");
    return "ArrayList[Size=" + size + ", Capacity=" + buffer.length + ", Contents=" + sb + "]";
  }
}
