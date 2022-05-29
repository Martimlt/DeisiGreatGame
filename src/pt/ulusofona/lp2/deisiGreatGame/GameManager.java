package pt.ulusofona.lp2.deisiGreatGame;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

public class GameManager {
    static ArrayList<Programmer> players = new ArrayList<>();
    static HashMap<Integer, Home> casas = new HashMap<>();
    static ArrayList<CountPisadas> contagemDePisadas = new ArrayList<>();
    static int currentPlayer = 0;
    static int tamanhoTabuleiro = 0;
    private static int nrTurnos = 1;

    public GameManager() {
    }

    public static void clear(){
        contagemDePisadas.clear();
        players.clear();
        casas.clear();
        currentPlayer = 0;
        nrTurnos = 1;
    }

    public int countToolsBetweenPositions(int p1, int p2){
        int count = 0;
        for (Home casa: casas.values()) {
            if (casa.getTipo() == 1){
                if (casa.getPosition() > p1 && casa.getPosition() < p2){
                    if (casa.getID() != 4){
                        count++;
                    }
                }
            }
        }
        return count;
    }

    public ArrayList<Programmer> getPlayers(){
        return players;
    }

    public List<Home> getCasas(){
        List<Home> arrayCasas = new ArrayList<>();
        for (Home casa : casas.values()) {
            if (casa.getTipo() == 0){
                arrayCasas.add(casa);
            }
        }
        return arrayCasas;
    }

    public String tryToPutAbyss(String iD, String position){
        for (Home casa: casas.values()) {
            if (casa.getPosition() == Integer.parseInt(position)){
                return "Position is occupied";
            }
        }
        Abyss abismo = new Abyss(0, Integer.parseInt(iD), Integer.parseInt(position));
        abismo.setImageAndTitleAbysses();
        casas.put(Integer.parseInt(position),abismo);
        return "OK";
    }

    public ArrayList<CountPisadas> getContagemDePisadas(){
        return contagemDePisadas;
    }

    public void createInitialBoard(String[][] playerInfo, int worldSize, String[][] abyssesAndTools) throws InvalidInitialBoardException {
        try {
            createInitialBoard(playerInfo,worldSize);
            if (abyssesAndTools != null) {
                for (String[] strings : abyssesAndTools){
                    if (strings[0].equals("0")){
                        if (((0 <= Integer.parseInt(strings[1]) && Integer.parseInt(strings[1]) <= 9) || Integer.parseInt(strings[1]) == 150) && (Integer.parseInt(strings[2]) > 1 && Integer.parseInt(strings[2]) < worldSize)) {
                            Abyss abismo = new Abyss(0, Integer.parseInt(strings[1]), Integer.parseInt(strings[2]));
                            abismo.setImageAndTitleAbysses();
                            casas.put(Integer.parseInt(strings[2]), abismo);
                        } else {
                            throw new InvalidInitialBoardException("Exception foi causada por um abismo;" + strings[1]);
                        }
                    } else if (strings[0].equals("1")){
                        if ((0 <= Integer.parseInt(strings[1]) && Integer.parseInt(strings[1]) <= 5) && (Integer.parseInt(strings[2]) > 1 && Integer.parseInt(strings[2]) < worldSize)) {
                            Tool ferramenta = new Tool(1, Integer.parseInt(strings[1]), Integer.parseInt(strings[2]));
                            ferramenta.setImageAndTitleTools();
                            casas.put(Integer.parseInt(strings[2]), ferramenta);
                        } else {
                            throw new InvalidInitialBoardException("Exception foi causada por uma ferramenta;" + strings[1]);
                        }
                    } else {
                        throw new InvalidInitialBoardException("Tipo errado");
                    }
                }
            }
        }catch (Exception e){
            throw new InvalidInitialBoardException(e.getMessage());
        }
    }

