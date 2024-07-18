<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/list/user_c2_ss.html"/>
<html>
<head>
    <title><spring:message code="menu.system.useragency"/></title>
</head>
<style>
    textarea {
        width: 100%;
        height: 500px;
    }

    .is-invalid .select2-container--default .select2-selection--single {
        border-color: #dc3545;
    }
</style>
<body>
<div class="container-fluid">
    <%--    title--%>
    <div class="row">
        <div class="col-12">
            <div class="page-title-box">
                <div class="page-title-right">
                    <ol class="breadcrumb m-0">
                        <li class="breadcrumb-item"><a href="javascript: void(0);"><spring:message
                                code="menu.system"/></a></li>
                        <li class="breadcrumb-item active"><spring:message code="menu.system.useragency"/></li>
                    </ol>
                </div>
                <h4 class="page-title"><spring:message code="menu.system.useragency"/></h4>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-body"><%--    search: --%>
                    <form:form id="listForm" modelAttribute="item">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="card-box table-responsive">
                                    <div class="form-group row">
                                        <label for="searchName" class="col-sm-2 col-form-label"
                                               style="left: 6%;"><spring:message code="label.user_name"/></label>
                                        <div class="col-sm-7">
                                            <form:input type="text" id="searchName" name="userName" path="displayName"
                                                        class="form-control" cssStyle="text-transform: uppercase"/>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="searchStatus" class="col-sm-2 col-form-label"
                                               style="left: 6%;"><spring:message
                                                code="label.user.status"/></label>
                                        <div class="col-sm-7">
                                            <form:select id="searchStatus" path="status" class="form-control">
                                                <option value="-1"><spring:message code="common.status"/></option>
                                                <option value="1" <c:if
                                                        test="${item.status == '1'}"> selected="selected" </c:if> >
                                                    <spring:message code="label.c2user.using"/></option>
                                                <option value="2" <c:if
                                                        test="${item.status == '2'}"> selected="selected" </c:if> >
                                                    <spring:message code="label.c2user.logTemp"/></option>
                                                <option value="4" <c:if
                                                        test="${item.status == '4'}"> selected="selected" </c:if> >
                                                    <spring:message code="label.c2user.log"/></option>
                                                <option value="0" <c:if
                                                        test="${item.status == '0'}"> selected="selected" </c:if> >
                                                    <spring:message code="label.c2user.cancel"/></option>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="searchGroup" class="col-sm-2 col-form-label"
                                               style="left: 6%;"><spring:message code="label.c2user.cen"/></label>
                                        <div class="col-sm-7">
                                            <form:select id="searchGroup" name="shop_code" path="shop_code"
                                                         class="form-control js-example-basic-single">
                                                <option value="-1"><fmt:message key="commonet.shop_code"/></option>
                                                <form:options items="${listShop}" item="shop" itemLabel="name"
                                                              itemValue="shop_code"/>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-sm-2"></div>
                                        <div class="col-sm-8 offset-md-2" style="display: flex;">
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
                    <form:form id="listFormDelete" modelAttribute="item" action="${formURL}" method="post">
                        <div class="table-responsive">
                            <table id="example" class="display dataTable cell-border table table-bordered"
                                   style="border-collapse: collapse; border-spacing: 0; width: 100%;"></table>
                        </div>
                        <input type="hidden" name="crudaction" value="unlock"/>
                    </form:form>
                </div>
            </div>
        </div>
    </div>
    <%@include file="insertC2UserAdmin.jsp" %>
    <%@include file="changeShopcodeUser.jsp" %>
    <%@include file="lockC2UserAdmin.jsp" %>
    <%@include file="history_user.jsp" %>
