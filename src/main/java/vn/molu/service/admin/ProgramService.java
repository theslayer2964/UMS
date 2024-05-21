package vn.molu.service.admin;

import vn.molu.domain.admin.Program;

import java.util.List;

public interface ProgramService {

    List<Program> findAll();

    Program findById(Long id);

    void delete(Long id);

    Program save(Program program);
}
