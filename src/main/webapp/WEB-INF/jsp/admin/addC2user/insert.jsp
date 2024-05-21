<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 1/17/2024
  Time: 2:00 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/list/add_user_c2.html"/>
<html>
<head>
    <title><fmt:message key="menu.system.addUser"/></title>
</head>
<style>
    .custom-file-input:invalid {
        border: 10px solid red;
    }
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
                <h4 class="page-title"><fmt:message key="menu.system.addUser"/></h4>
            </div>
        </div>
    </div>
    <div class="card">
        <form:form id="addForm" modelAttribute="item" method="post" action="${formURL}" enctype="multipart/form-data"
                   name="myForm">
            <div class="card-body">
                <div class="card" style="border: solid black 1px">
                    <div class="card-header">
                        <spring:message code="menu.system.user_total"/>
                    </div>
                    <div class="card-body">
                        <div class="form-row">
                            <div class="form-group col-md-3">
                                <label for="file" class="control-label"><spring:message code="label.file"/></label>
                                <div class="custom-file">
                                    <form:input type="file" cssClass="form-control" id="file" accept=".png,.pdf,.img"
                                                path="file" onblur="validateFile()" name="file"
                                                class="custom-file-input"/>
                                    <label for="file" class="custom-file-label text-truncate"><spring:message
                                            code="label.c2userfile.chooseFile"/></label>
                                </div>
                                <small class="text-danger" id="file-validation"></small>
                            </div>
                            <div class="form-group col-md-3" id="parentShop">
                                <label for="shop_code2" class="control-label"><spring:message
                                        code="label.c2user.cen"/></label>
                                <form:select id="shop_code2" name="shop_code2" path="pojo.shop_code"
                                             class="form-control js-example-basic-single">
                                    <option value="-1"><fmt:message key="commonet.shop_code"/></option>
                                    <form:options items="${listShop}" item="shop" itemLabel="name"
                                                  itemValue="shop_code"/>
                                </form:select>
                                <small class="text-danger" id="shop-validation"></small>
                            </div>
                            <div class="form-group col-md-3">
                                <label for="updateDate" class="control-label"><spring:message
                                        code="label.uploadDate"/> </label>
                                <form:input type="date" id="updateDate" path="uploadDate" cssClass="form-control"
                                            onblur="validateUpdateDate()" name="updateDate"/>
                                <small class="text-danger" id="updatedate-validation"></small>
                            </div>
                            <form:hidden path="form_type" id="form_type" value="1"/>
                        </div>
                        <div class="form-row">
                            <div class="form-group col-md-3">
                                <label for="user_name" class="control-label"><spring:message
                                        code="label.user_name"/></label>
                                <form:input type="text" id="user_name" path="pojo.user_name" cssClass="form-control"
                                            onblur="validateUsername()" name="user_name"/>
                                <small class="text-danger" id="username-validation"></small>
                            </div>
                            <div class="form-group col-md-3">
                                <label for="full_name" class="control-label"><spring:message
                                        code="label.user.name"/> </label>
                                <form:input type="text" id="full_name" path="pojo.full_name" cssClass="form-control"
                                            onblur="validateFullname()" name="full_name"/>
                                <small class="text-danger" id="fullname-validation"></small>
                            </div>
                            <div class="form-group col-md-3">
                                <label for="password" class="control-label"><spring:message
                                        code="label.password"/> </label>
                                <form:input type="password" id="password" path="pojo.password" cssClass="form-control"
                                            onblur="validatePassword()" name="password"/>
                                <small class="text-danger" id="password-validation"></small>
                            </div>
                            <div class="form-group col-md-3">
                                <label for="phone" class="control-label"><spring:message
                                        code="label.c2user.phone"/> </label>
                                <form:input type="text" id="phone" path="pojo.phone" cssClass="form-control"
                                            name="phone" onblur="validatePhone()"/>
                                <small class="text-danger" id="phone-validation"></small>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="card" style="border: solid black 1px">
                    <div class="card-header">
                        <spring:message code="label.program.dthgd"/>
                    </div>
                    <div class="card-body">
                        <div class="form-row">
                            <div class="form-group col-md-3">
                                <label for="toThu" class="control-label"><spring:message
                                        code="label.c2user.tothu"/> </label>
                                <form:select id="toThu" name="toThu" path="pojo.toThu"
                                             class="form-control js-example-basic-single">
                                    <option value="-1"><fmt:message key="commonet.tothu"/></option>
                                    <form:options items="${groups}" item="shop" itemLabel="name"
                                                  itemValue="name"/>
                                </form:select>
                            </div>
                            <div class="form-group col-md-3">
                                <label for="granted_ip" class="control-label"><spring:message
                                        code="label.c2user.ip"/> </label>
                                <form:input type="text" id="granted_ip" path="pojo.granted_ip"
                                            cssClass="form-control" onblur="validateGrantedIP()" name="granted_ip"/>
                                <small class="text-danger" id="granedIP-validation"></small>
                            </div>
                            <div class="form-group col-md-3">
                                <label for="access_schedule" class="control-label"><spring:message
                                        code="label.c2user.schedular"/></label>
                                <form:select id="access_schedule" path="pojo.access_schedule" class="form-control"
                                             onblur="validateAccessSchedule()" name="access_schedule">
                                    <form:option value="-1"><fmt:message key="commonet.access_time"/></form:option>
                                    <form:options items="${times}" item="name" itemLabel="name" itemValue="name"/>
                                </form:select>
                                <small class="text-danger" id="access_schedule-validation"></small>
                            </div>
                            <div class="form-group col-md-3">
                                <label for="groupUser" class="control-label"><spring:message
                                        code="label.c2user.groupUser"/></label>
                                <form:select id="groupUser" path="pojo.groupUser" class="form-control"
                                             onblur="validateGroupUser()" name="groupUser">
                                    <option value="0"><spring:message code="label.groupUser.no-permission"/></option>
                                    <option value="1"><spring:message code="label.groupUser.gdv"/></option>
                                    <option value="2"><spring:message code="label.groupUser.kam"/></option>
                                </form:select>
                                <small class="text-danger" id="groupUser-validation"></small>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-row">
                    <div class="form-group col-md-6">
                        <div class="card" style="border: solid black 1px">
                            <div class="card-header">
                                <spring:message code="label.program.cskh"/>
                            </div>
                            <div class="card-body">
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="status" class="control-label"><spring:message
                                                code="label.user.status"/> </label>
                                        <form:select id="status" path="pojo.status" class="form-control">
                                            <option value="0"><spring:message code="label.c2user.passive"/></option>
                                            <option value="1" selected><spring:message
                                                    code="label.c2user.active"/></option>
                                        </form:select>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="typeUser" class="control-label"><spring:message
                                                code="label.c2user.employeeType"/> </label>
                                        <form:select id="typeUser" path="pojo.type" class="form-control">
                                            <option value="0"><spring:message code="label.user.cskh.nvbh_tt"/></option>
                                            <option value="1"><spring:message code="label.user.cskh.nvpt_ch"/></option>
                                            <option value="2"><spring:message code="label.user.cskh.nv_am"/></option>
                                            <option value="3"><spring:message code="label.user.cskh.nv_kam"/></option>
                                            <option value="4"><spring:message code="label.user.cskh.gdv"/></option>
                                            <option value="5"><spring:message code="label.user.cskh.nvbh_ld"/></option>
                                            <option value="6"><spring:message
                                                    code="label.user.cskh.nvdbh_ddv"/></option>
                                        </form:select>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="city" class="control-label"><spring:message
                                                code="label.c2user.city"/> </label>
                                        <form:select id="city" path="pojo.city"
                                                     class="form-control js-example-basic-single"
                                                     onchange="getDistrictDetail(value);" name="city">
                                            <form:option value="-1"><fmt:message key="commonet.city"/></form:option>
                                            <form:options items="${cities}" item="city_name" itemLabel="city_name"
                                                          itemValue="city_id"/>
                                        </form:select>
                                        <small class="text-danger" id="city-validation"></small>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="district" class="control-label"><spring:message
                                                code="label.c2user.district"/> </label>
                                        <form:select id="district" path="pojo.district" class="form-control"
                                                     name="district"
                                                     onblur="validateDistrict()">
                                        </form:select>
                                        <small class="text-danger" id="district-validation"></small>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card" style="border: solid black 1px">
                            <div class="card-header">
                                <spring:message code="label.program.ttcp"/>
                            </div>
                            <div class="card-body">
                                <div class="form-row">
                                    <div class="form-group col-md-3">
                                        <label for="birthday" class="control-label"><spring:message
                                                code="label.c2user.birthday"/> </label>
                                        <form:input type="date" id="birthday" path="pojo.birthday"
                                                    cssClass="form-control"
                                                    onblur="validateBirthday()" name="birthday"/>
                                        <small class="text-danger" id="birthday-validation"></small>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label for="cmnd" class="control-label"><spring:message
                                                code="label.c2user.cmnd"/> </label>
                                        <form:input type="text" id="cmnd" path="pojo.cmnd" cssClass="form-control"
                                                    onblur="validateCmnd()" name="cmnd"/>
                                        <small class="text-danger" id="cmnd-validation"></small>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label for="ngaycap" class="control-label"><spring:message
                                                code="label.c2user.ngaycap"/> </label>
                                        <form:input type="date" id="ngaycap" path="pojo.ngaycap" cssClass="form-control"
                                                    onblur="validateNgayCap()" name="ngaycap"/>
                                        <small class="text-danger" id="ngaycap-validation"></small>
                                    </div>
                                    <div class="form-group col-md-3">
                                        <label for="noicap" class="control-label"><spring:message
                                                code="label.c2user.noicap"/> </label>
                                        <form:input type="text" id="noicap" path="pojo.noicap" cssClass="form-control"
                                                    onblur="validateNoiCap()" name="noicap"/>
                                        <small class="text-danger" id="noicap-validation"></small>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="card" style="border: solid black 1px">
                            <div class="card-header">
                                <spring:message code="label.program.cskk-resnum"/>
                            </div>
                            <div class="card-body">
                                <div class="form-row">
                                    <div class="form-group col-md-6">
                                        <label for="email" class="control-label"><spring:message
                                                code="label.cskk.email"/> </label>
                                        <form:input type="text" id="email" path="email"
                                                    cssClass="form-control"
                                                    onblur="validateEmail_CSKK()" name="email"/>
                                        <small class="text-danger" id="email-validation"></small>
                                    </div>
                                    <div class="form-group col-md-6">
                                        <label for="chucdanh" class="control-label"><spring:message
                                                code="label.c2user.chucdanh"/> </label>
                                        <form:select id="chucdanh" path="chucdanh" class="form-control"
                                                     onblur="validateChucDanh()" name="groupUser">
                                            <option value="GDV"><spring:message code="label.cskk.GDV"/></option>
                                            <option value="CHT"><spring:message code="label.cskk.CHT"/></option>
                                            <option value="AM"><spring:message code="label.cskk.AM"/></option>
                                        </form:select>
                                        <small class="text-danger" id="birthday-validation"></small>
                                    </div>
                                    <div class="form-group col-md-6" id="branch-tag" style="display: none">
                                        <label for="branch" class="control-label"><spring:message
                                                code="label.c2user.branch"/> </label>
                                        <form:select id="branch" name="branch" path="branch"
                                                     class="form-control js-example-basic-single">
                                            <option value="-1"><fmt:message key="commonet.shop_code"/></option>
                                            <form:options items="${listShop}" item="shop" itemLabel="name"
                                                          itemValue="shop_code"/>
                                        </form:select>
                                        <small class="text-danger" id="birthday-validation"></small>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <table id="datatable-buttons" class="table table-bordered"
                               style="border-collapse: collapse; border-spacing: 0; width: 100%;">
                            <thead>
                            <tr>
                                <th><spring:message code="label.action"/></th>
                                <th><spring:message code="label.program.name"/></th>
                                <th><spring:message code="label.user_role_url_acl.path"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${listProgram}" var="r" varStatus="loop">
                                <tr>
                                    <td class="table-number">
                                        <input type="checkbox" name="program" value="${r.programId}">
                                    </td>
                                    <td width="40%">${r.programName}</td>
                                    <td width="40%">${r.url}</td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
                <button type="submit" id="btnSubmit" class="btn btn-success waves-effect"><spring:message
                        code="button.add"/></button>
                <button type="button" class="btn btn-secondary waves-effect" data-dismiss="modal"><spring:message
                        code="button.close"/></button>
            </div>
            <form:input type="hidden" path="pojo.program" id="program"/>
            <input type="hidden" name="crudaction" value="insert-update"/>
        </form:form>
    </div>
