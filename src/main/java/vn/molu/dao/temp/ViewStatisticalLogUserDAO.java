package vn.molu.dao.temp;

import vn.molu.dto.admin.admin.ViewStatisticalLogShopDTO;
import vn.molu.dto.admin.admin.ViewStatisticalLogUserDTO;

import java.util.List;

public interface ViewStatisticalLogUserDAO {

    List<ViewStatisticalLogUserDTO> layDS_TraCuuUser_Log(String from, String to);
    List<ViewStatisticalLogUserDTO> layDS_TraCuuUser_Log_All(String from, String to, String shopCode, String users);
}
