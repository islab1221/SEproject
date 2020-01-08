<%--
  Created by IntelliJ IDEA.
  User: Satellite
  Date: 2018/11/10
  Time: 下午 09:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav class="navbar navbar-expand-sm bg-primary navbar-dark fixed-top" style="width: 100%;height: 60px">
    <ul class="navbar-nav">
        <div class="nav-item" style="position:absolute;">
            <a class="nav-link" href="doctorMain">回首頁</a>
        </div>
        <div class="nav-item" style="position:absolute;right:0px;">
            <a class="nav-link" href="logout">登出/醫生</a>
        </div>
    </ul>
</nav>
    <%--
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="adminMain">Medical Record System</a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="adminMain">首頁</a></li>
            <li class="dropdown">
                <a href="accountManage">帳戶管理</a>
            </li>
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="#"> 帳戶搜尋<span class="caret"></span></a>
                <ul class="dropdown-menu dropdown-menu-right">
                    <li>
                        <label for="exampleDropdownFormEmail1">欲搜尋帳戶ID</label>
                        <input type="email" class="form-control" id="exampleDropdownFormEmail1" placeholder="10700002">
                        <button type="submit" class="btn btn-primary">開始搜尋</button>
                    </li>
                </ul>
            </li>
        </ul>

        <ul class="nav navbar-nav navbar-right">
            <li class="dropdown">
                <a class="dropdown-toggle glyphicon glyphicon-user" data-toggle="dropdown" href="#"> ${account.getChineseName()}
                    <span class="caret"></span>
                </a>
                <ul class="dropdown-menu dropdown-menu-right">
                    <li><a href="userInformation">帳戶管理</a></li>
                    <li><a href="#">修改密碼</a></li>
                </ul>
            </li>
            <li><a href="logout"><span class="glyphicon glyphicon-log-in"></span> 登出</a></li>
        </ul>
    </div>--%>
</nav>
