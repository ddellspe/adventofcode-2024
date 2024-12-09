package net.ddellspe.day09;

import java.util.*;
import java.util.stream.IntStream;
import net.ddellspe.utils.InputUtils;

public class Day09 {
  private Day09() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day09.class);
    long fileID = 0;
    boolean file = true;
    List<Long> disk = new ArrayList<>();
    for (String value : lines.getFirst().split("")) {
      int size = Integer.parseInt(value);
      for (int i = 0; i < size; i++) {
        disk.add(file ? fileID : null);
      }
      if (file) {
        fileID++;
      }
      file = !file;
    }
    int lastNonNullIndex =
        disk.lastIndexOf(disk.stream().filter(Objects::nonNull).toList().getLast());
    int firstNullIndex = disk.indexOf(null);
    while (lastNonNullIndex > firstNullIndex) {
      Collections.swap(disk, lastNonNullIndex, firstNullIndex);
      lastNonNullIndex =
          disk.lastIndexOf(disk.stream().filter(Objects::nonNull).toList().getLast());
      firstNullIndex = disk.indexOf(null);
    }
    return IntStream.range(0, disk.size())
        .filter(i -> disk.get(i) != null)
        .mapToLong(i -> i * disk.get(i))
        .sum();
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day09.class);
    long fileID = 0;
    boolean file = true;
    List<Long> disk = new ArrayList<>();
    Map<Long, Integer> fileIdSizes = new HashMap<>();
    Map<Integer, List<Integer>> openSpaces = new HashMap<>();
    for (String value : lines.getFirst().split("")) {
      int size = Integer.parseInt(value);
      if (file) {
        fileIdSizes.put(fileID, size);
      } else {
        if (!openSpaces.containsKey(size)) {
          openSpaces.put(size, new ArrayList<>());
        }
        openSpaces.get(size).add(disk.size());
      }
      for (int i = 0; i < size; i++) {
        disk.add(file ? fileID : null);
      }
      if (file) {
        fileID++;
      }
      file = !file;
    }
    fileID--;
    while (fileID > 0) {
      final int size = fileIdSizes.get(fileID);
      List<Integer> candidateSpots =
          openSpaces.entrySet().stream()
              .filter(e -> e.getKey() >= size)
              .flatMapToInt(e -> e.getValue().stream().mapToInt(Integer::intValue))
              .sorted()
              .boxed()
              .toList();
      if (candidateSpots.isEmpty()) {
        fileID--;
        continue;
      }
      final int firstIndex = candidateSpots.getFirst();
      int spaceSize =
          openSpaces.entrySet().stream()
              .filter(e -> e.getValue().contains(firstIndex))
              .mapToInt(Map.Entry::getKey)
              .boxed()
              .findFirst()
              .get();
      int dataFirstIndex = disk.indexOf(fileID);
      if (firstIndex > dataFirstIndex) {
        fileID--;
        continue;
      }
      for (int i = 0; i < size; i++) {
        Collections.swap(disk, dataFirstIndex + i, firstIndex + i);
      }
      openSpaces.get(spaceSize).remove((Integer) firstIndex);
      if (size < spaceSize) {
        int remainder = spaceSize - size;
        List<Integer> data = openSpaces.get(remainder);
        data.add((firstIndex + size));
        Collections.sort(data);
        openSpaces.put(remainder, data);
      }
      fileID--;
    }
    return IntStream.range(0, disk.size())
        .filter(i -> disk.get(i) != null)
        .mapToLong(i -> i * disk.get(i))
        .sum();
  }
}
