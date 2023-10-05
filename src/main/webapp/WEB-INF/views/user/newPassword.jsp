<%@ page import="kopo.poly.dto.UserInfoDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    UserInfoDTO rDTO = (UserInfoDTO) request.getAttribute("rDTO");

    String newPassword = CmmUtil.nvl((String) session.getAttribute("NEW_PASSWORD"));

    String msg = "";

    if (CmmUtil.nvl(rDTO.getUserId()).length() > 0) {
        if (newPassword.length() == 0) {
            msg = "비정상적인 접근입니다. \n비밀번호 재설정 화면에 접근할 수 없습니다.";
        }
    } else {
        msg = "회원정보가 존재하지 않습니다";
    }
%>
<!doctype html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>비밀번호 찾기</title>

    <link rel="stylesheet" href="/css/table.css">
    <script type="text/javascript" src="/js/jquery-3.6.0.min.js"></script>

    <script type="text/javascript">

        let emailAuthValue = "";


        <%
        if (msg.length() > 0) {
        %>
        history.back();
        <%
        }
        %>

        $(document).ready(() => {
            let f = document.getElementById('f');


            $("#btnLogin").on("click", () => {
                location.href = '/user/login';
            })

            $("#btnGetEmailAuth").on("click", () => {
                // let f = document.getElementById('f');

                if (f.email.value === '') {
                    alert("이메일을 입력하세요.");
                    f.email.focus();
                    return;
                }

                $.ajax({
                    url: '/user/getEmailAuthValueInPassword',
                    type: 'post',
                    dataType: 'json',
                    data: $("#f").serialize(),
                    success: (json) => {
                        console.log(json);
                        emailAuthValue = json?.authNumber.toString();
                    }
                });
            })

            $("#btnValidEmailAuth").on('click', () => {

            })

            $("#newPasswordProc").on('click', () => {
                // let f = document.getElementById('f');

                let formInput = f.getElementsByTagName("input")
                console.log(formInput)

                for (const input in formInput) {
                    if(input.value === '') {
                        alert(input + "~~~ 를 입력하세요");
                        input.focus();
                        return
                    }
                }

                if (f.password.value === '') {
                    alert("새로운 비밀번호를 입력하세요.");
                    f.password.focus();
                    return;
                }

                if (f.password2.value === '') {
                    alert("검증을 위한 새로운 비밀번호를 입력하세요.");
                    f.password2.focus();
                    return;
                }

                if (f.password.value !== f.password2.value) {
                    alert("입력한 비밀번호가 일치하지 않습니다.");
                    f.password.focus();
                    return;
                }

                if (f.userName.value === '') {
                    alert("이름을 입력하세요.");
                    f.userName.focus();
                    return;
                }

                if (f.email.value === '') {
                    alert("이메일을 입력하세요.");
                    f.email.focus();
                    return;
                }

                if (f.emailAuth.value !== emailAuthValue) {
                    alert("이메일 인증번호가 동일하지 않습니다.");
                    f.emailAuth.focus();
                    return;
                }

                f.method = 'post';
                f.action = '/user/newPasswordProc'

                f.submit();
            })


        })

        // btnGetEmailAuth
        //
        // btnValidEmailAuth
    </script>

</head>
<body>
<h2><%=CmmUtil.nvl(rDTO.getUserName())%>님의 비밀번호 재설정</h2>
<hr/>
<br/>

<form id="f">
    <div class="divTable minimalistBlack">
        <div class="divTableBody">
            <div class="divTableRow">
                <div class="divTableCell">새로운 비밀번호</div>
                <div class="divTableCell">
                    <label for="password"></label><input type="password" name="password" id="password">
                </div>
            </div>

            <div class="divTableRow">
                <div class="divTableCell">검증을 위한 새로운 비밀번호</div>
                <div class="divTableCell">
                    <label for="password2"></label><input type="password" name="password2" id="password2">
                </div>
            </div>




            <div class="divTableRow">
                <div class="divTableCell">아이디</div>
                <div class="divTableCell">
                    <label for="userId"></label><input type="text" name="userId" id="userId">
                </div>
            </div>

            <div class="divTableRow">
                <div class="divTableCell">이름</div>
                <div class="divTableCell">
                    <label for="userName"></label><input type="text" name="userName" id="userName">
                </div>
            </div>

            <div class="divTableRow">
                <div class="divTableCell">이메일</div>
                <div class="divTableCell">
                    <label for="email"></label><input type="email" name="email" id="email">
                </div>

                <div class="divTableCell">
                    <button id="btnGetEmailAuth" type="button">이메일 인증번호 발송!!</button>
                </div>
            </div>


            <div class="divTableRow">
                <div class="divTableCell">인증번호</div>
                <div class="divTableCell">
                    <label for="emailAuth"></label><input type="text" name="emailAuth" id="emailAuth">
                </div>
                <div class="divTableCell">
                    <button id="btnValidEmailAuth" type="button">이메일 인증번호 확인!!</button>
                </div>
            </div>
        </div>
    </div>

    <div>
        <button id="newPasswordProc" type="button">비밀번호 재설정!!!</button>
        <button id="btnLogin" type="button">로그인</button>
    </div>
</form>

</body>
</html>