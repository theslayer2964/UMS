package vn.molu.repository.admin;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import vn.molu.domain.admin.Program;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {

    @Query(value = "select * from program order by program_id asc", nativeQuery = true)
    List<Program> findAllWithSort();
}
