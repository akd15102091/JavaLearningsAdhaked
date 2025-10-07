package Revision_Multithreading;

import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

class MergeSortTask extends RecursiveTask<Void>{
    private int[] array;
    private int start, end;

    public MergeSortTask(int[] array, int start, int end){
        this.array = array;
        this.start = start;
        this.end = end;
    }

    private void merge(int[] array, int start, int mid, int end) {
        int n1 = mid-start+1 ;
        int n2 = end-mid ;
        int leftArr[] = new int[n1] ;
        int rightArr[] = new int[n2] ;

        for(int i=0; i<n1; i++){
            leftArr[i] = array[start+i] ;
        }
        for(int i=0; i<n2; i++){
            rightArr[i] = array[mid+i+1] ;
        }

        int k=start;
        int i=0, j=0;

        while(i<n1 && j<n2){
            if(leftArr[i] < rightArr[j]){
                array[k++] = leftArr[i++] ;
            }
            else{
                array[k++] = rightArr[j++] ;
            }
        }

        while (i<n1) {
            array[k++] = leftArr[i++] ;
        }
        while (j<n2) {
            array[k++] = rightArr[j++] ;
        }
    }

    @Override
    protected Void compute() {
        if(start<end){
            int mid = (start+end)/2;

            MergeSortTask left = new MergeSortTask(array, start, mid);
            MergeSortTask right = new MergeSortTask(array, mid+1, end);

            left.fork();

            right.compute();
            left.join();

            merge(array, start, mid, end) ;
         }

        return null;
    }
    
}

public class ThreadSafeMergeSort {
    public static void main(String[] args) {
        int[] array = {38, 27, 43, 3, 9, 82, 10};
        System.out.println("Unsorted: " + Arrays.toString(array));
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        forkJoinPool.invoke(new MergeSortTask(array, 0, array.length-1));
        System.out.println("Sorted: " + Arrays.toString(array));
    }
}
