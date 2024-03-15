package org.example;

import java.util.LinkedList;
import java.util.Queue;
public class TaskQueue {
    private final Queue<String> taskQueue = new LinkedList<>();

    public synchronized void AddTask(int taskID, int taskNumber) {
        this.taskQueue.add(taskID + " " + taskNumber);
        notify();
    }

    public synchronized String GetTask() throws InterruptedException {
        if (taskQueue.isEmpty()) {
            wait();
        }
        return taskQueue.poll();
    }
}


