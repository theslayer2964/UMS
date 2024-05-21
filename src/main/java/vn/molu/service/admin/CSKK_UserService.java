package vn.molu.service.admin;

import vn.molu.dto.admin.admin.CSKK_UserDTO;

import java.util.List;

public interface CSKK_UserService {
    Integer insert(CSKK_UserDTO cskkUserDTO);
    Integer grand_DuyetSoPermission_ForCHT(String email);
    String deleteTemp();
    List<CSKK_UserDTO> getListCSKK_DTO();
    List<CSKK_UserDTO> getListCSKK_DTO_Unsuccessful();
    CSKK_UserDTO findCSKK_UserByUsername(String username);
    boolean findMailInHr_Nhanvien_Alldata(String mail);
    Integer lockUser(String userName);
}