</div>
<script>
    $('#file').on('change', function () {  // INPUT FILE
        let fileName = $(this).val().split('\\').pop();
        $(this).next('.custom-file-label').addClass("selected").html(fileName);
    });

    // reset all field when reload page
    $(document).ready(function () {
        $('#user_name').val('');
        $('#full_name').val('');
        $('#password').val('');
        $('#granted_ip').val('');
        $('#phone').val('');
        $('#birthday').val('');
        $('#cmnd').val('');
        $('#ngaycap').val('');
        $('#noicap').val('');
        $('#toThu').val('-1');
        $('#shop_code').val('-1');
        $('#city').val('-1');

        // SHOP-code select2
        $('.js-example-basic-single').select2();

        $('#shop_code').select2({
            dropdownParent: $('#parentShop')
        })

        $('#parentShop').on('select2:select', function (e) {
            $('#granted_ip').val('')
            $.ajax({
                url: '<c:url value="/ajax/findgrantedIpByShopcode.html" />',
                type: 'get',
                data: {
                    shop_code: e.params.data.id
                },
                dataType: 'json',
                success: function (res) {
                    $('#granted_ip').val(res.ipGranted)
                },
                error: function () {
                    $('#granted_ip').val('')
                }
            });
        });
        $('#updateDate').val(new Date().toISOString().substring(0, 10));

        // SHOW Message:
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

    // END: - SUBMIT FORM TO BACKEND
    var form = document.getElementById('addForm');
    form.addEventListener('submit', function (e) {
        e.preventDefault();
        var rsFile = validateFile();
        var rsUpdatedate = validateUpdateDate();
        var rsShopCode = validationShopcode();
        if (rsFile && rsShopCode && rsUpdatedate) { // b1: validate thong tin chung
            if (user_name.value) {
                $.ajax({
                    url: '<c:url value="/ajax/checkUserExist.html" />',
                    type: 'get',
                    data: {
                        user_name: user_name.value
                    },
                    dataType: 'json',
                    success: function (res) {
                        if (res == true) { // co user
                            user_name.classList.add("is-invalid");
                            user_name.classList.remove("is-valid");
                            document.getElementById('username-validation').textContent = "<spring:message code="label.c2user.message-user_name-exits" />";
                            return false;
                        } else {
                            var programList = "";
                            $(':checkbox:checked').each(function (i) {
                                programList += $(this).val() + "|";
                            });
                            document.getElementById('program').value = programList;
                            if (validateProgramList(programList)) { // b2: validate list chuong trinh
                                // b3: validate tung chuong trinh
                                if (programList.includes("1")) { // DTHGD
                                    validatePassword();
                                    validatePhone();
                                    validateGrantedIP();
                                    validateAccessSchedule();
                                }
                                if (programList.includes("2")) { // TTCP
                                    validateNgayCap();
                                    validateBirthday();
                                    validateCmnd();
                                    validateNoiCap();
                                }
                                if (programList.includes("3")) { // CSKH
                                    validateDistrict();
                                }
                                if (programList.includes("4")) { // CSKK
                                    validateEmail_CSKK();
                                } // Resum: ko can check gi`, có user là OK
                                form.submit();
                            }
                        }
                    }
                });
            } else {
                user_name.classList.add("is-invalid");
                user_name.classList.remove("is-valid");
                document.getElementById('username-validation').textContent = "<spring:message code="label.c2user.message-user_name" /> ";
                return false;
            }
        }
    });

    function getDistrictDetail(value) {
        var districtTag = document.getElementById('district');
        while (districtTag.firstChild) {
            districtTag.removeChild(districtTag.firstChild);
        }
        if (value !== "-1") {
            $.ajax({
                url: '<c:url value="/ajax/getNamByCityname.html" />',
                type: 'get',
                data: {
                    city: value
                },
                dataType: 'json',
                success: function (res) {
                    res.forEach(s => {
                        districtTag.innerHTML += '<option value="' + s.city_name + '">' + s.city_name + '</option>';
                    });
                }
            });
        }
    }

    const selectElement = document.getElementById('chucdanh')
    const divBranch = document.getElementById('branch-tag');
    selectElement.addEventListener("change", (event) => {
        if (event.target.value == 'GDV' || event.target.value == 'CHT') {
            divBranch.style.display = "none"
            $('#shop_code2').val('').change();
        } else {
            divBranch.style.display = "block"
            $('#shop_code2').val('').change();
        }
    });

    //     validator:
    var file = document.myForm.file;
    var updateDate = document.myForm.updateDate;
    var user_name = document.myForm.user_name;
    var full_name = document.myForm.full_name;
    var password = document.myForm.password;
    var granted_ip = document.myForm.granted_ip;
    var birthday = document.myForm.birthday;
    var cmnd = document.myForm.cmnd;
    var ngaycap = document.myForm.ngaycap;
    var noicap = document.myForm.noicap;
    var toThu = document.myForm.toThu;
    var shop_code = document.myForm.shop_code;
    var phone = document.myForm.phone;
    var access_schedule = document.myForm.access_schedule;
    var status = document.myForm.status;
    var typeUser = document.myForm.typeUser;
    var city = document.myForm.city;
    var district = document.myForm.district;
    var email = document.myForm.email;
    var today = new Date();

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

    function validationShopcode() {
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

    function validatePhone() {
        if (phone.value) {
            phone.classList.remove("is-invalid");
            phone.classList.add("is-valid");
            document.getElementById('phone-validation').textContent = '';
            return true;
        } else {
            phone.classList.add("is-invalid");
            phone.classList.remove("is-valid");
            document.getElementById('phone-validation').textContent = "<spring:message code="label.c2user.message-phone" /> ";
            return false;
        }
    }

    function validateUpdateDate() {
        if (updateDate.value) {
            if (new Date(updateDate.value) > today) {
                updateDate.classList.add("is-invalid");
                updateDate.classList.remove("is-valid");
                document.getElementById('updatedate-validation').textContent = "<spring:message code="label.c2user.message-updateDate-before-today" /> ";
                return false;
            } else {
                updateDate.classList.remove("is-invalid");
                updateDate.classList.add("is-valid");
                document.getElementById('updatedate-validation').textContent = '';
                return true;
            }
        } else {
            updateDate.classList.add("is-invalid");
            updateDate.classList.remove("is-valid");
            document.getElementById('updatedate-validation').textContent = "<spring:message code="label.c2user.message-updateDate" /> ";
            return false;
        }
    }

    function validateUsername() {
        if (user_name.value) {
            $.ajax({
                url: '<c:url value="/ajax/checkUserExist.html" />',
                type: 'get',
                data: {
                    user_name: user_name.value
                },
                dataType: 'json',
                success: function (res) {
                    if (res == true) { // co user
                        user_name.classList.add("is-invalid");
                        user_name.classList.remove("is-valid");
                        document.getElementById('username-validation').textContent = "<spring:message code="label.c2user.message-user_name-exits" />";
                        return false;
                    } else {
                        user_name.classList.remove("is-invalid");
                        user_name.classList.add("is-valid");
                        document.getElementById('username-validation').textContent = '';
                        return true;
                    }
                }
            });
        } else {
            user_name.classList.add("is-invalid");
            user_name.classList.remove("is-valid");
            document.getElementById('username-validation').textContent = "<spring:message code="label.c2user.message-user_name" /> ";
            return false;
        }
    }

    function validateFullname() {
        if (full_name.value) {
            full_name.classList.remove("is-invalid");
            full_name.classList.add("is-valid");
            document.getElementById('fullname-validation').textContent = '';
            return true;
        } else {
            full_name.classList.add("is-invalid");
            full_name.classList.remove("is-valid");
            document.getElementById('fullname-validation').textContent = "<spring:message code="label.c2user.message-full_name" /> ";
            return false;
        }
    }

    function validatePassword() {
        if (password.value) {
            password.classList.remove("is-invalid");
            password.classList.add("is-valid");
            document.getElementById('password-validation').textContent = '';
            return true;
        } else {
            password.classList.add("is-invalid");
            password.classList.remove("is-valid");
            document.getElementById('password-validation').textContent = "<spring:message code="label.c2user.message-password" /> ";
            return false;
        }
    }

    function isIPValid(ip) {
        const ipRegex = /^(\d{1,3}\.){3}\d{1,3}$/;
        return ipRegex.test(ip);
    }

    function isIPRangeValid(ipRange) {
        const ipRangeRegex = /^(\d{1,3}\.){3}\d{1,3}-(\d{1,3}\.){3}\d{1,3}$/;
        return ipRangeRegex.test(ipRange);
    }

    function validateGrantedIP() {
        if (granted_ip.value) {
            const ips = granted_ip.value.split(";");

            for (const ip_g of ips) {
                if (!isIPValid(ip_g) && !isIPRangeValid(ip_g)) {
                    granted_ip.classList.add("is-invalid");
                    granted_ip.classList.remove("is-valid");
                    document.getElementById('granedIP-validation').textContent = "<spring:message code="label.c2user.message-ip-reges" /> ";
                    return false; // Không đúng định dạng
                }
            }
            granted_ip.classList.remove("is-invalid");
            granted_ip.classList.add("is-valid");
            document.getElementById('granedIP-validation').textContent = '';
            return true; // Đúng định dạng
        } else {
            granted_ip.classList.add("is-invalid");
            granted_ip.classList.remove("is-valid");
            document.getElementById('granedIP-validation').textContent = "<spring:message code="label.c2user.message-granted_ip" /> ";
            return false;
        }
    }

    function validateBirthday() {
        if (birthday.value) {
            if (new Date(birthday.value) >= today) {
                birthday.classList.add("is-invalid");
                birthday.classList.remove("is-valid");
                document.getElementById('birthday-validation').textContent = "<spring:message code="label.c2user.message-birthday-before-day" /> ";
                return false;
            } else if (ngaycap.value && new Date(ngaycap.value) <= new Date(birthday.value)) {
                birthday.classList.add("is-invalid");
                birthday.classList.remove("is-valid");
                document.getElementById('birthday-validation').textContent = "<spring:message code="label.c2user.message-birthday-after-ngaycap" /> ";
                return false;
            } else {
                birthday.classList.remove("is-invalid");
                birthday.classList.add("is-valid");
                document.getElementById('birthday-validation').textContent = '';
                return true;
            }
        } else {
            birthday.classList.add("is-invalid");
            birthday.classList.remove("is-valid");
            document.getElementById('birthday-validation').textContent = "<spring:message code="label.c2user.message-birthday" /> ";
            return false;
        }
    }

    function validateNoiCap() {
        if (noicap.value) {
            noicap.classList.remove("is-invalid");
            noicap.classList.add("is-valid");
            document.getElementById('noicap-validation').textContent = '';
            return true;
        } else {
            noicap.classList.add("is-invalid");
            noicap.classList.remove("is-valid");
            document.getElementById('noicap-validation').textContent = "<spring:message code="label.c2user.message-noicap" /> ";
            return false;
        }
    }

    function validateNgayCap() {
        if (ngaycap.value) {
            if (new Date(ngaycap.value) >= today) {
                ngaycap.classList.add("is-invalid");
                ngaycap.classList.remove("is-valid");
                document.getElementById('ngaycap-validation').textContent = "<spring:message code="label.c2user.message-ngaycap-before-today" /> ";
                return false;
            } else if (birthday.value && new Date(birthday.value) >= new Date(ngaycap.value)) {
                ngaycap.classList.add("is-invalid");
                ngaycap.classList.remove("is-valid");
                document.getElementById('ngaycap-validation').textContent = "<spring:message code="label.c2user.message-ngaycap-before-birthay" /> ";
                return false;
            } else {
                ngaycap.classList.remove("is-invalid");
                ngaycap.classList.add("is-valid");
                document.getElementById('ngaycap-validation').textContent = '';
                return true;
            }
        } else {
            ngaycap.classList.add("is-invalid");
            ngaycap.classList.remove("is-valid");
            document.getElementById('ngaycap-validation').textContent = "<spring:message code="label.c2user.message-ngaycap" /> ";
            return false;
        }
    }

    function validateCmnd() {
        if (cmnd.value) {
            cmnd.classList.remove("is-invalid");
            cmnd.classList.add("is-valid");
            document.getElementById('cmnd-validation').textContent = '';
            return true;
        } else {
            cmnd.classList.add("is-invalid");
            cmnd.classList.remove("is-valid");
            document.getElementById('cmnd-validation').textContent = "<spring:message code="label.c2user.message-cmnd" /> ";
            return false;
        }
    }

    function validateAccessSchedule() {
        if (access_schedule.value !== "-1") {
            access_schedule.classList.remove("is-invalid");
            access_schedule.classList.add("is-valid");
            document.getElementById('access_schedule-validation').textContent = '';
            return true;
        } else {
            access_schedule.classList.add("is-invalid");
            access_schedule.classList.remove("is-valid");
            document.getElementById('access_schedule-validation').textContent = "<spring:message code="label.c2user.message-access_schedule" /> ";
            return false;
        }
    }

    function validateDistrict() {
        if (district.value) {
            district.classList.remove("is-invalid");
            district.classList.add("is-valid");
            document.getElementById('district-validation').textContent = '';
            return true;
        } else {
            district.classList.add("is-invalid");
            district.classList.remove("is-valid");
            document.getElementById('district-validation').textContent = "<spring:message code="label.c2user.message-district" /> ";
            return false;
        }
    }

    function validateGroupUser() {

    }

    function validateEmail_CSKK() {
        if (email.value) {
            const regex = /@mobifone\.vn$/;
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

    function validateChucDanh() {

    }

    function validateBranch() {

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
</script>
</body>
</html>
