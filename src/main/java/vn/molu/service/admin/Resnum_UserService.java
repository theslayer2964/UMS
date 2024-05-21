package vn.molu.service.admin;

import vn.molu.dto.admin.admin.Resum_UserDTO;

public interface Resnum_UserService {
    Integer insert(Resum_UserDTO resumUserDTO);

    Integer lockUser(String userName);
}
