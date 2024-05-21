package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.dao.temp.Resum_UserDAO;
import vn.molu.dto.admin.admin.Resum_UserDTO;
import vn.molu.service.admin.Resnum_UserService;

@Service
public class Resnum_UserServiceImpl implements Resnum_UserService {
    @Autowired
    private Resum_UserDAO resumUserDAO;
    @Override
    public Integer insert(Resum_UserDTO resumUserDTO) {
        return resumUserDAO.insert(resumUserDTO);
    }

    @Override
    public Integer lockUser(String userName) {
        try {
            return resumUserDAO.lockUser(userName);
        } catch (Exception e){
            throw e;
        }
    }
}
