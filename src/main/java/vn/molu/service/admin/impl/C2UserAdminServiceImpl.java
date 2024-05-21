package vn.molu.service.admin.impl;

import org.apache.commons.collections.ComparatorUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.dao.temp.C2UserAdminDAO;
import vn.molu.dto.admin.admin.C2UserAdminDTO;
import vn.molu.service.ResponseContainer;
import vn.molu.service.admin.C2UserAdminService;
import vn.molu.service.request.Column;
import vn.molu.service.request.OrderCol;
import vn.molu.service.request.ServerSideRequest;

import java.text.Collator;
import java.text.Normalizer;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class C2UserAdminServiceImpl implements C2UserAdminService {
    @Autowired
    private C2UserAdminDAO c2UserAdminDAO;
    @Override
    public ResponseContainer getListing(ServerSideRequest ssr) {
        int grandTotal = c2UserAdminDAO.count();
        List<C2UserAdminDTO> all;
        List<C2UserAdminDTO> filtered;
        List<C2UserAdminDTO> data;
        //th1: all
        if (ssr.getSearch().getValue().toString().trim().equals("") && ssr.getSearchName() == null &&
                ssr.getSearchGroup() == null && ssr.getSearchStatus() == null) {
            all = c2UserAdminDAO.findU2Limit(ssr);
            data = all.stream()
                    .sorted(buildSorter(ssr))
                    .collect(Collectors.toList());
            return new ResponseContainer(ssr.getDraw(), grandTotal, grandTotal, data);
        }
        else if(!ssr.getSearch().getValue().toString().trim().equals("")){ // th2: search cham
            System.out.println("CHAM`:" + ssr.getSearch());
            all = c2UserAdminDAO.findAll();
            filtered = all.stream()
                    .filter(containsText(normalize(ssr.getSearch().getValue().toLowerCase())))
                    .collect(Collectors.toList());
            data = filtered.stream()
                    .sorted(buildSorter(ssr))
                    .skip(ssr.getStart())
                    .limit(ssr.getLength())
                    .collect(Collectors.toList());
            return new ResponseContainer(ssr.getDraw(), grandTotal, filtered.size(), data);
        }
        else { // th3: filter
            Map<String, Object> prop = new HashMap<>();
            if (StringUtils.isNotBlank(ssr.getSearchName())) {
                prop.put("user_name", ssr.getSearchName().trim().toUpperCase());
            }
            if (StringUtils.isNotBlank(ssr.getSearchStatus())) {
                if (!ssr.getSearchStatus().equals("-1")) {
                    prop.put("status", ssr.getSearchStatus());
                }
            }
            if (ssr.getSearchGroup() != null) {
                if (!ssr.getSearchGroup().equals("-1"))
                    prop.put("shop_code", ssr.getSearchGroup());
            }
            if (prop.size() == 0)
                all = c2UserAdminDAO.findAll();
            else {
                System.out.println("CHAY:" + ssr);
                all = c2UserAdminDAO.findByPropertySS(prop, ssr);
            }
                int size = c2UserAdminDAO.countFindByPropertySS(prop);
            data = all.stream()
                    .sorted(buildSorter(ssr))
                    .collect(Collectors.toList());
            return new ResponseContainer(ssr.getDraw(), grandTotal, size, data);
        }
    }

    @Override
    public C2UserAdminDTO findbyUsername(String userName) {
        return c2UserAdminDAO.findByUsername(userName);
    }

    @Override
    public List<C2UserAdminDTO> layDS_ViPham_TanSuat_TraCuu_10Ngay_DeGuiMail() {
        return c2UserAdminDAO.layDS_ViPham_TanSuat_TraCuu_10Ngay_DeGuiMail();
    }

    private Predicate<C2UserAdminDTO> containsText(String searchTemp) {
        return e -> (e.getUserId() != null && normalize(String.valueOf(e.getUserId()).toLowerCase()).contains(searchTemp))
                || (e.getUsername() != null && normalize(e.getUsername().toLowerCase()).contains(searchTemp))
                || (e.getStatus() != null && normalize(e.getStatus().toLowerCase()).contains(searchTemp))
                || (e.getDescription() != null && normalize(e.getDescription().toLowerCase()).contains(searchTemp))
                || (e.getGranted_ip() != null && normalize(e.getGranted_ip().toLowerCase()).contains(searchTemp))
                || (e.getCenter_code() != null && normalize(e.getCenter_code().toLowerCase()).contains(searchTemp))
                || (e.getFullName() != null && normalize(e.getFullName().toLowerCase()).contains(searchTemp))
                || (e.getShopCode() != null && normalize(e.getShopCode().toLowerCase()).contains(searchTemp));
    }

    private String normalize(String input) {
        if (input == null) {
            return null;
        }
        return Normalizer.normalize(input, Normalizer.Form.NFD)
                .replaceAll("\\p{Mn}", "");
    }

    private Comparator<C2UserAdminDTO> buildSorter(ServerSideRequest ssr) {
        List<Comparator<C2UserAdminDTO>> sortChain = new ArrayList<>();
        for (OrderCol orderCol : ssr.getOrder()) {
            sortChain.add(buildComparatorClause(orderCol, ssr.getColumns(), getCollator()));
        }

        return ComparatorUtils.chainedComparator(sortChain);
    }

    private Comparator<C2UserAdminDTO> buildComparatorClause(OrderCol orderCol, List<Column> columns, Collator collator) {
        Comparator<C2UserAdminDTO> comp = Comparator.comparing(C2UserAdminDTO::getUsername, collator);

        String colName = columns.get(orderCol.getColumn()).getData();
        switch (colName) {
            case "user_id":
                comp = Comparator.comparing(C2UserAdminDTO::getUserId, collator);
                break;
            case "user_name":
                comp = Comparator.comparing(C2UserAdminDTO::getUsername, collator);
                break;
            case "full_name":
                comp = Comparator.comparing(C2UserAdminDTO::getFullName, collator);
                break;
            case "status":
                comp = Comparator.comparing(C2UserAdminDTO::getStatus, collator);
                break;
            case "description":
                comp = Comparator.comparing(C2UserAdminDTO::getDescription, collator);
                break;
            case "shop_code":
                comp = Comparator.comparing(C2UserAdminDTO::getShopCode, collator);
                break;
            case "center_code":
                comp = Comparator.comparing(C2UserAdminDTO::getCenter_code, collator);
                break;
            case "granted_ip":
                comp = Comparator.comparing(C2UserAdminDTO::getGranted_ip, collator);
                break;
        }
        return orderCol.getDir().equals("asc") ? comp : comp.reversed();
    }

    private Collator getCollator() {
        Collator collator = Collator.getInstance(Locale.US);
        collator.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
        return collator;
    }
}
