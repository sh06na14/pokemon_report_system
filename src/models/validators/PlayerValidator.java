package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.PlayerView;
import constants.MessageConst;
import services.PlayerService;

public class PlayerValidator {
    /**
     * Playerインスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param pv PlayerViewのインスタンス
     * @param codeDuplicateCheckFlag Player番号の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーのリスト
     */
    public static List<String> validate(
            PlayerService service, PlayerView pv, Boolean codeDuplicateCheckFlag, Boolean passwordCheckFlag) {
        List<String> errors = new ArrayList<String>();

        //プレイヤー番号のチェック
        String codeError = validateCode(service, pv.getCode(), codeDuplicateCheckFlag);
        if (!codeError.equals("")) {
            errors.add(codeError);
        }

        //氏名のチェック
        String nameError = validateName(pv.getName());
        if (!nameError.equals("")) {
            errors.add(nameError);
        }

        //パスワードのチェック
        String passError = validatePassword(pv.getPassword(), passwordCheckFlag);
        if (!passError.equals("")) {
            errors.add(passError);
        }

        return errors;
    }

    /**
     * プレイヤー番号の入力チェックを行い、エラーメッセージを返却
     * @param service EmployeeServiceのインスタンス
     * @param code プレイヤー番号
     * @param codeDuplicateCheckFlag プレイヤー番号の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validateCode(PlayerService service, String code, Boolean codeDuplicateCheckFlag) {

        //入力値がなければエラーメッセージを返却
        if (code == null || code.equals("")) {
            return MessageConst.E_NOPLAYER_CODE.getMessage();
        }

        if (codeDuplicateCheckFlag) {
            //プレイヤー番号の重複チェックを実施

            long employeesCount = isDuplicatePlayer(service, code);

            //同一社員番号が既に登録されている場合はエラーメッセージを返却
            if (employeesCount > 0) {
                return MessageConst.E_PLAYER_CODE_EXIST.getMessage();
            }
        }

        //エラーがない場合は空文字を返却
        return "";
    }

    /**
     * @param service EmployeeServiceのインスタンス
     * @param code プレイヤー番号
     * @return Playerテーブルに登録されている同一プレイヤー番号のデータの件数
     */
    private static long isDuplicatePlayer(PlayerService service, String code) {

        long playersCount = service.countByCode(code);
        return playersCount;
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
     * パスワードの入力チェックを行い、エラーメッセージを返却
     * @param password パスワード
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーメッセージ
     */
    private static String validatePassword(String password, Boolean passwordCheckFlag) {

        //入力チェックを実施 かつ 入力値がなければエラーメッセージを返却
        if (passwordCheckFlag && (password == null || password.equals(""))) {
            return MessageConst.E_NOPASSWORD.getMessage();
        }

        //エラーがない場合は空文字を返却
        return "";
    }

}
