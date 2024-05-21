package vn.molu.dao.admin.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.admin.UrlGroupDAO;
import vn.molu.domain.admin.UrlGroup;

@Repository
public class UrlGroupDAOImpl extends GenericDAOImpl<UrlGroup, Long> implements UrlGroupDAO {

}
