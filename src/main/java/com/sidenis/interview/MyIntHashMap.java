package com.sidenis.interview;

public class MyIntHashMap {

  private final static int DEFAULT_CAPACITY = 16;

  private int capacity, size;

  private Node[] nodes;

  public MyIntHashMap() {
    this(DEFAULT_CAPACITY);
  }

  public MyIntHashMap(int capacity) {
    if (capacity < 1) {
      throw new IllegalArgumentException("Capacity can't be less then 1");
    }

    this.capacity = capacity;
    nodes = new Node[capacity];
  }

  public void put(int key, int value) {
    int hash = hash(key);
    Node node = nodes[hash];

    if (node == null) {
      node = new Node(key, value);

      nodes[hash] = node;
      size++;
    } else {
      while (node.next != null) {
        if (node.key == key) {
          node.value = value;

          return;
        }

        node = node.next;
      }

      if (node.key == key) {
        node.value = value;
      } else {
        node.next = new Node(key, value);
        size++;
      }
    }

//    тут можно сделать ресайз
  }

  //  По скольку тут примитивы, то если ничего не находим возвращаю 0. В целом можно изменить
  //  возвращаемый тип на Integer и возвращать null, но тогда возможно NPE при автобоксинге.
  //  (хотя по мне лучше NPE чем 0)
  public int get(int key) {
    if (size == 0) {
      return 0;
    }


    Node node = nodes[hash(key)];

    while (node != null) {
      if (node.key == key) {

        return node.value;
      }

      node = node.next;
    }

    return 0;
  }

  public void remove(int key) {
    if (size == 0) {
      return;
    }

    int hash = hash(key);

    if (nodes[hash] != null) {
      Node previous = null;
      Node current = nodes[hash];

      while (current != null) {
        if (current.key == key) {
          if (previous == null) {
            nodes[hash] = nodes[hash].next;
            size--;

            return;
          } else {
            previous.next = current.next;
            size--;

            return;
          }
        }

        previous = current;
        current = current.next;
      }
    }
  }

  private int hash(int key) {
    return Math.abs(key) % nodes.length;
  }

  public int size() {
    return size;
  }

  static class Node {

    private final int key;
    private int value;
    private Node next;

    Node(int key, int value) {
      this.key = key;
      this.value = value;
    }
  }
}
