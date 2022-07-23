package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;
import models.Report;

public class ReportConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rv ReportViewのインスタンス
     * @return Reportのインスタンス
     */
    public static Report toModel(ReportView rv) {
        return new Report(
                rv.getId(),
                PlayerConverter.toModel(rv.getPlayer()),
                PokemonConverter.toModel(rv.getPokemon()),
                rv.getReportDate(),
                rv.getTitle(),
                rv.getAbility(),
                rv.getHitPoints(),
                rv.getAttack(),
                rv.getDefense(),
                rv.getSpecialAttack(),
                rv.getSpecialDefense(),
                rv.getSpeed(),
                rv.getMove_A(),
                rv.getMove_B(),
                rv.getMove_C(),
                rv.getMove_D(),
                rv.getHeldItem(),
                rv.getComment(),
                rv.getCreatedAt(),
                rv.getUpdatedAt(),
                rv.getDeleteFlag() == null
                ? null
                : rv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                        ? JpaConst.Report_DEL_TRUE
                        : JpaConst.Report_DEL_FALSE);
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param r Reportのインスタンス
     * @return ReportViewのインスタンス
     */
    public static ReportView toView(Report r) {

        if (r == null) {
            return null;
        }

        return new ReportView(
                r.getId(),
                PlayerConverter.toView(r.getPlayer()),
                PokemonConverter.toView(r.getPokemon()),
                r.getReportDate(),
                r.getTitle(),
                r.getAbility(),
                r.getHitPoints(),
                r.getAttack(),
                r.getDefense(),
                r.getSpecialAttack(),
                r.getSpecialDefense(),
                r.getSpeed(),
                r.getMove_A(),
                r.getMove_B(),
                r.getMove_C(),
                r.getMove_D(),
                r.getHeldItem(),
                r.getComment(),
                r.getCreatedAt(),
                r.getUpdatedAt(),
                r.getDeleteFlag() == null
                ? null
                : r.getDeleteFlag() == JpaConst.Report_DEL_TRUE
                        ? AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                        : AttributeConst.DEL_FLAG_FALSE.getIntegerValue());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<ReportView> toViewList(List<Report> list) {
        List<ReportView> evs = new ArrayList<>();

        for (Report r : list) {
            evs.add(toView(r));
        }

        return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param r DTOモデル(コピー先)
     * @param rv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Report r, ReportView rv) {
        r.setId(rv.getId());
        r.setPlayer(PlayerConverter.toModel(rv.getPlayer()));
        r.setPokemon(PokemonConverter.toModel(rv.getPokemon()));
        r.setReportDate(rv.getReportDate());
        r.setTitle(rv.getTitle());
        r.setAbility(rv.getAbility());
        r.setHitPoints(rv.getHitPoints());
        r.setAttack(rv.getAttack());
        r.setDefense(rv.getDefense());
        r.setSpecialAttack(rv.getSpecialAttack());
        r.setSpecialDefense(rv.getSpecialDefense());
        r.setSpeed(rv.getSpeed());
        r.setMove_A(rv.getMove_A());
        r.setMove_B(rv.getMove_B());
        r.setMove_C(rv.getMove_C());
        r.setMove_D(rv.getMove_D());
        r.setMove_D(rv.getMove_D());
        r.setHeldItem(rv.getHeldItem());
        r.setComment(rv.getComment());
        r.setCreatedAt(rv.getCreatedAt());
        r.setUpdatedAt(rv.getUpdatedAt());
        r.setDeleteFlag(rv.getDeleteFlag());

    }


}
