package br.unifil.dc.sisop;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

/**
 * Write a description of class ComandoPrompt here.
 *
 * @author Ricardo Inacio Alvares e Silva
 * @version 180823
 */
public class ComandoPrompt {
    
    public ComandoPrompt(String comando) {
        argumentos = new ArrayList<String>();
        if(comando.indexOf(' ') > -1){
            nome = comando.substring(0, comando.indexOf(' '));
            String curr = comando.substring(comando.indexOf(' ') + 1);
            int i = 0;
            while (!curr.isEmpty()){
                if (curr.indexOf(' ') > -1) {
                    argumentos.add(i, curr.substring(0, curr.indexOf(' ')));
                    curr = curr.substring(curr.indexOf(' ') + 1);
                    i++;
                } else {
                    argumentos.add(i, curr);
                    curr = "";
                }
            }
        } else {
            nome = comando;
        }
    }
    
    /**
     * Método acessor get para o nome do comando.
     * 
     * @return o nome do comando, exatamente como foi entrado.
     */
    public String getNome() { 

        return nome;
    }
    
    /**
     * Método acessor get para os argumentos que seguram ao nome do comando.
     * 
     * @return Lista de argumentos do comando, protegida contra modificações externas.
     */
    public List<String> getArgumentos() {

        return Collections.unmodifiableList(argumentos);
    }
    
    private final String nome;
    private final ArrayList<String> argumentos;
}
