
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class BankersAlgorithm {
    static int P; // Number of processes
    static int R; // Number of resources

    static void calculateNeed(int[][] need, int[][] max, int[][] allot) {
        for (int i = 0; i < P; i++) {
            for (int j = 0; j < R; j++) {
                need[i][j] = max[i][j] - allot[i][j];
            }
        }
    }

    static boolean isSafe(int[] processes, int[] avail, int[][] max, int[][] allot, String outputFilePath) {
        int[][] need = new int[P][R];
        calculateNeed(need, max, allot);

        boolean[] finish = new boolean[P];
        int[] safeSeq = new int[P];
        int[] work = new int[R];
        
        for (int i = 0; i < R; i++) {
            work[i] = avail[i];
        }

        int count = 0;
        while (count < P) {
            boolean found = false;
            for (int p = 0; p < P; p++) {
                if (!finish[p]) {
                    int j;
                    for (j = 0; j < R; j++) {
                        if (need[p][j] > work[j]) {
                            break;
                        }
                    }
                    if (j == R) {
                        for (int k = 0; k < R; k++) {
                            work[k] += allot[p][k];
                        }
                        safeSeq[count++] = p;
                        finish[p] = true;
                        found = true;
                    }
                }
            }
            if (!found) {
                try (FileWriter writer = new FileWriter(outputFilePath)) {
                    writer.write("System is not in a safe state\n");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }
        }

        try (FileWriter writer = new FileWriter(outputFilePath)) {
            writer.write("System is in a safe state\nSafe sequence is: ");
            for (int i = 0; i < P; i++) {
                writer.write(safeSeq[i] + " ");
            }
            writer.write("\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static void main(String[] args) {
        String inputFilePath = "input.txt";
        String outputFilePath = "output.txt";

        try (Scanner scanner = new Scanner(new File(inputFilePath))) {
            P = scanner.nextInt();
            R = scanner.nextInt();

            int[] processes = new int[P];
            for (int i = 0; i < P; i++) {
                processes[i] = i;
            }

            int[][] max = new int[P][R];
            int[][] allot = new int[P][R];
            int[] avail = new int[R];

            for (int i = 0; i < P; i++) {
                for (int j = 0; j < R; j++) {
                    allot[i][j] = scanner.nextInt();
                }
            }

            for (int i = 0; i < P; i++) {
                for (int j = 0; j < R; j++) {
                    max[i][j] = scanner.nextInt();
                }
            }

            for (int i = 0; i < R; i++) {
                avail[i] = scanner.nextInt();
            }

            isSafe(processes, avail, max, allot, outputFilePath);

        } catch (FileNotFoundException e) {
            System.out.println("Input file not found: " + inputFilePath);
            e.printStackTrace();
        }
    }
}
