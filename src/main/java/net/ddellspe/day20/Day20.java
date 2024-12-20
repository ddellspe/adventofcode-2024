package net.ddellspe.day20;

import java.util.*;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day20 {
  private Day20() {}

  public static long part1(String filename, long difference) {
    List<String> lines = InputUtils.stringPerLine(filename, Day20.class);
    Set<Point> walls = new HashSet<>();
    Point start = new Point(0, 0);
    Point end = new Point(0, 0);
    for (int y = 0; y < lines.size(); y++) {
      for (int x = 0; x < lines.get(y).length(); x++) {
        if (lines.get(y).charAt(x) == '#') {
          walls.add(new Point(x, y));
        } else if (lines.get(y).charAt(x) == 'S') {
          start = new Point(x, y);
        } else if (lines.get(y).charAt(x) == 'E') {
          end = new Point(x, y);
        }
      }
    }
    LinkedHashMap<Point, Long> path = getPath(walls, start, end);
    long count = 0L;
    for (Map.Entry<Point, Long> entry : path.entrySet()) {
      for (Map.Entry<Point, Long> entry2 : path.entrySet()) {
        if (entry2.getValue() > entry.getValue()) {
          long distanceBetween = entry.getKey().getManhattanDistance(entry2.getKey());
          if (distanceBetween == 2L
              && entry2.getValue() - entry.getValue() - distanceBetween >= difference) {
            count++;
          }
        }
      }
    }
    return count;
  }

  public static LinkedHashMap<Point, Long> getPath(Set<Point> walls, Point start, Point end) {
    LinkedHashMap<Point, Long> path = new LinkedHashMap<>();
    path.put(start, 0L);
    Queue<LinkedHashMap<Point, Long>> queue = new LinkedList<>();
    queue.add(path);
    LinkedHashMap<Point, Long> finalMap = path;
    while (!queue.isEmpty()) {
      LinkedHashMap<Point, Long> current = queue.poll();
      Map.Entry<Point, Long> lastEntry = current.lastEntry();
      if (lastEntry.getKey().equals(end)) {
        finalMap = current;
        break;
      }
      for (Point pt : lastEntry.getKey().getDirectNeighbors()) {
        if (!current.containsKey(pt) && !walls.contains(pt)) {
          LinkedHashMap<Point, Long> newCopy = new LinkedHashMap<>(current);
          newCopy.put(pt, lastEntry.getValue() + 1L);
          queue.add(newCopy);
        }
      }
    }
    return finalMap;
  }

  public static long part2(String filename, long difference) {
    List<String> lines = InputUtils.stringPerLine(filename, Day20.class);
    Set<Point> walls = new HashSet<>();
    Point start = new Point(0, 0);
    Point end = new Point(0, 0);
    for (int y = 0; y < lines.size(); y++) {
      for (int x = 0; x < lines.get(y).length(); x++) {
        if (lines.get(y).charAt(x) == '#') {
          walls.add(new Point(x, y));
        } else if (lines.get(y).charAt(x) == 'S') {
          start = new Point(x, y);
        } else if (lines.get(y).charAt(x) == 'E') {
          end = new Point(x, y);
        }
      }
    }
    LinkedHashMap<Point, Long> path = getPath(walls, start, end);
    long count = 0L;
    for (Map.Entry<Point, Long> entry : path.entrySet()) {
      for (Map.Entry<Point, Long> entry2 : path.entrySet()) {
        long distanceBetween = entry.getKey().getManhattanDistance(entry2.getKey());
        if (distanceBetween <= 20L
            && entry2.getValue() - entry.getValue() - distanceBetween >= difference) {
          count++;
        }
      }
    }
    return count;
  }
}
