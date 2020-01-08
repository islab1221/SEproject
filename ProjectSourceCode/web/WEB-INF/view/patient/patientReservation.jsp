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
    <script>
        function change() {
            var e = document.getElementById("hospital");
            var strUser = e.options[e.selectedIndex].value;
            var e1 = document.getElementById("department");
            var strUser1 = e1.options[e1.selectedIndex].value;



            if(strUser == '臺大醫院'&& strUser1 == '泌尿科'){
                document.getElementById('NTpdoctor').hidden = false;
                document.getElementById('NTindoctor').hidden = true;
                document.getElementById('Kindoctor').hidden = true;
                document.getElementById('Kpdoctor').hidden = true;
            }
            if(strUser == '臺大醫院'&& strUser1 == '內科'){
                document.getElementById('NTpdoctor').hidden = true;
                document.getElementById('NTindoctor').hidden = false;
                document.getElementById('Kindoctor').hidden = true;
                document.getElementById('Kpdoctor').hidden = true;
            }
            if(strUser == '高雄榮總'&& strUser1 == '泌尿科'){
                document.getElementById('NTpdoctor').hidden = true;
                document.getElementById('NTindoctor').hidden = true;
                document.getElementById('Kindoctor').hidden = false;
                document.getElementById('Kpdoctor').hidden = true;
            }
            if(strUser == '高雄榮總'&& strUser1 == '內科'){
                document.getElementById('NTpdoctor').hidden = true;
                document.getElementById('NTindoctor').hidden = true;
                document.getElementById('Kindoctor').hidden = true;
                document.getElementById('Kpdoctor').hidden = false;
            }
        }
    </script>
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
                            <th colspan="8" class="text-center">預約看診</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <th class="text-center">身分證字號</th>
                            <th class="text-center">看診日期</th>
                            <th class="text-center">病例編號</th>
                            <th class="text-center">醫事機構</th>
                            <th class="text-center">看診科別</th>
                            <th class="text-center">看診醫師</th>
                            <th class="text-center">看診序號</th>
                        </tr>
                        <c:if test="${bundle.getTotal() > 0}">
                            <c:forEach var="count" begin="0" end="${bundle.getTotal()-1}">
                                <tr class="text-center">
                                    <td>${bundle.getEntry().get(count).getResource().getIdentifierFirstRep().getValue()}</td>
                                    <td>${format.format(bundle.getEntry().get(count).getResource().getOccurrenceDateTimeType().getValue())}</td>
                                    <td>${bundle.getEntry().get(count).getResource().getIdElement().getIdPart()}</td>
                                    <td>${bundle.getEntry().get(count).getResource().getLocationReferenceFirstRep().getResource().getName()}</td>
                                    <td>${bundle.getEntry().get(count).getResource().getCategoryFirstRep().getText()}</td>
                                    <td>${bundle.getEntry().get(count).getResource().getCode().getText()}</td>
                                    <td>${bundle.getEntry().get(count).getResource().getRequisition().getValue()}</td>
                                </tr>
                            </c:forEach>
                        </c:if>
                        <c:if test = "${bundle.getTotal() == 0}">
                            <tr class="text-center">
                                <td colspan="7">查無資料</td>
                            </tr>
                        </c:if>
                        </tbody>
                    </table>
                    <button type='button' data-toggle='modal' data-target='#insert' class='btn btn-primary' style="background-color: rgba(255,255,255,0.51);width:100px;height:50px;border-color:#000000;">新增預約</button>
                </div>
            </div>
        </div>
</div><!-- /container -->
<form name="myForm" action="patientReservation" method="POST">
    <div class="modal fade" id="insert" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered" role="document">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="title">新增預約</h5>
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                        <span aria-hidden="true">&times;</span>
                    </button>
                </div>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="chinese_name" class="col-form-label">姓名：</label>
                        <input type="text" class="form-control" id="chinese_name" name="chinese_name" required>
                        <label for="id" class="col-form-label">身分證字號：</label>
                        <input type="text" class="form-control" id="id" name="id" required>
                        <label for="date" class="col-form-label">預約日期：</label>
                        <input type="date" class="form-control" id="date" name="date" required>
                        <label for="hospital" class="col-form-label">醫事機構：</label>
                        <select class="form-control" id="hospital" name="hospital" onchange=change()>
                            <option value="臺大醫院">臺大醫院</option>
                            <option value="高雄榮總">高雄榮總</option>
                        </select>
                        <label for="department" class="col-form-label">看診科別：</label>
                        <select class="form-control" id="department" name="department" onchange=change()>
                            <option value="內科">內科</option>
                            <option value="泌尿科">泌尿科</option>
                        </select>
                        <label  class="col-form-label">醫生：</label>
                        <select class="form-control" id="NTindoctor" name="doctor">
                            <c:if test="${NTindoc.size() > 0}">
                                <c:forEach var="count" begin="0" end="${NTindoc.size()-1}">
                                    <option value="${NTindoc.get(count).getName().get(0).getText()}/${NTindoc.get(count).getIdElement().getIdPart()}">${NTindoc.get(count).getName().get(0).getText()}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                        <select class="form-control" id="NTpdoctor" name="NTpdoctor" hidden="true">
                            <c:if test="${NTpdoc.size() > 0}">
                                <c:forEach var="count" begin="0" end="${NTpdoc.size()-1}">
                                    <option value="${NTpdoc.get(count).getName().get(0).getText()}/${NTpdoc.get(count).getIdElement().getIdPart()}">${NTpdoc.get(count).getName().get(0).getText()}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                        <select class="form-control" id="Kindoctor" name="Kindoctor" hidden="true">
                            <c:if test="${Kindoc.size() > 0}">
                                <c:forEach var="count" begin="0" end="${Kindoc.size()-1}">
                                    <option value="${Kindoc.get(count).getName().get(0).getText()}/${Kindoc.get(count).getIdElement().getIdPart()}">${Kindoc.get(count).getName().get(0).getText()}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                        <select class="form-control" id="Kpdoctor" name="Kpdoctor" hidden="true">
                            <c:if test="${Kpdoc.size() > 0}">
                                <c:forEach var="count" begin="0" end="${Kpdoc.size()-1}">
                                    <option value="${Kpdoc.get(count).getName().get(0).getText()}/${Kpdoc.get(count).getIdElement().getIdPart()}">${Kpdoc.get(count).getName().get(0).getText()}</option>
                                </c:forEach>
                            </c:if>
                        </select>
                    </div>
                </div>
                <div class="modal-footer">
                    <input type="submit" class="btn btn-info" value="取消" data-dismiss="modal" aria-label="Close">
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
