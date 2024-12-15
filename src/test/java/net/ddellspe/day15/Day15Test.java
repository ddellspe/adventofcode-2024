package net.ddellspe.day15;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import net.ddellspe.utils.Point;
import org.junit.jupiter.api.Test;

public class Day15Test {
  Day15Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(10092L, Day15.part1("example.txt"));
  }

  @Test
  public void providedInput2TestPart1() {
    assertEquals(2028L, Day15.part1("example2.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 15 Part 1 Answer is: " + Day15.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(9021L, Day15.part2("example.txt"));
  }

  @Test
  public void providedInput2TestPart2() {
    assertEquals(618L, Day15.part2("example3.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 15 Part 2 Answer is: " + Day15.part2("input.txt"));
  }

  @Test
  public void testBox() {
    Day15.Box box = new Day15.Box(new Point(0, 0));
    assertFalse(box.equals(new Point(0, 0)));
    Day15.Box box2 = new Day15.Box(new Point(1, 0), new Point(0, 0));
    assertFalse(box.equals(box2));
    Day15.Box box3 = new Day15.Box(new Point(0, 0), new Point(2, 0));
    assertFalse(box.equals(box3));
  }
}
