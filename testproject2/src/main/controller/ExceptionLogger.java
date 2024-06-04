package main.controller;

import java.io.*;

public class ExceptionLogger {

    private PrintWriter outputFile;
    /**
     * Creates a new file with specified file name.
     */
    public ExceptionLogger() {
        try{
           this.outputFile = new PrintWriter(new FileWriter("exceptions.txt"), true); 
        }catch(IOException exception){
           System.out.println("File creation failed");
        }
    }
    /**
     * Prints output file with a specific String.
     * @param totalPrice receives the total price of the sale
     */
  
    public void logException(Exception exception){
        this.outputFile.println(exception.getMessage());
        exception.printStackTrace(outputFile);
    }
}