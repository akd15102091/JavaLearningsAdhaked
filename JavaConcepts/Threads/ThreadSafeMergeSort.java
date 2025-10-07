package Threads;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.Arrays;

public class ThreadSafeMergeSort {
    private static final ForkJoinPool pool = new ForkJoinPool();

    public static void mergeSort(int[] array) {
        if (array == null || array.length < 2) {
            return;
        }
        pool.invoke(new MergeSortTask(array, 0, array.length - 1));
    }

    private static class MergeSortTask extends RecursiveTask<Void> {
        private final int[] array;
        private final int left;
        private final int right;

        MergeSortTask(int[] array, int left, int right) {
            this.array = array;
            this.left = left;
            this.right = right;
        }

        @Override
        protected Void compute() {
            if (left < right) {
                int mid = left + (right - left) / 2;
                MergeSortTask leftTask = new MergeSortTask(array, left, mid);
                MergeSortTask rightTask = new MergeSortTask(array, mid + 1, right);
                
                leftTask.fork();
                rightTask.compute();
                leftTask.join();
                
                // invokeAll(leftTask, rightTask);
                
                merge(left, mid, right);
            }
            return null;
        }

        private void merge(int left, int mid, int right) {
            int[] leftArray = Arrays.copyOfRange(array, left, mid + 1);
            int[] rightArray = Arrays.copyOfRange(array, mid + 1, right + 1);

            int i = 0, j = 0, k = left;
            while (i < leftArray.length && j < rightArray.length) {
                if (leftArray[i] <= rightArray[j]) {
                    array[k++] = leftArray[i++];
                } else {
                    array[k++] = rightArray[j++];
                }
            }
            
            while (i < leftArray.length) {
                array[k++] = leftArray[i++];
            }
            while (j < rightArray.length) {
                array[k++] = rightArray[j++];
            }
        }
    }

    public static void main(String[] args) {
        int[] array = {38, 27, 43, 3, 9, 82, 10};
        System.out.println("Unsorted: " + Arrays.toString(array));
        mergeSort(array);
        System.out.println("Sorted: " + Arrays.toString(array));
    }
}

