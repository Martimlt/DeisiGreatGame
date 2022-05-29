package pt.ulusofona.lp2.deisiGreatGame;

public enum CommandType {
    GET("get"),
    POST("post");

    String value;

    CommandType(String value) {
        this.value = value;
    }

}
