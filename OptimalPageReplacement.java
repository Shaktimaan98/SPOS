import java.util.*;

public class OptimalPageReplacement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of frames: ");
        int frames = sc.nextInt();

        System.out.print("Enter length of reference string: ");
        int n = sc.nextInt();

        int ref[] = new int[n];
        System.out.println("Enter reference string:");
        for(int i = 0; i < n; i++)
            ref[i] = sc.nextInt();

        int buffer[] = new int[frames];
        Arrays.fill(buffer, -1);    // initialize all frames as empty

        int hit = 0, fault = 0;

        System.out.println("\nFrame Table:\n");
        for(int i = 0; i < n; i++) {
            int page = ref[i];
            boolean found = false;

            // Check Hit
            for(int j = 0; j < frames; j++) {
                if(buffer[j] == page) {
                    found = true;
                    hit++;
                    break;
                }
            }

            // If Miss (Fault occurs)
            if(!found) {
                fault++;

                // If free frame exists, use it
                boolean placed = false;
                for(int j = 0; j < frames; j++) {
                    if(buffer[j] == -1) {
                        buffer[j] = page;
                        placed = true;
                        break;
                    }
                }

                // If no free frame → apply Optimal Replacement
                if(!placed) {
                    int indexToReplace = -1;
                    int farthest = -1;

                    for(int j = 0; j < frames; j++) {
                        int nextUse = Integer.MAX_VALUE;
                        for(int k = i+1; k < n; k++) {
                            if(buffer[j] == ref[k]) {
                                nextUse = k;
                                break;
                            }
                        }

                        if(nextUse > farthest) {
                            farthest = nextUse;
                            indexToReplace = j;
                        }
                    }
                    buffer[indexToReplace] = page;
                }
            }

            // Print frame status
            for(int j = 0; j < frames; j++) {
                if(buffer[j] == -1)
                    System.out.print("- ");
                else
                    System.out.print(buffer[j] + " ");
            }
            System.out.println();
        }

        System.out.println("\nTotal Hits   : " + hit);
        System.out.println("Total Faults : " + fault);
        System.out.println("Hit Ratio    : " + ((float)hit / n));
    }
}
