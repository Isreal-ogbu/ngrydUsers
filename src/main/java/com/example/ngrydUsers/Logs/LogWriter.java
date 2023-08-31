package com.example.ngrydUsers.Logs;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

public class LogWriter {
    public static void writeFile(String text) throws IOException {
        try(BufferedWriter write = new BufferedWriter(new FileWriter("C:\\Users\\USER\\ngrydUsers\\src\\main\\java\\com\\example\\ngrydUsers\\Logs\\logs", true))){
            write.write(text);
        } catch (FileNotFoundException e){
            System.out.println("File error message: "+ e.getMessage());
        }
    }
}
