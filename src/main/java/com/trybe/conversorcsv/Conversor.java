package com.trybe.conversorcsv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.Buffer;

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
        BufferedReader bufferedReader = null;
        
        try {
          fileReader = new FileReader(arquivo);
          bufferedReader = new BufferedReader(fileReader);

          String conteudoDaLinha = bufferedReader.readLine();
          
          while (conteudoDaLinha != null) {
            conteudoDaLinha = bufferedReader.readLine();
          }
        } catch (IOException error) {
          error.printStackTrace();
        } finally {
          this.fecharAquivo(fileReader, bufferedReader);
        }
      }
    }
  }
}