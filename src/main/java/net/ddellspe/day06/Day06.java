package net.ddellspe.day06;

import java.util.*;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day06 {
  private Day06() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day06.class);
    Point guard = new Point(0, 0);
    Point direction = new Point(0, 0);
    Set<Point> obstructions = new HashSet<>();
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (line.charAt(x) == '#') {
          obstructions.add(new Point(x, y));
        } else if (line.charAt(x) != '.') {
          guard = new Point(x, y);
          direction = new Point(0, -1);
        }
      }
    }
    Set<Point> visited = new HashSet<>();
    int xMin = 0, yMin = 0, xMax = lines.getFirst().length(), yMax = lines.size();
    while (guard.getX() >= xMin
        && guard.getX() < xMax
        && guard.getY() >= yMin
        && guard.getY() < yMax) {
      Point potential = guard.move(direction);
      if (!obstructions.contains(potential)) {
        visited.add(guard);
        guard = potential;
      } else {
        direction = rotateDirection(direction);
      }
    }
    return visited.size();
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day06.class);
    Point origGuard = new Point(0, 0);
    Point origDirection = new Point(0, 0);
    Set<Point> obstructions = new HashSet<>();
    for (int y = 0; y < lines.size(); y++) {
      String line = lines.get(y);
      for (int x = 0; x < line.length(); x++) {
        if (line.charAt(x) == '#') {
          obstructions.add(new Point(x, y));
        } else if (line.charAt(x) != '.') {
          origGuard = new Point(x, y);
          origDirection = new Point(0, -1);
        }
      }
    }
    Point guard = origGuard;
    Point direction = origDirection;
    Set<Point> visited = new HashSet<>();
    int xMin = 0, yMin = 0, xMax = lines.getFirst().length(), yMax = lines.size();
    while (guard.getX() >= xMin
        && guard.getX() < xMax
        && guard.getY() >= yMin
        && guard.getY() < yMax) {
      Point potential = guard.move(direction);
      if (!obstructions.contains(potential)) {
        visited.add(guard);
        guard = potential;
      } else {
        direction = rotateDirection(direction);
      }
    }
    Set<Point> traps = new HashSet<>();
    for (Point newObstruction : visited) {
      if (producesCycle(
          origGuard, origDirection, obstructions, xMin, xMax, yMin, yMax, newObstruction)) {
        traps.add(newObstruction);
      }
    }
    return traps.size();
  }

  private static Point rotateDirection(Point direction) {
    if (direction.equals(new Point(1, 0))) {
      return new Point(0, 1);
    } else if (direction.equals(new Point(-1, 0))) {
      return new Point(0, -1);
    } else if (direction.equals(new Point(0, 1))) {
      return new Point(-1, 0);
    } else {
      return new Point(1, 0);
    }
  }

  private static boolean producesCycle(
      Point firstPoint,
      Point movementDirection,
      Set<Point> obstructions,
      int xMin,
      int xMax,
      int yMin,
      int yMax,
      Point potentialBarrier) {
    Point position = firstPoint.move(new Point(0, 0));
    Point direction = movementDirection.move(new Point(0, 0));
    Set<Point> obstructionCopy = new HashSet<>(obstructions);
    obstructionCopy.add(potentialBarrier);
    List<Point> turns = new ArrayList<>();
    while (position.getX() >= xMin
        && position.getX() < xMax
        && position.getY() >= yMin
        && position.getY() < yMax) {
      Point nextPosition = position.move(direction);
      if (obstructionCopy.contains(nextPosition)) {
        if (turns.contains(position)) {
          return true;
        }

        boolean foundNext = false;
        while (!foundNext) {
          direction = rotateDirection(direction);
          nextPosition = position.move(direction);
          if (!obstructionCopy.contains(nextPosition)) {
            foundNext = true;
            turns.add(position);
          }
        }
      }
      position = nextPosition;
    }
    return false;
  }
}
