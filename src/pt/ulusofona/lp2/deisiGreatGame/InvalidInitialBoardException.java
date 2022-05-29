package pt.ulusofona.lp2.deisiGreatGame;

public class InvalidInitialBoardException extends Exception{

    String message;
    InvalidInitialBoardException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public boolean isInvalidAbyss(){
        String[] id = message.split(";");
        return id[0].equals("Exception foi causada por um abismo");
    }

    public boolean isInvalidTool() {
        String[] id = message.split(";");
        return id[0].equals("Exception foi causada por uma ferramenta");
    }

    public String getTypeId() {
        String[] id = message.split(";");
        return id[1];
    }
}
