package constants;

public enum MessageConst {
  //認証
    I_LOGINED("ログインしました"),
    E_LOGINED("ログインに失敗しました。"),
    I_LOGOUT("ログアウトしました。"),

    //DB更新
    I_REGISTERED("登録が完了しました。"),
    I_UPDATED("更新が完了しました。"),
    I_DELETED("削除が完了しました。"),

    //バリデーション
    E_NONAME("名前を入力してください。"),
    E_NOPASSWORD("パスワードを入力してください。"),
    E_NOPLAYER_CODE("プレイヤー番号を入力してください。"),
    E_PLAYER_CODE_EXIST("入力されたプレイヤー番号の情報は既に存在しています。"),
    E_NOTITLE("タイトルを入力してください。"),
    E_NOCONTENT("内容を入力してください。"),
    E_NOPOKEN_CODE("図鑑番号を入力してください。"),
    E_NOPOKEN_TYPE("タイプを入力してください。"),
    E_POKEN_CODE_EXIST("入力された図鑑番号の情報は既に存在しています。"),
    E_NOPOKEN_ABILITY("特性を入力してください。"),
    E_NOPOKEN_NATURE("性格を入力してください。"),
    E_NOPOKEN_MOVE1("わざ１を入力してください。"),
    E_NOPOKEN_MOVE2("わざ２を入力してください。"),
    E_NOPOKEN_MOVE3("わざ３を入力してください。"),
    E_NOPOKEN_MOVE4("わざ４を入力してください。"),
    E_NOPOKEN_ITEM("持ち物を入力してください。"),
    E_MISS_EffortValue_SUM("基礎ポイントの合計が508でありません。"),
    E_MISS_EffortValue_MAX("基礎ポイントの最大値が252を超えています。");


    /**
     * 文字列
     */
    private final String text;

    /**
     * コンストラクタ
     */
    private MessageConst(final String text) {
        this.text = text;
    }

    /**
     * 値(文字列)取得
     */
    public String getMessage() {
        return this.text;
    }

}
