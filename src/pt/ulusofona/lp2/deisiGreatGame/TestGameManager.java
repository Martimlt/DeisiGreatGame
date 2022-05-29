package pt.ulusofona.lp2.deisiGreatGame;


import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import static junit.framework.TestCase.*;
import static pt.ulusofona.lp2.deisiGreatGame.GameManager.*;

public class TestGameManager {
    
    @Test
    public void TestGameIsOver() {
        GameManager game = new GameManager();
        GameManager.clear();
        GameManager.tamanhoTabuleiro = 79;
        Programmer programador = new Programmer(88,"Diogo", "Java; C", ProgrammerColor.BROWN);
        programador.setPosition(79);
        players.add(programador);

        assertTrue(game.gameIsOver());
    }

    @Test
    public void TestGetCurrentPlayerID_001() {
        GameManager game = new GameManager();
        GameManager.clear();
        game.tamanhoTabuleiro = 79;
        Programmer programador = new Programmer(12,"Bruno", "Kotlin", ProgrammerColor.GREEN);
        programador.setPosition(12);
        players.add(programador);

        int resultadoObtido = players.get(0).getId();
        int resultadoEsperado = 12;

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void TestAddAndRemoveTool() {
        GameManager game = new GameManager();
        GameManager.clear();
        game.tamanhoTabuleiro = 79;
        Programmer programador = new Programmer(12,"Bruno", "Kotlin", ProgrammerColor.GREEN);
        programador.setPosition(12);
        players.add(programador);
        Tool tool = new Tool(1,5,13);
        programador.addTool(tool);

        int resultadoObtido = programador.tamanhoTools();
        int resultadoEsperado = 1;

        assertEquals(resultadoEsperado,resultadoObtido);

        programador.removeTool(5);

        resultadoObtido = programador.tamanhoTools();
        resultadoEsperado = 0;

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void TestGetCurrentPlayerId_002() {
        GameManager game = new GameManager();
        GameManager.clear();
        game.tamanhoTabuleiro = 79;
        Programmer programador = new Programmer(1,"Bruno", "Kotlin", ProgrammerColor.GREEN);
        Programmer programador1 = new Programmer(2,"Tiago", "C", ProgrammerColor.BLUE);
        Programmer programador2 = new Programmer(3,"Martim", "Java", ProgrammerColor.PURPLE);
        Programmer programador3 = new Programmer(4,"Rafael", "Lisp", ProgrammerColor.BROWN);
        programador.setPosition(1);
        programador1.setPosition(1);
        programador2.setPosition(1);
        programador3.setPosition(1);
        players.add(programador);
        players.add(programador1);
        players.add(programador2);
        players.add(programador3);


        int resultadoObtido = game.getCurrentPlayerID();
        int resultadoEsperado = 1;

        assertEquals(resultadoEsperado,resultadoObtido);

        game.reactToAbyssOrTool();

        resultadoObtido = game.getCurrentPlayerID();
        resultadoEsperado = 2;

        assertEquals(resultadoEsperado,resultadoObtido);

        game.reactToAbyssOrTool();

        resultadoObtido = game.getCurrentPlayerID();
        resultadoEsperado = 3;

        assertEquals(resultadoEsperado,resultadoObtido);

        game.reactToAbyssOrTool();

        resultadoObtido = game.getCurrentPlayerID();
        resultadoEsperado = 4;

        assertEquals(resultadoEsperado,resultadoObtido);

        game.reactToAbyssOrTool();

        resultadoObtido = game.getCurrentPlayerID();
        resultadoEsperado = 1;

        assertEquals(resultadoEsperado,resultadoObtido);

        game.reactToAbyssOrTool();

        resultadoObtido = game.getCurrentPlayerID();
        resultadoEsperado = 2;

        assertEquals(resultadoEsperado,resultadoObtido);
    }

    @Test
    public void TestMovePlayers_003() throws InvalidInitialBoardException{
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Pedro", "Kotlin", "Brown"},
                {"2", "Martim", "Kotlin", "Blue"},
                {"3", "Rafael", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"0", "5", "5"},
                {"1", "3", "2"},
                {"1", "1", "3"}
        };

        game.createInitialBoard(playerInfo, 20, itemsInfo);

        boolean resultadoEsperado = game.moveCurrentPlayer(4);

        game.reactToAbyssOrTool();

        assertTrue(resultadoEsperado);

        resultadoEsperado = game.moveCurrentPlayer(2);

        game.reactToAbyssOrTool();

        assertTrue(resultadoEsperado);

        resultadoEsperado = game.moveCurrentPlayer(3);

        game.reactToAbyssOrTool();

        assertTrue(resultadoEsperado);

        resultadoEsperado = game.moveCurrentPlayer(5);

        game.reactToAbyssOrTool();

        assertTrue(resultadoEsperado);

    }

    @Test
    public void TestverificaCicloInfinito_001() throws InvalidInitialBoardException {

        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Pedro", "Kotlin", "Brown"},
                {"2", "Martim", "Kotlin", "Blue"},
        };

        String[][] itemsInfo = new String[][]{
                {"0", "8", "5"},
        };

        game.createInitialBoard(playerInfo, 10, itemsInfo);

        game.moveCurrentPlayer(2); // 1

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(1); // 2

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 1

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(3); // 2

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 1

        game.reactToAbyssOrTool();

        boolean esperado = game.moveCurrentPlayer(2); // 2

        assertFalse(esperado);
    }

