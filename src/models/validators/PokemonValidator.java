package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.PokemonView;
import constants.MessageConst;
import services.PokemonService;

public class PokemonValidator {

    /**
     * Pokemonインスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param ev PokemonViewのインスタンス
     * @param codeDuplicateCheckFlag 図鑑番号の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーのリスト
     */
    public static List<String> validate(
            PokemonService service, PokemonView pv, Boolean codeDuplicateCheckFlag) {
        List<String> errors = new ArrayList<String>();

        //図鑑番号のチェック
        String codeError = validateCode(service, pv.getCode(), codeDuplicateCheckFlag);
        if (!codeError.equals("")) {
            errors.add(codeError);
        }

        //名前のチェック
        String nameError = validateName(pv.getName());
        if (!nameError.equals("")) {
            errors.add(nameError);
        }

      //名前のチェック
        String typeError = validateType1(pv.getType1());
        if (!typeError.equals("")) {
            errors.add(typeError);
        }

        return errors;
    }

    /**
     * 図鑑番号の入力チェックを行い、エラーメッセージを返却
     * @param service PokemonServiceのインスタンス
     * @param code 図鑑番号
     * @param codeDuplicateCheckFlag 図鑑番号の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validateCode(PokemonService service, String code, Boolean codeDuplicateCheckFlag) {

        //入力値がなければエラーメッセージを返却
        if (code == null || code.equals("")) {
            return MessageConst.E_NOPOKEN_CODE.getMessage();
        }

        if (codeDuplicateCheckFlag) {
            //図鑑番号の重複チェックを実施

            long pokemonsCount = isDuplicatePokemon(service, code);

            //同一図鑑番号が既に登録されている場合はエラーメッセージを返却
            if (pokemonsCount > 0) {
                return MessageConst.E_POKEN_CODE_EXIST.getMessage();
            }
        }

        //エラーがない場合は空文字を返却
        return "";
    }

    /**
     * @param service PokemonServiceのインスタンス
     * @param code 図鑑番号
     * @return Pokemonテーブルに登録されている同一図鑑番号のデータの件数
     */
    private static long isDuplicatePokemon(PokemonService service, String code) {

        long pokemonsCount = service.countByCode(code);
        return pokemonsCount;
    }

    /**
     * 氏名に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param name 氏名
     * @return エラーメッセージ
     */
    private static String validateName(String name) {

        if (name == null || name.equals("")) {
            return MessageConst.E_NONAME.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * 氏名に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param name 氏名
     * @return エラーメッセージ
     */
    private static String validateType1(String type1) {

        if (type1 == null || type1.equals("")) {
            return MessageConst.E_NOPOKEN_TYPE.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }


}
