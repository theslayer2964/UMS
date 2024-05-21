<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/list/department.html"/>

<html>
<head>
    <title><spring:message code="menu.system.department"/></title>
</head>
<style>
</style>
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
                <h4 class="page-title"><fmt:message key="menu.system.department"/></h4>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <div class="header-title sub-header" style="display: flex">
<%--                        <button class="btn btn-warning waves-effect waves-light" data-toggle="modal"--%>
<%--                                data-target="#myModal" data-target="#con-close-modal" id="add">--%>
<%--                            <spring:message code="button.add"/>--%>
<%--                        </button>--%>
                        <%@include file="insert.jsp" %>
                        <button class="btn btn-primary waves-effect waves-light" data-toggle="modal"
                                data-target="#mSale" data-target="#con-close-modal" id="add" onclick="get_mSaleIP()">
                            <spring:message code="button.msalte"/>
                        </button>
                        <%@include file="ip_mSale.jsp" %>
                    </div>

                    <form:form id="listFormDelete" modelAttribute="item" action="${formURL}" method="post">
                        <div class="table-responsive">
                            <table id="datatable-buttons" class="table table-bordered"
                                   style="border-collapse: collapse; border-spacing: 0; width: 100%;">
                                <thead>
                                <tr>
                                    <th>#</th>
                                    <th><spring:message code="label.action"/></th>
                                    <th><spring:message code="label.c2user.shop_code"/></th>
                                    <th><spring:message code="label.c2user.cen"/></th>
                                    <th><spring:message code="label.c2user.address"/></th>
                                </tr>
                                </thead>

                                <tbody>
                                <c:forEach items="${items.listResult }" var="r" varStatus="loop">
                                    <tr>
                                        <td class="table-number" width="5%">${loop.index+1}</td>
                                        <td class="table-action" width="8%">
                                            <a class="btn-icon waves-effect waves-light btn-primary btn-sm"
                                               onclick="javascript: editForm('${r.shop_code }', '${r.name}');">
                                                <i class="fas fa-wrench"></i>
                                            </a>
                                            <a class="btn-icon waves-effect waves-light btn-success btn-sm"
                                               onclick="javascript: getMangerUser('${r.shop_code }','${r.name}');">
                                                <i class="fas fa-info"></i>
                                            </a>
                                            <a class="btn-icon waves-effect waves-light btn-danger btn-sm"
                                               onclick="javascript: getShopIP('${r.shop_code }','${r.name}');">
                                                <i class="fas fa-wifi"></i>
                                            </a>
                                        </td>
                                        <td width="7%">${r.shop_code}</td>
                                        <td width="25%">${r.name}</td>
                                        <td width="25%">${r.address}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                        <!-- end .table-responsive-->
                        <input type="hidden" name="crudaction" value="delete"/>
                    </form:form>
                    <%@include file="user_list.jsp" %>
                    <%@include file="insert_ip.jsp" %>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('#myModal').on('hidden.bs.modal', function () {
            $("#ten").text('');
            $("#name").val('');
            $("#address").val('');
        });
    });

    function getMangerUser(id, name) {
        $("#loadingEvent2").show();
        $('#depart').text(name);
        document.getElementById('depart_id').value = id;
        if ($.fn.DataTable.isDataTable('#example2')) {
            $('#example2').DataTable().destroy();
        }
        $('#table2').hide();
        fetch_data(id);
    }

    function fetch_data(id) {
        $.ajax({
            url: '<c:url value="/ajax/getUserByDepartmentId.html" />',
            type: 'get',
            data: {
                departmentId: id
            },
            dataType: 'json',
            success: function (data) {
                $('#example2').DataTable({
                    data: data,
                    columns: [
                        {
                            title: "<spring:message code="label.action"/>", data: "managerUserId",
                            render: function (id, type, row) {
                                return "<a class=\'btn-icon waves-effect waves-light btn-primary btn-sm\' onclick=\'updateMangerUser(" + id + ")\'><i class='fa fa-edit'></i></a>" +
                                    "   <a class=\'btn-icon waves-effect waves-light btn-danger btn-sm\' onclick=\'deleteMangerUser(" + JSON.stringify(row) + ")\'><i class='fa fa-minus'></i></a>";
                            }
                        },
                        {title: "<spring:message code="label.user_name"/>", data: "username"},
                        {title: "<spring:message code="label.user.email"/>", data: "email"},
                        {title: "<spring:message code="label.department.position"/>", data: "position"},
                    ]
                });

                $("#loadingEvent2").hide();
                $('#table2').show();
            }
        });
        $('#myModal2').modal('show');
    }


    function editForm(shop_code, name) {
        $("#ten").text(name);
        $("#shop_code").val('');
        $("#name").val('');
        $("#grantedIp_shop").val('');
        $.ajax({
            url: '<c:url value="/ajax/getDepartmentById.html" />',
            type: 'get',
            data: {
                departmentId: shop_code
            },
            dataType: 'json',
            success: function (res) {
                $("#name").val(res.shopName);
                $("#grantedIp_shop").val(res.ipGranted);
                if (res.shopType == 1) {
                    $("#shop_type").val('1')
                } else {
                    $("#shop_type").val('0')
                }
            }
        });
        $("#shop_code").val(shop_code);
        $('#myModal').modal('show');
    }

    function deleteMangerUser(row) {
        bootbox.confirm("<spring:message code='msg.confirm.content'/>" + row.username + "\n" +
            "<spring:message code='msg.confirm.note'/>", function (result) {
            if (result) {
                $.ajax({
                    url: '<c:url value="/ajax/deleteUserbyId.html" />',
                    type: 'delete',
                    data: {
                        managerUserId: row.managerUserId
                    },
                    dataType: 'json',
                    success: function (res) {
                    }
                });
                var depart_name = document.getElementById('depart').value
                getMangerUser(row.managerUserId, depart_name);
            }
        });
    }

    function updateMangerUser(id) {
        $.ajax({
            url: '<c:url value="/ajax/getMangerUserById.html" />',
            type: 'get',
            data: {
                managerUserId: id
            },
            dataType: 'json',
            success: function (res) {
                document.getElementById('addManger').click();
                $("#idDemo01").val(id);
                $("#userDemo01").val(res.username);
                $("#emailDemo01").val(res.email);
                $("#positionDemo01").val(res.position);
            }
        });
    }

    function addUser() {
        var id = document.getElementById('idDemo01').value;
        var user = document.getElementById('userDemo01').value;
        var email = document.getElementById('emailDemo01').value;
        var position = document.getElementById('positionDemo01').value;
        var departmentId = document.getElementById('depart_id').value
        if (email != '' && position != '' && departmentId != '') {
            $.ajax({
                url: '<c:url value="/ajax/addUser.html" />',
                type: 'get',
                data: {
                    id: id,
                    user_name: user,
                    email: email,
                    position: position,
                    departmentId: departmentId
                },
                dataType: 'json',
                success: function (data) {
                    $("#idDemo01").val('');
                    $("#userDemo01").val('');
                    $("#emailDemo01").val('');
                    $("#positionDemo01").val('');
                    $("#loadingEvent2").show();
                    if ($.fn.DataTable.isDataTable('#example2')) {
                        $('#example2').DataTable().destroy();
                    }
                    $('#table2').hide();
                    fetch_data(departmentId);

                    document.getElementById('addManger').click();
                }
            });
        }
    }

    function getShopIP(code, name) {
        $.ajax({
            url: '<c:url value="/ajax/findgrantedIpByShopcode.html" />',
            type: 'get',
            data: {
                shop_code: code
            },
            dataType: 'json',
            success: function (res) {
                console.log("RES" + JSON.stringify(res));
                if (res.ipGranted != null) {
                    $('#ip').text(res.ipGranted);
                    if (res.shopType == 1) {
                        $('#ip_mSale').text('<spring:message code='msg.shopcode.ip_m_safe'/>');
                    }
                    document.getElementById('success').style.display = 'block';
                    document.getElementById('error').style.display = 'none';
                } else {
                    document.getElementById('success').style.display = 'none';
                    document.getElementById('error').style.display = 'block';
                    $('#error-messge').text('<spring:message code='msg.shopcode.ip'/>')
                }
            },
            error: function (error) {
                document.getElementById('success').style.display = 'none';
                document.getElementById('error').style.display = 'block';
                $('#error-messge').text('<spring:message code='msg.shopcode.ip'/>')
            }
        });
        $('#shopName_grantedIp').text(name);
        $('#ipGrantedModal').modal('show');
        $('#mSaleModal').modal('hide');
    }

    function get_mSaleIP() {
        $.ajax({
            url: '<c:url value="/ajax/findgrantedIpByShopcode.html" />',
            type: 'get',
            data: {
                shop_code: 'mSale'
            },
            dataType: 'json',
            success: function (res) {
                $('#m_sale_ip').val(res.ipGranted);
            }
        });
        $('#mSaleModal').modal('show');
        $('#ipGrantedModal').modal('hide');
    }
    // Update IP PHONG BAN:
    var form_updateIP_Shopcode = document.getElementById('updateIP_Shop');
    var ip_update_ShopCode = document.update_ip_shopcode.grantedIp_shop;
    form_updateIP_Shopcode.addEventListener('submit', function (e) {
        e.preventDefault();
        var rsValidateIP = validateIp_UpdateForm();
        if (rsValidateIP) {
            form_updateIP_Shopcode.submit();
        }
    })

    function validateIp_UpdateForm(){
        if (ip_update_ShopCode.value) {
            const ips = ip_update_ShopCode.value.split(";");

            for (const ip_g of ips) {
                if (!isIPValid(ip_g) && !isIPRangeValid(ip_g)) {
                    ip_update_ShopCode.classList.add("is-invalid");
                    ip_update_ShopCode.classList.remove("is-valid");
                    document.getElementById('ip-granted-validation').textContent = "<spring:message code="label.c2user.message-ip-reges" /> ";
                    return false; // Không đúng định dạng
                }
            }
            ip_update_ShopCode.classList.remove("is-invalid");
            ip_update_ShopCode.classList.add("is-valid");
            document.getElementById('ip-granted-validation').textContent = '';
            return true; // Đúng định dạng
        } else {
            ip_update_ShopCode.classList.add("is-invalid");
            ip_update_ShopCode.classList.remove("is-valid");
            document.getElementById('ip-granted-validation').textContent = "<spring:message code="label.c2user.message-ip" /> ";
            return false;
        }
    }

    // validate - IP MSALE:
    var form = document.getElementById('changeIp_mSale');
    var ip = document.ip_granted_form.m_sale_ip;

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        var rsValidateIP = validateIP();
        if (rsValidateIP) {
            form.submit();
        }
    })

    function isIPValid(ip) {
        const ipRegex = /^(\d{1,3}\.){3}\d{1,3}$/;
        return ipRegex.test(ip);
    }

    function isIPRangeValid(ipRange) {
        const ipRangeRegex = /^(\d{1,3}\.){3}\d{1,3}-(\d{1,3}\.){3}\d{1,3}$/;
        return ipRangeRegex.test(ipRange);
    }

    function isIPorIPRangeValid(input) {
        const ips = input.split(";");

        for (const ip_g of ips) {
            if (!isIPValid(ip_g) && !isIPRangeValid(ip_g)) {
                ip.classList.add("is-invalid");
                ip.classList.remove("is-valid");
                document.getElementById('ip-validation').textContent = "<spring:message code="label.c2user.message-ip-reges" /> ";
                return false; // Không đúng định dạng
            }
        }
        ip.classList.remove("is-invalid");
        ip.classList.add("is-valid");
        document.getElementById('ip-validation').textContent = '';
        return true; // Đúng định dạng
    }

    function validateIP() {
        if (ip.value) {
            return isIPorIPRangeValid(ip.value);
        } else {
            ip.classList.add("is-invalid");
            ip.classList.remove("is-valid");
            document.getElementById('ip-validation').textContent = "<spring:message code="label.c2user.message-ip" /> ";
            return false;
        }
    }

</script>
</body>
</html>
