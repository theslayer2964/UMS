package vn.molu.service.admin.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.molu.domain.admin.Program;
import vn.molu.repository.admin.ProgramRepository;
import vn.molu.service.admin.ProgramService;

import java.util.List;
import java.util.logging.Logger;

@Service
public class ProgramServiceImpl implements ProgramService {
    private final Logger log = Logger.getLogger(getClass().toString());

    @Autowired
    private ProgramRepository programRepository;

    @Override
    public List<Program> findAll() {
        log.info("Find all program: ");
        try {
            return programRepository.findAllWithSort();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Program findById(Long id) {
        log.info("Find with program: " + id);
        try {
            return programRepository.findById(id).get();
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void delete(Long id) {
        log.info("Delete with id: " + id);
        try {
            programRepository.deleteById(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public Program save(Program program) {
        log.info("save a program");
        try {
            return programRepository.save(program);
        } catch (Exception e) {
            throw e;
        }
    }
}
