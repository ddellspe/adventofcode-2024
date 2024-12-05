package net.ddellspe.day04;

import java.util.List;
import net.ddellspe.utils.InputUtils;

public class Day04 {
  private Day04() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day04.class);
    long count = 0;
    for (int row = 0; row < lines.size(); row++) {
      boolean lookUp = row >= 3;
      boolean lookDown = row <= lines.size() - 4;
      String line = lines.get(row);
      for (int col = 0; col < line.length(); col++) {
        boolean lookLeft = col >= 3;
        boolean lookRight = col <= line.length() - 4;
        if (line.charAt(col) == 'X') {
          if (lookRight
              && line.charAt(col + 1) == 'M'
              && line.charAt(col + 2) == 'A'
              && line.charAt(col + 3) == 'S') {
            count++;
          }
          if (lookLeft
              && line.charAt(col - 1) == 'M'
              && line.charAt(col - 2) == 'A'
              && line.charAt(col - 3) == 'S') {
            count++;
          }
          if (lookUp
              && lines.get(row - 1).charAt(col) == 'M'
              && lines.get(row - 2).charAt(col) == 'A'
              && lines.get(row - 3).charAt(col) == 'S') {
            count++;
          }
          if (lookDown
              && lines.get(row + 1).charAt(col) == 'M'
              && lines.get(row + 2).charAt(col) == 'A'
              && lines.get(row + 3).charAt(col) == 'S') {
            count++;
          }
          if (lookUp
              && lookLeft
              && lines.get(row - 1).charAt(col - 1) == 'M'
              && lines.get(row - 2).charAt(col - 2) == 'A'
              && lines.get(row - 3).charAt(col - 3) == 'S') {
            count++;
          }
          if (lookUp
              && lookRight
              && lines.get(row - 1).charAt(col + 1) == 'M'
              && lines.get(row - 2).charAt(col + 2) == 'A'
              && lines.get(row - 3).charAt(col + 3) == 'S') {
            count++;
          }
          if (lookDown
              && lookLeft
              && lines.get(row + 1).charAt(col - 1) == 'M'
              && lines.get(row + 2).charAt(col - 2) == 'A'
              && lines.get(row + 3).charAt(col - 3) == 'S') {
            count++;
          }
          if (lookDown
              && lookRight
              && lines.get(row + 1).charAt(col + 1) == 'M'
              && lines.get(row + 2).charAt(col + 2) == 'A'
              && lines.get(row + 3).charAt(col + 3) == 'S') {
            count++;
          }
        }
      }
    }
    return count;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day04.class);
    long count = 0;
    for (int row = 1; row < lines.size() - 1; row++) {
      String line = lines.get(row);
      for (int col = 1; col < line.length() - 1; col++) {
        if (line.charAt(col) == 'A') {
          if (((lines.get(row - 1).charAt(col - 1) == 'M'
                      && lines.get(row + 1).charAt(col + 1) == 'S')
                  || (lines.get(row - 1).charAt(col - 1) == 'S'
                      && lines.get(row + 1).charAt(col + 1) == 'M'))
              && ((lines.get(row - 1).charAt(col + 1) == 'M'
                      && lines.get(row + 1).charAt(col - 1) == 'S')
                  || (lines.get(row - 1).charAt(col + 1) == 'S'
                      && lines.get(row + 1).charAt(col - 1) == 'M'))) {
            count++;
          }
        }
      }
    }
    return count;
  }
}
