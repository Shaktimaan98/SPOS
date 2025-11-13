import java.util.*;

public class FIFO {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of frames: ");
        int frames = sc.nextInt();

        System.out.print("Enter length of reference string: ");
        int n = sc.nextInt();

        int[] ref = new int[n];
        int[] mem = new int[frames];
        int pointer = 0, hit = 0, fault = 0;

        // initialize memory frames as empty
        for(int i=0;i<frames;i++)
            mem[i] = -1;

        System.out.println("Enter reference string: ");
        for(int i=0;i<n;i++)
            ref[i] = sc.nextInt();

        // FIFO Logic
        for(int i=0;i<n;i++){
            boolean found = false;

            // Check if page is already in memory
            for(int j=0;j<frames;j++){
                if(mem[j] == ref[i]){
                    found = true;
                    hit++;
                    break;
                }
            }

            // If not found → replace using FIFO pointer
            if(!found){
                mem[pointer] = ref[i];
                pointer = (pointer + 1) % frames; // circular movement
                fault++;
            }

            // Print memory status each step
            System.out.print("Step " + (i+1) + ": ");
            for(int j=0;j<frames;j++)
                System.out.print(mem[j] + " ");
            System.out.println();
        }

        System.out.println("\nTotal Hits: " + hit);
        System.out.println("Total Faults: " + fault);
        System.out.println("Hit Ratio: " + (float)hit/n);
    }
}
