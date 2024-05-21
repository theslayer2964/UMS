<%--
  Created by IntelliJ IDEA.
  User: phaolo
  Date: 2020-03-18
  Time: 05:14
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp"%>
<c:url var="formURL" value="/admin/user_role_acl.html" />
<html>
<head>
    <title><spring:message code="menu.system.user.acl"/> </title>
</head>
<body>
<div class="container-fluid">

    <!-- start page title -->
    <div class="row">
        <div class="col-12">
            <div class="page-title-box">
                <div class="page-title-right">
                    <ol class="breadcrumb m-0">
                        <li class="breadcrumb-item"><a href="#"><spring:message code="menu.system"/></a></li>
                        <li class="breadcrumb-item active"><spring:message code="menu.system.user"/></li>
                        <li class="breadcrumb-item active"><spring:message code="menu.system.user.acl"/></li>
                    </ol>
                </div>
                <h4 class="page-title">
                    <spring:message code="menu.system.user.acl"/>&nbsp;
                    <small>${item.pojo.user.displayName } (${item.pojo.user.userName })</small>
                </h4>
            </div>
        </div>
    </div>
    <!-- end page title -->
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <div class="header-title sub-header">
                        <a href="user-list.html" class="fa fa-arrow-left btn btn-primary waves-effect waves-light" style="line-height: 1.5 !important;">
                            <spring:message code="button.back"/>
                        </a>
                        <button class="btn btn-warning waves-effect waves-light" data-toggle="modal" data-target="#myModal">
                            <spring:message code="button.add"/>
                        </button>
                        <%@include file="insert.jsp"%>
                    </div>
                </div>
                <form:form id="listForm" modelAttribute="item" action="${formURL}" method="post">
                    <div class="table-responsive">
                        <table id="datatable-buttons" class="table table-bordered" style="border-collapse: collapse; border-spacing: 0; width: 100%;">
                            <thead>
                                <tr>
                                    <th>#</th>
                                    <th><spring:message code="label.action"/></th>
                                    <th><spring:message code="userrole.code"/> </th>
                                    <th><spring:message code="userrole.name"/></th>
                                    <th><spring:message code="label.description"/></th>
                                </tr>
                            </thead>
                            <tbody>
                                <c:forEach items="${items.listResult }" var="r" varStatus="loop">
                                    <tr>
                                        <td width="30px" class="table-number">${loop.index+1}</td>
                                        <td width="80px" class="table-action">
                                            <a class="btn btn-icon waves-effect waves-light btn-danger btn-sm" id="${r.userRoleACLsId }"
                                               onclick="javascript: deleteForm('${r.userRoleACLsId}','${r.user.userId}')"><i class="fas fa-times"></i></a>
                                        </td>
                                        <td width="200px">${r.userRole.code}</td>
                                        <td width="300px">${r.userRole.name}</td>
                                        <td>${r.userRole.description}</td>
                                    </tr>
                                </c:forEach>
                            </tbody>
                        </table>
                    </div> <!-- end .table-responsive-->
                    <input type="hidden" name="crudaction" value="delete" />
                </form:form>
                </div> <!-- end card-body -->
            </div> <!-- end card -->
        </div> <!-- end col -->
    </div> <!-- end row -->
</div>
<script>
    $(document).ready(function() {
        <!-- Modal initilization -->
        <c:if test="${not empty isModal}">
        $('#myModal').modal('show');
        </c:if>

        $('#myModal').on('hidden.bs.modal', function () {
            $('input').removeClass('parsley-error');
            $("#error_code").remove();
            $("#error_path").remove();

        });
    });

    function deleteForm(userRoleACLId, userId){
        bootbox.confirm("<fmt:message key='msg.confirm.content'/>", function(result) {
            if (result) {
                $("<input type='hidden' name='pojo.userRoleACLsId' value='"+userRoleACLId+"'>").appendTo($("#listForm"));
                $("<input type='hidden' name='pojo.user.userId' value='"+userId+"'>").appendTo($("#listForm"));
                $("#listForm").submit();
            }
        });
    }
</script>
</body>
</html>

