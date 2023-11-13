package ru.netology.data;

import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class DataHelper {
    public static Faker faker = new Faker();

    @Value
    public static class AuthInfo {
        private String cardNumber;
        private String month;
        private String year;
        private String owner;
        private String cvc;
    }

    public static String getApprovedCardNumber(){
        return ("1111 2222 3333 4444");
    }

    public static String getDeclinedCardNumber(){
        return ("5555 6666 7777 8888");
    }

    public static String getInvalidCardNumber(){
        return ("0000 0000 0000 0000");
    }

    public static String generateCardNumberFifteenDigit(){
        return faker.numerify("#### #### #### ###");
    }

    public static String generateMonth(int target) {
        String month = LocalDate.now().plusMonths(target).format(DateTimeFormatter.ofPattern("MM"));
        return month;
    }

    public static String generateYear(int target) {
        String year = LocalDate.now().plusYears(target).format(DateTimeFormatter.ofPattern("yy"));
        return year;
    }

    public static String generateOwner(String language) {
        Faker faker = new Faker(new Locale(language));
        String owner = faker.name().lastName() + " " + faker.name().firstName();
        return  owner;
    }

    public static String generateOwnerUsingOnlyFirstName(String language) {
        Faker faker = new Faker(new Locale(language));
        String owner = faker.name().firstName();
        return  owner;
    }

    public static String generateOwnerOfNumbers() {
        String owner = faker.numerify("###########");
        return owner;
    }

    public static String generateOwnerWithSpecialSymbols(String language) {
        Faker faker = new Faker(new Locale(language));
        String owner = faker.name().firstName()+"@#$%*";
        return owner;
    }

    public static String generateCVC() {
        String cvc = faker.numerify("###");
        return cvc;
    }

    public static String generateOneDigit(){
        return faker.numerify("#");
    }

    public static String generateTwoDigits(){
        return faker.numerify("##");
    }

    public static String returnNull(){
        return null;
    }

}
