package com.csvconverter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

public class CsvUtils {

  private CsvUtils() {}

  public static void closeFile(
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

  public static String formatDate(String date) {
    String[] splittedDate = date.split("/");

    String day = splittedDate[0];
    String month = splittedDate[1];
    String year = splittedDate[2];

    return String.format("%s-%s-%s", year, month, day);
  }

  public static String formatCpf(String cpf) {
    String block1 = cpf.substring(0, 3);
    String block2 = cpf.substring(3, 6);
    String block3 = cpf.substring(6, 9);
    String block4 = cpf.substring(9);

    return String.format("%s.%s.%s-%s", block1, block2, block3, block4);
  }

  public static String formatLine(String line) {
    String[] newLine = line.split(",");

    String name = newLine[0].toUpperCase();
    String date = formatDate(newLine[1]);
    String email = newLine[2];
    String cpf = formatCpf(newLine[3]);

    return String.format("%s,%s,%s,%s\n", name, date, email, cpf);
  }
}
