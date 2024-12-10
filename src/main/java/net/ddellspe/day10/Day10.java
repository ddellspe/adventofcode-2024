package net.ddellspe.day10;

import java.util.*;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day10 {
  private Day10() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day10.class);
    Map<Point, Integer> topology = new HashMap<>();
    for (int y = 0; y < lines.size(); y++) {
      for (int x = 0; x < lines.get(y).length(); x++) {
        topology.put(new Point(x, y), Integer.parseInt(lines.get(y).charAt(x) + ""));
      }
    }
    long sum = 0;
    for (Point origin :
        topology.entrySet().stream()
            .filter(e -> e.getValue() == 0)
            .map(Map.Entry::getKey)
            .toList()) {
      sum += getScoringPoints(origin, topology).size();
    }
    return sum;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day10.class);
    Map<Point, Integer> topology = new HashMap<>();
    for (int y = 0; y < lines.size(); y++) {
      for (int x = 0; x < lines.get(y).length(); x++) {
        topology.put(new Point(x, y), Integer.parseInt(lines.get(y).charAt(x) + ""));
      }
    }
    long sum = 0;
    for (Point origin :
        topology.entrySet().stream()
            .filter(e -> e.getValue() == 0)
            .map(Map.Entry::getKey)
            .toList()) {
      sum += getRank(origin, topology, getScoringPoints(origin, topology));
    }
    return sum;
  }

  private static Set<Point> getScoringPoints(Point origin, Map<Point, Integer> topology) {
    Set<Point> points = new HashSet<>();
    Queue<Point> queue = new LinkedList<>();
    queue.add(origin);
    while (!queue.isEmpty()) {
      Point curr = queue.poll();
      if (topology.containsKey(curr)) {
        int value = topology.get(curr);
        if (value == 9) {
          points.add(curr);
        } else {
          for (Point pt : curr.getDirectNeighbors()) {
            if (topology.containsKey(pt) && topology.get(pt) == value + 1) {
              queue.add(pt);
            }
          }
        }
      }
    }
    return points;
  }

  private static long getRank(Point origin, Map<Point, Integer> topology, Set<Point> destinations) {
    Set<List<Point>> paths = new HashSet<>();
    Queue<List<Point>> queue = new LinkedList<>();
    ArrayList<Point> originalList = new ArrayList<>();
    originalList.add(origin);
    queue.add(originalList);
    while (!queue.isEmpty()) {
      List<Point> curr = queue.poll();
      Point lastPoint = curr.getLast();
      if (topology.containsKey(lastPoint)) {
        int value = topology.get(lastPoint);
        if (value == 9) {
          paths.add(curr);
        } else {
          for (Point pt : lastPoint.getDirectNeighbors()) {
            if (topology.containsKey(pt) && topology.get(pt) == value + 1) {
              List<Point> currCopy = new ArrayList<Point>(curr.stream().toList());
              currCopy.add(pt);
              queue.add(currCopy);
            }
          }
        }
      }
    }
    return paths.size();
  }
}
