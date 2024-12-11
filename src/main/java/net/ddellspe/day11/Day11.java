package net.ddellspe.day11;

import java.util.*;
import java.util.stream.Collectors;
import net.ddellspe.utils.InputUtils;

public class Day11 {
  private Day11() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day11.class);
    Map<String, Long> data =
        Arrays.stream(lines.getFirst().split(" ")).collect(Collectors.toMap(v -> v, v -> 1L));
    for (int i = 0; i < 25; i++) {
      data = blink(data);
    }
    return data.values().stream().mapToLong(Long::longValue).sum();
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day11.class);
    Map<String, Long> data =
        Arrays.stream(lines.getFirst().split(" ")).collect(Collectors.toMap(v -> v, v -> 1L));
    for (int i = 0; i < 75; i++) {
      data = blink(data);
    }
    return data.values().stream().mapToLong(Long::longValue).sum();
  }

  private static Map<String, Long> blink(Map<String, Long> data) {
    Map<String, Long> updatedData = new HashMap<>();
    for (Map.Entry<String, Long> entry : data.entrySet()) {
      String value = entry.getKey();
      if ("0".equals(value)) {
        updatedData.put("1", updatedData.getOrDefault("1", 0L) + entry.getValue());
      } else if (value.length() % 2 == 0) {
        String firstPart = String.valueOf(Long.parseLong(value.substring(0, value.length() / 2)));
        String secondPart = String.valueOf(Long.parseLong(value.substring(value.length() / 2)));
        updatedData.put(firstPart, updatedData.getOrDefault(firstPart, 0L) + entry.getValue());
        updatedData.put(secondPart, updatedData.getOrDefault(secondPart, 0L) + entry.getValue());
      } else {
        String newValue = String.valueOf(Long.parseLong(value) * 2024L);
        updatedData.put(newValue, updatedData.getOrDefault(newValue, 0L) + entry.getValue());
      }
    }
    return updatedData;
  }
}
