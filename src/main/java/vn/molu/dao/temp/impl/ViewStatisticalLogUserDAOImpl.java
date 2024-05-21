package vn.molu.dao.temp.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.temp.ViewStatisticalLogUserDAO;
import vn.molu.dto.admin.admin.ViewStatisticalLogUserDTO;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ViewStatisticalLogUserDAOImpl extends GenericDAOImpl<ViewStatisticalLogUserDTO, String> implements ViewStatisticalLogUserDAO {

    private List<ViewStatisticalLogUserDTO> DTOMapper(List<Object[]> objects) {
        List<ViewStatisticalLogUserDTO> resultList = new ArrayList<>();

        for (Object[] o: objects){
            ViewStatisticalLogUserDTO dto = new ViewStatisticalLogUserDTO();
            dto.setShop_code(o[0] != null ? o[0].toString().trim() : "");
            dto.setShop_name(o[1] != null ? o[1].toString().trim() : "");
            dto.setUser_name(o[2] != null ? o[2].toString().trim() : "");
            dto.setSoLuong(o[3] != null ? Long.parseLong(o[3].toString().trim()) : 0);
            resultList.add(dto);
        }

        return resultList;
    }

    @Override
    public List<ViewStatisticalLogUserDTO> layDS_TraCuuUser_Log(String from, String to) {
        String sqlClause = "        select sh.shop_code, sh.name shop_name, a.user_name, sum(a.quantity) \n" +
                "                from shop sh, c2_admin_user u, ( select user_name, count(*) quantity\n" +
                "                                                      from mc_view_log \n" +
                "                                                      where view_datetime >= to_date('"+from+"','YYYY-MM-DD')\n" +
                "                                                      and view_datetime < to_date('"+to+"','YYYY-MM-DD')\n" +
                "                                                      group by user_name\n" +
                "                                                      UNION ALL\n" +
                "                                                      select user_name, count(*) quantity\n" +
                "                                                      from mf_view_log \n" +
                "                                                      where view_datetime >= to_date('"+from+"','YYYY-MM-DD')\n" +
                "                                                      and view_datetime < to_date('"+to+"','YYYY-MM-DD')\n" +
                "                                                      group by user_name) a\n" +
                "                where sh.shop_code = u.shop_code\n" +
                "                      and u.user_name = a.user_name\n" +
                "                group by sh.shop_code,sh.name,a.user_name \n" +
                "                order by sum(a.quantity) desc";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .getResultList());
    }

    @Override
    public List<ViewStatisticalLogUserDTO> layDS_TraCuuUser_Log_All(String from, String to, String shopCode, String users) {
        String sqlClause = "        select sh.shop_code, sh.name shop_name, a.user_name, sum(a.quantity) \n" +
                "                from shop sh, c2_admin_user u, ( select user_name, count(*) quantity\n" +
                "                                                      from mc_view_log \n" +
                "                                                      where view_datetime >= to_date('"+from+"','YYYY-MM-DD')\n" +
                "                                                      and view_datetime < to_date('"+to+"','YYYY-MM-DD')\n" +
                "                                                      and user_name in ("+users+")         \n" +
                "                                                      group by user_name\n" +
                "                                                      UNION ALL\n" +
                "                                                      select user_name, count(*) quantity\n" +
                "                                                      from mf_view_log \n" +
                "                                                      where view_datetime >= to_date('"+from+"','YYYY-MM-DD')\n" +
                "                                                      and view_datetime < to_date('"+to+"','YYYY-MM-DD')\n" +
                "                                                      and user_name in ("+users+")         \n" +
                "                                                      group by user_name) a\n" +
                "where sh.shop_code = u.shop_code\n" +
                "      and u.user_name = a.user_name\n" +
                " group by sh.shop_code, sh.name, a.user_name\n" +
                " order by sum(a.quantity) desc";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .getResultList());
    }
}
