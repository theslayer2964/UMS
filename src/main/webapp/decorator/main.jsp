<%--
  Created by IntelliJ IDEA.
  User: phaolo
  Date: 2020-03-09
  Time: 16:32
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <%@include file="/common/meta.jsp" %>

    <title><spring:message code="system.title"/> | <sitemesh:write property="title"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta content="A fully featured admin theme which can be used to build CRM, CMS, etc." name="description"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>

    <link rel="stylesheet" type="text/css"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

    <!-- Custom Theme Style -->
    <link href="<c:url value='/assets/custom/css/custom.css'/>" rel="stylesheet">

    <!-- malihu Custom Scrollbar -->
    <link href="<c:url value="/assets/vendors/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.min.css"/>"
          rel="stylesheet" type="text/css">

    <!-- App favicon -->
    <link rel="shortcut icon" href="/assets/images/favicon.ico">

    <!-- Table datatable css -->
    <link href="<c:url value='/assets/libs/datatables/dataTables.bootstrap4.min.css' />" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value='/assets/libs/datatables/responsive.bootstrap4.min.css' />" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value='/assets/libs/datatables/buttons.bootstrap4.min.css' />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/assets/libs/datatables/fixedHeader.bootstrap4.min.css' />" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value='/assets/libs/datatables/scroller.bootstrap4.min.css' />" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value='/assets/libs/datatables/dataTables.colVis.css' />" rel="stylesheet" type="text/css"/>

    <!-- Bootstrap select pluings -->
    <link href="<c:url value='/assets/libs/bootstrap-select/bootstrap-select.min.css' />" rel="stylesheet"
          type="text/css"/>

    <!-- App css -->
    <link href="<c:url value='/assets/css/bootstrap-dark.min.css' />" rel="stylesheet" type="text/css"
          id="bootstrap-stylesheet"/>
    <link href="<c:url value='/assets/css/icons.min.css' />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/assets/css/app-dark.min.css' />" rel="stylesheet" type="text/css" id="app-stylesheet"/>

    <link href="<c:url value='/assets/css/dialog.css' />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/assets/css/home.css' />" rel="stylesheet" type="text/css"/>
    <link href="<c:url value='/assets/css/snackbar.css' />" rel="stylesheet" type="text/css"/>

    <%--  datepicker--%>
    <link href="<c:url value='/assets/libs/bootstrap-timepicker/bootstrap-timepicker.min.css' />" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value='/assets/libs/bootstrap-colorpicker/bootstrap-colorpicker.min.css' />" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value='/assets/libs/bootstrap-daterangepicker/daterangepicker.css' />" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value='/assets/libs/clockpicker/bootstrap-clockpicker.min.css' />" rel="stylesheet"
          type="text/css"/>
    <link href="<c:url value='/assets/libs/bootstrap-datepicker/bootstrap-datepicker.min.css' />" rel="stylesheet"
          type="text/css"/>

    <!-- Datatable -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <%--  <script src="https://cdn.datatables.net/1.10.18/js/jquery.dataTables.min.js"></script>--%>
    <script src="https://cdn.datatables.net/1.13.7/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.18/js/dataTables.bootstrap4.min.js"></script>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.1/bootstrap3-editable/css/bootstrap-editable.css"
          rel="stylesheet">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/x-editable/1.5.1/bootstrap3-editable/js/bootstrap-editable.js"></script>
    <script src="https://cdn.datatables.net/select/1.7.0/js/dataTables.select.min.js"></script>
    <link rel="stylesheet" href="https://cdn.datatables.net/select/1.7.0/css/select.dataTables.min.css"/>

    <!-- PNotify -->
    <link href="<c:url value='/assets/libs/pnotify/dist/pnotify.css'/>" rel="stylesheet">
    <link href="<c:url value='/assets/libs/pnotify/dist/pnotify.buttons.css'/>" rel="stylesheet">
    <link href="<c:url value='/assets/libs/pnotify/dist/pnotify.nonblock.css'/>" rel="stylesheet">

    <%--  <link href="node_modules/@pnotify/core/dist/PNotify.css" rel="stylesheet" type="text/css" />--%>
    <%--  <link href="node_modules/@pnotify/mobile/dist/PNotifyMobile.css" rel="stylesheet" type="text/css" />--%>

    <!-- Summernote -->
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n"
            crossorigin="anonymous"></script>
    <link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>

    <!-- Token field -->
    <%--  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-tokenfield/0.12.0/css/tokenfield-typeahead.css">--%>
    <%--  <link rel="stylesheet" type="text/css" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-tokenfield/0.12.0/css/bootstrap-tokenfield.css">--%>
    <%--  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/typeahead.js/0.10.1/typeahead.bundle.min.js"></script>--%>
    <%--  <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-tokenfield/0.12.0/bootstrap-tokenfield.js"></script>--%>
    <link rel="stylesheet" type="text/css" href="/assets/css/tokenfield-typeahead.css">
    <link rel="stylesheet" type="text/css" href="/assets/css/bootstrap-tokenfield.css">
    <script type="text/javascript" src="/assets/js/typeahead.bundle.min.js"></script>
    <script type="text/javascript" src="/assets/js/bootstrap-tokenfield.js"></script>

    <!-- Filer -->
    <link href="https://tjaddison.github.io/uplon/Admin/HTML/horizontal/assets/plugins/jquery.filer/css/jquery.filer.css"
          rel="stylesheet"/>
    <link href="https://tjaddison.github.io/uplon/Admin/HTML/horizontal/assets/plugins/jquery.filer/css/themes/jquery.filer-dragdropbox-theme.css"
          rel="stylesheet"/>

    <%--<!--  select2-->--%>
    <link href="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/css/select2.min.css" rel="stylesheet"/>
    <link href="<c:url value='/assets/libs/sweetalert2/sweetalert2.min.css' />" rel="stylesheet" type="text/css"/>

    <!-- rowGroup -->
    <link rel="stylesheet" type="text/css"
          href="https://cdn.datatables.net/rowgroup/1.1.4/css/rowGroup.dataTables.min.css">
    <%--  icon--%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.15.0/font/bootstrap-icons.css" rel="stylesheet">

    <%--    Excel--%>
    <script type="text/javascript" src="/assets/js/xlsx.full.min.js"></script>

    <%--    NOTYF.JS--%>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/notyf@3/notyf.min.css">

