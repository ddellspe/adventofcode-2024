package net.ddellspe.day15;

import java.util.*;
import java.util.stream.Collectors;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day15 {
  private Day15() {}

  private static final Map<String, Point> DIRECTIONS =
      Map.of(
          "^", new Point(0, -1), ">", new Point(1, 0), "<", new Point(-1, 0), "v", new Point(0, 1));

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day15.class);
    Set<Point> border = new HashSet<>();
    Set<Point> boxes = new HashSet<>();
    Point robot = null;
    List<String> movements = new ArrayList<>();
    boolean mvmts = false;
    for (int row = 0; row < lines.size(); row++) {
      String line = lines.get(row);
      if (line.isBlank()) {
        mvmts = true;
        continue;
      }
      if (!mvmts) {
        for (int col = 0; col < line.length(); col++) {
          char c = line.charAt(col);
          if (c == '#') {
            border.add(new Point(col, row));
          } else if (c == 'O') {
            boxes.add(new Point(col, row));
          } else if (c == '@') {
            robot = new Point(col, row);
          }
        }
      } else {
        movements.addAll(Arrays.stream(line.split("")).toList());
      }
    }
    for (String movement : movements) {
      Point direction = DIRECTIONS.get(movement);
      Point newRobot = robot.move(direction);
      if (boxes.contains(newRobot)) {
        Set<Point> pointsToMove = new HashSet<>();
        pointsToMove.add(newRobot);
        Point nextPoint = newRobot.move(direction);
        boolean continueSearch = true;
        while (continueSearch) {
          if (border.contains(nextPoint)) {
            continueSearch = false;
          } else if (boxes.contains(nextPoint)) {
            pointsToMove.add(nextPoint);
            nextPoint = nextPoint.move(direction);
          } else {
            pointsToMove.forEach(boxes::remove);
            boxes.addAll(pointsToMove.stream().map(p -> p.move(direction)).toList());
            robot = newRobot;
            continueSearch = false;
          }
        }
      } else if (!border.contains(newRobot)) {
        robot = newRobot;
      }
    }
    return boxes.stream().mapToLong(p -> p.getX() + p.getY() * 100L).sum();
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day15.class);
    Set<Point> border = new HashSet<>();
    Set<Box> boxes = new HashSet<>();
    Point robot = null;
    List<String> movements = new ArrayList<>();
    // int lastRow = lines.size();
    boolean mvmts = false;
    for (int row = 0; row < lines.size(); row++) {
      String line = lines.get(row);
      if (line.isBlank()) {
        // lastRow = row;
        mvmts = true;
        continue;
      }
      if (!mvmts) {
        for (int col = 0; col < line.length(); col++) {
          char c = line.charAt(col);
          if (c == '#') {
            border.add(new Point(col * 2L, row));
            border.add(new Point(col * 2L + 1L, row));
          } else if (c == 'O') {
            boxes.add(new Box(new Point(col * 2L, row)));
          } else if (c == '@') {
            robot = new Point(col * 2L, row);
          }
        }
      } else {
        movements.addAll(Arrays.stream(line.split("")).toList());
      }
    }
    //    StringBuilder puzzle = new StringBuilder();
    //    for (int row = 0; row < lastRow; row++) {
    //      for (int col = 0; col < lines.getFirst().length() * 2; col++) {
    //        if (border.contains(new Point(col, row))) {
    //          puzzle.append('#');
    //        } else if (boxes.contains(new Box(new Point(col, row)))) {
    //          puzzle.append("[]");
    //          col++;
    //        } else if (new Point(col, row).equals(robot)) {
    //          puzzle.append('@');
    //        } else {
    //          puzzle.append('.');
    //        }
    //      }
    //      puzzle.append('\n');
    //    }
    //    System.out.println(puzzle);
    for (String movement : movements) {
      Point direction = DIRECTIONS.get(movement);
      Point newRobot = robot.move(direction);
      if (boxes.stream().anyMatch(b -> b.includes(newRobot))) {
        Set<Box> boxesToMove = new HashSet<>();
        boxesToMove.add(boxes.stream().filter(b -> b.includes(newRobot)).findFirst().get());
        Set<Point> nextPoints =
            boxesToMove.stream()
                .flatMap(b -> b.candidateMoves(direction).stream())
                .collect(Collectors.toSet());
        boolean continueSearch = true;
        while (continueSearch) {
          Set<Point> candidateNextPoints = nextPoints;
          if (nextPoints.stream().anyMatch(border::contains)) {
            continueSearch = false;
          } else if (boxes.stream()
              .anyMatch(b -> candidateNextPoints.stream().anyMatch(b::includes))) {
            Set<Point> pointsNearBoxes =
                nextPoints.stream()
                    .filter(pt -> boxes.stream().anyMatch(b -> b.includes(pt)))
                    .collect(Collectors.toSet());
            Set<Box> boxesIntersecting =
                boxes.stream()
                    .filter(b -> pointsNearBoxes.stream().anyMatch(b::includes))
                    .collect(Collectors.toSet());
            boxesToMove.addAll(boxesIntersecting);
            nextPoints =
                boxesIntersecting.stream()
                    .flatMap(b -> b.candidateMoves(direction).stream())
                    .collect(Collectors.toSet());
            nextPoints.removeAll(pointsNearBoxes);
          } else {
            boxesToMove.forEach(boxes::remove);
            boxes.addAll(boxesToMove.stream().map(b -> b.shift(direction)).toList());
            robot = newRobot;
            continueSearch = false;
          }
        }
      } else if (!border.contains(newRobot)) {
        robot = newRobot;
      }
    }
    //    StringBuilder puzzle2 = new StringBuilder();
    //    for (int row = 0; row < lastRow; row++) {
    //      for (int col = 0; col < lines.getFirst().length() * 2; col++) {
    //        Point pt = new Point(col, row);
    //        if (border.contains(pt)) {
    //          puzzle2.append('#');
    //        } else if (boxes.stream().anyMatch(b -> b.includes(pt))) {
    //          puzzle2.append("[]");
    //          col++;
    //        } else if (pt.equals(robot)) {
    //          puzzle2.append('@');
    //        } else {
    //          puzzle2.append('.');
    //        }
    //      }
    //      puzzle2.append('\n');
    //    }
    //    System.out.println(puzzle2);
    return boxes.stream().mapToLong(p -> p.getX() + p.getY() * 100L).sum();
  }

  public static class Box {
    Point leftSide;
    Point rightSide;

    public Box(Point coordinate) {
      leftSide = new Point(coordinate.getX(), coordinate.getY());
      rightSide = new Point(coordinate.getX() + 1, coordinate.getY());
    }

    public Box(Point leftSide, Point rightSide) {
      this.leftSide = leftSide;
      this.rightSide = rightSide;
    }

    public Box shift(Point direction) {
      return new Box(leftSide.move(direction), rightSide.move(direction));
    }

    public boolean includes(Point point) {
      return leftSide.equals(point) || rightSide.equals(point);
    }

    public Set<Point> candidateMoves(Point direction) {
      return Set.of(leftSide.move(direction), rightSide.move(direction));
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof Box box)) return false;
      return Objects.equals(leftSide, box.leftSide) && Objects.equals(rightSide, box.rightSide);
    }

    @Override
    public int hashCode() {
      return Objects.hash(leftSide, rightSide);
    }

    public long getX() {
      return leftSide.getX();
    }

    public long getY() {
      return leftSide.getY();
    }
  }
}
