package Threads;

import java.util.concurrent.RecursiveTask;
import java.util.concurrent.ForkJoinPool;

class SumTask extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 10; // Threshold for splitting
    private int[] array;
    private int start, end;

    public SumTask(int[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) { // Base case: compute directly
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        } else { // Recursive case: split into two tasks
            int mid = (start + end) / 2;
            SumTask leftTask = new SumTask(array, start, mid);
            SumTask rightTask = new SumTask(array, mid, end);
            
            leftTask.fork(); // Start left task asynchronously
            int rightResult = rightTask.compute(); // Compute right task directly
            int leftResult = leftTask.join(); // Wait for left task
            
            return leftResult + rightResult;
        }
    }
}

public class RecursiveSum {
    public static void main(String[] args) {
        int[] array = new int[100];
        for (int i = 0; i < array.length; i++) {
            array[i] = i + 1; // Filling the array with values 1 to 100
        }

        ForkJoinPool pool = new ForkJoinPool();
        SumTask task = new SumTask(array, 0, array.length);
        int sum = pool.invoke(task);
        
        System.out.println("Sum of array elements: " + sum);
    }
}

