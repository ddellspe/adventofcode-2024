package net.ddellspe.day21;

import java.util.*;
import net.ddellspe.utils.InputUtils;
import net.ddellspe.utils.Point;

public class Day21 {
  private Day21() {}

  public static final Map<Point, String> MOVEMENT_MAP =
      Map.of(
          new Point(1, 0), ">", new Point(-1, 0), "<", new Point(0, 1), "v", new Point(0, -1), "^");
  public static final String NUMBER_PAD = "789\n456\n123\nX0A";
  public static final String D_PAD = "X^A\n<v>";
  public static final Map<Point, String> NUMBER_PAD_MAP = getItemMap(NUMBER_PAD);
  public static final Map<String, Point> REVERSE_NUMBER_PAD_MAP = getMapItem(NUMBER_PAD);
  public static final Map<Point, String> D_PAD_MAP = getItemMap(D_PAD);
  public static final Map<String, Point> REVERSE_D_PAD_MAP = getMapItem(D_PAD);

  public static Map<Point, String> getItemMap(String data) {
    List<String> lines = List.of(data.split("\n"));
    Map<Point, String> map = new HashMap<>();
    for (int y = 0; y < lines.size(); y++) {
      for (int x = 0; x < lines.get(y).length(); x++) {
        if (lines.get(y).charAt(x) == 'X') {
          continue;
        }
        map.put(new Point(x, y), String.valueOf(lines.get(y).charAt(x)));
      }
    }
    return map;
  }

  public static Map<String, Point> getMapItem(String data) {
    List<String> lines = List.of(data.split("\n"));
    Map<String, Point> map = new HashMap<>();
    for (int y = 0; y < lines.size(); y++) {
      for (int x = 0; x < lines.get(y).length(); x++) {
        if (lines.get(y).charAt(x) == 'X') {
          continue;
        }
        map.put(String.valueOf(lines.get(y).charAt(x)), new Point(x, y));
      }
    }
    return map;
  }

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day21.class);
    long total = 0L;
    Map<String, Map<Integer, Long>> cache = new HashMap<>();
    for (String line : lines) {
      total += solve(line, 4, cache) * Long.parseLong(line.substring(0, line.length() - 1));
    }
    return total;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day21.class);
    long total = 0L;
    Map<String, Map<Integer, Long>> cache = new HashMap<>();
    for (String line : lines) {
      total += solve(line, 27, cache) * Long.parseLong(line.substring(0, line.length() - 1));
    }
    return total;
  }

  public static Set<String> shortestPathBetweenPoints(
      String str1, String str2, Map<Point, String> keypad, Map<String, Point> reverseKeypad) {
    Point p1 = reverseKeypad.get(str1);
    Point p2 = reverseKeypad.get(str2);
    Point diff = new Point(p2.getX() - p1.getX(), p2.getY() - p1.getY());
    Set<String> paths = new HashSet<>();
    StringBuilder row = new StringBuilder();
    for (int y = 0; y < Math.abs(diff.getY()); y++) {
      row.append(diff.getY() >= 0 ? "v" : "^");
    }
    StringBuilder col = new StringBuilder();
    for (int x = 0; x < Math.abs(diff.getX()); x++) {
      col.append(diff.getX() >= 0 ? ">" : "<");
    }
    if (diff.equals(new Point(0, 0))) {
      paths.add("A");
    } else if (diff.getY() == 0) {
      paths.add(col + "A");
    } else if (diff.getX() == 0) {
      paths.add(row + "A");
    } else if (!keypad.containsKey(new Point(p1.getX(), p2.getY()))) {
      paths.add(col + row.toString() + "A");
    } else if (!keypad.containsKey(new Point(p2.getX(), p1.getY()))) {
      paths.add(row + col.toString() + "A");
    } else {
      paths.add(col + row.toString() + "A");
      paths.add(row + col.toString() + "A");
    }
    return paths;
  }

  public static List<Set<String>> getSequenceOfShortestPaths(
      String sequence, Map<Point, String> keypad, Map<String, Point> reverseKeypad) {
    List<Set<String>> result = new ArrayList<>();
    String start = "";
    String next = "";
    for (int i = 0; i < sequence.length(); i++) {
      if (i == 0) {
        start = "A";
      } else {
        start = next;
      }
      next = String.valueOf(sequence.charAt(i));
      result.add(shortestPathBetweenPoints(start, next, keypad, reverseKeypad));
    }
    return result;
  }

  public static long solve(String sequence, int depth, Map<String, Map<Integer, Long>> cache) {
    if (cache.containsKey(sequence) && cache.get(sequence).containsKey(depth)) {
      return cache.get(sequence).get(depth);
    }
    if (depth == 1) {
      Map<Integer, Long> internalCache = cache.getOrDefault(sequence, new HashMap<>());
      internalCache.put(depth, (long) sequence.length());
      cache.put(sequence, internalCache);
      return sequence.length();
    }
    Map<Point, String> keypad = NUMBER_PAD_MAP;
    Map<String, Point> reverseKeypad = REVERSE_NUMBER_PAD_MAP;
    if (Arrays.stream(sequence.split("")).noneMatch("0123456789"::contains)) {
      keypad = D_PAD_MAP;
      reverseKeypad = REVERSE_D_PAD_MAP;
    }
    long result = 0L;
    for (Set<String> paths : getSequenceOfShortestPaths(sequence, keypad, reverseKeypad)) {
      long minPath = Long.MAX_VALUE;
      for (String path : paths) {
        long pathSize = solve(path, depth - 1, cache);
        if (pathSize < minPath) {
          minPath = pathSize;
        }
      }
      result += minPath;
    }
    Map<Integer, Long> internalCache = cache.getOrDefault(sequence, new HashMap<>());
    internalCache.put(depth, result);
    cache.put(sequence, internalCache);
    return result;
  }
}
