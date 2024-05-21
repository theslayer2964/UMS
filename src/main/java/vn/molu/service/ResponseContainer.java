package vn.molu.service;

import vn.molu.dto.admin.admin.C2UserAdminDTO;

import java.util.List;

public class ResponseContainer {
    long draw;
    long recordsTotal;
    long recordsFiltered;
    List<C2UserAdminDTO> data;

    public long getDraw() {
        return draw;
    }

    public void setDraw(long draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<C2UserAdminDTO> getData() {
        return data;
    }

    public void setData(List<C2UserAdminDTO> data) {
        this.data = data;
    }

    public ResponseContainer(long draw, long recordsTotal, long recordsFiltered, List<C2UserAdminDTO> data) {
        this.draw = draw;
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsFiltered;
        this.data = data;
    }

    public ResponseContainer() {
    }

    @Override
    public String toString() {
        return "ResponseContainer{" +
                "draw=" + draw +
                ", recordsTotal=" + recordsTotal +
                ", recordsFiltered=" + recordsFiltered +
                ", data=" + data +
                '}';
    }
}
