package com.trybe.conversorcsv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;
import java.util.Arrays;

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

  private void fecharAquivo(FileReader fileReader, BufferedReader bufferedReader) {
    try {
      fileReader.close();
      bufferedReader.close();
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

    return String.format("%s,%s,%s,%s", nome, data, email, cpf);
  }

  private void leArquivo(File pastaDeEntradas) {
    if (pastaDeEntradas.isDirectory() && pastaDeEntradas.canRead()) {
      for (File arquivo : pastaDeEntradas.listFiles()) {
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        
        try {
          fileReader = new FileReader(arquivo);
          bufferedReader = new BufferedReader(fileReader);
          String linha = bufferedReader.readLine();
          
          while (linha != null) {
            if (!linha.contains("Nome")) {
              String novaLinha = this.manipulaLinha(linha);
            }

            linha = bufferedReader.readLine();
          }
        } catch (IOException error) {
          error.printStackTrace();
        } finally {
          this.fecharAquivo(fileReader, bufferedReader);
        }
      }
    }
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
    this.leArquivo(pastaDeEntradas);
    
    
  }
}