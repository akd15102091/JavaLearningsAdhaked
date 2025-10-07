package Revision;

import java.io.File;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/*
 * Approach: Recursive File Copy Using RecursiveTask
        - Divide the file into smaller chunks (e.g., 100MB each).
        - Use RecursiveTask<Long> to recursively copy each chunk.
        - Use RandomAccessFile for parallel read/write access.
        - Join the results to complete the copy process.
 */

public class ParallelFileCopy {
    private static final int CHUNK_SIZE = 100 * 1024 * 1024 ; //(100MB)
    private static ForkJoinPool forkJoinPool = ForkJoinPool.commonPool() ;
    public static void main(String[] args) {
        File sourceFile = new File("source.dat");
        File destFile = new File("destination.dat");

        long fileSize = sourceFile.length();
        FileCopyTask rootTask = new FileCopyTask(sourceFile, destFile, 0, fileSize);
        forkJoinPool.invoke(rootTask);

        System.out.println("File copy completed successfully!");
    }

    static class FileCopyTask extends RecursiveTask<Long>{
        private final File sourceFile;
        private final File destFile;
        private final long start;
        private final long end;

        public FileCopyTask(File source, File destination, long start, long end) {
            this.sourceFile = source;
            this.destFile = destination;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {
            long dataSize = end - start;
            if(dataSize <= CHUNK_SIZE){
                return copyChunk() ;
            }
            else {
                long mid = start + (dataSize / 2);
                FileCopyTask leftTask = new FileCopyTask(sourceFile, destFile, start, mid);
                FileCopyTask rightTask = new FileCopyTask(sourceFile, destFile, mid, end);

                leftTask.fork();  // Start left task in a separate thread
                long rightResult = rightTask.compute();  // Compute right task in the current thread
                long leftResult = leftTask.join();  // Wait for left task to complete

                return leftResult + rightResult;
            }
        }

        public Long copyChunk() {
            try(RandomAccessFile source = new RandomAccessFile(sourceFile, "r")){
                try (RandomAccessFile destination = new RandomAccessFile(destFile, "rw")) {
                    FileChannel sourceChannel = source.getChannel();
                    FileChannel destinationChannel = destination.getChannel();
                    source.seek(start);
                    destination.seek(start);
                    long bytesToCopy = end - start;
                    return sourceChannel.transferTo(start, bytesToCopy, destinationChannel) ;
                }
                
            } catch (Exception e) {
                e.printStackTrace();
                return 0l;
            } 
        }
        
    }
}
