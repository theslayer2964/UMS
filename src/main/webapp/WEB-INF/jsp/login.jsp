<%--
  Created by IntelliJ IDEA.
  User: phaolo
  Date: 2020-03-10
  Time: 09:52
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8"/>
    <title><spring:message code="system.title"/> | <spring:message code="system.login"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc." name="description"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <!-- App favicon -->
    <link rel="shortcut icon" href="<c:url value='/assets/images/favicon.ico'/>">

    <!-- Bootstrap select pluings -->
    <link href="<c:url value='/assets/libs/bootstrap-select/bootstrap-select.min.css'/>" rel="stylesheet"
          type="text/css"/>

    <!-- App css -->
    <link href="<c:url value='/assets/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css"
          id="bootstrap-stylesheet"/>
    <link href="<c:url value='/assets/css/icons.min.css'/>" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/assets/css/app.min.css'/> " rel="stylesheet" type="text/css" id="app-stylesheet"/>

</head>

<body class="authentication-bg" style="background-color: #ffffff" id="body">
<div class="account-pages my-5 pt-5">
    <div class="container">
        <div class="row justify-content-center">
            <div class="col-md-8 col-lg-6 col-xl-5">
                <div>

                    <div class="text-center authentication-logo mb-4">
                        <a href="/" class="logo-dark">
                            <span><img src="<c:url value='/assets/images/logo.png'/>" alt="" height="50"></span>
                            <span><img id="mPortalTitle" src="<c:url value='/assets/images/Title_mPortal_dark.png'/>"
                                       alt="" height="50"></span>
                        </a>
                    </div>

                    <form name='login-form' action="/j_spring_security_login" method='POST' id="loginForm">

                        <div class="form-group mb-3">
                            <label for="username"><spring:message code="label.username"/> </label>
                            <input class="form-control" type="text" name="username" id="username" required="required">
                        </div>

                        <%--            <a href="page-recoverpw.html" class="text-muted float-right"><spring:message code="label.system.forget_password"/>?</a>--%>

                        <div class="form-group mb-3">
                            <label for="password"><spring:message code="label.password"/> </label>
                            <input class="form-control" type="password" name="password" required="required"
                                   id="password">
                        </div>

                        <%--            <div class="form-group mb-3">--%>
                        <%--              <div class="custom-control custom-checkbox">--%>
                        <%--                <input type="checkbox" class="custom-control-input" id="checkbox-signin" checked>--%>
                        <%--                <label class="custom-control-label" for="checkbox-signin"><spring:message code="label.system.remember_me"/></label>--%>
                        <%--              </div>--%>
                        <%--            </div>--%>

                        <div class="form-group text-center mb-3">
                            <button class="btn btn-primary btn-lg width-lg btn-rounded" type="submit"><spring:message
                                    code="button.login"/></button>
                            <button class="btn btn-success btn-lg width-lg btn-rounded" onclick="signinAsGuest()">
                                <spring:message
                                        code="button.signin"/></button>
                        </div>

                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>

                </div>
                <!-- end card -->

                <%--        <div class="row">--%>
                <%--          <div class="col-sm-12 text-center">--%>
                <%--            <p class="text-muted">Don't have an account? <a href="page-register.html" class="text-dark ml-1">Sign Up</a></p>--%>
                <%--          </div>--%>
                <%--        </div>--%>
                <!-- end row -->

            </div> <!-- end col -->
        </div>
        <!-- end row -->
    </div>
    <!-- end container -->
</div>
<!-- end page -->


<!-- Vendor js -->
<script src="<c:url value='/assets/js/vendor.min.js'/>"></script>

<!-- Bootstrap select plugin -->
<script src="<c:url value='/assets/libs/bootstrap-select/bootstrap-select.min.js'/>"></script>

<!-- App js -->
<script src="<c:url value='/assets/js/app.min.js'/>"></script>

<script>
    $(document).ready(function () {
        const LOCAL_STORAGE_KEY = "toggle-bootstrap-theme";
        if (localStorage.getItem(LOCAL_STORAGE_KEY) !== null) {
            const LOCAL_META_DATA = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY));
            let isDark = LOCAL_META_DATA && LOCAL_META_DATA.isDark;
            let body = document.getElementById('body');
            let title = document.getElementById('mPortalTitle');
            if (isDark) {
                body.setAttribute("style", "background-color: #232a36");
                title.setAttribute("src", "<c:url value='/assets/images/Title_mPortal.png'/>")
            } else {
                body.setAttribute("style", "background-color: #ffffff");
                title.setAttribute("src", "<c:url value='/assets/images/Title_mPortal_dark.png'/>")
            }
        }
    });

    var form = document.getElementById('loginForm');
    var username = document.getElementById('username');
    var password = document.getElementById('password');


    function signinAsGuest() {
        username.value = 'Register';
        password.value = 'tt2'
        form.submit();
    }
</script>
</body>
</html>


