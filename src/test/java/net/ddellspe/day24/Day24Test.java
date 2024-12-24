package net.ddellspe.day24;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class Day24Test {
  Day24Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(4L, Day24.part1("example.txt"));
  }

  @Test
  public void providedInput2TestPart1() {
    assertEquals(2024L, Day24.part1("example2.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 24 Part 1 Answer is: " + Day24.part1("input.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 24 Part 2 Answer is: " + Day24.part2("input.txt"));
  }

  @Test
  public void coverageTest() {
    assertEquals(
        0,
        Day24.getOutput(
            "test", Map.of("aaa", List.of("aaa", "XAND", "bbb")), Map.of("aaa", 1, "bbb", 1)));
    assertEquals(
        0,
        Day24.getOutput(
            "ccc",
            Map.of("ccc", List.of("aaa", "XAND", "bbb")),
            Map.of("aaa", 1, "bbb", 1, "ccc", 0)));
    assertEquals(
        0,
        Day24.getOutput(
            "ccc",
            Map.of("ccc", List.of("aaa", "XOR", "bbb")),
            new HashMap<>(Map.of("aaa", 1, "bbb", 1))));
    assertEquals(
        1,
        Day24.getOutput(
            "ccc",
            Map.of("ccc", List.of("aaa", "AND", "bbb")),
            new HashMap<>(Map.of("aaa", 1, "bbb", 1))));
    assertEquals(
        1,
        Day24.getOutput(
            "ccc",
            Map.of("ccc", List.of("aaa", "OR", "bbb")),
            new HashMap<>(Map.of("aaa", 1, "bbb", 1))));
  }
}
