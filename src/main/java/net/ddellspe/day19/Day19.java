package net.ddellspe.day19;

import java.util.*;
import net.ddellspe.utils.InputUtils;

public class Day19 {
  private Day19() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day19.class);
    Set<String> patterns = new HashSet<>(Arrays.stream(lines.getFirst().split(", ")).toList());
    long validCount = 0L;
    for (int i = 2; i < lines.size(); i++) {
      String line = lines.get(i);
      if (hasPattern(line, patterns, new HashMap<>())) {
        validCount++;
      }
    }
    return validCount;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day19.class);
    Set<String> patterns = new HashSet<>(Arrays.stream(lines.getFirst().split(", ")).toList());
    long sequenceCount = 0L;
    for (int i = 2; i < lines.size(); i++) {
      String line = lines.get(i);
      long count = countPattern(line, patterns, new HashMap<>());
      sequenceCount += count;
    }
    return sequenceCount;
  }

  public static boolean hasPattern(String towel, Set<String> patterns, Map<String, Boolean> valid) {
    if (towel.isEmpty()) {
      valid.put(towel, true);
      return valid.get(towel);
    }
    if (valid.containsKey(towel)) {
      return valid.get(towel);
    }
    for (String pattern : patterns) {
      if (pattern.length() <= towel.length() && towel.startsWith(pattern)) {
        boolean present = hasPattern(towel.substring(pattern.length()), patterns, valid);
        if (present) {
          valid.put(towel, true);
          return valid.get(towel);
        }
      }
    }
    valid.put(towel, false);
    return valid.get(towel);
  }

  public static long countPattern(String towel, Set<String> patterns, Map<String, Long> valid) {
    if (towel.isEmpty()) {
      return 1L;
    }
    if (valid.containsKey(towel)) {
      return valid.get(towel);
    }
    long sequences = 0L;
    for (String pattern : patterns) {
      if (pattern.length() <= towel.length() && towel.startsWith(pattern)) {
        sequences += countPattern(towel.substring(pattern.length()), patterns, valid);
      }
    }
    valid.put(towel, sequences);
    return valid.get(towel);
  }
}
