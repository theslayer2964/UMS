package vn.molu.dao;

import vn.molu.dto.admin.admin.C2UserDTO;

public interface CommonDAO {
    C2UserDTO findActiveUserByUserName(String username);

}
