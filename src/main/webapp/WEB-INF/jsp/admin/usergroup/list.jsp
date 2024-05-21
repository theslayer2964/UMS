<%--
  Created by IntelliJ IDEA.
  User: phaolo
  Date: 2020-03-18
  Time: 05:14
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp"%>
<c:url var="formURL" value="/admin/usergroup-list.html" />
<html>
<head>
    <title><spring:message code="menu.system.user_group"/> </title>
</head>
<body>
<div class="container-fluid">

    <!-- start page title -->
    <div class="row">
        <div class="col-12">
            <div class="page-title-box">
                <div class="page-title-right">
                    <ol class="breadcrumb m-0">
                        <li class="breadcrumb-item"><a href="javascript: void(0);"><spring:message code="menu.system"/></a></li>
                        <li class="breadcrumb-item active"><spring:message code="menu.system.user_group"/></li>
                    </ol>
                </div>
                <h4 class="page-title"><spring:message code="label.user_group.list"/></h4>
            </div>
        </div>
    </div>
    <!-- end page title -->
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <div class="header-title sub-header">
                        <button class="btn btn-warning waves-effect waves-light" data-toggle="modal" data-target="#myModal"><spring:message code="button.add"/> </button>
                        <%@include file="insert.jsp"%>
                    </div>
                    <form:form id="listForm" modelAttribute="item" action="${formURL}" method="post">
                        <div class="table-responsive">
                            <table id="datatable-buttons" class="table table-bordered"
                                   style="border-collapse: collapse; border-spacing: 0; width: 100%;">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th><spring:message code="label.action"/></th>
                                    <th><spring:message code="label.user_group.code"/> </th>
                                    <th><spring:message code="label.user_group.name"/></th>
                                    <th><spring:message code="label.description"/></th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach items="${items.listResult }" var="r" varStatus="loop">
                                    <tr>
                                        <td width="30px" class="table-number">${loop.index + 1}</td>
                                        <td width="100px" class="table-action">
                                            <a class="btn-icon waves-effect waves-light btn-warning btn-sm" onclick="javascript: editForm('${r.userGroupId}', '${r.code}', '${r.name }', '${r.description}');">
                                                <i class="fas fa-wrench"></i>
                                            </a>
                                            <a class="btn-icon waves-effect waves-light btn-danger btn-sm deleteLink" id="${r.userGroupId }" onclick="javascript: deleteLink(${r.userGroupId});"><i class="fas fa-times"></i></a>
                                            <security:authorize  access="hasAnyRole('ADMIN','FAD_USERGROUPROLEACL')">
                                                <a href="group_role_acl.html?pojo.userGroup.userGroupId=${r.userGroupId }" class="btn-info waves-effect waves-light btn-sm" title="<spring:message code="label.user_role.role"/>"><i class="fa fa-cogs"></i></a>
                                            </security:authorize>
                                        </td>
                                        <td width="200px">${r.code}</td>
                                        <td width="300px">${r.name}</td>
                                        <td>${r.description}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div> <!-- end .table-responsive-->
                        <input type="hidden" name="crudaction" value="delete" /> <!--DELETE DELETE DELETE-->
                    </form:form>
                </div> <!-- end card-body -->
            </div> <!-- end card -->
        </div> <!-- end col -->
    </div> <!-- end row -->
</div>
<script>
    $(document).ready(function() {
        <!-- Modal initialization -->
        <c:if test="${not empty isModal}">
        if ($("#btnSave" ) != undefined) {
            $('#code').addClass('parsley-error');
        }
        $('#myModal').modal('show');
        </c:if>

        $('#myModal').on('hidden.bs.modal', function () {
            $("#userGroupId").val('');
            $("#code").val('');
            $("#code" ).prop("disabled", false);
            $("#name").val('');
            $("#name" ).prop("disabled", false);
            $("#description").val('');
            $("#description" ).prop("disabled", false);
            $("#btnSave" ).prop("disabled", false);

            $(".parsley-errors-list").remove();
            $('input').removeClass('parsley-error');
            $("#error_code").remove();
        });


        <!-- deleteLink -->
        <%--$(".deleteLink").click(function(){--%>
        <%--    var id = $(this).attr("id");--%>
        <%--    bootbox.confirm("<spring:message code='confirm.content'/>", function(result) {--%>
        <%--        if (result) {--%>
        <%--            $("<input type='hidden' name='pojo.userGroupId' value='"+id+"'>").appendTo($("#listForm"));--%>
        <%--            $("#listForm").submit();--%>
        <%--        }--%>
        <%--    });--%>
        <%--});--%>
    });

    function deleteLink(id) {
        bootbox.confirm("<spring:message code='msg.confirm.content'/>", function(result) {
            if (result) {
                $("<input type='hidden' name='pojo.userGroupId' value='"+id+"'>").appendTo($("#listForm"));
                $("#listForm").submit();
            }
        });
    }

    function editForm(userGroupId, code, name, description){
        $("#userGroupId").val(userGroupId);
        $("#code").val(code);
        $("#name").val(name);
        $("#description").val(description);

        $('#myModal').modal('show');
    }
</script>
</body>
</html>

