package com.martynyshyn.beautysalon.controller.utils;

import com.martynyshyn.beautysalon.utils.QueryCreator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class QueryCreatorTest {
    @Mock
    HttpServletRequest request;
    @Mock
    Map<String, String[]> map;

    private static Stream<Arguments> testCaseForMasterQuery() {
        return Stream.of(
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee",
                        new String[]{"1", "2", "3"},
                        "SELECT id, email, firstName, lastName, rate, speciality_id FROM employee WHERE speciality_id=1 OR " +
                                "speciality_id=2 OR speciality_id=3"),
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee",
                        new String[]{"3", "2", "1"},
                        "SELECT id, email, firstName, lastName, rate, speciality_id FROM employee WHERE speciality_id=3 OR " +
                                "speciality_id=2 OR speciality_id=1"),
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee",
                        new String[]{"2", "1", "3"},
                        "SELECT id, email, firstName, lastName, rate, speciality_id FROM employee WHERE speciality_id=2 OR " +
                                "speciality_id=1 OR speciality_id=3"),
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee",
                        new String[]{"3", "1", "2"},
                        "SELECT id, email, firstName, lastName, rate, speciality_id FROM employee WHERE speciality_id=3 OR " +
                                "speciality_id=1 OR speciality_id=2"),
                Arguments.of("SELECT id, email, firstName, lastName, rate, speciality_id FROM employee",
                        new String[]{"1", "2"},
                        "SELECT id, email, firstName, lastName, rate, speciality_id FROM employee WHERE speciality_id=1 OR " +
                                "speciality_id=2")
        );
    }

    @ParameterizedTest
    @MethodSource("testCaseForMasterQuery")
    void createSqlQueryForMasters(String starterQuery, String[] filterParams, String expectedQuery) {
        when(request.getParameterMap()).thenReturn(map);
        when(map.get("filer_param")).thenReturn(filterParams);

        String result = QueryCreator.createSqlQuery(request, starterQuery);

        assertEquals(expectedQuery, result);
    }
}
