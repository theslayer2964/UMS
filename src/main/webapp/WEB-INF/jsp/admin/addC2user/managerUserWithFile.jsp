<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 2/19/2024
  Time: 3:24 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/list/manager_user_c2_file.html"/>
<html>
<head>
    <title><fmt:message key="menu.system.inactivityUser"/></title>
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
                <h4 class="page-title"><fmt:message key="menu.system.inactivityUser"/></h4>
            </div>
        </div>
    </div>
    <div class="card">
        <div class="card-header">
            <spring:message code="menu.system.userDetail"/>
        </div>
        <form:form id="addForm" modelAttribute="item" action="${formURL}" name="myForm"
                   method="post" enctype="multipart/form-data">
            <div class="card-body">
                <div class="form-row">
                    <div class="input-group">
                        <div class="input-group-prepend">
                            <a class="btn-icon waves-effect waves-light btn-warning btn-sm"
                               href="<c:url value="/file/template/TEMPLATE_INACTIVITY_employee.xlsx" />">
                                <i class="fas fa-download" style="margin-top: 8px"></i>
                            </a>
                        </div>
                        <div class="custom-file">
                            <form:input type="file" cssClass="form-control" id="file" accept=".xlsx, .xls" path="file"
                                        onchange="validateFile()"/>
                            <label for="file" class="custom-file-label text-truncate"><spring:message
                                    code="label.c2userfile.chooseFile"/></label>
                        </div>
                    </div>
                    <small class="text-danger" id="file-validation"></small>
                </div>
                <br>
                <div class="form-row">
                    <div class="form-group col-md-12">
                        <div class="custom-control custom-radio">
                            <input type="radio" class="custom-control-input" id="inactivity" value="inactivity" checked
                                   name="customRadioInline1">
                            <label class="custom-control-label" for="inactivity"><spring:message
                                    code="label.c2userfile.inactivity"/></label>
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
            <input type="hidden" name="crudaction" id="crudaction" value="inactivity"/>
            <input type="hidden" name="listRetire" id="listRetire"/>
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
                                <th><spring:message code="label.c2user.shopCode"/></th>
                                <th><spring:message code="label.user.email"/></th>
                                <th><spring:message code="label.c2user.program"/></th>
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
    // message:


    $('#file').on('change', function () {
        let fileName = $(this).val().split('\\').pop();
        $(this).next('.custom-file-label').addClass("selected").html(fileName);
    });

    document.body.addEventListener('change', function (e) {
        if (e.target.id == 'inactivity') {
            $('#fileType').val('inactivity');
            $('#file').val('');
        }
    });

    // END - GUI DATA TO BACKEND
    var form = document.getElementById('addForm');
    form.addEventListener('submit', function (e) {
        e.preventDefault();
        $('#crudaction').val('SAVE_USER_FILE');


        var selectedRow = table.rows('.selected').data();
        const list = [];
        if (selectedRow.length > 0) {
            for (let i = 0; i < selectedRow.length; i++) {
                let row = selectedRow[i];
                let temp = new InactivateUser(row[1], row[2], row[3], row[4]);
                list.push(temp)
            }
        }
        $('#listRetire').val(JSON.stringify(list));
        form.submit();
    })

    function addDataToTable() {
        if (validateFile()) {
            table.row().remove().draw(); // remove all data

            var fileInput = document.getElementById('file');
            var file = fileInput.files[0];

            var reader = new FileReader();
            reader.onload = function (e) {
                var data = new Uint8Array(e.target.result);
                var workbook = XLSX.read(data, {type: 'array'});
                var worksheet = workbook.Sheets[workbook.SheetNames[0]];
                var jsonData = XLSX.utils.sheet_to_json(worksheet, {header: 1});
                for (let i = 1; i <= jsonData.length - 1; i++) {
                    var user_name = jsonData[i][0] != null ? jsonData[i][0].toUpperCase() : '';
                    var shop_code = jsonData[i][1] != null ? jsonData[i][1] : '';
                    var email = jsonData[i][2] != null ? jsonData[i][2] : '';
                    var programs = jsonData[i][3] != null ? jsonData[i][3] : '';
                    table.row.add(['', user_name, shop_code, email, programs]).draw(false);
                }
            };
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


    var file = document.myForm.file;

    function validateFile() {
        if (file.value) {
            file.classList.remove("is-invalid");
            file.classList.add("is-valid");
            document.getElementById('file-validation').textContent = '';
            return true;
        } else {
            file.classList.add("is-invalid");
            file.classList.remove("is-valid");
            document.getElementById('file-validation').textContent = "<spring:message code="label.c2user.message-file" /> ";
            return false;
        }
    }

    // Tao class convert data:
    class InactivateUser {
        constructor(user_name, shop_code, email, program) {
            this.user_name = user_name;
            this.shop_code = shop_code;
            this.email = email;
            this.program = program;
        }
    }
</script>
</body>
</html>
