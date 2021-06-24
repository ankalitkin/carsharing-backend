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
import ru.vsu.cs.carsharing.converter.CustomerMapper;
import ru.vsu.cs.carsharing.converter.admin.CarAdminMapper;
import ru.vsu.cs.carsharing.dto.CustomerDto;
import ru.vsu.cs.carsharing.dto.admin.FullCarDto;
import ru.vsu.cs.carsharing.entity.Car;
import ru.vsu.cs.carsharing.entity.Customer;
import ru.vsu.cs.carsharing.exception.WebException;
import ru.vsu.cs.carsharing.service.admin.CustomerAdminService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/customer")
@PreAuthorize("hasAuthority('Employee')")
@RequiredArgsConstructor

public class CustomerAdminController {
    private final CustomerAdminService service;

    @GetMapping("/{id}")
    public CustomerDto getById(@PathVariable int id) {
        Customer entity = service.findById(id)
                .orElseThrow(() -> new WebException("Customer not found", HttpStatus.NOT_FOUND));
        return CustomerMapper.INSTANCE.toDto(entity);
    }

    @GetMapping("/")
    public List<CustomerDto> getAll() {
        List<Customer> entities = service.getAllCustomers();
        return CustomerMapper.INSTANCE.toDtoList(entities);
    }

    @GetMapping("/desc")
    public Map<Integer, String> getShortDescriptions() {
        Map<Integer, String> descriptions = new LinkedHashMap<>();
        service.getAllCustomers().forEach(e -> descriptions.put(e.getId(), e.toString()));
        return descriptions;
    }

    @PostMapping("/")
    public CustomerDto add(@RequestBody CustomerDto dto) {
        if (dto == null) {
            throw new WebException("Entity cannot be null", HttpStatus.BAD_REQUEST);
        }
        Customer entity = CustomerMapper.INSTANCE.toEntity(dto);
        entity = service.updateOrAddNew(entity);
        return CustomerMapper.INSTANCE.toDto(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}

