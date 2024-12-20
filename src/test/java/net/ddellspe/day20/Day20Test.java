package net.ddellspe.day20;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class Day20Test {
  Day20Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(5L, Day20.part1("example.txt", 20L));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 20 Part 1 Answer is: " + Day20.part1("input.txt", 100L));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(285L, Day20.part2("example.txt", 50L));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 20 Part 2 Answer is: " + Day20.part2("input.txt", 100L));
  }
}
