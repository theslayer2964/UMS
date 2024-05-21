<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 12/27/2023
  Time: 2:09 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/utilities/user_c2_statistical.html"/>
<html>
<head>
    <title><spring:message code="menu.system.statisticalUser"/></title>
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
                        <li class="breadcrumb-item active"><spring:message code="menu.system.statisticalUser"/></li>
                    </ol>
                </div>
                <h4 class="page-title"><spring:message code="menu.system.statisticalUser"/></h4>
            </div>
        </div>
    </div>
</div>
<div class="row">
    <div class="col-12">
        <div class="card">
            <div class="card-body">
                <%@include file="C2UserLock_uploadFile.jsp" %>
                <%@include file="C2UserLock_viewlog.jsp" %>
                <%@include file="submitFormHistory.jsp" %>
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
                                <th><spring:message code="label.lockDate"/></th>
                                <th><spring:message code="label.c2user.cen"/></th>
                                <th><spring:message code="label.c2user.form_status"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${items.listResult }" var="r" varStatus="loop">
                                <tr>
                                    <td class="table-number" width="5%">${loop.index+1}</td>
                                    <td class="table-action" width="15%">
                                        <a class="btn-icon waves-effect waves-light btn-warning btn-sm"
                                           onclick="javascript: uploadFile('${r.username }','${r.granted_ip }');">
                                            <i class="fa fa-upload"></i>
                                        </a>
                                        <a class="btn-icon waves-effect waves-light btn-danger btn-sm deleteLink"
                                           id="${r.userId }"
                                           onclick="javascript: unlockLink('${r.userId}', '${r.username}');"
                                           title="<spring:message code="label.statistical.unlock"/>"><i
                                                class="fa fa-unlock"></i></a>
                                        <a onclick="javascript:getViewlogUser('${r.username}', '${r.description}');"
                                           class="btn-info waves-effect waves-light btn-sm"
                                           title="<spring:message code="label.statistical.log_view"/>"><i
                                                class="fa fa-history"></i></a>
                                        <a onclick="javascript:getHistoryFormSubmit('${r.username}');"
                                           class="btn-success waves-effect waves-light btn-sm"
                                           title="<spring:message code="label.statistical.log_view"/>"><i
                                                class="fa fa-file"></i></a>
                                    </td>
                                    <td width="15%">${r.username}</td>
                                    <td width="15%">${r.fullName}</td>
                                    <td>${r.description}</td>
                                    <td width="15%">${r.shopCode}</td>
                                    <td width="10%">
                                        <c:choose>
                                            <c:when test="${r.status != '99'}">
                                                <a class="rounded-circle btn-danger waves-effect waves-light"
                                                   data-toggle="dropdown"
                                                   href="#" style="width: 30px; height: 30px;position: relative"
                                                   role="button" aria-haspopup="false" aria-expanded="false">
                                                    <i class="fa fa-times"
                                                       style="position: relative;bottom: -4px;left: 9px"></i>
                                                </a>
                                            </c:when>
                                            <c:otherwise>
                                                <a class="rounded-circle btn-success waves-effect waves-light"
                                                   data-toggle="dropdown"
                                                   href="#" style="width: 30px; height: 30px;position: relative"
                                                   role="button" aria-haspopup="false" aria-expanded="false">
                                                    <i class="fa fa-check"
                                                       style="position: relative;bottom: -5px;left: 6px"></i>
                                                </a>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                    <input type="hidden" name="crudaction" value="unlock"/>
                </form:form>
            </div>
        </div>
    </div>
</div>

