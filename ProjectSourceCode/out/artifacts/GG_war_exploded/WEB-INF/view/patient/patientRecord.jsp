<%--
  Created by IntelliJ IDEA.
  User: bao
  Date: 2019/11/15
  Time: 上午 10:03
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Medical Record System</title>
    <!-- Latest compiled and minified CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">

    <!-- jQuery library -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>

    <!-- Popper JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"></script>

    <!-- Latest compiled JavaScript -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="resources/css/login.css">
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"/>
    <link rel="stylesheet" href="assets/css/main.css"/>
    <link rel="stylesheet" href="assets/css/noscript.css"/>
</head>
<body>
    <div class="container-fluid">
        <div class="col-sm-13">
            <div class="panel panel-info">
                <div class="panel-heading"><h4><span class="glyphicon glyphicon-user"></span></h4></div>
                <div class="panel-body">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th colspan="4" class="text-center">看診紀錄</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <th class="text-center">就醫日期</th>
                            <th class="text-center">醫事機構</th>
                            <th class="text-center">看診類別</th>
                            <th class="text-center">看診醫師</th>
                        </tr>
                        <c:if test="${bundle.getTotal() > 0}">
                            <c:forEach var="count" begin="0" end="${bundle.getTotal()-1}">
                                <tr class="text-center">
                                    <td>${bundle.getEntry().get(count).getResource().getCode().getText()}</td>
                                    <td>${bundle.getEntry().get(count).getResource().getLocation().getResource().getName()}</td>
                                    <td>${bundle.getEntry().get(count).getResource().getCategory().getText()}</td>
                                    <td>${bundle.getEntry().get(count).getResource().getPerformerFirstRep().getFunction().getText()}</td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test = "${bundle.getTotal() == 0}">
                            <tr class="text-center">
                                <td colspan="4">查無資料</td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
<%--                    <button type='button' data-toggle='modal' data-target='#insert' class='btn btn-primary'>修改</button>--%>
                </div>
            </div>
        </div>
</div><!-- /container -->

    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/browser.min.js"></script>
    <script src="assets/js/breakpoints.min.js"></script>
    <script src="assets/js/util.js"></script>
    <script src="assets/js/main.js"></script>
</body>
</html>
