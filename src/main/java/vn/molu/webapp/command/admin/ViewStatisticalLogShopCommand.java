package vn.molu.webapp.command.admin;

import vn.molu.dto.admin.admin.ViewStatisticalLogShopDTO;
import vn.molu.webapp.command.AbstractCommand;

public class ViewStatisticalLogShopCommand extends AbstractCommand<ViewStatisticalLogShopDTO> {

    public ViewStatisticalLogShopCommand() {
        this.pojo = new ViewStatisticalLogShopDTO();
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
