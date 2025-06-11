package main.controller;

import java.io.*;
import java.time.LocalTime;

public class ExceptionLogger {

    private PrintWriter outputFile;
    /**
     * Creates a new file with name "exceptions.txt".
     */
    public ExceptionLogger() {
        try{
           this.outputFile = new PrintWriter(new FileWriter("exceptions.txt"), true); 
        }catch(IOException exception){
           System.out.println("File creation failed");
        }
    }
    /**
     * Prints specified Exception to output file.
     */
    public void logException(Exception exception){
        this.outputFile.println("\n"+LocalTime.now());
        this.outputFile.println("Exception:  "+exception.getMessage());
        exception.printStackTrace(outputFile);
    }
}