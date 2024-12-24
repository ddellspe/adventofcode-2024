package net.ddellspe.day24;

import java.util.*;
import net.ddellspe.utils.InputUtils;

public class Day24 {
  private Day24() {}

  public static String X_FORMAT = "x%02d";
  public static String Y_FORMAT = "y%02d";
  public static String Z_FORMAT = "z%02d";

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day24.class);
    Map<String, Integer> inputData = new HashMap<>();
    Map<String, List<String>> processMap = new HashMap<>();
    boolean processTheMap = false;
    for (String line : lines) {
      if (line.isEmpty()) {
        processTheMap = true;
        continue;
      }
      if (!processTheMap) {
        String[] data = line.split(": ");
        inputData.put(data[0], Integer.parseInt(data[1]));
      } else {
        String[] data = line.split(" -> ");
        processMap.put(data[1], Arrays.stream(data[0].split(" ")).toList());
      }
    }
    StringBuilder sb = new StringBuilder();
    for (String value :
        processMap.keySet().stream()
            .filter(v -> v.startsWith("z"))
            .sorted(Comparator.reverseOrder())
            .toList()) {
      sb.append(getOutput(value, processMap, inputData));
    }
    return Long.parseLong(sb.toString(), 2);
  }

  public static int getOutput(
      String output, Map<String, List<String>> processMap, Map<String, Integer> input) {
    if (input.containsKey(output)) {
      return input.get(output);
    }
    if (processMap.containsKey(output)) {
      List<String> map = processMap.get(output);
      int value1 = getOutput(map.get(0), processMap, input);
      int value2 = getOutput(map.get(2), processMap, input);
      switch (map.get(1)) {
        case "XOR":
          input.put(output, value1 ^ value2);
          break;
        case "AND":
          input.put(output, value1 & value2);
          break;
        case "OR":
          input.put(output, value1 | value2);
          break;
      }
      return input.get(output);
    } else {
      return 0;
    }
  }

  public static String part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day24.class);
    Map<String, List<String>> processMap = new HashMap<>();
    boolean processTheMap = false;
    for (String line : lines) {
      if (line.isEmpty()) {
        processTheMap = true;
        continue;
      }
      if (processTheMap) {
        String[] data = line.split(" -> ");
        processMap.put(data[1], Arrays.stream(data[0].split(" ")).toList());
      }
    }
    int maxBit =
        Integer.parseInt(
            processMap.keySet().stream()
                .filter(k -> k.startsWith("z"))
                .sorted(Comparator.reverseOrder())
                .findFirst()
                .get()
                .replace("z", ""));
    int bit = 0;
    String carryGate = null;
    List<String> swaps = new ArrayList<>();
    while (bit < maxBit) {
      String x = String.format(X_FORMAT, bit);
      String y = String.format(Y_FORMAT, bit);
      String z = String.format(Z_FORMAT, bit);
      if (bit == 0) {
        carryGate = getDestinationWire(x, y, "AND", processMap);
      } else {
        String xorGate = getDestinationWire(x, y, "XOR", processMap);
        String andGate = getDestinationWire(x, y, "AND", processMap);

        String destinationXorGate = getDestinationWire(xorGate, carryGate, "XOR", processMap);
        if (destinationXorGate == null) {
          swaps.add(xorGate);
          swaps.add(andGate);
          List<String> temp = processMap.get(xorGate);
          processMap.put(xorGate, processMap.get(andGate));
          processMap.put(andGate, temp);
          bit = 0;
          continue;
        }
        if (!destinationXorGate.equals(z)) {
          swaps.add(destinationXorGate);
          swaps.add(z);
          List<String> temp = processMap.get(destinationXorGate);
          processMap.put(destinationXorGate, processMap.get(z));
          processMap.put(z, temp);
          bit = 0;
          continue;
        }
        String destinationAndGate = getDestinationWire(xorGate, carryGate, "AND", processMap);
        carryGate = getDestinationWire(andGate, destinationAndGate, "OR", processMap);
      }
      bit++;
    }
    return String.join(",", swaps.stream().sorted().toList());
  }

  public static String getDestinationWire(
      String xWire, String yWire, String gate, Map<String, List<String>> configs) {
    for (Map.Entry<String, List<String>> entry : configs.entrySet()) {
      if (entry.getValue().equals(List.of(xWire, gate, yWire))
          || entry.getValue().equals(List.of(yWire, gate, xWire))) {
        return entry.getKey();
      }
    }
    return null;
  }
}
