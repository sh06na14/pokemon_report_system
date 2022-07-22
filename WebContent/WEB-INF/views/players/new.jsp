<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actPlayer" value="${ForwardConst.ACT_Player.getValue()}" />
<c:set var="actTop" value="${ForwardConst.ACT_TOP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commCrt" value="${ForwardConst.CMD_CREATE.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <h2>プレイヤー　新規登録ページ</h2>

        <form method="POST" action="<c:url value='?action=${actPlayer}&command=${commCrt}' />">
            <c:import url="_form.jsp" />
        </form>

    </c:param>
</c:import>