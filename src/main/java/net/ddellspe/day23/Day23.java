package net.ddellspe.day23;

import java.util.*;
import net.ddellspe.utils.InputUtils;

public class Day23 {
  private Day23() {}

  public static long part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day23.class);
    Map<String, Set<String>> networks = new HashMap<>();
    for (String line : lines) {
      String comp1 = line.split("-")[0];
      String comp2 = line.split("-")[1];
      Set<String> connectedTo1 = networks.getOrDefault(comp1, new HashSet<>());
      connectedTo1.add(comp2);
      networks.put(comp1, connectedTo1);
      Set<String> connectedTo2 = networks.getOrDefault(comp2, new HashSet<>());
      connectedTo2.add(comp1);
      networks.put(comp2, connectedTo2);
    }
    Set<Set<String>> threeComputerNetworks = new HashSet<>();
    for (String comp1 : networks.keySet()) {
      for (String comp2 : networks.get(comp1)) {
        for (String comp3 : networks.get(comp1)) {
          if (comp2.equals(comp3)) {
            continue;
          }
          if (networks.get(comp2).contains(comp3) && networks.get(comp3).contains(comp1)) {
            threeComputerNetworks.add(Set.of(comp1, comp2, comp3));
          }
        }
      }
    }
    return threeComputerNetworks.stream()
        .filter(s -> s.stream().anyMatch(s2 -> s2.startsWith("t")))
        .count();
  }

  public static String part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day23.class);
    Map<String, Set<String>> networks = new HashMap<>();
    for (String line : lines) {
      String comp1 = line.split("-")[0];
      String comp2 = line.split("-")[1];
      Set<String> connectedTo1 = networks.getOrDefault(comp1, new HashSet<>());
      connectedTo1.add(comp2);
      networks.put(comp1, connectedTo1);
      Set<String> connectedTo2 = networks.getOrDefault(comp2, new HashSet<>());
      connectedTo2.add(comp1);
      networks.put(comp2, connectedTo2);
    }
    Set<String> maxClique = new HashSet<>();
    for (String node : networks.keySet()) {
      Set<String> clique = new HashSet<>();
      clique.add(node);
      for (String node2 : networks.keySet()) {
        if (!node.equals(node2)) {
          boolean add = true;
          for (String cNode : clique) {
            if (!networks.get(cNode).contains(node2)) {
              add = false;
              break;
            }
          }
          if (add) {
            clique.add(node2);
          }
        }
      }
      if (clique.size() > maxClique.size()) {
        maxClique = clique;
      }
    }
    return String.join(",", maxClique.stream().sorted().toList());
  }
}
