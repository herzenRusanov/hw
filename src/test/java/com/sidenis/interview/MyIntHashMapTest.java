package com.sidenis.interview;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class MyIntHashMapTest {

  private MyIntHashMap map;

  private static Object[] provider() {
    int[] arr = new int[100_000];
    Random random = new Random();
    for (int i = 0; i < arr.length; i++) {
      arr[i] = random.nextInt();
    }

    return new Object[]{arr};
  }

  private static Object[] uniqueProvider() {
    Set<Integer> set = new HashSet<>();
    Random random = new Random();
    for (int i = 0; i < 100_000; i++) {
      set.add(random.nextInt());
    }
    int[] arr = new int[set.size()];
    int j = 0;
    for (Integer i : set) {
      arr[j++] = i;
    }

    return new Object[]{arr};
  }

  @BeforeEach
  void init() {
    map = new MyIntHashMap(2);
  }

  @ParameterizedTest
  @DisplayName("Size")
  @MethodSource("uniqueProvider")
  void testSize(int[] ints) {
    fillMap(ints);

    assertEquals(ints.length, map.size());

    fillMap(ints);

    assertEquals(ints.length, map.size());

    for (int i : ints) {
      map.remove(i);
    }

    assertEquals(0, map.size());
  }

  @ParameterizedTest
  @DisplayName("Get value")
  @MethodSource("provider")
  void testGet(int[] ints) {
    fillMap(ints);

    for (int i : ints) {
      int actual = map.get(i);
      assertEquals(i, actual, () -> "Should return " + i + " but return " + actual);
    }
  }

  @Test
  @DisplayName("Get missing value")
  void testGetNotPresentedValue() {
    assertEquals(0, map.get(new Random().nextInt()));
  }

  @ParameterizedTest
  @MethodSource("provider")
  @DisplayName("Remove value")
  void testRemove(int[] ints) {
    fillMap(ints);

    for (int i : ints) {
      map.remove(i);
      int actual = map.get(i);
      assertEquals(0, actual, () -> "Should return 0 but return " + actual);
    }
  }

  @Test
  @DisplayName("Remove missing value")
  void testRemoveNotPresentedValue() {
    int i = new Random().nextInt();

    map.remove(i);
    int actual = map.get(i);
    assertEquals(0, actual, () -> "Should return 0 but return " + actual);
  }

  private void fillMap(int[] ints) {
    for (int i : ints) {
      map.put(i, i);
    }
  }
}