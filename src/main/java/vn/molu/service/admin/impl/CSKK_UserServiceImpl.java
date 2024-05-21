package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.dao.temp.CSKK_UserDAO;
import vn.molu.dto.admin.admin.CSKK_UserDTO;
import vn.molu.service.admin.CSKK_UserService;

import java.util.List;

@Service
public class CSKK_UserServiceImpl implements CSKK_UserService {

    @Autowired
    private CSKK_UserDAO cskkUserDAO;

    @Override
    public Integer insert(CSKK_UserDTO cskkUserDTO) {
        try {
            return cskkUserDAO.insert(cskkUserDTO);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Integer grand_DuyetSoPermission_ForCHT(String email) {
        try {
            return cskkUserDAO.grand_DuyetSoPermission_ForCHT(email);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String deleteTemp() {
        String rs = cskkUserDAO.deleteTemp();
        return rs.toString();
    }

    @Override
    public List<CSKK_UserDTO> getListCSKK_DTO() {
        return cskkUserDAO.getListCSKK_Result();
    }

    @Override
    public List<CSKK_UserDTO> getListCSKK_DTO_Unsuccessful() {
        return cskkUserDAO.getListCSKK_ResultUnsuccessful();
    }

    @Override
    public CSKK_UserDTO findCSKK_UserByUsername(String username) {
        return cskkUserDAO.findCSKK_ResultByUsername(username);
    }

    @Override
    public boolean findMailInHr_Nhanvien_Alldata(String mail) {
        try {
            return cskkUserDAO.findMailInHr_Nhanvien_Alldata(mail);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public Integer lockUser(String userName) {
        try {
            return cskkUserDAO.lockUser(userName);
        } catch (Exception e){
            throw e;
        }
    }
}
