package vn.molu.dao.temp.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.temp.ViewLogUser_ShopDAO;
import vn.molu.dto.admin.admin.ViewLogUser_ShopDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ViewLogUser_ShopDAOImpl extends GenericDAOImpl<ViewLogUser_ShopDTO, String> implements ViewLogUser_ShopDAO {
    @Override
    public List<ViewLogUser_ShopDTO> layDS_UserByShop_ThongKe(String users, String from, String to) {
        String sqlClause = "select user_name, sum(soluong) from\n" +
                "          (select user_name,count(user_name) soluong         \n" +
                "                from mc_view_log mf         \n" +
                "                where\n" +
                "                view_datetime >= to_date('"+from+"','YYYY-MM-DD')          \n" +
                "                and view_datetime < to_date('"+to+"','YYYY-MM-DD')         \n" +
                "                and user_name in ("+users+")         \n" +
                "                group by user_name         \n" +
                "                UNION ALL \n" +
                "                select user_name, count(user_name) soluong         \n" +
                "                from mf_view_log mf         \n" +
                "                where\n" +
                "                    view_datetime >= to_date('"+from+"','YYYY-MM-DD')          \n" +
                "                    and view_datetime < to_date('"+to+"','YYYY-MM-DD')         \n" +
                "                    and user_name in ("+users+")         \n" +
                "                group by user_name )\n" +
                "                    group by user_name\n" +
                "                order by user_name desc";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .getResultList());
    }

    private List<ViewLogUser_ShopDTO> DTOMapper(List<Object[]> objects) {
        List<ViewLogUser_ShopDTO> rs = new ArrayList<>();
        for (Object[] o: objects){
            ViewLogUser_ShopDTO dto = new ViewLogUser_ShopDTO();
            dto.setUser_name(o[0] != null ? o[0].toString().trim() : "");
            dto.setQuantity(o[1] != null ? Long.parseLong(o[1].toString().trim()) : 0);
            rs.add(dto);
        }
        return rs;
    }
}
