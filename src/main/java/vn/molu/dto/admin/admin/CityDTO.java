package vn.molu.dto.admin.admin;

public class CityDTO {

    private String city_id;
    private String city_name;

    public String getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    @Override
    public String toString() {
        return "CityDTO{" +
                "city_id='" + city_id + '\'' +
                ", city_name='" + city_name + '\'' +
                '}';
    }
}
