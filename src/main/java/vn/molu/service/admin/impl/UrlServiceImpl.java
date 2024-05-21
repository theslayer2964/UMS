package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.molu.domain.admin.Url;
import vn.molu.domain.admin.UrlGroup;
import vn.molu.repository.admin.UrlRepository;
import vn.molu.service.admin.UrlService;

import java.util.List;
import java.util.logging.Logger;

/**
 * @author phaolo
 * @since 2020-03-10
 */
@Service
public class UrlServiceImpl implements UrlService {
    private final Logger log = Logger.getLogger(getClass().toString());

    @Autowired
    private UrlRepository urlRepository;

    @Override
    public Url findByCode(String code) {
        log.info("Find with code: " + code);

        try {
            Url url = urlRepository.findByCode(code);
            return url;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Url findByPath(String path) {
        log.info("Find with path: " + path);

        try {
            Url url = urlRepository.findByPath(path);
            return url;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public List<Url> findAllByUserRoleId(Long userRoleId, Long urlGroupId) {
        return urlRepository.findAllByUserRoleId(userRoleId,urlGroupId,"1");
    }


    @Override
    public void delete(Long urlId) {
        log.info("Delete with id: " + urlId);

        try {
            urlRepository.deleteById(urlId);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Url> findAll() {
        log.info("Find all");

        try {
            return urlRepository.findAll();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public Url save(Url pojo) {
        log.info("save a url");

        try {
            return urlRepository.save(pojo);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public Url findById(Long urlGroupId) {
        log.info("find a Url");

        try {
            return urlRepository.findById(urlGroupId).get();
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public Url update(Url pojo) {
        log.info("update a url");

        try {
            return urlRepository.save(pojo);
        } catch (Exception e){
            throw e;
        }
    }

    @Override
    public List<Url> findAllNotAssignUserRoleId(Long userRoleId) {
        log.info("Find all URL which are not assigned to UserRole " + userRoleId);

        try {
            return urlRepository.findAllNotAssignUserRoleId(userRoleId);
        } catch (Exception e){
            throw e;
        }
    }
}
