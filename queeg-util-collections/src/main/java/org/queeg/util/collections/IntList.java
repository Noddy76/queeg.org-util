package org.queeg.util.collections;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IntList {
  private final Logger log = LoggerFactory.getLogger(getClass());

  private final int rowLength;
  private final List<int[]> data;
  private int size = 0;

  public IntList(int rowLength) {
    this.rowLength = rowLength;
    this.data = new ArrayList<int[]>();
  }

  /**
   * Add a value to the list
   * 
   * @param value
   *          The value to add
   * @return The offset where the value resides
   */
  public int add(int value) {
    int row = size / rowLength;
    int rowOffset = size % rowLength;

    log.debug("row={}, offset={}", row, rowOffset);

    if (row >= data.size()) {
      data.add(new int[rowLength]);
    }

    int[] rowArray = data.get(row);
    rowArray[rowOffset] = value;

    return size++;
  }

  public int get(int index) {
    if (index >= size) {
      throw new IndexOutOfBoundsException();
    }

    int row = index / rowLength;
    int rowOffset = index % rowLength;

    log.debug("row={}, offset={}", row, rowOffset);

    int[] rowArray = data.get(row);

    return rowArray[rowOffset];
  }
}
