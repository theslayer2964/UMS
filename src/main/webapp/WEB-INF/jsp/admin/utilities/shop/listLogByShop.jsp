<%--
  Created by IntelliJ IDEA.
  User: DELL
  Date: 12/27/2023
  Time: 2:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<c:url var="formURL" value="/utilities/user_c2_log_by_shop.html"/>
<html>
<head>
    <title><spring:message code="menu.system.statisticalShopByLog"/></title>
</head>
<body>
<div class="container-fluid">
    <%--    page title--%>
    <div class="row">
        <div class="col-12">
            <div class="page-title-box">
                <div class="page-title-right">
                    <ol class="breadcrumb m-0">
                        <li class="breadcrumb-item"><a href="javascript: void(0);"><spring:message
                                code="menu.system"/></a></li>
                        <li class="breadcrumb-item active"><spring:message
                                code="menu.system.statisticalShopByLog"/></li>
                    </ol>
                </div>
                <h4 class="page-title"><spring:message code="menu.system.statisticalShopByLog"/></h4>
            </div>
        </div>
    </div> <!--end title-->
    <div class="row">
        <div class="col-12">
            <div class="card">
                <div class="card-body">
                    <%--                    Search form--%>
                    <form:form id="listForm" modelAttribute="item" name="myForm">
                        <div class="row">
                            <div class="col-sm-12">
                                <div class="card-box table-responsive">
                                    <div class="form-group row">
                                        <label for="searchNameFrom" class="col-sm-2 col-form-label"
                                               style="left: 6%;"><spring:message code="label.c2user.from"/></label>
                                        <div class="col-sm-3">
                                            <form:input type="date" id="searchNameFrom" name="from" path="from"
                                                        onblur="validateSearchForm()" class="form-control"/>
                                            <small class="text-danger" id="from-validation"></small>
                                        </div>
                                        <label for="searchNameTo" class="col-sm-2 col-form-label"
                                               style="left: 6%;"><spring:message code="label.c2user.to"/></label>
                                        <div class="col-sm-3">
                                            <form:input type="date" id="searchNameTo" name="to" path="to"
                                                        onblur="validateSearchTo()" class="form-control"/>
                                            <small class="text-danger" id="to-validation"></small>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <label for="searchShopCode" class="col-sm-2 col-form-label"
                                               style="left: 6%;"><spring:message code="label.c2user.shopCode"/></label>
                                        <div class="col-sm-8">
                                            <form:select id="searchShopCode" name="shopCode" path="shopCode"
                                                         class="form-control js-example-basic-single">
                                                <option value="-1"><fmt:message key="label.c2user.shopCode"/></option>
                                                <form:options items="${listShop}" itemLabel="name"
                                                              itemValue="shop_code"/>
                                            </form:select>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                        <div class="col-sm-2"></div>
                                        <div class="col-sm-8 offset-md-2">
                                            <button type="submit" style="margin-right: 10px;" class="btn btn-success">
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
                    <div class="table-responsive" style="display: none;" id="table1">
                        <table id="example1" class="display dataTable cell-border table table-bordered"
                               style="border-collapse: collapse; border-spacing: 0; width: 100%;"></table>
                    </div>
                    <div id="loadingEvent1">
                        <div class="spinner-border text-success" role="status">
                            <span class="sr-only">Loading...</span>
                        </div>
                    </div>
                    <%@include file="listLogByShop_UserDetail.jsp" %>
                </div>
            </div>
        </div>
    </div>
