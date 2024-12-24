package net.ddellspe.day13;

import java.util.ArrayList;
import java.util.List;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day13 {
  private Day13() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day13.class);
    List<Calculation> calculations = new ArrayList<>();
    int aXMove = 0;
    int aYMove = 0;
    int bXMove = 0;
    int bYMove = 0;
    Point prize = null;
    long sum = 0L;
    for (String line : lines) {
      if (line.startsWith("Button A:")) {
        aXMove = Integer.parseInt(line.split("[ ,]+")[2].split("\\+")[1]);
        aYMove = Integer.parseInt(line.split("[ ,]+")[3].split("\\+")[1]);
      } else if (line.startsWith("Button B:")) {
        bXMove = Integer.parseInt(line.split("[ ,]+")[2].split("\\+")[1]);
        bYMove = Integer.parseInt(line.split("[ ,]+")[3].split("\\+")[1]);
      } else if (line.startsWith("Prize: ")) {
        int pX = Integer.parseInt(line.split("[ ,]+")[1].split("=")[1]);
        int pY = Integer.parseInt(line.split("[ ,]+")[2].split("=")[1]);
        prize = new Point(pX, pY);
      } else {
        calculations.add(new Calculation(aXMove, aYMove, bXMove, bYMove, prize));
      }
    }
    calculations.add(new Calculation(aXMove, aYMove, bXMove, bYMove, prize));
    for (Calculation calculation : calculations) {
      double[] output = calculation.gaussianElimination(0L);

      long a = Math.round(output[0]);
      long b = Math.round(output[1]);
      if (calculation.prize.equals(
          new Point(0, 0)
              .move(
                  new Point(
                      a * calculation.aXMove + b * calculation.bXMove,
                      a * calculation.aYMove + b * calculation.bYMove)))) {
        sum += (a * 3L + b);
      }
    }
    return sum;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day13.class);
    List<Calculation> calculations = new ArrayList<>();
    int aXMove = 0;
    int aYMove = 0;
    int bXMove = 0;
    int bYMove = 0;
    Point prize = null;
    long sum = 0L;
    for (String line : lines) {
      if (line.startsWith("Button A:")) {
        aXMove = Integer.parseInt(line.split("[ ,]+")[2].split("\\+")[1]);
        aYMove = Integer.parseInt(line.split("[ ,]+")[3].split("\\+")[1]);
      } else if (line.startsWith("Button B:")) {
        bXMove = Integer.parseInt(line.split("[ ,]+")[2].split("\\+")[1]);
        bYMove = Integer.parseInt(line.split("[ ,]+")[3].split("\\+")[1]);
      } else if (line.startsWith("Prize: ")) {
        int pX = Integer.parseInt(line.split("[ ,]+")[1].split("=")[1]);
        int pY = Integer.parseInt(line.split("[ ,]+")[2].split("=")[1]);
        prize = new Point(pX, pY);
      } else {
        calculations.add(new Calculation(aXMove, aYMove, bXMove, bYMove, prize));
      }
    }
    calculations.add(new Calculation(aXMove, aYMove, bXMove, bYMove, prize));
    for (Calculation calculation : calculations) {
      double[] output = calculation.gaussianElimination(10000000000000L);

      long a = Math.round(output[0]);
      long b = Math.round(output[1]);
      if (calculation
          .prize
          .move(new Point(10000000000000L, 10000000000000L))
          .equals(
              new Point(0, 0)
                  .move(
                      new Point(
                          a * calculation.aXMove + b * calculation.bXMove,
                          a * calculation.aYMove + b * calculation.bYMove)))) {
        sum += (a * 3L + b);
      }
    }
    return sum;
  }

  record Calculation(int aXMove, int aYMove, int bXMove, int bYMove, Point prize) {
    private double[] gaussianElimination(long prizeAddition) {
      final double[][] coefficients = new double[][] {{aXMove, bXMove}, {aYMove, bYMove}};
      final double[] solution =
          new double[] {prize.getX() + prizeAddition, prize.getY() + prizeAddition};
      for (int i = 0; i < 2; i++) {
        // Select pivot
        final double pivot = coefficients[i][i];
        // Normalize row i
        for (int j = 0; j < 2; j++) {
          coefficients[i][j] = coefficients[i][j] / pivot;
        }
        solution[i] = solution[i] / pivot;
        // Sweep using row i
        for (int k = 0; k < 2; k++) {
          if (k != i) {
            final double factor = coefficients[k][i];
            for (int j = 0; j < 2; j++) {
              coefficients[k][j] = coefficients[k][j] - factor * coefficients[i][j];
            }
            solution[k] = solution[k] - factor * solution[i];
          }
        }
      }
      return solution;
    }
  }
}
