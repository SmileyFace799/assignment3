package no.ntnu.os2024.group3;

import java.util.Random;

public class Process {
    private static final Random RANDOM = new Random();

    private final String processID;
    private final int arrivalTime;
    private final int burstTime;
    private final int priority;
    private int waitingTime;
    private int completionTime;
    private int turnaroundTime;

    public Process(String processID, int arrivalTime, int burstTime, int priority) {
        this.processID = processID;
        this.arrivalTime = arrivalTime;
        this.burstTime = burstTime;
        this.priority = priority;
        this.waitingTime = 0;
        this.completionTime = 0;
        this.turnaroundTime = 0;
    }

    public static Process generateRandomProcess(String processID, int spread) {
        return new Process(
                processID,
                RANDOM.nextInt(0, (int) (64.5 * spread)),
                RANDOM.nextInt(1, 128),
                RANDOM.nextInt(-128, 128)
        );
    }

    public String getProcessID() {
        return processID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getCompletionTime() {
        return completionTime;
    }

    public int getTurnaroundTime() {
        return turnaroundTime;
    }

    public void setWaitingTime(int waitingTime) {
        this.waitingTime = waitingTime;
    }

    public void setCompletionTime(int completionTime) {
        this.completionTime = completionTime;
    }

    public void setTurnaroundTime(int turnaroundTime) {
        this.turnaroundTime = turnaroundTime;
    }
}