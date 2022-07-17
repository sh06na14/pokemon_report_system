<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="action" value="${ForwardConst.ACT_Player.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:if test="${errors != null}">
    <div id="flush_error">
        入力内容にエラーがあります。<br />
        <c:forEach var="error" items="${errors}">
            ・<c:out value="${error}" /><br />
        </c:forEach>

    </div>
</c:if>
<label for="${AttributeConst.PLAYER_CODE.getValue()}">プレイヤー番号</label><br />
<input type="text" name="${AttributeConst.PLAYER_CODE.getValue()}" id="${AttributeConst.PLAYER_CODE.getValue()}" value="${player.code}" />
<br /><br />

<label for="${AttributeConst.PLAYER_NAME.getValue()}">氏名</label><br />
<input type="text" name="${AttributeConst.PLAYER_NAME.getValue()}" id="${AttributeConst.PLAYER_NAME.getValue()}" value="${player.name}" />
<br /><br />

<label for="${AttributeConst.PLAYER_PASS.getValue()}">パスワード</label><br />
<input type="password" name="${AttributeConst.PLAYER_PASS.getValue()}" id="${AttributeConst.PLAYER_PASS.getValue()}" />
<br /><br />

<label for="${AttributeConst.PLAYER_ADMIN_FLG.getValue()}">権限</label><br />
<select name="${AttributeConst.PLAYER_ADMIN_FLG.getValue()}" id="${AttributeConst.PLAYER_ADMIN_FLG.getValue()}">
    <option value="${AttributeConst.ROLE_GENERAL.getIntegerValue()}"<c:if test="${player.adminFlag == AttributeConst.ROLE_GENERAL.getIntegerValue()}"> selected</c:if>>一般</option>
    <option value="${AttributeConst.ROLE_ADMIN.getIntegerValue()}"<c:if test="${player.adminFlag == AttributeConst.ROLE_ADMIN.getIntegerValue()}"> selected</c:if>>管理者</option>
</select>
<br /><br />
<input type="hidden" name="${AttributeConst.PLAYER_ID.getValue()}" value="${player.id}" />
<input type="hidden" name="${AttributeConst.TOKEN.getValue()}" value="${_token}" />
<button type="submit">登録</button>