</div>
<script>
    $(document).ready(function () {
        $('#program2').val('');

        $('.js-example-basic-single').select2();

        $('#shop_code2').select2({
            dropdownParent: $('#parentShop')
        })
        // validateShopcode();

        $('#lockModal').modal('hide');
        $('#shop_code2').val('');

        var today = new Date().toISOString().split('T')[0];
        document.getElementById('updateDate').value = today;

        $('#parentShop').on('select2:select', function (e) {
            $('#granted_ip_new').val('');
            $.ajax({
                url: '<c:url value="/ajax/findgrantedIpByShopcode.html" />',
                type: 'get',
                data: {
                    shop_code: e.params.data.id
                },
                dataType: 'json',
                success: function (res) {
                    $('#granted_ip_new').val(res.ipGranted)
                },
                error: function () {
                    $('#granted_ip_new').val('')
                }
            });
        })

        // remove báo lỗi ở các input cần validate
        email.classList.remove("invalid");
        document.getElementById('email-validation').textContent = '';

        // Xử lí message phức tạp: KET QUA QUA TRINH và DS User Mail ko đúng
        <c:if test="${not empty isMessageDifficult}">
        var notyf = new Notyf({
            duration: 0,
            position: {x: 'left', y: 'bottom'},
            dismissible: true,
        });
        // Xử lí chuỗi từ Backend gửi về
        let cleanedString = '${messageResponse}'.slice(1, -1);
        var keyValuePairs = cleanedString.split(", ");
        keyValuePairs.forEach(user => {
            notyf.success(user);
        })
        </c:if>
        <%-- DS User ko đúng--%>
        <c:if test="${not empty isMessageIncorrectMail}">
        var notyf_Incorrect = new Notyf({
            duration: 0,
            position: {x: 'right', y: 'bottom'},
            dismissible: true,
        });
        notyf_Incorrect.error('${incorrectMail_messageResponse}');
        </c:if>

    });

    $(function () {
        $('#example').DataTable({
            serverSide: true,
            processing: true,
            searchDelay: 500,
            columnDef: [
                {width: "5%"},
                {width: "25%"},
                {width: "20%"},
                {width: "10%"},
                {width: "10%"},
                {width: "30%"},
            ],
            pageLength: 25,
            scrollY: 400,
            ajax: {
                url: '<c:url value="/ajax/user_c2_ss.html" />',
                type: "POST",
                contentType: 'application/json',
                data: function (d) {
                    return JSON.stringify(d);
                }
            },
            columns: [
                {
                    title: '<spring:message code="label.action"/>', data: "userId",
                    render: function (userId, type, row) {
                        if (row.status == '4' || row.status == '2') {
                            return "<a class=\'btn-info waves-effect waves-light btn-sm\' onclick=\'editForm(" + JSON.stringify(userId) + ")\'><i class=\'fa fa-wrench\'></i></a>"
                                + " <a style='width: 36px;height: 26px;border-radius: 2px;position: relative;background-color: #60cd37' class='btn-info waves-effect waves-light btn-success' onclick=\'unlockLink(" + JSON.stringify(row) + ")\'><i style='position: relative;top: 4px;right: -13px;' class=\'fa fa-unlock\'></i></a>"
                                + " <a style='width: 36px;height: 26px;border-radius: 2px;position: relative;background-color: #ee8d0c' class='btn-info waves-effect waves-light btn-success' onclick=\'changeShopCode(" + JSON.stringify(row) + ")\'><i style='position: relative;top: 3px;right: -10px;' class=\'fa fa-shopping-basket\'></i></a>"
                                + " <a style='width: 36px;height: 26px;border-radius: 2px;position: relative;background-color: #ee8d0c' class='waves-effect waves-light btn-dark' onclick=\'findHistory(" + JSON.stringify(row) + ")\'><i style='position: relative;top: 3px;right: -10px;' class=\'fa fa-history\'></i></a>"
                        } else {
                            return "<a class=\'btn-info waves-effect waves-light btn-sm\' onclick='editForm(" + JSON.stringify(userId) + ")'><i class=\'fa fa-wrench\'></i></a>"
                                + " <a style='width: 36px;height: 26px;border-radius: 2px;position: relative;' class='btn-info waves-effect waves-light btn-danger' onclick=\'lockLink(" + JSON.stringify(row) + ")\'><i style='position: relative;top: 4px;right: -13px;' class=\'fa fa-lock\'></i></a>"
                                + " <a style='width: 36px;height: 26px;border-radius: 2px;position: relative;background-color: #ee8d0c' class='btn-info waves-effect waves-light btn-success' onclick=\'changeShopCode(" + JSON.stringify(row) + ")\'><i style='position: relative;top: 3px;right: -10px;' class=\'fa fa-shopping-basket\'></i></a>"
                                + " <a style='width: 36px;height: 26px;border-radius: 2px;position: relative;background-color: #ee8d0c' class='waves-effect waves-light btn-primary' onclick=\'findHistory(" + JSON.stringify(row) + ")\'><i style='position: relative;top: 3px;right: -10px;' class=\'fa fa-history\'></i></a>"
                        }
                    }
                },
                {title: '<spring:message code="label.username"/>', data: "username"},
                {title: '<spring:message code="label.name"/>', data: "fullName"},
                {title: '<spring:message code="label.c2user.cen"/>', data: "shopCode"},
                {title: '<spring:message code="label.description"/>', data: "description"},
                {
                    title: '<spring:message code="label.status"/>', data: "status",
                    render: function (status) {
                        if (status == '1')
                            return '<spring:message code="label.c2user.using"/>'
                        else if (status == '4')
                            return '<spring:message code="label.c2user.log"/>'
                        else if (status == '0')
                            return '<spring:message code="label.c2user.cancel"/>'
                        else
                            return '<spring:message code="label.c2user.logTemp"/>'
                    }
                }
            ]
        })
    });

    function searchForm() {
        var form = document.getElementById('listForm');
        var searchName = document.getElementById('searchName').value;
        var searchStatus = document.getElementById('searchStatus').value;
        var searchGroup = document.getElementById('searchGroup').value;

        form.addEventListener('submit', function (event) {
            event.preventDefault();
            $('#example').DataTable().destroy();
            $('#example').DataTable({
                serverSide: true,
                processing: true,
                searchDelay: 500,
                columnDef: [
                    {width: "5%"},
                    {width: "25%"},
                    {width: "20%"},
                    {width: "10%"},
                    {width: "10%"},
                    {width: "30%"},
                ],
                pageLength: 25,
                scrollY: 400,
                ajax: {
                    url: '<c:url value="/ajax/user_c2_ss.html" />',
                    type: "POST",
                    contentType: 'application/json',
                    data: function (d) {
                        if (searchName != "")
                            d.searchName = searchName;
                        if (searchStatus != "-1")
                            d.searchStatus = searchStatus;
                        if (searchGroup != "-1")
                            d.searchGroup = searchGroup;
                        var send = Object.assign({}, d);
                        if (d.hasOwnProperty("searchName")) {
                            delete d.search;
                        }
                        if (d.hasOwnProperty("searchStatus")) {
                            delete d.searchStatus;
                        }
                        if (d.hasOwnProperty("searchGroup")) {
                            delete d.searchGroup;
                        }
                        return JSON.stringify(send);
                    }
                },
                columns: [
                    {
                        title: '<spring:message code="label.action"/>', data: "userId",
                        render: function (userId, type, row) {
                            if (row.status == '4' || row.status == '2') {
                                return "<a class=\'btn-info waves-effect waves-light btn-sm\' onclick=\'editForm(" + JSON.stringify(userId) + ")\'><i class=\'fa fa-wrench\'></i></a>"
                                    + " <a style='width: 36px;height: 26px;border-radius: 2px;position: relative;background-color: #60cd37' class='btn-info waves-effect waves-light btn-success' onclick=\'unlockLink(" + JSON.stringify(row) + ")\'><i style='position: relative;top: 4px;right: -13px;' class=\'fa fa-unlock\'></i></a>"
                                    + " <a style='width: 36px;height: 26px;border-radius: 2px;position: relative;background-color: #ee8d0c' class='btn-info waves-effect waves-light btn-success' onclick=\'changeShopCode(" + JSON.stringify(row) + ")\'><i style='position: relative;top: 3px;right: -10px;' class=\'fa fa-shopping-basket\'></i></a>"
                                    + " <a style='width: 36px;height: 26px;border-radius: 2px;position: relative;background-color: #ee8d0c' class='waves-effect waves-light btn-primary' onclick=\'findHistory(" + JSON.stringify(row) + ")\'><i style='position: relative;top: 3px;right: -10px;' class=\'fa fa-history\'></i></a>"
                            } else {
                                return "<a class=\'btn-info waves-effect waves-light btn-sm\' onclick='editForm(" + JSON.stringify(userId) + ")'><i class=\'fa fa-wrench\'></i></a>"
                                    + " <a style='width: 36px;height: 26px;border-radius: 2px;position: relative;' class='btn-info waves-effect waves-light btn-danger' onclick=\'lockLink(" + JSON.stringify(row) + ")\'><i style='position: relative;top: 4px;right: -13px;' class=\'fa fa-lock\'></i></a>"
                                    + " <a style='width: 36px;height: 26px;border-radius: 2px;position: relative;background-color: #ee8d0c' class='btn-info waves-effect waves-light btn-success' onclick=\'changeShopCode(" + JSON.stringify(row) + ")\'><i style='position: relative;top: 3px;right: -10px;' class=\'fa fa-shopping-basket\'></i></a>"
                                    + " <a style='width: 36px;height: 26px;border-radius: 2px;position: relative;background-color: #ee8d0c' class='waves-effect waves-light btn-primary' onclick=\'findHistory(" + JSON.stringify(row) + ")\'><i style='position: relative;top: 3px;right: -10px;' class=\'fa fa-history\'></i></a>"
                            }
                        }
                    },
                    {title: '<spring:message code="label.username"/>', data: "username"},
                    {title: '<spring:message code="label.name"/>', data: "fullName"},
                    {title: '<spring:message code="label.c2user.cen"/>', data: "shopCode"},
                    {title: '<spring:message code="label.description"/>', data: "description"},
                    {
                        title: '<spring:message code="label.status"/>', data: "status",
                        render: function (status) {
                            if (status == '1')
                                return '<spring:message code="label.c2user.using"/>'
                            else if (status == '4')
                                return '<spring:message code="label.c2user.log"/>'
                            else if (status == '0')
                                return '<spring:message code="label.c2user.cancel"/>'
                            else
                                return '<spring:message code="label.c2user.logTemp"/>'
                        }
                    }
                ]
            })
        });
    }

    function unlockLink(row) {
        bootbox.confirm("<spring:message code='msg.confirm.unlock'/>" + row.username, function (result) {
            if (result) {
                $("<input type='hidden' name='pojo.userId' value='" + row.userId + "'>").appendTo($("#listFormDelete"));
                $("#listFormDelete").submit();
            }
        });
    }

    function lockLink(row) {
        $("#usernameFile").text(row.username);
        $("#idUpload").val(row.userId);
        $("#nameUpload").val(row.username);
        $('#lockModal').modal('show');
    }

    // update user - update status, IP, quyền CHT :
    function editForm(userId) {
        $.ajax({
            url: '<c:url value="/ajax/getC2UserByUsername.html" />',
            type: 'get',
            data: {
                userId: userId
            },
            dataType: 'json',
            success: function (res) {
                $("#userId").val(userId);
                $("#usernamesearch").text(res.username);
                $("#status2").val(res.status);
                $("#granted_ip").val(res.granted_ip);
            }
        });
        $('#myModal').modal('show');
    }

    var updateUserForm = document.getElementById('addForm');
    updateUserForm.addEventListener('submit', function (e) {
        e.preventDefault();
        var emailRs = validateEmailForGrantedPermission();
        if (emailRs) {
            updateUserForm.submit();
        }
    });

    var email = document.addForm.email;

    function validateEmailForGrantedPermission() {
        if (email.value) {
            const regex = /@MOBIFONE\.VN$/;
            if (regex.test(email.value)) {
                email.classList.remove("invalid");
                document.getElementById('email-validation').textContent = '';
                return true;
            } else {
                email.classList.remove("invalid");
                document.getElementById('email-validation').textContent = '<spring:message code="label.c2user.message-mail" />';
                return false;
            }
        } else {
            return true;
        }
    }

    // CHANGE SHOP CODE:
    function changeShopCode(user) {
        $("#username2").val(user.username);
        $("#fullname2").val(user.fullName);
        $("#userChangeshop").text(user.username);
        $("#shopcode_old").text(user.shopCode);
        document.getElementById('shop_old2').value = user.shopCode
        $('#myModalChange').modal('show');
        $('#shop_code2').val('').change();
        $('#granted_ip_new').val('');
        $('#email').val('');
        $('#email-changeShop-validation').val('');
    }

    var formChangeShop = document.getElementById('changeshopForm');
    var emailChangeShop = document.changeshopForm.email;
    var table = document.changeshopForm.table

    formChangeShop.addEventListener('submit', function (e) {
        e.preventDefault();
        if (validateShopcode()) {
            document.getElementById('program2').value = '';
            var programList = "";
            $(':checkbox:checked').each(function (i) {
                programList += $(this).val() + "|";
            });
            document.getElementById('program2').value = programList;
            if (validateProgramList(programList)) {
                if (programList.includes("4")) { // check xem co change CSKK ko -> nếu có thì pahri check thim mail
                    if (validateEmailChangeShop()) {
                        formChangeShop.submit();
                    }
                } else
                    formChangeShop.submit();
            }
        }
    })

    function validateEmailChangeShop() {
        if (emailChangeShop.value) {
            const regex = /@mobifone\.vn$/;
            if (regex.test(emailChangeShop.value)) {
                emailChangeShop.classList.remove("is-invalid");
                emailChangeShop.classList.add("is-valid");
                document.getElementById('email-changeShop-validation').textContent = '';
                return true;
            } else {
                emailChangeShop.classList.remove("is-valid");
                emailChangeShop.classList.add("is-invalid");
                document.getElementById('email-changeShop-validation').textContent = '<spring:message code="label.c2user.message-mail" />';
                return false;
            }
        } else {
            emailChangeShop.classList.remove("is-valid");
            emailChangeShop.classList.add("is-invalid");
            document.getElementById('email-changeShop-validation').textContent = '<spring:message code="label.c2user.message-mail" />';
            return false;
        }
    }

    function validateProgramList(programList) {
        if (programList != "") {
            document.getElementById("programlist-validation").textContent = '';
            return true;
        } else {
            document.getElementById("programlist-validation").textContent = '<spring:message code="label.c2user.message-programList" />';
            return false;
        }
    }

    // history user:
    function findHistory(row) {
        $.ajax({
            url: '<c:url value="/ajax/getHistoryByUsername.html" />',
            type: 'get',
            data: {
                username: row.username
            },
            dataType: 'json',
            success: function (res) {
                $("#history_user").text(row.username);

                if ($.fn.DataTable.isDataTable('#history_table')) {
                    $('#history_table').DataTable().destroy();
                }

                $('#history_table').DataTable({
                    data: res,
                    bAutoWidth: false,
                    columns: [
                        {
                            title: '<spring:message code="label.action"/>', data: "id",
                            render: function (code) {
                                return "<a class=\'btn-icon waves-effect waves-light btn-success btn-sm\' onclick=\'predownload(" + JSON.stringify(code) + ")\'><i class='fa fa-info'></i></a>"
                            }
                        },
                        {
                            title: "<spring:message code="label.uploadDate"/>",
                            data: "update_date",
                            render: function (date) {
                                return date.substring(0, 10);
                            }
                        },
                        {title: "<spring:message code="label.file_name"/>", data: "file_name"},
                        {
                            title: "<spring:message code="label.file_type"/>", data: "form_type",
                            render: function (form_type) {
                                if (form_type == '0')
                                    return "<spring:message code="file.file-type.loai0"/>"
                                else if (form_type == '1')
                                    return "<spring:message code="file.file-type.loai1"/>"
                                else if (form_type == '2')
                                    return "<spring:message code="file.file-type.loai2"/>"
                                else if (form_type == '3')
                                    return "<spring:message code="file.file-type.loai3"/>"
                                else
                                    return "<spring:message code="file.file-type.loai4"/>"
                            },
                        },
                    ]
                })

            }
        });
        $('#history_modal').modal('show');
    }

    // predownload
    function predownload(file_id) {
        window.open("/file/preview.html?id=" + file_id, "_blank");
    }

    // validate select2
    function validateShopcode() {
        var $select2 = document.getElementById('shop_code2');
        // Reset
        $select2.classList.remove('is-invalid');

        if ($select2.value == '-1' || $select2.value == -1 || $select2.value == '') {
            // Add is-invalid class when select2 element is required
            $select2.classList.add('is-invalid');
            document.getElementById('shop-validation').textContent = "<spring:message code="label.c2user.message-shop-code" /> ";
            return false;
        } else {
            document.getElementById('shop-validation').textContent = "";
            return true;
        }
    }
</script>
</body>
</html>
