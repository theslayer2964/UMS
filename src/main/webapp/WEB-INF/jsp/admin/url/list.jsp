<%--
  Created by IntelliJ IDEA.
  User: phaolo
  Date: 2020-03-18
  Time: 05:14
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp"%>
<c:url var="formURL" value="/admin/url-list.html" />
<html>
<head>
  <title><spring:message code="menu.system.url"/> </title>
</head>
<body>
<div class="container-fluid">

  <!-- start page title -->
  <div class="row">
    <div class="col-12">
      <div class="page-title-box">
        <div class="page-title-right">
          <ol class="breadcrumb m-0">
            <li class="breadcrumb-item"><a href="javascript: void(0);"><spring:message code="menu.system"/></a></li>
            <li class="breadcrumb-item active"><spring:message code="menu.system.url"/></li>
          </ol>
        </div>
        <h4 class="page-title"><spring:message code="label.url.list"/></h4>
      </div>
    </div>
  </div>
  <!-- end page title -->
  <div class="row">
    <div class="col-12">
      <div class="card">
        <div class="card-body">
          <div class="header-title sub-header">
            <button class="btn btn-warning waves-effect waves-light" data-toggle="modal" data-target="#myModal" data-target="#con-close-modal"><spring:message code="button.add"/> </button>
            <%@include file="insert.jsp"%>
          </div>
          <form:form id="listForm" modelAttribute="item" action="${formURL}" method="post">
            <div class="table-responsive">
              <table id="datatable-buttons" class="table table-bordered" style="border-collapse: collapse; border-spacing: 0; width: 100%;">
                <thead>
                <tr>
                  <th>#</th>
                  <th><spring:message code="label.action"/></th>
                  <th><spring:message code="label.code"/> </th>
                  <th><spring:message code="label.name"/></th>
                  <th><spring:message code="label.path"/></th>
                  <th><spring:message code="label.url.group"/></th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${items.listResult }" var="r" varStatus="loop">
                  <tr>
                    <td class="table-number" width="5%">${loop.index+1}</td>
                    <td class="table-action" width="10%">
                      <a class="btn btn-icon waves-effect waves-light btn-warning btn-sm" onclick="javascript: editForm('${r.urlId }','${r.code}','${r.name }','${r.path }','${r.urlGroup.urlGroupId }');">
                        <i class="fas fa-wrench"></i>
                      </a>
                      <a class="btn btn-icon waves-effect waves-light btn-danger btn-sm" onclick="javascript: deleteForm('${r.urlId }')"><i class="fas fa-times"></i></a>
                    </td>
                    <td width="20%">${r.code}</td>
                    <td width="20%">${r.name}</td>
                    <td width="30%">${r.path}</td>
                    <td>${r.urlGroup.name}</td>
                  </tr>
                </c:forEach>
                </tbody>
              </table>
            </div> <!-- end .table-responsive-->
            <input type="hidden" name="crudaction" value="delete" />
          </form:form>
        </div> <!-- end card-body -->
      </div> <!-- end card -->
    </div> <!-- end col -->
  </div> <!-- end row -->
</div>
<script>
  $(document).ready(function() {
    <!-- Modal initilization -->
    <c:if test="${not empty isModal}">
    if ($("#btnSave" ) != undefined) {
      if ($("#error_code").val() != undefined){$('#code').addClass('parsley-error');}
      if ($("#error_path").val() != undefined){$('#path').addClass('parsley-error');}
    }
    $('#myModal').modal('show');
    </c:if>

    $('#myModal').on('hidden.bs.modal', function () {
      $("#urlId").val('');
      $("#code").val('');
      $("#code" ).prop("disabled", false);
      $("#name").val('');
      $("#name" ).prop("disabled", false);
      $("#path").val('');
      $("#path" ).prop("disabled", false);
      $("#urlGroupId").val('');
      $("#urlGroupId" ).prop("disabled", false);

      $("#btnSave" ).prop("disabled", false);

      $(".parsley-errors-list").remove();
      $('input').removeClass('parsley-error');
      $("#error_code").remove();
      $("#error_path").remove();

    });
  });
  function editForm(urlId, code, name, path, urlGroupId){
    $("#urlId").val(urlId);
    $("#code").val(code);
    $("#name").val(name);
    $("#path").val(path);
    $("#urlGroupId").val(urlGroupId);

    $('#myModal').modal('show');
  }
  function deleteForm(urlId){
    bootbox.confirm("<spring:message code='msg.confirm.content'/>", function(result) {
      if (result) {
        $("<input type='hidden' name='pojo.urlId' value='"+urlId+"'>").appendTo($("#listForm"));
        $("#listForm").submit();
      }
    });
  };
</script>
</body>
</html>

