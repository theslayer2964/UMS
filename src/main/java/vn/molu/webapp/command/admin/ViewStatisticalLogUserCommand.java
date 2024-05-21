package vn.molu.webapp.command.admin;

import vn.molu.dto.admin.admin.ViewStatisticalLogUserDTO;
import vn.molu.webapp.command.AbstractCommand;

public class ViewStatisticalLogUserCommand extends AbstractCommand<ViewStatisticalLogUserDTO> {

    public ViewStatisticalLogUserCommand() {
        this.pojo = new ViewStatisticalLogUserDTO();
    }

    private String from;
    private String to;
    private String shopCode;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

}
