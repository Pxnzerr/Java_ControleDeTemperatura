package com.industrial.sensor;

/**
 * Utilitário estático para conversão entre escalas termométricas industriais
 * (Celsius, Fahrenheit e Kelvin).
 */
public final class ConversorTemperatura {

    private ConversorTemperatura() {
        // Construtor privado para impedir instanciação
    }

    public static double paraFahrenheit(double celsius) {
        return (celsius * 9.0 / 5.0) + 32.0;
    }

    public static double paraKelvin(double celsius) {
        return celsius + 273.15;
    }

    public static double paraCelsiusDeFahrenheit(double fahrenheit) {
        return (fahrenheit - 32.0) * 5.0 / 9.0;
    }

    public static double paraCelsiusDeKelvin(double kelvin) {
        if (kelvin < 0.0) {
            throw new IllegalArgumentException("Temperatura em Kelvin não pode ser inferior ao zero absoluto (0 K).");
        }
        return kelvin - 273.15;
    }
}
