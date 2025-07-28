package DataStructures;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals; // Import necessary annotations
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the ArrayList class.
 * These tests follow the principles of Design Recipe Step 3 (Examples & Tests),
 * covering typical cases, edge cases, boundary conditions, and error handling.
 * - @DisplayName provides clearer names in test reports.
 * - @Nested groups tests by the method or concept under test.
 * - Assertion messages explain the 'why' behind each check, aiding debugging.
 */
@DisplayName("ArrayList<T> Tests")
class ArrayListTest {
  private ArrayList<String> list;

  @BeforeEach
  void setUp() {
    // Create a new empty list before each test
    list = new ArrayList<>();
  }

  @Nested
  @DisplayName("Basic Operations (Constructor, isEmpty, size)")
  class BasicOperationsTests {
    @Test
    @DisplayName("New list should be empty and have size 0")
    void testNewListIsEmpty() {
      assertTrue(list.isEmpty(), "A newly created list should report isEmpty() == true");
      assertEquals(0, list.size(), "A newly created list should have size() == 0");
    }
  }

  @Nested
  @DisplayName("Adding Elements (add, addFirst, addLast, addAfter)")
  class AddTests {

    @Test
    @DisplayName("addFirst(item) adds to the beginning")
    void testAddFirst() {
      list.addFirst("A");
      assertEquals(1, list.size(), "Size should be 1 after addFirst");
      assertEquals("A", list.first(), "first() should return the added item 'A'");
      list.addFirst("B");
      assertEquals(2, list.size(), "Size should be 2 after second addFirst");
      assertEquals("B", list.first(), "'B' should now be the first element");
      assertEquals("A", list.get(1), "'A' should have shifted to index 1");
    }

    @Test
    @DisplayName("addLast(item) adds to the end")
    void testAddLast() {
      list.addLast("A");
      assertEquals(1, list.size(), "Size should be 1 after addLast");
      assertEquals("A", list.last(), "last() should return the added item 'A'");
      list.addLast("B");
      assertEquals(2, list.size(), "Size should be 2 after second addLast");
      assertEquals("B", list.last(), "'B' should now be the last element");
      assertEquals("A", list.get(0), "'A' should still be at index 0");
    }

    @Test
    @DisplayName("add(index, item) inserts correctly and shifts elements")
    void testAddAtIndex_InsertsAndShifts() {
      // State: [] -> add(0, "A") -> ["A"]
      list.add(0, "A");
      assertEquals(1, list.size(), "Size should be 1 after add(0, A) on empty list");
      assertEquals("A", list.get(0), "get(0) should return 'A'");

      // State: ["A"] -> add(1, "B") -> ["A", "B"] (Add at end using index)
      list.add(1, "B");
      assertEquals(2, list.size(), "Size should be 2 after add(1, B)");
      assertEquals("A", list.get(0), "get(0) should still be 'A'");
      assertEquals("B", list.get(1), "get(1) should now be 'B'");

      // State: ["A", "B"] -> add(1, "C") -> ["A", "C", "B"] (Add in middle)
      list.add(1, "C");
      assertEquals(3, list.size(), "Size should be 3 after add(1, C)");
      assertEquals("A", list.get(0), "get(0) should still be 'A'");
      assertEquals("C", list.get(1), "get(1) should now be 'C'");
      assertEquals("B", list.get(2), "get(2) should be 'B' (shifted)");
    }

    @Test
    @DisplayName("add(index, item) allows adding at index == size()")
    void testAddAtIndex_AtSize() {
      list.addLast("A"); // ["A"]
      list.add(1, "B"); // Add at index 1 (size) -> ["A", "B"]
      assertEquals(2, list.size(), "Size should be 2 after adding at index == size");
      assertEquals("B", list.get(1), "Element should be present at the new last index");
    }

    @Test
    @DisplayName("addAfter(existing, item) adds correctly")
    void testAddAfter_ItemExists() {
      list.addLast("A");
      list.addLast("B"); // State: ["A", "B"]
      assertTrue(list.addAfter("A", "C"), "addAfter('A', 'C') should return true");
      assertEquals(3, list.size(), "Size should be 3 after addAfter");
      assertEquals("A", list.get(0), "get(0) should still be 'A'");
      assertEquals("C", list.get(1), "'C' should be inserted at index 1");
      assertEquals("B", list.get(2), "'B' should be shifted to index 2");
    }

