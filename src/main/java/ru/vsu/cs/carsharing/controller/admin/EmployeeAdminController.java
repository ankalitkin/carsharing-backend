package ru.vsu.cs.carsharing.controller.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.carsharing.converter.admin.EmployeeAdminMapper;
import ru.vsu.cs.carsharing.dto.admin.FullEmployeeDto;
import ru.vsu.cs.carsharing.entity.Employee;
import ru.vsu.cs.carsharing.exception.WebException;
import ru.vsu.cs.carsharing.service.admin.EmployeeAdminService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/employee")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('Admin')")
public class EmployeeAdminController {
    private final EmployeeAdminService service;

    @GetMapping("/{id}")
    public FullEmployeeDto getById(@PathVariable int id) {
        Employee entity = service.findById(id)
                .orElseThrow(() -> new WebException("Employee not found", HttpStatus.NOT_FOUND));
        return EmployeeAdminMapper.INSTANCE.toDto(entity);
    }

    @GetMapping("/")
    public List<FullEmployeeDto> getAll() {
        List<Employee> entities = service.getAllEmployees();
        return EmployeeAdminMapper.INSTANCE.toDtoList(entities);
    }

    @GetMapping("/desc")
    public Map<Integer, String> getShortDescriptions() {
        Map<Integer, String> descriptions = new LinkedHashMap<>();
        service.getAllEmployees().forEach(e -> descriptions.put(e.getId(), e.toString()));
        return descriptions;
    }

    @PostMapping("/")
    public FullEmployeeDto add(@RequestBody FullEmployeeDto dto) {
        if (dto == null) {
            throw new WebException("Entity cannot be null", HttpStatus.BAD_REQUEST);
        }
        Employee entity = service.updateOrAddNew(dto);
        return EmployeeAdminMapper.INSTANCE.toDto(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}

