package net.ddellspe.day16;

import java.util.*;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day16 {
  private Day16() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day16.class);
    Set<Point> walls = new HashSet<>();
    Point start = null;
    Point end = null;
    for (int y = 0; y < lines.size(); y++) {
      for (int x = 0; x < lines.get(y).length(); x++) {
        Point pt = new Point(x, y);
        switch (lines.get(y).charAt(x)) {
          case '#':
            walls.add(pt);
            break;
          case 'S':
            start = pt;
            break;
          case 'E':
            end = pt;
            break;
        }
      }
    }
    Point finalEnd = end;
    PriorityQueue<State> queue =
        new PriorityQueue<>(Comparator.comparing(s -> finalEnd.getManhattanDistance(s.pt)));
    Map<Point, Long> minValues = new HashMap<>();
    minValues.put(start, 0L);
    minValues.put(end, Long.MAX_VALUE);
    queue.add(new State(start, 0L, new Point(1, 0)));
    while (!queue.isEmpty()) {
      State state = queue.poll();
      if (walls.contains(state.pt)) {
        continue;
      }
      if (state.price < minValues.getOrDefault(state.pt, Long.MAX_VALUE)) {
        minValues.put(state.pt, state.price);
        if (state.pt.equals(end)) {
          continue;
        }
      }
      Point pt = state.pt;
      Point direction = state.direction;
      long price = state.price;
      State newState = new State(pt.move(direction), price + 1, direction);
      if (minValues.getOrDefault(newState.pt, Long.MAX_VALUE) > newState.price) {
        queue.add(newState);
      }
      newState = new State(pt.move(turnLeft(direction)), price + 1001, turnLeft(direction));
      if (minValues.getOrDefault(newState.pt, Long.MAX_VALUE) > newState.price) {
        queue.add(newState);
      }
      newState = new State(pt.move(turnRight(direction)), price + 1001, turnRight(direction));
      if (minValues.getOrDefault(newState.pt, Long.MAX_VALUE) > newState.price) {
        queue.add(newState);
      }
    }
    return minValues.get(end);
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day16.class);
    Set<Point> walls = new HashSet<>();
    Point start = null;
    Point end = null;
    for (int y = 0; y < lines.size(); y++) {
      for (int x = 0; x < lines.get(y).length(); x++) {
        Point pt = new Point(x, y);
        switch (lines.get(y).charAt(x)) {
          case '#':
            walls.add(pt);
            break;
          case 'S':
            start = pt;
            break;
          case 'E':
            end = pt;
            break;
        }
      }
    }
    LinkedList<State> queue = new LinkedList<>();
    Map<Point, Long> minValues = new HashMap<>();
    Set<State> finalStates = new HashSet<>();
    minValues.put(start, 0L);
    minValues.put(end, Long.MAX_VALUE);
    queue.add(new State(start, 0L, new Point(1, 0), Set.of(start)));
    while (!queue.isEmpty()) {
      State state = queue.poll();
      if (walls.contains(state.pt)) {
        continue;
      }
      if (state.price <= minValues.getOrDefault(state.pt, Long.MAX_VALUE)) {
        long previousPrice = minValues.getOrDefault(state.pt, Long.MAX_VALUE);
        minValues.put(state.pt, state.price);
        if (state.pt.equals(end)) {
          if (state.price < previousPrice) {
            finalStates = new HashSet<>();
          }
          finalStates.add(state);
          continue;
        }
      }
      Point pt = state.pt;
      Point direction = state.direction;
      long price = state.price;
      Set<Point> path = new HashSet<>(state.path);
      path.add(pt.move(direction));
      State newState = new State(pt.move(direction), price + 1, direction, path);
      if (minValues.getOrDefault(newState.pt, Long.MAX_VALUE) > newState.price) {
        queue.add(newState);
      }
      path = new HashSet<>(state.path);
      path.add(pt.move(turnLeft(direction)));
      newState = new State(pt.move(turnLeft(direction)), price + 1001, turnLeft(direction), path);
      if (minValues.getOrDefault(newState.pt, Long.MAX_VALUE) > newState.price) {
        queue.add(newState);
      }
      path = new HashSet<>(state.path);
      path.add(pt.move(turnRight(direction)));
      newState = new State(pt.move(turnRight(direction)), price + 1001, turnRight(direction), path);
      if (minValues.getOrDefault(newState.pt, Long.MAX_VALUE) > newState.price) {
        queue.add(newState);
      }
    }
    return finalStates.stream().flatMap(s -> s.path.stream()).distinct().count();
  }

  public static class State {
    Point pt;
    long price;
    Point direction;
    Set<Point> path;

    State(Point pt, long price, Point direction) {
      this(pt, price, direction, Set.of(pt));
    }

    State(Point pt, long price, Point direction, Set<Point> path) {
      this.pt = pt;
      this.price = price;
      this.direction = direction;
      this.path = path;
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof State state)) return false;
      return price == state.price
          && Objects.equals(pt, state.pt)
          && Objects.equals(direction, state.direction)
          && Objects.equals(path, state.path);
    }

    @Override
    public int hashCode() {
      return Objects.hash(pt, price, direction, path);
    }
  }

  private static Point turnRight(Point direction) {
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

  private static Point turnLeft(Point direction) {
    if (direction.equals(new Point(1, 0))) {
      return new Point(0, -1);
    } else if (direction.equals(new Point(-1, 0))) {
      return new Point(0, 1);
    } else if (direction.equals(new Point(0, 1))) {
      return new Point(1, 0);
    } else {
      return new Point(-1, 0);
    }
  }
}
