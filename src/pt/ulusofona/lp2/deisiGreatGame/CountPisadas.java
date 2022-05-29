package pt.ulusofona.lp2.deisiGreatGame;


public class CountPisadas {
    int nrDepisadelas;
    int posicao;

    public CountPisadas(int posicao) {
        this.posicao = posicao;
        nrDepisadelas = 0;
    }

    public void somarPidadela(){
        nrDepisadelas++;
    }

    public int getNrDepisadelas() {
        return nrDepisadelas;
    }

    public String message (){
        return posicao + ":" + nrDepisadelas;
    }
}
