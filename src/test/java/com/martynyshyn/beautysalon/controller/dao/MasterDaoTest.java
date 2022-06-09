package com.martynyshyn.beautysalon.controller.dao;

import com.martynyshyn.beautysalon.dao.ConnectionPool;
import com.martynyshyn.beautysalon.dao.MasterDao;
import com.martynyshyn.beautysalon.model.Master;
import com.martynyshyn.beautysalon.model.User;
import org.junit.jupiter.api.BeforeAll;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MasterDaoTest {

    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testDB", "root", "admin");
    @Mock
    private ConnectionPool connectionPool;
    private static final List<Master> expectedList = new ArrayList<>();

    public MasterDaoTest() throws SQLException {
    }

    @BeforeAll
    static void setUp() {
        expectedList.add(
                new Master.Builder()
                        .setId(1)
                        .setEmail("someUser1@bfactor.com")
                        .setFirstName("test1")
                        .setLastName("testF1")
                        .setRate(5)
                        .setSpecialityId(1)
                        .setSpecialityName("Hairdresser")
                        .build()
        );
        expectedList.add(
                new Master.Builder()
                        .setId(2)
                        .setEmail("someUser2@bfactor.com")
                        .setFirstName("test2")
                        .setLastName("testF2")
                        .setRate(2.5)
                        .setSpecialityId(2)
                        .setSpecialityName("SPA master")
                        .build()
        );
        expectedList.add(
                new Master.Builder()
                        .setId(3)
                        .setEmail("someUser3@bfactor.com")
                        .setFirstName("test3")
                        .setLastName("testF3")
                        .setRate(0)
                        .setSpecialityId(3)
                        .setSpecialityName("Manicurist")
                        .build()
        );
    }

    @Test
    void findAllTest() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            int expectedArraySize = 3;

            assertEquals(expectedArraySize, new MasterDao().findAll().size());
        }
    }

    @Test
    void findMasterByEmailTest() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            String email = "someUser3@bfactor.com";

            Master expectedMaster = new Master.Builder()
                    .setId(3)
                    .setEmail(email)
                    .setFirstName("test3")
                    .setLastName("testF3")
                    .setPassword("3")
                    .setRoleId(2)
                    .build();

            assertEquals(expectedMaster, new MasterDao().findMasterByEmail(email));
        }
    }

    @Test
    void findByIdTest() {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            int id = 2;

            Master expectedMaster = new Master.Builder()
                    .setId(id)
                    .setEmail("someUser2@bfactor.com")
                    .setFirstName("test2")
                    .setLastName("testF2")
                    .setRate(2.5)
                    .setSpecialityId(2)
                    .setRoleId(2)
                    .build();

            User resultWithCon = new MasterDao().findById(id, connection);
            User result = new MasterDao().findById(id);

            assertEquals(expectedMaster, result);
            assertEquals(result, resultWithCon);
        }
    }

    @ParameterizedTest
    @MethodSource("testCasesForFindWithSort")
    void findWithSort(String query, List<User> expectedList) {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            assertEquals(expectedList, new MasterDao().findMastersWithSortAndFilters("en", query));
        }
    }

    @ParameterizedTest
    @MethodSource("testCaseForFindWithSort")
    void findWithFilterTest(String query, int expectedListSize, int expectedSpecialityId) {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            List<Master> result = new MasterDao().findMastersWithSortAndFilters("en", query);

            assertEquals(expectedListSize, result.size());
            assertSame(expectedSpecialityId, result.get(0).getSpecialityId());
        }
    }

    @ParameterizedTest
    @MethodSource("testCasesFindWithFilters")
    void findWithFiltersTest(String query, int expectedListSize, int[] expectedSpecialitiesId) {
        try (MockedStatic<ConnectionPool> cp = Mockito.mockStatic(ConnectionPool.class)) {
            cp.when(ConnectionPool::getInstance).thenReturn(connectionPool);
            when(connectionPool.getConnection()).thenReturn(connection);

            List<Master> result = new MasterDao().findMastersWithSortAndFilters("en", query);

            int[] idArray = result
                    .stream()
                    .mapToInt(Master::getSpecialityId)
                    .sorted()
                    .toArray();

            assertEquals(expectedListSize, result.size());
            assertArrayEquals(expectedSpecialitiesId, idArray);
        }
    }

    public static Stream<Arguments> testCasesFindWithFilters() {
        return Stream.of(
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee WHERE speciality_id=1" +
                                " OR speciality_id=2",
                        2, new int[]{1, 2}),
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee WHERE speciality_id=1" +
                                " OR speciality_id=3",
                        2, new int[]{1, 3}),
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee WHERE speciality_id=2" +
                                " OR speciality_id=3",
                        2, new int[]{2, 3}),
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee WHERE speciality_id=1" +
                                " OR speciality_id=2 OR speciality_id=3",
                        3, new int[]{1, 2, 3})
        );
    }

    public static Stream<Arguments> testCasesForFindWithSort() {
        return Stream.of(
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee ORDER BY firstName ASC",
                        expectedList.stream().sorted(Comparator.comparing(Master::getFirstName))
                                .collect(Collectors.toList())),
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee ORDER BY firstName DESC",
                        expectedList.stream().sorted(Collections.reverseOrder(Comparator.comparing(Master::getFirstName)))
                                .collect(Collectors.toList())),
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee ORDER BY rate ASC",
                        expectedList.stream().sorted(Comparator.comparing(Master::getRate))
                                .collect(Collectors.toList())),
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee ORDER BY rate DESC",
                        expectedList.stream().sorted(Collections.reverseOrder(Comparator.comparing(Master::getRate)))
                                .collect(Collectors.toList())));
    }

    public static Stream<Arguments> testCaseForFindWithSort() {
        return Stream.of(
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee WHERE speciality_id=1",
                        1, 1),
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee WHERE speciality_id=2",
                        1, 2),
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee WHERE speciality_id=3",
                        1, 3)
        );
    }
}
