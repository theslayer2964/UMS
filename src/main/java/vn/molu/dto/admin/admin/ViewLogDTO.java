package vn.molu.dto.admin.admin;



public class ViewLogDTO {

    private String user_name;

    private String pc;

    private String view_datetime;

    private Long quantity;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPc() {
        return pc;
    }

    public void setPc(String pc) {
        this.pc = pc;
    }

    public String getView_datetime() {
        return view_datetime;
    }

    public void setView_datetime(String view_datetime) {
        this.view_datetime = view_datetime;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "ViewLogDTO{" +
                "user_name='" + user_name + '\'' +
                ", pc='" + pc + '\'' +
                ", view_datetime=" + view_datetime +
                ", quantity=" + quantity +
                '}';
    }
}
