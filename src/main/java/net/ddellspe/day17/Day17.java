package net.ddellspe.day17;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import net.ddellspe.utils.InputUtils;

public class Day17 {
  private Day17() {}

  public static String part1(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day17.class);
    long[] registers = new long[3];
    List<Integer> program = new ArrayList<>();
    for (String line : lines) {
      if (line.startsWith("Register A:")) {
        registers[0] = Long.parseLong(line.split(": ")[1]);
      } else if (line.startsWith("Register B:")) {
        registers[1] = Long.parseLong(line.split(": ")[1]);
      } else if (line.startsWith("Register C:")) {
        registers[2] = Long.parseLong(line.split(": ")[1]);
      } else if (line.startsWith("Program: ")) {
        program =
            Arrays.stream(line.split(": ")[1].split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .toList();
      }
    }
    return String.join(",", processData(registers, program).stream().map(String::valueOf).toList());
  }

  public static long part2(String filename) {
    List<String> lines = InputUtils.stringPerLine(filename, Day17.class);
    long[] registers = new long[3];
    List<Integer> program = new ArrayList<>();
    for (String line : lines) {
      if (line.startsWith("Register A:")) {
        registers[0] = Long.parseLong(line.split(": ")[1]);
      } else if (line.startsWith("Register B:")) {
        registers[1] = Long.parseLong(line.split(": ")[1]);
      } else if (line.startsWith("Register C:")) {
        registers[2] = Long.parseLong(line.split(": ")[1]);
      } else if (line.startsWith("Program: ")) {
        program =
            Arrays.stream(line.split(": ")[1].split(","))
                .mapToInt(Integer::parseInt)
                .boxed()
                .toList();
      }
    }
    return solveA(0L, program, 0);
  }

  public static long solveA(long registerA, List<Integer> program, int index) {
    if (index == program.size()) {
      return registerA;
    }
    for (int i = 0; i < 8; i++) {
      List<Integer> outputs = runProgram(registerA * 8 + i, program);
      if (!outputs.isEmpty()
          && Objects.equals(outputs.getFirst(), program.get(program.size() - index - 1))) {
        long result = solveA(registerA * 8 + i, program, index + 1);
        if (result != 0) {
          return result;
        }
      }
    }
    return 0;
  }

  public static List<Integer> runProgram(long registerA, List<Integer> program) {
    int instructionPointer = 0;
    List<Integer> outputs = new ArrayList<>();
    long[] registers = new long[] {registerA, 0L, 0L};
    while (instructionPointer < program.size()) {
      instructionPointer = processIteration(registers, program, outputs, instructionPointer);
    }
    return outputs;
  }

  public static List<Integer> processData(long[] registers, List<Integer> program) {
    int instructionPointer = 0;
    List<Integer> outputs = new ArrayList<>();
    while (instructionPointer < program.size()) {
      instructionPointer = processIteration(registers, program, outputs, instructionPointer);
    }
    return outputs;
  }

  public static int processIteration(
      long[] registers, List<Integer> program, List<Integer> outputs, int instructionPointer) {
    boolean jump = false;
    int opCode = program.get(instructionPointer);
    int literalOperand = program.get(instructionPointer + 1);
    long comboOperand = literalOperand;
    if (literalOperand == 4) {
      comboOperand = registers[0];
    } else if (literalOperand == 5) {
      comboOperand = registers[1];
    } else if (literalOperand == 6) {
      comboOperand = registers[2];
    }
    if (opCode == 0) {
      registers[0] = registers[0] / (long) Math.pow(2, comboOperand);
    } else if (opCode == 1) {
      registers[1] = registers[1] ^ literalOperand;
    } else if (opCode == 2) {
      registers[1] = comboOperand % 8;
    } else if (opCode == 3) {
      if (registers[0] != 0L) {
        jump = true;
        instructionPointer = literalOperand;
      }
    } else if (opCode == 4) {
      registers[1] = registers[1] ^ registers[2];
    } else if (opCode == 5) {
      outputs.add((int) (comboOperand % 8));
    } else if (opCode == 6) {
      registers[1] = registers[0] / (long) Math.pow(2, comboOperand);
    } else if (opCode == 7) {
      registers[2] = registers[0] / (long) Math.pow(2, comboOperand);
    }
    if (!jump) {
      instructionPointer += 2;
    }
    return instructionPointer;
  }
}
