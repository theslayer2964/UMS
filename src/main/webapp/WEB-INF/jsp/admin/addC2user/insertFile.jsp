<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 1/29/2024
  Time: 4:42 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/list/add_user_c2_file.html"/>
<html>
<head>
    <title><fmt:message key="menu.system.addUserFile"/></title>
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
                <h4 class="page-title"><fmt:message key="menu.system.addUserFile"/></h4>
            </div>
        </div>
    </div>
    <div class="card">
        <div class="card-header">
            <spring:message code="menu.system.userDetail"/>
        </div>
        <form:form id="addForm" modelAttribute="item" action="${formURL}" method="post" enctype="multipart/form-data"
                   name="addForm">
            <div class="card-body">
                <div class="form-row">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <button class="btn-icon waves-effect waves-light btn-warning btn-sm" type="button"
                                    onclick="javascript: downloadFile();">
                                <i class="fas fa-download"></i>
                            </button>
                        </div>
                        <div class="custom-file">
                            <form:input type="file" cssClass="form-control" id="file" name="file"
                                        onblur="validateFile()" accept=".xlsx" path="file"/>
                            <label for="file" class="custom-file-label text-truncate"><spring:message
                                    code="label.c2userfile.chooseFile"/></label></label>
                        </div>
                    </div>
                    <small class="text-danger" id="file-validation"></small>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-12">
                        <div class="custom-control custom-radio">
                            <input type="radio" class="custom-control-input" id="add" value="ADD" checked
                                   name="customRadioInline1">
                            <label class="custom-control-label" for="add"><spring:message
                                    code="label.c2userfile.add"/></label>
                        </div>
                        <div class="custom-control custom-radio">
                            <input type="radio" class="custom-control-input" id="update" value="UPDATE"
                                   name="customRadioInline1">
                            <label class="custom-control-label" for="update"><spring:message
                                    code="label.c2userfile.update"/></label>
                        </div>
                    </div>
                </div>
                <div class="form-row">
                    <button type="button" class="btn btn-info waves-effect waves-light" onclick="addDataToTable()">
                        <spring:message code="button.list"/></button>
                    <button type="submit" class="btn btn-info waves-effect waves-light">
                        <spring:message
                                code="button.execute"/></button>
                </div>
            </div>
            <input type="hidden" name="crudaction" id="crudaction" value="ADD"/>
            <input type="hidden" name="fileType" id="fileType" value="add"/>
            <input type="hidden" name="listUserAuto" id="listUserAuto"/>
        </form:form>
        <div class="card">
            <div class="card-body">
                <form:form id="frm-example" action="${formURL}" method="POST">
                    <div class="table-responsive">
                        <table id="example" class="table table-bordered"
                               style="border-collapse: collapse; border-spacing: 0;width:100%">
                            <thead>
                            <tr>
                                <th>
                                    <input type="checkbox" class="selectAll" name="selectAll" value="all">
                                </th>
                                <th><spring:message code="label.username"/></th>
                                <th id="username"><spring:message code="label.user_name"/></th>
                                <th id="phone"><spring:message code="label.c2user.phone"/></th>
                                <th id="birthday"><spring:message code="label.c2user.birthday"/></th>
                                <th id="cmnd"><spring:message code="label.c2user.cmnd"/></th>
                                <th id="ngaycap"><spring:message code="label.c2user.ngaycap"/></th>
                                <th id="noicap"><spring:message code="label.c2user.noicap"/></th>
                                <th id="shop_old"><spring:message code="label.c2user.tothu+CSKK"/></th>
                                <th id="shop_new"><spring:message code="label.shopcode"/></th>
                                <th id="ip"><spring:message code="label.IP"/></th>
                                <th id="schedular"><spring:message code="label.c2user.schedular"/></th>
                                <th id="status"><spring:message code="label.user.status"/></th>
                                <th id="employeeType"><spring:message code="label.c2user.employeeType"/></th>
                                <th id="city"><spring:message code="label.c2user.city"/></th>
                                <th id="district"><spring:message code="label.c2user.district"/></th>
                                <th id="program"><spring:message code="label.c2user.program"/></th>
                                <th id="groupUser"><spring:message code="label.user.group"/></th>
                                <th id="position"><spring:message code="label.department.position"/></th>
                                <th id="email"><spring:message code="label.cskk.email"/></th>
                                <th id="ldap"><spring:message code="label.add-user-file.ldap"/></th>
                            </tr>
                            </thead>
                        </table>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        // Thông báo KQ: DS USER: (user - ds chương trình thành công)
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

    $('#file').on('change', function () {
        let fileName = $(this).val().split('\\').pop();
        $(this).next('.custom-file-label').addClass("selected").html(fileName);
    });

    document.body.addEventListener('change', function (e) {
        if (e.target.id == 'add') {
            // remove all
            table.rows().remove().draw();
            // REMOTE FILE NAME:
            document.getElementsByClassName('custom-file-label').innerHTML = "Chọn file..."
            $('#file').val('');

            $('#fileType').val('add');
            $('#example').show();
            document.getElementById("username").removeAttribute("hidden");
            document.getElementById("phone").removeAttribute("hidden");
            document.getElementById("birthday").removeAttribute("hidden");
            document.getElementById("cmnd").removeAttribute("hidden");
            document.getElementById("ngaycap").removeAttribute("hidden");
            document.getElementById("noicap").removeAttribute("hidden");
            document.getElementById("schedular").removeAttribute("hidden");
            document.getElementById("status").removeAttribute("hidden");
            document.getElementById("employeeType").removeAttribute("hidden");
            document.getElementById("city").removeAttribute("hidden");
            document.getElementById("groupUser").removeAttribute("hidden");
            document.getElementById("position").removeAttribute("hidden");
            document.getElementById("email").removeAttribute("hidden");
            document.getElementById("ldap").removeAttribute("hidden");
            document.getElementById("shop_old").innerHTML = "<spring:message code="label.c2user.tothu"/>";
            document.getElementById("shop_new").innerHTML = "<spring:message code="label.shopcode"/>";
            document.getElementById("district").innerHTML = "<spring:message code="label.c2user.district"/>";
        } else if (e.target.id == 'update') {
            // remove all
            table.rows().remove().draw();
            // REMOTE FILE NAME:
            document.getElementsByClassName('custom-file-label').innerHTML = "Chọn file..."
            $('#file').val('');

            $('#fileType').val('update');
            document.getElementById("phone").setAttribute("hidden", true);
            document.getElementById("birthday").setAttribute("hidden", true);
            document.getElementById("cmnd").setAttribute("hidden", true);
            document.getElementById("ngaycap").setAttribute("hidden", true);
            document.getElementById("noicap").setAttribute("hidden", true);
            document.getElementById("schedular").setAttribute("hidden", true);
            document.getElementById("status").setAttribute("hidden", true);
            document.getElementById("employeeType").setAttribute("hidden", true);
            document.getElementById("city").setAttribute("hidden", true);
            document.getElementById("username").setAttribute("hidden", true);
            document.getElementById("groupUser").setAttribute("hidden", true);
            document.getElementById("position").setAttribute("hidden", true);
            document.getElementById("email").setAttribute("hidden", true);
            document.getElementById("ldap").setAttribute("hidden", true);
            document.getElementById("shop_old").innerHTML = "<spring:message code="label.c2user.shopcode_old"/>";
            document.getElementById("shop_new").innerHTML = "<spring:message code="label.c2user.shopcode_new"/>";
            document.getElementById("district").innerHTML = "<spring:message code="label.user.email"/>"
        }
    })

    function downloadFile() {
        if (document.getElementById('fileType').value === 'add') {
            window.location.href = "<c:url value="/file/template/TEMPLATE_FormEmployeeFile.xlsx" />"
        } else if (document.getElementById('fileType').value === 'update') {
            window.location.href = "<c:url value="/file/template/TEMPLATE_ChangeShop.xlsx" />"
        }
    }

    var form = document.getElementById('addForm');
    form.addEventListener('submit', function (e) {
        e.preventDefault();
        $('#crudaction').val('SAVE_USER_FILE');
        var selectedRow = table.rows('.selected').data();
        const list = [];
        if (selectedRow.length > 0) {
            for (let i = 0; i < selectedRow.length; i++) {
                let row = selectedRow[i];
                let temp = new C2AdminUserAuto(row[1], row[2], row[3], row[4], row[5], row[6], row[7], row[8], row[9],
                    row[10], row[11], row[12], row[13], row[14], row[15], row[16], row[17], row[18], row[19], row[20]);
                list.push(temp)
            }
        }
        $('#listUserAuto').val(JSON.stringify(list));
        form.submit();
    })

    function addDataToTable() {
        if (validateFile()) {
            // remove all
            table.row().remove().draw();

            var fileInput = document.getElementById('file');
            var file = fileInput.files[0];

            var reader = new FileReader();
            if (document.getElementById('fileType').value === 'add') {
                reader.onload = function (e) {
                    var data = new Uint8Array(e.target.result);
                    var workbook = XLSX.read(data, {type: 'array'});
                    var worksheet = workbook.Sheets[workbook.SheetNames[0]];
                    var jsonData = XLSX.utils.sheet_to_json(worksheet, {header: 1});
                    for (let i = 1; i <= jsonData.length - 1; i++) {
                        var user_name = jsonData[i][0] != null ? jsonData[i][0].toUpperCase() : '';
                        var full_name = jsonData[i][1] != null ? jsonData[i][1] : '';
                        var phone = jsonData[i][2] != null ? jsonData[i][2] : '';
                        var birthday = jsonData[i][3] != null ? jsonData[i][3] : '';
                        var cmnd = jsonData[i][4] != null ? jsonData[i][4] : '';
                        var ngay_cap = jsonData[i][5] != null ? jsonData[i][5] : '';
                        var noi_cap = jsonData[i][6] != null ? jsonData[i][6] : '';
                        var to_thu = jsonData[i][7] != null ? jsonData[i][7] : '';
                        var shop_code = jsonData[i][8] != null ? jsonData[i][8] : '';
                        var ip = jsonData[i][9] != null ? jsonData[i][9] : '';
                        var lich = jsonData[i][10] != null ? jsonData[i][10] : '';
                        var status = jsonData[i][11] != null ? jsonData[i][11] : '';
                        var loaiNV_CSKH = jsonData[i][12] != null ? jsonData[i][12] : '';
                        var city = jsonData[i][13] != null ? jsonData[i][13] : '';
                        var district = jsonData[i][14] != null ? jsonData[i][14] : '';
                        var program = jsonData[i][15] != null ? jsonData[i][15] : '';
                        var groupUser = jsonData[i][16] != null ? jsonData[i][16] : '';
                        var position = jsonData[i][17] != null ? jsonData[i][17] : '';
                        var email = jsonData[i][18] != null ? jsonData[i][18] : '';
                        var ldap = jsonData[i][19] != null ? jsonData[i][19] : '';
                        table.row.add(['', user_name, full_name, phone, birthday, cmnd, ngay_cap, noi_cap, to_thu, shop_code, ip, lich, status, loaiNV_CSKH, city, district, program, groupUser, position, email, ldap]).draw(false);
                    }
                };
            } else if (document.getElementById('fileType').value === 'update') {
                reader.onload = function (e) {
                    var data = new Uint8Array(e.target.result);
                    var workbook = XLSX.read(data, {type: 'array'});
                    var worksheet = workbook.Sheets[workbook.SheetNames[0]];
                    var jsonData = XLSX.utils.sheet_to_json(worksheet, {header: 1});
                    for (let i = 1; i <= jsonData.length - 1; i++) {
                        var user_name = jsonData[i][0] != null ? jsonData[i][0].toUpperCase() : '';
                        var to_thu = jsonData[i][1] != null ? jsonData[i][1] : '';
                        var shop_code = jsonData[i][2] != null ? jsonData[i][2] : '';
                        var granted_ip = jsonData[i][3] != null ? jsonData[i][3] : '';
                        var email = jsonData[i][4] != null ? jsonData[i][4] : '';
                        var program = jsonData[i][5] != null ? jsonData[i][5] : '';
                        table.row.add(['', user_name, to_thu, shop_code, granted_ip, email, program, '', '', '', '', '', '', '', '', '', '', '', '', '']).draw(false);
                    }
                };
            }
            reader.readAsArrayBuffer(file);
        }
    }

    // check row:
    var table = new DataTable('#example', {
        columnDefs: [
            {
                orderable: false,
                className: 'select-checkbox',
                targets: 0,
                'checkboxes': {
                    'selectRow': true
                }
            }
        ],
        select: {
            'style': 'multi'
        },
        order: [[1, 'asc']]
    });

    $(document).ready(function () {
        $(".selectAll").on("click", function (e) {
            if ($(this).is(":checked")) {
                table.rows({page: 'current'}).select();
            } else {
                table.rows({page: 'current'}).deselect();
            }
        });
    });

    class C2AdminUserAuto {
        constructor(user_name, full_name, phone, birthday, cmnd, ngaycap, noicap, toThu, shop_code, granted_ip,
                    access_schedule, status, type, city, district, program, groupUser, position, email, ldap) {
            this.user_name = user_name;
            this.full_name = full_name;
            this.phone = phone;
            this.birthday = birthday;
            this.cmnd = cmnd;
            this.toThu = toThu;
            this.shop_code = shop_code;
            this.granted_ip = granted_ip;
            this.access_schedule = access_schedule;
            this.ngaycap = ngaycap;
            this.noicap = noicap;
            this.status = status;
            this.type = type;
            this.city = city;
            this.district = district;
            this.program = program;
            this.groupUser = groupUser;
            this.position = position;
            this.email = email;
            this.ldap = ldap;
        }
    }

    // validate:
    var file = document.addForm.file;

    function validateFile() {
        if (file.value) {
            file.classList.remove("invalid");
            document.getElementById('file-validation').textContent = '';
            return true;
        } else {
            file.classList.add("invalid");
            document.getElementById('file-validation').textContent = "<spring:message code="label.c2user.message-file" /> ";
            return false;
        }
    }
</script>
</body>
</html>