</head>


<body data-layout="horizontal">
<!-- Begin page -->
<div id="wrapper">
    <!-- Navigation Bar-->
    <%@include file="/common/header.jsp" %>
    <!-- End Navigation Bar-->
    <!-- ============================================================== -->
    <!-- Start Page Content here -->
    <!-- ============================================================== -->

    <div class="content-page">
        <div class="content">

            <!-- Start Content-->
            <sitemesh:write property="body"/>
            <!-- end container-fluid -->

        </div> <!-- end content -->


        <!-- Footer Start -->
        <%@include file="/common/footer.jsp" %>
        <!-- end Footer -->

    </div>

    <!-- ============================================================== -->
    <!-- End Page content -->
    <!-- ============================================================== -->

</div>
<!-- END wrapper -->

<!-- Snackbar js -->
<script src="<c:url value='/assets/js/snackbar.js' />"></script>

<!-- Vendor js -->
<script src="<c:url value='/assets/js/vendor.min.js' />"></script>

<!-- Bootstrap select plugin -->
<script src="<c:url value='/assets/libs/bootstrap-select/bootstrap-select.min.js' />"></script>

<!-- Datatable plugin js -->
<script src="<c:url value='/assets/libs/datatables/jquery.dataTables.min.js' />"></script>
<script src="<c:url value='/assets/libs/datatables/dataTables.bootstrap4.min.js' />"></script>

<script src="<c:url value='/assets/libs/datatables/dataTables.responsive.min.js' />"></script>
<script src="<c:url value='/assets/libs/datatables/responsive.bootstrap4.min.js' />"></script>

<script src="<c:url value='/assets/libs/datatables/dataTables.buttons.min.js' />"></script>
<script src="<c:url value='/assets/libs/datatables/buttons.bootstrap4.min.js' />"></script>

