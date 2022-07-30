package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

import javax.servlet.ServletException;

import actions.views.PlayerView;
import actions.views.PokemonView;
import actions.views.ReportView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import services.ReportService;

public class ReportAction extends ActionBase {
    private ReportService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new ReportService();

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

        //指定されたページ数の一覧画面に表示する日報データを取得
        int page = getPage();
        List<ReportView> reports = service.getAllPerPage(page);

        //全日報データの件数を取得
        long reportsCount = service.countAll();

        putRequestScope(AttributeConst.REPORTS, reports); //取得した日報データ
        putRequestScope(AttributeConst.REP_COUNT, reportsCount); //全ての日報データの件数
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_REP_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //育成論情報の空インスタンスに、日報の日付＝今日の日付を設定する
        ReportView rv = new ReportView();
        rv.setReportDate(LocalDate.now());
        putRequestScope(AttributeConst.REPORT, rv); //日付のみ設定済みの日報インスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_REP_NEW);

    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //育成論の日付が入力されていなければ、今日の日付を設定
            LocalDate day = null;
            if (getRequestParam(AttributeConst.REP_DATE) == null
                    || getRequestParam(AttributeConst.REP_DATE).equals("")) {
                day = LocalDate.now();
            } else {
                day = LocalDate.parse(getRequestParam(AttributeConst.REP_DATE));
            }

            //セッションからログイン中のプレイヤー情報を取得
            PlayerView playerView = (PlayerView) getSessionScope(AttributeConst.LOGIN_PLAYER);
          //セッションから検索したポケモン情報を取得
            PokemonView pokemonView = (PokemonView) getSessionScope(AttributeConst.SEARCH_POKEMON);

