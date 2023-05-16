## Políticas de Reintentos - Sprint Retry

Spring Retry proporciona la capacidad de volver a invocar automáticamente una operación fallida. Esto es útil cuando los errores pueden ser transitorios (como una falla momentánea en la red).

## Descripción

Las llamadas de un servicio a otro en una arquitectura de microservicios son comunes, así como frecuentes pueden ser los errores que pueden tener lugar en la comunicación.

Ocasionalmente tienen lugar errores temporales por diversas causas como errores temporales de red, instancias saturadas, tiempos de respuesta excedidos, entre otros.

Cuando se produce este tipo de situaciones se afecta la calidad de servicio al no entregar la respuesta esperada por errores de muy poca duración, como solución a este tipo de situaciones se requiere poner en práctica una política de reintentos, que permita volver a realizar la solicitud en un número determinado de intentos para buscar la respuesta deseada. Esta práctica es el patrón Retry.

### Distintos Escenarios

Spring Retry se puede aplicar en diferentes escenarios en los que se requiere la reintentar una operación en caso de fallo. Aquí hay algunos ejemplos:

- Llamadas a servicios externos(HTTP): Por ejemplo, una aplicación que llama a un servicio web puede reintentar la llamada en caso de fallo de la conexión a Internet o de errores en el servicio web.
  
- Operaciones de base de datos: Por ejemplo, una aplicación que realiza operaciones de lectura o escritura en una base de datos puede reintentar la operación en caso de fallo de la conexión a la base de datos o errores en las consultas.
  
- Operaciones de archivo (SMB, FTP, FTPS, etc): Por ejemplo, una aplicación que lee o escribe en un archivo local o en la nube puede reintentar la operación en caso de fallo de la conexión o errores en el acceso al archivo.
  
- Operaciones de red: Por ejemplo, una aplicación que realiza operaciones de red, como enviar o recibir paquetes, puede reintentar la operación en caso de fallo de la conexión de red o errores en el transporte de datos.
  

Estos son solo algunos ejemplos, pero la aplicación de Spring Retry puede ser útil en cualquier situación en la que se requiera reintentar una operación en caso de fallo.

### Instalación

Agregar la depenencia de Spring Retry en tu proyecto, puedes hacer esto agregando la siguiente línea a tu archivo pom.xml si estás utilizando Maven:

```
<dependency>
  <groupId>org.springframework.retry</groupId>
  <artifactId>spring-retry</artifactId>
  <version>1.2.5.RELEASE</version>
</dependency>
```

Habilitar el soporte de Spring Retry en tu clase de configuración: Puedes hacer esto agregando la siguiente anotación a tu clase de configuración:

```
@EnableRetry
```

Anotar tus métodos para que sean reintentables: Puedes hacer esto agregando la siguiente anotación a tus métodos:

```
@Retryable(value = {RuntimeException.class},
            maxAttempts = 4,
            backoff = @Backoff(multiplier = 2))
```

```
@Retryable(maxAttempts = 3, backoff = @Backoff(delay = 5000))
```

