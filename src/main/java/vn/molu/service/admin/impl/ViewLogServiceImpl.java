package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.dao.GenericDAOImpl;
import vn.molu.dao.temp.ViewLogDAO;
import vn.molu.dto.admin.admin.ViewLogDTO;
import vn.molu.service.admin.ViewLogService;

import java.text.ParseException;
import java.util.List;

@Service
public class ViewLogServiceImpl extends GenericDAOImpl<ViewLogDTO, String> implements ViewLogService {
    @Autowired
    private ViewLogDAO viewLogDAO;
    @Override
    public List<ViewLogDTO> lay_LichSu_TraCuuLog_TheoTen_Thoigian(String username, String from, String to) throws ParseException {
        return viewLogDAO.lay_LichSu_TraCuuLog_TheoTen_Thoigian(username, from, to);
    }
}
