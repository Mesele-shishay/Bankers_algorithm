import java.util.Scanner;

public class BankersAlgorithm {
    static int P;
    static int R;

    static void calculateNeed(int need[][], int max[][], int allot[][]) {
        for (int i = 0; i < P; i++) {
            for (int j = 0; j < R; j++) {
                need[i][j] = max[i][j] - allot[i][j];
            }
        }
    }

    static boolean isSafe(int processes[], int avail[], int max[][], int allot[][]) {
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
                System.out.println("System is not in a safe state.");
                return false;
            }
        }

        System.out.println("System is in a safe state.");
        System.out.print("Safe sequence is: ");
        for (int i = 0; i < P; i++) {
            System.out.print(safeSeq[i] + " ");
        }
        System.out.println();
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        P = scanner.nextInt();
        System.out.print("Enter number of resources: ");
        R = scanner.nextInt();

        int[] processes = new int[P];
        for (int i = 0; i < P; i++) {
            processes[i] = i;
        }

        int[][] allot = new int[P][R];
        int[][] max = new int[P][R];
        int[] avail = new int[R];

        System.out.println("Enter allocation matrix:");
        for (int i = 0; i < P; i++) {
            for (int j = 0; j < R; j++) {
                allot[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Enter maximum matrix:");
        for (int i = 0; i < P; i++) {
            for (int j = 0; j < R; j++) {
                max[i][j] = scanner.nextInt();
            }
        }

        System.out.println("Enter available resources:");
        for (int j = 0; j < R; j++) {
            avail[j] = scanner.nextInt();
        }

        isSafe(processes, avail, max, allot);
        scanner.close();
    }
}