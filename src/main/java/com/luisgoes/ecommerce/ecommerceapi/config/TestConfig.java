package com.luisgoes.ecommerce.ecommerceapi.config;

import com.luisgoes.ecommerce.ecommerceapi.entities.Order;
import com.luisgoes.ecommerce.ecommerceapi.entities.Payment;
import com.luisgoes.ecommerce.ecommerceapi.entities.Product;
import com.luisgoes.ecommerce.ecommerceapi.entities.User;
import com.luisgoes.ecommerce.ecommerceapi.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final OrderItemRepository orderItemRepository;
    private final DataGenerator dataGenerator;

    public TestConfig(UserRepository userRepository, OrderRepository orderRepository,
                      CategoryRepository categoryRepository, ProductRepository productRepository,
                      OrderItemRepository orderItemRepository, DataGenerator dataGenerator) {
        this.userRepository = userRepository;
        this.orderRepository = orderRepository;
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.orderItemRepository = orderItemRepository;
        this.dataGenerator = dataGenerator;
    }

    @Override
    public void run(String... args) {
        List<User> users = dataGenerator.generateUsers();
        userRepository.saveAll(users);

        List<Order> orders = dataGenerator.generateOrders(users);
        orderRepository.saveAll(orders);

        categoryRepository.saveAll(dataGenerator.generateCategories());

        List<Product> products = dataGenerator.generateProducts();
        productRepository.saveAll(products);

        orderItemRepository.saveAll(dataGenerator.generateOrderItems(orders, products));

        for (Order order : orders) {
            Payment payment = generatePaymentForOrder(order);
            order.setPayment(payment);
            orderRepository.save(order);
        }

    }

    private Payment generatePaymentForOrder(Order order) {
        // Gerando aleatoriamente um número de dias entre 0 e 30
        long delayInDays = ThreadLocalRandom.current().nextInt(0, 7);
        // Gerando aleatoriamente um número de horas entre 0 e 23
        long delayInHours = ThreadLocalRandom.current().nextInt(0, 24);

        // Gerando aleatoriamente minutos e segundos entre 0 e 59
        long delayInMinutes = ThreadLocalRandom.current().nextInt(0, 60);
        long delayInSeconds = ThreadLocalRandom.current().nextInt(0, 60);

        // Calculando o delay total em segundos
        long totalDelayInSeconds = delayInDays * 24 * 60 * 60 + delayInHours * 60 * 60 + delayInMinutes * 60 + delayInSeconds;

        // Gerando o momento de pagamento, que será após o momento do pedido
        Instant paymentMoment = order.getMoment().plusSeconds(totalDelayInSeconds);

        return new Payment(paymentMoment, order);
    }

}