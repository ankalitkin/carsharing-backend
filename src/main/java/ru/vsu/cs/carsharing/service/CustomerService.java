package ru.vsu.cs.carsharing.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.dao.CustomerDao;
import ru.vsu.cs.carsharing.entity.Customer;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerDao customerDao;

    public Customer getOrCreateCustomerByPhoneNumber(String phoneNumber) {
        Optional<Customer> customerOptional = customerDao.getCustomerByPhoneNumber(phoneNumber);
        if (customerOptional.isPresent()) {
            return customerOptional.get();
        } else {
            Customer customer = new Customer();
            customer.setPhoneNumber(phoneNumber);
            return customerDao.save(customer);
        }
    }
}
