package com.sidenis.interview;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

class MyIntHashMapTest {

  private MyIntHashMap map;

  private static Object[] provider() {
    return new Object[]{new int[]{Integer.MAX_VALUE, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5,
        Integer.MIN_VALUE}};
  }

  @BeforeEach
  void init() {
//    сделал capacity меньше, чтобы увеличить количество коллизий
    map = new MyIntHashMap(2);
  }

  @ParameterizedTest
  @DisplayName("Size")
  @MethodSource("provider")
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
      assertEquals(i, map.get(i));
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
      assertEquals(0, map.get(i));
    }
  }

  @Test
  @DisplayName("Remove missing value")
  void testRemoveNotPresentedValue() {
    int i = new Random().nextInt();

    map.remove(i);
    assertEquals(0, map.get(i));
  }

  private void fillMap(int[] ints) {
    for (int i : ints) {
      map.put(i, i);
    }
  }
}