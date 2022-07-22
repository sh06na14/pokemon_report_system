package constants;

public enum ForwardConst {
  //action
    ACT("action"),
    ACT_TOP("Top"),
    ACT_Player("Player"),
    ACT_Pokemon("Pokemon"),
    ACT_REP("Report"),
    ACT_AUTH("Auth"),

    //command
    CMD("command"),
    CMD_NONE(""),
    CMD_INDEX("index"),
    CMD_SHOW("show"),
    CMD_SHOW_LOGIN("showLogin"),
    CMD_LOGIN("login"),
    CMD_LOGOUT("logout"),
    CMD_NEW("entryNew"),
    CMD_CREATE("create"),
    CMD_EDIT("edit"),
    CMD_UPDATE("update"),
    CMD_DESTROY("destroy"),

    //jsp
    FW_ERR_UNKNOWN("error/unknown"),
    FW_TOP_INDEX("topPage/index"),
    FW_LOGIN("login/login"),
    FW_PLAYER_INDEX("players/index"),
    FW_PLAYER_SHOW("players/show"),
    FW_PLAYER_NEW("players/new"),
    FW_PLAYER_EDIT("players/edit"),
    FW_POKEMON_INDEX("pokemons/index"),
    FW_POKEMON_SHOW("pokemons/show"),
    FW_POKEMON_NEW("pokemons/new"),
    FW_POKEMON_EDIT("pokemons/edit"),
    FW_REP_INDEX("reports/index"),
    FW_REP_SHOW("reports/show"),
    FW_REP_NEW("reports/new"),
    FW_REP_EDIT("reports/edit");

    /**
     * 文字列
     */
    private final String text;

    /**
     * コンストラクタ
     */
    private ForwardConst(final String text) {
        this.text = text;
    }

    /**
     * 値(文字列)取得
     */
    public String getValue() {
        return this.text;
    }


}
