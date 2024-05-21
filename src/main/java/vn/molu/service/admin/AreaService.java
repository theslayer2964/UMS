package vn.molu.service.admin;

import vn.molu.domain.admin.Area;
import vn.molu.dto.admin.admin.CityDTO;

import java.util.List;

public interface AreaService {

    List<CityDTO> getCity();

    List<CityDTO> getCitybyId(String city);
}
