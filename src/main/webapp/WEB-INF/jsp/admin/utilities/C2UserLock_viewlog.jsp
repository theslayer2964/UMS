<%--
  Created by IntelliJ IDEA.
  User: User
  Date: 1/9/2024
  Time: 9:23 AM
  To change this template use File | Settings | File Templates.
--%>
<%@include file="/common/taglibs.jsp" %>
<div class="modal fade" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true"
     style="display: none" id="viewlogModal">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 1000px;right: 50%">
            <div class="modal-header">
                <h4 class="modal-title mt-0">
                    <spring:message code="label.user.info"/>
                    <span id="usernameView"></span>
                </h4>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true" onclick="destroyTable()">&times;</span>
                </button>
            </div>
            <div class="modal-body">
                <form id="form">
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="card-box table-responsive">
                                <div class="form-group row">
                                    <label for="searchNameFrom" class="col-sm-2 col-form-label"
                                           style="left: 6%;"><spring:message code="label.c2user.from"/></label>
                                    <div class="col-sm-3">
                                        <input type="date" id="searchNameFrom" class="form-control"/>
                                    </div>
                                    <label for="searchNameTo" class="col-sm-2 col-form-label"
                                           style="left: 6%;"><spring:message code="label.c2user.to"/></label>
                                    <div class="col-sm-3">
                                        <input type="date" id="searchNameTo" class="form-control"/>
                                    </div>
                                    <input type="hidden" id="searchNameUser"/>
                                </div>
                                <div class="form-group row">
                                    <div class="col-sm-2"></div>
                                    <div class="col-sm-8 offset-md-2">
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
                </form>
                <div class="table-responsive" style="display: none;" id="table1">
                    <table id="example" class="display dataTable cell-border table table-bordered"
                           style="border-collapse: collapse; border-spacing: 0; width: 100%;"></table>
                </div>
                <div id="loadingEvent">
                    <div class="spinner-border text-success" role="status">
                        <span class="sr-only">Loading...</span>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
