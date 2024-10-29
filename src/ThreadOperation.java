/*
Programmer: Zach Nowlin
Date: October 29, 2024
Purpose: Implement a thread for concurrent matrix addition
*/

import java.util.Arrays;

public class ThreadOperation extends Thread {
  // Stores the input matrices A and B
  private int[][] A;
  private int[][] B;
  // Stores the result matrix for the current quadrant
  private int[][] result;
  // Stores the quadrant identifier (e.g., "00", "01", "10", "11")
  private String quadrant;

  // Constructor that takes in the input matrices and quadrant identifier
  public ThreadOperation(int[][] A, int[][] B, String quadrant) {
    this.A = A;
    this.B = B;
    this.quadrant = quadrant;
    // Initialize the result matrix with the appropriate size
    this.result = new int[A.length / 2][A[0].length / 2];
  }

  // Overrides the run() method of the Thread class
  @Override
  public void run() {
    // Calculate the row and column offsets based on the quadrant identifier
    int rowOffset = Integer.parseInt(quadrant.substring(0, 1)) * (A.length / 2);
    int colOffset = Integer.parseInt(quadrant.substring(1, 2)) * (A[0].length / 2);

    // Iterate through the elements of the assigned quadrant and perform the
    // addition
    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[0].length; j++) {
        result[i][j] = A[i + rowOffset][j + colOffset] + B[i + rowOffset][j + colOffset];
      }
    }
  }

  // Getter method to retrieve the computed result matrix
  public int[][] getResult() {
    return result;
  }
}
