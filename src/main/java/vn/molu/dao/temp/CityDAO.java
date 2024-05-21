package vn.molu.dao.temp;

import vn.molu.dto.admin.admin.CityDTO;

import java.util.List;

public interface CityDAO {

    List<CityDTO> findCity();
    List<CityDTO> getCitybyId(String city);
    List<CityDTO> getCityname(String id);
}