</div>
<script>
    $(document).ready(function () {
        $('.js-example-basic-single').select2();

        loadingData();
    });

    function loadingData() {
        $("#loadingEvent1").show();

        if ($.fn.DataTable.isDataTable('#example1')) {
            $('#example1').DataTable().destroy();
        }

        /// Ngày tháng năm
        var date = new Date();
        var to = date.toISOString().substring(0, 10);
        document.getElementById('searchNameFrom').value = to;
        document.getElementById('searchNameTo').value = to;
        date.setDate(date.getDate() + 1);

        $('#example1').hide();
        $.ajax({
            url: '<c:url value="/admin/statistical_log_shop.html" />',
            type: 'get',
            data: {
                from: document.getElementById('searchNameFrom').value,
                to: date.toISOString().substring(0, 10),
                shop_code: document.getElementById('searchShopCode').value
            },
            dataType: 'json',
            success: function (data) {
                // c2: client side:
                $('#example1').DataTable({
                    data: data,
                    columns: [
                        {
                            title: '<spring:message code="label.action"/>', data: "shop_code",
                            render: function (code) {
                                return "<a class=\'btn-icon waves-effect waves-light btn-warning btn-sm\' onclick=\'showShopUserList(" + JSON.stringify(code) + ")\'><i class='fa fa-info-circle'></i></a>";
                            }
                        },
                        {title: "<spring:message code="label.c2user.cen"/>", data: "shop_code"},
                        {title: "<spring:message code="label.c2user.shopName"/>", data: "shop_name"},
                        {title: "<spring:message code="label.number"/>", data: "soLuong"}
                    ]
                });

                $("#loadingEvent1").hide();
                $('#example1').show();
                $('#table1').show();
            }
        });
    }

    var formSearch = document.getElementById('listForm');
    formSearch.addEventListener('submit', function (e) {
        e.preventDefault();
        var rsFrom = validateSearchForm();
        var rsTo = validateSearchTo();
        if (rsFrom && rsTo) {
            $("#loadingEvent1").show();

            if ($.fn.DataTable.isDataTable('#example1')) {
                $('#example1').DataTable().destroy();
            }
            var to = new Date(document.getElementById('searchNameTo').value);
            to.setDate(to.getDate() + 1);

            $('#table1').hide();
            $.ajax({
                url: '<c:url value="/admin/statistical_log_shop.html" />',
                type: 'get',
                data: {
                    from: document.getElementById('searchNameFrom').value,
                    to: to.toISOString().substring(0, 10),
                    shop_code: document.getElementById('searchShopCode').value
                },
                dataType: 'json',
                success: function (data) {
                    // c2: client side:
                    $('#example1').DataTable({
                        data: data,
                        columns: [
                            {
                                title: '<spring:message code="label.action"/>', data: "shop_code",
                                render: function (code, type, row) {
                                    return "<a class=\'btn-icon waves-effect waves-light btn-warning btn-sm\' onclick=\'showShopUserList(" + JSON.stringify(code) + ")\'><i class='fa fa-info-circle'></i></a>";
                                }
                            },
                            {title: "<spring:message code="label.c2user.cen"/>", data: "shop_code"},
                            {title: "<spring:message code="label.c2user.shopName"/>", data: "shop_name"},
                            {title: "<spring:message code="label.number"/>", data: "soLuong"}
                        ]
                    });
                    $("#loadingEvent1").hide();
                    $('#table1').show();
                    $('#example1').show();
                }
            });
        }
    });

    function showShopUserList(shop_code) {
        $("#loadingEvent2").show();
        $('#shopcode_search').text(shop_code);
        if ($.fn.DataTable.isDataTable('#example2')) {
            $('#example2').DataTable().destroy();
        }
        var to = new Date(document.getElementById('searchNameTo').value);
        to.setDate(to.getDate() + 1);

        $('#table2').hide();
        $.ajax({
            url: '<c:url value="/ajax/getViewLogUserDetail_Shop.html" />',
            type: 'get',
            data: {
                shop_code: shop_code,
                from: document.getElementById('searchNameFrom').value,
                to: to.toISOString().substring(0, 10),
            },
            dataType: 'json',
            success: function (data) {

                $('#example2').DataTable({
                    data: data,
                    columns: [
                        {title: "<spring:message code="label.username"/>", data: "user_name"},
                        {title: "<spring:message code="label.number"/>", data: "quantity"}
                    ]
                });
                $("#loadingEvent2").hide();
                $('#table2').show();
            }
        });
        $('#myModal').modal('show');
    }

    // validate:
    var searchForm = document.myForm.from;
    var searchTo = document.myForm.to;
    var today = new Date();

    function validateSearchForm() {
        if (searchForm.value) {
            if (new Date(searchForm.value) >= today) {
                searchForm.classList.add("is-invalid");
                searchForm.classList.remove("is-valid");
                document.getElementById('from-validation').textContent = "<spring:message code="label.c2user.message-from-to" /> ";
                return false;
            } else if (searchTo.value && new Date(searchForm.value) > new Date(searchTo.value)) {
                searchForm.classList.add("is-invalid");
                searchForm.classList.remove("is-valid");
                document.getElementById('from-validation').textContent = "<spring:message code="label.c2user.message-from-after-to" /> ";
                return false;
            } else {
                searchForm.classList.add("is-valid");
                searchForm.classList.remove("is-invalid");
                document.getElementById('from-validation').textContent = "";
                return true;
            }
        } else {
            searchForm.classList.add("is-invalid");
            searchForm.classList.remove("is-valid");
            document.getElementById('from-validation').textContent = "<spring:message code="label.c2user.message-from-to-null" /> ";
            return false;
        }
    }

    function validateSearchTo() {
        if (searchTo.value) {
            if (new Date(searchTo.value) >= today) {
                searchTo.classList.add("is-invalid");
                searchTo.classList.remove("is-valid");
                document.getElementById('to-validation').textContent = "<spring:message code="label.c2user.message-from-to" /> ";
                return false;
            } else if (searchForm.value && new Date(searchTo.value) < new Date(searchForm.value)) {
                searchTo.classList.add("is-invalid");
                searchTo.classList.remove("is-valid");
                document.getElementById('to-validation').textContent = "<spring:message code="label.c2user.message-from-after-to" /> ";
                return false;
            } else {
                searchTo.classList.add("is-valid");
                searchTo.classList.remove("is-invalid");
                document.getElementById('to-validation').textContent = "";
                return true;
            }
        } else {
            searchTo.classList.add("is-invalid");
            searchTo.classList.remove("is-valid");
            document.getElementById('to-validation').textContent = "<spring:message code="label.c2user.message-from-to-null" /> ";
            return false;
        }
    }
</script>
</body>
</html>
