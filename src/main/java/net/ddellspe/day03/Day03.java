package net.ddellspe.day03;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day03 {
  private Day03() {}

  private static final Pattern MUL_PATTERN = Pattern.compile("mul\\((\\d{1,3}),(\\d{1,3})\\)");
  private static final Pattern DO_PATTERN = Pattern.compile("do\\(\\)");
  private static final Pattern DONT_PATTERN = Pattern.compile("don't\\(\\)");

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day03.class);
    String data = String.join("", lines);
    Matcher matches = MUL_PATTERN.matcher(data);
    long sum = 0L;
    while (matches.find()) {
      Long firstNumber = Long.parseLong(matches.group(1));
      Long secondNumber = Long.parseLong(matches.group(2));
      sum += (firstNumber * secondNumber);
    }
    return sum;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day03.class);
    String data = String.join("", lines);
    List<Integer> dontStarts =
        DONT_PATTERN.matcher(data).results().map(MatchResult::start).toList();
    List<Integer> doStarts = DO_PATTERN.matcher(data).results().map(MatchResult::start).toList();
    int index = 0;
    List<Point> noGoZones = new ArrayList<>();
    for (final int start : dontStarts) {
      if (start < index) {
        continue;
      }
      Optional<Integer> end = doStarts.stream().filter(st -> st >= start).findFirst();
      int endPt = end.orElseGet(() -> (data.length() + 1));
      noGoZones.add(new Point(start, endPt));
      index = endPt;
    }
    Matcher matches = MUL_PATTERN.matcher(data);
    long sum = 0L;
    while (matches.find()) {
      if (noGoZones.stream()
          .anyMatch(p -> matches.start() > p.getX() && matches.start() < p.getY())) {
        continue;
      }
      Long firstNumber = Long.parseLong(matches.group(1));
      Long secondNumber = Long.parseLong(matches.group(2));
      sum += (firstNumber * secondNumber);
    }
    return sum;
  }
}
