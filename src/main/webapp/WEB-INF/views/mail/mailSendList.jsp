<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ page import="java.util.List" %>
<%@ page import="kopo.poly.dto.NoticeDTO" %>
<%@ page import="kopo.poly.util.CmmUtil" %>
<%@ page import="kopo.poly.dto.MailDTO" %>
<%@ page import="java.time.format.DateTimeFormatter" %>
<%--<%@ page import="org.springframework.ui.ModelMap" %>--%>

<%
    // NoticeController 함수에서 model 객체에 저장된 값 불러오기
    // TODO 여기선 왜 request 로 받아옴? model 에 넣어줬는데
    List<MailDTO> rList = (List<MailDTO>) request.getAttribute("rList");

%>
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
        /**
         * 여기서 그냥 싹다 ajax로 조져버리고 싶음
         * 그러려면 react 같은거 써서 계속 컴포넌트, div 같은 태그를 늘려주는 프레임워크를 써야하는거 아님?
         * 동적으로 페이지 생성해주는거니까
         * 우리가 타임리프나 jsp 같은거 써가지고 페이지 만드는거는 그냥 단순히 동적인 페이지를 db에서 값 꺼내와서 보여주는 거니까
         * 프론트단에서 동적으로 처리해주고 싶으면 react 같은거 써야될듯?
         */
        // $(function () {
        //     $("#btnSend").on("click", function () {
        //         $.ajax({
        //             url: "/mail/sendMail",
        //             type: 'post',
        //             dataType: 'json',
        //             data: $("#f").serialize(),
        //             success: function (data) {
        //                 alert(data.msg);
        //             }
        //         })
        //     })
        // })
    </script>
</head>
<body>
<h2>메일 작성하기</h2>
<hr />
<br />
<%--<form id="f">--%>
<%--    <div class="divTable minimalistBlack">--%>
<%--        <div class="divTableBody">--%>
<%--            <div class="divTableRow">--%>
<%--                <div class="divTableCell">받는사람</div>--%>
<%--                <div class="divTableCell">--%>
<%--                    <input type="text" name="toMail" maxlength="100" style="width: 95%;" />--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <div class="divTableRow">--%>
<%--                <div class="divTableCell">메일제목</div>--%>
<%--                <div class="divTableCell">--%>
<%--                    <input type="text" name="title" maxlength="100" style="width: 95%;" />--%>
<%--                </div>--%>
<%--            </div>--%>
<%--            <div class="divTableRow">--%>
<%--                <div class="divTableCell">메일내용</div>--%>
<%--                <div class="divTableCell">--%>
<%--                    <input type="text" name="contents" maxlength="100" style="width: 95%;" />--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--        <div>--%>
<%--            <button id="btnSend" type="button">메일 발송</button>--%>
<%--            <button type="reset">다시 작성</button>--%>
<%--        </div>--%>
<%--    </div>--%>
<%--</form>--%>
<div class="divTable minimalistBlack">
    <div class="divTableHeading">
        <div class="divTableRow">
            <div class="divTableHead">발송 순번</div>
            <div class="divTableHead">받는 사람</div>
            <div class="divTableHead">메일 제목</div>
            <div class="divTableHead">메일 내용</div>
            <div class="divTableHead">발송 시간</div>
        </div>
    </div>
    <div class="divTableBody">
        <%
            for (MailDTO dto : rList) {
        %>
        <div class="divTableRow">
            <%
//                if (CmmUtil.nvl(dto.getNoticeYn()).equals("Y")) { //공지글이라면, [공지]표시
//                    out.print("<div class=\"divTableCell\">공지</div>");
//
//                } else { //공지글이 아니라면, 글번호 보여주기
//                    out.print("<div class=\"divTableCell\">" + CmmUtil.nvl(dto.getNoticeSeq()) + "</div>");
//
//                }
                out.print("<div class=\"divTableCell\">" + CmmUtil.nvl(dto.getMailSeq()) + "</div>");
            %>
            <div class="divTableCell"
                 onclick="doDetail('<%=CmmUtil.nvl(dto.getMailSeq())%>')"><%=CmmUtil.nvl(dto.getToMail())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getTitle())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(dto.getContents())%>
            </div>
            <div class="divTableCell"><%=CmmUtil.nvl(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss").format(dto.getSendTime()))%>
<%--                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format()--%>
            </div>
        </div>
        <%
            }
        %>
    </div>
</div>
</body>
</html>