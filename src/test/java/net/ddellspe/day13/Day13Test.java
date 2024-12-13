package net.ddellspe.day13;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day13Test {
  Day13Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(480L, Day13.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 13 Part 1 Answer is: " + Day13.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(875318608908L, Day13.part2("example.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 13 Part 2 Answer is: " + Day13.part2("input.txt"));
  }
}
