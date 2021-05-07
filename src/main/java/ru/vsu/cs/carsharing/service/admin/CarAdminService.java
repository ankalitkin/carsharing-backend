package ru.vsu.cs.carsharing.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.dao.CarDao;
import ru.vsu.cs.carsharing.entity.Car;
import ru.vsu.cs.carsharing.exception.WebException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarAdminService {
    private final CarDao dao;

    public List<Car> getAllCars() {
        return dao.findAllByOrderById();
    }

    public Optional<Car> findById(int id) {
        return dao.findById(id);
    }

    public Car updateOrAddNew(Car entity) {
        if (entity.getId() != null && !dao.existsById(entity.getId())) {
            throw new WebException("Car not found", HttpStatus.NOT_FOUND);
        }
        return dao.save(entity);
    }

    public void delete(int id) {
        if (!dao.existsById(id)) {
            throw new WebException("Car not found", HttpStatus.NOT_FOUND);
        }
        dao.deleteById(id);
    }
}

