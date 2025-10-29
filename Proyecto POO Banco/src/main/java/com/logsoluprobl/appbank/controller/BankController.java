package com.logsoluprobl.appbank.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.logsoluprobl.appbank.exception.DomainException;
import com.logsoluprobl.appbank.model.Account;
import com.logsoluprobl.appbank.model.Customer;
import com.logsoluprobl.appbank.model.Transaction;
import com.logsoluprobl.appbank.service.BankService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/api/bank")
public class BankController {

    private final BankService bankService;

    public BankController(BankService bankService) {
        this.bankService = bankService;
    }

    // DTO (Data Transfer Object) para crear cuentas
    public static class AccountCreationRequest {
        public String type;       // "SAVINGS" o "CHECKING"
        public String accountId;
        public double parameter;  // Tasa de interés o Límite de sobregiro
    }

    // ------------------------------
    // Gestión de clientes
    // ------------------------------

    @Operation(summary = "Registrar un nuevo cliente", 
               description = "Crea un nuevo cliente en el sistema bancario con su ID, nombre y correo electrónico.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error en los datos proporcionados")
    })
    @PostMapping("/customers")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        try {
            Customer createdCustomer = bankService.createCustomer(
                customer.getId(), 
                customer.getName(), 
                customer.getEmail()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Obtener todos los clientes", 
               description = "Devuelve una lista con todos los clientes registrados en el banco.")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente")
    @GetMapping("/customers")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = bankService.getAllCustomers();
        return ResponseEntity.ok(customers);
    }

    @Operation(summary = "Buscar cliente por ID", 
               description = "Obtiene la información de un cliente a partir de su identificador único.")
    @Parameter(name = "customerId", description = "ID del cliente (por ejemplo, C001)", required = true)
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("/customers/{customerId}")
    public ResponseEntity<Customer> findCustomerById(@PathVariable String customerId) {
        Customer customer = bankService.findCustomerById(customerId);
        return customer != null ? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    // ------------------------------
    // Gestión de cuentas
    // ------------------------------

    @Operation(summary = "Crear una nueva cuenta", 
               description = "Crea una cuenta de tipo 'SAVINGS' o 'CHECKING' asociada a un cliente existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cuenta creada correctamente"),
        @ApiResponse(responseCode = "400", description = "Error en los datos o tipo de cuenta inválido"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PostMapping("/customers/{customerId}/accounts")
    public ResponseEntity<Account> createAccount(
            @Parameter(description = "ID del cliente al que se le creará la cuenta") 
            @PathVariable String customerId, 
            @RequestBody AccountCreationRequest request) {
        try {
            Customer customer = bankService.findCustomerById(customerId);
            if (customer == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            Account createdAccount;
            if ("SAVINGS".equalsIgnoreCase(request.type)) {
                createdAccount = bankService.createSavingsAccount(request.accountId, customer, request.parameter);
            } else if ("CHECKING".equalsIgnoreCase(request.type)) {
                createdAccount = bankService.createCheckingAccount(request.accountId, customer, request.parameter);
            } else {
                throw new DomainException("Tipo de cuenta no válido.");
            }
            
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAccount);
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @Operation(summary = "Buscar cuenta por ID", 
               description = "Obtiene la información de una cuenta bancaria usando su identificador.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cuenta encontrada"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<Account> findAccountById(@PathVariable String accountId) {
        Account account = bankService.findAccountById(accountId);
        return account != null ? ResponseEntity.ok(account) : ResponseEntity.notFound().build();
    }

    @Operation(summary = "Listar cuentas de un cliente", 
               description = "Devuelve todas las cuentas asociadas a un cliente específico.")
    @Parameter(name = "customerId", description = "ID del cliente (por ejemplo, C001)", required = true)
    @ApiResponse(responseCode = "200", description = "Lista de cuentas obtenida correctamente")
    @GetMapping("/customers/{customerId}/accounts")
    public ResponseEntity<List<Account>> getAccountsByCustomer(@PathVariable String customerId) {
        List<Account> accounts = bankService.getAccountsByCustomer(customerId);
        return ResponseEntity.ok(accounts);
    }

    // ------------------------------
    // Operaciones de transacciones
    // ------------------------------

    @Operation(summary = "Depositar dinero", 
               description = "Realiza un depósito de dinero en una cuenta bancaria existente.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Depósito realizado correctamente"),
        @ApiResponse(responseCode = "400", description = "Error al realizar el depósito")
    })
    @PostMapping("/accounts/{accountId}/deposit")
    public ResponseEntity<Boolean> deposit(
            @PathVariable String accountId, 
            @Parameter(description = "Monto a depositar") @RequestParam double amount) {
        try {
            boolean success = bankService.deposit(accountId, amount);
            return success ? ResponseEntity.status(HttpStatus.CREATED).body(true)
                           : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @Operation(summary = "Retirar dinero", 
               description = "Permite retirar fondos de una cuenta bancaria si hay saldo suficiente.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Retiro exitoso"),
        @ApiResponse(responseCode = "400", description = "Saldo insuficiente o cuenta inválida")
    })
    @PostMapping("/accounts/{accountId}/withdraw")
    public ResponseEntity<Boolean> withdraw(
            @PathVariable String accountId, 
            @Parameter(description = "Monto a retirar") @RequestParam double amount) {
        try {
            boolean success = bankService.withdraw(accountId, amount);
            return success ? ResponseEntity.status(HttpStatus.CREATED).body(true)
                           : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    public static class TransferRequest {
        public String toAccountId;
        public double amount;
    }

    @Operation(summary = "Transferir dinero entre cuentas", 
               description = "Transfiere una cantidad de dinero de una cuenta a otra.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Transferencia realizada con éxito"),
        @ApiResponse(responseCode = "400", description = "Fondos insuficientes o cuentas inválidas")
    })
    @PostMapping("/accounts/{fromAccountId}/transfer")
    public ResponseEntity<Boolean> transfer(
            @PathVariable String fromAccountId, 
            @RequestBody TransferRequest request) {
        try {
            boolean success = bankService.transfer(fromAccountId, request.toAccountId, request.amount);
            return success ? ResponseEntity.ok(true)
                           : ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        } catch (DomainException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(false);
        }
    }

    @Operation(summary = "Consultar transacciones", 
               description = "Obtiene el historial de transacciones realizadas en una cuenta específica.")
    @Parameter(name = "accountId", description = "ID de la cuenta a consultar", required = true)
    @ApiResponse(responseCode = "200", description = "Lista de transacciones obtenida correctamente")
    @GetMapping("/accounts/{accountId}/transactions")
    public ResponseEntity<List<Transaction>> getTransactions(@PathVariable String accountId) {
        List<Transaction> transactions = bankService.getAccountTransactions(accountId);
        return ResponseEntity.ok(transactions);
    }

    // ------------------------------
    // Intereses
    // ------------------------------

    @Operation(summary = "Aplicar intereses", 
               description = "Aplica los intereses acumulados a la cuenta indicada.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Intereses aplicados correctamente"),
        @ApiResponse(responseCode = "404", description = "Cuenta no encontrada")
    })
    @PostMapping("/accounts/{accountId}/apply-interest")
    public ResponseEntity<Void> applyInterest(@PathVariable String accountId) {
        bankService.applyInterest(accountId);
        return ResponseEntity.ok().build();
    }

    // ------------------------------
    // Manejo global de errores
    // ------------------------------

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<String> handleDomainException(DomainException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