<script src="<c:url value='/assets/libs/datatables/dataTables.keyTable.min.js' />"></script>
<script src="<c:url value='/assets/libs/datatables/dataTables.fixedHeader.min.js' />"></script>
<script src="<c:url value='/assets/libs/datatables/dataTables.scroller.min.js' />"></script>
<script src="<c:url value='/assets/libs/datatables/dataTables.colVis.js' />"></script>

<script src="<c:url value='/assets/js/jszip/dist/jszip.js'/>"></script>
<script src="<c:url value='/assets/js/jszip/vendor/FileSaver.js'/>"></script>

<script src="<c:url value='/assets/js/datatables-buttons/js/buttons.html5.js'/>"></script>
<script src="<c:url value='/assets/libs/datatables/buttons.print.min.js' />"></script>
<%--<script src="<c:url value='/assets/libs/pdfmake/pdfmake.min.js' />"></script>--%>
<%--<script src="<c:url value='/assets/libs/pdfmake/vfs_fonts.js' />"></script>--%>

<script src="<c:url value='/assets/js/pages/datatables.init.js' />"></script>
<script src="<c:url value='/assets/js/bootbox.min.js' />"></script>
<!-- PNotify -->
<script src="<c:url value='/assets/libs/pnotify/dist/pnotify.js'/>"></script>
<script src="<c:url value='/assets/libs/pnotify/dist/pnotify.buttons.js'/>"></script>
<script src="<c:url value='/assets/libs/pnotify/dist/pnotify.nonblock.js'/>"></script>
<%--datepicker--%>
<script src="<c:url value='/assets/libs/moment/moment.min.js'/>"></script>
<script src="<c:url value='/assets/libs/bootstrap-timepicker/bootstrap-timepicker.min.js'/>"></script>
<script src="<c:url value='/assets/libs/bootstrap-colorpicker/bootstrap-colorpicker.min.js'/>"></script>
<script src="<c:url value='/assets/libs/bootstrap-daterangepicker/daterangepicker.js'/>"></script>
<script src="<c:url value='/assets/libs/clockpicker/bootstrap-clockpicker.min.js'/>"></script>
<script src="<c:url value='/assets/libs/bootstrap-datepicker/bootstrap-datepicker.min.js'/>"></script>
<%--<script type="text/javascript" src="node_modules/@pnotify/core/dist/PNotify.js"></script>--%>
<%--<script type="text/javascript" src="node_modules/@pnotify/mobile/dist/PNotifyMobile.js"></script>--%>

<!-- App js -->
<script src="<c:url value='/assets/js/app.min.js' />"></script>

<!-- Filer -->
<script src="/assets/js/jquery.filer.min.js"></script>
<script src="/assets/js/jquery.fileuploads.init.js"></script>

<!-- Custom Theme Scripts -->
<script src="<c:url value='/assets/custom/js/custom.min.js'/>"></script>

<!-- Smart Wizard Scripts -->
<script src="<c:url value="/assets/vendors/jQuery-Smart-Wizard/jquery.smartWizard_v1.js" />"></script>

<!-- malihu Custom Scrollbar Scripts -->
<script type="text/javascript"
        src="<c:url value="/assets/vendors/malihu-custom-scrollbar-plugin/jquery.mCustomScrollbar.concat.min.js"/>"></script>
<%--<!--  select2-->--%>
<script src="<c:url value="https://cdnjs.cloudflare.com/ajax/libs/select2/4.0.13/js/select2.min.js" />"></script>
<%--<script src="<c:url value="/assets/libs/sweetalert2/sweetalert2.min.js" />"></script>--%>
<script src="<c:url value="/assets/js/pages/sweetalert2@11.js" />"></script>

<!-- rowGroup Scripts -->
<script src="https://cdn.datatables.net/rowgroup/1.1.4/js/dataTables.rowGroup.min.js"></script>

<%--    NOTYF.JS--%>
<script src="https://cdn.jsdelivr.net/npm/notyf@3/notyf.min.js"></script>

