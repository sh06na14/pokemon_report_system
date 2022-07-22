<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="actPlayer" value="${ForwardConst.ACT_Player.getValue()}" />
<c:set var="actPokemon" value="${ForwardConst.ACT_Pokemon.getValue()}" />
<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="actAuth" value="${ForwardConst.ACT_AUTH.getValue()}" />

<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commOut" value="${ForwardConst.CMD_LOGOUT.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commShow_Login" value="${ForwardConst.CMD_SHOW_LOGIN.getValue()}" />



<!DOCTYPE html>
<html lang="ja">
<head>
<meta charset="UTF-8">
    <title><c:out value="ポケモン育成論管理システム" /></title>
    <link rel="stylesheet" href="<c:url value='/css/reset.css' />">
    <link rel="stylesheet" href="<c:url value='/css/style.css' />">
</head>
<body>
    <div id="wrapper">
        <div id="header">
            <div id="header_menu">
                <h1><a href="<c:url value='/?action=${actTop}&command=${commIdx}' />">ポケモン育成論管理システム</a></h1>&nbsp;&nbsp;&nbsp;
                <a href="<c:url value='?action=${actRep}&command=${commIdx}' />">育成論管理</a>&nbsp;
                <c:if test="${sessionScope.login_player != null}">
                    <c:if test="${sessionScope.login_player.adminFlag == AttributeConst.ROLE_ADMIN.getIntegerValue()}">
                        <a href="<c:url value='?action=${actPlayer}&command=${commIdx}' />">プレイヤー管理</a>&nbsp;
                        <a href="<c:url value='?action=${actPokemon}&command=${commIdx}' />">ポケモン管理</a>&nbsp;
                    </c:if>
                </c:if>
            </div>
            <c:if test="${sessionScope.login_player != null}">
                <div id="player_name">
                    <c:out value="${sessionScope.login_player.name}" />
                    &nbsp;さん&nbsp;&nbsp;&nbsp;
                    <a href="<c:url value='?action=${actAuth}&command=${commOut}' />">ログアウト</a>
                </div>
            </c:if>
            <c:if test="${sessionScope.login_player == null}">
                <div id="login">
                    <a href="<c:url value='?action=${actAuth}&command=${commShow_Login}' />">ログイン</a>
                </div>
            </c:if>
        </div>
        <div id="content">${param.content}</div>
        <div id="footer">by Shun Nagasawa.</div>
    </div>
</body>
</html>
