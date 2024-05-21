package vn.molu.dao.temp;

import vn.molu.dto.admin.admin.Resum_UserDTO;

public interface Resum_UserDAO {
    Integer insert(Resum_UserDTO resumUserDTO);

    Integer lockUser(String userName);
}
