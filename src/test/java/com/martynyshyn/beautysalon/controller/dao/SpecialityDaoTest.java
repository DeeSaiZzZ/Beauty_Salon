package com.martynyshyn.beautysalon.controller.dao;

import com.martynyshyn.beautysalon.dao.SpecialityDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
class SpecialityDaoTest {

    private final Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/testDB", "root", "admin");

    public SpecialityDaoTest() throws SQLException {
    }

    @Test
    void getSpecialityNameByIdEngTest() {
        String expectedName = "Hairdresser";
        assertEquals(expectedName, new SpecialityDao().getSpecialityNameById(1, "en", connection));
    }

    @Test
    void getSpecialityNameByIdUkrTest() {
        String expectedName = "Перукар";
        assertEquals(expectedName, new SpecialityDao().getSpecialityNameById(1, "uk", connection));
    }

    @Test
    void getSpecialityNameByIdLocaleNull() {
        assertNull(new SpecialityDao().getSpecialityNameById(1, "eng", connection));
    }
}
