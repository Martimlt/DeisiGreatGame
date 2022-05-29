package pt.ulusofona.lp2.deisiGreatGame

fun receiveCommandType (tipo: CommandType): (GameManager, List<String>) -> String? {
    return when(tipo){
        CommandType.GET -> ::getCommand
        CommandType.POST -> ::postCommand
    }
}

fun getCommand(manager: GameManager, args: List<String>): String? {
    return when(args[0]){
        "PLAYER" -> manager.getPlayers()
            .filter { it.getFirstName() == args[1] }
            .toString()
            .replace("[]","Inexistent player")
            .replace("[","")
            .replace("]","")


        "PLAYERS_BY_LANGUAGE" -> manager.getPlayers()
            .filter { it.temLinguagem(args[1]) }
            .map { it.getFirstName() }
            .joinToString (",")
            .replace("[","")
            .replace("]","")

        "POLYGLOTS" -> manager.getPlayers()
            .filter { it.numeroLinguagens() > 1 }
            .sortedBy { it.numeroLinguagens() }
            .map{ it.getName() + ":" + it.numeroLinguagens() + "\n"}.toString()
            .replace("[","")
            .replace("\n]","")
            .replace(", ","")

        "MOST_USED_POSITIONS" -> manager.getContagemDePisadas()
            .sortedByDescending { it.getNrDepisadelas() }
            .take(Integer.parseInt(args[1]))
            .map {it.message() + "\n"}.toString()
            .replace("[","")
            .replace("\n]","")
            .replace(", ", "")

        "MOST_USED_ABYSSES" -> manager.getCasas()
            .sortedByDescending { it.getPisadelas() }
            .distinctBy { it.getTitle() }
            .take(Integer.parseInt(args[1]))
            .map { it.getTitle() + ":" + it.getPisadelas() + "\n"}
            .toString()
            .replace("[","")
            .replace("\n]","")
            .replace(", ", "")

        else -> null
    }

}

fun postCommand (manager: GameManager, args: List<String>): String?{
    return when(args[0]){
        "MOVE" -> manager.moveCurrentPlayerPost(args[1])
        "ABYSS" -> manager.tryToPutAbyss(args[1], args[2])
        else -> null
    }
}

fun router(): (CommandType) ->(GameManager, List<String>) -> String? {
    return ::receiveCommandType
}