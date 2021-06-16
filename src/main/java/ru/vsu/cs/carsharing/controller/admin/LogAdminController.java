package ru.vsu.cs.carsharing.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.carsharing.converter.LogMapper;
import ru.vsu.cs.carsharing.dto.LogDto;
import ru.vsu.cs.carsharing.entity.Log;
import ru.vsu.cs.carsharing.exception.WebException;
import ru.vsu.cs.carsharing.service.admin.LogAdminService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/log")
@RequiredArgsConstructor

public class LogAdminController {
    private final LogAdminService service;

    @GetMapping("/{id}")
    public LogDto getById(@PathVariable int id) {
        Log entity = service.findById(id)
                .orElseThrow(() -> new WebException("Log not found", HttpStatus.NOT_FOUND));
        return LogMapper.INSTANCE.toDto(entity);
    }

    @GetMapping("/")
    public List<LogDto> getAll() {
        List<Log> entities = service.getAllLogs();
        return LogMapper.INSTANCE.toDtoList(entities);
    }

    @GetMapping("/desc")
    public Map<Integer, String> getShortDescriptions() {
        Map<Integer, String> descriptions = new LinkedHashMap<>();
        service.getAllLogs().forEach(e -> descriptions.put(e.getId(), e.toString()));
        return descriptions;
    }

    @PostMapping("/")
    public LogDto add(@RequestBody LogDto dto) {
        if (dto == null) {
            throw new WebException("Entity cannot be null", HttpStatus.BAD_REQUEST);
        }
        Log entity = LogMapper.INSTANCE.toEntity(dto);
        entity = service.updateOrAddNew(entity);
        return LogMapper.INSTANCE.toDto(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}

