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
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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

    public TestConfig(UserRepository userRepository, OrderRepository orderRepository, CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.faker = new Faker();
    }

    @Override
    public void run(String... args) {
        // Criar e salvar usuários
        List<User> users = createRandomUsers();
        userRepository.saveAll(users);

        // Criar e salvar pedidos
        List<Order> orders = createRandomOrders(users);
        orderRepository.saveAll(orders);

        // Criar e salvar categorias
        List<Category> categories = createFixedCategories();
        categoryRepository.saveAll(categories);

        // Criar e salvar produtos
        List<Product> products = createFixedProducts(categories);
        productRepository.saveAll(products);
    }

    private List<User> createRandomUsers() {
        return java.util.stream.IntStream.range(0, TOTAL_USERS)
                .mapToObj(i -> {
                    String fullName = faker.name().fullName();
                    return new User(
                            fullName,
                            generateRandomEmail(fullName),
                            generateRandomPhone(),
                            faker.internet().password(8, 12)
                    );
                })
                .collect(Collectors.toList());
    }

    private List<Category> createFixedCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Books"));
        categories.add(new Category("Electronics"));
        categories.add(new Category("Clothing"));
        return categories;
    }

    private List<Product> createFixedProducts(List<Category> categories) {
        List<Product> products = new ArrayList<>();

        Product book = new Product("Java Programming", "A comprehensive guide to Java programming.", new BigDecimal("49.99"), "book-image.jpg");
        book.getCategories().add(categories.get(0)); // Books
        book.getCategories().add(categories.get(1)); // Electronics
        products.add(book);

        Product laptop = new Product("Laptop", "High performance laptop for programming and gaming.", new BigDecimal("999.99"), "laptop-image.jpg");
        laptop.getCategories().add(categories.get(1)); // Electronics
        laptop.getCategories().add(categories.get(2)); // Clothing
        products.add(laptop);

        Product tshirt = new Product("Graphic T-Shirt", "Comfortable graphic t-shirt with tech design.", new BigDecimal("19.99"), "tshirt-image.jpg");
        tshirt.getCategories().add(categories.get(2)); // Clothing
        products.add(tshirt);

        Product smartphone = new Product("Smartphone", "Latest model smartphone with great features.", new BigDecimal("799.99"), "smartphone-image.jpg");
        smartphone.getCategories().add(categories.get(1)); // Electronics
        smartphone.getCategories().add(categories.get(2)); // Clothing
        products.add(smartphone);

        Product novel = new Product("Mystery Novel", "A thrilling mystery novel for all book lovers.", new BigDecimal("15.99"), "novel-image.jpg");
        novel.getCategories().add(categories.get(0)); // Books
        products.add(novel);

        Product jacket = new Product("Leather Jacket", "Stylish leather jacket for all seasons.", new BigDecimal("199.99"), "jacket-image.jpg");
        jacket.getCategories().add(categories.get(2)); // Clothing
        products.add(jacket);

        return products;
    }

    private String generateRandomEmail(String fullName) {
        return fullName.toLowerCase().replaceAll("[^a-zA-Z0-9]", ".") + "@"
                + EMAIL_DOMAINS.get(faker.random().nextInt(EMAIL_DOMAINS.size()));
    }

    private String generateRandomPhone() {
        return String.format("(%02d) 9%s-%s", ThreadLocalRandom.current().nextInt(11, 99),
                ThreadLocalRandom.current().nextInt(1000, 9999), ThreadLocalRandom.current().nextInt(1000, 9999));
    }

    private List<Order> createRandomOrders(List<User> users) {
        if (users.isEmpty()) {
            throw new EntityNotFoundException("Não foi possível achar usuários no banco de dados.");
        }

        return users.stream()
                .map(user -> new Order(generateRandomInstant(), getRandomOrderStatus(), user))
                .collect(Collectors.toList());
    }

    private Instant generateRandomInstant() {
        long startEpoch = Instant.parse("2023-01-01T00:00:00Z").getEpochSecond();
        long endEpoch = Instant.now().getEpochSecond();
        return Instant.ofEpochSecond(ThreadLocalRandom.current().nextLong(startEpoch, endEpoch));
    }

    private OrderStatus getRandomOrderStatus() {
        return OrderStatus.values()[ThreadLocalRandom.current().nextInt(OrderStatus.values().length)];
    }

}