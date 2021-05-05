package ru.vsu.cs.carsharing.service.external;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

@Service
@Slf4j
public class SMSProviderImpl implements SMSProvider {

    public static final String SMS_RU_API_ID = System.getenv("SMS_RU_API_ID");
    public static final String API_URL = "https://sms.ru/sms/send?api_id={api_id}&to={to}&msg={msg}&json=1";

    @Override
    public void sendSMS(String phoneNumber, String text) {
        System.out.printf("Новое сообщение для %s: %s%n", phoneNumber, text);

        Map<String, String> params = new LinkedHashMap<>();
        params.put("api_id", SMS_RU_API_ID);
        params.put("to", "7" + phoneNumber);
        params.put("msg", text);
        String data = new RestTemplate().getForEntity(API_URL, String.class, params).getBody();
        log.debug(data);
    }
}
