package vn.molu.dao.temp.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.temp.CollectionGroupDAO;
import vn.molu.dto.admin.admin.CollectionGroupDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CollectionGroupDAOImpl extends GenericDAOImpl<CollectionGroupDTO, Integer> implements CollectionGroupDAO {
    @Override
    public List<CollectionGroupDTO> findActiveGroupCollection(String status) {
        String sqlClause = "select collection_group_id, name from collection_group where status = ?1";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .setParameter(1, status)
                .getResultList());
    }

    private List<CollectionGroupDTO> DTOMapper(List<Object[]> objects) {
        List<CollectionGroupDTO> rs = new ArrayList<>();
        for (Object[] o: objects){
            CollectionGroupDTO dto = new CollectionGroupDTO();
            dto.setCollectionGroupId(o[0] != null ? Integer.parseInt(o[0].toString().trim()) : 0);
            dto.setName(o[1] != null ? o[1].toString().trim() : "");
            rs.add(dto);
        }
        return rs;
    }
}
