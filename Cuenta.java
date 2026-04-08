import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Cuenta {
    private String codCuenta;
    private double saldo;
    private String nombreCuentaHabiente;
    private String fechaCreacion;
    private int cantDepositosRealizados;
    private int cantRetirosExitososRealizados;
    private static int cantCuentasCreadas = 0;

    public Cuenta(String nombreCuentaHabiente, double pSaldo) {
        cantCuentasCreadas++;
        this.codCuenta = "cta-" + cantCuentasCreadas;
        this.nombreCuentaHabiente = nombreCuentaHabiente;
        this.saldo = pSaldo;
        this.fechaCreacion = establecerFechaCreacion();
        this.cantDepositosRealizados = 0;
        this.cantRetirosExitososRealizados = 0;
    }

    public Cuenta(double pSaldo) {
        this("Sin asignar", pSaldo);
    }

    // Obtener la fecha actual 
    private String establecerFechaCreacion() {
        Date fecha = new Date(System.currentTimeMillis());
        DateFormat formatoFecha = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        return formatoFecha.format(fecha);
    }

    public void setNombreCuentaHabiente(String pNombreCuentaHabiente) {
        this.nombreCuentaHabiente = pNombreCuentaHabiente;
    }

    // Get el código de cuenta
    public String getCodCuenta() {
        return codCuenta;
    }

    // Get el saldo
    public double getSaldo() {
        return saldo;
    }

    // modifica el saldo y devuleve el saldo actualizado
    public double depositar(double monto) {
        if (monto > 0) {
            saldo += monto;
            cantDepositosRealizados++;
        }
        return saldo;
    }

    // modifica el saldo si hay fondos suficientes
    public double retirar(double monto) {
        if (validarRetiro(monto)) {
            saldo -= monto;
            cantRetirosExitososRealizados++;
        }
        return saldo;
    }

    // validación de retiro
    private boolean validarRetiro(double monto) {
        return saldo >= monto;
    }

    // cantidad total de cuentas creadas
    public static int getCantCuentasCreadas() {
        return cantCuentasCreadas;
    }

    
    public String toString() {
        String msg = "";

        msg += "========== Estado de la Cuenta ==========\n";
        msg += "Código:               " + codCuenta + "\n";
        msg += "Cuenta habiente:      " + nombreCuentaHabiente + "\n";
        msg += "Saldo:                " + String.format("%.2f", saldo) + "\n";
        msg += "Fecha de creación:    " + fechaCreacion + "\n";
        msg += "Depósitos realizados: " + cantDepositosRealizados + "\n";
        msg += "Retiros exitosos:     " + cantRetirosExitososRealizados + "\n";
        msg += "=========================================\n";

        return msg;
    }
}