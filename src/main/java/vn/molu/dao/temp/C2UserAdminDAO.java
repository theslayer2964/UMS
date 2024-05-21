package vn.molu.dao.temp;

import vn.molu.dto.admin.admin.C2UserAdminDTO;
import vn.molu.service.request.ServerSideRequest;

import java.util.List;
import java.util.Map;

public interface C2UserAdminDAO {

    List<C2UserAdminDTO> findAll();
    List<C2UserAdminDTO> layDS_ViPham_Homnay();
    List<C2UserAdminDTO> layDS_ViPham_TanSuat_TraCuu_All();
    List<C2UserAdminDTO> layDS_ViPham_TanSuat_TraCuu_10Ngay_DeGuiMail();
    C2UserAdminDTO findByUsername(String username);
    C2UserAdminDTO findByUserId(Long userId);
    List<C2UserAdminDTO> findByProperty(Map<String, Object> props);
    Integer updateStatusC2UserAdmin(Long userId, String newStatus);
    Integer update(Long userId, String newStatus, String newGrantedIp);
    Integer updateIP(String userId, String newGrantedIp);
    List<C2UserAdminDTO> findUserByShop(String shop_code);
    Integer count();
    List<C2UserAdminDTO> findU2Limit(ServerSideRequest ssr);
    List<C2UserAdminDTO> findByPropertySS(Map<String, Object> props, ServerSideRequest ssr);
    int countFindByPropertySS(Map<String, Object> prop);
    List<C2UserAdminDTO> findBannedUserNNotSendExplanation();
}
