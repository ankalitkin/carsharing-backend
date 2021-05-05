package ru.vsu.cs.carsharing.dao;

import org.springframework.data.repository.CrudRepository;
import ru.vsu.cs.carsharing.entity.Customer;

import java.util.Optional;

public interface CustomerDao extends CrudRepository<Customer, Integer> {
    Optional<Customer> getCustomerByPhoneNumber(String phoneNumber);
}
