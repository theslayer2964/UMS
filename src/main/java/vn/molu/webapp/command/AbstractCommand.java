package vn.molu.webapp.command;

import java.io.Serializable;
import java.util.List;

/**
 * @author phaolo
 * @since 2020-03-17
 */
public class AbstractCommand<E> implements Serializable {

    private static final long serialVersionUID = 7632896350818457453L;
    protected int maxPageItems = 20;
    protected int reportMaxPageItems = 100;
    private int firstItem = 0;
    private int totalItems = 0;
    private String sortExpression;
    private String sortDirection;
    private String[] checkList;
    private List<E> listResult;
    private String crudaction;
    private String sessionId;
    private String tableId = "tableList";
    private String warningMsg;
    private String messageType;

    private int page = 1;
    protected E pojo;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getMaxPageItems() {
        return maxPageItems;
    }

    public void setMaxPageItems(int maxPageItems) {
        this.maxPageItems = maxPageItems;
    }

    public int getReportMaxPageItems() {
        return reportMaxPageItems;
    }

    public void setReportMaxPageItems(int reportMaxPageItems) {
        this.reportMaxPageItems = reportMaxPageItems;
    }

    public int getFirstItem() {
        return firstItem;
    }

    public void setFirstItem(int firstItem) {
        this.firstItem = firstItem;
    }

    public int getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(int totalItems) {
        this.totalItems = totalItems;
    }

    public String getSortExpression() {
        return sortExpression;
    }

    public void setSortExpression(String sortExpression) {
        this.sortExpression = sortExpression;
    }

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    public String[] getCheckList() {
        return checkList;
    }

    public void setCheckList(String[] checkList) {
        this.checkList = checkList;
    }

    public List<E> getListResult() {
        return listResult;
    }

    public void setListResult(List<E> listResult) {
        this.listResult = listResult;
    }

    public String getCrudaction() {
        return crudaction;
    }

    public void setCrudaction(String crudaction) {
        this.crudaction = crudaction;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getWarningMsg() {
        return warningMsg;
    }

    public void setWarningMsg(String warningMsg) {
        this.warningMsg = warningMsg;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public E getPojo() {
        return pojo;
    }

    public void setPojo(E pojo) {
        this.pojo = pojo;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
}
