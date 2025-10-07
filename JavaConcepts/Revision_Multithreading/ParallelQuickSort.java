package Revision_Multithreading;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

@SuppressWarnings("unused")
class QuickSortTask extends RecursiveTask<Void>{
    private int[] array;
    private int start;
    private int end;

    public QuickSortTask(int[] array, int start, int end){
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Void compute() {
        if(start<end){
            int x = partition(array, start, end);

            QuickSortTask leftTask = new QuickSortTask(array, start, x-1);
            QuickSortTask righTask = new QuickSortTask(array, x+1, end);

            leftTask.fork();
            righTask.compute();

            leftTask.join();
        }
        return null;
    }

    public void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    } 
    public int partition(int[] array, int start, int end){
        int pivot = array[end] ;
        int i = start-1;
        for(int j=start; j<=end; j++){
            if(array[j]<pivot){
                i++;
                swap(array, i, j);
            }
        }
        // Move pivot after smaller elements and
        // return its position
        swap(array, i + 1, end);  
        return i + 1;
    }
    
}

public class ParallelQuickSort {
    public static void main(String[] args) {
        int[] array = {15,10,17,19,7,6,90,80,100,95,93,97};
        System.out.println("Before sort : "+Arrays.toString(array));

        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        forkJoinPool.invoke(new QuickSortTask(array, 0, array.length-1));

        System.out.println("After sort : "+Arrays.toString(array));
    }
}
