package com.martynyshyn.beautysalon.controller.dao;

import com.martynyshyn.beautysalon.dao.ConnectionPool;
import com.martynyshyn.beautysalon.dao.ServicesDao;
import com.martynyshyn.beautysalon.model.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ServicesDaoTest {

    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testDB", "root", "admin");
    @Mock
    ConnectionPool connectionPool;

    public ServicesDaoTest() throws SQLException {
    }

    @Test
    void findByIdTest() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            int id = 13;

            Service expectedService = new Service.Builder()
                    .setId(id)
                    .setName("SPA догляд за тілом")
                    .setPrice(500)
                    .setSpeciality_id(2)
                    .build();

            assertEquals(expectedService, new ServicesDao().findById(id));
        }
    }

    @Test
    void findBySpecialityTest() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            int specialityId = 3;

            List<Service> expectedList = new ArrayList<>();

            expectedList.add(new Service.Builder()
                    .setId(9)
                    .setName("Манікюр класичний")
                    .setPrice(130)
                    .setSpeciality_id(specialityId)
                    .build()
            );
            expectedList.add(new Service.Builder()
                    .setId(10)
                    .setName("Експрес манікюр")
                    .setPrice(70)
                    .setSpeciality_id(specialityId)
                    .build()
            );

            assertEquals(expectedList, new ServicesDao().findBySpeciality(specialityId));
        }
    }

    @ParameterizedTest
    @MethodSource("testCaseFindWithFilter")
    void findWithFilters(String query, int expectedSize, List<String> expectedNamesArray) {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            List<String> resultNames = new ServicesDao()
                    .findWithFilters(query)
                    .stream()
                    .map(Service::getName)
                    .sorted()
                    .collect(Collectors.toList());

            assertEquals(expectedSize, resultNames.size());
            assertEquals(expectedNamesArray, resultNames);
        }
    }

    public static Stream<Arguments> testCaseFindWithFilter() {
        return Stream.of(
                Arguments.of("SELECT id, name, price, speciality_id FROM servicelist WHERE speciality_id=1 OR speciality_id=3",
                        6, Stream.of("Стрижка жіноча", "Стрижка чоловіча", "Полірування волосся",
                                        "Фарбування короткого волосся", "Манікюр класичний", "Експрес манікюр")
                                .sorted()
                                .collect(Collectors.toList())),

                Arguments.of("SELECT id, name, price, speciality_id FROM servicelist WHERE speciality_id=5",
                        2, Stream.of("Макіяж денний", "Макіяж вечірній")
                                .sorted()
                                .collect(Collectors.toList()))
        );
    }
}

