package net.ddellspe.day07;

import java.util.Arrays;
import java.util.List;
import net.ddellspe.utils.InputUtils;

public class Day07 {
  private Day07() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day07.class);
    long sum = 0;
    for (String line : lines) {
      long goal = Long.parseLong(line.split(": ")[0]);
      List<Long> digits =
          Arrays.stream(line.split(": ")[1].split(" ")).mapToLong(Long::parseLong).boxed().toList();
      long combinations = (long) Math.pow(2, digits.size() - 1);
      for (long mask = 0; mask < combinations; mask++) {
        long total = digits.getFirst();
        for (int position = 0; position < digits.size() - 1; position++) {
          if ((mask & (1L << position)) != 0) {
            total += digits.get(position + 1);
          } else {
            total *= digits.get(position + 1);
          }
        }
        if (total == goal) {
          sum += total;
          break;
        }
      }
    }
    return sum;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day07.class);
    long sum = 0;
    for (String line : lines) {
      long goal = Long.parseLong(line.split(": ")[0]);
      List<Long> digits =
          Arrays.stream(line.split(": ")[1].split(" ")).mapToLong(Long::parseLong).boxed().toList();
      long combinations = (long) Math.pow(3, digits.size() - 1);
      for (long mask = 0; mask < combinations; mask++) {
        long total = digits.getFirst();
        for (int position = 0; position < digits.size() - 1; position++) {
          if ((mask / (long) Math.pow(3, position)) % 3 == 0) {
            total += digits.get(position + 1);
          } else if ((mask / (long) Math.pow(3, position)) % 3 == 1) {
            total *= digits.get(position + 1);
          } else {
            total = Long.parseLong(total + String.valueOf(digits.get(position + 1)));
          }
        }
        if (total == goal) {
          sum += total;
          break;
        }
      }
    }
    return sum;
  }
}
