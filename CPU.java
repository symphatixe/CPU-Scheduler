import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

class ProcessComparator implements Comparator<Process> {

    @Override
    public int compare(Process p1, Process p2) {

        if (p1.getPriority() < p2.getPriority() ) return -1;
        else if (p1.getPriority() > p2.getPriority() ) return 1;
        else return 0;
    }
}

public class CPU {

    private static int stopwatch = 1;
    private PriorityQueue <Process> processes;
    private Queue <Process> waitQueue;
    private Queue <Process> readyQueue;
    private Queue <Process> finishQueue;
    private boolean complete;
    private int numberOfProcesses;

    private final static int contextSwitchTime = 2;
    private static int contextSwitches = 0;
    
    public CPU(ArrayList <Process> p) {

        processes = new PriorityQueue <Process> (new ProcessComparator() );
        processes.addAll(p);
        waitQueue = new LinkedList <Process> ();
        readyQueue = new LinkedList <Process> ();
        finishQueue = new LinkedList<Process> ();
        numberOfProcesses = p.size();
    }

    public void run(int q) {
        System.out.println("\n");

        while (!complete) {

            if (!processes.isEmpty() ) readyQueue.add(processes.poll());
            else readyQueue.add(waitQueue.poll() );
            System.out.println("\nCPU Stopwatch Time: " + stopwatch + "ms");
            System.out.println("Process ID running: " + readyQueue.peek().getProcessID() );
            System.out.println("Burst Time: " + readyQueue.peek().getBurstTime() );


            readyQueue.peek().runProcess(q);
            if (readyQueue.peek().getBurstTime() < 0) {

                stopwatch += (q + readyQueue.peek().getBurstTime() );
                readyQueue.peek().setBurstTime(0);
            }
            else stopwatch += q;


            System.out.println("\nCPU Stopwatch Time: " + stopwatch + "ms");
            System.out.println("Process running " + readyQueue.peek().getProcessID() );
            System.out.println("Burst Time: " + readyQueue.peek().getBurstTime() );

            if (readyQueue.peek().getBurstTime() <= 0) {

                readyQueue.peek().setCompletionTime(stopwatch);
                finishQueue.add(readyQueue.poll() );
            }

            else {
                
                waitQueue.add(readyQueue.poll() );
                Iterator <Process> itr = waitQueue.iterator();

                while (itr.hasNext() ) {
                    itr.next().idlingProcess(contextSwitchTime);
                }
            }

            if (!complete) {

                contextSwitches++;
                stopwatch += contextSwitchTime;
            }

            if (finishQueue.size() == numberOfProcesses) complete = true;
        }

        double waitTime = 0;
        double turnaroundTime = 0;
        int idleTime = 0;

        for (Process p: finishQueue) {

            waitTime += p.getWaitTime();
            turnaroundTime += p.getTurnaroundTime();
            idleTime += p.getIdleTime();
        }

        System.out.println("\nCPU Statistics using time quantum of " + q);
        System.out.println("Throughput: " + getThroughput(finishQueue.size(), stopwatch));
        System.out.println("CPU Utilization: " + (getUtilization(idleTime)) + "%");
        System.out.println("Average Turnaround Time: " + (turnaroundTime / finishQueue.size()) );
        System.out.println("Average Waiting Time: " + (waitTime / finishQueue.size()) );
    }
    //////////////////////////////////
    /////////////////////////////////
    public String getUtilization(int it) {

        DecimalFormat df = new DecimalFormat("00.##");
        double utilization = (1 - (1.0 * it / stopwatch) ) * 100;
        return df.format(utilization);
    }

    public String getThroughput(int s, int t) {

        DecimalFormat df = new DecimalFormat("0.##");
        double throughput = 1.0 * s / t;
        return df.format(throughput);
    }
    //////////////////////////////////
    /////////////////////////////////
    public String toString() {
        return "";
    }
}

