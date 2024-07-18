package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.dao.temp.UserLoginDAO;
import vn.molu.dto.admin.admin.UserLoginDTO;
import vn.molu.service.admin.UserLoginService;

import java.util.List;

@Service
public class UserLoginServiceImpl implements UserLoginService {

    @Autowired
    private UserLoginDAO userLoginDAO;

    @Override
    public List<UserLoginDTO> findUsersResnumByName(List<String> usernames) {
        return userLoginDAO.findUsersByUsername(usernames);
    }

    @Override
    public UserLoginDTO findUserResnumByName(String username) {
        return userLoginDAO.findUserByname(username);
    }
}
