package Revision_Multithreading;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class ComputeRecursiveSum extends RecursiveTask<Integer> {
    private static final int THRESHOLD = 10; // Threshold for splitting
    private int[] array;
    private int start;
    private int end;

    public ComputeRecursiveSum(int[] array, int start, int end){
        this.array = array;
        this.start = start;
        this.end = end ;
    }

    @Override
    protected Integer compute() {
        if (end - start <= THRESHOLD) { // Base case: compute directly
            int sum = 0;
            for (int i = start; i < end; i++) {
                sum += array[i];
            }
            return sum;
        }

        int mid = (start+end)/2;

        ComputeRecursiveSum left = new ComputeRecursiveSum(array, start, mid) ;
        ComputeRecursiveSum right = new ComputeRecursiveSum(array, mid, end) ;

        left.fork();// Start left task asynchronously
        int rightSum = right.compute();// Compute right task directly
        int leftSum = left.join(); // wait for left task

        return leftSum+rightSum;
    }
    
}


public class RecursiveSum {
    public static void main(String[] args) {
        int array[] = new int[100];

        for(int i=0;i<100;i++){
            array[i] = i+1;
        }

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        ComputeRecursiveSum sumTask = new ComputeRecursiveSum(array, 0, 100) ;
        int sum = forkJoinPool.invoke(sumTask) ;

        System.out.println("Sum is : "+sum);
    }
}
