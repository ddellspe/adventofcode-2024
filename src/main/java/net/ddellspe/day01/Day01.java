package net.ddellspe.day01;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day01 {
  private Day01() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day01.class);
    List<Integer> leftList = new ArrayList<>();
    List<Integer> rightList = new ArrayList<>();
    for (String line : lines) {
      leftList.add(Integer.parseInt(line.split("[ ]+")[0]));
      rightList.add(Integer.parseInt(line.split("[ ]+")[1]));
    }
    leftList = leftList.stream().sorted().toList();
    rightList = rightList.stream().sorted().toList();
    long sum = 0L;
    for (int i = 0; i < leftList.size(); i++) {
      sum += Math.abs(rightList.get(i) - leftList.get(i));
    }
    return sum;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day01.class);
    List<Integer> leftList = new ArrayList<>();
    List<Integer> rightList = new ArrayList<>();
    for (String line : lines) {
      leftList.add(Integer.parseInt(line.split("[ ]+")[0]));
      rightList.add(Integer.parseInt(line.split("[ ]+")[1]));
    }
    leftList = leftList.stream().sorted().toList();
    rightList = rightList.stream().sorted().toList();
    List<Integer> finalRightList = rightList;
    Map<Long, Long> rightListCount =
        rightList.stream()
            .distinct()
            .map(
                v ->
                    new Point(v, finalRightList.stream().filter(i -> Objects.equals(i, v)).count()))
            .collect(Collectors.toMap(Point::getX, Point::getY));
    return leftList.stream().mapToLong(v -> v * rightListCount.getOrDefault((long) v, 0L)).sum();
  }
}
