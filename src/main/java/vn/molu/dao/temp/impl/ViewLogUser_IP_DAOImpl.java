package vn.molu.dao.temp.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.temp.ViewLogUser_IP_DAO;
import vn.molu.dto.admin.admin.ViewLogUser_IP_DTO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ViewLogUser_IP_DAOImpl extends GenericDAOImpl<ViewLogUser_IP_DTO, String> implements ViewLogUser_IP_DAO {
    @Override
    public List<ViewLogUser_IP_DTO> layDS_IP_Truycap(String user_name, String from, String to) {
        String sqlCaule = "    select pc, date1, sum(quantity1) quantity from\n" +
                "        (select pc, trunc(view_datetime) date1,count(user_name) quantity1\n" +
                "    from\n" +
                "        mf_view_log      \n" +
                "    where\n" +
                "        user_name = '"+user_name+"' \n" +
                "        and view_datetime >= to_date('"+from+"','YYYY-MM-DD')     \n" +
                "        and view_datetime < to_date('"+to+"','YYYY-MM-DD')\n" +
                "        group by pc, trunc(view_datetime)\n" +
                "        UNION ALL\n" +
                "        select pc, trunc(view_datetime) date1, count(user_name) quantity1\n" +
                "    from\n" +
                "        mc_view_log      \n" +
                "    where\n" +
                "        user_name = '"+user_name+"' \n" +
                "        and  view_datetime >= to_date('"+from+"','YYYY-MM-DD')     \n" +
                "        and view_datetime < to_date('"+to+"','YYYY-MM-DD')\n" +
                "        group by pc, trunc(view_datetime))\n" +
                "        group by  pc, date1\n" +
                "        order by date1 desc";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlCaule)
                .getResultList());
    }

    private List<ViewLogUser_IP_DTO> DTOMapper(List<Object[]> objects) {
        List<ViewLogUser_IP_DTO> rs = new ArrayList<>();
        for (Object[] o:objects){
            ViewLogUser_IP_DTO dto = new ViewLogUser_IP_DTO();
            dto.setPc(o[0] != null ? o[0].toString().trim(): "");
            dto.setView_datetime(o[1] != null ? (Date) o[1]: null);
            dto.setQuantity(o[2] != null ? Long.parseLong(o[2].toString().trim()) : 0);
            rs.add(dto);
        }

        return rs;
    }
}
