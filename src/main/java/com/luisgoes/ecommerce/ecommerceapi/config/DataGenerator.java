package com.luisgoes.ecommerce.ecommerceapi.config;

import com.github.javafaker.Faker;
import com.luisgoes.ecommerce.ecommerceapi.entities.*;
import com.luisgoes.ecommerce.ecommerceapi.entities.enums.OrderStatus;
import com.luisgoes.ecommerce.ecommerceapi.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class DataGenerator {

    private static final List<String> EMAIL_DOMAINS = List.of("gmail.com", "hotmail.com", "yahoo.com", "outlook.com", "live.com");
    private final Faker faker;
    private final CategoryRepository categoryRepository;

    public DataGenerator(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.faker = new Faker();
    }

    public List<User> generateUsers() {
        return IntStream.range(0, 10)
                .mapToObj(i -> {
                    String fullName = faker.name().fullName();
                    return new User(fullName, generateEmail(fullName), generatePhoneNumber(), faker.internet().password(8, 12));
                })
                .toList();
    }

    public List<Order> generateOrders(List<User> users) {
        return users.stream()
                .map(user -> new Order(randomInstant(), randomOrderStatus(), user))
                .toList();
    }

    public List<Category> generateCategories() {
        return List.of(
                new Category("Books"),
                new Category("Electronics"),
                new Category("Clothing"),
                new Category("Home & Kitchen"),
                new Category("Toys"),
                new Category("Sports & Outdoors")
        );
    }

    public List<Product> generateProducts() {
        List<Category> categories = categoryRepository.findAll();
        if (categories.isEmpty()) {
            throw new EntityNotFoundException("Nenhuma categoria encontrada.");
        }

        return List.of(
                createProduct("Java Programming", "A comprehensive guide to Java.", new BigDecimal("49.99"), "book-image.jpg", categories.get(0)),
                createProduct("Mystery Novel", "Thrilling mystery novel.", new BigDecimal("15.99"), "novel-image.jpg", categories.get(0)),
                createProduct("Laptop", "High performance laptop.", new BigDecimal("999.99"), "laptop-image.jpg", categories.get(1)),
                createProduct("Smartphone", "Latest model smartphone.", new BigDecimal("799.99"), "smartphone-image.jpg", categories.get(1)),
                createProduct("Wireless Headphones", "Noise-canceling wireless headphones.", new BigDecimal("199.99"), "headphones-image.jpg", categories.get(1))
        );
    }

    public List<OrderItem> generateOrderItems(List<Order> orders, List<Product> products) {
        return orders.stream()
                .flatMap(order -> products.stream()
                        .limit(ThreadLocalRandom.current().nextInt(1, 4))
                        .map(product -> createOrderItem(order, product)))
                .collect(Collectors.toList());
    }

    private Product createProduct(String name, String description, BigDecimal price, String imgUrl, Category... categories) {
        Product product = new Product(name, description, price, imgUrl);
        product.getCategories().addAll(List.of(categories));
        return product;
    }

    private OrderItem createOrderItem(Order order, Product product) {
        int quantity = ThreadLocalRandom.current().nextInt(1, 6);
        return new OrderItem(order, product, quantity, product.getPrice());
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

    public Instant randomInstantAfter(Instant after) {
        long afterEpoch = after.getEpochSecond();
        long endEpoch = Instant.now().getEpochSecond();
        return Instant.ofEpochSecond(ThreadLocalRandom.current().nextLong(afterEpoch, endEpoch));
    }

    private OrderStatus randomOrderStatus() {
        return OrderStatus.values()[ThreadLocalRandom.current().nextInt(OrderStatus.values().length)];
    }

}