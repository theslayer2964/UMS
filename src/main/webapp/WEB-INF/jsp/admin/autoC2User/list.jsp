<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 1/31/2024
  Time: 8:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/list/user_c2_auto.html"/>
<html>
<head>
    <title><spring:message code="menu.system.c2_user_auto"/></title>
</head>
<body>
<div class="container-fluid">
    <div class="row">
        <div class="col-12">
            <div class="page-title-box">
                <div class="page-title-right">
                    <ol class="breadcrumb m-0">
                        <li class="breadcrumb-item"><a href="javascript: void(0);"><spring:message
                                code="menu.system"/></a></li>
                        <li class="breadcrumb-item active"><spring:message code="menu.system.c2_user_auto"/></li>
                    </ol>
                </div>
                <h4 class="page-title"><spring:message code="menu.system.c2_user_auto"/></h4>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <div class="header-title sub-header">
                        <button class="btn btn-warning waves-effect waves-light" type="button">
                            <a href="<c:url value='/admin/add_user_c2.html'/>"><spring:message code="button.add"/></a>
                        </button>
                    </div>

                    <form:form id="listForm" modelAttribute="item" action="${formURL}" method="post">
                        <div class="table-responsive">
                            <table id="datatable-buttons" class="table table-bordered"
                                   style="border-collapse: collapse; border-spacing: 0;width: 190%;overflow-x: scroll">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th style="width: 50px;"><spring:message code="label.action"/></th>
                                    <th style="width: 150px;"><spring:message code="label.name"/></th>
                                    <th style="width: 150px;"><spring:message code="label.c2user.username"/></th>
                                    <th style="width: 200px;"><spring:message code="label.c2user.tothu"/></th>
                                    <th style="width: 200px;"><spring:message code="label.c2user.shopCode"/></th>
                                    <th style="width: 200px;"><spring:message code="label.c2user.ip"/></th>
                                    <th style="width: 150px;"><spring:message code="label.c2user.schedular"/></th>
                                    <th><spring:message code="label.c2user.program"/></th>
                                    <th><spring:message code="label.c2user.birthday"/></th>
                                    <th><spring:message code="label.c2user.cmnd"/></th>
                                    <th><spring:message code="label.c2user.ngaycap"/></th>
                                    <th><spring:message code="label.c2user.noicap"/></th>
                                    <th><spring:message code="label.status"/></th>
                                    <th><spring:message code="label.c2user.employeeType"/></th>
                                    <th><spring:message code="label.c2user.city"/></th>
                                    <th><spring:message code="label.c2user.district"/></th>
                                    <th><spring:message code="label.c2user.phone"/></th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${items.listResult}" var="r" varStatus="loop">
                                    <tr>
                                        <td class="table-number" width="2%">${loop.index+1}</td>
                                        <td class="table-action" width="8%">
                                            <a class="btn btn-icon waves-effect waves-light btn-warning btn-sm">
                                                <i class="fas fa-wrench"></i>
                                            </a>
                                            <a class="btn btn-icon waves-effect waves-light btn-danger btn-sm"><i
                                                    class="fas fa-times"></i></a>
                                        </td>
                                        <td>${r.full_name}</td>
                                        <td>${r.user_name}</td>
                                        <td>${r.toThu}</td>
                                        <td>${r.shop_code}</td>
                                        <td>${r.granted_ip}</td>
                                        <td>${r.access_schedule}</td>
                                        <td>${r.program}</td>
                                        <td>${r.birthday}</td>
                                        <td>${r.cmnd}</td>
                                        <td>${r.ngaycap}</td>
                                        <td>${r.noicap}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${r.status == 0}">
                                                    <spring:message code="label.c2user.passive"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <spring:message code="label.c2user.active"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${r.type == 0}">
                                                    <spring:message code="label.user.cskh.nvbh_tt"/>
                                                </c:when>
                                                <c:when test="${r.type == 1}">
                                                    <spring:message code="label.user.cskh.nvpt_ch"/>
                                                </c:when>
                                                <c:when test="${r.type == 2}">
                                                    <spring:message code="label.user.cskh.nv_am"/>
                                                </c:when>
                                                <c:when test="${r.type == 3}">
                                                    <spring:message code="label.user.cskh.nv_kam"/>
                                                </c:when>
                                                <c:when test="${r.type == 4}">
                                                    <spring:message code="label.user.cskh.gdv"/>
                                                </c:when>
                                                <c:when test="${r.type == 5}">
                                                    <spring:message code="label.user.cskh.nvbh_ld"/>
                                                </c:when>
                                                <c:otherwise>
                                                    <spring:message code="label.user.cskh.nvdbh_ddv"/>
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>${r.city}</td>
                                        <td>${r.district}</td>
                                        <td>${r.phone}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- end .table-responsive-->
                        <input type="hidden" name="crudaction" value="delete"/>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    // $(document).ready(function () {
    //     new DataTable('#datatable-buttons', {
    //         scrollX: true
    //     });
    // });
</script>
</body>
</html>
