package com.luisgoes.ecommerce.ecommerceapi.config;

import com.github.javafaker.Faker;
import com.luisgoes.ecommerce.ecommerceapi.entities.Order;
import com.luisgoes.ecommerce.ecommerceapi.entities.User;
import com.luisgoes.ecommerce.ecommerceapi.repositories.OrderRepository;
import com.luisgoes.ecommerce.ecommerceapi.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private static final int TOTAL_USERS = 10;
    private static final List<String> EMAIL_DOMAINS = List.of("gmail.com", "hotmail.com", "yahoo.com", "outlook.com", "live.com");

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final Faker faker;

    public TestConfig(UserRepository userRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) {
        List<User> users = createRandomUsers();
        userRepository.saveAll(users);

        List<Order> orders = createRandomOrders(users);
        orderRepository.saveAll(orders);
    }

    private List<User> createRandomUsers() {
        return IntStream.range(0, TOTAL_USERS)
                .mapToObj(i -> {
                    String fullName = faker.name().fullName();
                    return new User(
                            fullName,
                            generateRandomEmail(fullName),
                            generateRandomPhone(),
                            faker.internet().password(8, 16)
                    );
                })
                .collect(Collectors.toList());
    }

    private String generateRandomEmail(String fullName) {
        String namePart = fullName.toLowerCase()
                .replaceAll("[^a-zA-Z0-9]", ".")
                .replaceAll("\\.+", ".");

        String domain = EMAIL_DOMAINS.get(faker.random().nextInt(EMAIL_DOMAINS.size()));

        return namePart + "." + "@" + domain;
    }

    private String generateRandomPhone() {
        String areaCode = String.format("%02d", faker.number().numberBetween(11, 99));
        return String.format("(%s) 9%s-%s", areaCode, faker.number().digits(4), faker.number().digits(4));
    }

    private List<Order> createRandomOrders(List<User> users) {
        if (users.isEmpty()) {
            throw new EntityNotFoundException("Não foi possível achar usuários no banco de dados.");
        }

        return users.stream()
                .map(user -> new Order(generateRandomInstant(), user))
                .collect(Collectors.toList());
    }

    private Instant generateRandomInstant() {
        long startEpoch = Instant.parse("2023-01-01T00:00:00Z").getEpochSecond();
        long endEpoch = Instant.now().getEpochSecond();
        return Instant.ofEpochSecond(ThreadLocalRandom.current().nextLong(startEpoch, endEpoch));
    }
}
