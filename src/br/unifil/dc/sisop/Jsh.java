package br.unifil.dc.sisop;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Write a description of class Jsh here.
 *
 * @author Ricardo Inacio Alvares e Silva
 * @version 180823
 */
public final class Jsh {



    /**
    * Funcao principal do Jsh.
    */
    public static void promptTerminal() throws Exception {
        String currDir = System.getProperty("user.dir");
        while (true) {
    		exibirPrompt(currDir);
    		ComandoPrompt comandoEntrado = lerComando();
    		currDir = executarComando(comandoEntrado, currDir);
    	}
    }

    /**
    * Escreve o prompt na saida padrao para o usuário reconhecê-lo e saber que o
    * terminal está pronto para receber o próximo comando como entrada.
    */
    public static void exibirPrompt(String currDir){
        String u = System.getProperty("user.name");
        String UID = "";
        try {
            Process p = Runtime.getRuntime().exec("id");
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            UID = stdInput.readLine();
        } catch (Exception e){
            //o usuario não está usando linux, então UID não existe (eu acho), que pena!
        } finally {
            System.out.print("\n"+u+"#"+UID+":"+ currDir + "% ");
        }


    }

    /**
    * Preenche as strings comando e parametros com a entrada do usuario do terminal.
    * A primeira palavra digitada eh sempre o nome do comando desejado. Quaisquer
    * outras palavras subsequentes sao parametros para o comando. A palavras sao
    * separadas pelo caractere de espaco ' '. A leitura de um comando eh feita ate
    * que o usuario pressione a tecla <ENTER>, ou seja, ate que seja lido o caractere
    * EOL (End Of Line).
    *
    * @return 
    */
    public static ComandoPrompt lerComando() {
        Scanner input = new Scanner(System.in);
        String i = input.nextLine();
        ComandoPrompt c = new ComandoPrompt(i);
        return c;
    }

    /**
    * Recebe o comando lido e os parametros, verifica se eh um comando interno e,
    * se for, o executa.
    * 
    * Se nao for, verifica se é o nome de um programa terceiro localizado no atual 
    * diretorio de trabalho. Se for, cria um novo processo e o executa. Enquanto
    * esse processo executa, o processo do uniterm deve permanecer em espera.
    *
    * Se nao for nenhuma das situacoes anteriores, exibe uma mensagem de comando ou
    * programa desconhecido.
    */
    public static String executarComando(ComandoPrompt comando, String currDir) {
        switch(verificarComando(comando.getNome())){
            case -1:
                executarPrograma(comando, currDir);
                return currDir;
            case 0:
                System.exit(0);
                break;
            case 1:
                System.out.println("Sao "+ ComandosInternos.exibirRelogio());
                break;
            case 2:
                System.out.println(ComandosInternos.escreverListaArquivos(currDir));
                break;
            case 3:
                try{
                    ComandosInternos.criarNovoDiretorio(comando.getArgumentos().get(0), currDir);
                } catch (IndexOutOfBoundsException e){
                    System.out.println("Esta faltando argumentos! RTFM");
                } catch (Exception e){
                    System.out.println(e);
                }
                break;
            case 4:
                try{
                    ComandosInternos.apagarDiretorio(comando.getArgumentos().get(0), currDir);
                } catch (IndexOutOfBoundsException e){
                    System.out.println("Esta faltando argumentos! RTFM");
                } catch (Exception e){
                    System.out.println(e);
                }
                break;
            case 5:
                try{
                    currDir = ComandosInternos.mudarDiretorioTrabalho(comando.getArgumentos().get(0), currDir);
                } catch (IndexOutOfBoundsException e){
                    System.out.println("Esta faltando argumentos! RTFM");
                } catch (Exception e){
                    System.out.println(e);
                }
                break;
        }
        return currDir;
    }

    /**
     * Verifica se o comando existe
     * @param c comando a ser virifcado
     * @return retorna o indice do comando caso encontrado, caso contrario, retorna -1, indicando que o comando não existe;
     */
    public static int verificarComando(String c){
        List<String> listaComandos = Arrays.asList("encerrar", "relogio", "la", "cd", "ad", "mdt");
        for(int i = 0; i < listaComandos.size(); i++){
            if(c.equals(listaComandos.get(i))){
                return i;
            }
        }
        return -1;
    }

    public static void executarPrograma(ComandoPrompt comando, String currDir) {
        File f = new File(currDir);
        String[] arrF = f.list();
        boolean found = false;

        for(String a : arrF){
            if(a.equals(comando.getNome())){
                found = true;
                File f2 = new File (currDir+"\\"+comando.getNome());
                if(f2.canExecute()){
                    try {
                        Process p = new ProcessBuilder(comando.getNome()).start();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
                        String s;
                        while ((s = reader.readLine()) != null) {
                            System.out.println(s);
                        }

                        int exitCode = p.waitFor();
                        if(exitCode > 0){
                            System.out.print("Erro na execucao do processo filho! ");
                        }
                        System.out.println("Processo filho encerrado com exit code " + exitCode);
                    } catch (IOException e){
                        e.getMessage();
                    } catch (InterruptedException e){
                        e.getMessage();
                    }
                } else {
                    System.out.println("Nao foi possivel fazer a execucao do programa "+ comando.getNome()+"!");
                }
            }
        }
        if (!found){
            System.out.println("Comando ou programa \""+comando.getNome()+"\" inexistente.");
        }


    }
    
    
    /**
     * Entrada do programa. Provavelmente você não precisará modificar esse método.
     */
    public static void main(String[] args) throws Exception {

        promptTerminal();
    }
    
    
    /**
     * Essa classe não deve ser instanciada.
     */
    private Jsh() {}
}
