package vn.molu.dao.temp;

import vn.molu.dto.admin.admin.UserLoginDTO;

import java.util.List;

public interface UserLoginDAO {
    List<UserLoginDTO> findUsersByUsername(List<String> emails);

    UserLoginDTO findUserByname(String username);
}
