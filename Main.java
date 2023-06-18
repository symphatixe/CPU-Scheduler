import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedReader;

public class Main {
    public static void main (String [] args) {

        Scanner input = new Scanner(System.in);
        ArrayList <Process> processes = new ArrayList <Process> ();

        String filepath = args[0];
        int quantum = Integer.parseInt(args[1]);

        try {
            String line;

            BufferedReader br = new BufferedReader(new FileReader(filepath));
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] process = line.split(",");
                
                String p = process[0];
                String at = process[1];
                String bt = process[2];
                String pr = process[3];

                processes.add(new Process(Integer.parseInt(p), Integer.parseInt(at), Integer.parseInt(bt), Integer.parseInt(pr)) );
            }
            br.close();
        }

        catch (IOException e) {
            
            System.out.println("File does not exist, try again.");
        }

        CPU main = new CPU(processes);
        main.run(quantum);
    }
}
