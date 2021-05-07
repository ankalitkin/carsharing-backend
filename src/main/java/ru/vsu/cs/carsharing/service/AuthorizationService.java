package ru.vsu.cs.carsharing.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.converter.CustomerMapper;
import ru.vsu.cs.carsharing.converter.EmployeeMapper;
import ru.vsu.cs.carsharing.dao.EmployeeDao;
import ru.vsu.cs.carsharing.dto.AuthorizedCustomerDto;
import ru.vsu.cs.carsharing.dto.AuthorizedEmployeeDto;
import ru.vsu.cs.carsharing.dto.CustomerDto;
import ru.vsu.cs.carsharing.dto.EmployeeDto;
import ru.vsu.cs.carsharing.entity.Customer;
import ru.vsu.cs.carsharing.entity.Employee;
import ru.vsu.cs.carsharing.exception.WebException;
import ru.vsu.cs.carsharing.security.JwtTokenProvider;
import ru.vsu.cs.carsharing.security.Keychain;
import ru.vsu.cs.carsharing.service.external.SMSAuthService;
import ru.vsu.cs.carsharing.service.external.SMSUtil;

import java.util.Objects;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthorizationService {
    public static final String EMPLOYEE = "Employee";
    public static final String CUSTOMER = "Customer";
    private final JwtTokenProvider jwtTokenProvider;
    private final SMSAuthService smsAuthService;
    private final Keychain keychain;
    private final EmployeeDao employeeDao;
    private final CustomerService customerService;

    public AuthorizedEmployeeDto authorizeEmployee(String login, String password) {
        try {
            Employee authorizedEmployee = authUser(login, password);
            EmployeeDto dto = EmployeeMapper.INSTANCE.toDto(authorizedEmployee);
            int userId = authorizedEmployee.getId();
            String token = jwtTokenProvider.createToken(String.format("%d %s", userId, login), EMPLOYEE);
            Set<String> authorities = keychain.getEmployeeAuthoritiesStrings(login, userId);
            return new AuthorizedEmployeeDto(dto, JwtTokenProvider.BEARER + token, authorities);
        } catch (Exception e) {
            log.error("Error during authorization", e);
            throw e;
        }
    }

    private Employee authUser(String login, String password) {
        if (!employeeDao.existsByLoginAndDeleted(login, false)) {
            throw new WebException("Login doesn't exist", HttpStatus.UNAUTHORIZED);
        }
        Employee employee = employeeDao.getByLogin(login);
        if (!Objects.equals(HashUtills.hashPassword(password, employee.getSalt()), employee.getPassword())) {
            throw new WebException("Wrong password", HttpStatus.FORBIDDEN);
        }
        return employee;
    }

    public void sendCustomerSMS(String phoneNumber) {
        phoneNumber = SMSUtil.processPhoneNumber(phoneNumber);
        smsAuthService.generateAndSendSMS(phoneNumber);
    }

    public AuthorizedCustomerDto authorizeCustomer(String phoneNumber, String code) {
        try {
            phoneNumber = SMSUtil.processPhoneNumber(phoneNumber);
            if (!smsAuthService.validateCode(phoneNumber, code)) {
                throw new WebException("Wrong code", HttpStatus.FORBIDDEN);
            }
            Customer customer = customerService.getOrCreateCustomerByPhoneNumber(phoneNumber);
            CustomerDto dto = CustomerMapper.INSTANCE.toDto(customer);
            String token = jwtTokenProvider.createToken(String.valueOf(customer.getId()), CUSTOMER);
            return new AuthorizedCustomerDto(dto, JwtTokenProvider.BEARER + token);
        } catch (Exception e) {
            log.error("Error during authorization", e);
            throw e;
        }
    }
}
