<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>
<%@ page import="constants.AttributeConst" %>

<c:set var="actPokemon" value="${ForwardConst.ACT_Pokemon.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdit" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>id : ${pokemon.id} のポケモン情報 詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>図鑑番号</th>
                    <td><c:out value="${pokemon.code}" /></td>
                </tr>
                <tr>
                    <th>名前</th>
                    <td><c:out value="${pokemon.name}" /></td>
                </tr>
                <tr>
                    <th>タイプ１</th>
                    <td><c:out value="${pokemon.type1}" /></td>
                </tr>
                <tr>
                    <th>タイプ２</th>
                    <td><c:out value="${pokemon.type2}" /></td>
                </tr>
                <tr>
                    <th>HP</th>
                    <td><c:out value="${pokemon.hitPoints}" /></td>
                </tr>
                <tr>
                    <th>こうげき</th>
                    <td><c:out value="${pokemon.attack}" /></td>
                </tr>
                <tr>
                    <th>ぼうぎょ</th>
                    <td><c:out value="${pokemon.defense}" /></td>
                </tr>
                <tr>
                    <th>とくこう</th>
                    <td><c:out value="${pokemon.specialAttack}" /></td>
                </tr>
                <tr>
                    <th>とくぼう</th>
                    <td><c:out value="${pokemon.specialDefense}" /></td>
                </tr>
                <tr>
                    <th>すばやさ</th>
                    <td><c:out value="${pokemon.speed}" /></td>
                </tr>
                <tr>
                    <th>合計</th>
                    <td><c:out value="${sum}" /></td>
                </tr>
            </tbody>
        </table>

        <p>
            <a href="<c:url value='?action=${actPokemon}&command=${commEdit}&id=${pokemon.id}' />">このポケモン情報を編集する</a>
        </p>

        <p>
            <a href="<c:url value='?action=${actPokemon}&command=${commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>