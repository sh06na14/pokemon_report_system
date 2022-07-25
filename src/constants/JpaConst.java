package constants;

public interface JpaConst {
  //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "pokemon_report_system";

    //データ取得件数の最大値
    int ROW_PER_PAGE = 15; //1ページに表示するレコードの数

    //従業員テーブル
    String TABLE_PLayer = "players"; //テーブル名
    //従業員テーブルカラム
    String PLayer_COL_ID = "id"; //id
    String PLayer_COL_CODE = "code"; //プレイヤー番号
    String PLayer_COL_NAME = "name"; //氏名
    String PLayer_COL_PASS = "password"; //パスワード
    String PLayer_COL_ADMIN_FLAG = "admin_flag"; //管理者権限
    String PLayer_COL_CREATED_AT = "created_at"; //登録日時
    String PLayer_COL_UPDATED_AT = "updated_at"; //更新日時
    String PLayer_COL_DELETE_FLAG = "delete_flag"; //削除フラグ

    int ROLE_ADMIN = 1; //管理者権限ON(管理者)
    int ROLE_GENERAL = 0; //管理者権限OFF(一般)
    int PLayer_DEL_TRUE = 1; //削除フラグON(削除済み)
    int PLayer_DEL_FALSE = 0; //削除フラグOFF(現役)
    int Pokemon_DEL_TRUE = 1; //削除フラグON(削除済み)
    int Pokemon_DEL_FALSE = 0; //削除フラグOFF(現役)
    int Report_DEL_TRUE = 1; //削除フラグON(削除済み)
    int Report_DEL_FALSE = 0; //削除フラグOFF(現役)

    //育成論テーブル
    String TABLE_REP = "reports"; //テーブル名
    //育成論テーブルカラム
    String REP_COL_ID = "id"; //id
    String REP_COL_PLayer = "player_id"; //育成論を作成したプレイヤーのid
    String REP_COL_POKEMON = "pokemon_id"; //育成論のポケモンのid
    String REP_COL_REP_DATE = "report_date"; //いつの育成論かを示す日付
    String REP_COL_TITLE = "title"; //育成論のタイトル
    String REP_COL_CONTENT = "comment"; //育成論のコメント
    String REP_COL_CREATED_AT = "created_at"; //登録日時
    String REP_COL_UPDATED_AT = "updated_at"; //更新日時

    //Entity名
    String ENTITY_EMP = "employee"; //従業員
    String ENTITY_REP = "report"; //日報

    //JPQL内パラメータ
    String JPQL_PARM_CODE = "code"; //社員番号
    String JPQL_PARM_PASSWORD = "password"; //パスワード
    String JPQL_PARM_PLAYER = "player"; //従業員


}
