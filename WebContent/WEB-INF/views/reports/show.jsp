<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="constants.ForwardConst" %>

<c:set var="actRep" value="${ForwardConst.ACT_REP.getValue()}" />
<c:set var="commIdx" value="${ForwardConst.CMD_INDEX.getValue()}" />
<c:set var="commEdt" value="${ForwardConst.CMD_EDIT.getValue()}" />

<c:import url="/WEB-INF/views/layout/app.jsp">
    <c:param name="content">

        <h2>育成論 詳細ページ</h2>

        <table>
            <tbody>
                <tr>
                    <th>氏名</th>
                    <td><c:out value="${report.player.name}" /></td>
                </tr>
                <tr>
                    <th>日付</th>
                    <fmt:parseDate value="${report.reportDate}" pattern="yyyy-MM-dd" var="reportDay" type="date" />
                    <td><fmt:formatDate value='${reportDay}' pattern='yyyy-MM-dd' /></td>
                </tr>
                <tr>
                    <th>タイトル</th>
                    <td><pre><c:out value="${report.title}" /></pre></td>
                </tr>
                <tr>
                    <th>ポケモン</th>
                    <td><c:out value="${report.pokemon.name}" /></td>
                </tr>
                <tr>
                    <th>特性</th>
                    <td><pre><c:out value="${report.ability}" /></pre></td>
                </tr>
                <tr>
                    <th>性格</th>
                    <td><pre><c:out value="${report.nature}" /></pre></td>
                </tr>
                <tr>
                    <th>わざ１</th>
                    <td><pre><c:out value="${report.move1}" /></pre></td>
                </tr>
                <tr>
                    <th>わざ２</th>
                    <td><pre><c:out value="${report.move2}" /></pre></td>
                </tr>
                <tr>
                    <th>わざ３</th>
                    <td><pre><c:out value="${report.move3}" /></pre></td>
                </tr>
                <tr>
                    <th>わざ４</th>
                    <td><pre><c:out value="${report.move4}" /></pre></td>
                </tr>
                <tr>
                    <th>もちもの</th>
                    <td><pre><c:out value="${report.heldItem}" /></pre></td>
                </tr>
            </tbody>
        </table>

        <table>
            <tbody>
                <tr>
                    <th></th>
                    <th>種族値</th>
                    <th>基礎ポイント</th>
                    <th>ステータス(LV50)</th>
                </tr>
                <tr>
                    <th>HP</th>
                    <td><pre><c:out value="${report.pokemon.hitPoints}" /></pre></td>
                    <td><pre><c:out value="${report.hitPoints}" /></pre></td>
                    <td><pre><c:out value="${hitPoints}" /></pre></td>
                </tr>
                <tr>
                    <th>こうげき</th>
                    <td><pre><c:out value="${report.pokemon.attack}" /></pre></td>
                    <td><pre><c:out value="${report.attack}" /></pre></td>
                    <td><pre><c:out value="${attack}" /></pre></td>
                </tr>
                <tr>
                    <th>ぼうぎょ</th>
                    <td><pre><c:out value="${report.pokemon.defense}" /></pre></td>
                    <td><pre><c:out value="${report.defense}" /></pre></td>
                    <td><pre><c:out value="${defense}" /></pre></td>
                </tr>
                <tr>
                    <th>とくこう</th>
                    <td><pre><c:out value="${report.pokemon.specialAttack}" /></pre></td>
                    <td><pre><c:out value="${report.specialAttack}" /></pre></td>
                    <td><pre><c:out value="${specialAttack}" /></pre></td>
                </tr>
                <tr>
                    <th>とくぼう</th>
                    <td><pre><c:out value="${report.pokemon.specialDefense}" /></pre></td>
                    <td><pre><c:out value="${report.specialDefense}" /></pre></td>
                    <td><pre><c:out value="${specialDefense}" /></pre></td>
                </tr>
                <tr>
                    <th>すばやさ</th>
                    <td><pre><c:out value="${report.pokemon.speed}" /></pre></td>
                    <td><pre><c:out value="${report.speed}" /></pre></td>
                    <td><pre><c:out value="${speed}" /></pre></td>
                </tr>
            </tbody>
        </table>

        <table>
            <tbody>
                <tr>
                    <th>コメント</th>
                    <td><pre><c:out value="${report.comment}" /></pre></td>
                </tr>
                <tr>
                    <th>登録日時</th>
                    <fmt:parseDate value="${report.createdAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="createDay" type="date" />
                    <td><fmt:formatDate value="${createDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
                <tr>
                    <th>更新日時</th>
                    <fmt:parseDate value="${report.updatedAt}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="updateDay" type="date" />
                    <td><fmt:formatDate value="${updateDay}" pattern="yyyy-MM-dd HH:mm:ss" /></td>
                </tr>
            </tbody>
        </table>

        <c:if test="${sessionScope.login_player.id == report.player.id}">
            <p>
                <a href="<c:url value='?action=${actRep}&command=${commEdt}&id=${report.id}' />">この日報を編集する</a>
            </p>
        </c:if>

        <p>
            <a href="<c:url value='?action=${actRep}&command=${commIdx}' />">一覧に戻る</a>
        </p>
    </c:param>
</c:import>