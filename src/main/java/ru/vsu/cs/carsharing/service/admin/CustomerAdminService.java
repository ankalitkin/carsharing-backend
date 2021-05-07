package ru.vsu.cs.carsharing.service.admin;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.dao.CustomerDao;
import ru.vsu.cs.carsharing.entity.Customer;
import ru.vsu.cs.carsharing.exception.WebException;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerAdminService {
    private final CustomerDao dao;

    public List<Customer> getAllCustomers() {
        return dao.findAllByOrderById();
    }

    public Optional<Customer> findById(int id) {
        return dao.findById(id);
    }

    public Customer updateOrAddNew(Customer entity) {
        if (entity.getId() != null && !dao.existsById(entity.getId())) {
            throw new WebException("Customer not found", HttpStatus.NOT_FOUND);
        }
        return dao.save(entity);
    }

    public void delete(int id) {
        if (!dao.existsById(id)) {
            throw new WebException("Customer not found", HttpStatus.NOT_FOUND);
        }
        dao.deleteById(id);
    }
}

