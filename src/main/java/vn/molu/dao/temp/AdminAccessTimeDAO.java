package vn.molu.dao.temp;


import vn.molu.dto.admin.admin.AdminAccessTimeDTO;

import java.util.List;

public interface AdminAccessTimeDAO {
    List<AdminAccessTimeDTO> findAll();
}
