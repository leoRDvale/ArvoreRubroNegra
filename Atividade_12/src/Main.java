import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Random;

public class Main {
    public static void main(String[] args) throws Exception{
        int[] valoresIniciais = LerArquivoTxt("valores.txt");
        int[] numeros = new int[50000];

        Random rand = new Random();

        for(int i=0; i < 50000; i++){
            numeros[i] = rand.nextInt((9999 - -9999) + 1) + -9999;
        }

        long inicioAvl = System.currentTimeMillis();
        arvoreAVL(valoresIniciais, numeros);
        long fimAvl = System.currentTimeMillis();

        long inicioRubro = System.currentTimeMillis();
        arvoreRubroNegra(valoresIniciais, numeros);
        long fimRubro = System.currentTimeMillis();

        System.out.println("Tempo de Execução da Arvore AVL = "+ calcTempo(inicioAvl, fimAvl));
        System.out.println("Tempo de Execução da Arvore Rubro Negra = "+ calcTempo(inicioRubro, fimRubro));
    }

    public static void arvoreAVL(int[] inserir, int[] verificar){
        ArvoreAvl avl = new ArvoreAvl();

        System.out.println("Inserindo dados na Arvore AVL");
        for(int i = 0; i < inserir.length; i++){
            avl.setRaiz(inserir[i]);
        }

        for(int i = 0; i<verificar.length; i++){
            int valor = verificar[i];

            if(valor % 3 == 0 || valor % 5 == 0){
                avl.remocao(valor);
                System.out.println(valor + "- Foi removido");
            }
            else{
                int recorrencia = avl.buscarRecorrencia(valor);
                System.out.println(valor + " - Sua recorrencia é: " +recorrencia);
            }
        }
    }

    public static void arvoreRubroNegra(int[] inserir, int[] verificar){
        ArvoreRubro rubro = new ArvoreRubro();

        System.out.println("Inserindo dados na Arvore AVL");
        for(int i = 0; i < inserir.length; i++){
            rubro.setRaiz(inserir[i]);
        }

        for(int i = 0; i<verificar.length; i++){
            int valor = verificar[i];

            if(valor % 3 == 0 || valor % 5 == 0){
                rubro.remover(valor);
                System.out.println(valor + "- Foi removido");
            }
            else{
                int recorrencia = rubro.buscar(valor);
                System.out.println(valor + " - Sua recorrencia é: " +recorrencia);
            }
        }
    }

    public static int[] LerArquivoTxt(String local) throws IOException{
        Path file = Path.of(local);

        if (Files.notExists(file)) {
            return null;
        }
        else {
            String valorRetirado = Files.readString(file);
            valorRetirado = valorRetirado.replace("[", "");
            valorRetirado = valorRetirado.replace("]", "");
            valorRetirado = valorRetirado.replace(" ", "");

            String[] ValoresString = valorRetirado.split(",");

            int[] dadosConvertidos = new int[ValoresString.length];

            for (int i = 0; i < ValoresString.length; i++) {
                dadosConvertidos[i] = Integer.valueOf(ValoresString[i]);
            }
            return dadosConvertidos;
        }
    }

    public static String calcTempo(long inicio, long fim){
        long duracao = fim - inicio;
        long segundos = duracao / 1000;
        long milissegundos = duracao % 1000;

        return segundos + " segundos e " + milissegundos + " milissegundos";
    }
}