La anotación [Retryable](https://docs.spring.io/spring-retry/docs/1.1.2.RELEASE/apidocs/org/springframework/retry/annotation/Retryable.html) posee los parámetros:

- **value**: Indica las excepciones (tipos) para las cuales el método anotado va aplicar reintentos, este caso los reintentos tendrán lugar cuando tengan lugar excepciones del tipo RuntimeException.
- **maxAttempts**: Número máximo de reintentos que se van a aplicar.
- **backoff**: Define la forma en que el reintento puede aplicarse. La forma del reintento o política puede ser configurada a través de la anotación derivada @Backoff.

La anotación [backoff](https://docs.spring.io/spring-retry/docs/api/current/org/springframework/retry/annotation/Backoff.html) por su parte en el ejemplo tiene el atributo ***multiplier*** en valor 2, lo que significa que ese va a ser el factor de multiplicación que se usará entre reintento y reintento, para este caso particular se intentará en los segundos al pasar 1s, luego 2s, luego 4s, etc, hasta cumplirse el número de reintentos establecidos en **maxAttemps**. Esta anotación incluye el atributo ***delay***, que se usa para reintentar en cada ciertos períodos de tiempo fijos, sin necesidad del factor de multiplicación.

```
@Recover
    public void recover(Exception e) {
        // Acción a tomar si la llamada al servicio web ha fallado después de los 3 intentos
    }
```

@[Recover](https://docs.spring.io/spring-retry/docs/api/current/org/springframework/retry/annotation/Recover.html) recibe como parámetro un objeto del tipo de la excepción que él es capaz de recuperar. Este método será el que se invocará cuando la política de reintento se aplique y aún así no se logre recuperar el método anotado con @Retryable.

En resumen, utilizando Spring Retry es una forma fácil y efectiva de manejar esquemas de reintentos en Java Spring. Te permite especificar la cantidad de reintentos permitidos, el intervalo entre reintentos y la lógica de detención en una forma declarativa y fácil de usar.

### Ejemplos:

##### Ejemplo Servicio Web:

Un ejemplo práctico de Spring Retry podría ser una aplicación que intenta realizar una operación remota (por ejemplo, una llamada a un servicio web) y desea reintentar la operación en caso de fallo. Aquí está un ejemplo de código que muestra cómo implementar esta funcionalidad utilizando Spring Ret- se debe esperar 5 segundos entre cada intento. La anotación @Recover indica el método que se debe llamar si la operación falla después de los 3 intentos.

```
@Service
public class RemoteService {

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public void callRemoteService() throws Exception {
        // Realizar la llamada al servicio web aquí
        // Si la llamada falla, se reintentará hasta 3 veces con un retardo de 5 segundos entre cada intento
    }

    @Recover
    public void recover(Exception e) {
        // Acción a tomar si la llamada al servicio web ha fallado después de los 3 intentos
    }
}
```

En este ejemplo, la anotación @Retryable indica que el método callRemoteService debe ser reintentado en caso de fallo, hasta un máximo de 3 intentos. La anotación @Backoff indica que se debe esperar 5 segundos entre cada intento. La anotación @Recover indica el método que se debe llamar si la operación falla después de los 3 intentos.

##### Ejemplo Base de Datos:

Un ejemplo práctico de Spring Retry con una llamada a una base de datos podría ser una aplicación que intenta realizar una operación de lectura en una base de datos (por ejemplo, consultar un registro) y desea reintentar la operación en caso de fallo de la conexión a la base de datos. Aquí está un ejemplo de código que muestra cómo implementar esta funcionalidad utilizando Spring Retry:

```
@Service
public class DatabaseService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Retryable(value = SQLException.class, maxAttempts = 3, backoff = @Backoff(delay = 5000))
    public void readDataFromDatabase(String query) throws SQLException {
        // Ejecutar la consulta en la base de datos aquí
        jdbcTemplate.queryForList(query);
    }

    @Recover
    public void recover(SQLException e) {
        // Acción a tomar si la consulta a la base de datos ha fallado después de los 3 intentos
    }
}
```

En este ejemplo, la anotación @Retryable indica que el método readDataFromDatabase debe ser reintentado en caso de fallo de la conexión a la base de datos, hasta un máximo de 3 intentos. La anotación @Backoff indica que se debe esperar 5 segundos entre cada intento. La anotación @Recover indica el método que se debe llamar si la operación falla después de los 3 intentos. La clase JdbcTemplate de Spring es una herramienta para simplificar el acceso a bases de datos mediante JDBC.

### Parametrizar Reintentos con RetryTemplate

Para parametrizar los reintentos en Spring Retry, se puede utilizar la clase `RetryTemplate`. Esta clase permite configurar diferentes opciones, como el número máximo de reintentos, la estrategia de espera entre reintentos, y la condición para decidir cuándo reintentar una operación.

A continuación, se muestra un ejemplo de código que muestra cómo parametrizar una operación para que se reintente hasta 3 veces con una espera de 2 segundos entre reintentos:

```
@Autowired
private RetryTemplate retryTemplate;

public void executeWithRetry() {
    retryTemplate.execute(context -> {
        try {
            // Aquí se pone la lógica de la operación que se quiere proteger con reintentos
            return operation();
        } catch (Exception e) {
            // Se puede analizar la excepción aquí para decidir si se reintenta o no
            throw e;
        }
    });
}

private Object operation() {
    // Aquí se pone la lógica de la operación
}
```

```
@Configuration
public class RetryConfig {

  @Bean
  public RetryTemplate retryTemplate() {
    RetryTemplate retryTemplate = new RetryTemplate();

    //Fixed delay of 1 second between retries
    FixedBackOffPolicy fixedBackOffPolicy = new FixedBackOffPolicy();
    fixedBackOffPolicy.setBackOffPeriod(2000L);
    retryTemplate.setBackOffPolicy(fixedBackOffPolicy);

    //Retry only 3 times
    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
    retryPolicy.setMaxAttempts(3);
    retryTemplate.setRetryPolicy(retryPolicy);

    return retryTemplate;
  }
}
```

En este ejemplo, se crea una instancia de `RetryTemplate` y se configura con una política de espera fija de 2 segundos y una política de reintentos sencilla que permite un máximo de 3 reintentos. La operación que se desea proteger con reintentos se pasa como argumento al método `execute` de la plantilla de reintentos, y el contexto del reintento se proporciona a través de un `RetryCallback`. En caso de que la operación falle, se puede analizar la excepción para decidir si se debe reintentar o no.

## Otras Opciones.

Hay varias opciones disponibles para de implementar políticas de reintentos en Java:

- Hystrix: Es un framework de microservicios que incluye una opción de reintento automático en caso de fallo.
  
- Resilience4j: Es una biblioteca de Java que proporciona herramientas de resiliencia, incluido un módulo de reintento.
  
- Apache Camel: Es un framework de integración de datos que incluye una opción de reintento automático en caso de fallo.
  
- SimpleRetry: Es una biblioteca de Java que proporciona una implementación simple y fácil de usar para políticas de reintento.
  
- AsyncRetry: Es una biblioteca de Java que proporciona una implementación de reintento asíncrono para Java.
  
- Utilizando try-catch-finally: Puedes envolver el código que deseas reintentar en un bloque try-catch-finally y reintentar en el bloque finally después de cada excepción.
  
- Utilizando el patrón de diseño Retry: Puedes utilizar el patrón de diseño Retry para encapsular el código que deseas reintentar y aplicar lógica de reintento en una clase separada.
  

Estas son solo algunas de las opciones disponibles para implementar políticas de reintento en Java, y puede elegir la que mejor se adapte a sus necesidades y requisitos.

En Spring, puedes manejar esquemas de reintentos utilizando la biblioteca Spring Retry. Algunos de los pasos para hacer esto incluyen:

En cualquiera de estos casos, es importante tener en cuenta factores como la cantidad de reintentos permitidos, el intervalo entre reintentos y la lógica de detención para evitar un ciclo infinito de reintentos.
