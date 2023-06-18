public class Process {
    //private instance variables to identify process attributes
    private int processID;
    private int arrivalTime;
    private int burst;
    private int burstTime;
    private Integer priority;
    private int completionTime;

    //private instance variables to identify the completion and statistics of a process
    private int processTime;
    private int idleTime;

    public Process(int pid, int at, int bt, int p) {

        processID = pid;
        arrivalTime = at;
        burst = bt;
        burstTime = bt;
        priority = Integer.valueOf(p);

        processTime = 0;
        idleTime = 0;
        completionTime = 0;
    }

    public int getProcessID() {
        return processID;
    }

    public int getArrivalTime() {
        return arrivalTime;
    }

    public int getBurst() {
        return burst;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public int getPriority() {
        return priority;
    }

    public int getProcessTime() {
        return processTime;
    }

    public int getIdleTime() {
        return idleTime;
    }

    public int getCompletionTime(){
        return completionTime;
    }

    public int getWaitTime() {
        return getTurnaroundTime() - getBurst();
    }

    public int getTurnaroundTime() {
        return getCompletionTime() - getArrivalTime();
    }

    public int getResponseTime(int stopwatch) {
        return stopwatch - getArrivalTime();
    }
    //////////////////////////////////
    /////////////////////////////////
    public void setProcessID(int pid) {
        processID = pid;
    }

    public void setArrivalTime(int at) {
        arrivalTime = at;
    }   

    public void setBurstTime(int bt) {
        burstTime = bt;
    }   

    public void setPriority(int p) {
        priority = p;
    }

    public void setProcessTime(int pt) {
        processTime = pt;
    }

    public void setIdleTime(int it) {
        idleTime = it;
    }

    public void setCompletionTime(int stopwatch) {
        completionTime = stopwatch;
    }
    //////////////////////////////////
    /////////////////////////////////
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

    public String toString() {

        return "Process ID: " + processID + 
        "\nArrival Time: " + arrivalTime + 
        "\nBurst Time: " + burstTime + 
        "\nPriority: " + priority;
    }
}