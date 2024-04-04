package no.ntnu.os2024.group3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

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

	public static void preemptivePriority(Process[] processesArray) {
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

		System.out.println(
				"Average Waiting Time: " + totalWaitingTime.doubleValue() / processesArray.length
		);
		System.out.println(
				"Average Turnaround Time: " +
						totalTurnaroundTime.doubleValue() / processesArray.length
		);
	}

	public static void main(String[] args) {
		int numberOfProcesses = 10;
		Process[] processes = new Process[numberOfProcesses];
		for (int i = 0; i < numberOfProcesses; i++) {
			processes[i] = Process.generateRandomProcess(String.valueOf(i));
		}

		fcfs(Arrays.copyOf(processes, processes.length));
		preemptivePriority(processes);
	}
}
