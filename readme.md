# 🍽️ El Buen Sabor Final - Backend

Este proyecto representa el backend de **El Buen Sabor**, una aplicación de comercio electrónico ficticia enfocada en la venta de alimentos. Desarrollado como parte de un proyecto académico, este sistema gestiona operaciones clave como pedidos, productos, usuarios y pagos.

## 🚀 Tecnologías Utilizadas

- **Java 17**
- **Spring Boot**
- **JPA/Hibernate**
- **Lombok**
- **Base de datos H2**
- **Gradle** como herramienta de construcción

## 🧩 Estructura del Proyecto

```plaintext
El-Buen-Sabor-Final/
├── src/
│   └── main/
│       ├── java/
│       │   └── com/
│       │       └── elbuensabor/
│       │           ├── controllers/
│       │           ├── models/
│       │           ├── repositories/
│       │           └── services/
│       └── resources/
│           ├── application.properties
│           └── static/
├── build.gradle
├── settings.gradle
└── README.md
```

## ⚙️ Configuración y Ejecución

### 1. Clonar el repositorio

```bash
git clone https://github.com/Kiaryy/El-Buen-Sabor-Final.git
cd El-Buen-Sabor-Final
```

### 2. Compilar y ejecutar la aplicación

Utilizar tu editor preferido (Se recomienda NeoVim) o ejecutar desde la línea de comandos:

```bash
./gradlew bootRun
```

### 3. Acceder a la aplicación

Una vez iniciada, la aplicación estará disponible en:

[http://localhost:8080](http://localhost:8080)

### 4. Consola de la base de datos H2

Accede a la consola de H2 en:

[http://localhost:8080/h2-console](http://localhost:8080/h2-console)

- **JDBC URL:** `jdbc:h2:./db/buenSabor_db`
- **Usuario:** `sa`
- **Contraseña:** *(dejar en blanco)*

## 📦 Funcionalidades Principales

- Gestión de productos, insumos y recetas.
- Procesamiento de pedidos y seguimiento de estados.
- Administración de usuarios y roles.
- Integración con pasarelas de pago.
- API RESTful para interacción con el frontend.

## 📁 Recursos Adicionales

- **Frontend del proyecto:** [El-Buen-Sabor-FrontEnd](https://github.com/OTSUGUA14/Buen-Sabor-Front2)
- **Repositorio anterior (archivado):** [El-Buen-Sabor-BackEnd](https://github.com/Kiaryy/El-Buen-Sabor-BackEnd)
