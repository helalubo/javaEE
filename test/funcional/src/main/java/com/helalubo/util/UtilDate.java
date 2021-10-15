package com.helalubo.util;

import java.time.LocalDate;
import java.time.Period;

public class UtilDate {

    // ************************************
    // Obtiene rango entre fecha ingresada y actual
    // ideal para sacar edades

    public static int getAge(LocalDate birthDate) {
        return Period.between(birthDate, LocalDate.now()).getYears();
    }
}
