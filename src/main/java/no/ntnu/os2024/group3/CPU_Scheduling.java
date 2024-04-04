package no.ntnu.os2024.group3;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

class Process {

  String processID;
  int arrivalTime;
  int burstTime;
  int priority;
  int waitingTime;
  int completionTime;
  int turnaroundTime;

  public Process(String pid, int arrTime, int burst, int prio) {
    processID = pid;
    arrivalTime = arrTime;
    burstTime = burst;
    priority = prio;
    waitingTime = 0;
    completionTime = 0;
    turnaroundTime = 0;
  }
}

public class CPU_Scheduling {

  public static void FCFS(Process[] processes) {
    Arrays.sort(processes, Comparator.comparingInt(p -> p.arrivalTime));
    int currentTime = 0;
    int totalWaitingTime = 0;
    int totalTurnaroundTime = 0;

    for (Process process : processes) {
      if (currentTime < process.arrivalTime) {
        currentTime = process.arrivalTime;
      }
      process.waitingTime = currentTime - process.arrivalTime;
      process.completionTime = currentTime + process.burstTime;
      process.turnaroundTime = process.completionTime - process.arrivalTime;

      currentTime += process.burstTime;

      totalWaitingTime += process.waitingTime;
      totalTurnaroundTime += process.turnaroundTime;
    }

    System.out.println("First Come First Serve (FCFS) Scheduling:");
    for (Process process : processes) {
      System.out.println(
        "Process ID: " +
        process.processID +
        ", Waiting Time: " +
        process.waitingTime +
        ", Turnaround Time: " +
        process.turnaroundTime
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

    Process[] fcfsProcesses = {
      new Process("P1", 0, 5, -1),
      new Process("P2", 1, 3, -1),
      new Process("P3", 2, 8, -1),
      new Process("P4", 3, 6, -1),
    };

    FCFS(fcfsProcesses);
  }
}
