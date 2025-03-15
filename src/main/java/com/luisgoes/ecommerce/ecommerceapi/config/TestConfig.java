package com.luisgoes.ecommerce.ecommerceapi.config;

import com.github.javafaker.Faker;
import com.luisgoes.ecommerce.ecommerceapi.entities.Order;
import com.luisgoes.ecommerce.ecommerceapi.entities.User;
import com.luisgoes.ecommerce.ecommerceapi.repositories.OrderRepository;
import com.luisgoes.ecommerce.ecommerceapi.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@Profile(value = "test")
public class TestConfig implements CommandLineRunner {
    private final static int QUANTITY_OF_GENERATION = 10;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) {
        var userList = createRandomUsers();
        userRepository.saveAll(userList);

        var orderList = createRandomOrders();
        orderRepository.saveAll(orderList);
    }

    private List<User> createRandomUsers() {
        Faker faker = new Faker();
        List<User> userList = new ArrayList<>();

        List<String> emailDomains = Arrays.asList("gmail.com", "hotmail.com", "yahoo.com", "outlook.com", "live.com");

        for (int i = 0; i < QUANTITY_OF_GENERATION; i++) {
            String name = faker.name().fullName();

            String domain = emailDomains.get(faker.number().numberBetween(0, emailDomains.size()));

            String email = name.toLowerCase().replaceAll("[^a-zA-Z0-9]", ".") + "@" + domain;

            String areaCode = String.format("%02d", faker.number().numberBetween(11, 99));
            String phone = String.format("(%s) 9%s-%s", areaCode,
                    faker.number().digits(4),
                    faker.number().digits(4));

            String password = faker.internet().password(8, 16);

            User user = new User(name, email, phone, password);
            userList.add(user);
        }

        return userList;
    }

    private List<Order> createRandomOrders() {
        List<Order> orderList = new ArrayList<>();

        List<User> savedUsers = userRepository.findAll();

        if (savedUsers.isEmpty()) {
            throw new EntityNotFoundException("Não foi possível achar usuários no banco de dados.");
        }

        Instant start = Instant.parse("2023-01-01T00:00:00Z");
        Instant end = Instant.now();

        for (User client : savedUsers) {
            long randomEpochSecond = ThreadLocalRandom.current()
                    .nextLong(start.getEpochSecond(), end.getEpochSecond());

            Instant moment = Instant.ofEpochSecond(randomEpochSecond);

            Order order = new Order(moment, client);
            orderList.add(order);
        }

        return orderList;
    }


}