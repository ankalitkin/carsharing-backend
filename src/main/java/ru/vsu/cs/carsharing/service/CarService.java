package ru.vsu.cs.carsharing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.dao.CarDao;
import ru.vsu.cs.carsharing.entity.Car;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CarService {
    private final CarDao dao;

    public List<Car> getAllCars() {
        return dao.findAllByOrderById();
    }

    public Optional<Car> findById(int id) {
        return dao.findById(id);
    }

}