    public void createInitialBoard(String[][] playerInfo, int worldSize) throws InvalidInitialBoardException{
        try {
            clear();
            for (String[] strings : playerInfo) {
                if (verificaNome(strings[1]) && (playerInfo.length > 1 && playerInfo.length <= 4) && (Integer.parseInt(strings[0]) > 0)) {
                    if (programmerColor(strings[3]) != null) {
                        ProgrammerColor colorSet = programmerColor(strings[3]);
                        Programmer programador = new Programmer(Integer.parseInt(strings[0]), strings[1], strings[2], colorSet);
                        players.add(programador);
                    } else {
                        throw new InvalidInitialBoardException("Cor errada");
                    }
                } else {
                    throw new InvalidInitialBoardException("Id errado ou quantidade de jogadores invalida");
                }
            }

            tamanhoTabuleiro = worldSize;
            players.sort(Comparator.comparing(Programmer::getId));
            for (int i = 0; i <=  tamanhoTabuleiro; i++) {
                CountPisadas casa = new CountPisadas(i);
                contagemDePisadas.add(casa);
            }
            if (!(verificaIDAndColor() && worldSize >= (2 * players.size()))){
                throw new InvalidInitialBoardException("Erro ID Color ou worldSize inferior ao doubro de jogadores");
            }
        } catch (Exception e){
            throw new InvalidInitialBoardException(e.getMessage());
        }
    }

    public boolean verificaNome(String nome){
        return nome != null && !nome.equals("");
    }

    public ProgrammerColor programmerColor(String color){
        if (color.equals(ProgrammerColor.BROWN.toString())){
            return ProgrammerColor.BROWN;
        } else if (color.equals(ProgrammerColor.BLUE.toString())){
            return ProgrammerColor.BLUE;
        } else if (color.equals(ProgrammerColor.PURPLE.toString())){
            return ProgrammerColor.PURPLE;
        } else if (color.equals(ProgrammerColor.GREEN.toString())) {
            return ProgrammerColor.GREEN;
        } else {
            return null;
        }
    }

    public boolean verificaIDAndColor (){
        for (int i = 0; i < players.size(); i++) {
            for (int j = i+1; j < players.size(); j++) {
                if ((players.get(i).getId() == players.get(j).getId()) || (players.get(i).getColor() == players.get(j).getColor())){
                    return false;
                }
            }
        }
        return true;
    }

