package net.ddellspe.day14;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day14 {
  private Day14() {}

  public static long part1(String filename, int width, int height) {
    List<String> lines = InputUtils.stringPerLine(filename, Day14.class);
    List<Robot> robots = new ArrayList<>();
    for (String line : lines) {
      String[] parts = line.split(" ");
      String[] pCoords = parts[0].split("=")[1].split(",");
      String[] vCoords = parts[1].split("=")[1].split(",");
      Point p = new Point(Integer.parseInt(pCoords[0]), Integer.parseInt(pCoords[1]));
      Point v = new Point(Integer.parseInt(vCoords[0]), Integer.parseInt(vCoords[1]));
      robots.add(new Robot(p, v));
    }
    for (int iteration = 0; iteration < 100; iteration++) {
      for (Robot robot : robots) {
        robot.move(width, height);
      }
    }
    long quad1 = 0L, quad2 = 0L, quad3 = 0L, quad4 = 0L;
    for (int x = 0; x < width; x++) {
      for (int y = 0; y < height; y++) {
        Point p = new Point(x, y);
        long count = robots.stream().filter(r -> r.position.equals(p)).count();
        if (x < width / 2) {
          if (y < height / 2) {
            quad1 += count;
          } else if (y > height / 2) {
            quad2 += count;
          }
        } else if (x > width / 2) {
          if (y < height / 2) {
            quad3 += count;
          } else if (y > height / 2) {
            quad4 += count;
          }
        }
      }
    }
    return quad1 * quad2 * quad3 * quad4;
  }

  public static long part2(String filename, int width, int height) {
    List<String> lines = InputUtils.stringPerLine(filename, Day14.class);
    List<Robot> robots = new ArrayList<>();
    for (String line : lines) {
      String[] parts = line.split(" ");
      String[] pCoords = parts[0].split("=")[1].split(",");
      String[] vCoords = parts[1].split("=")[1].split(",");
      Point p = new Point(Integer.parseInt(pCoords[0]), Integer.parseInt(pCoords[1]));
      Point v = new Point(Integer.parseInt(vCoords[0]), Integer.parseInt(vCoords[1]));
      robots.add(new Robot(p, v));
    }
    long iterations = 0;
    boolean completed = false;
    while (!completed) {
      iterations++;
      Set<Point> visited = new HashSet<>();
      for (Robot robot : robots) {
        robot.move(width, height);
        visited.add(robot.position);
      }
      if (visited.size() == robots.size()) {
        for (int row = 0; row < height; row++) {
          int count = 0;
          for (int col = width / 2; col < width; col++) {
            Point p = new Point(col, row);
            if (visited.contains(p)) {
              count++;
            } else {
              break;
            }
          }
          if (count > 3) {
            completed = true;
            break;
          }
        }
      }
    }
    return iterations;
  }

  private record Robot(Point position, Point velocity) {
    public void move(int width, int height) {
      Point p = position.move(velocity);
      position.setX((int) ((p.getX() + width) % width));
      position.setY((int) ((p.getY() + height) % height));
    }
  }
}
