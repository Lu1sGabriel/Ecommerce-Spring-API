package com.luisgoes.ecommerce.ecommerceapi.config;

import com.github.javafaker.Faker;
import com.luisgoes.ecommerce.ecommerceapi.entities.User;
import com.luisgoes.ecommerce.ecommerceapi.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@Profile(value = "test")
public class TestConfig implements CommandLineRunner {
    private final static int QUANTITY = 10;

    @Autowired
    private UserRepository userRepository;


    @Override
    public void run(String... args) {
        var userList = createRandomUsers();
        userRepository.saveAll(userList);
    }

    private List<User> createRandomUsers() {
        Faker faker = new Faker();
        List<User> userList = new ArrayList<>();

        // Lista de domínios populares
        List<String> emailDomains = Arrays.asList("gmail.com", "hotmail.com", "yahoo.com", "outlook.com", "live.com");

        for (int i = 0; i < QUANTITY; i++) {
            // Gerando dados aleatórios
            String name = faker.name().fullName();

            // Gerando o domínio aleatório a partir da lista
            String domain = emailDomains.get(faker.number().numberBetween(0, emailDomains.size()));

            // Gerando o email concatenando o nome com o domínio aleatório
            String email = name.toLowerCase().replaceAll("[^a-zA-Z0-9]", ".") + "@" + domain;

            // Gerando um número de telefone no formato brasileiro
            String areaCode = String.format("%02d", faker.number().numberBetween(11, 99)); // Código de área aleatório (11 a 99)
            String phone = String.format("(%s) 9%s-%s", areaCode,
                    faker.number().digits(4), // 4 primeiros números do celular
                    faker.number().digits(4)); // 4 últimos números do celular

            String password = faker.internet().password(8, 16);

            // Criando o objeto User e adicionando à lista
            User user = new User(name, email, phone, password);
            userList.add(user);
        }

        return userList;
    }

}