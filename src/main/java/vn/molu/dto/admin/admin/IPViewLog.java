package vn.molu.dto.admin.admin;

import java.util.Date;

public class IPViewLog {
    private String user_name;

    private Date view_datetime;

    private String pc;

    private Long access_number;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getView_datetime() {
        return view_datetime;
    }

    public void setView_datetime(Date view_datetime) {
        this.view_datetime = view_datetime;
    }

    public Long getAccess_number() {
        return access_number;
    }

    public void setAccess_number(Long access_number) {
        this.access_number = access_number;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    @Override
    public String toString() {
        return "IPViewLog{" +
                ", user_name='" + user_name + '\'' +
                ", view_datetime=" + view_datetime +
                ", access_number=" + access_number +
                ", granted_ip='" + pc + '\'' +
                '}';
    }

    public IPViewLog(String user_name, Date view_datetime, String pc, Long access_number) {
        this.user_name = user_name;
        this.view_datetime = view_datetime;
        this.pc = pc;
        this.access_number = access_number;
    }

    public IPViewLog() {
    }
}
