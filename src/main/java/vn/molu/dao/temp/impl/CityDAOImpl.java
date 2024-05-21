package vn.molu.dao.temp.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.temp.CityDAO;
import vn.molu.dto.admin.admin.CityDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public class CityDAOImpl extends GenericDAOImpl<CityDAO, String> implements CityDAO {
    @Override
    public List<CityDTO> findCity() {
        String sqlClause = "select distinct city_id, city_name from AREA_2";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .getResultList());
    }

    private List<CityDTO> DTOMapper(List<Object[]> objects) {
        List<CityDTO> rs = new ArrayList<>();
        for (Object[] o : objects) {
            CityDTO dto = new CityDTO();
            dto.setCity_id(o[0] != null ? o[0].toString().trim() : "");
            dto.setCity_name(o[1] != null ? o[1].toString().trim() : "");
            rs.add(dto);
        }
        return rs;
    }

    @Override
    public List<CityDTO> getCitybyId(String city) {
        String sqlClause = "select city_id, name from AREA_2 where city_id = ?1 ";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .setParameter(1, city)
                .getResultList());
    }

    @Override
    public List<CityDTO> getCityname(String id) {
        String sqlClause = "select city_id, city_name as name from AREA_2 where city_id = ?1";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .setParameter(1, id)
                .getResultList());
    }
}
