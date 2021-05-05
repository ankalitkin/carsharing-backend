package ru.vsu.cs.carsharing.service.external;

public interface SMSProvider {
    void sendSMS(String phoneNumber, String text);
}
