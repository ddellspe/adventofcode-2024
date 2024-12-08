package net.ddellspe.day08;

import java.util.*;
import java.util.stream.Collectors;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day08 {
  private Day08() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day08.class);
    Map<String, Set<Point>> nodes = new HashMap<>();
    for (int y = 0; y < lines.size(); y++) {
      for (int x = 0; x < lines.get(y).length(); x++) {
        if (lines.get(y).charAt(x) != '.') {
          String value = lines.get(y).charAt(x) + "";
          if (!nodes.containsKey(value)) {
            nodes.put(value, new HashSet<>());
          }
          nodes.get(value).add(new Point(x, y));
        }
      }
    }
    int xMax = lines.getFirst().length() - 1, yMax = lines.size() - 1;
    Set<Point> antiNodes = new HashSet<>();
    for (Set<Point> nodeData : nodes.values()) {
      List<Point> nodeList = new ArrayList<>(nodeData);
      for (int i = 0; i < nodeList.size(); i++) {
        for (int j = 0; j < nodeList.size(); j++) {
          if (i == j) {
            continue;
          }
          antiNodes.add(nodeList.get(j).move(nodeList.get(i).difference(nodeList.get(j))));
        }
      }
    }
    Set<Point> finalAntiNodes =
        antiNodes.stream()
            .filter(
                point ->
                    point.getX() >= 0
                        && point.getX() <= xMax
                        && point.getY() >= 0
                        && point.getY() <= yMax)
            .collect(Collectors.toSet());
    return finalAntiNodes.size();
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day08.class);
    Map<String, Set<Point>> nodes = new HashMap<>();
    for (int y = 0; y < lines.size(); y++) {
      for (int x = 0; x < lines.get(y).length(); x++) {
        if (lines.get(y).charAt(x) != '.') {
          String value = lines.get(y).charAt(x) + "";
          if (!nodes.containsKey(value)) {
            nodes.put(value, new HashSet<>());
          }
          nodes.get(value).add(new Point(x, y));
        }
      }
    }
    int xMax = lines.getFirst().length() - 1, yMax = lines.size() - 1;
    Set<Point> antiNodes = nodes.values().stream().flatMap(Set::stream).collect(Collectors.toSet());
    for (Set<Point> nodeData : nodes.values()) {
      List<Point> nodeList = new ArrayList<>(nodeData);
      for (int i = 0; i < nodeList.size(); i++) {
        for (int j = 0; j < nodeList.size(); j++) {
          Set<Point> setData = new HashSet<>();
          if (i == j) {
            continue;
          }
          Point pt1 = nodeList.get(i);
          Point pt2 = nodeList.get(j);
          Point diff = pt1.difference(pt2);
          pt2 = pt2.move(diff);
          while (pt2.getX() >= 0 && pt2.getX() <= xMax && pt2.getY() >= 0 && pt2.getY() <= yMax) {
            setData.add(pt2);
            pt2 = pt2.move(diff);
          }
          antiNodes.addAll(setData);
        }
      }
    }
    return antiNodes.size();
  }
}
