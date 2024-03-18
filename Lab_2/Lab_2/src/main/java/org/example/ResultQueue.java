package org.example;
import java.util.LinkedList;
import java.util.Queue;

class QueueOut{
    private final Integer ID;
    private final Double piValue;
    private final Double percents;
    public QueueOut(Integer ID, Double piValue, Double percents){
        this.ID=ID;
        this.piValue=piValue;
        this.percents=percents;
    }
    public int getID(){
        return ID;
    }

    public double getPiValue(){
        return piValue;
    }
    public double getPercents(){
        return percents;
    }
    @Override
    public String toString() {
        return "\n" +
                "ID= " + ID +
                ", pi= " + piValue +
                ", percents= " + percents+
                "%";
    }
}
public class ResultQueue {
    LinkedList<QueueOut> resultQueue = new LinkedList<>();

    public synchronized void addResult(int ID,double piValue, double percents){
        try {
            resultQueue.addLast(new QueueOut(ID, piValue,percents));
        } catch (NumberFormatException e) {
            System.err.println("Invalid result: " + ID);
            // You might choose to log the error, throw a custom exception, or handle it differently based on your requirements
        }
    }
    public synchronized void removeResult(int taskId) {
        // Find and remove element by ID
        for (QueueOut result : resultQueue) {
            if (result.getID() == taskId) {
                resultQueue.remove(result);
                break; // Exit loop once element is removed
            }
        }
    }
    @Override
    public String toString() {
        synchronized (this) {
            return "" + new LinkedList<>(resultQueue);
        }
    }


}