<%@include file="/common/taglibs.jsp"%>
<c:url var="formURL" value="/admin/group_role_acl.html" />
<html>
<head>
    <title><spring:message code="label.user_role.role"/> </title>
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
                        <li class="breadcrumb-item active"><a href="usergroup-list.html"><spring:message code="menu.system.user_group"/></a></li>
                        <li class="breadcrumb-item active"><spring:message code="label.user_role.role"/></li>
                    </ol>
                </div>
                <h4 class="page-title">
                    <spring:message code="label.user_role.role"/> &nbsp;
                    <small>${item.pojo.userGroup.name } (${item.pojo.userGroup.code })</small>
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
                        <a href="usergroup-list.html" class="fa fa-arrow-left btn btn-primary waves-effect waves-light" style="line-height: 1.5 !important;">
                            <spring:message code="button.back"/>
                        </a>
                        <button class="btn btn-warning waves-effect waves-light" data-toggle="modal" data-target="#myModal"><spring:message code="button.add"/> </button>
                        <%@include file="insert.jsp"%>
                    </div>
                    <form:form id="listForm" modelAttribute="item" action="${formURL}" method="post">
                        <div class="table-responsive">
                            <table id="datatable-buttons" class="table table-bordered"  style="border-collapse: collapse; border-spacing: 0; width: 100%;">
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
                                        <td width="30px" class="table-number">${loop.index + 1}</td>
                                        <td width="80px" class="table-action">
                                            <a class="btn btn-icon waves-effect waves-light btn-danger btn-sm"
                                               onclick="javascript: deleteForm('${r.userGroupRoleACLId}','${r.userGroup.userGroupId}')"><i class="fas fa-times"></i></a>
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
    var id = null;
    $(document).ready(function() {
        <!-- Modal initilization -->
        <c:if test="${not empty isModal}">
        $('#myModal').modal('show');
        </c:if>

        $('#myModal').on('hidden.bs.modal', function () {
            $("#error_select").remove();
        });
    });

    function deleteForm(userGroupRoleACLId,userGroupId){
        bootbox.confirm("<fmt:message key='msg.confirm.content'/>", function(result) {
            if (result) {
                $("<input type='hidden' name='pojo.userGroupRoleACLId' value='"+userGroupRoleACLId+"'>").appendTo($("#listForm"));
                $("<input type='hidden' name='pojo.userGroup.userGroupId' value='"+userGroupId+"'>").appendTo($("#listForm"));
                $("#listForm").submit();
            }
        });
    };

</script>
</body>
</html>