<script>
    $(document).ready(function () {
        var navigation = document.getElementById("navigation");
        if (navigation.clientHeight > 60) {
            $(".content-page").css({'padding-top': '120px'});
        }

        const LOCAL_STORAGE_KEY = "toggle-bootstrap-theme";
        if (localStorage.getItem(LOCAL_STORAGE_KEY) !== null) {
            const LOCAL_META_DATA = JSON.parse(localStorage.getItem(LOCAL_STORAGE_KEY));
            let isDark = LOCAL_META_DATA && LOCAL_META_DATA.isDark;
            let bootstrap = document.getElementById('bootstrap-stylesheet');
            let app = document.getElementById('app-stylesheet');
            <%-- Đổi màu theme của tất cả các trang thành sáng/tối theo localStorage --%>
            if (isDark) {
                bootstrap.setAttribute("href", "<c:url value='/assets/css/bootstrap-dark.min.css' />");
                app.setAttribute("href", "<c:url value='/assets/css/app-dark.min.css' />");
            } else {
                bootstrap.setAttribute("href", "<c:url value='/assets/css/bootstrap.min.css' />");
                app.setAttribute("href", "<c:url value='/assets/css/app.min.css' />");
            }
            <%-- Đổi màu theme của tất cả modal thành sáng/tối theo localStorage --%>
            let modalNodeList = document.getElementsByClassName('modal-content');
            if (modalNodeList.length > 0) {
                let modalAtt;
                let modal;
                for (let i = 0; i < modalNodeList.length; i++) {
                    modalAtt = document.getElementsByClassName('modal-content')[i].getAttribute('style');
                    modal = document.getElementsByClassName('modal-content')[i];
                    if (isDark) {
                        modal.setAttribute("style", modalAtt + ";background-color: #232a36");
                    } else {
                        modal.setAttribute("style", modalAtt + ";background-color: #fefefe");
                    }
                }
            }
            <%-- Đổi màu theme của tất cả Displaytable thành sáng/tối theo localStorage --%>
            let displayTableModalNodeList = document.getElementsByClassName('x_panel');
            if (displayTableModalNodeList.length > 0) {
                let dtModalAtt;
                let dtModal;
                for (let i = 0; i < displayTableModalNodeList.length; i++) {
                    dtModalAtt = document.getElementsByClassName('x_panel')[i].getAttribute('style');
                    dtModal = document.getElementsByClassName('x_panel')[i];
                    if (isDark) {
                        dtModal.setAttribute("style", dtModalAtt + ";background-color: #232a36");
                    } else {
                        dtModal.setAttribute("style", dtModalAtt + ";background-color: #fefefe");
                    }
                }
            }
        } else {
            <%-- Đổi màu theme của tất cả modal thành sáng (mặc định nếu chưa chọn theme) --%>
            let bootstrap = document.getElementById('bootstrap-stylesheet');
            let app = document.getElementById('app-stylesheet');
            bootstrap.setAttribute("href", "<c:url value='/assets/css/bootstrap.min.css' />");
            app.setAttribute("href", "<c:url value='/assets/css/app.min.css' />");
            let modalNodeList = document.getElementsByClassName('modal-content');
            if (modalNodeList.length > 0) {
                let modal;
                for (let i = 0; i < modalNodeList.length; i++) {
                    modal = document.getElementsByClassName('modal-content')[i];
                    modal.style.backgroundColor = "#fefefe";
                }
            }
            <%-- Đổi màu theme của tất cả Displaytable thành sáng (mặc định nếu chưa chọn theme) --%>
            let displayTableModalNodeList = document.getElementsByClassName('x_panel');
            if (displayTableModalNodeList.length > 0) {
                let dtModal;
                for (let i = 0; i < displayTableModalNodeList.length; i++) {
                    dtModal = document.getElementsByClassName('x_panel')[i];
                    dtModal.style.backgroundColor = "#fefefe";
                }
            }
        }
    });
</script>

<script>
    $(document).ready(function () {
        <c:if test="${not empty isMessage}">
        new PNotify({
            title: '<fmt:message key="msg.snackbar.title"/>',
            text: '${messageResponse}',
            type: '${messageType}',
            styling: 'bootstrap3'
        });
        </c:if>
    });
</script>
</body>
</html>