package br.unifil.dc.sisop;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

/**
 * Write a description of class ComandosInternos here.
 *
 * @author Ricardo Inacio Alvares e Silva
 * @version 180823
 */
public final class ComandosInternos {
    
    public static String exibirRelogio() {
        SimpleDateFormat f = new SimpleDateFormat("HH:mm 'de' dd/MM/yyyy.");
        Date d = new Date();
        return f.format(d);
    }
    
    public static String escreverListaArquivos(String currDir) {
        String retorno = "";
        File f = new File(currDir);
        String[] arrF = f.list();

        for(String a : arrF){
            retorno += "\n" + a;
        }

        return retorno;
    }
    
    public static void criarNovoDiretorio(String nomeDir, String currDir) {
        File newDir = new File(currDir+"\\"+nomeDir);
        newDir.mkdir();
    }
    
    public static void apagarDiretorio(String nomeDir, String currDir) {
        File rmvDir = new File(currDir+"\\"+nomeDir);
        rmvDir.delete();
    }
    
    public static String mudarDiretorioTrabalho(String nomeDir, String currDir){

        if(verificarDiretorioInCurr(nomeDir, currDir)){
            return currDir+"\\"+nomeDir;
        } else if(verificarDiretorioExists(nomeDir, currDir)){
            return currDir+"\\"+nomeDir;
        } else if(verificarDiretorioExists(nomeDir)){
            return nomeDir;
        } else {
            throw new RuntimeException("Diretorio especificado nao existe!");
        }
    }

    private static boolean verificarDiretorioInCurr(String nomeDir, String currDir){
        File f = new File(currDir);
        String[] arrF = f.list();

        for(int i = 0; i < arrF.length; i++){
            if (arrF[i].equals(nomeDir)){
                return true;
            }
        }
        return false;
    }

    private static boolean verificarDiretorioExists(String nomeDir, String currDir){
        File f = new File(currDir+"\\"+nomeDir);
        if(f.exists()){
            return true;
        } else {
            return false;
        }
    }

    private static boolean verificarDiretorioExists(String nomeDir){
        File f = new File(nomeDir);
        if(f.exists()){
            return true;
        } else {
            return false;
        }
    }
    
    /**
     * Essa classe não deve ser instanciada.
     */
    private ComandosInternos() {}
}
