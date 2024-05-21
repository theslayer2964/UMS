package vn.molu.dao.temp.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.temp.ViewLogDAO;
import vn.molu.dto.admin.admin.ViewLogDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ViewLogDAOImpl extends GenericDAOImpl<ViewLogDTO, String> implements ViewLogDAO {
    private List<ViewLogDTO> DTOMapper(List<Object[]> objects) {
        List<ViewLogDTO> rs = new ArrayList<>();
        for (Object[] o : objects) {
            ViewLogDTO viewLogDTO = new ViewLogDTO();
            viewLogDTO.setUser_name(o[0] != null ? o[0].toString().trim() : "");
            viewLogDTO.setPc(o[1] != null ? o[1].toString().trim() : "");
            viewLogDTO.setView_datetime(o[2] != null ? o[2].toString().trim() : null);
            viewLogDTO.setQuantity(o[3] != null ? Long.parseLong(o[3].toString().trim()) : 0);
            rs.add(viewLogDTO);
        }
        return rs;
    }


    @Override
    public List<ViewLogDTO> lay_LichSu_TraCuuLog_TheoTen_Thoigian(String username, String from, String to) {
        String sqlClause = "    select user_name, pc, ngay, sum(quantity) from\n" +
                "                        (select user_name,pc,trunc(view_datetime) ngay,count(user_name) quantity      \n" +
                "                        from mf_view_log      \n" +
                "                        where user_name = '" + username + "'    \n" +
                "                        and view_datetime >= to_date('" + from + "','YYYY-MM-DD')      \n" +
                "                        and view_datetime < to_date('" + to + "','YYYY-MM-DD')      \n" +
                "                        group by user_name,pc,trunc(view_datetime) \n" +
                "                        UNION ALL\n" +
                "                        select user_name,pc,trunc(view_datetime) ngay,count(user_name) quantity      \n" +
                "                        from mc_view_log      \n" +
                "                        where user_name = '" + username + "'    \n" +
                "                        and view_datetime >= to_date('" + from + "','YYYY-MM-DD')      \n" +
                "                        and view_datetime < to_date('" + to + "','YYYY-MM-DD')      \n" +
                "                        group by user_name,pc,trunc(view_datetime))\n" +
                "        group by user_name, pc, ngay\n" +
                "        order by ngay DESC";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .getResultList());
    }
}
