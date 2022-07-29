package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.PlayerView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.PlayerService;

public class PlayerAction extends ActionBase {
    private PlayerService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new PlayerService();

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
      //管理者かどうかのチェック //追記
        if (checkAdmin()) { //追記
            //指定されたページ数の一覧画面に表示するデータを取得
            int page = getPage();
            List<PlayerView> players = service.getPerPage(page);
            //全てのPlayerデータの件数を取得
            long playerCount = service.countAll();

            putRequestScope(AttributeConst.PLAYERS, players); //取得したPlayerデータ
            putRequestScope(AttributeConst.PLAYER_COUNT, playerCount); //全てのPlayerデータの件数
            putRequestScope(AttributeConst.PAGE, page); //ページ数
            putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }

            //一覧画面を表示
            forward(ForwardConst.FW_PLAYER_INDEX);
        }

    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.PLAYER, new PlayerView()); //空のPlayerインスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_PLAYER_NEW);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //パラメータの値を元にPlayer情報のインスタンスを作成する
            PlayerView pv = new PlayerView(
                    null,
                    getRequestParam(AttributeConst.PLAYER_CODE),
                    getRequestParam(AttributeConst.PLAYER_NAME),
                    getRequestParam(AttributeConst.PLAYER_PASS),
                    toNumber(getRequestParam(AttributeConst.PLAYER_ADMIN_FLG)),
                    null,
                    null,
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //アプリケーションスコープからpepper文字列を取得
            String pepper = getContextScope(PropertyConst.PEPPER);

            //プレイヤー情報登録
            List<String> errors = service.create(pv, pepper);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.PLAYER, pv); //入力された従業員情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_PLAYER_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_AUTH, ForwardConst.CMD_SHOW_LOGIN);
            }

        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {
      //管理者かどうかのチェック //追記
        if (checkAdmin()) { //追記

            //idを条件にPlayerデータを取得する
            PlayerView pv = service.findOne(toNumber(getRequestParam(AttributeConst.PLAYER_ID)));

            if (pv == null || pv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

                //データが取得できなかった、または論理削除されている場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;
            }

            putRequestScope(AttributeConst.PLAYER, pv); //取得した従業員情報

            //詳細画面を表示
            forward(ForwardConst.FW_PLAYER_SHOW);
        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {
        //管理者かどうかのチェック //追記
        if (checkAdmin()) { //追記

            //idを条件にPlayerデータを取得する
            PlayerView pv = service.findOne(toNumber(getRequestParam(AttributeConst.PLAYER_ID)));

            if (pv == null || pv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

                //データが取得できなかった、または論理削除されている場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;
            }

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.PLAYER, pv); //取得した従業員情報

            //編集画面を表示する
            forward(ForwardConst.FW_PLAYER_EDIT);
        }

    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {
      //管理者かどうかのチェック //追記
        if (checkAdmin()) { //追記

            //CSRF対策 tokenのチェック
            if (checkToken()) {
                //パラメータの値を元にPlayer情報のインスタンスを作成する
                PlayerView pv = new PlayerView(
                        toNumber(getRequestParam(AttributeConst.PLAYER_ID)),
                        getRequestParam(AttributeConst.PLAYER_CODE),
                        getRequestParam(AttributeConst.PLAYER_NAME),
                        getRequestParam(AttributeConst.PLAYER_PASS),
                        toNumber(getRequestParam(AttributeConst.PLAYER_ADMIN_FLG)),
                        null,
                        null,
                        AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

                //アプリケーションスコープからpepper文字列を取得
                String pepper = getContextScope(PropertyConst.PEPPER);

                //プレイヤー情報更新
                List<String> errors = service.update(pv, pepper);

                if (errors.size() > 0) {
                    //更新中にエラーが発生した場合

                    putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                    putRequestScope(AttributeConst.PLAYER, pv); //入力された従業員情報
                    putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                    //編集画面を再表示
                    forward(ForwardConst.FW_PLAYER_EDIT);
                } else {
                    //更新中にエラーがなかった場合

                    //セッションに更新完了のフラッシュメッセージを設定
                    putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                    //一覧画面にリダイレクト
                    redirect(ForwardConst.ACT_Player, ForwardConst.CMD_INDEX);
                }
            }
        }
    }

    /**
     * 論理削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkAdmin() && checkToken()) {

            //idを条件にPlayerデータを論理削除する
            service.destroy(toNumber(getRequestParam(AttributeConst.PLAYER_ID)));

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_Player, ForwardConst.CMD_INDEX);
        }
    }

    /**
     * ログイン中のPlayerが管理者かどうかチェックし、管理者でなければエラー画面を表示
     * true: 管理者 false: 管理者ではない
     * @throws ServletException
     * @throws IOException
     */
    private boolean checkAdmin() throws ServletException, IOException {

        //セッションからログイン中のPlayer情報を取得
        PlayerView pv = (PlayerView) getSessionScope(AttributeConst.LOGIN_PLAYER);

        //管理者でなければエラー画面を表示
        if (pv.getAdminFlag() != AttributeConst.ROLE_ADMIN.getIntegerValue()) {

            forward(ForwardConst.FW_ERR_UNKNOWN);
            return false;

        } else {

            return true;
        }

    }

}
