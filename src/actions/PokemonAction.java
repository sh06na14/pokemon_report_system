package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.PokemonView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.PokemonService;

public class PokemonAction extends ActionBase {
    private PokemonService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new PokemonService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {

        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<PokemonView> pokemons = service.getPerPage(page);

        //全ての従業員データの件数を取得
        long pokemonCount = service.countAll();

        putRequestScope(AttributeConst.POKEMONS, pokemons); //取得したポケモンデータ
        putRequestScope(AttributeConst.POKEMON_COUNT, pokemonCount); //全てのポケモンデータの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_POKEMON_INDEX);

    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.POKEMON, new PokemonView()); //空の従業員インスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_POKEMON_NEW);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //パラメータの値を元に従業員情報のインスタンスを作成する
            PokemonView pv = new PokemonView(
                    null,
                    getRequestParam(AttributeConst.POKEMON_CODE),
                    getRequestParam(AttributeConst.POKEMON_NAME),
                    getRequestParam(AttributeConst.POKEMON_TYPE_A),
                    getRequestParam(AttributeConst.POKEMON_TYPE_B),
                    toNumber(getRequestParam(AttributeConst.POKEMON_HP)),
                    toNumber(getRequestParam(AttributeConst.POKEMON_ATTACK)),
                    toNumber(getRequestParam(AttributeConst.POKEMON_DEFENSE)),
                    toNumber(getRequestParam(AttributeConst.POKEMON_SPECIAL_ATTACK)),
                    toNumber(getRequestParam(AttributeConst.POKEMON_SPECIAL_DEFENSE)),
                    toNumber(getRequestParam(AttributeConst.POKEMON_SPEED)),
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //従業員情報登録
            List<String> errors = service.create(pv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.POKEMON, pv); //入力された従業員情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_POKEMON_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_Pokemon, ForwardConst.CMD_INDEX);
            }

        }
    }
    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に従業員データを取得する
        PokemonView pv = service.findOne(toNumber(getRequestParam(AttributeConst.POKEMON_ID)));

        if (pv == null || pv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

            //データが取得できなかった、または論理削除されている場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.POKEMON, pv); //取得した従業員情報

        //詳細画面を表示
        forward(ForwardConst.FW_POKEMON_SHOW);
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件に従業員データを取得する
        PokemonView pv = service.findOne(toNumber(getRequestParam(AttributeConst.POKEMON_ID)));

        if (pv == null || pv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

            //データが取得できなかった、または論理削除されている場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.POKEMON, pv); //取得した従業員情報

        //編集画面を表示する
        forward(ForwardConst.FW_POKEMON_EDIT);

    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {
            //パラメータの値を元に従業員情報のインスタンスを作成する
            PokemonView pv = new PokemonView(
                    toNumber(getRequestParam(AttributeConst.POKEMON_ID)),
                    getRequestParam(AttributeConst.POKEMON_CODE),
                    getRequestParam(AttributeConst.POKEMON_NAME),
                    getRequestParam(AttributeConst.POKEMON_TYPE_A),
                    getRequestParam(AttributeConst.POKEMON_TYPE_B),
                    toNumber(getRequestParam(AttributeConst.POKEMON_HP)),
                    toNumber(getRequestParam(AttributeConst.POKEMON_ATTACK)),
                    toNumber(getRequestParam(AttributeConst.POKEMON_DEFENSE)),
                    toNumber(getRequestParam(AttributeConst.POKEMON_SPECIAL_ATTACK)),
                    toNumber(getRequestParam(AttributeConst.POKEMON_SPECIAL_DEFENSE)),
                    toNumber(getRequestParam(AttributeConst.POKEMON_SPEED)),
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());


            //従業員情報更新
            List<String> errors = service.update(pv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.POKEMON, pv); //入力された従業員情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_POKEMON_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_Pokemon, ForwardConst.CMD_INDEX);
            }
        }
    }
}
