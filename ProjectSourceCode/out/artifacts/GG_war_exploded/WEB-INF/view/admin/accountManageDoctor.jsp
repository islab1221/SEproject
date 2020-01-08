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
<form name="myForm" action="accountManageDoctor">
    <div class="modal-body">
        <div class="form-group">
            <label for="reportId" class="col-form-label">帳號查詢：</label>
            <input type="text" class="form-control" id="reportId" name="reportId">
        </div>

        <input type="submit" class="btn btn-info"  style="background-color: rgba(255,255,255,0.51);width:100px;height:50px;border-color:#000000;" value="搜尋">
    </div>
</form>

<div class="container-fluid">
    <div class="col-sm-13">
        <div class="panel panel-info">
            <div class="panel-heading"><h4><span class="glyphicon glyphicon-user"></span></h4></div>
            <div class="panel-body">
                <table class="table table-bordered">
                    <thead>
                    <tr>
                        <th colspan="5" class="text-center">醫生帳戶管理</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th class="text-center">帳號</th>
                        <th class="text-center">姓名</th>
                        <th class="text-center">性別</th>
<%--                        <th class="text-center">地址</th>--%>
<%--                        <th class="text-center">生日</th>--%>
<%--                        <th class="text-center">身分證字號</th>--%>
                        <th class="text-center">醫事機構</th>
                        <th class="text-center">科別</th>

                    </tr>
                    <c:if test = "${v.size() == 0}">
                        <tr class="text-center">
                            <td colspan="5">查無資料</td>
                        </tr>
                    </c:if>
                    <c:if test="${v.size() > 0}">
                        <c:forEach var="count" begin="0" end="${v.size()-1}">
                            <tr class="text-center">
                                <td class="text-center">${id_account.elementAt(count)}</td>
                                <td class="text-center">${v.elementAt(count).getName().get(0).getText()}</td>
                                <td class="text-center">${v.elementAt(count).getGender()}</td>
<%--                                <td class="text-center">${v.elementAt(count).getAddress().get(0).getText()}</td>--%>
<%--                                <td class="text-center">${birthday.elementAt(count)}</td>--%>
<%--                                <td class="text-center">${v.elementAt(count).getIdentifier().get(0).getValue()}</td>--%>
                                <td class="text-center">${v.elementAt(count).getQualification().get(0).getIdentifier().get(0).getValue()}</td>
                                <td class="text-center">${v.elementAt(count).getQualification().get(0).getCode().getText()}</td>
                            </tr>
                        </c:forEach>
                    </c:if>
                    </tbody>
                </table>
                <button type='button' data-toggle='modal' data-target='#insertDoctor' class='btn btn-primary' style="background-color: rgba(255,255,255,0.51);width:100px;height:50px;border-color:#000000;">新增醫生</button>
                <button type='button' data-toggle='modal' data-target='#deleteDoctor' class='btn btn-primary' style="background-color: rgba(255,255,255,0.51);width:100px;height:50px;border-color:#000000;">刪除醫生</button>
            </div>
        </div>
    </div>
</div><!-- /container -->
<form name="myForm" action="accountManageDoctor" method="post">
    <div class="modal fade" id="insertDoctor" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="titleDoctor">新增醫生帳戶</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="accountDoctor" class="col-form-label">帳號：</label>
                        <input type="text" class="form-control" id="accountDoctor" name="accountDoctor" required>
                        <label for="nameDoctor" class="col-form-label">姓名：</label>
                        <input type="text" class="form-control" id="nameDoctor" name="nameDoctor" required>
                        <label for="sexualDoctor" class="col-form-label">性別：</label>
                        <select class="form-control" id="sexualDoctor" name="sexualDoctor">
                            <option value="男">1：男</option>
                            <option value="女">2：女</option>
                        </select>
                        <label for="addressDoctor" class="col-form-label">地址：</label>
                        <input type="text" class="form-control" id="addressDoctor" name="addressDoctor" required>
                        <label for="birthdayDoctor" class="col-form-label">生日：</label>
                        <input type="date" class="form-control" id="birthdayDoctor" name="birthdayDoctor" required>
                        <label for="idDoctor" class="col-form-label">身分證字號：</label>
                        <input type="text" class="form-control" id="idDoctor" name="idDoctor" required>
                        <label for="hospital" class="col-form-label">醫事機構：</label>
                        <select class="form-control" id="hospital" name="hospital">
                            <option value="臺大醫院">臺大醫院</option>
                            <option value="高雄榮總">高雄榮總</option>
                        </select>
                        <label for="department" class="col-form-label">科別：</label>
                        <select class="form-control" id="department" name="department">
                            <option value="泌尿科">泌尿科</option>
                            <option value="內科">內科</option>
                        </select>
                        <label for="priorityDoctor" class="col-form-label">權限：</label>
                        <input type="text" class="form-control" id="priorityDoctor" name="priorityDoctor" readonly="readonly" value="doctor" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="submit" class="btn btn-info" value="新增"><div class="modal-content">
                </div>
                </div>
            </div>
        </div>
    </div>
</form>
<form name="myForm" action="accountManageDoctor" method="POST">
    <div class="modal fade" id="deleteDoctor" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="titledeleteDoctor">刪除醫生帳戶</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="deleteId" class="col-form-label">輸入帳號：</label>
                        <input type="text" class="form-control" id="deleteId" name="deleteId" required>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="submit" class="btn btn-info" value="刪除"><div class="modal-content">
                </div>
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