            //パラメータの値をもとに育成論情報のインスタンスを作成する
            ReportView rv = new ReportView(
                    null,
                    playerView, //ログインしているプレイヤーを、作成者として登録する
                    pokemonView, //検索したポケモンを登録する
                    day,
                    getRequestParam(AttributeConst.REP_TITLE),
                    getRequestParam(AttributeConst.REP_ABILITY),
                    getRequestParam(AttributeConst.REP_NATURE),
                    toNumber(getRequestParam(AttributeConst.REP_HP)),
                    toNumber(getRequestParam(AttributeConst.REP_ATTACK)),
                    toNumber(getRequestParam(AttributeConst.REP_DEFENSE)),
                    toNumber(getRequestParam(AttributeConst.REP_SPECIAL_ATTACK)),
                    toNumber(getRequestParam(AttributeConst.REP_SPECIAL_DEFENSE)),
                    toNumber(getRequestParam(AttributeConst.REP_SPEED)),
                    getRequestParam(AttributeConst.REP_MOVE_A),
                    getRequestParam(AttributeConst.REP_MOVE_B),
                    getRequestParam(AttributeConst.REP_MOVE_C),
                    getRequestParam(AttributeConst.REP_MOVE_D),
                    getRequestParam(AttributeConst.REP_HELDITEM),
                    getRequestParam(AttributeConst.REP_COMMENT),
                    null,
                    null,
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //育成論情報登録
            List<String> errors = service.create(rv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.REPORT, rv);//入力された日報情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_REP_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
            }
        }
    }



    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {

        //idを条件に育成論データを取得する
        ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

        if (rv == null) {
            //該当の育成論データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.REPORT, rv); //取得した育成論データ
            int hitPoints = calculateHP(rv.getPokemon().getHitPoints(), rv.getHitPoints());
            request.setAttribute("hitPoints", hitPoints);
            int attack = calculateAttack(rv.getNature(), rv.getPokemon().getAttack(), rv.getAttack());
            request.setAttribute("attack", attack);
            int defense = calculateDefense(rv.getNature(), rv.getPokemon().getDefense(), rv.getDefense());
            request.setAttribute("defense", defense);
            int specialAttack = calculateSpecialAttack(rv.getNature(), rv.getPokemon().getSpecialAttack(), rv.getSpecialAttack());
            request.setAttribute("specialAttack", specialAttack);
            int specialDefense = calculateSpecialDefense(rv.getNature(), rv.getPokemon().getSpecialDefense(), rv.getSpecialDefense());
            request.setAttribute("specialDefense", specialDefense);
            int speed = calculateSpeed(rv.getNature(),rv.getPokemon().getSpeed(), rv.getSpeed());
            request.setAttribute("speed", speed);

            //詳細画面を表示
            forward(ForwardConst.FW_REP_SHOW);
        }
    }

    public static int calculateHP(int baseStatsHP, int effortValueHP) {
        int hitPoints = (baseStatsHP * 2 + 31 + effortValueHP / 4) * 50 / 100 + 50 + 10;
        return hitPoints;
    }

    public static int calculateAttack(String nature, int baseStatsAttack, int effortValueAttack) {
        int attack = (baseStatsAttack * 2 + 31 + effortValueAttack / 4) * 50 /100 + 5;
        if (Objects.equals(nature, "いじっぱり") || Objects.equals(nature, "ゆうかん")|| Objects.equals(nature, "さみしがり")|| Objects.equals(nature, "やんちゃ")) {
            attack = attack * 11 / 10;
        }else if (Objects.equals(nature, "ひかえめ")|| Objects.equals(nature, "おくびょう")|| Objects.equals(nature, "ずぶとい")|| Objects.equals(nature, "おだやか")) {
            attack = attack * 9 /10;
        }
        return attack;
    }

    public static int calculateDefense(String nature, int baseStatsDefense, int effortValueDefense) {
        int defense = (baseStatsDefense * 2 + 31 + effortValueDefense / 4) * 50 /100 + 5;
        if (Objects.equals(nature, "ずぶとい") || Objects.equals(nature, "わんぱく")|| Objects.equals(nature, "のうてんき")|| Objects.equals(nature, "のんき")) {
            defense = defense * 11 / 10;
        }else if (Objects.equals(nature, "さみしがり")|| Objects.equals(nature, "おっとり")|| Objects.equals(nature, "おとなしい")|| Objects.equals(nature, "せっかち")) {
            defense = defense * 9 /10;
        }
        return defense;
    }

    public static int calculateSpecialAttack(String nature, int baseStatsSpecialAttack, int effortValueSpecialAttack) {
        int specialAttack = (baseStatsSpecialAttack * 2 + 31 + effortValueSpecialAttack / 4) * 50 /100 + 5;
        if (Objects.equals(nature, "ひかえめ") || Objects.equals(nature, "おっとり")|| Objects.equals(nature, "うっかりや")|| Objects.equals(nature, "れいせい")) {
            specialAttack = specialAttack * 11 / 10;
        }else if (Objects.equals(nature, "いじっぱり")|| Objects.equals(nature, "わんぱく")|| Objects.equals(nature, "しんちょう")|| Objects.equals(nature, "ようき")) {
            specialAttack = specialAttack * 9 /10;
        }
        return specialAttack;
    }

    public static int calculateSpecialDefense(String nature, int baseStatsSpecialDefense, int effortValueSpecialDefense) {
        int specialDefense = (baseStatsSpecialDefense * 2 + 31 + effortValueSpecialDefense / 4) * 50 /100 + 5;
        if (Objects.equals(nature, "おだやか") || Objects.equals(nature, "おとなしい")|| Objects.equals(nature, "しんちょう")|| Objects.equals(nature, "なまいき")) {
            specialDefense = specialDefense * 11 / 10;
        }else if (Objects.equals(nature, "やんちゃ")|| Objects.equals(nature, "のうてんき")|| Objects.equals(nature, "うっかりや")|| Objects.equals(nature, "むじゃき")) {
            specialDefense = specialDefense * 9 /10;
        }
        return specialDefense;
    }

    public static int calculateSpeed(String nature, int baseStatsSpeed, int effortValueSpeed) {
        int speed = (baseStatsSpeed * 2 + 31 + effortValueSpeed / 4) * 50 /100 + 5;
        if (Objects.equals(nature, "おくびょう") || Objects.equals(nature, "せっかち")|| Objects.equals(nature, "ようき")|| Objects.equals(nature, "むじゃき")) {
            speed = speed * 11 / 10;
        }else if (Objects.equals(nature, "ゆうかん")|| Objects.equals(nature, "のんき")|| Objects.equals(nature, "れいせい")|| Objects.equals(nature, "なまいき")) {
            speed = ((baseStatsSpeed * 2 + effortValueSpeed / 4) * 50 /100 + 5) * 9 /10;
        }
        return speed;
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {

        //idを条件に育成論データを取得する
        ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

        //セッションからログイン中のプレイヤー情報を取得
        PlayerView pv = (PlayerView) getSessionScope(AttributeConst.LOGIN_PLAYER);

        if (rv == null || pv.getId() != rv.getPlayer().getId()) {
            //該当の育成論データが存在しない、または
            //ログインしているプレイヤーが育成論の作成者でない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.REPORT, rv); //取得した育成論データ

            //編集画面を表示
            forward(ForwardConst.FW_REP_EDIT);
        }

    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件に育成論データを取得する
            ReportView rv = service.findOne(toNumber(getRequestParam(AttributeConst.REP_ID)));

            //入力された育成論内容を設定する
            rv.setReportDate(toLocalDate(getRequestParam(AttributeConst.REP_DATE)));
            rv.setTitle(getRequestParam(AttributeConst.REP_TITLE));
            rv.setAbility(getRequestParam(AttributeConst.REP_ABILITY));
            rv.setNature(getRequestParam(AttributeConst.REP_NATURE));
            rv.setHitPoints(toNumber(getRequestParam(AttributeConst.REP_HP)));
            rv.setAttack(toNumber(getRequestParam(AttributeConst.REP_ATTACK)));
            rv.setDefense(toNumber(getRequestParam(AttributeConst.REP_DEFENSE)));
            rv.setSpecialAttack(toNumber(getRequestParam(AttributeConst.REP_SPECIAL_ATTACK)));
            rv.setSpecialDefense(toNumber(getRequestParam(AttributeConst.REP_SPECIAL_DEFENSE)));
            rv.setSpeed(toNumber(getRequestParam(AttributeConst.REP_SPEED)));
            rv.setMove1(getRequestParam(AttributeConst.REP_MOVE_A));
            rv.setMove2(getRequestParam(AttributeConst.REP_MOVE_B));
            rv.setMove3(getRequestParam(AttributeConst.REP_MOVE_C));
            rv.setMove4(getRequestParam(AttributeConst.REP_MOVE_D));
            rv.setHeldItem(getRequestParam(AttributeConst.REP_HELDITEM));
            rv.setComment(getRequestParam(AttributeConst.REP_COMMENT));

            //日報データを更新する
            List<String> errors = service.update(rv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.REPORT, rv); //入力された育成論情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_REP_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);

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
        if (checkToken()) {

            //idを条件に従業員データを論理削除する
            service.destroy(toNumber(getRequestParam(AttributeConst.REP_ID)));

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_REP, ForwardConst.CMD_INDEX);
        }


    }
}
