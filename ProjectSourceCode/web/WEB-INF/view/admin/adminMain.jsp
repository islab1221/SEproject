<%--
  Created by IntelliJ IDEA.
  User: bao
  Date: 2019/11/15
  Time: 下午 03:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Medical Record System</title>
    <!-- Latest compiled and minified CSS -->
<%--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">--%>

<%--    <!-- jQuery library -->--%>
<%--    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>--%>

<%--    <!-- Popper JS -->--%>
<%--    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>--%>

<%--    <!-- Latest compiled JavaScript -->--%>
<%--    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>--%>
<%--    <link rel="stylesheet" type="text/css" href="resources/css/login.css">--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"/>
    <link rel="stylesheet" href="assets/css/main.css"/>
    <link rel="stylesheet" href="assets/css/noscript.css"/>
</head>
<body>
    <jsp:include page="../menuBar/adminTopBar.jsp"></jsp:include>
    <p></p>
    <jsp:include page="../menuBar/adminSideBar.jsp"></jsp:include>
    <div>
        <iframe src="accountManagePatient" name="main" style="width:100%;height: 100%"></iframe>
    </div>
</body>
<script src="assets/js/jquery.min.js"></script>
<script src="assets/js/browser.min.js"></script>
<script src="assets/js/breakpoints.min.js"></script>
<script src="assets/js/util.js"></script>
<script src="assets/js/main.js"></script>
</html>
