# 🏦 Proyecto BancoPOO - Java

## 📖 Descripción
Este proyecto fue desarrollado como parte del **Taller Unidad 3** de la asignatura *Lógica para la solución de problemas*.  
El objetivo es simular el funcionamiento básico de un banco aplicando los principios de **Programación Orientada a Objetos (POO)** en Java.

El sistema permite gestionar clientes, cuentas y transacciones, con operaciones como crear cuentas, depositar, retirar y transferir dinero.  
Además, se implementan conceptos fundamentales como **clases, objetos, herencia, encapsulamiento y polimorfismo**.

---

## ⚙️ Tecnologías utilizadas
- **Lenguaje:** Java  
- **Paradigma:** Programación Orientada a Objetos (POO)  
- **IDE:** IntelliJ IDEA / Eclipse / NetBeans  
- **Control de versiones:** Git y GitHub  
- **Pruebas:** Postman (para validar operaciones simuladas del sistema)

---
## Estructura del proyecto
bank-app/
├── src/
│   ├── main/java/com/logsoluprobl/appbank/
│   │   ├── app/                     # Clase principal
│   │   │   └── BankAppApplication.java
│   │   ├── model/                   # Modelos de dominio (Account, Customer, etc.)
│   │   ├── service/                 # Lógica de negocio
│   │   ├── repository/              # Persistencia con JSON
│   │   ├── controller/              # Controladores REST
│   │   ├── exception/               # Excepciones personalizadas
│   │   ├── util/                    # Utilidades de lectura/escritura JSON
│   │   └── config/                  # Configuración de OpenAPI (Swagger)
│   └── resources/
│       ├── application.properties   # Configuración de Spring
│       ├── data/
│       │   ├── customers.json
│       │   └── accounts.json
│       └── static/
│
└── pom.xml

## 🧱 Componentes principales del proyecto
El sistema está dividido en varios módulos que cumplen diferentes responsabilidades:

- **Modelo:** Contiene las clases principales (`Cliente`, `Cuenta`, `Banco`, `Transaccion`, etc.)  
- **Lógica de negocio:** Gestiona las operaciones del banco (depósitos, retiros, transferencias).  
- **Interfaz o consola:** Permite la interacción del usuario con el sistema.  
- **Datos y reportes:** Archivos o estructuras utilizadas para guardar información de los clientes y transacciones.  
- **Pruebas:** Archivos de Postman y capturas de las ejecuciones realizadas.

---

## 🧩 Pruebas postman 
   <img width="1365" height="721" alt="Captura de pantalla 2025-10-28 204235" src="https://github.com/user-attachments/assets/fe8e24e9-2657-41c6-ba50-6d5da2df60f9" />
<img width="1365" height="718" alt="Captura de pantalla 2025-10-28 194701" src="https://github.com/user-attachments/assets/047b9246-e0d9-4bb2-ad4f-4946419a9e57" />
<img width="1364" height="719" alt="Captura de pantalla 2025-10-28 194353" src="https://github.com/user-attachments/assets/20eeb1eb-4e29-43f2-9ada-5f6e7f42b9d3" />
<img width="1365" height="722" alt="Captura de pantalla 2025-10-28 194336" src="https://github.com/user-attachments/assets/2c2c52b2-c866-4dc8-a447-95f0f04a294a" />
<img width="1365" height="720" alt="Captura de pantalla 2025-10-28 204556" src="https://github.com/user-attachments/assets/28699abd-b7f2-44db-bcf2-3fc3810e8f28" />
<img width="1365" height="719" alt="Captura de pantalla 2025-10-28 204544" src="https://github.com/user-attachments/assets/8570f871-0885-4b4f-961b-d43792bd50c6" />
<img width="1365" height="718" alt="Captura de pantalla 2025-10-28 204533" src="https://github.com/user-attachments/assets/e1b217bb-93e6-46e2-a679-741017f4ce38" />
<img width="1365" height="719" alt="Captura de pantalla 2025-10-28 204524" src="https://github.com/user-attachments/assets/63a442aa-07cc-42cb-a010-9eed9d7a9ea6" />
<img width="1365" height="718" alt="Captura de pantalla 2025-10-28 204250" src="https://github.com/user-attachments/assets/48d8f138-daff-414d-95a1-623ca762f847" />





## 🚀 Cómo ejecutar el proyecto
1. Clona este repositorio:
   ```bash
   git clone https://github.com/ZOJ0709/BancoPOO_Java.git


