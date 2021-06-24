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
import ru.vsu.cs.carsharing.converter.admin.CarAdminMapper;
import ru.vsu.cs.carsharing.dto.admin.FullCarDto;
import ru.vsu.cs.carsharing.entity.Car;
import ru.vsu.cs.carsharing.exception.WebException;
import ru.vsu.cs.carsharing.service.admin.CarAdminService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("admin/car")
@PreAuthorize("hasAuthority('Employee')")
@RequiredArgsConstructor

public class CarAdminController {
    private final CarAdminService service;

    @GetMapping("/{id}")
    public FullCarDto getById(@PathVariable int id) {
        Car entity = service.findById(id)
                .orElseThrow(() -> new WebException("Car not found", HttpStatus.NOT_FOUND));
        return CarAdminMapper.INSTANCE.toDto(entity);
    }

    @GetMapping("/")
    public List<FullCarDto> getAll() {
        List<Car> entities = service.getAllCars();
        return CarAdminMapper.INSTANCE.toDtoList(entities);
    }

    @GetMapping("/desc")
    public Map<Integer, String> getShortDescriptions() {
        Map<Integer, String> descriptions = new LinkedHashMap<>();
        service.getAllCars().forEach(e -> descriptions.put(e.getId(), e.toString()));
        return descriptions;
    }

    @PostMapping("/")
    public FullCarDto add(@RequestBody FullCarDto dto) {
        if (dto == null) {
            throw new WebException("Entity cannot be null", HttpStatus.BAD_REQUEST);
        }
        Car entity = CarAdminMapper.INSTANCE.toEntity(dto);
        entity = service.updateOrAddNew(entity);
        return CarAdminMapper.INSTANCE.toDto(entity);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.delete(id);
    }
}

