package vn.molu.dto.admin.admin;

import java.io.Serializable;
import java.util.Date;

public class ViewLogUser_IP_DTO{

    private String pc;
    private Date view_datetime;
    private long quantity;

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public Date getView_datetime() {
        return view_datetime;
    }

    public void setView_datetime(Date view_datetime) {
        this.view_datetime = view_datetime;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ViewLogUser_IP_DTO{" +
                "pc='" + pc + '\'' +
                ", view_datetime=" + view_datetime +
                ", quantity=" + quantity +
                '}';
    }
}
