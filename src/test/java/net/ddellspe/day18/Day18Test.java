package net.ddellspe.day18;

import static org.junit.jupiter.api.Assertions.assertEquals;

import net.ddellspe.utils.Point;
import org.junit.jupiter.api.Test;

public class Day18Test {
  Day18Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(22L, Day18.part1("example.txt", new Point(6, 6), 12));
  }

  @Test
  public void solutionPart1() {
    System.out.println(
        "Day 18 Part 1 Answer is: " + Day18.part1("input.txt", new Point(70, 70), 1024));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(new Point(6, 1), Day18.part2("example.txt", new Point(6, 6)));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 18 Part 2 Answer is: " + Day18.part2("input.txt", new Point(70, 70)));
  }
}
