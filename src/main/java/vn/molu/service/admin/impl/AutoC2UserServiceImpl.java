package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.domain.admin.C2AdminUserAuto;
import vn.molu.repository.admin.C2AdminUserAutoRepository;
import vn.molu.service.admin.AutoC2UserService;

import java.util.List;

@Service
public class AutoC2UserServiceImpl implements AutoC2UserService {
    @Autowired
    private C2AdminUserAutoRepository repository;


    @Override
    public List<C2AdminUserAuto> findAll() {
        return repository.findAll();
    }

    @Override
    public C2AdminUserAuto save(C2AdminUserAuto user) {
        return repository.save(user);
    }
}
