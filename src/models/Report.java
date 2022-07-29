package models;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "reports")
@NamedQueries({
    @NamedQuery(
            name = "report.getAll",
            query = "SELECT r FROM Report AS r ORDER BY r.id DESC"),
    @NamedQuery(
            name = "report.count",
            query = "SELECT COUNT(r) FROM Report AS r"),
    @NamedQuery(
            name = "report.getAllMine",
            query = "SELECT r FROM Report AS r WHERE r.player = :player ORDER BY r.id DESC"),
    @NamedQuery(
            name = "report.countAllMine",
            query = "SELECT COUNT(r) FROM Report AS r WHERE r.player = :player")
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Report {
    /**
     * id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 育成論を登録したプレイヤー
     */
    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private Player player;

    /**
     * 育成論を登録するポケモン
     */
    @ManyToOne
    @JoinColumn(name = "pokemon_id", nullable = false)
    private Pokemon pokemon;

    /**
     * いつの育成論かを示す日付
     */
    @Column(name = "report_date", nullable = false)
    private LocalDate reportDate;

    /**
     * 育成論のタイトル
     */
    @Column(name = "title", length = 255, nullable = false)
    private String title;

    /**
     * 特性
     */
    @Column(name = "ability", nullable = false)
    private String ability;

    /**
     * 性格
     */
    @Column(name = "nature", nullable = false)
    private String nature;

    /**
     * 基礎ポイント(HP)
     */
    @Column(name = "hitPoints", nullable = false)
    private int hitPoints;

    /**
     * 基礎ポイント(こうげき)
     */
    @Column(name = "attack", nullable = false)
    private int attack;

    /**
     * 基礎ポイント(ぼうぎょ)
     */
    @Column(name = "defense", nullable = false)
    private int defense;

    /**
     * 基礎ポイント(とくこう)
     */
    @Column(name = "specialAttack", nullable = false)
    private int specialAttack;

    /**
     * 基礎ポイント(とくぼう)
     */
    @Column(name = "specialDefense", nullable = false)
    private int specialDefense;

    /**
     * 基礎ポイント(すばやさ)
     */
    @Column(name = "speed", nullable = false)
    private int speed;

    /**
     * わざ1
     */
    @Column(name = "move1", nullable = false)
    private String move1;

    /**
     * わざ2
     */
    @Column(name = "move2", nullable = false)
    private String move2;

    /**
     * わざ3
     */
    @Column(name = "move3", nullable = false)
    private String move3;

    /**
     * わざ4
     */
    @Column(name = "move4", nullable = false)
    private String move4;

    /**
     * 持ち物
     */
    @Column(name = "heldItem", nullable = false)
    private String heldItem;

    /**
     * 育成論のコメント
     */
    @Lob
    @Column(name = "comment", nullable = false)
    private String comment;

    /**
     * 登録日時
     */
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * 更新日時
     */
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    /**
     * 削除された育成論かどうか（現役：0、削除済み：1）
     */
    @Column(name = "delete_flag", nullable = false)
    private Integer deleteFlag;
}
