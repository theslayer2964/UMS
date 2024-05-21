package vn.molu.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import vn.molu.domain.admin.Area;
import vn.molu.dto.admin.admin.CityDTO;

import java.util.List;

@Repository
public interface AreaRepository extends JpaRepository<Area, String> {

    @Query(value = "select distinct city_id, city_name from area_2 order by city_id", nativeQuery = true)
    List<CityDTO> findCity();

    @Query(value = "select id, name, eng_name, city_id , city_name, from area_2 where city_id = :city order by city_id ", nativeQuery = true)
    List<CityDTO> getCityById(String city);
}
