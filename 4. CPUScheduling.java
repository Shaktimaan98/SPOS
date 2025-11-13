import java.util.*;

public class CPUScheduling {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\n===== CPU SCHEDULING ALGORITHMS =====");
            System.out.println("1. FCFS (First Come First Serve)");
            System.out.println("2. SJF (Shortest Job First)");
            System.out.println("3. Priority Scheduling");
            System.out.println("4. Round Robin");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    fcfs(sc);
                    break;
                case 2:
                    sjf(sc);
                    break;
                case 3:
                    priority(sc);
                    break;
                case 4:
                    roundRobin(sc);
                    break;
                case 5:
                    System.out.println("Exiting Program...");
                    break;
                default:
                    System.out.println("Invalid choice! Try again.");
            }
        } while (choice != 5);

        sc.close();
    }

    // ---------------- FCFS ----------------
    public static void fcfs(Scanner sc) {
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int p[] = new int[n];
        int at[] = new int[n];
        int bt[] = new int[n];
        int ct[] = new int[n];
        int tat[] = new int[n];
        int wt[] = new int[n];

        // Input
        for (int i = 0; i < n; i++) {
            p[i] = i + 1;
            System.out.print("Enter Arrival Time of P" + p[i] + ": ");
            at[i] = sc.nextInt();
            System.out.print("Enter CPU Time of P" + p[i] + ": ");
            bt[i] = sc.nextInt();
        }

        // Sort by Arrival Time
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (at[i] > at[j]) {
                    int temp = at[i]; at[i] = at[j]; at[j] = temp;
                    temp = bt[i]; bt[i] = bt[j]; bt[j] = temp;
                    temp = p[i]; p[i] = p[j]; p[j] = temp;
                }
            }
        }

        // Completion Time
        ct[0] = at[0] + bt[0];
        for (int i = 1; i < n; i++) {
            if (ct[i - 1] < at[i])
                ct[i] = at[i] + bt[i];
            else
                ct[i] = ct[i - 1] + bt[i];
        }

        float totalTAT = 0, totalWT = 0;
        for (int i = 0; i < n; i++) {
            tat[i] = ct[i] - at[i];
            wt[i] = tat[i] - bt[i];
            totalTAT += tat[i];
            totalWT += wt[i];
        }

        System.out.println("\nProcess\tAT\tBT\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + p[i] + "\t" + at[i] + "\t" + bt[i] + "\t" + ct[i] + "\t" + tat[i] + "\t" + wt[i]);
        }

        System.out.println("\nAverage Turnaround Time: " + (totalTAT / n));
        System.out.println("Average Waiting Time: " + (totalWT / n));
    }

    // ---------------- SJF ----------------
    public static void sjf(Scanner sc) {
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int process[] = new int[n];
        int arrival[] = new int[n];
        int cpu[] = new int[n];
        int finish[] = new int[n];
        int turntt[] = new int[n];
        int wait[] = new int[n];

        float total_tt = 0, total_waiting = 0;
        int sum = 0;

        for (int i = 0; i < n; i++) {
            System.out.print("Enter arrival time of P" + (i + 1) + ": ");
            arrival[i] = sc.nextInt();
            System.out.print("Enter CPU time of P" + (i + 1) + ": ");
            cpu[i] = sc.nextInt();
            process[i] = i + 1;
        }

        // Sort by CPU time
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (cpu[i] > cpu[j]) {
                    int temp = cpu[i]; cpu[i] = cpu[j]; cpu[j] = temp;
                    temp = arrival[i]; arrival[i] = arrival[j]; arrival[j] = temp;
                    temp = process[i]; process[i] = process[j]; process[j] = temp;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            sum += cpu[i];
            finish[i] = sum;
            turntt[i] = finish[i] - arrival[i];
            total_tt += turntt[i];
            wait[i] = turntt[i] - cpu[i];
            total_waiting += wait[i];
        }

        System.out.println("\nProcess\tAT\tBT\tTAT\tWT");
        for (int i = 0; i < n; i++)
            System.out.println("P" + process[i] + "\t" + arrival[i] + "\t" + cpu[i] + "\t" + turntt[i] + "\t" + wait[i]);

        System.out.println("\nAverage Turnaround Time: " + (total_tt / n));
        System.out.println("Average Waiting Time: " + (total_waiting / n));
    }

    // ---------------- PRIORITY ----------------
    public static void priority(Scanner sc) {
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int[] arrival = new int[n];
        int[] burst = new int[n];
        int[] priority = new int[n];
        int[] process = new int[n];
        int[] wait = new int[n];
        int[] tat = new int[n];
        int[] finish = new int[n];

        int sum = 0;
        float total_tt = 0, total_wait = 0;

        for (int i = 0; i < n; i++) {
            process[i] = i + 1;
            System.out.print("Enter arrival time of P" + process[i] + ": ");
            arrival[i] = sc.nextInt();
            System.out.print("Enter burst time of P" + process[i] + ": ");
            burst[i] = sc.nextInt();
            System.out.print("Enter priority of P" + process[i] + ": ");
            priority[i] = sc.nextInt();
        }

        // Sort by Priority (lower number = higher priority)
        for (int i = 0; i < n - 1; i++) {
            for (int j = i + 1; j < n; j++) {
                if (priority[i] > priority[j]) {
                    int temp = priority[i]; priority[i] = priority[j]; priority[j] = temp;
                    temp = burst[i]; burst[i] = burst[j]; burst[j] = temp;
                    temp = arrival[i]; arrival[i] = arrival[j]; arrival[j] = temp;
                    temp = process[i]; process[i] = process[j]; process[j] = temp;
                }
            }
        }

        for (int i = 0; i < n; i++) {
            sum += burst[i];
            finish[i] = sum;
            tat[i] = finish[i] - arrival[i];
            total_tt += tat[i];
            wait[i] = tat[i] - burst[i];
            total_wait += wait[i];
        }

        System.out.println("\nProcess\tAT\tBT\tPR\tCT\tTAT\tWT");
        for (int i = 0; i < n; i++) {
            System.out.println("P" + process[i] + "\t" + arrival[i] + "\t" + burst[i] + "\t" + priority[i]
                    + "\t" + finish[i] + "\t" + tat[i] + "\t" + wait[i]);
        }

        System.out.println("\nAverage Turnaround Time: " + (total_tt / n));
        System.out.println("Average Waiting Time: " + (total_wait / n));
    }

    // ---------------- ROUND ROBIN ----------------
    public static void roundRobin(Scanner sc) {
        System.out.print("Enter number of processes: ");
        int n = sc.nextInt();

        int at[] = new int[n];
        int bt[] = new int[n];
        int rt[] = new int[n];
        int wt[] = new int[n];
        int tat[] = new int[n];

        for (int i = 0; i < n; i++) {
            System.out.print("\nArrival time of P" + (i + 1) + ": ");
            at[i] = sc.nextInt();
            System.out.print("Burst time of P" + (i + 1) + ": ");
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
            time++;
        } while (done == 0);

        for (int i = 0; i < n; i++)
            tat[i] = bt[i] + wt[i];

        System.out.println("\nProcess\tAT\tBT\tWT\tTAT");
        for (int i = 0; i < n; i++)
            System.out.println("P" + (i + 1) + "\t" + at[i] + "\t" + bt[i] + "\t" + wt[i] + "\t" + tat[i]);
    }
}
