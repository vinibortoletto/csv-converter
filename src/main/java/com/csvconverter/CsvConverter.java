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
              String newLine = CsvUtils.formatLine(line);
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
          CsvUtils.closeFile(fileReader, fileWriter, bufferedReader, bufferedWriter);
        }
      }
    }
  }
}