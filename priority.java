import java.util.*;

public class priority{
    public static void main(String[] args){
        
        int n, sum=0;
        int total_tt=0, total_wait=0;
        Scanner sc= new Scanner(System.in);
        System.out.println("Enter number of processes ? ");
        n=sc.nextInt();
        int[] arrival= new int[n];
        int[] burst= new int[n];
        int[] priority = new int[n];
        int[] process = new int[n];
        int[] wait= new int[n];
        int[] tat= new int[n];
        int[] finish= new int[n];

        for(int i=0;i<n;i++){
            System.out.println("Enter arrival time of process "+(i+1));
            arrival[i]= sc.nextInt();
            System.out.println("Enter burst time of process "+(i+1));
            burst[i]= sc.nextInt();
            System.out.println("Enter priority of process "+(i+1));
            priority[i]=sc.nextInt();
            process[i]=i+1;

        }

        for(int i=0;i<n-1;i++){
            for(int j=i+1;j<n;j++){
                if(priority[i]> priority[j]){
                    int temp= process[i];
                    process[i]=process[j];
                    process[j]=temp;

                    temp= burst[i];
                    burst[i]=burst[j];
                    burst[j]=temp;

                    temp= priority[i];
                    priority[i]=priority[j];
                    priority[j]=temp;


                }
            }
        }

        for(int i=0;i<n;i++){
            sum=sum+burst[i];
            finish[i]=sum;
        }

        for(int i=0;i<n;i++){
            tat[i]= finish[i]-arrival[i];
            total_tt=total_tt+tat[i];

            wait[i]= tat[i]-burst[i];
            total_wait=total_wait+wait[i];

            
        }

        System.out.println("Average turn around time" + (total_tt/n));
        System.out.println("Average waiting time: "+ (total_wait/n));

        System.out.println("Process \n AT \n BT \n Priority \n CT \n TAT \n WT");


        for(int i=0;i<n;i++){
           


            System.out.println(process[i] + "\t" + arrival[i] + "\t"+ burst[i] + "\t" + priority[i]+ "\t" + finish[i] +"\t" + tat[i]+ "\t" + wait[i]);

        }





    }
}