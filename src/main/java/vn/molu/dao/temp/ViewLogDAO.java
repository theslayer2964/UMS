package vn.molu.dao.temp;

import vn.molu.dto.admin.admin.ViewLogDTO;

import java.text.ParseException;
import java.util.List;

public interface ViewLogDAO {
    List<ViewLogDTO> lay_LichSu_TraCuuLog_TheoTen_Thoigian(String username, String from, String to) throws ParseException;

}
