---


---

<h1 id="cpu-scheduler">CPU Scheduler</h1>
<p>Create a program that simulates Round Robin CPU scheduling, including Round Robin and Priority Queue algorithms is optional and in my case I decided to do both. You are required to use five different quantums to showcase the differences in efficiency of the algorithm and how it changes based on the processes entered. You will use the Java command line with two arguments to run the program: <code>file path of the processes file</code> and <code>time quantum</code>.</p>
<p>The simulation should include the following:</p>
<ul>
<li>Clock - timestamps for all events including: creation time, completion time, etc.</li>
<li>Process Creator - creates processes at arrival time</li>
<li>CPU - runs processes for a time slice (time quantum)</li>
<li>Queue - first in, first out (FIFO) read queue used by process creator and CPU</li>
<li>Process Arrival Time - arrival time of new processes into the ready queue</li>
<li>Process Service Time - amount of time required by the processes to complete execution</li>
<li>Time Quantum - time each processes spends in the CPU until it rotates out to the next process</li>
<li>Context Switch - number of times a process is switched, this number is predetermined</li>
</ul>
<br>
The program will print out the performance evaluation criteria:
<blockquote>
<p>CPU Utilization - keep the CPU 100% busy, always have it running to increase efficiency</p>
</blockquote>
<blockquote>
<p>Throughput - the number of jobs processed within a certain time period</p>
</blockquote>
<blockquote>
<p>Average Waiting Time - total time processes wait in the ready queue</p>
</blockquote>
<blockquote>
<p>Average Turnaround Time - total time to complete processes</p>
</blockquote>
<br>
## Sample Input and Execution
The file that is used to test your file and the processes that are used will be given in CSV format. There will be three columns: `Process ID`, `Arrival Time`, and `Burst Time`. In my case, since I was using priority queue I had to include a column for priority indicated by an integer. Highest priority is 1, and so on. 
<p>Execution looks like this:</p>
<pre class=" language-java"><code class="prism  language-java">$ javac target<span class="token punctuation">.</span>java <span class="token comment">//this is the name of the java program you are compiling</span>
$ java target processes<span class="token punctuation">.</span>csv <span class="token number">2</span> <span class="token comment">//you are running the file here with two arguments of file path and time quantum</span>
</code></pre>
<p><br><br></p>
<h2 id="code-walkthrough">Code Walkthrough</h2>
<h3 id="processes-.csv-file">Processes .csv file</h3>
<pre><code>pid,arrive,burst,priority
1,0,7,3
2,1,5,1
3,0,2,2
4,2,6,4
</code></pre>
<p>The csv file is formatted based on the project outline given to me. The <code>pid</code> is the process id number, the <code>arrive</code> property is the arrival time that is calculated in the stopwatch to determine how long a process takes to enter the ready queue from the waiting queue, <code>burst</code> is the property that indicates how much burst a process needs to complete itself, finally the <code>priority</code> property is used for the priority queue to indicate which process should go first.</p>
<br>
<h3 id="process-class">Process Class</h3>
<pre class=" language-java"><code class="prism  language-java"><span class="token keyword">private</span> <span class="token keyword">int</span> processID<span class="token punctuation">;</span>
<span class="token keyword">private</span> <span class="token keyword">int</span> arrivalTime<span class="token punctuation">;</span>
<span class="token keyword">private</span> <span class="token keyword">int</span> burst<span class="token punctuation">;</span>
<span class="token keyword">private</span> <span class="token keyword">int</span> burstTime<span class="token punctuation">;</span>
<span class="token keyword">private</span> Integer priority<span class="token punctuation">;</span>
<span class="token keyword">private</span> <span class="token keyword">int</span> completionTime<span class="token punctuation">;</span>

