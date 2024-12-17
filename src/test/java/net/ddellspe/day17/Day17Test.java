package net.ddellspe.day17;

import static net.ddellspe.day17.Day17.solveA;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

public class Day17Test {
  Day17Test() {}

  @Test
  public void providedInputTestPart1() {
    assertEquals("4,6,3,5,6,3,5,2,1,0", Day17.part1("example.txt"));
  }

  @Test
  public void solutionPart1() {
    System.out.println("Day 17 Part 1 Answer is: " + Day17.part1("input.txt"));
  }

  @Test
  public void providedInputTestPart2() {
    assertEquals(117440L, Day17.part2("example2.txt"));
  }

  @Test
  public void solutionPart2() {
    System.out.println("Day 17 Part 2 Answer is: " + Day17.part2("input.txt"));
  }

  @Test
  public void testCase1() {
    long[] registers = new long[] {0, 0, 9L};
    List<Integer> program = List.of(2, 6);
    assertEquals(List.of(), Day17.processData(registers, program));
    assertEquals(1L, registers[1]);
  }

  @Test
  public void testCase2() {
    long[] registers = new long[] {10L, 0, 0};
    List<Integer> program = List.of(5, 0, 5, 1, 5, 4);
    assertEquals(List.of(0, 1, 2), Day17.processData(registers, program));
  }

  @Test
  public void testCase3() {
    long[] registers = new long[] {2024L, 0, 0};
    List<Integer> program = List.of(0, 1, 5, 4, 3, 0);
    assertEquals(List.of(4, 2, 5, 6, 7, 7, 7, 7, 3, 1, 0), Day17.processData(registers, program));
    assertEquals(0L, registers[0]);
  }

  @Test
  public void testCase4() {
    long[] registers = new long[] {0, 29L, 0};
    List<Integer> program = List.of(1, 7);
    assertEquals(List.of(), Day17.processData(registers, program));
    assertEquals(26L, registers[1]);
  }

  @Test
  public void testCase5() {
    long[] registers = new long[] {0, 2024L, 43690L};
    List<Integer> program = List.of(4, 0);
    assertEquals(List.of(), Day17.processData(registers, program));
    assertEquals(44354L, registers[1]);
  }

  @Test
  public void testCoverage() {
    long[] registers = new long[] {0, 0, 0};
    List<Integer> program = List.of(6, 0, 8, 0);
    assertEquals(List.of(), Day17.processData(registers, program));
    assertEquals(0L, solveA(0, program, 0));
  }
}
