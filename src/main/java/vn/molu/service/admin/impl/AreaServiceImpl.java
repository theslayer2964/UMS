package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.domain.admin.Area;
import vn.molu.dto.admin.admin.CityDTO;
import vn.molu.repository.admin.AreaRepository;
import vn.molu.service.admin.AreaService;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {
    @Autowired
    private AreaRepository areaRepository;

    @Override
    public List<CityDTO> getCity() {
        return areaRepository.findCity();
    }

    @Override
    public List<CityDTO> getCitybyId(String city) {
        return areaRepository.getCityById(city);
    }
}
