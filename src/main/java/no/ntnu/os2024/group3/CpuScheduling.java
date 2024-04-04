package no.ntnu.os2024.group3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class CpuScheduling {

	public static double[] fcfs(Process[] processes) {
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
		return new double[]{
				(double) totalWaitingTime / processes.length,
				(double) totalTurnaroundTime / processes.length
		};
	}

	public static double[] preemptivePriority(Process[] processesArray) {
		final AtomicInteger currentTime = new AtomicInteger(0);
		final AtomicInteger totalWaitingTime = new AtomicInteger(0);
		final AtomicInteger totalTurnaroundTime = new AtomicInteger(0);

		List<Process> processes = new ArrayList<>(Arrays.asList(processesArray));
		List<Process> finishedProcesses = new ArrayList<>();
		while (!processes.isEmpty()) {
			List<Process> arrivedProcesses = processes
					.stream()
					.filter(p -> p.getArrivalTime() <= currentTime.get())
					.toList();
			if (arrivedProcesses.isEmpty()) {
				currentTime.set(processes.stream().mapToInt(Process::getArrivalTime).min().getAsInt());
			} else {
				arrivedProcesses
						.stream()
						.sorted(Comparator.comparingInt(Process::getArrivalTime))
						.min(Comparator.comparingInt(Process::getPriority))
						.ifPresent(next -> {
							next.setWaitingTime(currentTime.get() - next.getArrivalTime());
							next.setCompletionTime(currentTime.get() + next.getBurstTime());
							next.setTurnaroundTime(next.getCompletionTime() - next.getArrivalTime());

							currentTime.addAndGet(next.getBurstTime());
							totalWaitingTime.addAndGet(next.getWaitingTime());
							totalTurnaroundTime.addAndGet(next.getTurnaroundTime());

							processes.remove(next);
							finishedProcesses.add(next);
						});
			}
		}

		System.out.println("Preemptive Priority Scheduling:");
		for (Process process : finishedProcesses) {
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
					+ ", Priority: "
					+ process.getPriority()
			);
		}
		return new double[]{
				totalWaitingTime.doubleValue() / processesArray.length,
				totalTurnaroundTime.doubleValue() / processesArray.length
		};
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
				processes[i] = Process.generateRandomProcess(String.valueOf(i + 1), numberOfProcesses);
			}
		} else {
			System.out.println("Invalid input. Exiting program.");
			scanner.close();
			return;
		}
		scanner.close();
		double[] valuesFcfs = fcfs(Arrays.copyOf(processes, processes.length));
		double[] valuesPp = preemptivePriority(processes);
		System.out.println("First come first served (FCFS) scheduling:");
		System.out.println(
				"Average Waiting Time: " + valuesFcfs[0]
		);
		System.out.println(
				"Average Turnaround Time: " + valuesFcfs[1]
		);
		System.out.println("Preemptive Priority scheduling:");
		System.out.println(
				"Average Waiting Time: " + valuesPp[0]
		);
		System.out.println(
				"Average Turnaround Time: " + valuesPp[1]
		);
	}
}
