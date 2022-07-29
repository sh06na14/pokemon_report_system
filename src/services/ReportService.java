package services;

import java.time.LocalDateTime;
import java.util.List;

import actions.views.PlayerConverter;
import actions.views.PlayerView;
import actions.views.ReportConverter;
import actions.views.ReportView;
import constants.JpaConst;
import models.Report;
import models.validators.ReportValidator;

public class ReportService extends ServiceBase {
    /**
     * 指定したプレイヤーが作成した育成論データを、指定されたページ数の一覧画面に表示する分取得しReportViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ReportView> getMinePerPage(PlayerView player, int page) {

        List<Report> reports = em.createNamedQuery("report.getAllMine", Report.class)
                .setParameter(JpaConst.JPQL_PARM_PLAYER, PlayerConverter.toModel(player))
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ReportConverter.toViewList(reports);
    }

    /**
     * 指定したプレイヤーが作成した育成論データの件数を取得し、返却する
     * @return 育成論データの件数
     */
    public long countAllMine(PlayerView player) {

        long count = (long) em.createNamedQuery("report.countAllMine", Long.class)
                .setParameter(JpaConst.JPQL_PARM_PLAYER, PlayerConverter.toModel(player))
                .getSingleResult();

        return count;
    }

    /**
     * 指定されたページ数の一覧画面に表示する育成論データを取得し、ReportViewのリストで返却する
     * @param page ページ数
     * @return 一覧画面に表示するデータのリスト
     */
    public List<ReportView> getAllPerPage(int page) {

        List<Report> reports = em.createNamedQuery("report.getAll", Report.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();
        return ReportConverter.toViewList(reports);
    }

    /**
     * 育成論テーブルのデータの件数を取得し、返却する
     * @return データの件数
     */
    public long countAll() {
        long reports_count = (long) em.createNamedQuery("report.count", Long.class)
                .getSingleResult();
        return reports_count;
    }

    /**
     * idを条件に取得したデータをReportViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public ReportView findOne(int id) {
        return ReportConverter.toView(findOneInternal(id));
    }

    /**
     * 画面から入力された育成論の内容を元にデータを1件作成し、育成論テーブルに登録する
     * @param rv 育成論の登録内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> create(ReportView rv) {
        List<String> errors = ReportValidator.validate(rv);
        if (errors.size() == 0) {
            LocalDateTime ldt = LocalDateTime.now();
            rv.setCreatedAt(ldt);
            rv.setUpdatedAt(ldt);
            createInternal(rv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力された育成論の登録内容を元に、日報データを更新する
     * @param rv 育成論の更新内容
     * @return バリデーションで発生したエラーのリスト
     */
    public List<String> update(ReportView rv) {

        //バリデーションを行う
        List<String> errors = ReportValidator.validate(rv);

        if (errors.size() == 0) {

            //更新日時を現在時刻に設定
            LocalDateTime ldt = LocalDateTime.now();
            rv.setUpdatedAt(ldt);

            updateInternal(rv);
        }

        //バリデーションで発生したエラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にデータを1件取得する
     * @param id
     * @return 取得データのインスタンス
     */
    private Report findOneInternal(int id) {
        return em.find(Report.class, id);
    }

    /**
     * 育成論データを1件登録する
     * @param rv 育成論データ
     */
    private void createInternal(ReportView rv) {

        em.getTransaction().begin();
        em.persist(ReportConverter.toModel(rv));
        em.getTransaction().commit();

    }

    /**
     * 育成論データを更新する
     * @param rv 育成論データ
     */
    private void updateInternal(ReportView rv) {

        em.getTransaction().begin();
        Report r = findOneInternal(rv.getId());
        ReportConverter.copyViewToModel(r, rv);
        em.getTransaction().commit();

    }

    /**
     * idを条件に育成論データを論理削除する
     * @param id
     */
    public void destroy(Integer id) {

        //idを条件に登録済みの育成論情報を取得する
        ReportView savedRep = findOne(id);

        //更新日時に現在時刻を設定する
        LocalDateTime today = LocalDateTime.now();
        savedRep.setUpdatedAt(today);

        //論理削除フラグをたてる
        savedRep.setDeleteFlag(JpaConst.Report_DEL_TRUE);

        //更新処理を行う
        updateInternal(savedRep);

    }


}
