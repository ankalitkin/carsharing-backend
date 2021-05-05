package ru.vsu.cs.carsharing.service.external;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import ru.vsu.cs.carsharing.exception.WebException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SMSAuthService {
    public static final long TTL_SECONDS = 300;
    public static final long MAX_SMS = 10;
    public static final int BOUND = 1000000;
    public static final String CODE_MESSAGE_FORMAT = "Ваш код: %s";
    public static final String CODE_FORMAT = "%06d";

    private final SMSProvider smsProvider;
    private final Random random = new Random(System.currentTimeMillis());
    private final List<SentSMS> sentSMS = Collections.synchronizedList(new LinkedList<>());

    public void generateAndSendSMS(String processedNumber) {
        if (!sendingAvailable(processedNumber)) {
            throw new WebException("Too many SMS are already sent", HttpStatus.TOO_MANY_REQUESTS);
        }
        String code = generateCode();
        SentSMS sms = new SentSMS(processedNumber, code, LocalDateTime.now());
        sentSMS.add(sms);

        String message = String.format(CODE_MESSAGE_FORMAT, code);
        smsProvider.sendSMS(processedNumber, message);
    }

    public boolean validateCode(String processedNumber, String code) {
        removeObsoleteCodes();
        Iterator<SentSMS> iterator = sentSMS.iterator();
        while (iterator.hasNext()) {
            SentSMS sms = iterator.next();
            if (sms.getPhoneNumber().equals(processedNumber) && sms.getCode().equals(code)) {
                iterator.remove();
                return true;
            }
        }
        return false;
    }

    private String generateCode() {
        int value = random.nextInt(BOUND);
        return String.format(CODE_FORMAT, value);
    }

    private void removeObsoleteCodes() {
        Iterator<SentSMS> iterator = sentSMS.iterator();
        LocalDateTime deadline = LocalDateTime.now().minusSeconds(TTL_SECONDS);
        while (iterator.hasNext()) {
            SentSMS sms = iterator.next();
            if (sms.getSentAt().isBefore(deadline)) {
                iterator.remove();
            }
        }
    }

    private boolean sendingAvailable(String phoneNumber) {
        removeObsoleteCodes();
        return sentSMS.stream().filter(sms -> sms.getPhoneNumber().equals(phoneNumber)).count() <= MAX_SMS;
    }

}
