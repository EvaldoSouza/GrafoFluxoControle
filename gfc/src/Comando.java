public class Comando {
    //deve receber uma linha do codigo de trÊs endereços
    private String linha;
    private Boolean contemIF = false;
    private Boolean contemGOTO = false;
    private Boolean destinoDeGOTO = false;

    public Comando(String linha){
        setLinha(linha);
        ifCheck();
        ifDestino();
        ifGoto();
    }

    public void setLinha(String linha) {
        this.linha = linha;
    }

    private void ifCheck(){
        if(linha.contains("if")){
            this.contemIF = true;
        }else{
            this.contemIF = false;
        }    
    }

    private void ifGoto(){
        if(linha.contains("goto")){
            this.contemGOTO = true;
        }else{
            this.contemGOTO = false;
        }  
    }

    //chamar esse metodo para a primeira linha do arquivo, provavelmente fora do loop principal
    public void primeiraLinha(){
        destinoDeGOTO = true;
    }

    //mais complicado dos checkups. Mesmo que sempre for L, ainda assim é complicado
    /**Se a linha começa com L(maíusculo), é um destino?
     * Aparentemente sim...e mesmo que houver outras formas de implementar (T, J, K...), n é dificil modificar o codigo
     */
    private void ifDestino(){
        if(linha.startsWith("L")){//pode ser q n funcione...testar para ver
            this.destinoDeGOTO = true;
        }else{
            this.destinoDeGOTO = false;
        }
    }

    public String getLinha() {
        return linha;
    }
    
    public Boolean getContemGOTO() {
        return contemGOTO;
    }

    public Boolean getContemIF() {
        return contemIF;
    }

    public Boolean getDestinoDeGOTO() {
        return destinoDeGOTO;
    }
}
