package services;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.NoResultException;

import actions.views.PlayerConverter;
import actions.views.PlayerView;
import constants.JpaConst;
import models.Player;
import models.validators.PlayerValidator;
import utils.EncryptUtil;

public class PlayerService extends ServiceBase {
    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、PlayerViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<PlayerView> getPerPage(int page) {
        List<Player> players = em.createNamedQuery("player.getAll", Player.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return PlayerConverter.toViewList(players);
    }

    /**
     * Playerテーブルのデータの件数を取得し、返却する
     * @return Playerテーブルのデータの件数
     */
    public long countAll() {
        long playerCount = (long) em.createNamedQuery("player.count", Long.class)
                .getSingleResult();

        return playerCount;
    }

    /**
     * Player番号、パスワードを条件に取得したデータをEmployeeViewのインスタンスで返却する
     * @param code Player番号
     * @param plainPass パスワード文字列
     * @param pepper pepper文字列
     * @return 取得データのインスタンス 取得できない場合null
     */
    public PlayerView findOne(String code, String plainPass, String pepper) {
        Player p = null;
        try {
            //パスワードのハッシュ化
            String pass = EncryptUtil.getPasswordEncrypt(plainPass, pepper);

            //Player番号とハッシュ化済パスワードを条件に未削除の従業員を1件取得する
            p = em.createNamedQuery("player.getByCodeAndPass", Player.class)
                    .setParameter(JpaConst.JPQL_PARM_CODE, code)
                    .setParameter(JpaConst.JPQL_PARM_PASSWORD, pass)
                    .getSingleResult();

        } catch (NoResultException ex) {
        }

        return PlayerConverter.toView(p);

    }

    /**
     * idを条件に取得したデータをPlayerViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public PlayerView findOne(int id) {
        Player p = findOneInternal(id);
        return PlayerConverter.toView(p);
    }

    /**
     * Player番号を条件に該当するデータの件数を取得し、返却する
     * @param code Player番号
     * @return 該当するデータの件数
     */
    public long countByCode(String code) {

        //指定した社員番号を保持する従業員の件数を取得する
        long players_count = (long) em.createNamedQuery("player.countRegisteredByCode", Long.class)
                .setParameter(JpaConst.JPQL_PARM_CODE, code)
                .getSingleResult();
        return players_count;
    }

    /**
     * 画面から入力されたPlayerの登録内容を元にデータを1件作成し、Playerテーブルに登録する
     * @param pv 画面から入力されたプレイヤーの登録内容
     * @param pepper pepper文字列
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(PlayerView pv, String pepper) {

        //パスワードをハッシュ化して設定
        String pass = EncryptUtil.getPasswordEncrypt(pv.getPassword(), pepper);
        pv.setPassword(pass);

        //登録日時、更新日時は現在時刻を設定する
        LocalDateTime now = LocalDateTime.now();
        pv.setCreatedAt(now);
        pv.setUpdatedAt(now);

        //登録内容のバリデーションを行う
        List<String> errors = PlayerValidator.validate(this, pv, true, true);

        //バリデーションエラーがなければデータを登録する
        if (errors.size() == 0) {
            create(pv);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力されたPlayerの更新内容を元にデータを1件作成し、Playerテーブルを更新する
     * @param pv 画面から入力されたプレイヤーの登録内容
     * @param pepper pepper文字列
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(PlayerView pv, String pepper) {

        //idを条件に登録済みのプレイヤー情報を取得する
        PlayerView savedPlayer = findOne(pv.getId());

        boolean validateCode = false;
        if (!savedPlayer.getCode().equals(pv.getCode())) {
            //プレイヤー番号を更新する場合

            //プレイヤー番号についてのバリデーションを行う
            validateCode = true;
            //変更後のプレイヤー番号を設定する
            savedPlayer.setCode(pv.getCode());
        }

        boolean validatePass = false;
        if (pv.getPassword() != null && !pv.getPassword().equals("")) {
            //パスワードに入力がある場合

            //パスワードについてのバリデーションを行う
            validatePass = true;

            //変更後のパスワードをハッシュ化し設定する
            savedPlayer.setPassword(
                    EncryptUtil.getPasswordEncrypt(pv.getPassword(), pepper));
        }

        savedPlayer.setName(pv.getName()); //変更後の氏名を設定する
        savedPlayer.setAdminFlag(pv.getAdminFlag()); //変更後の管理者フラグを設定する

        //更新日時に現在時刻を設定する
        LocalDateTime today = LocalDateTime.now();
        savedPlayer.setUpdatedAt(today);

        //更新内容についてバリデーションを行う
        List<String> errors = PlayerValidator.validate(this, savedPlayer, validateCode, validatePass);

        //バリデーションエラーがなければデータを更新する
        if (errors.size() == 0) {
            update(savedPlayer);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にプレイヤーデータを論理削除する
     * @param id
     */
    public void destroy(Integer id) {

        //idを条件に登録済みのプレイヤー情報を取得する
        PlayerView savedPlayer = findOne(id);

        //更新日時に現在時刻を設定する
        LocalDateTime today = LocalDateTime.now();
        savedPlayer.setUpdatedAt(today);

        //論理削除フラグをたてる
        savedPlayer.setDeleteFlag(JpaConst.PLayer_DEL_TRUE);

        //更新処理を行う
        update(savedPlayer);

    }

    /**
     * Player番号とパスワードを条件に検索し、データが取得できるかどうかで認証結果を返却する
     * @param code Player番号
     * @param plainPass パスワード
     * @param pepper pepper文字列
     * @return 認証結果を返却す(成功:true 失敗:false)
     */
    public Boolean validateLogin(String code, String plainPass, String pepper) {

        boolean isValidPlayer = false;
        if (code != null && !code.equals("") && plainPass != null && !plainPass.equals("")) {
            PlayerView pv = findOne(code, plainPass, pepper);

            if (pv != null && pv.getId() != null) {

                //データが取得できた場合、認証成功
                isValidPlayer = true;
            }
        }

        //認証結果を返却する
        return isValidPlayer;
    }

    /**
     * idを条件にデータを1件取得し、Playerのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Player findOneInternal(int id) {
        Player p = em.find(Player.class, id);

        return p;
    }

    /**
     * プレイヤーデータを1件登録する
     * @param pv プレイヤーデータ
     * @return 登録結果(成功:true 失敗:false)
     */
    private void create(PlayerView pv) {

        em.getTransaction().begin();
        em.persist(PlayerConverter.toModel(pv));
        em.getTransaction().commit();

    }

    /**
     * プレイヤーデータを更新する
     * @param pv 画面から入力されたプレイヤーの登録内容
     */
    private void update(PlayerView pv) {

        em.getTransaction().begin();
        Player p = findOneInternal(pv.getId());
        PlayerConverter.copyViewToModel(p, pv);
        em.getTransaction().commit();

    }


}
