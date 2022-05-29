package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;

import static pt.ulusofona.lp2.deisiGreatGame.GameManager.*;

public class Tool extends Home{

    public Tool(int tipo, int iD, int boardPosition) {
        super(tipo, iD, boardPosition);
    }


    public void setImageAndTitleTools (){
        switch (iD){
            case 0: { // Feito
                titulo = "Herança";
                image = "inheritance.png";
                message = "Agora ficou muito mais simples";
                break;
            }
            case 1: {
                titulo = "Programação Funcional";
                image = "functional.png";
                message = "Agora fica mais fácil";
                break;
            }
            case 2: {
                titulo = "Testes unitários";
                image = "unit-tests.png";
                message = "Erros? Não sei o que é isso";
                break;
            }
            case 3: {
                titulo = "Tratamento de Excepções";
                image = "catch.png";
                message = "Agora é que as exceções já não te apanham!";
                break;
            }
            case 4: {
                titulo = "IDE";
                image = "IDE.png";
                message = "Melhor ajuda que poderias ter";
                break;
            }
            case 5: { // Feito
                titulo = "Ajuda Do Professor";
                image = "ajuda-professor.png";
                message = "Só com ajuda do professor é que vais lá!";
                break;
            }
        }
    }

    public String whatsHappenTool(int id , int playerAtual){
        Tool tool = (Tool) casas.get(players.get(playerAtual).getPosition());
        players.get(playerAtual).addTool(tool);

        return message;
    }
}
