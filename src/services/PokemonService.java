package services;

import java.util.List;

import javax.persistence.NoResultException;

import actions.views.PokemonConverter;
import actions.views.PokemonView;
import constants.JpaConst;
import models.Pokemon;
import models.validators.PokemonValidator;

public class PokemonService extends ServiceBase {
    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、PokemonViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<PokemonView> getPerPage(int page) {
        List<Pokemon> pokemons = em.createNamedQuery("pokemon.getAll", Pokemon.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return PokemonConverter.toViewList(pokemons);
    }

    /**
     * ポケモンテーブルのデータの件数を取得し、返却する
     * @return ポケモンテーブルのデータの件数
     */
    public long countAll() {
        long pokemonCount = (long) em.createNamedQuery("pokemon.count", Long.class)
                .getSingleResult();

        return pokemonCount;
    }

    /**
     * 図鑑番号を条件に取得したデータをPokemonViewのインスタンスで返却する
     * @param code 図鑑番号
     * @return 取得データのインスタンス 取得できない場合null
     */
    public PokemonView findOne(String code) {
        Pokemon p = null;
        try {

            //図鑑番号を条件に未削除のポケモンを1件取得する
            p = em.createNamedQuery("pokemon.getByCode", Pokemon.class)
                    .setParameter(JpaConst.JPQL_PARM_CODE, code)
                    .getSingleResult();

        } catch (NoResultException ex) {
        }

        return PokemonConverter.toView(p);

    }

    /**
     * idを条件に取得したデータをPokemonViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public PokemonView findOne(int id) {
        Pokemon p = findOneInternal(id);
        return PokemonConverter.toView(p);
    }

    /**
     * 図鑑番号を条件に該当するデータの件数を取得し、返却する
     * @param code 図鑑番号
     * @return 該当するデータの件数
     */
    public long countByCode(String code) {

        //指定した図鑑番号を保持するポケモンの件数を取得する
        long pokemons_count = (long) em.createNamedQuery("pokemon.countRegisteredByCode", Long.class)
                .setParameter(JpaConst.JPQL_PARM_CODE, code)
                .getSingleResult();
        return pokemons_count;
    }

    /**
     * 画面から入力されたポケモンの登録内容を元にデータを1件作成し、ポケモンテーブルに登録する
     * @param pv 画面から入力されたポケモンの登録内容
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(PokemonView pv) {


        //登録内容のバリデーションを行う
        List<String> errors = PokemonValidator.validate(this, pv, true);

        //バリデーションエラーがなければデータを登録する
        if (errors.size() == 0) {
            createInternal(pv);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力されたポケモンの更新内容を元にデータを1件作成し、ポケモンテーブルを更新する
     * @param pv 画面から入力されたポケモンの登録内容
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(PokemonView pv) {

        //idを条件に登録済みのポケモン情報を取得する
        PokemonView savedPokemon = findOne(pv.getId());

        boolean validateCode = false;
        if (!savedPokemon.getCode().equals(pv.getCode())) {
            //図鑑番号を更新する場合

            //図鑑番号についてのバリデーションを行う
            validateCode = true;
            //変更後の図鑑番号を設定する
            savedPokemon.setCode(pv.getCode());
        }

        savedPokemon.setName(pv.getName()); //変更後の氏名を設定する
        savedPokemon.setType1(pv.getType1()); //変更後のType1を設定する
        savedPokemon.setType2(pv.getType2()); //変更後のType2を設定する
        savedPokemon.setHitPoints(pv.getHitPoints()); //変更後のHitPointsを設定する
        savedPokemon.setAttack(pv.getAttack()); //変更後のAttackを設定する
        savedPokemon.setDefense(pv.getDefense()); //変更後のDefenseを設定する
        savedPokemon.setSpecialAttack(pv.getSpecialAttack()); //変更後のSpecialAttackを設定する
        savedPokemon.setSpecialDefense(pv.getSpecialDefense()); //変更後のSpecialDefenseを設定する
        savedPokemon.setSpeed(pv.getSpeed()); //変更後のSpeedを設定する

        //更新内容についてバリデーションを行う
        List<String> errors = PokemonValidator.validate(this, savedPokemon, validateCode);

        //バリデーションエラーがなければデータを更新する
        if (errors.size() == 0) {
            updateInternal(savedPokemon);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件にポケモンデータを論理削除する
     * @param id
     */
    public void destroy(Integer id) {

        //idを条件に登録済みのポケモン情報を取得する
        PokemonView savedPokemon = findOne(id);

        //論理削除フラグをたてる
        savedPokemon.setDeleteFlag(JpaConst.Pokemon_DEL_TRUE);

        //更新処理を行う
        updateInternal(savedPokemon);

    }

    /**
     * 図鑑番号を条件に検索し、データが取得できるかどうかで認証結果を返却する
     * @param code 図鑑番号
     * @return 認証結果を返却す(成功:true 失敗:false)
     */
    public Boolean validateLogin(String code) {

        boolean isValidPokemon = false;
        if (code != null && !code.equals("")) {
            PokemonView pv = findOne(code);

            if (pv != null && pv.getId() != null) {

                //データが取得できた場合、認証成功
                isValidPokemon = true;
            }
        }

        //認証結果を返却する
        return isValidPokemon;
    }

    /**
     * idを条件にデータを1件取得し、Pokemonのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Pokemon findOneInternal(int id) {
        Pokemon p = em.find(Pokemon.class, id);

        return p;
    }

    /**
     * Pokemonデータを1件登録する
     * @param pv Pokemonデータ
     * @return 登録結果(成功:true 失敗:false)
     */
    private void createInternal(PokemonView pv) {

        em.getTransaction().begin();
        em.persist(PokemonConverter.toModel(pv));
        em.getTransaction().commit();

    }

    /**
     * Pokemonデータを更新する
     * @param ev 画面から入力された従業員の登録内容
     */
    private void updateInternal(PokemonView pv) {

        em.getTransaction().begin();
        Pokemon p = findOneInternal(pv.getId());
        PokemonConverter.copyViewToModel(p, pv);
        em.getTransaction().commit();

    }

}
