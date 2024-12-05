package net.ddellspe.day05;

import java.util.*;
import java.util.stream.Collectors;
import net.ddellspe.utils.InputUtils;

public class Day05 {
  private Day05() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day05.class);
    Map<Integer, Set<Integer>> before = new HashMap<>();
    List<List<Integer>> updates = new ArrayList<>();
    boolean secondSection = false;
    for (String line : lines) {
      if (line.isBlank()) {
        secondSection = true;
        continue;
      }
      if (!secondSection) {
        List<Integer> data =
            Arrays.stream(line.split("\\|")).mapToInt(Integer::parseInt).boxed().toList();
        if (!before.containsKey(data.getFirst())) {
          before.put(data.getFirst(), new HashSet<>(Set.of(data.getLast())));
        } else {
          before.get(data.getFirst()).add(data.getLast());
        }
      } else {
        updates.add(Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).boxed().toList());
      }
    }
    long sum = 0L;
    for (List<Integer> update : updates) {
      boolean valid = true;
      Set<Integer> passedPages = new HashSet<>();
      for (int page : update.reversed()) {
        if (passedPages.isEmpty()) {
          passedPages.add(page);
          continue;
        }
        Set<Integer> afters = before.get(page);
        if (afters == null) {
          valid = false;
          break;
        }
        for (int num : passedPages) {
          if (!afters.contains(num)) {
            valid = false;
            break;
          }
        }
        if (!valid) {
          break;
        }
        passedPages.add(page);
      }
      if (valid) {
        sum += update.get(update.size() / 2);
      }
    }
    return sum;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day05.class);
    Map<Integer, Set<Integer>> before = new HashMap<>();
    List<List<Integer>> updates = new ArrayList<>();
    boolean secondSection = false;
    for (String line : lines) {
      if (line.isBlank()) {
        secondSection = true;
        continue;
      }
      if (!secondSection) {
        List<Integer> data =
            Arrays.stream(line.split("\\|")).mapToInt(Integer::parseInt).boxed().toList();
        if (!before.containsKey(data.getFirst())) {
          before.put(data.getFirst(), new HashSet<>(Set.of(data.getLast())));
        } else {
          before.get(data.getFirst()).add(data.getLast());
        }
      } else {
        updates.add(Arrays.stream(line.split(",")).mapToInt(Integer::parseInt).boxed().toList());
      }
    }
    long sum = 0L;
    for (List<Integer> update : updates) {
      boolean valid = true;
      Set<Integer> passedPages = new HashSet<>();
      for (int page : update.reversed()) {
        if (passedPages.isEmpty()) {
          passedPages.add(page);
          continue;
        }
        Set<Integer> afters = before.get(page);
        if (afters == null) {
          valid = false;
          break;
        }
        for (int num : passedPages) {
          if (!afters.contains(num)) {
            valid = false;
            break;
          }
        }
        if (!valid) {
          break;
        }
        passedPages.add(page);
      }
      if (!valid) {
        Set<Integer> numbers = new HashSet<>(update);
        List<Integer> newList = new ArrayList<>();
        while (!numbers.isEmpty()) {
          for (int num : numbers) {
            Set<Integer> remainingNumbers =
                numbers.stream().filter(v -> v != num).collect(Collectors.toSet());
            if (remainingNumbers.isEmpty()
                || before.getOrDefault(num, new HashSet<>()).stream()
                        .filter(remainingNumbers::contains)
                        .count()
                    == remainingNumbers.size()) {
              newList.add(num);
              break;
            }
          }
          numbers.remove(newList.getLast());
        }
        sum += newList.get(newList.size() / 2);
      }
    }
    return sum;
  }
}