    @Test
    @DisplayName("addAfter(existing, item) handles non-existent item and adds after last")
    void testAddAfter_ItemNonExistentAndAtEnd() {
      assertFalse(list.addAfter("nonexistent", "A"), "addAfter should return false if 'existing' item not found");
      assertEquals(0, list.size(), "Size should remain 0 if item not found");

      list.addFirst("A"); // State: ["A"]
      assertTrue(list.addAfter("A", "B"), "addAfter('A', 'B') should return true when 'A' is the only element");
      assertEquals(2, list.size(), "Size should be 2 after adding after the last element");
      assertEquals("B", list.last(), "'B' should be the new last element");
      assertEquals("B", list.get(1), "'B' should be at index 1");
    }
  }

  @Nested
  @DisplayName("Removing Elements (remove, removeFirst, removeLast, clear)")
  class RemoveTests {

    @Test
    @DisplayName("removeFirst() removes and returns the first element")
    void testRemoveFirst_Basic() {
      list.addLast("A");
      list.addLast("B"); // State: ["A", "B"]
      assertEquals("A", list.removeFirst(), "removeFirst() should return 'A'");
      assertEquals(1, list.size(), "Size should be 1 after removeFirst");
      assertEquals("B", list.first(), "'B' should now be the first element");
    }

    @Test
    @DisplayName("removeFirst() on single-element list")
    void testRemoveFirst_SingleElement() {
      list.addLast("A"); // State: ["A"]
      assertEquals("A", list.removeFirst(), "removeFirst() should return 'A'");
      assertEquals(0, list.size(), "Size should be 0 after removing the only element");
      assertTrue(list.isEmpty(), "List should be empty after removing the only element");
    }

    @Test
    @DisplayName("removeLast() removes and returns the last element")
    void testRemoveLast_Basic() {
      list.addLast("A");
      list.addLast("B"); // State: ["A", "B"]
      assertEquals("B", list.removeLast(), "removeLast() should return 'B'");
      assertEquals(1, list.size(), "Size should be 1 after removeLast");
      assertEquals("A", list.last(), "'A' should now be the last element");
      assertEquals("A", list.first(), "'A' should still be the first element");
    }

    @Test
    @DisplayName("removeLast() on single-element list")
    void testRemoveLast_SingleElement() {
      list.addLast("A"); // State: ["A"]
      assertEquals("A", list.removeLast(), "removeLast() should return 'A'");
      assertEquals(0, list.size(), "Size should be 0 after removing the only element");
      assertTrue(list.isEmpty(), "List should be empty after removing the only element");
    }

    @Test
    @DisplayName("remove(T item) removes first occurrence and returns true")
    void testRemove_ByItem_Present() {
      list.addLast("A");
      list.addLast("B");
      list.addLast("A"); // State: ["A", "B", "A"]
      assertTrue(list.remove("A"), "remove('A') should return true when 'A' is present");
      assertEquals(2, list.size(), "Size should be 2 after removing first 'A'");
      assertEquals("B", list.get(0), "'B' should now be at index 0");
      assertEquals("A", list.get(1), "Second 'A' should now be at index 1");
    }

    @Test
    @DisplayName("remove(T item) returns false if item not present")
    void testRemove_ByItem_Absent() {
      list.addLast("A");
      list.addLast("B"); // State: ["A", "B"]
      assertFalse(list.remove("C"), "remove('C') should return false if 'C' is not present");
      assertEquals(2, list.size(), "Size should remain 2 if item to remove is not found");
    }

    @Test
    @DisplayName("remove(int index) removes element and shifts subsequent elements")
    void testRemove_ByIndex_CausesShifting() {
      list.addLast("A");
      list.addLast("B");
      list.addLast("C");
      list.addLast("D"); // State: ["A", "B", "C", "D"]

      // Remove from middle
      assertEquals("B", list.remove(1), "remove(1) should return 'B'");
      assertEquals(3, list.size(), "Size should be 3 after removing index 1");
      assertEquals("A", list.get(0), "Index 0 should still be 'A'");
      assertEquals("C", list.get(1), "'C' should shift to index 1");
      assertEquals("D", list.get(2), "'D' should shift to index 2");

      // Remove from start
      assertEquals("A", list.remove(0), "remove(0) should return 'A'");
      assertEquals(2, list.size(), "Size should be 2 after removing index 0");
      assertEquals("C", list.get(0), "'C' should shift to index 0");
      assertEquals("D", list.get(1), "'D' should shift to index 1");

      // Remove from end
      assertEquals("D", list.remove(1), "remove(1) (the last element) should return 'D'");
      assertEquals(1, list.size(), "Size should be 1 after removing last element");
      assertEquals("C", list.get(0), "'C' should be the only element left at index 0");
    }

