package no.ntnu.os2024.group3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class CpuScheduling {

  public static void fcfs(Process[] processes) {
    Arrays.sort(processes, Comparator.comparingInt(Process::getArrivalTime));
    int currentTime = 0;
    int totalWaitingTime = 0;
    int totalTurnaroundTime = 0;

    for (Process process : processes) {
      if (currentTime < process.getArrivalTime()) {
        currentTime = process.getArrivalTime();
      }
      process.setWaitingTime(currentTime - process.getArrivalTime());
      process.setCompletionTime(currentTime + process.getBurstTime());
      process.setTurnaroundTime(process.getCompletionTime() - process.getArrivalTime());

      currentTime += process.getBurstTime();

      totalWaitingTime += process.getWaitingTime();
      totalTurnaroundTime += process.getTurnaroundTime();
    }

    System.out.println("First Come First Serve (FCFS) Scheduling:");
    for (Process process : processes) {
      System.out.println(
        "Process ID: " +
        process.getProcessID() +
        ", Waiting Time: " +
        process.getWaitingTime() +
        ", Turnaround Time: " +
        process.getTurnaroundTime()
      );
    }

    System.out.println(
      "Average Waiting Time: " + (double) totalWaitingTime / processes.length
    );
    System.out.println(
      "Average Turnaround Time: " +
      (double) totalTurnaroundTime /
      processes.length
    );
  }

  public static void PreemptivePriority(Process[] processes) {
    System.out.println(
      "Preemptive Priority Scheduling is not implemented yet."
    );
  }

  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);

    int numberOfProcesses = 10;
    Process[] fcfsProcesses = new Process[numberOfProcesses];
    for (int i = 0; i < numberOfProcesses; i++) {
      fcfsProcesses[i] = Process.generateRandomProcess(String.valueOf(i));
    }

    fcfs(fcfsProcesses);
  }
}
