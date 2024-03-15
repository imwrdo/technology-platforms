package org.example;
import java.lang.Math;

public class Operator implements Runnable{
    public final TaskQueue taskQueue;
    public final ResultQueue resultQueue;

    public Operator(TaskQueue taskQueue, ResultQueue resultQueue) {
        this.taskQueue = taskQueue;
        this.resultQueue = resultQueue;
    }

    @Override
    public void run() {
        while(true){
            try{
                String[] task = taskQueue.GetTask().split(" ");
                int taskId = Integer.parseInt(task[0]);
                int taskNumber = Integer.parseInt(task[1]);
                float res = 0;
                for (int i =1;i<taskNumber;i++ ){
                    res +=(Math.pow((-1),i-1)/2*i-1);
                    resultQueue.addResult(taskId,res*4,(float)i/(taskNumber*100));
                    Thread.sleep(20);
                    resultQueue.removeResult(taskId);
                }
                System.out.print("Pi value = " + res + ". Percents: " + 100.0 + "%.\n");
                resultQueue.addResult(taskId, res * 4, 100.0);

            }catch (InterruptedException e){
                break;
            }
        }
    }
}