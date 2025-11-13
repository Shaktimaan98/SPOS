import java.util.*;

public class FCFS {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int p[] = new int[n];
        int at[] = new int[n];
        int bt[] = new int[n];
        int ct[] = new int[n];
        int tat[] = new int[n];
        int wt[] = new int[n];

        // Input
        for(int i=0;i<n;i++){
            p[i] = i+1;
            System.out.print("Enter Arrival Time of P"+p[i]+": ");
            at[i] = sc.nextInt();
            System.out.print("Enter CPU Time of P"+p[i]+": ");
            bt[i] = sc.nextInt();
        }

        // Sort by Arrival Time (Simple Bubble Sort)
        for(int i=0;i<n-1;i++){
            for(int j=i+1;j<n;j++){
                if(at[i] > at[j]){
                    // swap arrival
                    int temp = at[i]; at[i] = at[j]; at[j] = temp;
                    // swap burst
                    temp = bt[i]; bt[i] = bt[j]; bt[j] = temp;
                    // swap process number
                    temp = p[i]; p[i] = p[j]; p[j] = temp;
                }
            }
        }

        // Calculate Completion Time
        ct[0] = at[0] + bt[0];
        for(int i=1;i<n;i++){
            if(ct[i-1] < at[i])
                ct[i] = at[i] + bt[i];  // CPU idle
            else
                ct[i] = ct[i-1] + bt[i];
        }

        // Calculate TAT and WT
        float totalTAT = 0, totalWT = 0;
        for(int i=0;i<n;i++){
            tat[i] = ct[i] - at[i];
            wt[i] = tat[i] - bt[i];
            totalTAT += tat[i];
            totalWT += wt[i];
        }

        // Output
        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
        for(int i=0;i<n;i++){
            System.out.println("P"+p[i]+"\t"+at[i]+"\t"+bt[i]+"\t"+ct[i]+"\t"+tat[i]+"\t"+wt[i]);
        }

        System.out.println("\nAverage Turnaround Time: "+(totalTAT/n));
        System.out.println("Average Waiting Time: "+(totalWT/n));
    }
}
