import java.util.ArrayList;
import java.util.List;

public class Grafo {
    // Deve conter varios vértices, com suas ligações, e métodos de impressão
    private List<List<Vertice>> bolinhas = new ArrayList<>(); // uma lista de listas de vertices
    private String id = "";

    /**
     * Uma lista de listas de vertices, cada linha contendo uma lista de vertices,
     * onde o primeiro elemento é o "main", e os proximos são suas ligações
     */

    public void setId(String id) {
        this.id = id;
    }

    // metodo para adicionar um vertice, criando uma nova linha na matriz para isso
    public void addVertice(Vertice finalizado) {
        // da certo fazer isso aqui dentro?
        final List<Vertice> nodeUnico = new ArrayList<>(); // lista para fazer as ligações. Passar ela para 'bolinhas'
        nodeUnico.add(finalizado);
        bolinhas.add(nodeUnico);
    }

    // cria o vertice de entrada do grafo...acredito que para efeitos de pesquisa e
    // organização...
    // mas ainda n estou muito certo do motivo desse bloco
    public void verticeEntrada() {
        // o q o vertice de entrada tem q ter?
        // id especial (entry) e ligação seqencial com o proximo
        Comando dummy = new Comando("Entry");
        Vertice entrada = new Vertice(dummy, "Entry");
        entrada.preencheDummy(dummy);
        addVertice(entrada);
    }

    // o saida tem q ser ligado com os blocos relevantes!
    // acho q n precisa ter vertice de saida...acho
    public void verticeSaida() {
        Comando dummy = new Comando("Exit");
        Vertice saida = new Vertice(dummy, "Exit");
        saida.preencheDummy(dummy);
        addVertice(saida);
    }

    // ler cada vertice, ver quem são suas ligações, e colocar na msm linha
    public void setaLicacao() {
        // dois tipos de ligação: destino de goto, e sequencial
        for (int externo = 0; externo < bolinhas.size(); externo++) {
            // sequencial vem da flag 'fLAG', e significa q o proximo vertice se liga ao
            // atual
            // se o vertice B1 tiver fLAG, quer dizer q o B2 se liga a ele
            // primeiro confiro se getfLAG n é null, depois vejo se é verdadeiro, pois
            // talvez tenha chance de ser null
            if (bolinhas.get(externo).get(0).getfLAG() != null && bolinhas.get(externo).get(0).getfLAG()
                    && externo + 1 < bolinhas.size()) {
                bolinhas.get(externo).add(bolinhas.get(externo + 1).get(0));
            }

            // destino de goto vem da string "destino", que pega o endereço logo após o goto
            // (L0,L1...), e é necessario pesquisar
            String ligacao = bolinhas.get(externo).get(0).getLigacoes();

            if (!ligacao.isBlank() && !ligacao.isEmpty()) {
                for (int interno = 0; interno < bolinhas.size(); interno++) {
                    String nomeLider = bolinhas.get(interno).get(0).getLider().getLinha();
                    // fazendo a partir do lider do bloco, pois 'atual' é apenas um pedaço da string
                    if (nomeLider.startsWith(ligacao)) {
                        bolinhas.get(externo).add(bolinhas.get(interno).get(0));// adicionando o primeiro vertice do
                                                                                // 'interno'
                        // a linha do 'externo'
                    }
                }
            }
        }
    }

    // imprimindo só as ligações
    public void imprimeLigacoes() {
        for (int externo = 0; externo < bolinhas.size(); externo++) {
            if (!bolinhas.get(externo).get(0).getId().equals("Exit")) {

                System.out.print(bolinhas.get(externo).get(0).getId() + "-> ");
            }
            if (bolinhas.get(externo).size() > 1) {
                for (int interno = 1; interno < bolinhas.get(externo).size(); interno++) {
                    System.out.print(bolinhas.get(externo).get(interno).getId() + ",");

                }
            }
            if (!bolinhas.get(externo).get(0).getId().equals("Exit")) {
                System.out.println("\n--------------");// para dar um \n no final
            }
        }
    }

    public void imprimeLideres() {
        for (int externo = 0; externo < bolinhas.size(); externo++) {
            System.out.println(bolinhas.get(externo).get(0).getLider().getLinha());
        }

    }

    public void imprimeSaidas() {
        for (int externo = 0; externo < bolinhas.size(); externo++) {
            System.out.println(bolinhas.get(externo).get(0).getSaida().getLinha());
        }
    }

    public int quantosVertices() {
        return bolinhas.size();
    }

    // retorna o primeiro vertice da linha passada
    public Vertice retornaVertice(int indexLinha) {
        return bolinhas.get(indexLinha - 1).get(0);
    }

    public String getId() {
        return id;
    }

    public void imprimeTudo(){
        for (int externo = 1; externo < bolinhas.size(); externo++){//começando no 1, pq o a posição 0 é o vetor de entrada
            System.out.print(bolinhas.get(externo).get(0).getId()+ ": ");
            bolinhas.get(externo).get(0).imprimeBloco();
            System.out.println("");
        }
        System.out.println("Grafo:");
        imprimeLigacoes();
    }
}
