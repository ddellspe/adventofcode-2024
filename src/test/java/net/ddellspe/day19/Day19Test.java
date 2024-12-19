package net.ddellspe.day19;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day19Test {
  Day19Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(6L, Day19.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 19 Part 1 Answer is: " + Day19.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(16L, Day19.part2("example.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 19 Part 2 Answer is: " + Day19.part2("input.txt"));
  }
}
