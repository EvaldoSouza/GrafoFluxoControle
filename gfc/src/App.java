import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static Comando criaComandos(String linha) {

        return new Comando(linha);
    }

    public static String nomeDeBloco(int atual) {
        return "B".concat(String.valueOf(atual));
    }

    // função para gerar um grafo baseado no arquivo passado
    public static Grafo gerador(File aProcessar) {
        Grafo processado = new Grafo();
        processado.setId(aProcessar.getName());
        processado.verticeEntrada();// adiciona o vertice "Entry", sendo o primeiro de todo o grafo
        Boolean ultimoBlocoProblematico = false;// flag para determinar se o ultimo bloco é um destino de goto

        try {
            Scanner scan = new Scanner(aProcessar);
            String linha = scan.nextLine();
            int numBlocos = 1;

            while (scan.hasNextLine()) {
                Vertice valfa = new Vertice(criaComandos(linha), nomeDeBloco(numBlocos));

                // separa o arquivo linha por linha, criando um Comando para cada uma, e
                // adicionando no vertice
                while (!valfa.getSaiu() && scan.hasNextLine()) {
                    linha = scan.nextLine();
                    valfa.addComando(criaComandos(linha));
                }

                if (!valfa.getProxEDestinoGOTO() && scan.hasNextLine()) {
                    // se for destino de goto, teho q manter a linha, se n for, posso pegar a
                    // proxima linha
                    linha = scan.nextLine();

                }

                // Caso em que só existe um Vertice de Comandos, precisa setar a saida de forma
                // separada
                if (!scan.hasNextLine() && valfa.getSaida() == null) {
                    valfa.addComandoDeSaida(criaComandos("Exit"));
                }

                if (!scan.hasNextLine() && valfa.getSaida() != null && !valfa.getSaida().getLinha().equals(linha)) {// acabou
                                                                                                                    // o
                                                                                                                    // arquivo
                    // mas a saida é diferente da linha atual
                    ultimoBlocoProblematico = true; // vamos ter o bloco problematico
                }

                processado.addVertice(valfa);
                numBlocos++;

            }

            // adiciona um vertice "Exit" ao final de um bloco problemático
            if (ultimoBlocoProblematico) {
                Vertice problematico = new Vertice(criaComandos(linha), "Exit");
                processado.addVertice(problematico);
            }

            processado.setaLicacao();
            scan.close();
        } catch (FileNotFoundException e) {
            System.out.println("Arquivo não encontrado na pasta inicial!");
            e.printStackTrace();
        }

        return processado;
    }

    public static void main(String[] args) {

        String pasta = new File("").getAbsolutePath();
        File arquivosDeEntrada = new File(pasta + "/entradas");
        File[] arquivos = arquivosDeEntrada.listFiles();
        List<Grafo> processados = new ArrayList<>();

        for (File aLer : arquivos) {
            processados.add(gerador(aLer));
        }

        for (Grafo e : processados) {
            System.out.println("Grafo " + e.getId() + ":\n");
            e.imprimeTudo();
        }
    }
}
