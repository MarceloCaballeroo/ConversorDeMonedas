# Conversor de Monedas

Este es un programa simple de conversión de monedas que utiliza la API de ExchangeRate-API para obtener tasas de cambio en tiempo real.

## Requisitos

- Java 8 o superior
- Gradle (para manejar dependencias)

## Configuración

1. Clona este repositorio.
2. Asegúrate de tener Gradle instalado.
3. Ejecuta `gradle build` para descargar las dependencias necesarias.

## Uso

1. Ejecuta la clase `Main` para iniciar el programa.
2. Sigue las instrucciones en pantalla para convertir monedas.

## Notas

- El programa utiliza la API gratuita de ExchangeRate-API, que tiene un límite de solicitudes. Si planeas usar este programa con frecuencia, considera obtener una clave API propia y actualizarla en `CurrencyService.java`.
- Los códigos de moneda deben ser códigos ISO 4217 válidos de tres letras (por ejemplo, USD, EUR, JPY).

## Contribuciones

Las contribuciones son bienvenidas. Por favor, abre un issue o un pull request para sugerir cambios o mejoras.