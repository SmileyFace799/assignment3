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
			System.out.println("Process ID: "
					+ process.getProcessID()
					+ ", Waiting Time: "
					+ process.getWaitingTime()
					+ ", Turnaround Time: "
					+ process.getTurnaroundTime()
					+ ", Arrival Time: "
					+ process.getArrivalTime()
					+ ", Burst Time: "
					+ process.getBurstTime()
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

	public static void preemptivePriority(Process[] processes) {
		System.out.println(
				"Preemptive Priority Scheduling is not implemented yet."
		);
	}

  public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter the number of processes:");
    int numberOfProcesses = scanner.nextInt();
    Process[] processes = new Process[numberOfProcesses];

    System.out.println("Enter process details manually or generate them randomly? (Enter 'manual' or 'random')");
    String userInput = scanner.next().trim().toLowerCase();

    if ("manual".equals(userInput)) {
        System.out.println("Enter process details:");
        for (int i = 0; i < numberOfProcesses; i++) {
            System.out.println("Process " + (i + 1) + " ID:");
            String processID = scanner.next();
            System.out.println("Arrival Time:");
            int arrivalTime = scanner.nextInt();
            System.out.println("Burst Time:");
            int burstTime = scanner.nextInt();
            System.out.println("Priority:");
            int priority = scanner.nextInt();
            processes[i] = new Process(processID, arrivalTime, burstTime, priority);
        }
    } else if ("random".equals(userInput)) {
        System.out.println("Generating random processes:");
        for (int i = 0; i < numberOfProcesses; i++) {
            processes[i] = Process.generateRandomProcess(String.valueOf(i + 1));
        }
    } else {
        System.out.println("Invalid input. Exiting program.");
        scanner.close();
        return;
    }

    fcfs(Arrays.copyOf(processes, processes.length));

    // not done yet
    preemptivePriority(Arrays.copyOf(processes, processes.length));

    scanner.close();
}



}
