package actions.views;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class PokemonView {
    /**
     * id
     */
    private Integer id;

    /**
     * 図鑑番号
     */
    private String code;

    /**
     * ポケモン名
     */
    private String name;

    /**
     * タイプ1
     */
    private String type1;

    /**
     * タイプ2
     */
    private String type2;

    /**
     * 種族値(HP)
     */
    private int hitPoints;

    /**
     * 種族値(こうげき)
     */
    private int attack;

    /**
     * 種族値(ぼうぎょ)
     */
    private int defense;

    /**
     * 種族値(とくこう)
     */
    private int specialAttack;

    /**
     * 種族値(とくぼう)
     */
    private int specialDefense;

    /**
     * 種族値(すばやさ)
     */
    private int speed;

    /**
     * 削除された従業員かどうか（現役：0、削除済み：1）
     */
    private Integer deleteFlag;

}
