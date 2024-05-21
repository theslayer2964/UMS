<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 1/17/2024
  Time: 2:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/list/program.html"/>
<html>
<head>
    <title><spring:message code="menu.system.program"/></title>
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
                        <li class="breadcrumb-item active"><spring:message code="menu.system.user"/></li>
                    </ol>
                </div>
                <h4 class="page-title"><fmt:message key="menu.system.program"/></h4>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <div class="header-title sub-header">
                        <button class="btn btn-warning waves-effect waves-light" data-toggle="modal"
                                data-target="#myModal" data-target="#con-close-modal"><spring:message
                                code="button.add"/></button>
                        <%@include file="insert.jsp" %>
                    </div>

                    <form:form id="listFormDelete" modelAttribute="item" action="${formURL}" method="post">
                        <div class="table-responsive">
                            <table id="datatable-buttons" class="table table-bordered"
                                   style="border-collapse: collapse; border-spacing: 0; width: 100%;">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th><spring:message code="label.action"/></th>
                                    <th><spring:message code="label.program.name"/></th>
                                    <th><spring:message code="label.user_role_url_acl.path"/></th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach items="${items.listResult }" var="r" varStatus="loop">
                                    <tr>
                                        <td class="table-number" width="5%">${loop.index+1}</td>
                                        <td class="table-action" width="10%">
                                            <a class="btn-icon waves-effect waves-light btn-warning btn-sm"
                                               onclick="javascript: editForm('${r.programId }', '${r.programName}');">
                                                <i class="fas fa-wrench"></i>
                                            </a>
                                            <a class="btn-icon waves-effect waves-light btn-danger btn-sm deleteLink"
                                               id="${r.programId }"
                                               onclick="javascript: deleteLink('${r.programId}','${r.programName}');"><i
                                                    class="fas fa-times"></i></a>
                                        </td>
                                        <td width="20%">${r.programName}</td>
                                        <td width="20%">${r.url}</td>
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
    $(document).ready(function () {
        <!-- Modal initilization -->
        <c:if test="${not empty isModal}">
        if ($("#btnSave") != undefined) {
            if ($("#error_userName").val() != undefined) {
                $('#userName').addClass('parsley-error');
            }
            if ($("#error_password").val() != undefined) {
                $('#password').addClass('parsley-error');
            }
            if ($("#error_email").val() != undefined) {
                $('#email').addClass('parsley-error');
            }
        }
        $('#myModal').modal('show');
        </c:if>

        $('#myModal').on('hidden.bs.modal', function () {
            $("#programId").val('');
            $("#programName").val('');
            $("#url").val('');

            $("#btnSave").prop("disabled", false);

            $(".parsley-errors-list").remove();
            $('input').removeClass('parsley-error');
            $("#error_url").remove();
            $("#error_programName").remove();

        });
    });

    function editForm(id, name) {
        $.ajax({
            url: '<c:url value="/ajax/getProgramById.html" />',
            type: 'get',
            data: {
                programId: id
            },
            dataType: 'json',
            success: function (res) {
                $('#programId').val(res.programId);
                $('#programName').val(res.programName);
                $('#url').val(res.url);
            }
        });
        $('#myModal').modal('show');
    }

    function deleteLink(id, name) {
        bootbox.confirm("<spring:message code='msg.confirm.content'/>" + name, function (result) {
            if (result) {
                $("<input type='hidden' name='pojo.programId' value='" + id + "'>").appendTo($("#listFormDelete"));
                $("#listFormDelete").submit();
            }
        });
    }


</script>
</body>
</html>
