package experiments;

import com.opencsv.CSVWriter;
import datastructures.concrete.DoubleLinkedList;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Time;

public class experiments {

    public static void main(String[] args) {
        DoubleLinkedList<Integer> l = new DoubleLinkedList<Integer>();


        File f = new File("analysis/input/getMiddle_2.csv");
        try {
            FileWriter fw = new FileWriter(f);
            CSVWriter writer = new CSVWriter(fw);
            String[] header = new String[]{"N", "Time"};
            writer.writeNext(header);
            Thread.sleep(1000);
            Integer N = (int) Math.pow(10,6);
            for(int k = 0; k < N; k++) {
                l.add(k);
                if(k%1000==0){
                    long startTime = 0;
                    long elapsedTime = 0;
                    startTime = System.nanoTime();
                    int middle = l.get(k/2);
                    elapsedTime = (System.nanoTime() - startTime);
                    writer.writeNext(new String[]{String.valueOf(k), String.valueOf(elapsedTime)});
                }
            }
            writer.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }



}
