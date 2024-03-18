package org.example;

import java.util.Scanner;
import java.lang.System;

public class Main {
    public static void main(String[] args){
        int threadCount = 5;
        TaskQueue taskQueue = new TaskQueue();
        ResultQueue resultQueue = new ResultQueue();
        Operator[] newOperator = new Operator[threadCount];

        Thread[] threads = new Thread[threadCount];
        for (int i = 0; i < threadCount; i++){
            newOperator[i] = new Operator(taskQueue,resultQueue);
            threads[i] = new Thread(newOperator[i]);
            threads[i].start();
        }
        Scanner sc = new Scanner(System.in);
        boolean isRunning = true;
        int taskID = 1;
        mainloop:
        while(isRunning){
            System.out.println("1 - add number\n2 - show results\n3 - quit");
            int choise = Integer.parseInt(sc.nextLine());
            if(choise == 1){
                System.out.println("Enter a Number:");
                String input = sc.nextLine();
                int taskNumber = Integer.parseInt(input);
                taskQueue.AddTask(taskID, taskNumber);
                taskID++;

            }
            else if(choise == 2){
                System.out.println(resultQueue);
            }
            else if(choise == 3){
                System.out.println("\nExiting...");
                break mainloop;
            }

        }
        sc.close();
        for(Thread thread: threads){
            thread.interrupt();
        }
    }

}