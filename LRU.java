import java.util.Scanner;

class LRU {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

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

        // initialize
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
                int pos = -1, min = 9999;
                for (int j = 0; j < m; j++) {
                    if (frame[j] == -1) { pos = j; break; } // empty slot
                    if (recent[j] < min) { min = recent[j]; pos = j; } // least used
                }
                frame[pos] = page;
                recent[pos] = i;
                pageFaults++;
            }

            // print current frames
            System.out.print("Page " + page + " -> ");
            for (int j = 0; j < m; j++)
                if (frame[j] == -1) System.out.print("[ ] ");
                else System.out.print("[" + frame[j] + "] ");
            System.out.println();
        }

        System.out.println("\nTotal Page Faults: " + pageFaults);
    }
}
