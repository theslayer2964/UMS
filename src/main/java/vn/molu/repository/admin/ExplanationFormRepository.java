package vn.molu.repository.admin;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import vn.molu.domain.admin.ExplanationForm;

import javax.transaction.Transactional;
import java.util.List;

public interface ExplanationFormRepository extends JpaRepository<ExplanationForm, Long> {

    @Query(value = "select * from EXPLANATION_FORM where file_name = :file_name", nativeQuery = true)
    ExplanationForm findByFile_name(String file_name);

    @Query(value = "select * from EXPLANATION_FORM where user_name = :user_name order by update_date desc ", nativeQuery = true)
    List<ExplanationForm> findExplanationFormByUser_name(String user_name);

    @Query(value = "select distinct user_name from EXPLANATION_FORM where form_type = :form_type and status = :status", nativeQuery = true)
    List<String> findExplanationFormByStatus(String form_type, Integer status);

    @Query(value = "select * from EXPLANATION_FORM where user_name = :username and form_type = :status order by update_date desc ", nativeQuery = true)
    List<ExplanationForm> findExplanationFormByUser_nameNFormtype(String username, Integer status);

    @Modifying
    @Transactional
    @Query(value = "update EXPLANATION_FORM set status = :status where id = :id", nativeQuery = true)
    int updateFormStatusByid(Long id, Integer status);
}