    @Test
    @DisplayName("remove(int index) works at boundaries (0 and size-1)")
    void testRemove_ByIndex_AtBoundaries() {
      list.addLast("A");
      list.addLast("B");
      list.addLast("C"); // State: ["A", "B", "C"]

      // Test index == size-1 (last element)
      assertEquals("C", list.remove(2), "remove(size-1) should return last element 'C'");
      assertEquals(2, list.size(), "Size should be 2 after removing last element");
      assertEquals("B", list.get(1), "Element 'B' should be the new last element at index 1");

      // Test index == 0 (first element)
      assertEquals("A", list.remove(0), "remove(0) should return first element 'A'");
      assertEquals(1, list.size(), "Size should be 1 after removing first element");
      assertEquals("B", list.get(0), "Element 'B' should be the new first element at index 0");

      // Test index == 0 when size is 1
      assertEquals("B", list.remove(0), "remove(0) should return the only element 'B'");
      assertEquals(0, list.size(), "Size should be 0 after removing the only element");
      assertTrue(list.isEmpty(), "List should be empty after removing the only element");
    }

    @Test
    @DisplayName("clear() removes all elements")
    void testClear() {
      list.addLast("A");
      list.addLast("B");
      assertFalse(list.isEmpty(), "List should not be empty before clear()");
      list.clear();
      assertTrue(list.isEmpty(), "List should be empty after clear()");
      assertEquals(0, list.size(), "Size should be 0 after clear()");
    }

    @Test
    @DisplayName("clear() on an already empty list")
    void testClear_OnEmptyList() {
      assertTrue(list.isEmpty(), "List should be empty initially");
      list.clear();
      assertTrue(list.isEmpty(), "List should remain empty after clearing an empty list");
      assertEquals(0, list.size(), "Size should remain 0 after clearing an empty list");
    }
  }

  @Nested
  @DisplayName("Accessing & Modifying Elements (get, set, first, last)")
  class AccessModifyTests {

    @BeforeEach
    void setupList() {
      // Common setup for tests needing a non-empty list
      list.addLast("A");
      list.addLast("B");
      list.addLast("C"); // State: ["A", "B", "C"]
    }

    @Test
    @DisplayName("get(index) returns correct element at valid indices")
    void testGet_ValidIndex() {
      assertEquals("A", list.get(0), "get(0) should return first element 'A'");
      assertEquals("B", list.get(1), "get(1) should return middle element 'B'");
      assertEquals("C", list.get(2), "get(2) should return last element 'C'");
    }

    @Test
    @DisplayName("set(index, item) replaces element and returns old element")
    void testSet_ValidIndex() {
      assertEquals("B", list.set(1, "X"), "set(1, 'X') should return the old element 'B'");
      assertEquals(3, list.size(), "Size should remain 3 after set");
      assertEquals("A", list.get(0), "get(0) should still be 'A'");
      assertEquals("X", list.get(1), "get(1) should now be the new element 'X'");
      assertEquals("C", list.get(2), "get(2) should still be 'C'");
    }

    @Test
    @DisplayName("first() returns the first element without removing")
    void testFirst() {
      assertEquals("A", list.first(), "first() should return 'A'");
      assertEquals(3, list.size(), "Size should remain 3 after first()");
      assertEquals("A", list.get(0), "Element 'A' should still be at index 0 after first()");
    }

    @Test
    @DisplayName("last() returns the last element without removing")
    void testLast() {
      assertEquals("C", list.last(), "last() should return 'C'");
      assertEquals(3, list.size(), "Size should remain 3 after last()");
      assertEquals("C", list.get(2), "Element 'C' should still be at index 2 after last()");
    }
  }

  @Nested
  @DisplayName("Querying List State (contains, indexOf, isEmpty, size)")
  class QueryTests {

    @Test
    @DisplayName("contains(item) works correctly")
    void testContains() {
      assertFalse(list.contains("A"), "contains('A') should be false on empty list");
      list.addLast("A");
      list.addLast("B"); // State: ["A", "B"]
      assertTrue(list.contains("A"), "contains('A') should be true when 'A' is present");
      assertTrue(list.contains("B"), "contains('B') should be true when 'B' is present");
      assertFalse(list.contains("C"), "contains('C') should be false when 'C' is not present");
      assertFalse(list.contains(null), "contains(null) should be false (or handle as per spec if nulls allowed)");
    }

