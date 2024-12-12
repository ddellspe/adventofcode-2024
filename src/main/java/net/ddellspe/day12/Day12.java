package net.ddellspe.day12;

import java.util.*;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day12 {
  private Day12() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day12.class);
    Map<Point, String> data = new HashMap<>();
    int yMax = lines.size();
    int xMax = lines.getFirst().length();
    for (int y = 0; y < yMax; y++) {
      for (int x = 0; x < xMax; x++) {
        data.put(new Point(x, y), lines.get(y).charAt(x) + "");
      }
    }
    long sum = 0;
    Set<Point> trackedPoints = new HashSet<>();
    for (int y = 0; y < yMax; y++) {
      for (int x = 0; x < xMax; x++) {
        Point candidatePoint = new Point(x, y);
        if (trackedPoints.contains(candidatePoint)) {
          continue;
        }
        Set<Point> region = getRegion(new Point(x, y), data);
        long area = region.size();
        long perimeter =
            region.stream()
                .mapToLong(
                    p -> p.getDirectNeighbors().stream().filter(n -> !region.contains(n)).count())
                .sum();
        sum += (area * perimeter);
        trackedPoints.addAll(region);
      }
    }
    return sum;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day12.class);
    Map<Point, String> data = new HashMap<>();
    int yMax = lines.size();
    int xMax = lines.getFirst().length();
    for (int y = 0; y < yMax; y++) {
      for (int x = 0; x < xMax; x++) {
        data.put(new Point(x, y), lines.get(y).charAt(x) + "");
      }
    }
    long sum = 0;
    Set<Point> trackedPoints = new HashSet<>();
    for (int y = 0; y < yMax; y++) {
      for (int x = 0; x < xMax; x++) {
        Point candidatePoint = new Point(x, y);
        if (trackedPoints.contains(candidatePoint)) {
          continue;
        }
        Set<Point> region = getRegion(new Point(x, y), data);
        long area = region.size();
        long edges = region.stream().mapToLong(p -> getCornerCount(p, region)).sum();
        sum += (area * edges);
        trackedPoints.addAll(region);
      }
    }
    return sum;
  }

  private static long getCornerCount(Point p, Set<Point> region) {
    long corners = 0L;
    if (region.contains(p.move(new Point(0, -1)))) {
      if (region.contains(p.move(new Point(-1, 0)))
          && !region.contains(p.move(new Point(-1, -1)))) {
        corners++;
      }
      if (region.contains(p.move(new Point(1, 0))) && !region.contains(p.move(new Point(1, -1)))) {
        corners++;
      }
    } else {
      if (!region.contains(p.move(new Point(-1, 0)))) {
        corners++;
      }
      if (!region.contains(p.move(new Point(1, 0)))) {
        corners++;
      }
    }
    if (region.contains(p.move(new Point(0, 1)))) {
      if (region.contains(p.move(new Point(-1, 0))) && !region.contains(p.move(new Point(-1, 1)))) {
        corners++;
      }
      if (region.contains(p.move(new Point(1, 0))) && !region.contains(p.move(new Point(1, 1)))) {
        corners++;
      }
    } else {
      if (!region.contains(p.move(new Point(-1, 0)))) {
        corners++;
      }
      if (!region.contains(p.move(new Point(1, 0)))) {
        corners++;
      }
    }
    return corners;
  }

  private static Set<Point> getRegion(Point startingPoint, Map<Point, String> potentialPoints) {
    Set<Point> region = new HashSet<>();
    Queue<Point> queue = new LinkedList<>();
    String regionName = potentialPoints.get(startingPoint);
    queue.add(startingPoint);
    while (!queue.isEmpty()) {
      Point point = queue.poll();
      if (region.contains(point) || !potentialPoints.containsKey(point)) {
        continue;
      }
      if (potentialPoints.get(point).equals(regionName)) {
        region.add(point);
        queue.addAll(point.getDirectNeighbors());
      }
    }
    return region;
  }
}
