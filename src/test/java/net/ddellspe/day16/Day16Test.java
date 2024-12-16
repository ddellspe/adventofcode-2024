package net.ddellspe.day16;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;
import net.ddellspe.utils.Point;
import org.junit.jupiter.api.Test;

public class Day16Test {
  Day16Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals(7036L, Day16.part1("example.txt"));
  }

  @Test
  public void providedInput2TestPart1() {
    assertEquals(11048L, Day16.part1("example2.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 16 Part 1 Answer is: " + Day16.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(45L, Day16.part2("example.txt"));
  }

  @Test
  public void providedInput2TestPart2() {
    assertEquals(64L, Day16.part2("example2.txt"));
  }

  //    @Test
  //    public void solutionPart2() {
  //      System.out.println("Day 16 Part 2 Answer is: " + Day16.part2("input.txt"));
  //    }

  @Test
  public void stateTests() {
    Day16.State state =
        new Day16.State(new Point(0, 0), 0L, new Point(1, 0), Set.of(new Point(0, 0)));
    Day16.State state2 =
        new Day16.State(new Point(0, 0), 0L, new Point(1, 0), Set.of(new Point(0, 0)));
    assertEquals(state, state2);
    assertFalse(state.equals("hello"));
    Day16.State state3 =
        new Day16.State(new Point(0, 0), 0L, new Point(1, 0), Set.of(new Point(1, 0)));
    assertNotEquals(state, state3);
    Day16.State state4 =
        new Day16.State(new Point(0, 0), 0L, new Point(1, 1), Set.of(new Point(1, 0)));
    assertNotEquals(state, state4);
    Day16.State state5 =
        new Day16.State(new Point(0, 0), 1L, new Point(1, 1), Set.of(new Point(1, 0)));
    assertNotEquals(state, state5);
    Day16.State state6 =
        new Day16.State(new Point(1, 0), 1L, new Point(1, 1), Set.of(new Point(1, 0)));
    assertNotEquals(state, state6);
  }
}
