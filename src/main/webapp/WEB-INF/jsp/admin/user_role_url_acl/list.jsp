
<%@include file="/common/taglibs.jsp"%>
<c:url var="formURL" value="/admin/user_role_url_acl.html" />
<html>
<head>
    <title><spring:message code="menu.system.user_role.acl"/> </title>
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
                        <li class="breadcrumb-item active"><spring:message code="menu.system.url_group"/></li>
                        <li class="breadcrumb-item active"><spring:message code="menu.system.user_role.acl"/></li>
                    </ol>
                </div>
                <h4 class="page-title">
                    <spring:message code="menu.system.user_role.acl"/>
                    <small>${item.pojo.userRole.name } (${item.pojo.userRole.code })</small>
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
                        <a href="userrole-list.html" class="fa fa-arrow-left btn btn-primary waves-effect waves-light" style="line-height: 1.5 !important;">
                            <spring:message code="button.back"/>
                        </a>
                        <button class="btn btn-warning waves-effect waves-light" data-toggle="modal" data-target="#myModal">
                            <spring:message code="button.add"/>
                        </button>
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
                                    <th><spring:message code="label.user_role_url_acl.code"/> </th>
                                    <th><spring:message code="label.user_role_url_acl.name"/></th>
                                    <th><spring:message code="label.path"/></th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach items="${items.listResult }" var="r" varStatus="loop">
                                    <tr>
                                        <td class="table-number" width="5%">${loop.index + 1}</td>
                                        <td class="table-action" width="10%">
                                            <a class="btn btn-icon waves-effect waves-light btn-danger btn-sm deleteLink" id="${r.userRoleUrlACLId }"><i class="fas fa-times"></i></a>
                                        </td>
                                        <td width="15%">${r.url.code}</td>
                                        <td width="25%">${r.url.name}</td>
                                        <td>${r.url.path}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div> <!-- end .table-responsive-->
                        <input type="hidden" name="crudaction" value="delete" />
                        <form:hidden path="pojo.userRole.userRoleId"/>
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
            $("#error_select").remove();
        });

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
        $(".deleteLink").click(function(){
            var id = $(this).attr("id");
            bootbox.confirm("<fmt:message key='msg.confirm.content'/>", function(result) {
                if (result) {
                    $("<input type='hidden' name='pojo.userRoleUrlACLId' value='"+id+"'>").appendTo($("#listForm"));
                    $("#listForm").submit();
                }
            });
        });

        <!-- Checkbox -->
        $("#check-all").change(function() {
            if (this.checked) {
                $(".checkParent, .checkChild").each(function() {
                    this.checked = true;
                });
            } else {
                $(".checkParent, .checkChild").each(function() {
                    this.checked = false;
                });
            }
        });

        $(".checkParent").click(function() {
            if ($(this).is(":checked")) {
                var isAllChecked = 0;
                $(".checkParent").each(function() {
                    if (!this.checked)
                        isAllChecked = 1;
                })
                $(this).closest("tr").next("tr").find(".checkChild").prop("checked", true);
                if (isAllChecked == 0) {
                    $("#check-all").prop("checked", true);
                }
            } else {
                $("#check-all").prop("checked", false);
                $(this).closest("tr").next("tr").find(".checkChild").prop("checked", false);
            }
        });

        $(".checkChild").click(function() {
            if ($(this).is(":checked")) {

                var isChildChecked = 0;
                $(".checkChild").each(function() {
                    if (!this.checked)
                        isChildChecked = 1;
                });
                if (isChildChecked == 0) {
                    $("#check-all").prop("checked", true);
                }
                $(this).closest("table").closest("tr").prev("tr").find(".checkParent").prop("checked", true);

            } else {
                var isAllSiblingChecked = 0;
                $(this).closest("tr").nextAll("tr").find(".checkChild").each(function() {
                    if ($(this).is(":checked"))
                        isAllSiblingChecked = 1;
                });

                $(this).closest("tr").prev("tr").find(".checkChild").each(function() {
                    if ($(this).is(":checked"))
                        isAllSiblingChecked = 1;
                });

                if (isAllSiblingChecked == 0) {
                    $(this).closest("table").closest("tr").prev("tr").find(".checkParent").prop("checked", false);
                }
                $("#check-all").prop("checked", false);
            }
        });
    });

</script>
</body>
</html>

