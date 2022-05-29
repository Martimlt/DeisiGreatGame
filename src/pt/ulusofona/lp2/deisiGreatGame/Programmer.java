package pt.ulusofona.lp2.deisiGreatGame;

import java.util.*;

public class Programmer {
    private int iD;
    private String name;
    private String languages;
    private int bordPosition = 1;
    private ProgrammerColor color;
    private ArrayList<Integer> posicoesAnteriores = new ArrayList<>();
    private HashMap<Integer,Tool> tools = new HashMap<>();
    private boolean inGame = true;
    private boolean bloqueado = false;

    public Programmer() {
    }

    public Programmer(int iD, String name, String languages, ProgrammerColor color) {
        this.iD = iD;
        this.name = name;
        this.languages = languages;
        this.color = color;
        posicoesAnteriores.add(1);
    }

    public  int tamanhoTools(){
        return tools.size();
    }

    public void  perdeu(){
        inGame = false;
    }

    public void addPosicao(int novaPosicao) {
        posicoesAnteriores.add(novaPosicao);
    }

    public boolean getInGame(){
        return inGame;
    }

    public int getId() {
        return this.iD;
    }

    public String getName() {
        return name;
    }

    public String getFirstName() {
        String[] firstName = name.split(" ");
        return firstName[0];
    }

    public ProgrammerColor getColor(){
        return color;
    }

    public int getPosition(){
        return bordPosition;
    }

    public String getListadeferramentas(){
        StringBuilder listaDeFerramentas = new StringBuilder("");
        int count = 0;
        if (tools.size() == 0){
            listaDeFerramentas.append("No tools");
        } else {
            for (Tool tool: tools.values()) {
                count++;
                if (count == 1) {
                    listaDeFerramentas.append(tool.getTitle());
                } else {
                    listaDeFerramentas.append(";").append(tool.getTitle());
                }
            }
        }

        return String.valueOf(listaDeFerramentas);
    }

    public void setPosition(int positionAtual){
        bordPosition =  positionAtual;
    }

    public ArrayList<Integer> getPosicoesAnteriores (){
        return posicoesAnteriores;
    }

    String sortAndTurnToString(){
        String languagesInString = "";
        languages = languages.replace("; ", ";");
        List<String> list = new ArrayList<String>(Arrays.asList(languages.split(";")));
        Collections.sort(list);
        for (int i = 0; i < list.size(); i++) {
            if (list.size() - 1 > i){
                languagesInString += list.get(i) + "; ";
            } else {
                languagesInString += list.get(i);
            }
        }
        return languagesInString;
    }

    public boolean temLinguagem(String language){
        String[] linguagens = languages.split(";");
        for (int i = 0; i < linguagens.length; i++) {
            if (Objects.equals(linguagens[i], language)){
                return true;
            }
        }
        return false;
    }

    public int numeroLinguagens(){
        String[] linguagens = languages.split(";");
        return linguagens.length;
    }

    public boolean temATool(int id) {
        return tools.get(id) != null;
    }

    public void removeTool(int i){
        tools.remove(i);
    }

    public void addTool(Tool tool) {
        tools.put(tool.getID(),tool);
    }

    public void bloquear(){
        bloqueado = true;
    }

    public void desbloquear(){
        bloqueado = false;
    }

    public boolean estaBloqueado(){
        return bloqueado;
    }

    @Override
    public String toString() {
        if (inGame){
            return iD + " | " + name + " | " + bordPosition + " | " + getListadeferramentas() + " | " + sortAndTurnToString() + " | Em Jogo";
        } else {
            return iD + " | " + name + " | " + bordPosition + " | " + getListadeferramentas() + " | " + sortAndTurnToString() + " | Derrotado";
        }
    }

}
