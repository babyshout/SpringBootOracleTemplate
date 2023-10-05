<%@ page import="kopo.poly.dto.UserInfoDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%
    String msg = CmmUtil.nvl(
            (String) request.getAttribute("msg")
    );
%>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title><%=msg%></title>

    <link rel="stylesheet" href="/css/table.css">
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>

    <script type="text/javascript">
        $(() => {
            $("#btnLogin").on('click', () =>
                location.href = '/user/login'
            )
        })
    </script>
</head>
<body>
<h2>비밀번호 재설정 결과</h2>
<hr/>
<br/>

<form id="f">
    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">
                    <%=msg%>
                </div>
            </div>
        </div>
    </div>
    <div>
        <button id="btnLogin" type="button">로그인</button>
    </div>
</form>


</body>
</html>