package ru.vsu.cs.carsharing.dao;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.carsharing.entity.Customer;
import ru.vsu.cs.carsharing.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface CustomerDao extends CrudRepository<Customer, Integer> {
    List<Customer> findAllByOrderById();

    Optional<Customer> getCustomerByPhoneNumber(String phoneNumber);
}
