<%--<jsp:useBean id="employeeId" scope="request" type=""/>--%>
<% int employeeId = 1; %>
<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 5/27/2024
  Time: 4:41 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/guest/request-user.html"/>
<html>
<head>
    <title><spring:message code="menu.guest.request"/></title>
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
                        <li class="breadcrumb-item active"><spring:message code="menu.guest.request"/></li>
                    </ol>
                </div>
                <h4 class="page-title"><spring:message code="menu.guest.request"/></h4>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <form:form id="proposeForm" modelAttribute="item" action="${formURL}" method="post"
                       enctype="multipart/form-data" name="proposeForm">
                <div class="modal-content" style="width: 1200px;">
                    <div class="modal-header">
                        <h4 class="modal-title mt-0">
                            <spring:message code="label.form.detail"/>
                            <span id="usernameFile"></span>
                        </h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="file" class="control-label"><spring:message
                                            code="label.guest.file"/></label>
                                    <form:input type="file" id="file" path="pojo.file" cssClass="form-control"
                                                accept=".png,.pdf,.img" onblur="validateFile()"/>
                                    <small class="text-danger" id="file-validation"></small>
                                </div>
                            </div>
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="sender" class="control-label"><spring:message
                                            code="label.guest.sender"/> </label>
                                    <form:input type="text" id="sender" path="pojo.sender" cssClass="form-control"
                                                cssStyle="text-transform: uppercase" onblur="validateSender()"/>
                                    <small class="text-danger" id="sender-validation"></small>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <button id="addRowBtn" class="btn btn-info waves-effect waves-light"><i
                                        class="fas fa-plus"></i></button>
                                <button id="sendBtn">Send</button>
                            </div>
                            <div class="col-md-12">
                                <table class="table table-striped">
                                    <thead>
                                    <tr>
                                        <th scope="col">#</th>
                                        <th scope="col">First</th>
                                        <th scope="col">Last</th>
                                        <th scope="col">Handle</th>
                                    </tr>
                                    </thead>
                                    <tbody>
                                    <tr>
                                        <th scope="row">1</th>
                                        <td>Mark</td>
                                        <td>Otto</td>
                                        <td>@mdo</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">2</th>
                                        <td>Jacob</td>
                                        <td>Thornton</td>
                                        <td>@fat</td>
                                    </tr>
                                    <tr>
                                        <th scope="row">3</th>
                                        <td>Larry</td>
                                        <td>the Bird</td>
                                        <td>@twitter</td>
                                    </tr>
                                    </tbody>
                                </table>
                            </div>
                                <%--                            <div class="col-md-12">--%>
                                <%--                                <table id="datatable" class="table table-bordered"--%>
                                <%--                                       style="border-collapse: collapse; border-spacing: 0; width: 100%;">--%>
                                <%--                                    <thead>--%>
                                <%--                                    <tr>--%>
                                <%--                                        <th>#</th>--%>
                                <%--                                        <th><spring:message code="label.action"/></th>--%>
                                <%--                                        <th><spring:message code="label.username"/></th>--%>
                                <%--                                        <th><spring:message code="label.guest.typePropose"/></th>--%>
                                <%--                                        <th><spring:message code="label.guest.updateDate"/></th>--%>
                                <%--                                    </tr>--%>
                                <%--                                    </thead>--%>
                                <%--                                    <tbody>--%>
                                <%--                                    </tbody>--%>
                                <%--                                </table>--%>
                                <%--                            </div>--%>
                        </div>
                        <div class="row">
                            <div class="col-md-12">
                                <label for="message" class="control-label"><spring:message
                                        code="label.guest.message"/> </label>
                                <form:textarea type="text" id="message" path="pojo.message" name="message"
                                               cssClass="form-control" onblur="validateMessage()"
                                               style="overflow: hidden; word-wrap: break-word; resize: horizontal; height: 150px;"/>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary waves-effect" data-dismiss="modal">
                            <spring:message
                                    code="button.close"/></button>
                        <button type="submit" class="btn btn-info waves-effect waves-light"><spring:message
                                code="button.send"/></button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>