<script>
    $(document).ready(function () {
        <c:if test="${not empty isModal}">
        $('#myModal').modal('show');
        </c:if>

        var today = new Date().toISOString().split('T')[0];
        document.getElementById('updateDate').value = today;
    });
    // Su kien xay ra tren myModal khi myModal hidden
    $('#myModal').on('hidden.bs.modal', function () {

    });

    function destroyTable() {
        $('#example').DataTable().destroy();
    }

    function uploadFile(username, granted_ip) {
        $("#usernameFile").text(username);
        $("#usernameValue").val(username);
        $("#granted_ip").text(granted_ip);
        $('#myModal').modal('show');
    };

    // Xem lịch sử truy cập của user
    function getViewlogUser(username, banDate) {
        $("#loadingEvent").show();
        $("#usernameView").text(username);
        $("#searchNameUser").val(username);

        if ($.fn.DataTable.isDataTable('#example')) {
            $('#example').DataTable().destroy();
        }
        /// Ngày tháng năm
        document.getElementById('searchNameFrom').value = convertDateValue(banDate, -7);
        document.getElementById('searchNameTo').value = convertDateValue(banDate, 1);

        $('#table1').hide();
        $.ajax({
            url: '<c:url value="/admin/log_view2.html" />',
            type: 'get',
            data: {
                username: username,
                from: document.getElementById('searchNameFrom').value,
                to: document.getElementById('searchNameTo').value
            },
            dataType: 'json',
            success: function (data) {
                // c2: client side:
                $('#example').DataTable({
                    data: data,
                    columns: [
                        {title: "<spring:message code="label.pc"/>", data: "pc"},
                        {
                            title: "<spring:message code="label.viewDate"/>", data: "view_datetime",
                            render: function (date) {

                                console.log(date);
                                console.log(date.substring(0, 10));

                                return date.substring(0, 10);
                            }
                        },
                        {title: "<spring:message code="label.number"/>", data: "quantity"}
                    ]
                });
                $("#loadingEvent").hide();
                $('#table1').show();
            }
        });

        $('#viewlogModal').modal('show');
    }

    // xem file giải trình
    function getHistoryFormSubmit(username) {
        $("#loadingEvent2").show();
        $("#usernameView2").text(username);

        if ($.fn.DataTable.isDataTable('#example2')) {
            $('#example2').DataTable().destroy();
        }

        $('#table2').hide();
        $.ajax({
            url: '<c:url value="/admin/explanation_history.html" />',
            type: 'get',
            data: {
                username: username
            },
            dataType: 'json',
            success: function (data) {
                // c2: client side:
                $('#example2').DataTable({
                    data: data,
                    columns: [
                        {
                            title: "<spring:message code="label.c2user.explanation_form"/>", data: "id",
                            render: function (id) {
                                return "<a style='width: 36px;height: 26px;border-radius: 2px;position: relative;margin-top: 4px' class='waves-effect waves-light btn-info' onclick=\'predownload(" + JSON.stringify(id) + ")\'><i style='position: relative;top: 4px;right: -12px;' class=\'fa fa-file-pdf\'></i></a>"
                            }
                        },
                        {title: "<spring:message code="label.c2user.username"/>", data: "user_name"},
                        {
                            title: "<spring:message code="label.uploadDate"/>", data: "update_date",
                            render: function (date) {
                                return JSON.stringify(date).split(":")[0];
                            }
                        },
                        {
                            title: "<spring:message code="label.user.status"/>", data: "status",
                            render: function (status, type, row) {
                                if (status == '0')
                                    return "<button type=\'button\' class=\'btn btn-warning\' style=\'height: 30px;padding-bottom: 5px\' onclick=\'changeStatusForm(" + JSON.stringify(row) + ")\'><spring:message code="label.explanation-form.status.processing"/></button>";
                                else if (status == '1')
                                    return "<button type='button' class=\'btn btn-success\' style=\'height: 30px;padding-bottom: 5px\' onclick=\'changeStatusForm(" + JSON.stringify(row) + ")\'><spring:message code="label.explanation-form.status.accept"/></button>";
                                else
                                    return "<button type='button' class=\'btn btn-danger\' style=\'height: 30px;padding-bottom: 5px\' onclick=\'changeStatusForm(" + JSON.stringify(row) + ")\'><spring:message code="label.explanation-form.status.reject"/></button>";

                            }
                        },
                    ]
                });

                $("#loadingEvent2").hide();
                $('#table2').show();
            }
        });

        $('#submitFormHistoryModal').modal('show');
    }

    function predownload(file_id) {
        window.open("/file/preview.html?id=" + file_id, "_blank");
    }

    function changeStatusForm(row) { // admin duyệt cái form mà người bị khóa submit lên
        if (row.status == 0 || row.status == 1) { // Status: đang xử lí | đã bị từ chối
            console.log("0 -1")
            bootbox.confirm({
                message: "<spring:message code='msg.confirm.explanation-form'/>",
                buttons: {
                    confirm: {
                        label: '<spring:message code="button.accept"/>',
                        className: 'btn btn-success'
                    },
                    cancel: {
                        label: '<spring:message code="button.reject"/>',
                        className: 'btn btn-danger'
                    }
                },
                callback: function (result) {
                    if (result) { // chấp nhận file
                        $.ajax({
                            url: '<c:url value="/admin/updateFormStatus.html" />',
                            type: 'get',
                            data: {
                                id: row.id,
                                status: 1
                            },
                            dataType: 'json',
                            success: function (res) {
                                window.location.href = "<c:url value="/utilities/user_c2_statistical.html" />"
                            },
                            error: function (error) {
                                console.log("ERROR:" + error);
                            }
                        });
                    } else { // từ chôi file
                        $.ajax({
                            url: '<c:url value="/admin/updateFormStatus.html" />',
                            type: 'get',
                            data: {
                                id: row.id,
                                status: 2
                            },
                            dataType: 'json',
                            success: function (res) {
                                window.location.href = "<c:url value="/utilities/user_c2_statistical.html" />"
                            },
                            error: function (error) {
                                console.log("ERROR:" + error);
                            }
                        });
                    }
                }
            })
        } else if (row.status == 2) { // đang từ chối
            console.log("2")
            bootbox.confirm({
                message: "<spring:message code='msg.confirm.explanation-form'/>",
                buttons: {
                    confirm: {
                        label: '<spring:message code="button.accept"/>',
                        className: 'btn btn-success'
                    },
                    cancel: {
                        label: '<spring:message code="button.reject"/>',
                        className: 'btn btn-danger'
                    }
                },
                callback: function (result) {
                    if (result) {
                        $.ajax({
                            url: '<c:url value="/admin/updateFormStatus.html" />',
                            type: 'get',
                            data: {
                                id: row.id,
                                status: 1
                            },
                            dataType: 'json',
                            success: function (res) {
                                window.location.href = "<c:url value="/utilities/user_c2_statistical.html" />"
                            },
                            error: function (error) {
                                console.log("ERROR:" + error);
                            }
                        });
                    } else {
                        $.ajax({
                            url: '<c:url value="/admin/updateFormStatus.html" />',
                            type: 'get',
                            data: {
                                id: row.id,
                                status: 2
                            },
                            dataType: 'json',
                            success: function (res) {
                                window.location.href = "<c:url value="/utilities/user_c2_statistical.html" />"
                            },
                            error: function (error) {
                                console.log("ERROR:" + error);
                            }
                        });
                    }
                }
            })
        }
    }

    /// search history tra cuu user
    function searchForm() {
        var form = document.getElementById('form');

        form.addEventListener('submit', function (event) {
            event.preventDefault();
            $("#loadingEvent").show();

            var from = document.getElementById('searchNameFrom').value;
            var to = document.getElementById('searchNameTo').value;

            $('#table1').hide();
            $.ajax({
                url: '<c:url value="/admin/log_view2.html" />',
                type: 'get',
                data: {
                    username: document.getElementById('searchNameUser').value,
                    from: from,
                    to: convertDateValueSearch(to, 1)// them 1 ngay
                },
                dataType: 'json',
                success: function (data) {
                    // c2: client side:
                    $('#example').DataTable().destroy();
                    $('#example').DataTable({
                        data: data,
                        columns: [
                            {title: "<spring:message code="label.pc"/>", data: "pc"},
                            {
                                title: "<spring:message code="label.viewDate"/>", data: "view_datetime",
                                render: function (date) {
                                    return date.substring(0, 10);
                                }
                            },
                            {title: "<spring:message code="label.number"/>", data: "quantity"}
                        ]
                    });

                    $("#loadingEvent").hide();
                    $('#table1').show();
                }
            });

            $('#viewlogModal').modal('show');
        });
    }

    function unlockLink(userId, username) {
        bootbox.confirm("<spring:message code='msg.confirm.unlock'/>" + username, function (result) {
            if (result) {
                $("<input type='hidden' name='pojo.userId' value='" + userId + "'>").appendTo($("#listFormDelete"));
                $("#listFormDelete").submit();
            }
        });
    }

    function convertDateValue(dateTemp, changeTime) {
        const parts = dateTemp.split("/");
        const day = parseInt(parts[0], 10);
        const month = parseInt(parts[1], 10);
        const year = parseInt(parts[2], 10);

        const date = new Date(year, month - 1, day);

        date.setDate(date.getDate() + changeTime);

        return date.toISOString().substring(0, 10);
    }

    function convertDateValueSearch(dateTemp, changeTime) {
        var parts = dateTemp.split("-");
        var year = parseInt(parts[0], 10);
        var month = parseInt(parts[1], 10);
        var day = parseInt(parts[2], 10);

        var date = new Date(year, month - 1, day);

        date.setDate(date.getDate() + 2);

        return date.toISOString().substring(0, 10);
    }
</script>
</body>
</html>