    @Test
    @DisplayName("indexOf(item) finds first occurrence or returns -1")
    void testIndexOf() {
      assertEquals(-1, list.indexOf("A"), "indexOf('A') should be -1 on empty list");
      list.addLast("A");
      list.addLast("B");
      list.addLast("A");
      list.addLast("C"); // State: ["A", "B", "A", "C"]

      assertEquals(0, list.indexOf("A"), "indexOf('A') should return index of first occurrence (0)");
      assertEquals(1, list.indexOf("B"), "indexOf('B') should return 1");
      assertEquals(3, list.indexOf("C"), "indexOf('C') should return 3");
      assertEquals(-1, list.indexOf("D"), "indexOf('D') should return -1 for non-existent item");
      assertEquals(-1, list.indexOf(null), "indexOf(null) should return -1 (or handle as per spec)");
    }

    @Test
    @DisplayName("isEmpty() and size() reflect list state after operations")
    void testIsEmptyAndSize_AfterOps() {
      assertTrue(list.isEmpty(), "Initially empty");
      assertEquals(0, list.size(), "Initially size 0");

      list.addFirst("A");
      assertFalse(list.isEmpty(), "Not empty after addFirst");
      assertEquals(1, list.size(), "Size 1 after addFirst");

      list.removeFirst();
      assertTrue(list.isEmpty(), "Empty after removing only element");
      assertEquals(0, list.size(), "Size 0 after removing only element");
    }
  }

  @Nested
  @DisplayName("Exception Handling")
  class ExceptionTests {

    @Test
    @DisplayName("Adding or Setting null item throws IllegalArgumentException")
    void testAddSet_NullItem_ThrowsIllegalArgumentException() {
      assertThrows(IllegalArgumentException.class, () -> list.addFirst(null),
          "addFirst(null) should throw IllegalArgumentException");
      assertThrows(IllegalArgumentException.class, () -> list.addLast(null),
          "addLast(null) should throw IllegalArgumentException");
      assertThrows(IllegalArgumentException.class, () -> list.add(0, null),
          "add(0, null) on empty list should throw IllegalArgumentException");
      list.addLast("A"); // Need an element to set
      assertThrows(IllegalArgumentException.class, () -> list.set(0, null),
          "set(0, null) should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("addAfter with null arguments throws IllegalArgumentException")
    void testAddAfter_NullArguments_ThrowsIllegalArgumentException() {
      list.addFirst("A"); // Need an existing item for some tests
      assertThrows(IllegalArgumentException.class, () -> list.addAfter(null, "B"),
          "addAfter(null, B) should throw IllegalArgumentException");
      assertThrows(IllegalArgumentException.class, () -> list.addAfter("A", null),
          "addAfter(A, null) should throw IllegalArgumentException");
      assertThrows(IllegalArgumentException.class, () -> list.addAfter(null, null),
          "addAfter(null, null) should throw IllegalArgumentException");
    }

    @Test
    @DisplayName("Accessing/Modifying with invalid index throws IndexOutOfBoundsException")
    void testAccessModify_InvalidIndex_ThrowsIndexOutOfBounds() {
      // Test on empty list
      assertThrows(IndexOutOfBoundsException.class, () -> list.get(0),
          "get(0) on empty list should throw IndexOutOfBoundsException");
      assertThrows(IndexOutOfBoundsException.class, () -> list.set(0, "A"),
          "set(0, A) on empty list should throw IndexOutOfBoundsException");
      assertThrows(IndexOutOfBoundsException.class, () -> list.remove(0),
          "remove(0) on empty list should throw IndexOutOfBoundsException");

      // Test on list with one element ["A"]
      list.addLast("A");
      assertThrows(IndexOutOfBoundsException.class, () -> list.get(-1),
          "get(-1) should throw IndexOutOfBoundsException");
      assertThrows(IndexOutOfBoundsException.class, () -> list.get(1),
          "get(1) on size=1 list should throw IndexOutOfBoundsException");
      assertThrows(IndexOutOfBoundsException.class, () -> list.set(-1, "B"),
          "set(-1, B) should throw IndexOutOfBoundsException");
      assertThrows(IndexOutOfBoundsException.class, () -> list.set(1, "B"),
          "set(1, B) on size=1 list should throw IndexOutOfBoundsException");
      assertThrows(IndexOutOfBoundsException.class, () -> list.remove(-1),
          "remove(-1) should throw IndexOutOfBoundsException");
      assertThrows(IndexOutOfBoundsException.class, () -> list.remove(1),
          "remove(1) on size=1 list should throw IndexOutOfBoundsException");
    }

    @Test
    @DisplayName("add(index, item) with invalid index throws IndexOutOfBoundsException")
    void testAdd_InvalidIndex_ThrowsIndexOutOfBounds() {
      // Test on empty list
      assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "A"),
          "add(-1, A) on empty list should throw IndexOutOfBoundsException");
      assertThrows(IndexOutOfBoundsException.class, () -> list.add(1, "A"),
          "add(1, A) on empty list (index > size) should throw IndexOutOfBoundsException");

      // Test on non-empty list
      list.addLast("A"); // State: ["A"]
      assertThrows(IndexOutOfBoundsException.class, () -> list.add(-1, "B"),
          "add(-1, B) on size=1 list should throw IndexOutOfBoundsException");
      assertThrows(IndexOutOfBoundsException.class, () -> list.add(2, "B"),
          "add(2, B) on size=1 list (index > size) should throw IndexOutOfBoundsException");
    }

