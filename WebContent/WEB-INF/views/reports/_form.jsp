<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="constants.AttributeConst" %>

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<fmt:parseDate value="${report.reportDate}" pattern="yyyy-MM-dd" var="reportDay" type="date" />
<label for="${AttributeConst.REP_DATE.getValue()}">日付</label><br />
<input type="date" name="${AttributeConst.REP_DATE.getValue()}" id="${AttributeConst.REP_DATE.getValue()}" value="<fmt:formatDate value='${reportDay}' pattern='yyyy-MM-dd' />" />
<br /><br />

<label>氏名</label><br />
<c:out value="${sessionScope.login_player.name}" />
<br /><br />

<label>ポケモン名</label><br />
<c:choose>
    <c:when test="${report.pokemon.name == null}">
        <c:out value="${sessionScope.search_pokemon.name}" />
    </c:when>
    <c:otherwise>
        <c:out value="${report.pokemon.name}" />
    </c:otherwise>
</c:choose>
<br /><br />

<label for="${AttributeConst.REP_TITLE.getValue()}">タイトル</label><br />
<input type="text" name="${AttributeConst.REP_TITLE.getValue()}" id="${AttributeConst.REP_TITLE.getValue()}" value="${report.title}" />
<br /><br />

<label for="${AttributeConst.REP_ABILITY.getValue()}">特性</label><br />
<input type="text" name="${AttributeConst.REP_ABILITY.getValue()}" id="${AttributeConst.REP_ABILITY.getValue()}" value="${report.ability}" />
<br /><br />

<label for="${AttributeConst.REP_NATURE.getValue()}">性格</label><br />
<input type="text" name="${AttributeConst.REP_NATURE.getValue()}" id="${AttributeConst.REP_NATURE.getValue()}" value="${report.nature}" />
<br /><br />

<label for="${AttributeConst.REP_HP.getValue()}">基礎ポイント(HP)</label><br />
<input type="number" name="${AttributeConst.REP_HP.getValue()}" id="${AttributeConst.REP_HP.getValue()}" value="${report.hitPoints}" />
<br /><br />

<label for="${AttributeConst.REP_ATTACK.getValue()}">基礎ポイント(こうげき)</label><br />
<input type="number" name="${AttributeConst.REP_ATTACK.getValue()}" id="${AttributeConst.REP_ATTACK.getValue()}" value="${report.attack}" />
<br /><br />

<label for="${AttributeConst.REP_DEFENSE.getValue()}">基礎ポイント(ぼうぎょ)</label><br />
<input type="number" name="${AttributeConst.REP_DEFENSE.getValue()}" id="${AttributeConst.REP_DEFENSE.getValue()}" value="${report.defense}" />
<br /><br />

<label for="${AttributeConst.REP_SPECIAL_ATTACK.getValue()}">基礎ポイント(とくこう)</label><br />
<input type="number" name="${AttributeConst.REP_SPECIAL_ATTACK.getValue()}" id="${AttributeConst.REP_SPECIAL_ATTACK.getValue()}" value="${report.specialAttack}" />
<br /><br />

<label for="${AttributeConst.REP_SPECIAL_DEFENSE.getValue()}">基礎ポイント(とくぼう)</label><br />
<input type="number" name="${AttributeConst.REP_SPECIAL_DEFENSE.getValue()}" id="${AttributeConst.REP_SPECIAL_DEFENSE.getValue()}" value="${report.specialDefense}" />
<br /><br />

<label for="${AttributeConst.REP_SPEED.getValue()}">基礎ポイント(すばやさ)</label><br />
<input type="number" name="${AttributeConst.REP_SPEED.getValue()}" id="${AttributeConst.REP_SPEED.getValue()}" value="${report.speed}" />
<br /><br />

<label for="${AttributeConst.REP_MOVE_A.getValue()}">わざ１</label><br />
<input type="text" name="${AttributeConst.REP_MOVE_A.getValue()}" id="${AttributeConst.REP_MOVE_A.getValue()}" value="${report.move1}" />
<br /><br />

<label for="${AttributeConst.REP_MOVE_B.getValue()}">わざ２</label><br />
<input type="text" name="${AttributeConst.REP_MOVE_B.getValue()}" id="${AttributeConst.REP_MOVE_B.getValue()}" value="${report.move2}" />
<br /><br />

<label for="${AttributeConst.REP_MOVE_C.getValue()}">わざ３</label><br />
<input type="text" name="${AttributeConst.REP_MOVE_C.getValue()}" id="${AttributeConst.REP_MOVE_C.getValue()}" value="${report.move3}" />
<br /><br />

<label for="${AttributeConst.REP_MOVE_D.getValue()}">わざ４</label><br />
<input type="text" name="${AttributeConst.REP_MOVE_D.getValue()}" id="${AttributeConst.REP_MOVE_D.getValue()}" value="${report.move4}" />
<br /><br />

<label for="${AttributeConst.REP_HELDITEM.getValue()}">持ち物</label><br />
<input type="text" name="${AttributeConst.REP_HELDITEM.getValue()}" id="${AttributeConst.REP_HELDITEM.getValue()}" value="${report.heldItem}" />
<br /><br />

<label for="${AttributeConst.REP_COMMENT.getValue()}">コメント</label><br />
<textarea  name="${AttributeConst.REP_COMMENT.getValue()}" id="${AttributeConst.REP_COMMENT.getValue()}" rows="10" cols="50">${report.comment}</textarea>
<br /><br />
<input type="hidden" name="${AttributeConst.REP_ID.getValue()}" value="${report.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">投稿</button>