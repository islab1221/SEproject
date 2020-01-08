<%--
  Created by IntelliJ IDEA.
  User: bao
  Date: 2019/9/18
  Time: 下午 05:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Medical Record System</title>
<%--    <link rel="stylesheet" href="resources/css/bootstrap.min.css">--%>
<%--    <link rel="stylesheet" type="text/css" href="resources/css/login.css">--%>
    <meta name="viewport" content="width=device-width, initial-scale=1, user-scalable=no"/>
    <link rel="stylesheet" href="assets/css/main.css"/>
    <noscript><link rel="stylesheet" href="assets/css/noscript.css"/></noscript>
</head>
<%--<body>--%>
<%--<div class="container">--%>
<%--    <div class="card card-container">--%>
<%--        <img id="profile-img" class="profile-img-card" src="resources/image/medical_record.png"/>--%>
<%--        <p id="profile-name" class="profile-name-card"></p>--%>
<%--        <form class="form-signin" action="" method="POST">--%>
<%--            <span id="reauth-email" class="reauth-email"></span>--%>
<%--            <input type="text" id="inputID" name="inputID" class="form-control" placeholder="帳號" required autofocus>--%>
<%--            <input type="password" id="inputPassword" name="inputPassword" class="form-control" placeholder="密碼" >--%>
<%--            <p style="color:red;"> ${error}</p>--%>
<%--            <div id="remember" class="checkbox">--%>
<%--            </div>--%>
<%--            <button class="btn btn-lg btn-primary btn-block btn-signin" type="submit">登入</button>--%>
<%--        </form>--%>
<%--    </div><!-- /card-container -->--%>

<%--</div><!-- /container -->--%>
<body class="is-preload">
<div id="wrapper">
    <section id="main">
        <header>
            <span class="avatar"><img src="resources/image/medical_record.png" alt="" width="200" height="200" /></span>
            <h1>Login</h1>
        </header>
        <hr />
        <form action="login" method="POST">
            <div class="fields">
                <div class="field">
                    <input type="text" name="inputID" id="inputID" placeholder="User Name" required/>
                </div>
                <div class="field">
                    <input type="password" name="inputPassword" id="inputPassword" placeholder="Password" required/>
                </div>
            </div>
            <button class="actions special" type="submit">登入</button>
        </form>
        <hr />
    </section>
</div>
<script>
    if ('addEventListener' in window) {
        window.addEventListener('load', function() { document.body.className = document.body.className.replace(/\bis-preload\b/, ''); });
        document.body.className += (navigator.userAgent.match(/(MSIE|rv:11\.0)/) ? ' is-ie' : '');
    }
</script>
</body>

<%--</body>--%>
</html>