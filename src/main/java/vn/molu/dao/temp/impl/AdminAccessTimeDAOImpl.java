package vn.molu.dao.temp.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.temp.AdminAccessTimeDAO;
import vn.molu.dto.admin.admin.AdminAccessTimeDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AdminAccessTimeDAOImpl extends GenericDAOImpl<AdminAccessTimeDTO, Integer> implements AdminAccessTimeDAO {
    @Override
    public List<AdminAccessTimeDTO> findAll() {
        String sqlClause = "select access_time_id, name from admin_access_time ";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .getResultList());
    }

    private List<AdminAccessTimeDTO> DTOMapper(List<Object[]> objects) {
        List<AdminAccessTimeDTO> rs = new ArrayList<>();
        for (Object[] o: objects){
            AdminAccessTimeDTO dto = new AdminAccessTimeDTO();
            dto.setAccess_time_id(o[0] != null ? Integer.parseInt(o[0].toString().trim()) : 0);
            dto.setName(o[1] != null ? o[1].toString().trim() : "");
            rs.add(dto);
        }
        return rs;
    }
}
