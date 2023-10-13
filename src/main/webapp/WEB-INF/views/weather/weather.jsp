<%@ page import="kopo.poly.dto.FoodDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page contentType="text/html; charset=utf-8" pageEncoding="UTF-8" %>
<%

%>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>지금 날씨!!</title>

    <link rel="stylesheet" href="/css/table.css" />
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>

</head>
<body>
<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableCell">
                요일
            </div>
            <div class="divTableCell">
                메뉴
            </div>
        </div>
    </div>
    <div class="divTableBody">
        <%
            for (WeatherDTO rDTO : rList) {
        %>
        <div class="divTableRow">
            <div class="divTableCell">
                <%=CmmUtil.nvl(rDTO.getDay())%>
            </div>
            <div class="divTableCell">
                <%=CmmUtil.nvl(rDTO.getFood_nm())%>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>
</body>
</html>