<script>
    // var table = new DataTable('#datatable', {
    //     //initialization params as usual
    //     fnInitComplete: function () {
    //         if ($(this).find(".dataTables_empty").length == 1) {
    //             $(this).parent().hide();
    //         }
    //     }
    // });

    // FORM SUBMIT
    var form = document.getElementById('proposeForm');
    var file = document.proposeForm.file;
    var sender = document.proposeForm.sender;
    var message = document.proposeForm.message;

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        var rsFile = validateFile();
        var rsMessage = validateMessage();
        if (rsFile && rsMessage) {
            $.ajax({
                url: '<c:url value="/ajax/checkUserExist.html" />',
                type: 'get',
                data: {
                    user_name: sender.value.toUpperCase()
                },
                dataType: 'json',
                success: function (res) {
                    console.log("RES:" + res);
                    if (res) {
                        form.submit();
                    } else {
                        sender.classList.remove("is-valid");
                        sender.classList.add("is-invalid");
                        document.getElementById('sender-validation').textContent = '<spring:message code="label.c2user.message-user_name-no-exits" />';
                    }
                }
            });
        }
    })
    // inline table
    // Tạo data ở trong table:
    <%--const typePropose = `<select class="form-control typeProposeDropdown">--%>
    <%--                <option value="-1"><spring:message code="label.guest.typePropose.lock"/></option>--%>
    <%--                <option value="1"><spring:message code="label.guest.typePropose.unlock"/></option>--%>
    <%--                        </select>--%>
    <%--`;--%>

    <%--$(document).ready(function () {--%>

    <%--    // Function to add a new row--%>
    <%--    $("#addRowBtn").click(function () {--%>
    <%--        let newRow = `<tr>--%>
    <%--                                    <td><%=employeeId%></td>--%>
    <%--                                    <td>--%>
    <%--                                        <button class="btn btn-secondary waves-effect saveBtn">Save</button>--%>
    <%--                                        <button class="btn btn-danger waves-effect deleteBtn">Delete</button>--%>
    <%--                                    </td>--%>
    <%--                                    <td contenteditable="true">Nhập tên</td>--%>
    <%--                                    <td contenteditable="true">${typePropose}</td>--%>
    <%--                                    <td contenteditable="true">--%>
    <%--                                        <input type="date" class="form-control updateDate">--%>
    <%--                                    </td>--%>
    <%--                                </tr>--%>
    <%--        `;--%>
    <%--        $('#datatable tbody').append(newRow);--%>
    <%--        // table.row.add($("<tr>" +--%>
    <%--        //     "<td></td>" +--%>
    <%--        //     "<td></td>" +--%>
    <%--        //     "<td></td>" +--%>
    <%--        //     "<td></td>" +--%>
    <%--        //     "<td></td>" +--%>
    <%--        //     "</tr>")).draw();--%>
    <%--    });--%>

    <%--    // Function to save changes--%>
    <%--    $(document).on("click", ".saveBtn", function () {--%>
    <%--        let row = $(this).closest("tr");--%>
    <%--        row.find("td").attr("contenteditable", "false")--%>

    <%--        // replace dropdown with selected text--%>
    <%--        let selectedTypePropose = row.find(".typeProposeDropdown").val();--%>
    <%--        row.find(".typeProposeDropdown").parent().text(selectedTypePropose);--%>

    <%--        //  replace date input with selected date--%>
    <%--        let updateDate = row.find(".updateDate").val();--%>
    <%--        row.find(".updateDate").parent().text(updateDate);--%>

    <%--        // change button: SAVE <> EDIT DELETE--%>
    <%--        $(this).sibling(".editBtn").show();--%>
    <%--        $(this).sibling(".deleteBtn").show();--%>
    <%--        $(this).text("Edit").removeClass("saveBtn").addClass("editBtn")--%>

    <%--        // Function to edit a row: -> transfer data -> input:text--%>
    <%--        $(document).one("click", ".editBtn", function () {--%>
    <%--            let row = $(this).closest("tr");--%>
    <%--            row.find("td").attr("contenteditable", "true");--%>

    <%--            // Replace type propose text with dropdown--%>
    <%--            let typeProposeCurrent = row.find("td:eq(3)").text();--%>
    <%--            row.find("td:eq(3)").html(typePropose);--%>
    <%--            row.find(".typeProposeDropdown").val(typeProposeCurrent)--%>

    <%--            // Replace update date with input:--%>
    <%--            let currentDate = row.find("td:eq(4)").text();--%>
    <%--            row.find("td:eq(4)").html(--%>
    <%--                `<input type="date" class="form-control updateDate" value="${currentDate}">`--%>
    <%--            );--%>

    <%--            $(this).sibling(".deleteBtn").hide();--%>
    <%--            $(this).text("Save").removeClass("editBtn").addClass("saveBtn");--%>
    <%--        });--%>

    <%--        // Function to delete a row--%>
    <%--        $(document).on("click", ".deleteBtn", function () {--%>
    <%--            $(this).closest("tr").remote();--%>
    <%--        });--%>

    <%--        // send data: SUA LAI:--%>
    <%--        $("#senBtn").click(function () {--%>
    <%--            let tableData = [];--%>
    <%--            $("#datatable tbody tr").each(function () {--%>
    <%--                let row = $(this);--%>
    <%--                if (!row.find("td").is('[contenteditable="true"]')) {--%>
    <%--                    let rowData = {--%>
    <%--                        id: row.find("td:eq[0]").text(),--%>
    <%--                        user: row.find("td:eq[1]").text(),--%>
    <%--                        type: row.find("td:eq[2]").text(),--%>
    <%--                        updateDate: row.find("td:eq[3]").text()--%>
    <%--                    };--%>
    <%--                    tableData.push(rowData);--%>
    <%--                }--%>
    <%--            });--%>
    <%--            console.log(tableData)--%>
    <%--        })--%>
    <%--    });--%>
    <%--});--%>

    // validate:
    function validateFile() {
        if (file.value) {
            file.classList.remove("invalid");
            document.getElementById('file-validation').textContent = '';
            return true;
        } else {
            file.classList.add("invalid");
            document.getElementById('file-validation').textContent = "<spring:message code="label.c2user.message-file" />";
            return false;
        }
    }

    function validateSender() {
        if (sender.value) {
            $.ajax({
                url: '<c:url value="/ajax/checkUserExist.html" />',
                type: 'get',
                data: {
                    user_name: sender.value.toUpperCase()
                },
                dataType: 'json',
                success: function (res) {
                    if (res) {
                        console.log("RES:" + res);
                        sender.classList.remove("is-invalid");
                        sender.classList.add("is-valid");
                        document.getElementById('sender-validation').textContent = '';
                        return true;
                    } else {
                        sender.classList.remove("is-valid");
                        sender.classList.add("is-invalid");
                        document.getElementById('sender-validation').textContent = '<spring:message code="label.c2user.message-user_name-no-exits" />';
                        return false;
                    }
                }, error: function (error) {
                    console.log("ERROR:" + error)
                    return false;
                }
            });
        } else {
            sender.classList.add("is-invalid");
            sender.classList.remove("is-valid");
            document.getElementById('sender-validation').textContent = "<spring:message code="label.c2user.message-user_name" />";
            return false;
        }
    }

    function validateMessage() {
        if (message.value) {
            message.classList.remove("invalid");
            document.getElementById('message-validation').textContent = '';
            return true;
        } else {
            message.classList.add("invalid");
            document.getElementById('message-validation').textContent = "<spring:message code="label.c2user.message-message" />";
            return false;
        }
    }
</script>
</body>
</html>
