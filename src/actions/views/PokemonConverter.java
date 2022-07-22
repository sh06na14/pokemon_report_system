package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;
import models.Pokemon;

public class PokemonConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param pv PokemonViewのインスタンス
     * @return Pokemonのインスタンス
     */
    public static Pokemon toModel(PokemonView pv) {

        return new Pokemon(
                pv.getId(),
                pv.getCode(),
                pv.getName(),
                pv.getType1(),
                pv.getType2(),
                pv.getHitPoints(),
                pv.getAttack(),
                pv.getDefense(),
                pv.getSpecialAttack(),
                pv.getSpecialDefense(),
                pv.getSpeed(),
                pv.getDeleteFlag() == null
                        ? null
                        : pv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                                ? JpaConst.Pokemon_DEL_TRUE
                                : JpaConst.Pokemon_DEL_FALSE);
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param p Pokemonのインスタンス
     * @return PokemonViewのインスタンス
     */
    public static PokemonView toView(Pokemon p) {

        if(p == null) {
            return null;
        }

        return new PokemonView(
                p.getId(),
                p.getCode(),
                p.getName(),
                p.getType1(),
                p.getType2(),
                p.getHitPoints(),
                p.getAttack(),
                p.getDefense(),
                p.getSpecialAttack(),
                p.getSpecialDefense(),
                p.getSpeed(),
                p.getDeleteFlag() == null
                        ? null
                        : p.getDeleteFlag() == JpaConst.Pokemon_DEL_TRUE
                                ? AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                                : AttributeConst.DEL_FLAG_FALSE.getIntegerValue());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<PokemonView> toViewList(List<Pokemon> list) {
        List<PokemonView> pvs = new ArrayList<>();

        for (Pokemon p : list) {
            pvs.add(toView(p));
        }

        return pvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param e DTOモデル(コピー先)
     * @param ev Viewモデル(コピー元)
     */
    public static void copyViewToModel(Pokemon p, PokemonView pv) {
        p.setId(pv.getId());
        p.setCode(pv.getCode());
        p.setName(pv.getName());
        p.setType1(pv.getType1());
        p.setType2(pv.getType2());
        p.setHitPoints(pv.getHitPoints());
        p.setAttack(pv.getAttack());
        p.setDefense(pv.getDefense());
        p.setSpecialAttack(pv.getSpecialAttack());
        p.setSpecialDefense(pv.getSpecialDefense());
        p.setSpeed(pv.getSpeed());
        p.setDeleteFlag(pv.getDeleteFlag());

    }


}
