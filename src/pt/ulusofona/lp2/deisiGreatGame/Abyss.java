package pt.ulusofona.lp2.deisiGreatGame;

import java.util.ArrayList;

import static pt.ulusofona.lp2.deisiGreatGame.GameManager.*;

public class Abyss extends Home{

    public Abyss(int tipo, int iD, int boardPosition) {
        super(tipo, iD, boardPosition);
    }

    private int verificaPosicao(int valor) {
        if (valor < 1){
            valor = 1;
        }
        return valor;
    }

    public void setImageAndTitleAbysses (){
        switch (iD){
            case 0 : {
                titulo = "Erro de sintaxe";
                image = "syntax.png";
                message = "Volta para trás";
                break;
            }
            case 1 : {
                titulo = "Erro de lógica";
                image = "logic.png";
                message = "Andar para trás nunca fez mal a ninguém";
                break;
            }
            case 2 : {
                titulo = "Exception";
                image = "exception.png";
                message = "Toca a andar 2 casas para trás";
                break;
            }
            case 3 : {
                titulo = "File Not Found Exception";
                image = "file-not-found-exception.png";
                message = "Toca a andar 3 casas para trás";
                break;
            }
            case 4 : {
                titulo = "Crash (aka Rebentanço)";
                image = "crash.png";
                message = "E que tal começares o jogo de novo?\nVolta para a casa 1";
                break;
            }
            case 5 : {
                titulo = "Duplicated Code";
                image = "duplicated-code.png";
                message = "Aqui não ficas, volta para onde estavas!";
                break;
            }
            case 6 : {
                titulo = "Efeitos secundários";
                image = "secondary-effects.png";
                message = "Vá volta para trás que estás a ir demasiado rápido";
                break;
            }
            case 7 : {
                titulo = "Blue Screen of Death";
                image = "bsod.png";
                message = "Ups, alguém acabou de perder o jogo";
                break;
            }
            case 8 : {
                titulo = "Ciclo infinito";
                image = "infinite-loop.png";
                message = "É melhor rezares para que calhe aqui outro jogador";
                break;
            }
            case 9 : {
                titulo = "Segmentation Fault";
                image = "core-dumped.png";
                message = "Tudo a voltar 3 casas para trás";
                break;
            }
        }
    }

    public String whatsHappenAbyss(int id , int playerAtual){
        switch (id) {
            case 0 : { // Erro de sintaxe
                if (players.get(playerAtual).temATool(4)){
                    players.get(playerAtual).removeTool(4);
                    return "Safaste-te malandro";
                }
                if (players.get(playerAtual).temATool(5)){
                    players.get(playerAtual).removeTool(5);
                    return "Safaste-te malandro";
                }
                int novaPosicao = boardPosition - 1;
                players.get(playerAtual).setPosition(novaPosicao);
                players.get(playerAtual).addPosicao(novaPosicao);
                return message;
            }
            case 1 : { // Erro de Lógica
                if (players.get(playerAtual).temATool(2)){
                    players.get(playerAtual).removeTool(2);
                    return "Safaste-te malandro";
                }
                if (players.get(playerAtual).temATool(5)){
                    players.get(playerAtual).removeTool(5);
                    return "Safaste-te malandro";
                }
                int posicaoAnterior = players.get(playerAtual).getPosicoesAnteriores().get(players.get(playerAtual).getPosicoesAnteriores().size() - 2);
                int posicaoAtual = players.get(playerAtual).getPosition();
                int metadeDado = (posicaoAtual - posicaoAnterior)/2;
                players.get(playerAtual).setPosition(posicaoAtual - metadeDado);
                players.get(playerAtual).addPosicao(posicaoAtual - metadeDado);
                return message;
            }
            case 2 : { // Exception
                if (players.get(playerAtual).temATool(3)){
                    players.get(playerAtual).removeTool(3);
                    return "Safaste-te malandro";
                }
                if (players.get(playerAtual).temATool(5)){
                    players.get(playerAtual).removeTool(5);
                    return "Safaste-te malandro";
                }
                int novaPosicao = boardPosition - 2;
                novaPosicao = verificaPosicao(novaPosicao);
                players.get(playerAtual).setPosition(novaPosicao);
                players.get(playerAtual).addPosicao(novaPosicao);
                return message;
            }
            case 3 : { // File Not Found Exception
                if (players.get(playerAtual).temATool(3)){
                    players.get(playerAtual).removeTool(3);
                    return "Safaste-te malandro";
                }
                if (players.get(playerAtual).temATool(5)){
                    players.get(playerAtual).removeTool(5);
                    return "Safaste-te malandro";
                }
                int novaPosicao = boardPosition - 3;
                novaPosicao = verificaPosicao(novaPosicao);
                players.get(playerAtual).setPosition(novaPosicao);
                players.get(playerAtual).addPosicao(novaPosicao);
                return message;
            }
            case 4 : { // Crash
                players.get(playerAtual).setPosition(1);
                players.get(playerAtual).addPosicao(1);
                return message;
            }
            case 5 : { // Duplicated code
                if (players.get(playerAtual).temATool(0)){
                    players.get(playerAtual).removeTool(0);
                    return "Safaste-te malandro";
                }
                int posicaoAnterior = players.get(playerAtual).getPosicoesAnteriores().get(players.get(playerAtual).getPosicoesAnteriores().size() - 2);
                players.get(playerAtual).setPosition(posicaoAnterior);
                players.get(playerAtual).addPosicao(posicaoAnterior);
                return message;
            }
            case 6 : { // Efeitos Secundarios
                if (players.get(playerAtual).temATool(1)){
                    players.get(playerAtual).removeTool(1);
                    return "Safaste-te malandro";
                }
                int duasPosicoesAtras = players.get(playerAtual).getPosicoesAnteriores().get(players.get(playerAtual).getPosicoesAnteriores().size() - 3);
                players.get(playerAtual).setPosition(duasPosicoesAtras);
                players.get(playerAtual).addPosicao(duasPosicoesAtras);
                return message;
            }
            case 7 : { // blue screen of death
                if (players.get(playerAtual).getInGame()){
                    players.get(playerAtual).perdeu();
                    return message;
                }
                break;
            }
            case 8 : { // Ciclo infinito
                if (players.get(playerAtual).temATool(1)){
                    players.get(playerAtual).removeTool(1);
                    return "Safaste-te malandro";
                }
                if (!players.get(playerAtual).estaBloqueado()){
                    players.get(playerAtual).bloquear();
                    programmersInHome.add(players.get(playerAtual));
                }
                if (programmersInHome.size() >= 2){
                    programmersInHome.get(0).desbloquear();
                    programmersInHome.remove(0);
                }
                return message;
            }
            case 9 : { // Segmentation Fault
                ArrayList<Programmer> playersInHome = new ArrayList<>();
                for (int i = 0; i < players.size(); i++) {
                    if (players.get(i).getPosition() == boardPosition){
                        playersInHome.add(players.get(i));
                    }
                }
                if (playersInHome.size() >= 2){
                    for (int i = 0; i < playersInHome.size(); i++) {
                        int novaposicao = playersInHome.get(i).getPosition() - 3;
                        novaposicao = verificaPosicao(novaposicao);
                        playersInHome.get(i).setPosition(novaposicao);
                        playersInHome.get(i).addPosicao(novaposicao);
                    }
                    playersInHome.clear();
                }
                return message;
            }
        }
        return null;
    }

}
