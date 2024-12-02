package net.ddellspe.day02;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import net.ddellspe.utils.InputUtils;

public class Day02 {
  private Day02() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day02.class);
    long safeReports = 0;
    for (String report : lines) {
      List<Integer> levels =
          Arrays.stream(report.split(" ")).mapToInt(Integer::parseInt).boxed().toList();
      List<Integer> differences =
          IntStream.range(0, levels.size() - 1)
              .map(i -> levels.get(i + 1) - levels.get(i))
              .boxed()
              .toList();
      if (determineSafeReport(differences)) {
        safeReports++;
      }
    }
    return safeReports;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day02.class);
    long safeReports = 0;
    for (String report : lines) {
      List<Integer> levels =
          Arrays.stream(report.split(" ")).mapToInt(Integer::parseInt).boxed().toList();
      List<Integer> differences =
          IntStream.range(0, levels.size() - 1)
              .map(i -> levels.get(i + 1) - levels.get(i))
              .boxed()
              .toList();
      if (determineSafeReport(differences)) {
        safeReports++;
        continue;
      }
      for (int skip = 0; skip < levels.size(); skip++) {
        int finalSkip = skip;
        List<Integer> levels2 =
            IntStream.range(0, levels.size())
                .filter(i -> i != finalSkip)
                .map(levels::get)
                .boxed()
                .toList();
        List<Integer> differences2 =
            IntStream.range(0, levels2.size() - 1)
                .map(i -> levels2.get(i + 1) - levels2.get(i))
                .boxed()
                .toList();
        if (determineSafeReport(differences2)) {
          safeReports++;
          break;
        }
      }
    }
    return safeReports;
  }

  private static boolean determineSafeReport(List<Integer> differences) {
    if (differences.stream().filter(v -> List.of(1, 2, 3).contains(v)).count()
        == differences.size()) {
      return true;
    }
    return differences.stream().filter(v -> List.of(-1, -2, -3).contains(v)).count()
        == differences.size();
  }
}
