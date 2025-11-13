import java.util.*;

public class RoundRobin {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int at[] = new int[n];  // arrival time
        int bt[] = new int[n];  // burst time
        int rt[] = new int[n];  // remaining time
        int wt[] = new int[n];
        int tat[] = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("\nArrival time of P" + (i+1) + ": ");
            at[i] = sc.nextInt();
            System.out.print("Burst time of P" + (i+1) + ": ");
            bt[i] = sc.nextInt();
            rt[i] = bt[i];
        }

        System.out.print("\nEnter Time Quantum: ");
        int tq = sc.nextInt();

        int time = 0, done;

        do {
            done = 1;
            for (int i = 0; i < n; i++) {
                if (rt[i] > 0 && at[i] <= time) {
                    done = 0;
                    if (rt[i] > tq) {
                        time += tq;
                        rt[i] -= tq;
                    } else {
                        time += rt[i];
                        wt[i] = time - at[i] - bt[i];
                        rt[i] = 0;
                    }
                }
            }
            // if no process arrived yet, move time forward
            time++;
        } while (done == 0);

        for (int i = 0; i < n; i++)
            tat[i] = bt[i] + wt[i];

        System.out.println("\nProcess\tAT\tBT\tWT\tTAT");
        for (int i = 0; i < n; i++)
            System.out.println("P" + (i+1) + "\t" + at[i] + "\t" + bt[i] + "\t" + wt[i] + "\t" + tat[i]);
    }
}
