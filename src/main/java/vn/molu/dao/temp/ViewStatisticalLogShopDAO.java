package vn.molu.dao.temp;

import vn.molu.dto.admin.admin.ViewStatisticalLogShopDTO;

import java.util.List;

public interface ViewStatisticalLogShopDAO {

    List<ViewStatisticalLogShopDTO> layDS_TraCuu_Log_TheoShop(String from, String to);
    List<ViewStatisticalLogShopDTO> layDS_TraCuu_Log_TheoShop_All(String from, String to, String shopCode, String users);

}
