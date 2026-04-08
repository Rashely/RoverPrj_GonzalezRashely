import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PrincipalCuenta {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Cuenta> cuentas = new ArrayList<>();
        int actual = -1; // índice de la cuenta seleccionada actualmente

        System.out.println("======================================");
        System.out.println("   Sistema de Gestión de Cuentas");
        System.out.println("======================================");

        boolean salir = false;
        while (!salir) {
            System.out.println("\nMenú principal");
            System.out.println("1) Crear cuenta");
            System.out.println("2) Conocer cantidad de cuentas creadas");
            System.out.println("3) Listar cuentas");
            System.out.println("4) Seleccionar cuenta actual");
            System.out.println("5) Asignar nombre del cuenta habiente");
            System.out.println("6) Depositar");
            System.out.println("7) Retirar");
            System.out.println("8) Consultar saldo");
            System.out.println("9) Consultar estado de la cuenta");
            System.out.println("10) Salir");
            System.out.print("Opción: ");
            String op = sc.nextLine().trim();

            switch (op) {

                case "1": { // Crear cuenta
                    System.out.println("Creación de cuenta");
                    System.out.println("1) Constructor con nombre y saldo");
                    System.out.println("2) Constructor solo con saldo");
                    System.out.print("Elija: ");
                    String tipoConstructor = sc.nextLine().trim();

                    System.out.print("Saldo inicial: ");
                    String lineSaldo = sc.nextLine().trim();
                    double saldo;
                    try {
                        saldo = Double.parseDouble(lineSaldo);
                    } catch (NumberFormatException e) {
                        System.out.println("Número inválido. No se creó la cuenta.");
                        break;
                    }

                    Cuenta c;
                    if (tipoConstructor.equals("1")) {
                        System.out.print("Nombre del cuenta habiente: ");
                        String nombre = sc.nextLine().trim();
                        c = new Cuenta(nombre, saldo);
                    } else if (tipoConstructor.equals("2")) {
                        c = new Cuenta(saldo);
                    } else {
                        System.out.println("Opción inválida. No se creó la cuenta.");
                        break;
                    }

                    cuentas.add(c);
                    actual = cuentas.size() - 1;
                    System.out.println("Cuenta creada y seleccionada (índice " + actual + ").");
                    System.out.println("Código asignado: " + c.getCodCuenta());
                    break;
                }

                case "2": { // Cantidad de cuentas creadas
                    System.out.println("Total de cuentas creadas: " + Cuenta.getCantCuentasCreadas());
                    break;
                }

                case "3": { // Listar cuentas
                    if (cuentas.isEmpty()) {
                        System.out.println("No hay cuentas creadas.");
                    } else {
                        System.out.println("Índice | Código    | Cuenta habiente       | Saldo");
                        for (int i = 0; i < cuentas.size(); i++) {
                            Cuenta c = cuentas.get(i);
                            System.out.printf("  %d    | %-9s | %-21s | %.2f%n",
                                    i,
                                    c.getCodCuenta(),
                                    c.toString().split("Cuenta habiente:")[1].split("\n")[0].trim(),
                                    c.getSaldo());
                        }
                    }
                    break;
                }

                case "4": { // Seleccionar cuenta
                    if (cuentas.isEmpty()) {
                        System.out.println("No hay cuentas creadas. Cree una cuenta primero.");
                        break;
                    }
                    System.out.print("Índice de la cuenta a seleccionar: ");
                    String idxS = sc.nextLine().trim();
                    try {
                        int idx = Integer.parseInt(idxS);
                        if (idx >= 0 && idx < cuentas.size()) {
                            actual = idx;
                            System.out.println("Cuenta índice " + actual + " seleccionada (" + cuentas.get(actual).getCodCuenta() + ").");
                        } else {
                            System.out.println("Índice fuera de rango. No existe esa cuenta.");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Índice inválido.");
                    }
                    break;
                }

                case "5": { // Asignar nombre del cuenta habiente
                    if (actual < 0 || cuentas.isEmpty()) {
                        System.out.println("Debe crear y seleccionar una cuenta primero.");
                        break;
                    }
                    System.out.print("Nuevo nombre del cuenta habiente: ");
                    String nuevoNombre = sc.nextLine().trim();
                    cuentas.get(actual).setNombreCuentaHabiente(nuevoNombre);
                    System.out.println("Nombre asignado correctamente.");
                    break;
                }

                case "6": { // Depositar
                    if (actual < 0 || cuentas.isEmpty()) {
                        System.out.println("Debe crear y seleccionar una cuenta primero.");
                        break;
                    }
                    System.out.print("Monto a depositar: ");
                    String lineDeposito = sc.nextLine().trim();
                    double monto;
                    try {
                        monto = Double.parseDouble(lineDeposito);
                    } catch (NumberFormatException e) {
                        System.out.println("Monto inválido. No se realizó el depósito.");
                        break;
                    }
                    if (monto <= 0) {
                        System.out.println("El monto debe ser mayor a cero.");
                        break;
                    }
                    double saldoTrasDeposito = cuentas.get(actual).depositar(monto);
                    System.out.printf("Depósito exitoso. Saldo actual: %.2f%n", saldoTrasDeposito);
                    break;
                }

                case "7": { // Retirar
                    if (actual < 0 || cuentas.isEmpty()) {
                        System.out.println("Debe crear y seleccionar una cuenta primero.");
                        break;
                    }
                    System.out.print("Monto a retirar: ");
                    String lineRetiro = sc.nextLine().trim();
                    double montoRetiro;
                    try {
                        montoRetiro = Double.parseDouble(lineRetiro);
                    } catch (NumberFormatException e) {
                        System.out.println("Monto inválido. No se realizó el retiro.");
                        break;
                    }
                    if (montoRetiro <= 0) {
                        System.out.println("El monto debe ser mayor a cero.");
                        break;
                    }
                    Cuenta cuentaActual = cuentas.get(actual);

                    double saldoAntes = cuentaActual.getSaldo();
                    double saldoDespues = cuentaActual.retirar(montoRetiro);

                    if (saldoDespues == saldoAntes) {
                        System.out.println("Fondos insuficientes. No se realizó el retiro.");
                    } else {
                        System.out.printf("Retiro exitoso. Saldo actual: %.2f%n", saldoDespues);
                    }
                    break;
                }

                case "8": { // Consultar saldo
                    if (actual < 0 || cuentas.isEmpty()) {
                        System.out.println("Debe crear y seleccionar una cuenta primero.");
                        break;
                    }
                    System.out.printf("Saldo de %s: %.2f%n",
                            cuentas.get(actual).getCodCuenta(),
                            cuentas.get(actual).getSaldo());
                    break;
                }

                case "9": { // Consultar estado 
                    if (actual < 0 || cuentas.isEmpty()) {
                        System.out.println("Debe crear y seleccionar una cuenta primero.");
                        break;
                    }
                    System.out.println(cuentas.get(actual).toString());
                    break;
                }

                case "10": { // Salir
                    salir = true;
                    System.out.println("¡Hasta luego!");
                    break;
                }

                default:
                    System.out.println("Opción inválida.");
            }
        }
        sc.close();
    }
}