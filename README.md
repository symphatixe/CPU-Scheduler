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

> Average Waiting Time - total time processes wait in the ready queue

> Average Turnaround Time - total time to complete processes

<br/>
## Sample Input and Execution
The file that is used to test your file and the processes that are used will be given in CSV format. There will be three columns: `Process ID`, `Arrival Time`, and `Burst Time`. In my case, since I was using priority queue I had to include a column for priority indicated by an integer. Highest priority is 1, and so on. 

Execution looks like this:
```java
$ javac target.java //this is the name of the java program you are compiling
$ java target processes.csv 2 //you are running the file here with two arguments of file path and time quantum
```

<br/><br/>
## Code Walkthrough
### Processes .csv file
```
pid,arrive,burst,priority
1,0,7,3
2,1,5,1
3,0,2,2
4,2,6,4
```

The csv file is formatted based on the project outline given to me. The `pid` is the process id number, the `arrive` property is the arrival time that is calculated in the stopwatch to determine how long a process takes to enter the ready queue from the waiting queue, `burst` is the property that indicates how much burst a process needs to complete itself, finally the `priority` property is used for the priority queue to indicate which process should go first. 

<br/>

### Process Class
```java
private int processID;
private int arrivalTime;
private int burst;
private int burstTime;
private Integer priority;
private int completionTime;

private int processTime;
private int idleTime;
```

These are the private instance variable used for the class. `processID`, `arrivalTime`, `burst`, `burstTime` and `priority` are taken from the csv file. `burst` is used to store the burst time to be used in the wait time, and `burstTime` is used to run the process and perform calculations based on the time quantum.  `priority` uses the `Integer` data type because of the `Comparator` interface to determine priority in the priority queue. 
`completionTime` is a property that is used when the the process completes and stores the integer from the CPU stopwatch. 
`processTime` and `idleTime` store the statistics of the process so that they can be used later to calculate statistics. In my implementation and the requirements of the project, process time was not used, but it can be called if you want to see the statistics of each process. 

The constructor for `Process` objects is self explanatory and getters and setters were created for the properties as needed.

#### Process Special Methods
These are methods that are used to calculate something about the process.

```java
public int getWaitTime() {
  return getTurnaroundTime() - getBurst();
}

public int getTurnaroundTime() {
  return  getCompletionTime() - getArrivalTime();
}

public int getResponseTime(int stopwatch) {
  return  stopwatch - getArrivalTime();
}
```
`getWaitTime()` is used to determine the wait time of the process which is calculated by subtracting the burst (burst that was given by the csv file) from the turnaround time which has its own calculation
`getTurnaroundTime`  is used to determine the turnaround time by subtracting the arrival time from the completion time, the completion time is set by the stopwatch in the `CPU` class.
`getResponseTime` takes the stopwatch in the `CPU` class and subtracts the arrival time from it. 

<br/>

```java
public void runProcess(int q) {
  burstTime -= q;
  processTime += q;
}

  

public void idlingProcess(int ct) {
  idleTime += ct;
  processTime += ct;
}

  

public void contextSwitch(int csTime) {
  processTime += csTime;
}
```
`runProcess` is used enter code here



<!--stackedit_data:
eyJoaXN0b3J5IjpbLTE3MzAwNjQ5NCw1MzI4MTkwMF19
-->