    @Test
    @DisplayName("Operations on empty list throw NoSuchElementException where appropriate")
    void testAccessRemove_OnEmptyList_ThrowsNoSuchElement() {
      assertTrue(list.isEmpty(), "Precondition: list must be empty for this test");
      assertThrows(NoSuchElementException.class, () -> list.first(),
          "first() on empty list should throw NoSuchElementException");
      assertThrows(NoSuchElementException.class, () -> list.last(),
          "last() on empty list should throw NoSuchElementException");
      assertThrows(NoSuchElementException.class, () -> list.removeFirst(),
          "removeFirst() on empty list should throw NoSuchElementException");
      assertThrows(NoSuchElementException.class, () -> list.removeLast(),
          "removeLast() on empty list should throw NoSuchElementException");
    }
  }

  @Nested
  @DisplayName("Array Growth / Resizing")
  class GrowthTests {

    @Test
    @DisplayName("Adding elements beyond initial capacity forces resize")
    void testAdd_BeyondInitialCapacity_ForcesResize() {
      // Assuming default capacity is 10
      int initialCapacity = 10;
      // Fill to capacity using letters A-J
      for (int i = 0; i < initialCapacity; i++) {
        char elementChar = (char) ('A' + i);
        list.addLast(String.valueOf(elementChar));
      }
      assertEquals(initialCapacity, list.size(), "List size should be equal to initial capacity");

      // Add one more element ('K') to trigger resize
      list.addLast("K");
      assertEquals(initialCapacity + 1, list.size(), "Size should increase after adding 11th element");
      assertEquals("A", list.first(), "First element should still be 'A'");
      assertEquals("K", list.last(), "Last element should be the newly added 'K'");
      assertEquals("K", list.get(10), "11th element 'K' should be accessible at index 10");
      // We can't directly assert the new capacity without breaking encapsulation,
      // but the successful add & access implies resizing occurred.
    }

    @Test
    @DisplayName("Growth works correctly when interspersed with removals")
    void testGrowth_InterspersedWithRemovals() {
      // Fill beyond initial capacity (e.g., 12 elements)
      for (int i = 0; i < 12; i++) {
        list.addLast(Character.toString((char) ('A' + i))); // A through L
      }
      assertEquals(12, list.size(), "Size should be 12 initially");

      // Remove some elements
      list.remove(0); // Remove "A" -> ["B", ..., "L"] size 11
      list.remove(5); // Remove "G" (originally at index 6) -> ["B", "C", "D", "E", "F", "H", ...,
                      // "L"] size 10
      list.remove(list.size() - 1); // Remove "L" -> ["B", ..., "F", "H", ..., "K"] size 9

      assertEquals(9, list.size(), "Size should be 9 after removals");
      assertEquals("B", list.first(), "First element should now be 'B'");
      assertEquals("K", list.last(), "Last element should now be 'K'");

      // Add more elements, potentially triggering another resize if capacity shrunk
      // (though typically it doesn't)
      for (int i = 0; i < 5; i++) {
        list.addLast(Character.toString((char) ('Z' - i))); // Add Z, Y, X, W, V
      }
      assertEquals(14, list.size(), "Size should be 14 after adding more elements");
      assertEquals("V", list.last(), "Last added element 'V' should be last");
    }
  }
}