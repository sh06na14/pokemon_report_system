package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "pokemons")
@NamedQueries({
    @NamedQuery(
            name = "pokemon.getAll",
            query = "SELECT p FROM Pokemon AS p ORDER BY p.id DESC"),
    @NamedQuery(
            name = "pokemon.count",
            query = "SELECT COUNT(p) FROM Pokemon AS p"),
    @NamedQuery(
            name = "pokemon.getByCode",
            query = "SELECT p FROM Pokemon AS p WHERE p.deleteFlag = 0 AND p.code = :code"),
    @NamedQuery(
            name = "pokemon.countRegisteredByCode",
            query = "SELECT COUNT(p) FROM Pokemon AS p WHERE p.code = :code")
})

@Getter //全てのクラスフィールドについてgetterを自動生成する(Lombok)
@Setter //全てのクラスフィールドについてsetterを自動生成する(Lombok)
@NoArgsConstructor //引数なしコンストラクタを自動生成する(Lombok)
@AllArgsConstructor //全てのクラスフィールドを引数にもつ引数ありコンストラクタを自動生成する(Lombok)
@Entity
public class Pokemon {
    /**
     * id
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 図鑑番号
     */
    @Column(name = "code", nullable = false, unique = true)
    private String code;

    /**
     * ポケモン名
     */
    @Column(name = "name", nullable = false)
    private String name;

    /**
     * タイプ1
     */
    @Column(name = "type1", nullable = false)
    private String type1;

    /**
     * タイプ2
     */
    @Column(name = "type2")
    private String type2;

    /**
     * 種族値(HP)
     */
    @Column(name = "hitPoints", nullable = false)
    private int hitPoints;

    /**
     * 種族値(こうげき)
     */
    @Column(name = "attack", nullable = false)
    private int attack;

    /**
     * 種族値(ぼうぎょ)
     */
    @Column(name = "defense", nullable = false)
    private int defense;

    /**
     * 種族値(とくこう)
     */
    @Column(name = "specialAttack", nullable = false)
    private int specialAttack;

    /**
     * 種族値(とくぼう)
     */
    @Column(name = "specialDefense", nullable = false)
    private int specialDefense;

    /**
     * 種族値(すばやさ)
     */
    @Column(name = "speed", nullable = false)
    private int speed;

    /**
     * 削除されたポケモンかどうか（現役：0、削除済み：1）
     */
    @Column(name = "delete_flag", nullable = false)
    private Integer deleteFlag;

}
