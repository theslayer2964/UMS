<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 4/22/2024
  Time: 3:16 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/guest/explanation-form.html"/>
<html>
<head>
    <title><spring:message code="menu.guest.explannation-form"/></title>
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
                        <li class="breadcrumb-item active"><spring:message code="menu.guest.explannation-form"/></li>
                    </ol>
                </div>
                <h4 class="page-title"><spring:message code="menu.guest.explannation-form"/></h4>
            </div>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <form:form id="explanationForm" modelAttribute="item" action="${formURL}" method="post"
                       enctype="multipart/form-data" name="explanationForm">
                <div class="modal-content" style="width: 1200px;">
                    <div class="modal-header">
                        <h4 class="modal-title mt-0">
                            <spring:message code="label.user.explain_form"/>
                            <span id="usernameFile"></span>
                        </h4>
                    </div>
                    <div class="modal-body">
                        <div class="row">
                            <div class="col-md-12">
                                <div class="form-group">
                                    <label for="file" class="control-label"><spring:message code="label.file"/></label>
                                    <form:input type="file" id="file" path="file" cssClass="form-control"
                                                accept=".png,.pdf,.img" onblur="validateFile()"/>
                                    <small class="text-danger" id="file-validation"></small>
                                </div>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-md-6">
                                <div class="form-group">
                                    <label for="username" class="control-label"><spring:message
                                            code="label.user_name"/> </label>
                                    <form:input type="text" id="username" path="username" cssClass="form-control"
                                                cssStyle="text-transform: uppercase" onblur="validateUsername2()"/>
                                    <small class="text-danger" id="username-validation"></small>
                                </div>
                            </div>
                            <div class="col-md-6" style="display: none">
                                <div class="form-group">
                                    <label for="updateDate" class="control-label"><spring:message
                                            code="label.uploadDate"/> </label>
                                    <form:input type="date" id="updateDate" path="uploadDate" cssClass="form-control"/>
                                    <small class="text-danger" id="updatedate-validation"></small>
                                </div>
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
                <!-- hidden field -->
                <input type="hidden" name="crudaction" value="upload"/>
                <%--            <form:hidden path="username" id="usernameValue"/>--%>
                <form:hidden path="form_type" id="form_type" value='0'/>
            </form:form>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        /// Ngày tháng năm
        var date = new Date().toISOString().substring(0, 10);
        document.getElementById('updateDate').value = date;
    });
    var form = document.getElementById('explanationForm');
    var file = document.explanationForm.file;
    var username = document.explanationForm.username;
    var updateDate = document.explanationForm.updateDate;
    var today = new Date();

    form.addEventListener('submit', function (e) {
        e.preventDefault();
        if (validateFile()) {
            $.ajax({
                url: '<c:url value="/ajax/checkUserExist.html" />',
                type: 'get',
                data: {
                    user_name: username.value.toUpperCase()
                },
                dataType: 'json',
                success: function (res) {
                    console.log("XXXX:" + res);
                    if (res) {
                        form.submit();
                    } else {
                        username.classList.remove("is-valid");
                        username.classList.add("is-invalid");
                        document.getElementById('username-validation').textContent = '<spring:message code="label.c2user.message-user_name-no-exits" />';
                    }
                }
            });
        }
    });

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

    function validateUsername2() {
        if (username.value) {
            $.ajax({
                url: '<c:url value="/ajax/checkUserExist.html" />',
                type: 'get',
                data: {
                    user_name: username.value
                },
                dataType: 'json',
                success: function (res) {
                    if (res) {
                        console.log("RES:" + res);
                        username.classList.remove("is-invalid");
                        username.classList.add("is-valid");
                        document.getElementById('username-validation').textContent = '';
                        return true;
                    } else {
                        username.classList.remove("is-valid");
                        username.classList.add("is-invalid");
                        document.getElementById('username-validation').textContent = '<spring:message code="label.c2user.message-user_name-no-exits" />';
                        return false;
                    }
                }, error: function (error) {
                    console.log("ERROR:" + error)
                    return false;
                }
            });
        } else {
            username.classList.add("is-invalid");
            username.classList.remove("is-valid");
            document.getElementById('username-validation').textContent = "<spring:message code="label.c2user.message-user_name" />";
            return false;
        }
    }
</script>
</body>
</html>
