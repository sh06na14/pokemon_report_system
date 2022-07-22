<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="constants.AttributeConst" %>
<%@ page import="constants.ForwardConst" %>

<c:set var="actPokemon" value="${ForwardConst.ACT_Pokemon.getValue()}" />
<c:set var="commShow" value="${ForwardConst.CMD_SHOW.getValue()}" />
<c:set var="commNew" value="${ForwardConst.CMD_NEW.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />

<c:import url="../layout/app.jsp">
    <c:param name="content">
        <c:if test="${flush != null}">
            <div id="flush_success">
                <c:out value="${flush}"></c:out>
            </div>
        </c:if>
        <h2>ポケモン　一覧</h2>
        <table id="employee_list">
            <tbody>
                <tr>
                    <th>図鑑番号</th>
                    <th>名前</th>
                    <th>操作</th>
                </tr>
                <c:forEach var="employee" items="${pokemons}" varStatus="status">
                    <tr class="row${status.count % 2}">
                        <td><c:out value="${pokemon.code}" /></td>
                        <td><c:out value="${pokemon.name}" /></td>
                        <td>
                            <c:choose>
                                <c:when test="${pokemon.deleteFlag == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()}">
                                    （削除済み）
                                </c:when>
                                <c:otherwise>
                                    <a href="<c:url value='?action=${actPokemon}&command=${commShow}&id=${pokemon.id}' />">詳細を見る</a>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <div id="pagination">
            （全 ${pokemons_count} 件）<br />
            <c:forEach var="i" begin="1" end="${((pokemons_count - 1) / maxRow) + 1}" step="1">
                <c:choose>
                    <c:when test="${i == page}">
                        <c:out value="${i}" />&nbsp;
                    </c:when>
                    <c:otherwise>
                        <a href="<c:url value='?action=${actPokemon}&command=${commIdx}&page=${i}' />"><c:out value="${i}" /></a>&nbsp;
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
        <p><a href="<c:url value='?action=${actPokemon}&command=${commNew}' />">新規従業員の登録</a></p>

    </c:param>
</c:import>