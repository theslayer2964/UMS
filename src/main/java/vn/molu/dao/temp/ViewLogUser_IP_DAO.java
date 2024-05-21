package vn.molu.dao.temp;

import vn.molu.dto.admin.admin.ViewLogUser_IP_DTO;

import java.util.List;

public interface ViewLogUser_IP_DAO {

    List<ViewLogUser_IP_DTO> layDS_IP_Truycap(String user_name, String from, String to);


}
