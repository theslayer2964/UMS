package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.domain.admin.UrlGroup;
import vn.molu.repository.admin.UrlGroupRepository;
import vn.molu.service.admin.UrlGroupService;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author phaolo
 * @since 2020-03-10
 */
@Service
public class UrlGroupServiceImpl implements UrlGroupService {
    private final Logger log = Logger.getLogger(getClass().toString());

    @Autowired
    private UrlGroupRepository urlGroupRepository;

    @Override
    public UrlGroup findByCode(String value) {
        log.info("Find with code: " + value);

        try {
          UrlGroup urlGroup = urlGroupRepository.findByCode(value);
          return  urlGroup;
        } catch (Exception e){
            throw  e;
        }
    }

    @Override
    public void delete(Long urlGroupId) {
        log.info("Delete with id: " + urlGroupId);

        try {
            urlGroupRepository.deleteById(urlGroupId);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<UrlGroup> findAllByUserRoleId(Long userRoleId) {
        return urlGroupRepository.findAllByUserRoleId(userRoleId,"1");
    }

    @Override
    public List<UrlGroup> findAll() {
        log.info("Find all");

        try {
            return urlGroupRepository.findAll();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UrlGroup save(UrlGroup pojo) {
        log.info("save a Url Group");

        try {
            return urlGroupRepository.save(pojo);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UrlGroup findById(Long urlGroupId) {
        log.info("save a Url Group");

        try {
            return urlGroupRepository.findById(urlGroupId).get();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public UrlGroup update(UrlGroup pojo) {
        log.info("update a Url Group");

        try {
            return urlGroupRepository.save(pojo);
        } catch (Exception e){
            throw e;
        }
    }
}
