package com._5.content_manager.utils;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.ThreadLocalRandom;

public class FechaUtils {


    public static LocalDateTime generarFechaEntre(LocalDateTime inicio, LocalDateTime fin) {
        long inicioSegundos = inicio.toEpochSecond(ZoneOffset.UTC);
        long finSegundos = fin.toEpochSecond(ZoneOffset.UTC);

        if (finSegundos <= inicioSegundos) {
            throw new IllegalArgumentException("La fecha de fin debe ser posterior a la fecha de inicio.");
        }

        long randomSegundos = ThreadLocalRandom.current().nextLong(inicioSegundos, finSegundos);

        return LocalDateTime.ofEpochSecond(randomSegundos, 0, ZoneOffset.UTC);
    }
}
