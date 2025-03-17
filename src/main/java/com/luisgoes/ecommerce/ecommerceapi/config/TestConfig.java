package com.luisgoes.ecommerce.ecommerceapi.config;

import com.github.javafaker.Faker;
import com.luisgoes.ecommerce.ecommerceapi.entities.Category;
import com.luisgoes.ecommerce.ecommerceapi.entities.Order;
import com.luisgoes.ecommerce.ecommerceapi.entities.Product;
import com.luisgoes.ecommerce.ecommerceapi.entities.User;
import com.luisgoes.ecommerce.ecommerceapi.entities.enums.OrderStatus;
import com.luisgoes.ecommerce.ecommerceapi.repositories.CategoryRepository;
import com.luisgoes.ecommerce.ecommerceapi.repositories.OrderRepository;
import com.luisgoes.ecommerce.ecommerceapi.repositories.ProductRepository;
import com.luisgoes.ecommerce.ecommerceapi.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private static final int TOTAL_USERS = 10;
    private static final List<String> EMAIL_DOMAINS = List.of("gmail.com", "hotmail.com", "yahoo.com", "outlook.com", "live.com");

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final Faker faker;

    public TestConfig(UserRepository userRepository, OrderRepository orderRepository,
                      CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) {
        List<User> users = generateUsers();
        userRepository.saveAll(users);

        orderRepository.saveAll(generateOrders(users));
        categoryRepository.saveAll(generateCategories());
        productRepository.saveAll(generateProducts());
    }

    private List<User> generateUsers() {
        return IntStream.range(0, TOTAL_USERS)
                .mapToObj(i -> {
                    String fullName = faker.name().fullName();
                    return new User(fullName, generateEmail(fullName), generatePhoneNumber(), faker.internet().password(8, 12));
                })
                .toList();
    }

    private List<Order> generateOrders(List<User> users) {
        if (users.isEmpty()) {
            throw new EntityNotFoundException("Nenhum usuário encontrado para gerar pedidos.");
        }

        return users.stream()
                .map(user -> new Order(randomInstant(), randomOrderStatus(), user))
                .toList();
    }

    private List<Category> generateCategories() {
        return List.of(
                new Category("Books"),
                new Category("Electronics"),
                new Category("Clothing"),
                new Category("Home & Kitchen"),
                new Category("Toys"),
                new Category("Sports & Outdoors")
        );
    }

    private List<Product> generateProducts() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma categoria encontrada.");
        }

        return List.of(
                createProduct("Java Programming", "A comprehensive guide to Java.", new BigDecimal("49.99"), "book-image.jpg", categories.get(0)),
                createProduct("Mystery Novel", "Thrilling mystery novel.", new BigDecimal("15.99"), "novel-image.jpg", categories.get(0)),
                createProduct("Laptop", "High performance laptop.", new BigDecimal("999.99"), "laptop-image.jpg", categories.get(1)),
                createProduct("Smartphone", "Latest model smartphone.", new BigDecimal("799.99"), "smartphone-image.jpg", categories.get(1)),
                createProduct("Wireless Headphones", "Noise-canceling wireless headphones.", new BigDecimal("199.99"), "headphones-image.jpg", categories.get(1)),
                createProduct("Graphic T-Shirt", "Tech design t-shirt.", new BigDecimal("19.99"), "tshirt-image.jpg", categories.get(2)),
                createProduct("Leather Jacket", "Stylish leather jacket.", new BigDecimal("199.99"), "jacket-image.jpg", categories.get(2)),
                createProduct("Coffee Maker", "Automatic coffee maker.", new BigDecimal("89.99"), "coffee-maker-image.jpg", categories.get(3)),
                createProduct("Blender", "High-speed kitchen blender.", new BigDecimal("69.99"), "blender-image.jpg", categories.get(3)),
                createProduct("Lego Set", "Creative building block set.", new BigDecimal("59.99"), "lego-image.jpg", categories.get(4)),
                createProduct("RC Car", "Remote control car with high speed.", new BigDecimal("79.99"), "rc-car-image.jpg", categories.get(4)),
                createProduct("Tennis Racket", "Professional tennis racket.", new BigDecimal("149.99"), "racket-image.jpg", categories.get(5)),
                createProduct("Camping Tent", "Waterproof camping tent for 4 people.", new BigDecimal("249.99"), "tent-image.jpg", categories.get(5))
        );
    }

    private Product createProduct(String name, String description, BigDecimal price, String imgUrl, Category... categories) {
        Product product = new Product(name, description, price, imgUrl);
        product.getCategories().addAll(List.of(categories));
        return product;
    }

    private String generateEmail(String fullName) {
        String formattedName = fullName.toLowerCase().replaceAll("[^a-zA-Z0-9]", ".");
        String domain = EMAIL_DOMAINS.get(faker.random().nextInt(EMAIL_DOMAINS.size()));
        return formattedName + "@" + domain;
    }

    private String generatePhoneNumber() {
        return String.format("(%02d) 9%d-%d",
                ThreadLocalRandom.current().nextInt(11, 99),
                ThreadLocalRandom.current().nextInt(1000, 9999),
                ThreadLocalRandom.current().nextInt(1000, 9999));
    }

    private Instant randomInstant() {
        long startEpoch = Instant.parse("2023-01-01T00:00:00Z").getEpochSecond();
        long endEpoch = Instant.now().getEpochSecond();
        return Instant.ofEpochSecond(ThreadLocalRandom.current().nextLong(startEpoch, endEpoch));
    }

    private OrderStatus randomOrderStatus() {
        return OrderStatus.values()[ThreadLocalRandom.current().nextInt(OrderStatus.values().length)];
    }

}