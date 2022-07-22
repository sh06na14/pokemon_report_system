<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_Pokemon.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="${AttributeConst.POKEMON_CODE.getValue()}">図鑑番号</label><br />
<input type="text" name="${AttributeConst.POKEMON_CODE.getValue()}" id="${AttributeConst.POKEMON_CODE.getValue()}" value="${pokemon.code}" />
<br /><br />

<label for="${AttributeConst.POKEMON_NAME.getValue()}">名前</label><br />
<input type="text" name="${AttributeConst.POKEMON_NAME.getValue()}" id="${AttributeConst.POKEMON_NAME.getValue()}" value="${pokemon.name}" />
<br /><br />

<label for="${AttributeConst.POKEMON_TYPE_A.getValue()}">タイプ１</label><br />
<input type="text" name="${AttributeConst.POKEMON_TYPE_A.getValue()}" id="${AttributeConst.POKEMON_TYPE_A.getValue()}" value="${pokemon.type1}" />
<br /><br />

<label for="${AttributeConst.POKEMON_TYPE_B.getValue()}">タイプ２</label><br />
<input type="text" name="${AttributeConst.POKEMON_TYPE_B.getValue()}" id="${AttributeConst.POKEMON_TYPE_B.getValue()}" value="${pokemon.type2}" />
<br /><br />

<label for="${AttributeConst.POKEMON_HP.getValue()}">HP</label><br />
<input type="number" name="${AttributeConst.POKEMON_HP.getValue()}" id="${AttributeConst.POKEMON_HP.getValue()}" value="${pokemon.hitPoints}" />
<br /><br />

<label for="${AttributeConst.POKEMON_ATTACK.getValue()}">こうげき</label><br />
<input type="number" name="${AttributeConst.POKEMON_ATTACK.getValue()}" id="${AttributeConst.POKEMON_ATTACK.getValue()}" value="${pokemon.attack}" />
<br /><br />

<label for="${AttributeConst.POKEMON_DEFENSE.getValue()}">ぼうぎょ</label><br />
<input type="number" name="${AttributeConst.POKEMON_DEFENSE.getValue()}" id="${AttributeConst.POKEMON_DEFENSE.getValue()}" value="${pokemon.defense}" />
<br /><br />

<label for="${AttributeConst.POKEMON_SPECIAL_ATTACK.getValue()}">とくこう</label><br />
<input type="number" name="${AttributeConst.POKEMON_SPECIAL_ATTACK.getValue()}" id="${AttributeConst.POKEMON_SPECIAL_ATTACK.getValue()}" value="${pokemon.specialAttack}" />
<br /><br />

<label for="${AttributeConst.POKEMON_SPECIAL_DEFENSE.getValue()}">とくぼう</label><br />
<input type="number" name="${AttributeConst.POKEMON_SPECIAL_DEFENSE.getValue()}" id="${AttributeConst.POKEMON_SPECIAL_DEFENSE.getValue()}" value="${pokemon.specialDefense}" />
<br /><br />

<label for="${AttributeConst.POKEMON_SPEED.getValue()}">すばやさ</label><br />
<input type="number" name="${AttributeConst.POKEMON_SPEED.getValue()}" id="${AttributeConst.POKEMON_SPEED.getValue()}" value="${pokemon.speed}" />
<br /><br />

<br /><br />
<input type="hidden" name="${AttributeConst.POKEMON_ID.getValue()}" value="${pokemon.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>