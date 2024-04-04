package no.ntnu.os2024.group3;

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