<span class="token keyword">private</span> <span class="token keyword">int</span> processTime<span class="token punctuation">;</span>
<span class="token keyword">private</span> <span class="token keyword">int</span> idleTime<span class="token punctuation">;</span>
</code></pre>
<p>These are the private instance variable used for the class. <code>processID</code>, <code>arrivalTime</code>, <code>burst</code>, <code>burstTime</code> and <code>priority</code> are taken from the csv file. <code>burst</code> is used to store the burst time to be used in the wait time, and <code>burstTime</code> is used to run the process and perform calculations based on the time quantum.  <code>priority</code> uses the <code>Integer</code> data type because of the <code>Comparator</code> interface to determine priority in the priority queue.<br>
<code>completionTime</code> is a property that is used when the the process completes and stores the integer from the CPU stopwatch.<br>
<code>processTime</code> and <code>idleTime</code> store the statistics of the process so that they can be used later to calculate statistics. In my implementation and the requirements of the project, process time was not used, but it can be called if you want to see the statistics of each process.</p>
<p>The constructor for <code>Process</code> objects is self explanatory and getters and setters were created for the properties as needed.</p>
<h4 id="process-special-methods">Process Special Methods</h4>
<p>These are methods that are used to calculate something about the process.</p>
<pre class=" language-java"><code class="prism  language-java"><span class="token keyword">public</span> <span class="token keyword">int</span> <span class="token function">getWaitTime</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span>
	<span class="token keyword">return</span> <span class="token function">getTurnaroundTime</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token operator">-</span> <span class="token function">getBurst</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>

<span class="token keyword">public</span> <span class="token keyword">int</span> <span class="token function">getTurnaroundTime</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token punctuation">{</span>
	<span class="token keyword">return</span>  <span class="token function">getCompletionTime</span><span class="token punctuation">(</span><span class="token punctuation">)</span> <span class="token operator">-</span> <span class="token function">getArrivalTime</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>

<span class="token keyword">public</span> <span class="token keyword">int</span> <span class="token function">getResponseTime</span><span class="token punctuation">(</span><span class="token keyword">int</span> stopwatch<span class="token punctuation">)</span> <span class="token punctuation">{</span>
	<span class="token keyword">return</span>  stopwatch <span class="token operator">-</span> <span class="token function">getArrivalTime</span><span class="token punctuation">(</span><span class="token punctuation">)</span><span class="token punctuation">;</span>
<span class="token punctuation">}</span>
</code></pre>
<p><code>getWaitTime()</code> is used to determine the wait time of the process which is calculated by subtracting the burst (burst that was given by the csv file) from the turnaround time which has its own calculation<br>
<code>getTurnaroundTime</code>  is used to determine the turnaround time by subtracting the arrival time from the completion time, the completion time is set by the stopwatch in the <code>CPU</code> class.<br>
<code>getResponseTime</code> takes the stopwatch in the <code>CPU</code> class and subtracts the arrival time from it.</p>
<br>
<pre class=" language-java"><code class="prism  language-java"><span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">runProcess</span><span class="token punctuation">(</span><span class="token keyword">int</span> q<span class="token punctuation">)</span> <span class="token punctuation">{</span>
	burstTime <span class="token operator">-=</span> q<span class="token punctuation">;</span>
	processTime <span class="token operator">+=</span> q<span class="token punctuation">;</span>
<span class="token punctuation">}</span>

  

<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">idlingProcess</span><span class="token punctuation">(</span><span class="token keyword">int</span> ct<span class="token punctuation">)</span> <span class="token punctuation">{</span>
	idleTime <span class="token operator">+=</span> ct<span class="token punctuation">;</span>
	processTime <span class="token operator">+=</span> ct<span class="token punctuation">;</span>
<span class="token punctuation">}</span>

  

<span class="token keyword">public</span> <span class="token keyword">void</span> <span class="token function">contextSwitch</span><span class="token punctuation">(</span><span class="token keyword">int</span> csTime<span class="token punctuation">)</span> <span class="token punctuation">{</span>
	processTime <span class="token operator">+=</span> csTime<span class="token punctuation">;</span>
<span class="token punctuation">}</span>
</code></pre>

