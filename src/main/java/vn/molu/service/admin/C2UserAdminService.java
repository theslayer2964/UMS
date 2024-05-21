package vn.molu.service.admin;

import vn.molu.dto.admin.admin.C2UserAdminDTO;
import vn.molu.service.ResponseContainer;
import vn.molu.service.request.ServerSideRequest;

import java.util.List;

public interface C2UserAdminService {
    ResponseContainer getListing(ServerSideRequest ssr);
    C2UserAdminDTO findbyUsername(String userName);
    List<C2UserAdminDTO> layDS_ViPham_TanSuat_TraCuu_10Ngay_DeGuiMail();

}
