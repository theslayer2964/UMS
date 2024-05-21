package vn.molu.dao.temp;

import vn.molu.dto.admin.admin.CSKK_UserDTO;

import java.util.List;

public interface CSKK_UserDAO {
    Integer insert(CSKK_UserDTO cskkUserDTO);
    String executeProcedureInsert();
    String deleteTemp();
    Integer grand_DuyetSoPermission_ForCHT(String email);
    List<CSKK_UserDTO> getListCSKK_Result();
    List<CSKK_UserDTO> getListCSKK_ResultUnsuccessful();
    CSKK_UserDTO findCSKK_ResultByUsername(String username);
    boolean findMailInHr_Nhanvien_Alldata(String mail);
    Integer lockUser(String userName);
}
