<%@ page contentType="text/html; charset=utf-8" %>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>메일 작성하기</title>
    <Link rel="stylesheet" href="/css/table.css"/>
<%--    여기 왜 자동으로 static 못잡아줌?--%>
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>
    <script>
        $(function () {
            $("#btnSend").on("click", function () {
                $.ajax({
                    url: "/mail/sendMail",
                    type: 'post',
                    dataType: 'json',
                    data: $("#f").serialize(),
                    success: function (data) {
                        alert(data.msg);
                    },
                    error: function(data) {
                        alert("error!" + data)
                    }
                })
            })
        })
    </script>
</head>
<body>
<h2>메일 작성하기</h2>
<hr />
<br />
<form id="f">
    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">받는사람</div>
                <div class="divTableCell">
                    <input type="text" name="toMail" maxlength="100" style="width: 95%;" />
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">메일제목</div>
                <div class="divTableCell">
                    <input type="text" name="title" maxlength="100" style="width: 95%;" />
                </div>
            </div>
            <div class="divTableRow">
                <div class="divTableCell">메일내용</div>
                <div class="divTableCell">
                    <input type="text" name="contents" maxlength="100" style="width: 95%;" />
                </div>
            </div>
        </div>
        <div>
            <button id="btnSend" type="button">메일 발송</button>
            <button type="reset">다시 작성</button>
        </div>
    </div>
</form>
</body>
</html>