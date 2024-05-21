package vn.molu.dao.temp;

import vn.molu.dto.admin.admin.CollectionGroupDTO;

import java.util.List;

public interface CollectionGroupDAO {
    List<CollectionGroupDTO> findActiveGroupCollection(String status);
}
