# Sistema-de-vacunacion


## Descripción
El **Sistema de Vacunación** es una aplicación web diseñada para gestionar la administración de vacunas a niños en diferentes municipios. Este sistema permite registrar, actualizar y consultar información sobre niños y las vacunas que han recibido, así como gestionar municipios y departamentos asociados. Además, incluye un sistema de manejo de excepciones personalizadas para garantizar un manejo adecuado de los errores.

### Características Principales
- **Gestión de niños**: Registrar, actualizar, y listar niños en función de su municipio.
- **Aplicación de vacunas**: Asignar vacunas a los niños, validando restricciones de edad para cada vacuna.
- **Gestión de municipios y departamentos**: Crear y modificar municipios asociados a un departamento.
- **Manejo de excepciones personalizado**: Utiliza un manejador global de excepciones con mensajes de error personalizados y códigos de estado HTTP.
  
## Tecnologías Usadas
- **Java 17**
- **Spring Boot 3.3.4**
- **Hibernate/JPA** para la persistencia de datos
- **MySQL** como base de datos
- **Maven** para la gestión de dependencias
- **JUnit 5** y **Mockito** para pruebas unitarias
- **Lombok** para la generación automática de código boilerplate
- **Jakarta Validation** para validación de entradas

## Estructura del Proyecto

El proyecto sigue una arquitectura basada en capas:

- **api**: Contiene los controladores REST y los DTOs.
- **domain**: Contiene las entidades del dominio y excepciones personalizadas.
- **infrastructure**: Incluye los repositorios y servicios.
- **utils**: Incluye enumeraciones y clases auxiliares como mensajes de error y estados HTTP personalizados.

## Instalación y Configuración

### Prerrequisitos
- **JDK 17** o superior.
- **Maven** para la gestión de dependencias.
- **MySQL** para la base de datos.

### Instrucciones de Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/tu-usuario/sistemaVacunacion.git
   cd sistemaVacunacion
