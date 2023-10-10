<%@ page import="kopo.poly.dto.MovieDTO" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8"%>
<%
    List<MovieDTO> rList = (List<MovieDTO>) request.getAttribute("rList");
%>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>영화 순위 결과</title>

    <link rel="stylesheet" href="/css/table.css" />

</head>
<body>
<h2>영화 순위 정보</h2>
<hr />
<br />
<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableCell">
                순위
            </div>
            <div class="divTableCell">
                제목
            </div>
            <div class="divTableCell">
                평점
            </div>
            <div class="divTableCell">
                개봉일
            </div>
            <div class="divTableCell">
                사진
            </div>
        </div>
    </div>
    <div class="divTableBody">
        <%
            for (MovieDTO rDTO : rList) {
        %>
        <div class="divTableRow">
            <div class="divTableCell">
                <%=CmmUtil.nvl(rDTO.getMovieRank())%>>
            </div>
            <div class="divTableCell">
                <%=CmmUtil.nvl(rDTO.getMovieNm())%>>
            </div>
            <div class="divTableCell">
                <%=CmmUtil.nvl(rDTO.getScore())%>>
            </div>
            <div class="divTableCell">
                <%=CmmUtil.nvl(rDTO.getOpenDay())%>>
            </div>
<%--            <div class="divTableCell">--%>
<%--                <%=CmmUtil.nvl(rDTO.getMovieRank())%>>--%>
<%--            </div>--%>
        </div>
        <%
            }
        %>
    </div>
</div>
</body>
</html>