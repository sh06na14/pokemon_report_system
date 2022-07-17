package actions.views;

import java.util.ArrayList;
import java.util.List;

import constants.AttributeConst;
import constants.JpaConst;
import models.Player;

public class PlayerConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param pv PlayerViewのインスタンス
     * @return Playerのインスタンス
     */
    public static Player toModel(PlayerView pv) {

        return new Player(
                pv.getId(),
                pv.getCode(),
                pv.getName(),
                pv.getPassword(),
                pv.getAdminFlag() == null
                        ? null
                        : pv.getAdminFlag() == AttributeConst.ROLE_ADMIN.getIntegerValue()
                                ? JpaConst.ROLE_ADMIN
                                : JpaConst.ROLE_GENERAL,
                pv.getCreatedAt(),
                pv.getUpdatedAt(),
                pv.getDeleteFlag() == null
                        ? null
                        : pv.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                                ? JpaConst.EMP_DEL_TRUE
                                : JpaConst.EMP_DEL_FALSE);
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param p Playerのインスタンス
     * @return PlayerViewのインスタンス
     */
    public static PlayerView toView(Player p) {

        if(p == null) {
            return null;
        }

        return new PlayerView(
                p.getId(),
                p.getCode(),
                p.getName(),
                p.getPassword(),
                p.getAdminFlag() == null
                        ? null
                        : p.getAdminFlag() == JpaConst.ROLE_ADMIN
                                ? AttributeConst.ROLE_ADMIN.getIntegerValue()
                                : AttributeConst.ROLE_GENERAL.getIntegerValue(),
                p.getCreatedAt(),
                p.getUpdatedAt(),
                p.getDeleteFlag() == null
                        ? null
                        : p.getDeleteFlag() == JpaConst.EMP_DEL_TRUE
                                ? AttributeConst.DEL_FLAG_TRUE.getIntegerValue()
                                : AttributeConst.DEL_FLAG_FALSE.getIntegerValue());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<PlayerView> toViewList(List<Player> list) {
        List<PlayerView> pvs = new ArrayList<>();

        for (Player p : list) {
            pvs.add(toView(p));
        }

        return pvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param e DTOモデル(コピー先)
     * @param ev Viewモデル(コピー元)
     */
    public static void copyViewToModel(Player p, PlayerView pv) {
        p.setId(pv.getId());
        p.setCode(pv.getCode());
        p.setName(pv.getName());
        p.setPassword(pv.getPassword());
        p.setAdminFlag(pv.getAdminFlag());
        p.setCreatedAt(pv.getCreatedAt());
        p.setUpdatedAt(pv.getUpdatedAt());
        p.setDeleteFlag(pv.getDeleteFlag());

    }

}
