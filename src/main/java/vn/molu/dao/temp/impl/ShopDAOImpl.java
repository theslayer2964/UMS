package vn.molu.dao.temp.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.temp.ShopDAO;
import vn.molu.dto.admin.admin.ShopDTO;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ShopDAOImpl extends GenericDAOImpl<ShopDTO, String> implements ShopDAO {
    @Override
    public List<ShopDTO> findActiveShopCenCode2(String cen_code, String status) {
        String sqlClause = "select shop_code, cen_code, status, shop_code || ' - ' || name as ten, address " +
                "  from Shop where CEN_CODE = ?1 and STATUS = ?2 " +
                "  order by shop_code desc ";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .setParameter(1, cen_code)
                .setParameter(2, status)
                .getResultList());
    }

    @Override
    public List<ShopDTO> findShopDepartment(String cen_code, String status) {
        String sqlClause = "select shop_code, cen_code, status, name, address " +
                "  from Shop where CEN_CODE = ?1 and STATUS = ?2 " +
                "  order by shop_code desc ";
        return DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .setParameter(1, cen_code)
                .setParameter(2, status)
                .getResultList());
    }

    @Override
    public ShopDTO findById(String shopCode) {
        String sqlClause = "select shop_code, cen_code, status, name, address from shop where shop_code = ?1 ";
        List<ShopDTO> rs = DTOMapper((List<Object[]>) entityManager.createNativeQuery(sqlClause)
                .setParameter(1, shopCode)
                .getResultList());
        if (rs.size() > 0)
            return rs.get(0);
        return null;
    }

    @Override
    @Transactional
    public Integer updateShop(String name, String address, String shop_code) {
        try {
            String sqlClause = "update shop set address = ?1, name = ?2 where shop_code = ?3 ";
            Integer rs = entityManager.createNativeQuery(sqlClause)
                    .setParameter(1, address)
                    .setParameter(2, name)
                    .setParameter(3, shop_code)
                    .executeUpdate();
            return rs;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<ShopDTO> DTOMapper(List<Object[]> objects) {
        List<ShopDTO> resultList = new ArrayList<>();

        for (Object[] o : objects) {
            ShopDTO shopDTO = new ShopDTO();
            shopDTO.setShop_code(o[0] != null ? o[0].toString().trim() : "");
            shopDTO.setCenter_code(o[1] != null ? o[1].toString().trim() : "");
            shopDTO.setStatus(o[2] != null ? o[2].toString().trim() : "");
            shopDTO.setName(o[3] != null ? o[3].toString().trim() : "");
            shopDTO.setAddress(o[4] != null ? o[4].toString().trim() : "");
            resultList.add(shopDTO);
        }
        return resultList;
    }
}
