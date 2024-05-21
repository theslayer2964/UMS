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
  <title><spring:message code="system.title" | <spring:message code="msg.404.title"/> </title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc." name="description" />
  <meta http-equiv="X-UA-Compatible" content="IE=edge" />
  <!-- App favicon -->
  <link rel="shortcut icon" href="<c:url value='/assets/images/favicon.ico'/>">

  <!-- Bootstrap select pluings -->
  <link href="<<c:url value='/assets/libs/bootstrap-select/bootstrap-select.min.css'/>" rel="stylesheet" type="text/css" />

  <!-- App css -->
  <link href="<c:url value='/assets/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css" id="bootstrap-stylesheet" />
  <link href="<c:url value='/assets/css/icons.min.css'/>" rel="stylesheet" type="text/css" />
  <link href="<c:url value='/assets/css/app.min.css'/>" rel="stylesheet" type="text/css"  id="app-stylesheet" />

</head>

<body class="authentication-bg">
<div class="account-pages my-5 pt-5">
  <div class="container">
    <div class="row justify-content-center">
      <div class="col-md-8 col-lg-6">
        <div>

          <div class="text-center authentication-logo mb-4">
            <a href="index.html" class="logo-dark">
              <span><img src="<c:url value='/assets/images/logo.png'/>" alt="" height="30"></span>
            </a>
          </div>

          <div class="text-center">
            <img src="<c:url value='/assets/images/icons/high_priority.svg'/>" alt="high_priority.svg" height="60">
            <h2 class="text-uppercase text-primary mt-4"><spring:message code="msg.404.content"/> </h2>

            <a href="<c:url value='/index.html'/>" class="btn btn-primary waves-effect waves-light mt-4"> <spring:message code="button.return_home"/></a>
          </div>

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

</body>
</html>
