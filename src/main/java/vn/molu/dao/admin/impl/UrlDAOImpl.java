package vn.molu.dao.admin.impl;

import org.springframework.stereotype.Repository;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.admin.UrlDAO;
import vn.molu.domain.admin.Url;

@Repository
public class UrlDAOImpl extends GenericDAOImpl<Url, Long> implements UrlDAO {

}
