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
    <script type="text/javascript">
    //     window.onload = function(){
    // //獲取按鈕的物件
    //         var btn = document.getElementById("addButton");
    // //為按鈕繫結單擊響應函式
    //         btn.onclick = function(){
    // //點選以後使按鈕不可用
    //             this.disabled=true;
    // //當將提交按鈕設定為不可用時，會自動取消它的預設行為
    // //手動提交表單
    //             this.parentNode.submit();
    //         };
    //     };
    </script>
    <form name="myForm" action="doctorReport" method="POST">
        <div class="modal-body" style="position: absolute;left: 20%;right: 20%">
            <div class="form-group">
                <label for="chinese_name" class="col-form-label">病患姓名：</label>
                <input type="text" class="form-control" id="chinese_name" name="chinese_name" required>
                <label for="patientId" class="col-form-label">病患身分證字號：</label>
                <input type="text" class="form-control" id="patientId" name="patientId" required>
                <label for="diseaseName" class="col-form-label">疾病名稱：</label>
                <input type="text" class="form-control" id="diseaseName" name="diseaseName" required>
                <label for="medicalId" class="col-form-label">藥品代碼：</label>
                <input type="text" class="form-control" id="medicalId" name="medicalId" required>
                <label for="medicalName" class="col-form-label">藥品名稱：</label>
                <input type="text" class="form-control" id="medicalName" name="medicalName" required>
                <label for="medicalDay" class="col-form-label">給藥日數：</label>
                <input type="text" class="form-control" id="medicalDay" name="medicalDay" required>
                <label for="medicalAmount" class="col-form-label">藥品用量：</label>
                <input type="text" class="form-control" id="medicalAmount" name="medicalAmount" required>
                <label for="doctorTalk" class="col-form-label">醫生囑咐：</label>
                <input type="text" class="form-control" id="doctorTalk" name="doctorTalk">
            </div>
            <input type="submit" class="btn btn-info" id="addButton" name="addButton" value="新增">
        </div>
        <div class="modal-footer">

        </div>
    </form>
    <script src="assets/js/jquery.min.js"></script>
    <script src="assets/js/browser.min.js"></script>
    <script src="assets/js/breakpoints.min.js"></script>
    <script src="assets/js/util.js"></script>
    <script src="assets/js/main.js"></script>
</body>
</html>
