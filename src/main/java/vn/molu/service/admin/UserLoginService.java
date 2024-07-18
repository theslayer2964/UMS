package vn.molu.service.admin;

import vn.molu.dto.admin.admin.UserLoginDTO;

import java.util.List;

public interface UserLoginService {
    List<UserLoginDTO> findUsersResnumByName(List<String> usernames);

    UserLoginDTO findUserResnumByName(String username);
}