    public boolean saveGame(File file) {
        try {
            FileWriter myWriter = new FileWriter(file);

            myWriter.write(tamanhoTabuleiro + "\n");

            myWriter.write(players.size() + "\n");

            myWriter.write(casas.size() + "\n");

            for (Programmer player: players) {
                myWriter.write(player.getId() + "/" + player.getName() + "/" + player.sortAndTurnToString() + "/" + player.getColor() + "\n");
                myWriter.write(player.getPosition() + "\n");
            }

            myWriter.write("\n"); // separar o tamanho do tabuleiro dos abismos e ferramentas

            for (Integer position: casas.keySet()) {
                myWriter.write(casas.get(position).getTipo() + "/" + casas.get(position).getID() + "/" + casas.get(position).getPosition() + "\n");
            }

            myWriter.write("\n");

            myWriter.write(currentPlayer + "\n");

            myWriter.write(nrTurnos + "\n");
            myWriter.close();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public void setPosition(List<Integer> positions) {
        for (int i = 0; i < positions.size(); i++) {
            players.get(i).setPosition(positions.get(i));
        }
    }

    public boolean loadGame(File file) {
        clear();
        List<Integer> position = new ArrayList<>();
        try {
            Scanner myReader = new Scanner(file);
            tamanhoTabuleiro = Integer.parseInt(myReader.nextLine());
            String[][] playerInfo = new String[Integer.parseInt(myReader.nextLine())][4];
            String[][] abyssesAndTools = new String[Integer.parseInt(myReader.nextLine())][3];
            int count = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (!Objects.equals(data, "")){
                    String[] newData = data.split("/");
                    for (int i = 0; i < 4; i++) {
                        playerInfo[count][i] = newData[i];
                    }
                    position.add(Integer.parseInt(myReader.nextLine()));
                    count ++;
                } else {
                    break;
                }
            }
            count = 0;
            while (myReader.hasNextLine()) {
                String data = myReader.nextLine();
                if (!Objects.equals(data, "")){
                    String[] newData = data.split("/");
                    for (int i = 0; i < 3; i++) {
                        abyssesAndTools[count][i] = newData[i];
                    }
                    count++;
                } else {
                    break;
                }
            }
            createInitialBoard(playerInfo,tamanhoTabuleiro,abyssesAndTools);

            currentPlayer = Integer.parseInt(myReader.nextLine());
            nrTurnos = Integer.parseInt(myReader.nextLine());

            setPosition(position);
            myReader.close();
            return true;
        } catch (FileNotFoundException | InvalidInitialBoardException e) {
            return false;
        }
    }

    public String getImagePng(int position){
        if (position < 1 || tamanhoTabuleiro < position){
            return null;
        } else if (position == tamanhoTabuleiro){
            return "glory.png";
        } else {
            if (casas.get(position) == null){
                return null;
            } else {
                return casas.get(position).getImage();
            }
        }
    }

    public List<Programmer> getProgrammers(boolean includeDefeated){
        List<Programmer> allOrInGamePlayers = new ArrayList<>();
        if (includeDefeated){
            return players;
        } else {
            for (int i = 0; i < players.size(); i++) {
                if(players.get(i).getInGame()){
                    allOrInGamePlayers.add(players.get(i));
                }
            }
            return allOrInGamePlayers;
        }
    }

    public List<Programmer> getProgrammers(int position){
        if (position > 0 && position <= tamanhoTabuleiro){
            ArrayList<Programmer> programmersInPosition = new ArrayList<>();
            for (Programmer player : players) {
                if (player.getPosition() == position) {
                    programmersInPosition.add(player);
                }
            }
            if (programmersInPosition.size() == 0){
                return null;
            }
            return programmersInPosition;
        }
        return null;
    }

    public int getCurrentPlayerID(){
        return players.get(currentPlayer).getId();
    }

    public String getProgrammersInfo(){
        StringBuilder resposta = new StringBuilder();
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getInGame()){ // se ele sair do jogo tem de sair do rodape
                if (players.size() - 1 == i) {
                    if (players.get(i).tamanhoTools()== 0){
                        resposta.append(players.get(i).getName()).append(" : No tools");
                    } else {
                        resposta.append(players.get(i).getName()).append(" : ").append(players.get(i).getListadeferramentas());
                    }
                } else {
                    if (players.get(i).tamanhoTools() == 0){
                        resposta.append(players.get(i).getName()).append(" : No tools | ");
                    } else {
                        resposta.append(players.get(i).getName()).append(" : ").append(players.get(i).getListadeferramentas()).append(" | ");
                    }
                }
            }
        }
        return resposta.toString();
    }

    public String getTitle(int position){
        if (0 < position && position <= tamanhoTabuleiro){
            if (casas.get(position) == null){
                return null;
            } else {
                return casas.get(position).getTitle();
            }
        }
        return null;
    }

    public boolean moveCurrentPlayer(int nrPositions){
        if (nrPositions > 0 && nrPositions <= 6){

            if (!players.get(currentPlayer).estaBloqueado()){
                int novaPosicao = players.get(currentPlayer).getPosition() + nrPositions;

                if (novaPosicao <= tamanhoTabuleiro) {
                    players.get(currentPlayer).setPosition(novaPosicao);
                    players.get(currentPlayer).addPosicao(novaPosicao);
                    contagemDePisadas.get(novaPosicao).somarPidadela();
                } else {
                    players.get(currentPlayer).setPosition(tamanhoTabuleiro + (tamanhoTabuleiro - players.get(currentPlayer).getPosition() - nrPositions));
                    players.get(currentPlayer).addPosicao(tamanhoTabuleiro + (tamanhoTabuleiro - players.get(currentPlayer).getPosition() - nrPositions));
                    contagemDePisadas.get(tamanhoTabuleiro + (tamanhoTabuleiro - players.get(currentPlayer).getPosition() - nrPositions)).somarPidadela();
                }
                return true;
            }
        }
        return false;
    }

    public String moveCurrentPlayerPost(String nrPositions){
        int position = Integer.parseInt(nrPositions);
        moveCurrentPlayer(position);
        if (casas.get(players.get(currentPlayer).getPosition()) != null){
            return reactToAbyssOrTool();
        }
        switchCurrentPlayer();
        return "OK";
    }

    public String reactToAbyssOrTool(){
        nrTurnos++;
        Programmer programmer = players.get(currentPlayer);
        Home casa = casas.get(programmer.getPosition());
        String auxiliar;

        if (casa != null){
            if(casa.getTipo() == 0){
                auxiliar = ((Abyss) casas.get(programmer.getPosition())).whatsHappenAbyss(casa.getID(),currentPlayer);
                switchCurrentPlayer();
                casa.icrementaPisadelas();
                return auxiliar;
            } else if (casa.getTipo() == 1){
                auxiliar = ((Tool) casas.get(programmer.getPosition())).whatsHappenTool(casa.getID(),currentPlayer);
                switchCurrentPlayer();
                return auxiliar;
            }
        }
        switchCurrentPlayer();
        return null;
    }

    public void switchCurrentPlayer(){
        do {
            if (currentPlayer < players.size() - 1){
                currentPlayer++;
            } else {
                currentPlayer = 0;
            }
        } while(!players.get(currentPlayer).getInGame());
    }

    public boolean gameIsOver(){
        int count = 0;
        for (Programmer programmer: players) {
            if (programmer.getPosition() == tamanhoTabuleiro){
                return true;
            }
            if (programmer.getInGame()){
                count++;
            }
        }
        return count == 1;
    }

    public List<String> getGameResults(){
        players.sort(Comparator.comparing(Programmer::getPosition));
        Collections.reverse(players);
        int count = 1;
        while (!players.get(0).getInGame()){
            if (!players.get(0).getInGame()){
                Collections.swap(players,0,count);
                count++;
            }
        }
        String texto = "O GRANDE JOGO DO DEISI;;NR. DE TURNOS;" + nrTurnos + ";;VENCEDOR;" + players.get(0).getName() + ";;RESTANTES";

        String[] separa = texto.split(";");

        ArrayList<String> gamesResults = new ArrayList<>(Arrays.asList(separa));
        if (gameIsOver()){
            for (int i = 1; i < players.size() - 1; i++) {
                for (int j = i+1; j < players.size(); j++) {
                    if (players.get(i).getPosition() == players.get(j).getPosition()){
                        if(players.get(i).getName().compareTo(players.get(j).getName()) > 0){
                            Collections.swap(players,i,j);
                        }
                    }
                }
            }
        }
        for (Programmer programmer: players.subList(1, players.size())) {
            gamesResults.add(programmer.getName() + " " + programmer.getPosition());
        }
        return gamesResults;
    }

    public JPanel getAuthorsPanel(){
        JPanel panel=new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new GridLayout(12, 1, 0, 0));
        JLabel jlabel = new JLabel("\n");
        panel.add(jlabel);
        JLabel jlabel0 = new JLabel("\n");
        panel.add(jlabel0);
        JLabel jlabel1 = new JLabel("\n");
        panel.add(jlabel1);
        JLabel jlabel2 = new JLabel("\n");
        panel.add(jlabel2);
        JLabel jlabel3 = new JLabel("                       JOGO REALIZADO POR: \n");
        panel.add(jlabel3);
        JLabel jlabel4 = new JLabel("                              Martim Teixeira\n");
        panel.add(jlabel4);
        JLabel jlabel5 = new JLabel("                                Rafael Sousa\n");
        panel.add(jlabel5);
        return panel;
    }
}
