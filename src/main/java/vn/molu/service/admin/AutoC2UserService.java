package vn.molu.service.admin;

import vn.molu.domain.admin.C2AdminUserAuto;

import java.util.List;

public interface AutoC2UserService {
    List<C2AdminUserAuto> findAll();

    C2AdminUserAuto save(C2AdminUserAuto user);
}
