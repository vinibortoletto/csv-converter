package com.trybe.conversorcsv;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Classe Conversor.
 */
public class Conversor {

  /**
   * Função utilizada apenas para validação da solução do desafio.
   *
   * @param args Não utilizado.
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou
   *                     gravar os arquivos de saída.
   */
  public static void main(String[] args) throws IOException {
    File pastaDeEntradas = new File("./entradas/");
    File pastaDeSaidas = new File("./saidas/");
    new Conversor().converterPasta(pastaDeEntradas, pastaDeSaidas);
  }

  private void fecharAquivo(
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

  private String formataData(String data) {
    String[] dataDividida = data.split("/");
    String dia = dataDividida[0];
    String mes = dataDividida[1];
    String ano = dataDividida[2];
    String novaData = String.format("%s-%s-%s", ano, mes, dia); 
    
    return novaData;
  }

  private String formataCpf(String cpf) {
    String cpf1 = cpf.substring(0, 3);
    String cpf2 = cpf.substring(3, 6);
    String cpf3 = cpf.substring(6, 9);
    String cpf4 = cpf.substring(9);
    String novoCpf = String.format("%s.%s.%s-%s", cpf1, cpf2, cpf3, cpf4);

    return novoCpf;
  }

  private String manipulaLinha(String linha) {
    String[] novaLinha = linha.split(","); 
    String nome = novaLinha[0].toUpperCase();
    String data = this.formataData(novaLinha[1]);
    String email = novaLinha[2];
    String cpf = this.formataCpf(novaLinha[3]);

    return String.format("%s,%s,%s,%s\n", nome, data, email, cpf);
  }

  /**
   * Converte todos os arquivos CSV da pasta de entradas. Os resultados são gerados
   * na pasta de saídas, deixando os arquivos originais inalterados.
   *
   * @param pastaDeEntradas Pasta contendo os arquivos CSV gerados pela página web.
   * @param pastaDeSaidas Pasta em que serão colocados os arquivos gerados no formato
   *                      requerido pelo subsistema.
   *
   * @throws IOException Caso ocorra algum problema ao ler os arquivos de entrada ou
   *                     gravar os arquivos de saída.
   */
  public void converterPasta(File pastaDeEntradas, File pastaDeSaidas) throws IOException {
    if (pastaDeEntradas.isDirectory() && pastaDeEntradas.canRead()) {
      for (File arquivo : pastaDeEntradas.listFiles()) {
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        BufferedReader bufferedReader = null;
        BufferedWriter bufferedWriter = null;

        try {
          pastaDeSaidas.mkdirs();

          fileReader = new FileReader(arquivo);
          fileWriter = new FileWriter(pastaDeSaidas.getPath() + "/" + arquivo.getName());
          bufferedReader = new BufferedReader(fileReader);
          bufferedWriter = new BufferedWriter(fileWriter);

          String linha = bufferedReader.readLine();
          
          while (linha != null) {
            if (!linha.contains("Nome")) {
              String novaLinha = this.manipulaLinha(linha);
              bufferedWriter.write(novaLinha);
            } else {
              bufferedWriter.write(linha + "\n");
            }
            
            bufferedWriter.flush();
            linha = bufferedReader.readLine();
          }
        } catch (IOException error) {
          error.printStackTrace();
        } finally {
          this.fecharAquivo(fileReader, fileWriter, bufferedReader, bufferedWriter);
        }
      }
    }
  }
}