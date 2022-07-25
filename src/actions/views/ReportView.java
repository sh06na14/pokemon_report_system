package actions.views;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
public class ReportView {
    /**
     * id
     */
    private Integer id;

    /**
     * 育成論を登録したプレイヤー
     */
    private PlayerView player;

    /**
     * 育成論を登録するポケモン
     */
    private PokemonView pokemon;

    /**
     * いつの日報かを示す日付
     */
    private LocalDate reportDate;

    /**
     * 日報のタイトル
     */
    private String title;

    /**
     * 特性
     */
    private String ability;

    /**
     * 性格
     */
    private String nature;

    /**
     * 基礎ポイント(HP)
     */
    private int hitPoints;

    /**
     * 基礎ポイント(こうげき)
     */
    private int attack;

    /**
     * 基礎ポイント(ぼうぎょ)
     */
    private int defense;

    /**
     * 基礎ポイント(とくこう)
     */
    private int specialAttack;

    /**
     * 基礎ポイント(とくぼう)
     */
    private int specialDefense;

    /**
     * 基礎ポイント(すばやさ)
     */
    private int speed;

    /**
     * わざ1
     */
    private String move1;

    /**
     * わざ2
     */
    private String move2;

    /**
     * わざ3
     */
    private String move3;

    /**
     * わざ4
     */
    private String move4;

    /**
     * 持ち物
     */
    private String heldItem;

    /**
     * 育成論のコメント
     */
    private String comment;

    /**
     * 登録日時
     */
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    private LocalDateTime updatedAt;

    /**
     * 削除された育成論かどうか（現役：0、削除済み：1）
     */
    private Integer deleteFlag;

}
