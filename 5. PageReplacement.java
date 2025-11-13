import java.util.*;

public class PageReplacement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Choose Page Replacement Algorithm:");
        System.out.println("1. FIFO");
        System.out.println("2. LRU");
        System.out.println("3. OPTIMAL");
        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        switch (choice) {
            case 1:
                fifo(sc);
                break;
            case 2:
                lru(sc);
                break;
            case 3:
                optimal(sc);
                break;
            default:
                System.out.println("Invalid choice!");
        }

        sc.close();
    }

    // ---------------- FIFO ALGORITHM ----------------
    public static void fifo(Scanner sc) {
        System.out.print("Enter number of frames: ");
        int frames = sc.nextInt();

        System.out.print("Enter length of reference string: ");
        int n = sc.nextInt();

        int[] ref = new int[n];
        int[] mem = new int[frames];
        int pointer = 0, hit = 0, fault = 0;

        // initialize memory frames as empty
        for (int i = 0; i < frames; i++)
            mem[i] = -1;

        System.out.println("Enter reference string: ");
        for (int i = 0; i < n; i++)
            ref[i] = sc.nextInt();

        // FIFO Logic
        for (int i = 0; i < n; i++) {
            boolean found = false;

            // Check if page is already in memory
            for (int j = 0; j < frames; j++) {
                if (mem[j] == ref[i]) {
                    found = true;
                    hit++;
                    break;
                }
            }

            // If not found → replace using FIFO pointer
            if (!found) {
                mem[pointer] = ref[i];
                pointer = (pointer + 1) % frames; // circular replacement
                fault++;
            }

            // Print memory status each step
            System.out.print("Step " + (i + 1) + ": ");
            for (int j = 0; j < frames; j++)
                System.out.print(mem[j] + " ");
            System.out.println();
        }

        System.out.println("\nTotal Hits: " + hit);
        System.out.println("Total Faults: " + fault);
        System.out.println("Hit Ratio: " + (float) hit / n);
    }

    // ---------------- LRU ALGORITHM ----------------
    public static void lru(Scanner sc) {
        System.out.print("Enter number of pages: ");
        int n = sc.nextInt();
        int[] pages = new int[n];

        System.out.println("Enter page reference string:");
        for (int i = 0; i < n; i++)
            pages[i] = sc.nextInt();

        System.out.print("Enter frame size: ");
        int m = sc.nextInt();
        int[] frame = new int[m];
        int[] recent = new int[m]; // tracks how recently used
        int pageFaults = 0;

        // initialize frames
        for (int i = 0; i < m; i++) {
            frame[i] = -1;
            recent[i] = 0;
        }

        for (int i = 0; i < n; i++) {
            int page = pages[i];
            boolean found = false;

            // check if page already in frame
            for (int j = 0; j < m; j++) {
                if (frame[j] == page) {
                    found = true;
                    recent[j] = i; // update usage time
                    break;
                }
            }

            // if not found → page fault
            if (!found) {
                int pos = -1, min = Integer.MAX_VALUE;
                for (int j = 0; j < m; j++) {
                    if (frame[j] == -1) { 
                        pos = j; 
                        break; 
                    }
                    if (recent[j] < min) { 
                        min = recent[j]; 
                        pos = j; 
                    }
                }
                frame[pos] = page;
                recent[pos] = i;
                pageFaults++;
            }

            // print current frames
            System.out.print("Page " + page + " -> ");
            for (int j = 0; j < m; j++)
                if (frame[j] == -1)
                    System.out.print("[ ] ");
                else
                    System.out.print("[" + frame[j] + "] ");
            System.out.println();
        }

        System.out.println("\nTotal Page Faults: " + pageFaults);
    }

    // ---------------- OPTIMAL ALGORITHM ----------------
    public static void optimal(Scanner sc) {
        System.out.print("Enter number of frames: ");
        int frames = sc.nextInt();

        System.out.print("Enter length of reference string: ");
        int n = sc.nextInt();

        int ref[] = new int[n];
        System.out.println("Enter reference string:");
        for (int i = 0; i < n; i++)
            ref[i] = sc.nextInt();

        int buffer[] = new int[frames];
        Arrays.fill(buffer, -1); // initialize all frames as empty

        int hit = 0, fault = 0;

        System.out.println("\nFrame Table:\n");
        for (int i = 0; i < n; i++) {
            int page = ref[i];
            boolean found = false;

            // Check Hit
            for (int j = 0; j < frames; j++) {
                if (buffer[j] == page) {
                    found = true;
                    hit++;
                    break;
                }
            }

            // If Miss (Fault occurs)
            if (!found) {
                fault++;

                // If free frame exists, use it
                boolean placed = false;
                for (int j = 0; j < frames; j++) {
                    if (buffer[j] == -1) {
                        buffer[j] = page;
                        placed = true;
                        break;
                    }
                }

                // If no free frame → apply Optimal Replacement
                if (!placed) {
                    int indexToReplace = -1;
                    int farthest = -1;

                    for (int j = 0; j < frames; j++) {
                        int nextUse = Integer.MAX_VALUE;
                        for (int k = i + 1; k < n; k++) {
                            if (buffer[j] == ref[k]) {
                                nextUse = k;
                                break;
                            }
                        }

                        if (nextUse > farthest) {
                            farthest = nextUse;
                            indexToReplace = j;
                        }
                    }
                    buffer[indexToReplace] = page;
                }
            }

            // Print frame status
            for (int j = 0; j < frames; j++) {
                if (buffer[j] == -1)
                    System.out.print("- ");
                else
                    System.out.print(buffer[j] + " ");
            }
            System.out.println();
        }

        System.out.println("\nTotal Hits   : " + hit);
        System.out.println("Total Faults : " + fault);
        System.out.println("Hit Ratio    : " + ((float) hit / n));
    }
}
