/*
Programmer: Zach Nowlin
Date: October 29, 2024
Purpose: Implement concurrent matrix addition using Java multi-threading
*/

/*
One of the goals of multi-threading is to minimize the resource usage, such as memory and processor cycles. Multi-threading accomplishes this by allowing the CPU to efficiently utilize all of its available cores, rather than being bottlenecked by a single-threaded process.
By dividing the larger matrix addition problem into smaller subproblems that can be executed concurrently by separate threads, the overall computation can be completed more quickly, without requiring additional memory to store intermediate results. This leverages the parallelism of modern multi-core processors, allowing the workload to be distributed across available resources.
Additionally, multi-threading can help reduce blocking on I/O operations, as threads can continue processing other tasks while waiting for data to be read or written. This improves overall system throughput and responsiveness compared to a single-threaded approach.
*/

import java.io.IOException;
import java.io.File;
import java.util.Scanner;

public class Main {
  // Utility method to print a 2D array in a formatted manner
  public static void print2dArray(int[][] matrix) {
    for (int[] row : matrix) {
      for (int i = 0; i < row.length; i++) {
        System.out.printf("%4d", row[i]);
      }
      System.out.println();
    }
    System.out.println();
  }

  // Helper method to read a matrix from the input file
  public static int[][] matrixFromFile(int rows, int columns, Scanner fileReader) {
    int[][] matrix = new int[rows][columns];
    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < columns; j++) {
        matrix[i][j] = fileReader.nextInt();
      }
    }
    return matrix;
  }

  public static void main(String[] args) {
    // Check if the filename is provided as a command-line argument
    if (args.length < 1) {
      System.out.println("Please provide the filename as a command-line argument.");
      return;
    }

    String filename = args[0];
    try (Scanner fileReader = new Scanner(new File(filename))) {
      // Read the matrix dimensions and the input matrices from the file
      int rows = fileReader.nextInt();
      int columns = fileReader.nextInt();

      int[][] matrixA = matrixFromFile(rows, columns, fileReader);
      int[][] matrixB = matrixFromFile(rows, columns, fileReader);

      // Create four ThreadOperation objects, one for each quadrant
      ThreadOperation thread0 = new ThreadOperation(matrixA, matrixB, "00");
      ThreadOperation thread1 = new ThreadOperation(matrixA, matrixB, "01");
      ThreadOperation thread2 = new ThreadOperation(matrixA, matrixB, "10");
      ThreadOperation thread3 = new ThreadOperation(matrixA, matrixB, "11");

      // Start the threads
      thread0.start();
      thread1.start();
      thread2.start();
      thread3.start();

      // Wait for the threads to complete
      try {
        thread0.join();
        thread1.join();
        thread2.join();
        thread3.join();
      } catch (InterruptedException e) {
        e.printStackTrace();
      }

      // Combine the results from the four threads into the final result matrix
      int[][] resultMatrix = new int[rows][columns];
      resultMatrix = thread0.getResult();
      resultMatrix = thread1.getResult();
      resultMatrix = thread2.getResult();
      resultMatrix = thread3.getResult();

      // Print the result matrix
      System.out.println("Result Matrix:");
      print2dArray(resultMatrix);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
