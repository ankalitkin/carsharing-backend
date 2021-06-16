package ru.vsu.cs.carsharing.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.dao.LogDao;
import ru.vsu.cs.carsharing.entity.Log;
import ru.vsu.cs.carsharing.exception.WebException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LogAdminService {
    private final LogDao dao;

    public List<Log> getAllLogs() {
        return dao.findAllByOrderById();
    }

    public Optional<Log> findById(int id) {
        return dao.findById(id);
    }

    public Log updateOrAddNew(Log entity) {
        if (entity.getId() != null && !dao.existsById(entity.getId())) {
            throw new WebException("Log not found", HttpStatus.NOT_FOUND);
        }
        return dao.save(entity);
    }

    public void delete(int id) {
        if (!dao.existsById(id)) {
            throw new WebException("Log not found", HttpStatus.NOT_FOUND);
        }
        dao.deleteById(id);
    }
}

