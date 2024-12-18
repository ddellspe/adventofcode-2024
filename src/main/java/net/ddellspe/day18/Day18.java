package net.ddellspe.day18;

import java.util.*;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day18 {
  private Day18() {}

  public static long part1(String filename, Point end, int bytesToRead) {
    List<String> lines = InputUtils.stringPerLine(filename, Day18.class);
    Set<Point> points = new HashSet<>();
    for (String line : lines) {
      points.add(
          new Point(Integer.parseInt(line.split(",")[0]), Integer.parseInt(line.split(",")[1])));
      if (points.size() == bytesToRead) {
        break;
      }
    }
    return processPuzzle(points, end);
  }

  public static Point part2(String filename, Point end) {
    List<String> lines = InputUtils.stringPerLine(filename, Day18.class);
    List<Point> points = new ArrayList<>();
    for (String line : lines) {
      points.add(
          new Point(Integer.parseInt(line.split(",")[0]), Integer.parseInt(line.split(",")[1])));
    }
    Point finalPoint = null;
    for (int i = 1; i < points.size(); i++) {
      if (processPuzzle(new HashSet<>(points.subList(0, i)), end) == null) {
        finalPoint = points.get(i - 1);
        break;
      }
    }
    return finalPoint;
  }

  public static Long processPuzzle(Set<Point> points, Point end) {
    Map<Point, Long> minAtPoint = new HashMap<>();
    Set<Point> seen = new HashSet<>();
    Queue<State> queue = new LinkedList<>();
    queue.add(new State(new Point(0, 0), 0L));
    seen.add(new Point(0, 0));
    while (!queue.isEmpty()) {
      State state = queue.poll();
      Point point = state.point;
      minAtPoint.put(point, state.score);
      if (point.equals(end)) {
        break;
      }
      for (Point pt :
          List.of(
              new Point(point.getX(), point.getY() + 1),
              new Point(point.getX(), point.getY() - 1),
              new Point(point.getX() + 1, point.getY()),
              new Point(point.getX() - 1, point.getY()))) {
        if (!seen.contains(pt)
            && !points.contains(pt)
            && pt.getX() >= 0
            && pt.getX() <= end.getX()
            && pt.getY() >= 0
            && pt.getY() <= end.getY()) {
          queue.add(new State(pt, state.score + 1));
          seen.add(pt);
        }
      }
    }
    return minAtPoint.get(end);
  }

  record State(Point point, Long score) {}
}
