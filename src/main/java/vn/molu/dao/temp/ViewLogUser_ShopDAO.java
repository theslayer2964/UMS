package vn.molu.dao.temp;

import vn.molu.dto.admin.admin.ViewLogUser_ShopDTO;

import java.util.List;


public interface ViewLogUser_ShopDAO {
    List<ViewLogUser_ShopDTO> layDS_UserByShop_ThongKe(String users, String from, String to);
}
