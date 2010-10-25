package org.queeg.util.collections;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class IntListTest {
  @Test
  public void addGetTest() {
    int i;
    IntList list = new IntList(10);
    for (i = 0; i < 100; i++) {
      list.add(i);
    }
    for (i = 0; i < 100; i++) {
      assertEquals(i, list.get(i));
    }
  }
  
  @Test(expected=IndexOutOfBoundsException.class)
  public void indexOutOfBoundsTest() throws Exception {
    IntList list = new IntList(10);
    list.add(0);
    list.add(1);
    list.get(2);
  }
}
