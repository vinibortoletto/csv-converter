package com.csvconverter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CsvConverter {

  public static void main(String[] args) throws IOException {
    File inputsDir= new File("./inputs/");
    File outputsDir = new File("./outputs/");
    new CsvConverter().convertFiles(inputsDir, outputsDir);
  }

  private void closeFile(
      FileReader fileReader,
      FileWriter fileWriter, 
      BufferedReader bufferedReader,  
      BufferedWriter bufferedWriter
  ) {
    try {
      fileReader.close();
      fileWriter.close();
      bufferedReader.close();
      bufferedWriter.close();
    } catch (Exception error) {
      error.printStackTrace();
    }
  }

  private String formatDate(String date) {
    String[] splittedDate = date.split("/");
    
    String day = splittedDate[0];
    String month = splittedDate[1];
    String year = splittedDate[2];
    
    return String.format("%s-%s-%s", year, month, day); 
  }

  private String formatCpf(String cpf) {
    String block1 = cpf.substring(0, 3);
    String block2 = cpf.substring(3, 6);
    String block3 = cpf.substring(6, 9);
    String block4 = cpf.substring(9);

    return String.format("%s.%s.%s-%s", block1, block2, block3, block4);
  }

  private String formatLine(String line) {
    String[] newLine = line.split(","); 

    String name = newLine[0].toUpperCase();
    String date = this.formatDate(newLine[1]);
    String email = newLine[2];
    String cpf = this.formatCpf(newLine[3]);

    return String.format("%s,%s,%s,%s\n", name, date, email, cpf);
  }

  public void convertFiles(File inputsDir, File outputsDir) throws IOException {
    if (inputsDir.isDirectory() && inputsDir.canRead()) {
      for (File file : inputsDir.listFiles()) {
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {
          outputsDir.mkdirs();

          fileReader = new FileReader(file);
          fileWriter = new FileWriter(outputsDir.getPath() + "/" + file.getName());
          bufferedReader = new BufferedReader(fileReader);
          bufferedWriter = new BufferedWriter(fileWriter);

          String line = bufferedReader.readLine();
          
          while (line != null) {
            if (!line.contains("Nome")) {
              String newLine = this.formatLine(line);
              bufferedWriter.write(newLine);
            } else {
              bufferedWriter.write(line + "\n");
            }
            
            bufferedWriter.flush();
            line = bufferedReader.readLine();
          }
        } catch (IOException error) {
          error.printStackTrace();
        } finally {
          this.closeFile(fileReader, fileWriter, bufferedReader, bufferedWriter);
        }
      }
    }
  }
}