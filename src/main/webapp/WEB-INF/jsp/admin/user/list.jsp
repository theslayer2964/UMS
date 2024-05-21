<%--
  Created by IntelliJ IDEA.
  User: phaolo
  Date: 2020-03-18
  Time: 05:14
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/admin/user-list.html"/>
<html>
<head>
    <title><spring:message code="menu.system.user"/></title>
</head>
<body>
<div class="container-fluid">

    <!-- start page title -->
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
                <h4 class="page-title"><spring:message code="label.user.list"/></h4>
            </div>
        </div>
    </div>
    <!-- end page title -->
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <form:form id="listForm" modelAttribute="item" action="${formURL}" method="post">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="card-box table-responsive">
                                    <div class="form-group row">
                                        <label for="searchName" class="col-sm-2 col-form-label"
                                               style="left: 6%;"><spring:message code="label.user.name"/></label>
                                        <div class="col-sm-7">
                                            <form:input type="text" id="searchName" name="userName" path="displayName"
                                                        class="form-control"/>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label for="searchType" class="col-sm-2 col-form-label"
                                               style="left: 6%;"><spring:message code="label.user.type"/></label>
                                        <div class="col-sm-7">
                                            <form:select id="searchType" path="accountType" class="form-control">
                                                <%--                                                <option value="-1"><spring:message code="common.select"/></option>--%>
                                                <option value="1" <c:if
                                                        test="${item.accountType == '1'}"> selected="selected" </c:if> >
                                                    <spring:message code="label.user.system_type"/></option>
                                                <option value="2" <c:if
                                                        test="${item.accountType == '2'}"> selected="selected" </c:if> >
                                                    <spring:message code="label.user.connect_type"/></option>
                                                <option value="3" <c:if
                                                        test="${item.accountType == '3'}"> selected="selected" </c:if> >
                                                    <spring:message code="label.user.LDAP_type"/></option>
                                            </form:select>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <label for="searchGroup" class="col-sm-2 col-form-label"
                                               style="left: 6%;"><spring:message code="label.user.group"/></label>
                                        <div class="col-sm-7">
                                            <form:select id="searchGroup" name="userGroup" path="userGroup.userGroupId"
                                                         class="form-control">
                                                <%--                                                <option value="-1"><fmt:message key="common.select"/></option>--%>
                                                <form:options items="${listOfUserGroup}" itemLabel="name"
                                                              itemValue="userGroupId"/>
                                            </form:select>
                                        </div>
                                    </div>

                                    <div class="form-group row">
                                        <div class="col-sm-2"></div>
                                        <div class="col-sm-8 offset-md-2">
                                            <button type="submit" style="margin-right: 10px;" class="btn btn-success"
                                                    onclick="javascript: searchForm();">
                                                <i class="fa fa-search"></i>&nbsp;<fmt:message key="button.search"/>
                                            </button>
                                            <button type="reset" class="btn btn-secondary"><fmt:message
                                                    key="button.reset"/></button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <input type="hidden" name="crudaction" value="search"/>
                    </form:form>

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
                                    <th><spring:message code="label.username"/></th>
                                    <th><spring:message code="label.user.name"/></th>
                                    <th><spring:message code="label.user.email"/></th>
                                    <th><spring:message code="label.user.group"/></th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach items="${items.listResult }" var="r" varStatus="loop">
                                    <tr>
                                        <td class="table-number" width="5%">${loop.index+1}</td>
                                        <td class="table-action" width="10%">
                                            <a class="btn-icon waves-effect waves-light btn-warning btn-sm"
                                               onclick="javascript: editForm('${r.userId }');">
                                                <i class="fas fa-wrench"></i>
                                            </a>
                                            <a class="btn-icon waves-effect waves-light btn-danger btn-sm deleteLink"
                                               id="${r.userId }" onclick="javascript: deleteLink('${r.userId}');"><i
                                                    class="fas fa-times"></i></a>
                                            <security:authorize access="hasAnyRole('ADMIN','FAD_USERROLEACL')">
                                                <a href="user_role_acl.html?pojo.user.userId=${r.userId }"
                                                   class="btn-info waves-effect waves-light btn-sm"
                                                   title="<spring:message code="label.user_role.role"/>"><i
                                                        class="fa fa-cogs"></i></a>
                                            </security:authorize>
                                        </td>
                                        <td width="20%">${r.userName}</td>
                                        <td width="20%">${r.displayName}</td>
                                        <td width="15%">${r.email}</td>
                                        <td>${r.userGroup.name}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- end .table-responsive-->
                        <input type="hidden" name="crudaction" value="delete"/>
                    </form:form>
                </div> <!-- end card-body -->
            </div> <!-- end card -->
        </div> <!-- end col -->
    </div> <!-- end row -->
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
            $("#userId").val('');
            $("#userName").val('');
            $("#userName").prop("disabled", false);
            $("#password").val('');
            $("#password").prop("disabled", false);
            $("#displayName").val('');
            $("#displayName").prop("disabled", false);
            $("#email").val('');
            $("#email").prop("disabled", false);
            $("#userGroupId").val('');
            $("#userGroupId").prop("disabled", false);
            $("#shopId").val('');
            $("#shopId").prop("disabled", false);

            $("#btnSave").prop("disabled", false);

            $(".parsley-errors-list").remove();
            $('input').removeClass('parsley-error');
            $("#error_userName").remove();
            $("#error_password").remove();
            $("#error_email").remove();

        });

        <!-- deleteLink -->
        <%--$(".deleteLink").click(function(){--%>
        <%--    var id = $(this).attr("id");--%>
        <%--    bootbox.confirm("<spring:message code='confirm.content'/>", function(result) {--%>
        <%--        if (result) {--%>
        <%--            $("<input type='hidden' name='pojo.userId' value='"+id+"'>").appendTo($("#listForm"));--%>
        <%--            $("#listForm").submit();--%>
        <%--        }--%>
        <%--    });--%>
        <%--});--%>
    });

    function deleteLink(id) {
        bootbox.confirm("<spring:message code='msg.confirm.content'/>", function (result) {
            if (result) {
                $("<input type='hidden' name='pojo.userId' value='" + id + "'>").appendTo($("#listFormDelete"));
                $("#listFormDelete").submit();
            }
        });
    }

    function searchForm() {
        $("#listForm").submit();
    }

    function editForm(userId) {
        $.ajax({
            url: '<c:url value="/ajax/getUserById.html" />',
            type: 'get',
            data: {
                userId: userId
            },
            dataType: 'json',
            success: function (res) {
                $("#userId").val(userId);
                $("#userName").val(res.userName);
                $("#password").val(res.password);
                $("#displayName").val(res.displayName);
                $("#email").val(res.email);
                $("#accountType").val(res.accountType);
                $("#userGroupId").val(res.userGroup == null ? -1 : res.userGroup.userGroupId);
            }
        });
        $('#myModal').modal('show');
    }
</script>
</body>
</html>

