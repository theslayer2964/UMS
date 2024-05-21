<%--
  Created by IntelliJ IDEA.
  User: phaolo
  Date: 2020-03-17
  Time: 05:04
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp"%>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8" />
  <title><spring:message code="system.title"/> | <spring:message code="msg.500.title"/></title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc." name="description" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <!-- App favicon -->
  <link rel="shortcut icon" href="<c:url value='/assets/images/favicon.ico'/>">

  <!-- Bootstrap select pluings -->
  <link href="<c:url value='/assets/libs/bootstrap-select/bootstrap-select.min.css'/>" rel="stylesheet" type="text/css" />

  <!-- App css -->
  <link href="<c:url value='/assets/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css" id="bootstrap-stylesheet" />
  <link href="<c:url value='/assets/css/icons.min.css'/>" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/assets/css/app.min.css'/>" rel="stylesheet" type="text/css"  id="app-stylesheet" />

</head>

<body class="authentication-bg" style="background-color: #ffffff" id="body">
<div class="account-pages my-5 pt-5">
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-8 col-lg-6">
        <div class="text-center authentication-logo mb-4">
          <a href="/" class="logo-dark">
            <span><img src="<c:url value='/assets/images/logo.png'/>" alt="" height="30"></span>
          </a>
        </div>

        <div class="text-center">
          <img src="<c:url value='/assets/images/icons/globe.svg'/>" alt="globe.svg" height="60">
          <h1 class="display-3 mt-4" id="title"><spring:message code="msg.500.title"/></h1>
          <p class="text-muted mt-4"><spring:message code="msg.500.content"/></p>

          <a href="<c:url value='/'/>" class="btn btn-primary waves-effect waves-light mt-3"><spring:message code="button.return_home"/> </a>
        </div>
        <!-- end card -->

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
  $(document).ready(function() {
    const LOCAL_STORAGE_KEY = "toggle-bootstrap-theme";
    if (localStorage.getItem(LOCAL_STORAGE_KEY) !== null) {
      const LOCAL_META_DATA = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY));
      let isDark = LOCAL_META_DATA && LOCAL_META_DATA.isDark;
      let body = document.getElementById('body');
      let title = document.getElementById('title');
      if (isDark) {
        body.setAttribute("style", "background-color: #232a36");
        title.setAttribute("style", "color: #ffffff");
      } else {
        body.setAttribute("style", "background-color: #ffffff");
        title.setAttribute("style", "color: #323a46");
      }
    }
  });
</script>
</body>
</html>