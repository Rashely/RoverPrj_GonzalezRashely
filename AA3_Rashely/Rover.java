import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class Rover {

  private String nombrePropio;
  private double potenciaInicial;
  private double potenciaDisponible;
  private int posicionInicialX;
  private int posicionInicialY;
  private int posicionActualX;
  private int posicionActualY;
  private int cantidadRecargasRealizadas;
  private int contadorDeteccionesFuga;
  private ArrayList<ArrayList<String>> mandatosExitosos;
  private ArrayList<ArrayList<String>> mandatosFallidos;
  private double costoMovimiento;
  private double costoDeteccion;
  private int recargasMaximas;
  private String codigoRover;
  private static int cantidadRoversCreados = 0;
  private static ArrayList<Rover> todosLosRovers = new ArrayList<Rover>();

  public Rover(String nombrePropio) {
    this(nombrePropio, 100.0);
  }

  public Rover(String nombrePropioP, double potencia) {
    this.nombrePropio = nombrePropioP;
    this.potenciaInicial = potencia;
    this.potenciaDisponible = potencia;

    this.posicionInicialX = 0;
    this.posicionInicialY = 0;
    this.posicionActualX = posicionInicialX;
    this.posicionActualY = posicionInicialY;

    this.cantidadRecargasRealizadas = 0;
    this.contadorDeteccionesFuga = 0;

    this.mandatosExitosos = new ArrayList<ArrayList<String>>();
    this.mandatosFallidos = new ArrayList<ArrayList<String>>();

    this.costoMovimiento = 0.50;
    this.costoDeteccion = 0.25;

    this.recargasMaximas = 5;

    this.codigoRover = "RVR-" + System.currentTimeMillis() % 100000;

    cantidadRoversCreados++;
    todosLosRovers.add(this);
  }

  public void moverIzquierda() {
    if (validarPotenciaActual()) {
      if (!detectarFuga()) {
        posicionActualX -= 1;
        potenciaDisponible -= costoMovimiento;
        registrarMandato("Desplazamiento Izquierda", "Posible");
      } else {
        registrarMandato("Desplazamiento Izquierda", "No posible: fuga detectada");
      }
    } else {
      registrarMandato("Desplazamiento Izquierda", "No posible: potencia insuficiente");
    }
  }

  public void moverDerecha() {
    if (validarPotenciaActual()) {
      if (!detectarFuga()) {
        posicionActualX += 1;
        potenciaDisponible -= costoMovimiento;
        registrarMandato("Desplazamiento Derecha", "Posible");
      } else {
        registrarMandato("Desplazamiento Derecha", "No posible: fuga detectada");
      }
    } else {
      registrarMandato("Desplazamiento Derecha", "No posible: potencia insuficiente");
    }
  }

  public void moverArriba() {
    if (validarPotenciaActual()) {
      if (!detectarFuga()) {
        posicionActualY += 1;
        potenciaDisponible -= costoMovimiento;
        registrarMandato("Desplazamiento Arriba", "Posible");
      } else {
        registrarMandato("Desplazamiento Arriba", "No posible: fuga detectada");
      }
    } else {
      registrarMandato("Desplazamiento Arriba", "No posible: potencia insuficiente");
    }
  }
  
  public void moverAbajo() {
    if (validarPotenciaActual()) {
      if (!detectarFuga()) {
        posicionActualY -= 1;
        potenciaDisponible -= costoMovimiento;
        registrarMandato("Desplazamiento Abajo", "Posible");
      } else {
        registrarMandato("Desplazamiento Abajo", "No posible: fuga detectada");
      }
    } else {
      registrarMandato("Desplazamiento Abajo", "No posible: potencia insuficiente");
    }
  }

  public String consultarPosicionActual() {
    return "Posición actual (x,y): " + posicionActualX + ", " + posicionActualY;
  }

  public double getPotenciaDisponible() {
    return potenciaDisponible;
  }

  public void recargarUnidadesPotencia(double potencia) {
    if (validarRecarga()) {
      potenciaDisponible += potencia;
      cantidadRecargasRealizadas++;
      registrarMandato("Recarga (" + potencia + ")", "Posible");
    } else {
      registrarMandato("Recarga (" + potencia + ")", "No posible: recargas agotadas");
    }
  }

  public static int getCantidadRoversCreados() {
    return cantidadRoversCreados;
  }

  public static String getInformacionTodosLosRovers() {
    String info = "===== Información de todos los Rovers =====\n";
    info += "Total de Rovers creados: " + cantidadRoversCreados + "\n\n";
    for (int i = 0; i < todosLosRovers.size(); i++) {
      info += todosLosRovers.get(i).toString();
      info += "\n";
    }
    return info;
  }

  private boolean detectarFuga() {
    contadorDeteccionesFuga++;
    potenciaDisponible -= costoDeteccion;
    Random random = new Random();
    return random.nextDouble() >= 0.5;
  }

  private boolean validarRecarga() {
    return cantidadRecargasRealizadas < recargasMaximas;
  }

  private boolean validarPotenciaActual() {
    double costoMinimo = costoMovimiento + costoDeteccion;
    return potenciaDisponible >= costoMinimo;
  }

  private String determinarFechaHoraActual() {
    Date fecha = new Date(System.currentTimeMillis());
    DateFormat formato = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
    return formato.format(fecha);
  }

  private void registrarMandato(String tipoMandato, String estatusMandato) {
    ArrayList<String> mandato = new ArrayList<String>();
    mandato.add(tipoMandato);
    mandato.add(estatusMandato);
    mandato.add(determinarFechaHoraActual());

    if ("Posible".compareTo(estatusMandato) == 0) {
      mandatosExitosos.add(mandato);
    } else {
      mandatosFallidos.add(mandato);
    }
  }

  public String toString() {
    String msg = "";

    msg += "========== Ficha del Rover ==========\n";
    msg += "Código: " + codigoRover + "\n";
    msg += "Nombre: " + nombrePropio + "\n";
    msg += "Potencia inicial: " + potenciaInicial + "\n";
    msg += "Potencia disponible: " + potenciaDisponible + "\n";
    msg += "Posición inicial: (" + posicionInicialX + ", " + posicionInicialY + ")\n";
    msg += "Posición actual:  (" + posicionActualX + ", " + posicionActualY + ")\n";
    msg += "Recargas realizadas: " + cantidadRecargasRealizadas + "\n";
    msg += "Recargas disponibles: " + (recargasMaximas - cantidadRecargasRealizadas) + "\n";
    msg += "Detecciones de fuga realizadas: " + contadorDeteccionesFuga + "\n";

    msg += "---- Mandatos Exitosos(" + mandatosExitosos.size() + ") ----\n";
    for (int i = 0; i < mandatosExitosos.size(); i++) {
      ArrayList<String> m = mandatosExitosos.get(i);
      msg += (i + 1) + ". " + m.get(2) + " | " + m.get(0) + " | " + m.get(1) + "\n";
    }
    if (mandatosExitosos.isEmpty()) {
      msg += "(sin registros)\n";
    }

    msg += "\n---- Mandatos Fallidos(" + mandatosFallidos.size() + ") ----\n";
    for (int i = 0; i < mandatosFallidos.size(); i++) {
      ArrayList<String> m = mandatosFallidos.get(i);
      msg += (i + 1) + ". " + m.get(2) + " | " + m.get(0) + " | " + m.get(1) + "\n";
    }
    if (mandatosFallidos.isEmpty()) {
      msg += "(sin registros)\n";
    }

    return msg;
  }
}