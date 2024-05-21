package vn.molu.service.request;

import java.util.List;

public class ServerSideRequest {
    long draw;
    List<Column> columns;
    List<OrderCol> order;
    long start;
    long length;
    Search search;
    String searchName;
    String searchStatus;
    String searchGroup;

    public String getSearchName() {
        return searchName;
    }

    public void setSearchName(String searchName) {
        this.searchName = searchName;
    }

    public String getSearchStatus() {
        return searchStatus;
    }

    public void setSearchStatus(String searchStatus) {
        this.searchStatus = searchStatus;
    }

    public String getSearchGroup() {
        return searchGroup;
    }

    public void setSearchGroup(String searchGroup) {
        this.searchGroup = searchGroup;
    }

    public long getDraw() {
        return draw;
    }

    public void setDraw(long draw) {
        this.draw = draw;
    }

    public List<Column> getColumns() {
        return columns;
    }

    public void setColumns(List<Column> columns) {
        this.columns = columns;
    }

    public List<OrderCol> getOrder() {
        return order;
    }

    public void setOrder(List<OrderCol> order) {
        this.order = order;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public Search getSearch() {
        return search;
    }

    public void setSearch(Search search) {
        this.search = search;
    }

    @Override
    public String toString() {
        return "ServerSideRequest{" +
                "draw=" + draw +
                ", columns=" + columns +
                ", order=" + order +
                ", start=" + start +
                ", length=" + length +
                ", search=" + search +
                ", searchName='" + searchName + '\'' +
                ", searchStatus='" + searchStatus + '\'' +
                ", searchGroup='" + searchGroup + '\'' +
                '}';
    }
}
