<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String ssUserName = CmmUtil.nvl((String) session.getAttribute("SS_USER_NAME"));
    String ssUserId = CmmUtil.nvl((String) session.getAttribute("SS_USER_ID"));
%>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>로그인 성공</title>

    <link rel="stylesheet" href="/css/table.css">
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script type="text/javascript">

        $(function () {
            $("#btnSend").on("click", function () {
                location.href = "/html/index.html";
            })
        })
    </script>

</head>
<body>

<div class="divTable minimalistBlack">
    <div class="divTableBody">
        <div class="divTableRow">
            <div class="divTableCell">로그인된 사용자 이름</div>
            <div class="divTableCell">
                <%=ssUserName%> 님이 로그인 하였습니다.">
            </div>
        </div>

        <div class="divTableRow">
            <div class="divTableCell">로그인된 사용자 아이디</div>
            <div class="divTableCell">
                <%=ssUserId%> 입니다.">
            </div>
        </div>

    </div>
</div>

<div></div>
<br /><br />
<button id="btnSend" type="button">메인 화면 이동</button>


</body>
</html>