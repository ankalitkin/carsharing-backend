package ru.vsu.cs.carsharing.service.external;

import org.springframework.http.HttpStatus;
import ru.vsu.cs.carsharing.exception.WebException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SMSUtil {
    public static final String PHONE_NUMBER_IGNORED_CHARS_REGEX = "[ ()-]";
    public static final Pattern PHONE_NUMBER_PATTERN = Pattern.compile("(8|\\+7)(\\d{10})");

    public static String processPhoneNumber(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll(PHONE_NUMBER_IGNORED_CHARS_REGEX, "");
        Matcher matcher = PHONE_NUMBER_PATTERN.matcher(phoneNumber);
        if (!matcher.find()) {
            throw new WebException("Wrong phone number", HttpStatus.BAD_REQUEST);
        }
        return matcher.group(2);
    }
}
