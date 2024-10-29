import java.util.Arrays;

public class ThreadOperation extends Thread {
  private int[][] A;
  private int[][] B;
  private int[][] result;
  private String quadrant;

  public ThreadOperation(int[][] A, int[][] B, String quadrant) {
    this.A = A;
    this.B = B;
    this.quadrant = quadrant;
    this.result = new int[A.length / 2][A[0].length / 2];
  }

  @Override
  public void run() {
    int rowOffset = Integer.parseInt(quadrant.substring(0, 1)) * (A.length / 2);
    int colOffset = Integer.parseInt(quadrant.substring(1, 2)) * (A[0].length / 2);

    for (int i = 0; i < result.length; i++) {
      for (int j = 0; j < result[0].length; j++) {
        result[i][j] = A[i + rowOffset][j + colOffset] + B[i + rowOffset][j + colOffset];
      }
    }
  }

  public int[][] getResult() {
    return result;
  }
}