    @Test
    public void TestverificaCicloInfinito_002() throws InvalidInitialBoardException{

        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Pedro", "Kotlin", "Brown"},
                {"2", "Martim", "Kotlin", "Blue"},
                {"3", "Rafael", "Kotlin", "Green"},
                {"4", "Joao", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "1", "3"},
                {"0", "8", "5"},
        };

        game.createInitialBoard(playerInfo, 15, itemsInfo);

        game.moveCurrentPlayer(4); // 1

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 2

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 3

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 4

        game.reactToAbyssOrTool();

        boolean esperado = players.get(GameManager.currentPlayer).estaBloqueado();
        assertFalse(esperado);

        game.moveCurrentPlayer(2); // 1

        game.reactToAbyssOrTool();

        esperado = players.get(GameManager.currentPlayer).estaBloqueado();
        assertFalse(esperado);

        game.moveCurrentPlayer(1); // 2

        game.reactToAbyssOrTool();

        esperado = players.get(GameManager.currentPlayer).estaBloqueado();
        assertFalse(esperado);

        game.moveCurrentPlayer(2); // 3

        game.reactToAbyssOrTool();

        esperado = game.moveCurrentPlayer(2); // 4
        assertFalse(esperado);

    }

    @Test
    public void TestverificaCicloInfinito_003() throws InvalidInitialBoardException{

        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Pedro", "Kotlin", "Brown"},
                {"2", "Martim", "Kotlin", "Blue"},
                {"3", "Rafael", "Kotlin", "Green"},
        };

        String[][] itemsInfo = new String[][]{
                {"1", "1", "3"},
                {"0", "8", "5"},
        };

        game.createInitialBoard(playerInfo, 15, itemsInfo);

        game.moveCurrentPlayer(2); // 1

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 2

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 3

        game.reactToAbyssOrTool();

        boolean esperado = players.get(GameManager.currentPlayer).estaBloqueado();
        assertFalse(esperado);

        esperado = game.moveCurrentPlayer(2); // 1
        assertTrue(esperado);

        game.reactToAbyssOrTool();

        esperado = players.get(GameManager.currentPlayer).estaBloqueado();
        assertFalse(esperado);

        esperado = game.moveCurrentPlayer(2); // 2
        assertTrue(esperado);

        game.reactToAbyssOrTool();

        esperado = players.get(GameManager.currentPlayer).estaBloqueado();
        assertTrue(esperado);

        esperado = game.moveCurrentPlayer(1); // 3
        assertFalse(esperado);
    }

    @Test
    public void TestverificaCicloInfinito_004() throws InvalidInitialBoardException{

        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Pedro", "Kotlin", "Brown"},
                {"2", "Martim", "Kotlin", "Blue"},
        };

        String[][] itemsInfo = new String[][]{
                {"0", "8", "3"},
                {"0", "2", "5"}
        };

        game.createInitialBoard(playerInfo, 15, itemsInfo);

        game.moveCurrentPlayer(4); // 1

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 2

        game.reactToAbyssOrTool();

        boolean esperado = players.get(GameManager.currentPlayer).estaBloqueado();
        assertFalse(esperado);

        game.moveCurrentPlayer(2); // 1

        game.reactToAbyssOrTool();

        esperado = players.get(GameManager.currentPlayer).estaBloqueado();
        assertTrue(esperado);
    }

    @Test
    public void TestverificaCicloInfinito_005()throws InvalidInitialBoardException{
    GameManager game = new GameManager();
        GameManager.clear();
    String[][] playerInfo = new String[][]{
            {"1", "Pedro", "Kotlin", "Brown"},
            {"2", "Martim", "Kotlin", "Blue"},
            {"3", "Rafael", "Kotlin", "Green"},
    };

    String[][] itemsInfo = new String[][]{
            {"1", "1", "3"},
            {"0", "8", "5"},
    };

        game.createInitialBoard(playerInfo, 15, itemsInfo);

        game.moveCurrentPlayer(2); // 1 apanha ferramenta

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 2 fica bloqueado

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(3); // 3 fica em casa sem nada

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 1 vai para a casa com abismo mas não fica preso

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 2 Não mexe

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(1); // 3 Fica preso

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 1 anda normal

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 2 Esta solto

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(1); // 3 Está preso

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 1 Esta solto

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 2 esta solto

        game.reactToAbyssOrTool();

    boolean esperado = game.moveCurrentPlayer(1); // 3 esta preso
    assertFalse(esperado);
}

    @Test
    public void TestverificaSegmentationFault_002()throws InvalidInitialBoardException {

        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Pedro", "Kotlin", "Brown"},
                {"2", "Martim", "Kotlin", "Blue"},
        };

        String[][] itemsInfo = new String[][]{
                {"0", "9", "3"},
        };

        game.createInitialBoard(playerInfo, 10, itemsInfo);

        game.moveCurrentPlayer(2); // 1

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 2

        game.reactToAbyssOrTool();

        int resultado = players.get(0).getPosition();

        assertEquals(1,resultado);

        int resultado2 = players.get(1).getPosition();

        assertEquals(1, resultado2);
    }

    @Test
    public void TestverificaSegmentationFault_001() throws InvalidInitialBoardException{

        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Pedro", "Kotlin", "Brown"},
                {"2", "Martim", "Kotlin", "Blue"},
        };

        String[][] itemsInfo = new String[][]{
                {"0", "9", "5"},
        };

        game.createInitialBoard(playerInfo, 10, itemsInfo);

        game.moveCurrentPlayer(4); // 1

        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 2

        game.reactToAbyssOrTool();

        int resultado = players.get(0).getPosition();

        assertEquals(2,resultado);


    }

    @Test
    public void TestverificaGameIsOver_001() throws InvalidInitialBoardException{

        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Pedro", "Kotlin", "Brown"},
                {"2", "Martim", "Kotlin", "Blue"},
        };

        String[][] itemsInfo = new String[][]{
        };

        game.createInitialBoard(playerInfo, 10, itemsInfo);

        game.moveCurrentPlayer(4); // 1

        game.reactToAbyssOrTool();

        boolean esperado = game.gameIsOver();
        assertFalse(esperado);

        game.moveCurrentPlayer(4); // 2

        game.reactToAbyssOrTool();

        esperado = game.gameIsOver();
        assertFalse(esperado);

        game.moveCurrentPlayer(5); // 1

        game.reactToAbyssOrTool();

        esperado = game.gameIsOver();
        assertTrue(esperado);
    }

    @Test
    public void TestverificaGameIsOver_002() throws InvalidInitialBoardException{

        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Pedro", "Kotlin", "Brown"},
                {"2", "Martim", "Kotlin", "Blue"},
        };

        String[][] itemsInfo = new String[][]{
        };

        game.createInitialBoard(playerInfo, 10, itemsInfo);

        game.moveCurrentPlayer(4); // 1

        game.reactToAbyssOrTool();

        boolean esperado = game.gameIsOver();
        assertFalse(esperado);

        game.moveCurrentPlayer(4); // 2

        game.reactToAbyssOrTool();

        esperado = game.gameIsOver();
        assertFalse(esperado);

        game.moveCurrentPlayer(2); // 1

        players.get(GameManager.currentPlayer).perdeu();

        game.reactToAbyssOrTool();

        esperado = game.gameIsOver();
        assertTrue(esperado);
    }

    @Test
    public void TestGetGameResults_001() throws InvalidInitialBoardException{

        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"2", "Rafael", "Kotlin", "Brown"},
                {"3", "Gabriel", "Kotlin", "Blue"},
                {"4", "Pedro", "Kotlin", "Green"},
                {"1", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{};

        game.createInitialBoard(playerInfo, 10, itemsInfo);

        game.moveCurrentPlayer(4); // 1
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 2
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 3
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(6); // 4
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 1
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 2
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 3
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(3); // 4
        game.reactToAbyssOrTool();

        List<String> resultadoObetido = game.getGameResults();
        for (String s:resultadoObetido) {
            System.out.println(s);
        }

    }

    @Test
    public void TestGetGameResults_002()throws InvalidInitialBoardException{

        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"2", "Neo", "Kotlin", "Brown"},
                {"3", "Bruninho", "Kotlin", "Blue"}

        };

        String[][] itemsInfo = new String[][]{
                {"0","7","7"}
        };

        game.createInitialBoard(playerInfo, 10, itemsInfo);

        game.moveCurrentPlayer(6); // 1
        game.reactToAbyssOrTool();
        assertTrue(game.gameIsOver());
        List<String> resultadoObtido = game.getGameResults();
        for (String s:resultadoObtido) {
            System.out.println(s);
        }
    }

    @Test
    public void TestGetGameResults_003() throws InvalidInitialBoardException{

        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"2", "Rafael", "Kotlin", "Brown"},
                {"3", "Gabriel", "Kotlin", "Blue"},
                {"4", "Pedro", "Kotlin", "Green"},
                {"1", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"0","7","7"}
        };

        game.createInitialBoard(playerInfo, 10, itemsInfo);

        game.moveCurrentPlayer(6); // 1
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 2
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(6); // 3
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(6); // 4
        game.reactToAbyssOrTool();

        List<String> resultadoObetido = game.getGameResults();
        for (String s:resultadoObetido) {
            System.out.println(s);
        }

    }

    @Test
    public void TestGetGameResults_004() throws InvalidInitialBoardException{

        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin", "Brown"},
                {"2", "Gabriel", "Kotlin", "Blue"},
                {"3", "Pedro", "Kotlin", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"0","7","10"}
        };

        game.createInitialBoard(playerInfo, 12, itemsInfo);

        game.moveCurrentPlayer(6); // 1
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 2
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(6); // 3
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(6); // 4
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(5); // 1 - ganha
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(6); // 2 - posição 11
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(3); // 3 - bsod - posição 10
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(3); // 4 - bsod - posição 10
        game.reactToAbyssOrTool();

        List<String> resultadoObetido = game.getGameResults();
        for (String s:resultadoObetido) {
            System.out.println(s);
        }

    }

    @Test
    public void testeFullGameTools() throws InvalidInitialBoardException{
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin", "Brown"},
                {"2", "Gabriel", "Kotlin", "Blue"},
                {"3", "Pedro", "Kotlin", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1","0","2"},
                {"1","1","3"},
                {"1","2","4"},
                {"1","3","5"},
                {"1","4","6"},
                {"1","5","7"}
        };

        game.createInitialBoard(playerInfo, 20, itemsInfo);

        game.moveCurrentPlayer(1); // 1 "Herança"
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 2 "Programação Funcional";
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(3); // 3 "Testes unitários";
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 4  "Tratamento de Excepções";
        game.reactToAbyssOrTool();

        // ------------------------------------------------

        game.moveCurrentPlayer(4); // 1 "IDE";
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 2 "Ajuda Do Professor";
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 3 "Erro de sintaxe";
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 4 Erro de lógica 2 casas
        game.reactToAbyssOrTool();


        List<String> resultadoObetido = game.getGameResults();
        for (String s:resultadoObetido) {
            System.out.println(s);
        }

    }

    @Test
    public void testeFullGameAbyss_001() throws InvalidInitialBoardException{
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin", "Brown"},
                {"2", "Gabriel", "Kotlin", "Blue"},
                {"3", "Pedro", "Kotlin", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"0","0","3"},
                {"0","1","4"},
                {"0","2","7"},
                {"0","3","8"},
                {"0","4","9"},
                {"0","5","10"}
        };

        game.createInitialBoard(playerInfo, 20, itemsInfo);

        game.moveCurrentPlayer(2); // 1 posicao 2
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(3); // 2 posicao 3
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(6); // 3 posicao 5
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(5); // 4 posicao 6
        game.reactToAbyssOrTool();

        // ------------------------------------------------

        game.moveCurrentPlayer(6); // 1 posicao 5
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(6); // 2 posicao 1
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(6); // 3 posicao 11
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 4 posicao 6
        game.reactToAbyssOrTool();

        // ------------------------------------------------

        game.moveCurrentPlayer(6); // 1 posicao 11
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(5); // 2 posicao 6
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 3
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 4
        game.reactToAbyssOrTool();


        List<String> resultadoObetido = game.getGameResults();
        for (String s:resultadoObetido) {
            System.out.println(s);
        }

    }

    @Test
    public void testeFullGameAbyss_002() throws InvalidInitialBoardException{
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin", "Brown"},
                {"2", "Gabriel", "Kotlin", "Blue"}
        };

        String[][] itemsInfo = new String[][]{
                {"0", "6", "10"},
                {"0", "7", "3"}
        };
        game.createInitialBoard(playerInfo, 20, itemsInfo);

        game.moveCurrentPlayer(4); // 1 posicao 5
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 2 posicao 3 morriii!!
        game.reactToAbyssOrTool();

        // ------------------------------------------------

        game.moveCurrentPlayer(3); // 1 posicao 7
        game.reactToAbyssOrTool();

        // ------------------------------------------------

        game.moveCurrentPlayer(2); // 1 posicao 10
        game.reactToAbyssOrTool();

        List<String> resultadoObetido = game.getGameResults();
        for (String s:resultadoObetido) {
            System.out.println(s);
        }
    }

    @Test
    public void TestToStringProgrammer() throws InvalidInitialBoardException{
        Programmer programador = new Programmer();
        programador = new Programmer(47, "Martim", "Java; Python; C", ProgrammerColor.BLUE);
        String  resultadoEsperado = programador.toString();

        assertEquals("47 | Martim | 1 | No tools | C; Java; Python | Em Jogo",resultadoEsperado);

        Tool ferramenta = new Tool(1, 2, 5);
        ferramenta.setImageAndTitleTools();
        programador.addTool(ferramenta);
        resultadoEsperado = programador.toString();
        assertEquals("47 | Martim | 1 | Testes unitários | C; Java; Python | Em Jogo",resultadoEsperado);

        ferramenta = new Tool(1, 4, 7);
        ferramenta.setImageAndTitleTools();
        programador.addTool(ferramenta);
        programador.perdeu();
        resultadoEsperado = programador.toString();
        assertEquals("47 | Martim | 1 | Testes unitários;IDE | C; Java; Python | Derrotado",resultadoEsperado);
        assertFalse(programador.getInGame());

    }

    @Test
    public void TestGetProgrammersInfo() throws InvalidInitialBoardException{
        Main i = new Main();
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin", "Brown"},
                {"2", "Pedro", "Kotlin", "Green"},
                {"3", "Martim", "Kotlin", "Purple"}
        };
        String[][] itemsInfo = new String[][]{
        };
        game.createInitialBoard(playerInfo, 20, itemsInfo);

        Tool ferramenta = new Tool(1, 2, 5);
        Tool ferramenta2 = new Tool(1, 4, 7);

        ferramenta.setImageAndTitleTools();
        ferramenta2.setImageAndTitleTools();

        players.get(2).addTool(ferramenta);

        players.get(1).addTool(ferramenta);
        players.get(1).addTool(ferramenta2);

        String resultadoEsperado = game.getProgrammersInfo();
        assertEquals("Rafael : No tools | Pedro : Testes unitários;IDE | Martim : Testes unitários",resultadoEsperado);

        players.get(2).removeTool(2);

        resultadoEsperado = game.getProgrammersInfo();
        assertEquals("Rafael : No tools | Pedro : Testes unitários;IDE | Martim : No tools",resultadoEsperado);

        List<Programmer> resultadoObetido = game.getProgrammers(1);
        for (Programmer s:resultadoObetido) {
            System.out.println(s);
        }

        resultadoObetido = game.getProgrammers(false);
        for (Programmer s:resultadoObetido) {
            System.out.println(s);
        }
    }

    @Test
    public void TestGetProgrammers() throws InvalidInitialBoardException{
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin", "Brown"},
                {"2", "Pedro", "Kotlin", "Green"},
                {"3", "Martim", "Kotlin", "Purple"}
        };
        String[][] itemsInfo = new String[][]{
        };
        game.createInitialBoard(playerInfo, 20, itemsInfo);

        game.moveCurrentPlayer(1);
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(1);
        game.reactToAbyssOrTool();

        String esperado = game.getProgrammers(1).toString();

        assertEquals("[3 | Martim | 1 | No tools | Kotlin | Em Jogo]", esperado);

    }

    @Test
    public void TestGetImagePngAndTitle() throws InvalidInitialBoardException{
        Main i = new Main();
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin", "Brown"},
                {"2", "Pedro", "Kotlin", "Green"},
                {"3", "Martim", "Kotlin", "Purple"}
        };
        String[][] itemsInfo = new String[][]{
                {"0", "7", "3"}
        };
        game.createInitialBoard(playerInfo, 20, itemsInfo);

        String resultadoObtido = game.getImagePng(3);
        assertEquals("bsod.png",resultadoObtido);
        resultadoObtido = game.getImagePng(21);
        assertEquals(null,resultadoObtido);
        resultadoObtido = game.getImagePng(4);
        assertEquals(null,resultadoObtido);
        resultadoObtido = game.getImagePng(20);
        assertEquals("glory.png",resultadoObtido);
        game.getAuthorsPanel();

        resultadoObtido = game.getTitle(3);
        assertEquals("Blue Screen of Death",resultadoObtido);
        resultadoObtido = game.getTitle(21);
        assertEquals(null,resultadoObtido);
        resultadoObtido = game.getTitle(4);
        assertEquals(null,resultadoObtido);
    }

    @Test
    public void TestIdInvalido() throws InvalidInitialBoardException{
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin", "Brown"},
                {"2", "Pedro", "Kotlin", "Green"}
        };
        String[][] itemsInfo = new String[][]{
        };
        game.createInitialBoard(playerInfo, 20, itemsInfo);


        Abyss abismo = new Abyss(0, 10, 3);
        GameManager.casas.put(3,abismo);
        game.moveCurrentPlayer(2);

        String resultadoObtido = game.reactToAbyssOrTool();
        assertNull(resultadoObtido);
    }

    @Test
    public void testeGameAbyssAndTools_001() throws InvalidInitialBoardException{
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin", "Brown"},
                {"2", "Gabriel", "Kotlin", "Blue"},
                {"3", "Pedro", "Kotlin", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "2"},

                {"0", "0", "3"},
                {"0", "1", "4"},
                {"0", "2", "5"},
                {"0", "3", "6"},

        };

        game.createInitialBoard(playerInfo, 20, itemsInfo);

        game.moveCurrentPlayer(1); // 1 posicao 2
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(1); // 2 posicao 2
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(1); // 3 posicao 2
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(1); // 4 posicao 2
        game.reactToAbyssOrTool();

        // ------------------------------------------------

        game.moveCurrentPlayer(1); // 1 posicao 3
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 2 posicao 4
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(3); // 3 posicao 5
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(4); // 4 posicao 6
        game.reactToAbyssOrTool();

        List<String> resultadoObetido = game.getGameResults();
        for (String s:resultadoObetido) {
            System.out.println(s);
        }
    }

    @Test
    public void testeGameAbyssAndTools_002() throws InvalidInitialBoardException{
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin", "Brown"},
                {"2", "Gabriel", "Kotlin", "Blue"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "3", "2"},
                {"0", "2", "3"},
                {"0", "3", "4"}
        };

        game.createInitialBoard(playerInfo, 20, itemsInfo);

        game.moveCurrentPlayer(1); // 1 posicao 2
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(1); // 2 posicao 2
        game.reactToAbyssOrTool();


        // ------------------------------------------------

        game.moveCurrentPlayer(1); // 1 posicao 3
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 2 posicao 4
        game.reactToAbyssOrTool();

        List<String> resultadoObetido = game.getGameResults();
        for (String s:resultadoObetido) {
            System.out.println(s);
        }
    }

    @Test
    public void testeGameAbyssAndTools_003() throws InvalidInitialBoardException{
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin", "Brown"},
                {"2", "Gabriel", "Kotlin", "Blue"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "2", "2"},
                {"1", "4", "3"},

                {"0", "1", "4"},
                {"0", "0", "5"},

                {"1", "0", "7"},
                {"1", "1", "8"},

                {"0", "5", "9"},
                {"0", "6", "10"}

        };

        game.createInitialBoard(playerInfo, 20, itemsInfo);

        game.moveCurrentPlayer(1); // 1 posicao 2
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 2 posicao 3
        game.reactToAbyssOrTool();


        // ------------------------------------------------

        game.moveCurrentPlayer(2); // 1 posicao 4
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 2 posicao 5
        game.reactToAbyssOrTool();
        // ------------------------------------------------

        game.moveCurrentPlayer(2); // 1 posicao 6
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(1); // 2 posicao 6
        game.reactToAbyssOrTool();
        // ------------------------------------------------

        game.moveCurrentPlayer(1); // 1 posicao 7
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 2 posicao 8
        game.reactToAbyssOrTool();
        // ------------------------------------------------

        game.moveCurrentPlayer(2); // 1 posicao 9
        game.reactToAbyssOrTool();

        game.moveCurrentPlayer(2); // 2 posicao 10
        game.reactToAbyssOrTool();

        List<String> resultadoObetido = game.getGameResults();
        for (String s:resultadoObetido) {
            System.out.println(s);
        }
    }

    @Test
    public void TestGetLanguage_003() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Pedro", "Kotlin", "Brown"},
                {"2", "Martim", "Kotlin", "Blue"},
                {"3", "Rafael", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
        };

        game.createInitialBoard(playerInfo, 20, itemsInfo);


        boolean resultado = players.get(0).temLinguagem("Kotlin");
        assertTrue(resultado);
    }

    @Test
    public void TestSaveGame_001() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Pedro", "Kotlin", "Brown"},
                {"2", "Martim", "Kotlin", "Blue"},
                {"3", "Rafael", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "2", "2"},
                {"1", "4", "3"},
        };

        game.createInitialBoard(playerInfo, 20, itemsInfo);

        File file = new File("text.txt");
        game.saveGame(file);
    }

    @Test
    public void TestSaveGame_002() throws InvalidInitialBoardException {
        GameManager game = new GameManager();

        assertTrue(game.saveGame(new File("text.txt")));
    }

    @Test
    public void TestLoadGame_001() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        File file = new File("text.txt");
        game.loadGame(file);

    }

    @Test
    public void TestGetLanguage_001() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "2"},

                {"0", "0", "3"},
                {"0", "1", "4"},
                {"0", "2", "5"},
                {"0", "3", "6"},

        };

        game.createInitialBoard(playerInfo, 20, itemsInfo);

        System.out.println(players.get(0).sortAndTurnToString());
        System.out.println(players.get(1).sortAndTurnToString());
        System.out.println(players.get(2).sortAndTurnToString());
        System.out.println(players.get(3).sortAndTurnToString());

        for (Programmer player: players) {
            boolean esperado = player.temLinguagem("C");
        }
    }

    @Test
    public void TestCreateInitialBoard_001() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "2"},
                {"0", "0", "3"},
                {"0", "1", "4"},
                {"0", "2", "5"},
                {"0", "3", "6"},
        };
        game.createInitialBoard(playerInfo, 10, null);
    }

    @Test
    public void TestRouter_001() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        List<String> information = new ArrayList<>();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"}
        };

        information.add("PLAYER");
        information.add("Ada");

        game.createInitialBoard(playerInfo, 10, null);
        Function1 routerFn = FunctionsKt.router();
        Function2 commandFn = (Function2)routerFn.invoke(CommandType.GET);
        String result = (String)commandFn.invoke(game,information);
        assertEquals("Inexistent player",result);
    }

    @Test
    public void TestRouter_002() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        List<String> information = new ArrayList<>();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "2"},
                {"0", "0", "3"}
        };
        information.add("PLAYER");
        information.add("Martim");

        game.createInitialBoard(playerInfo, 10, itemsInfo);
        Function1 routerFn = FunctionsKt.router();
        Function2 commandFn = (Function2)routerFn.invoke(CommandType.GET);
        String result = (String)commandFn.invoke(game,information);
        assertEquals("4 | Martim | 1 | No tools | Kotlin | Em Jogo",result);

    }

    @Test
    public void TestRouter_003() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        List<String> information = new ArrayList<>();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "2"},
                {"0", "0", "3"}

        };
        information.add("PLAYERS_BY_LANGUAGE");
        information.add("Kotlin");

        game.createInitialBoard(playerInfo, 10, itemsInfo);
        Function1 routerFn = FunctionsKt.router();
        Function2 commandFn = (Function2)routerFn.invoke(CommandType.GET);
        String result = (String)commandFn.invoke(game,information);
        assertEquals("Rafael,Gabriel,Martim",result);

    }

    @Test
    public void TestRouter_004() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        List<String> information = new ArrayList<>();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "2"},
                {"0", "0", "3"}
        };
        information.add("POLYGLOTS");

        game.createInitialBoard(playerInfo, 10, itemsInfo);
        Function1 routerFn = FunctionsKt.router();
        Function2 commandFn = (Function2)routerFn.invoke(CommandType.GET);
        String result = (String)commandFn.invoke(game,information);
        assertEquals("Gabriel:3\nPedro:3\nRafael:4",result);

    }

    @Test
    public void TestRouter_005() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        List<String> information = new ArrayList<>();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "2"},
                {"0", "0", "3"}

        };
        information.add("MOST_USED_POSITIONS");
        information.add("2");

        game.createInitialBoard(playerInfo, 10, itemsInfo);
        contagemDePisadas.get(1).somarPidadela();
        contagemDePisadas.get(2).somarPidadela();
        contagemDePisadas.get(2).somarPidadela();
        contagemDePisadas.get(1).somarPidadela();
        contagemDePisadas.get(3).somarPidadela();
        contagemDePisadas.get(1).somarPidadela();
        Function1 routerFn = FunctionsKt.router();
        Function2 commandFn = (Function2)routerFn.invoke(CommandType.GET);
        String result = (String)commandFn.invoke(game,information);
        assertEquals("1:3\n2:2",result);

    }

    @Test
    public void TestRouter_006() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        List<String> information = new ArrayList<>();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"0", "2", "4"},
                {"0", "0", "6"},
                {"0", "1", "11"},
                {"0", "5", "14"},
                {"0", "2", "17"}
        };

        information.add("MOST_USED_ABYSSES");
        information.add("2");

        game.createInitialBoard(playerInfo, 20, itemsInfo);
        for (Home casa: casas.values()) {
            casa.icrementaPisadelas();
        }
        Function1 routerFn = FunctionsKt.router();
        Function2 commandFn = (Function2)routerFn.invoke(CommandType.GET);
        String result = (String)commandFn.invoke(game,information);
        assertEquals("Exception:1\nErro de sintaxe:1",result);

    }

    @Test
    public void TestPostMove_000() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        List<String> information = new ArrayList<>();

        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "2"},
                {"0", "0", "3"}
        };

        information.add("MOVE");
        information.add("3");

        game.createInitialBoard(playerInfo, 10, itemsInfo);
        Function1 routerFn = FunctionsKt.router();
        Function2 commandFn = (Function2)routerFn.invoke(CommandType.POST);
        String result = (String)commandFn.invoke(game,information);

        assertEquals("OK",result);

    }

    @Test
    public void TestPostMove_001() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        List<String> information = new ArrayList<>();

        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "2"},
                {"0", "0", "3"}
        };

        information.add("MOVE");
        information.add("2");

        game.createInitialBoard(playerInfo, 10, itemsInfo);
        Function1 routerFn = FunctionsKt.router();
        Function2 commandFn = (Function2)routerFn.invoke(CommandType.POST);
        String result = (String)commandFn.invoke(game,information);

        assertEquals("Volta para trás",result);

    }

    @Test
    public void TestPostMove_002() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        List<String> information = new ArrayList<>();

        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "2"},
                {"0", "0", "3"}
        };

        information.add("ABYSS");
        information.add("8");
        information.add("7");

        game.createInitialBoard(playerInfo, 10, itemsInfo);
        Function1 routerFn = FunctionsKt.router();
        Function2 commandFn = (Function2)routerFn.invoke(CommandType.POST);
        String result = (String)commandFn.invoke(game,information);

        assertEquals("OK",result);

    }

    @Test
    public void TestPostMove_003() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        List<String> information = new ArrayList<>();

        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "2"},
                {"0", "0", "3"}

        };

        information.add("ABYSS");
        information.add("8");
        information.add("3");

        game.createInitialBoard(playerInfo, 10, itemsInfo);
        Function1 routerFn = FunctionsKt.router();
        Function2 commandFn = (Function2)routerFn.invoke(CommandType.POST);
        String result = (String)commandFn.invoke(game,information);

        assertEquals("Position is occupied",result);

    }

    @Test
    public void TestPost_GET() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        List<String> information = new ArrayList<>();

        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "2"},
                {"0", "0", "3"}

        };

        information.add("OLA");
        information.add("8");
        information.add("3");

        game.createInitialBoard(playerInfo, 10, itemsInfo);
        Function1 routerFn = FunctionsKt.router();
        Function2 commandFn = (Function2) routerFn.invoke(CommandType.POST);
        String result = (String) commandFn.invoke(game, information);

        assertNull(null, result);

        commandFn = (Function2) routerFn.invoke(CommandType.GET);
        result = (String) commandFn.invoke(game, information);
        assertEquals(null, result);


    }

    @Test
    public void TestmoveCurrentPlayer_000() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();
        List<String> information = new ArrayList<>();

        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "2"},
                {"0", "0", "3"}

        };

        game.createInitialBoard(playerInfo, 10, itemsInfo);

        game.moveCurrentPlayer(6);
        game.reactToAbyssOrTool();
        game.moveCurrentPlayer(6);
        game.reactToAbyssOrTool();
        game.moveCurrentPlayer(6);
        game.reactToAbyssOrTool();
        game.moveCurrentPlayer(6);
        game.reactToAbyssOrTool();

        List<String> resultadoObetido = game.getGameResults();
        for (String s:resultadoObetido) {
            System.out.println(s);
        }
    }

    @Test
    public void TestPostInvalidInitialBoardException_000() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();

        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "2"},
                {"0", "0", "13"}
        };

        try {
            game.createInitialBoard(playerInfo, 10, itemsInfo);
        } catch (InvalidInitialBoardException e) {
            assertTrue(e.isInvalidAbyss());
        }
    }

    @Test
    public void TestPostInvalidInitialBoardException_001() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();

        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "12"},
                {"0", "0", "3"}
        };


        try {
            game.createInitialBoard(playerInfo, 10, itemsInfo);
        } catch (InvalidInitialBoardException e) {
            assertTrue( e.isInvalidTool());
        }
    }

    @Test
    public void testPostInvalidInitialBoardException_002() throws InvalidInitialBoardException{
        GameManager game = new GameManager();
        GameManager.clear();

        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "2"},
                {"0", "10", "3"}
        };
        try {
            game.createInitialBoard(playerInfo, 10, itemsInfo);
        } catch (InvalidInitialBoardException ex) {
            assertEquals("10", ex.getTypeId());
        }
    }

    @Test
    public void testPostInvalidInitialBoardException_003() throws InvalidInitialBoardException{
        GameManager game = new GameManager();
        GameManager.clear();

        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "9"},
                {"0", "1", "8"},
                {"1", "5", "2"},
                {"2", "1", "3"}
        };
        try {
            game.createInitialBoard(playerInfo, 10, itemsInfo);
        } catch (InvalidInitialBoardException ex) {
            assertEquals("Tipo errado", ex.message);
        }
    }

    @Test
    public void testPostInvalidInitialBoardException_004() throws InvalidInitialBoardException{
        GameManager game = new GameManager();
        GameManager.clear();
        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Azul"}
        };
        String[][] itemsInfo = new String[][]{
                {"1", "5", "9"}
        };
        try {
            game.createInitialBoard(playerInfo, 10, itemsInfo);
        } catch (InvalidInitialBoardException ex) {
            assertEquals("Cor errada", ex.message);
        }


        GameManager.clear();
        playerInfo = new String[][]{
                {"-1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"}
        };
        itemsInfo = new String[][]{
                {"1", "5", "9"}
        };
        try {
            game.createInitialBoard(playerInfo, 10, itemsInfo);
        } catch (InvalidInitialBoardException ex) {
            assertEquals("Id errado ou quantidade de jogadores invalida", ex.message);
        }

        GameManager.clear();
        playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"}
        };
        itemsInfo = new String[][]{
                {"1", "5", "9"}
        };
        try {
            game.createInitialBoard(playerInfo, 3, itemsInfo);
        } catch (InvalidInitialBoardException ex) {
            assertEquals("Erro ID Color ou worldSize inferior ao doubro de jogadores", ex.message);
        }

        GameManager.clear();
        playerInfo = new String[][]{
                {"2", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"}
        };
        itemsInfo = new String[][]{
                {"1", "5", "9"}
        };
        try {
            game.createInitialBoard(playerInfo, 3, itemsInfo);
        } catch (InvalidInitialBoardException ex) {
            assertEquals("Erro ID Color ou worldSize inferior ao doubro de jogadores", ex.message);
        }
    }
    
    @Test
    public void testGetProgrammer_000() throws InvalidInitialBoardException {
        GameManager game = new GameManager();
        GameManager.clear();

        String[][] playerInfo = new String[][]{
                {"1", "Rafael", "Kotlin; Java; C; Ruby", "Brown"},
                {"2", "Gabriel", "Kotlin; Lisp; C", "Blue"},
                {"3", "Pedro", "Java; C; Lisp", "Green"},
                {"4", "Martim", "Kotlin", "Purple"}
        };

        String[][] itemsInfo = new String[][]{
                {"1", "5", "9"},
                {"0", "1", "8"},
                {"1", "5", "2"},
                {"1", "1", "3"}
        };

        game.createInitialBoard(playerInfo,20,itemsInfo);

        assertEquals(players,game.getProgrammers(true));
        assertNull(game.getProgrammers(21));
        assertNull(game.getProgrammers(2));
    }

    @Test
    public void testesDefesa() throws InvalidInitialBoardException {

        int tamanhoTabuleiro = 15;
        String[][] jogadores = new String[][] {{ "1", "AAA", "Java", "Brown" }, {"2", "BBB", "Java", "Blue"}};
        String[][] abismosEFerramentas = new String[][] {
                { "0", "150", "6"},  // escafandro na posição 6
                { "1", "4", "4"},  // IDE na posição 4
                { "1", "0", "5"},  // Herança na posição 5
        };
        GameManager manager = new GameManager();
        manager.createInitialBoard(jogadores, tamanhoTabuleiro, abismosEFerramentas);

        System.out.println("1:" + manager.countToolsBetweenPositions(1,4));
        System.out.println("2:" + manager.countToolsBetweenPositions(3,5));
        System.out.println("3:" + manager.countToolsBetweenPositions(4,6));

        System.out.println("4:" + manager.moveCurrentPlayer(5));  // jogador 1 avança 5 e cai no Escafandro
        System.out.println("5:" + manager.reactToAbyssOrTool());
        System.out.println("6:" + manager.getProgrammers(1));

        System.out.println("7:" + manager.moveCurrentPlayer(3));  // jogador 2 avança 3 e apanha IDE
        System.out.println("8:" + manager.reactToAbyssOrTool());
        System.out.println("9:" + manager.getProgrammers(4).get(0));

        System.out.println("10:" + manager.moveCurrentPlayer(4));  // jogador 1 tenta avançar para a posição 5
        manager.reactToAbyssOrTool();

        // ainda só o jogador 2 é que tem ferramenta
        System.out.println("11:" + manager.gameIsOver());

        manager.moveCurrentPlayer(3);  // jogador 2 avança para a posição 7
        manager.reactToAbyssOrTool();

        manager.moveCurrentPlayer(3);  // jogador 1 avança para a posição 4 e apanha IDE
        System.out.println("12:" + manager.reactToAbyssOrTool());
        System.out.println("13:" + manager.gameIsOver());
    }


}
