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
    <div class="modal-body">
        <div class="form-group">
            <label for="reportId" class="col-form-label">帳號查詢：</label>
            <input type="text" class="form-control" id="reportId" name="reportId">
            <input type="submit" class="btn btn-info" value="搜尋">
        </div>
    </div>
    <div class="container-fluid">
        <div class="col-sm-13">
            <div class="panel panel-info">
                <div class="panel-heading"><h4><span class="glyphicon glyphicon-user"></span></h4></div>
                <div class="panel-body">
                    <table class="table table-bordered">
                        <thead>
                        <tr>
                            <th colspan="5" class="text-center">帳戶管理</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <th class="text-center">姓名</th>
                            <th class="text-center">性別</th>
                            <th class="text-center">地址</th>
                            <th class="text-center">生日</th>
                            <th class="text-center">身分證字號</th>
                        </tr>
                        <c:forEach items="${memberList}" var="member" varStatus="status">
                            <tr class="text-center">
                                <td>${member.getIdNumber()}</td>
                                <td>${member.getChineseName()}</td>
                                <td>${member.getPhone()}</td>
                                <td>${member.getEmail()}</td>
                                <button type='button' data-toggle='modal' data-target='#insert' class='btn btn-primary' style="background-color: rgba(255,255,255,0.51);width:100px;height:50px;border-color:#000000;">修改</button>
                            </tr>
                        </c:forEach>
                        <c:if test = "${fn:length(memberList) == 0}">
                            <tr class="text-center">
                                <td colspan="5">查無資料</td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                    <button type='button' data-toggle='modal' data-target='#insert' class='btn btn-primary' style="background-color: rgba(255,255,255,0.51);width:100px;height:50px;border-color:#000000;">新增帳戶</button>
                </div>
            </div>
        </div>
</div><!-- /container -->
<form name="myForm" action="accountManage" method="POST">
    <div class="modal fade" id="insert" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="title">新增帳戶</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="account" class="col-form-label">帳號：</label>
                        <input type="text" class="form-control" id="account" name="account" value="${latestId}" required>
                        <label for="chinese_name" class="col-form-label">姓名：</label>
                        <input type="text" class="form-control" id="chinese_name" name="chinese_name" required>
                        <label for="sexual" class="col-form-label">性別：</label>
                        <input type="text" class="form-control" id="sexual" name="sexual" required>
                        <label for="address" class="col-form-label">地址：</label>
                        <input type="text" class="form-control" id="address" name="address" required>
                        <label for="birthday" class="col-form-label">生日：</label>
                        <input type="text" class="form-control" id="birthday" name="birthday" required>
                        <label for="id" class="col-form-label">身分證字號：</label>
                        <input type="text" class="form-control" id="id" name="id" required>
                        <label for="priority" class="col-form-label">權限：</label>
                        <select class="form-control" id="priority" name="priority">
                            <option value="1">1：doctor</option>
                            <option value="2">2：patient</option>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">取消</button>
                    <input type="submit" class="btn btn-info" value="新增">
                </div>
            </div>
        </div>
    </div>
</form>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/browser.min.js"></script>
    <script src="assets/js/breakpoints.min.js"></script>
    <script src="assets/js/util.js"></script>
    <script src="assets/js/main.js"></script>
</body>
</html>
