package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.ReportView;
import constants.MessageConst;

public class ReportValidator {
    /**
     * 日報インスタンスの各項目についてバリデーションを行う
     * @param rv 日報インスタンス
     * @return エラーのリスト
     */
    public static List<String> validate(ReportView rv) {
        List<String> errors = new ArrayList<String>();

        //タイトルのチェック
        String titleError = validateTitle(rv.getTitle());
        if (!titleError.equals("")) {
            errors.add(titleError);
        }

      //とくせいのチェック
        String abilityError = validateAbility(rv.getAbility());
        if (!abilityError.equals("")) {
            errors.add(abilityError);
        }

      //性格のチェック
        String natureError = validateNature(rv.getNature());
        if (!natureError.equals("")) {
            errors.add(natureError);
        }

      //わざ１のチェック
        String move1Error = validateMove1(rv.getMove1());
        if (!move1Error.equals("")) {
            errors.add(move1Error);
        }
      //わざ２のチェック
        String move2Error = validateMove2(rv.getMove2());
        if (!move2Error.equals("")) {
            errors.add(move2Error);
        }
      //わざ３のチェック
        String move3Error = validateMove3(rv.getMove3());
        if (!move3Error.equals("")) {
            errors.add(move3Error);
        }
      //わざ４のチェック
        String move4Error = validateMove4(rv.getMove4());
        if (!move4Error.equals("")) {
            errors.add(move4Error);
        }
      //わざ４のチェック
        String heldItemError = validateHeldItem(rv.getHeldItem());
        if (!heldItemError.equals("")) {
            errors.add(heldItemError);
        }

      //基礎ポイントの合計のチェック
        String effortValueTotalError = validateEffortValueTotal(rv.getHitPoints(), rv.getAttack(), rv.getDefense(), rv.getSpecialAttack(),rv.getSpecialDefense(),rv.getSpeed());
        if (!effortValueTotalError.equals("")) {
            errors.add(effortValueTotalError);
        }

      //基礎ポイントの最大値のチェック
        String effortValueMaxError = validateEffortValueMax(rv.getHitPoints(), rv.getAttack(), rv.getDefense(), rv.getSpecialAttack(),rv.getSpecialDefense(),rv.getSpeed());
        if (!effortValueMaxError.equals("")) {
            errors.add(effortValueMaxError);
        }

        return errors;
    }

    /**
     * タイトルに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @param title タイトル
     * @return エラーメッセージ
     */
    private static String validateTitle(String title) {
        if (title == null || title.equals("")) {
            return MessageConst.E_NOTITLE.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * 特性に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @return エラーメッセージ
     */
    private static String validateAbility(String ability) {
        if (ability == null || ability.equals("")) {
            return MessageConst.E_NOPOKEN_ABILITY.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * 性格に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @return エラーメッセージ
     */
    private static String validateNature(String nature) {
        if (nature == null || nature.equals("")) {
            return MessageConst.E_NOPOKEN_NATURE.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * わざ１に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @return エラーメッセージ
     */
    private static String validateMove1(String move1) {
        if (move1 == null || move1.equals("")) {
            return MessageConst.E_NOPOKEN_MOVE1.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * わざ２に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @return エラーメッセージ
     */
    private static String validateMove2(String move2) {
        if (move2 == null || move2.equals("")) {
            return MessageConst.E_NOPOKEN_MOVE2.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * わざ３に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @return エラーメッセージ
     */
    private static String validateMove3(String move3) {
        if (move3 == null || move3.equals("")) {
            return MessageConst.E_NOPOKEN_MOVE3.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    /**
     * わざ４に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @return エラーメッセージ
     */
    private static String validateMove4(String move4) {
        if (move4 == null || move4.equals("")) {
            return MessageConst.E_NOPOKEN_MOVE4.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }
    /**
     * 持ち物に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
     * @return エラーメッセージ
     */
    private static String validateHeldItem(String heldItem) {
        if (heldItem == null || heldItem.equals("")) {
            return MessageConst.E_NOPOKEN_ITEM.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }
    /**
     * 基礎ポイントが正しいかチェックし、エラーメッセージを返却
     * @return エラーメッセージ
     */
    private static String validateEffortValueTotal(int hitPoints, int attack, int defense, int specialAttack, int specialDefense, int speed) {
        if (hitPoints + attack + defense + specialAttack + specialDefense + speed == 508) {
            return MessageConst.E_MISS_EffortValue_SUM.getMessage();
        }
        //入力値がある場合は空文字を返却
        return "";
    }

    private static String validateEffortValueMax(int hitPoints, int attack, int defense, int specialAttack, int specialDefense, int speed) {

        int [] array = {hitPoints, attack, defense, specialAttack, specialDefense, speed};

        int intMax = calcMax(array);

        if(intMax > 252) {
            return MessageConst.E_MISS_EffortValue_MAX.getMessage();
        }

        //入力値がある場合は空文字を返却
        return "";
    }

    private static int calcMax(int[] array) {

        int intMax = array[0];

        for (int i = 1; i < array.length; i++ ) {
            if(intMax < array[i]) {
                intMax = array[i];
            }
        }
        return intMax;
    }
}
