package net.ddellspe.day22;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.ddellspe.utils.InputUtils;

public class Day22 {
  private Day22() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day22.class);
    long total = 0L;
    for (String line : lines) {
      long secretNumber = Long.parseLong(line);
      for (int i = 0; i < 2000; i++) {
        long value = secretNumber * 64L;
        secretNumber ^= value;
        secretNumber %= 16777216L;
        value = secretNumber / 32L;
        secretNumber ^= value;
        secretNumber %= 16777216L;
        value = secretNumber * 2048L;
        secretNumber ^= value;
        secretNumber %= 16777216L;
      }
      total += secretNumber;
    }
    return total;
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day22.class);
    List<List<Integer>> prices = new ArrayList<>();
    List<List<Integer>> sequences = new ArrayList<>();
    for (String line : lines) {
      long secretNumber = Long.parseLong(line);
      List<Integer> monkeyPrices = new ArrayList<>();
      List<Integer> moneyDiffs = new ArrayList<>();
      int lastValue = (int) (secretNumber % 10);
      monkeyPrices.add(lastValue);
      for (int i = 0; i < 2000; i++) {
        long value = secretNumber * 64L;
        secretNumber ^= value;
        secretNumber %= 16777216L;
        value = secretNumber / 32L;
        secretNumber ^= value;
        secretNumber %= 16777216L;
        value = secretNumber * 2048L;
        secretNumber ^= value;
        secretNumber %= 16777216L;
        int newValue = (int) secretNumber % 10;
        moneyDiffs.add(newValue - lastValue);
        monkeyPrices.add(newValue);
        lastValue = newValue;
      }
      prices.add(monkeyPrices);
      sequences.add(moneyDiffs);
    }
    List<String> sequenceStr = new ArrayList<>();
    for (List<Integer> diffs : sequences) {
      StringBuilder b = new StringBuilder();
      for (int val : diffs) {
        b.append((char) ('j' + val));
      }
      sequenceStr.add(b.toString());
    }
    long maxValue = 0L;
    Set<String> tried = new HashSet<>();
    for (int list = 0; list < sequenceStr.size(); list++) {
      String diffSeq = sequenceStr.get(list);
      for (int i = 4; i < diffSeq.length(); i++) {
        String searchSeq = diffSeq.substring(i - 4, i);
        long total = 0L;
        if (tried.contains(searchSeq)) {
          continue;
        }
        for (int otherList = 0; otherList < sequenceStr.size(); otherList++) {
          String diffSeq2 = sequenceStr.get(otherList);
          int index = diffSeq2.indexOf(searchSeq);
          if (index >= 0) {
            total += prices.get(otherList).get(index + 4);
          }
        }
        tried.add(searchSeq);
        if (total > maxValue) {
          maxValue = total;
        }
      }
    }
    return maxValue;
  }
}
