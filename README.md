# CPU Scheduler

Create a program that simulates Round Robin CPU scheduling, including Round Robin and Priority Queue algorithms is optional and in my case I decided to do both. You are required to use five different quantums to showcase the differences in efficiency of the algorithm and how it changes based on the processes entered. You will use the Java command line with two arguments to run the program: `file path of the processes file` and `time quantum`. 

The simulation should include the following:
* Clock - timestamps for all events including: creation time, completion time, etc.
* Process Creator - creates processes at arrival time
* CPU - runs processes for a time slice (time quantum)
* Queue - first in, first out (FIFO) read queue used by process creator and CPU
* Process Arrival Time - arrival time of new processes into the ready queue
* Process Service Time - amount of time required by the processes to complete execution
* Time Quantum - time each processes spends in the CPU until it rotates out to the next process
* Context Switch - number of times a process is switched, this number is predetermined

<br/>

The program will print out the performance evaluation criteria:

> CPU Utilization - keep the CPU 100% busy, always have it running to increase efficiency

> Throughput - the number of jobs processed within a certain time period

> Average Waiting Time - totla time processes wait in the ready queue

> Average Turnaround Time - total time to complete processes

<br/>

## Sample Input and Execution

The file that is used to test your file and the processes that are used will be given in CSV format. There will be three columns: `Process ID`, `Arrival Time`, and `Burst Time`. In my case, since I was using priority queue I had to include a column for priority indicated by an integer. Highest priority is 1, and so on. 

Execution looks like this:
```java
$ javac target.java //this is the name of the java program you are trying to run
$ java target processes.csv 2 //you are running the file here with two arguments of file path and time quantum
```

<br/><br/>

## Code Walkthrough
