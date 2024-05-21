<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 4/22/2024
  Time: 3:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/utilities/user_c2_statistical.html"/>
<html>
<head>
    <title><spring:message code="menu.guest.info"/></title>
</head>
<body>
<div class="container-fluid">
    <%--    title: --%>
    <div class="row">
        <div class="col-12">
            <div class="page-title-box">
                <div class="page-title-right">
                    <ol class="breadcrumb m-0">
                        <li class="breadcrumb-item"><a href="javascript: void(0);"><spring:message
                                code="menu.system"/></a></li>
                        <li class="breadcrumb-item active"><spring:message code="menu.guest.info"/></li>
                    </ol>
                </div>
                <h4 class="page-title"><spring:message code="menu.guest.info"/></h4>
            </div>
        </div>
    </div>
</div>
</body>
</html>
