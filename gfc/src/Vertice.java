import java.util.ArrayList;
import java.util.List;

public class Vertice {
    // contem coleção de comandos. Um deve ser o lider, o outro a saida, o resto
    // 'bloco'
    private String id;
    private List<Comando> partes = new ArrayList<>();// posso deixar a 1° posição como lider, e a ultima como saida?
                                                     // Melhor ter especifico
    private Comando lider;
    private Comando saida;
    private Boolean saiu = false;
    private Boolean proxEDestinoGOTO = false;
    private Boolean fLAG = true;// durante a execução do grafo, se um vertice tiver fLAG, quer dizer q a proxima
                                // linha é uma
    // ligação dele. Por padrão ele segue sequencialmente
    private String ligacoes = "";

    public Vertice(Comando inicial, String identificacao) {
        this.id = identificacao;
        setLider(inicial);
        setSaida(inicial);
    }

    // função para preencher um Vertice "em branco", usado para criar os vertices de
    // Entrada e Saida
    public void preencheDummy(Comando dummy) {

        setLider(dummy);
        this.saida = dummy;
        saiu = true;
    }

    // setar a saida manualmente. Necessário quando não há goto ou if no arquivo
    public void addComandoDeSaida(Comando saideira) {
        this.saida = saideira;
    }

    // tenta adicionar um comando a lista. Para ser usado em loops...eu acho
    public void addComando(Comando unico) {

        if (unico.getDestinoDeGOTO()) {
            // se é um destino de goto, tenho q dizer q o anterior a ele é a saida
            // as partes sempre vão conter 1 elemento pelo menos, mas msm assim é bom checar
            if (!partes.isEmpty()) {
                saiu = true;
                proxEDestinoGOTO = true;
                saida = partes.get(partes.size() - 1); // pegando a parte anterior a essa
            }

        } else {
            partes.add(unico);
            // preciso checar se é um comando de saida, q termina o bloco
            setSaida(unico);// confere se tem os elementos q termina o bloco
        }

    }

    private void setSaida(Comando linha) {
        if (linha.getContemGOTO()) {
            this.saida = linha;
            this.saiu = true;
            final String[] temp;
            temp = linha.getLinha().split("goto ");
            this.ligacoes = temp[1];// pegando o destino do goto, na segunda posicao do vetor
            this.fLAG = false;// se contem goto, n é sequencial mais

            if (linha.getContemIF()) {// Se contem if, ele vai seguir para o proximo tmb, além do goto
                this.fLAG = true;
                // durante a execução do grafo, se um vertice tiver fLAG, quer dizer q a proxima
                // linha é uma
                // ligação dele.
            }
        }
    }

    public void saidaProblematica() {
        this.saida = this.lider;
    }

    public void setLider(Comando candidato) {
        this.lider = candidato;
        partes.add(candidato); // o lider tmb faz parte do bloco maior de comandos...esse é msm um bom
                               // conceito?
        // preciso msm de um setLider, n posso usar apenas a instanciação?
        // colocar logica adicional para alterar lider, ou é irrelevante?
    }

    public Boolean getfLAG() {
        return fLAG;
    }

    public Boolean getSaiu() {
        return saiu;
    }

    public Comando getLider() {
        return lider;
    }

    public Comando getSaida() {
        return saida;
    }

    public Boolean getProxEDestinoGOTO() {
        return proxEDestinoGOTO;
    }

    public String getLigacoes() {
        return ligacoes;
    }

    public String getId() {
        return id;
    }

    public void imprimeBloco(){
        for(Comando imp : partes){
            System.out.println(imp.getLinha());
        }
    }

    // talvez n esse daqui
    public List<Comando> getPartes() {
        return partes;
    }

}
