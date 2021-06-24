package ru.vsu.cs.carsharing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vsu.cs.carsharing.converter.CarMapper;
import ru.vsu.cs.carsharing.dto.CarDto;
import ru.vsu.cs.carsharing.dto.FileInfoDto;
import ru.vsu.cs.carsharing.entity.Car;
import ru.vsu.cs.carsharing.service.CarService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/car/")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;
    private final FileUploadController fileUploadController;

    @GetMapping("")
    public List<CarDto> getAllCars() {
        List<Car> cars = carService.getAllCars();
        List<CarDto> carDtos = CarMapper.INSTANCE.toDtoList(cars);
        carDtos.forEach(carDto -> {
            List<String> list = fileUploadController.getAllCarFilesFromFolder(Integer.toString(carDto.getId())).stream()
                    .map(FileInfoDto::getName).collect(Collectors.toList());
            carDto.setPhotoFilenames(list);
        });
        return carDtos;
    }
}
