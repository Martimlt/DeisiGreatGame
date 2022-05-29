package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;


public class Home {
    protected int tipo;
    protected int iD;
    protected int boardPosition;
    protected String image;
    protected String titulo;
    protected String message;
    protected ArrayList<Programmer> programmersInHome = new ArrayList<>();
    protected int pisadelas = 0;

    public Home(int tipo, int iD, int boardPosition) {
        this.tipo = tipo;
        this.iD = iD;
        this.boardPosition = boardPosition;
    }

    public void icrementaPisadelas() {
        pisadelas++;
    }

    public int getPisadelas() {
        return pisadelas;
    }

    public int getTipo (){
        return tipo;
    }

    public int getID(){
        return iD;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return titulo;
    }

    public int getPosition(){
        return boardPosition;
    }

}
