package constants;

public enum AttributeConst {
  //フラッシュメッセージ
    FLUSH("flush"),

    //一覧画面共通
    MAX_ROW("maxRow"),
    PAGE("page"),

    //入力フォーム共通
    TOKEN("_token"),
    ERR("errors"),

    //ログイン中の従業員
    LOGIN_PLAYER("login_player"),
    //検索したポケモン
    SEARCH_POKEMON("search_pokemon"),

    //ログイン画面
    LOGIN_ERR("loginError"),

    //従業員管理
    PLAYER("player"),
    PLAYERS("players"),
    PLAYER_COUNT("players_count"),
    PLAYER_ID("id"),
    PLAYER_CODE("code"),
    PLAYER_PASS("password"),
    PLAYER_NAME("name"),
    PLAYER_ADMIN_FLG("admin_flag"),

  //ポケモン管理
    POKEMON("pokemon"),
    POKEMONS("pokemons"),
    POKEMON_COUNT("pokemons_count"),
    POKEMON_ID("id"),
    POKEMON_CODE("code"),
    POKEMON_NAME("name"),
    POKEMON_TYPE_A("type1"),
    POKEMON_TYPE_B("type2"),
    POKEMON_HP("hitPoints"),
    POKEMON_ATTACK("attack"),
    POKEMON_DEFENSE("defense"),
    POKEMON_SPECIAL_ATTACK("specialAttack"),
    POKEMON_SPECIAL_DEFENSE("specialDefense"),
    POKEMON_SPEED("speed"),

    //管理者フラグ
    ROLE_ADMIN(1),
    ROLE_GENERAL(0),

    //削除フラグ
    DEL_FLAG_TRUE(1),
    DEL_FLAG_FALSE(0),

    //育成論管理
    REPORT("report"),
    REPORTS("reports"),
    REP_COUNT("reports_count"),
    REP_ID("id"),
    REP_DATE("report_date"),
    REP_TITLE("title"),
    REP_ABILITY("ability"),
    REP_NATURE("nature"),
    REP_HP("hitPoints"),
    REP_ATTACK("attack"),
    REP_DEFENSE("defense"),
    REP_SPECIAL_ATTACK("specialAttack"),
    REP_SPECIAL_DEFENSE("specialDefense"),
    REP_SPEED("speed"),
    REP_MOVE_A("move1"),
    REP_MOVE_B("move2"),
    REP_MOVE_C("move3"),
    REP_MOVE_D("move4"),
    REP_HELDITEM("heldItem"),
    REP_COMMENT("comment");

    private final String text;
    private final Integer i;

    private AttributeConst(final String text) {
        this.text = text;
        this.i = null;
    }

    private AttributeConst(final Integer i) {
        this.text = null;
        this.i = i;
    }

    public String getValue() {
        return this.text;
    }

    public Integer getIntegerValue() {
        return this.i;
    }

}
