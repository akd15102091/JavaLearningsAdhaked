package UberLLDs.SortLargeFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class ExternalSort {
    // Number of lines we can hold in memory at a time (simulate limited RAM)
    private static final int CHUNK_SIZE = 50000;

    public static void main(String[] args) throws Exception {

        File input = new File("input.txt");          // Your input file
        File output = new File("sorted_output.txt"); // Final sorted file

        // Step 1: Split into sorted chunks
        List<File> chunks = createChunks(input);

        // Step 2: Merge all sorted chunks
        mergeChunks(chunks, output);

        System.out.println("Sorting completed!");
    }

    // ---------------------------------------------------------------------
    // STEP 1 — Read small parts → Sort → Write temp files
    // ---------------------------------------------------------------------
    private static List<File> createChunks(File inputFile) throws Exception {
        List<File> chunkFiles = new ArrayList<>();
        List<String> buffer = new ArrayList<>(CHUNK_SIZE);

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {

            String line;
            while ((line = br.readLine()) != null) {
                buffer.add(line);

                // If buffer is full → sort + write to temp file
                if (buffer.size() == CHUNK_SIZE) {
                    chunkFiles.add(writeChunk(buffer));
                    buffer.clear();
                }
            }

            // Write remaining last chunk
            if (!buffer.isEmpty()) {
                chunkFiles.add(writeChunk(buffer));
            }
        }

        return chunkFiles;
    }

    private static File writeChunk(List<String> buffer) throws Exception {

        // Sort by score (the second value in each line)
        buffer.sort((a, b) -> {
            int scoreA = Integer.parseInt(a.split(" ")[1]);
            int scoreB = Integer.parseInt(b.split(" ")[1]);
            return Integer.compare(scoreA, scoreB);
        });

        File temp = File.createTempFile("chunk_", ".txt");
        temp.deleteOnExit();

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(temp))) {
            for (String line : buffer) {
                bw.write(line);
                bw.newLine();
            }
        }

        return temp;
    }
    // ---------------------------------------------------------------------
    // STEP 2 — Merge sorted chunks using a Min-Heap
    // ---------------------------------------------------------------------
    private static class Node implements Comparable<Node> {
        String line;
        BufferedReader reader;

        Node(String line, BufferedReader reader) {
            this.line = line;
            this.reader = reader;
        }

        @Override
        public int compareTo(Node other) {
            int score1 = Integer.parseInt(this.line.split(" ")[1]);
            int score2 = Integer.parseInt(other.line.split(" ")[1]);
            return Integer.compare(score1, score2);
        }
    }

    private static void mergeChunks(List<File> chunkFiles, File outputFile) throws Exception {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        List<BufferedReader> readers = new ArrayList<>();

        // Initialize heap: read first line from each file
        for (File f : chunkFiles) {
            BufferedReader br = new BufferedReader(new FileReader(f));
            readers.add(br);
            String line = br.readLine();
            if (line != null) {
                pq.add(new Node(line, br));
            }
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile))) {

            while (!pq.isEmpty()) {
                Node smallest = pq.poll(); // get lowest score line
                bw.write(smallest.line);
                bw.newLine();

                // Read next line from same chunk
                String next = smallest.reader.readLine();
                if (next != null) {
                    pq.add(new Node(next, smallest.reader));
                }
            }
        }

        // Close all readers
        for (BufferedReader br : readers) {
            br.close();
        }
    }
}
