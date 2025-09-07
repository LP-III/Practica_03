import java.util.*; 
import java.time.LocalDate;  

class Cliente{  
    String nombre;  
    String email;  
    Cliente(String n,String e){  
        nombre=n;  
        email=e;  
    }  
}
interface PoliticaCancelacion{  
    boolean puedeCancelar(Reserva r,LocalDate fechaCancelacion);  
    double calcularPenalidad(Reserva r,LocalDate fechaCancelacion);  
}  

class PoliticaCancelacionFlexible implements PoliticaCancelacion{  
    public boolean puedeCancelar(Reserva r,LocalDate fechaCancelacion){  
        return fechaCancelacion.isBefore(r.inicio.minusDays(1));  
    }  
    public double calcularPenalidad(Reserva r,LocalDate fechaCancelacion){  
        return 0;  
    }  
}  

class PoliticaCancelacionModerada implements PoliticaCancelacion{  
    public boolean puedeCancelar(Reserva r,LocalDate fechaCancelacion){  
        return fechaCancelacion.isBefore(r.inicio.minusDays(3));  
    }  
    public double calcularPenalidad(Reserva r,LocalDate fechaCancelacion){  
        if(puedeCancelar(r,fechaCancelacion)){  
            return 0;  
        }else{  
            return 0.5;  
        }  
    }  
}  

class PoliticaCancelacionEstricta implements PoliticaCancelacion{  
    public boolean puedeCancelar(Reserva r,LocalDate fechaCancelacion){  
        return false;  
    }  
    public double calcularPenalidad(Reserva r,LocalDate fechaCancelacion){  
        return 1.0;  
    }  
}  
interface ServicioLimpieza{  
    void solicitarLimpieza();  
}  

interface ServicioComida{  
    void solicitarComida(String plato);  
}  

interface ServicioLavanderia{  
    void solicitarLavanderia(int prendas);  
}  
interface CanalNotificacion{  
    void enviarNotificacion(String mensaje);  
}  

class EnviadorCorreo implements CanalNotificacion{  
    public void enviarNotificacion(String mensaje){  
        System.out.println("enviando correo: "+mensaje);  
    }  
}  

class EnviadorSMS implements CanalNotificacion{  
    public void enviarNotificacion(String mensaje){  
        System.out.println("enviando SMS: "+mensaje);  
    }  
}  

class NotificadorSlack implements CanalNotificacion{  
    public void enviarNotificacion(String mensaje){  
        System.out.println("enviando Slack: "+mensaje);  
    }  
}  
class NotificadorReserva{  
    private CanalNotificacion canal;  
    NotificadorReserva(CanalNotificacion canal){  
        this.canal=canal;  
    }  
    void notificar(String mensaje){  
        canal.enviarNotificacion(mensaje);  
    }  
}  
class Reserva{  
    Habitacion habitacion;  
    LocalDate inicio;  
    LocalDate fin;  
    Cliente cliente;  
    PoliticaCancelacion politica;  
    Reserva(Habitacion habitacion,LocalDate inicio,LocalDate fin,Cliente cliente,PoliticaCancelacion politica){  
        this.habitacion=habitacion;  
        this.inicio=inicio;  
        this.fin=fin;  
        this.cliente=cliente;  
        this.politica=politica;  
     }  
     boolean fechasValida(LocalDate i,LocalDate f){  
          if(f.isBefore(inicio)||i.isAfter(fin)){  
              return false;  
          }else{  
              return true;  
          }  
     }  
     boolean cancelar(LocalDate fechaCancelacion){  
         if(politica.puedeCancelar(this,fechaCancelacion)){  
             return true;  
         }else{  
             double p=politica.calcularPenalidad(this,fechaCancelacion);  
             System.out.println("cancelacion no permitida sin penalidad penalidad: "+(p*100)+"%");  
             return false;  
         }  
     }  
}  
class HabitacionLibre{  
    ArrayList<Reserva> reservas;  
    HabitacionLibre(){  
        reservas=new ArrayList<>();  
    }  
    boolean verificarDisponibilidad(LocalDate i,LocalDate f){  
        for(Reserva r:reservas){  
            if(r.fechasValida(i,f)){  
                return false;  
            }  
        }  
        return true;  
    }  
    void agregarReserva(Reserva r){  
        reservas.add(r);  
    }  
    void eliminarReserva(Reserva r){  
        reservas.remove(r);  
    }  
}  
class Habitacion{  
    int numero;  
    String tipo;  
    double precio;  
    boolean ocupada;  
    HabitacionLibre gestor;  
    Habitacion(int n,String t,double p){  
        numero=n;  
        tipo=t;  
        precio=p;  
        ocupada=false;  
        gestor=new HabitacionLibre();  
    }  
    void marcarReservada(){  
        ocupada=true;  
    }  
    void marcarDisponible(){  
        ocupada=false;  
    }  
    HabitacionLibre getGestor(){  
        return gestor;  
    }  
}  
class HabitacionSimple extends Habitacion implements ServicioLimpieza{  
    HabitacionSimple(int n,double p){  
        super(n,"simple",p);  
    }  
    public void solicitarLimpieza(){  
        System.out.println("limpieza solicitada para habitacion simple "+numero);  
    }  
}  

class HabitacionDoble extends Habitacion implements ServicioLimpieza,ServicioComida{  
    HabitacionDoble(int n,double p){  
        super(n,"doble",p);  
    }  
    public void solicitarLimpieza(){  
        System.out.println("limpieza solicitada para habitacion doble "+numero);  
    }  
    public void solicitarComida(String plato){  
        System.out.println("pedido de "+plato+" en habitacion doble "+numero);  
    }  
}  

class HabitacionSuite extends Habitacion implements ServicioLimpieza,ServicioComida,ServicioLavanderia{  
    HabitacionSuite(int n,double p){  
        super(n,"suite",p);  
    }  
    public void solicitarLimpieza(){  
        System.out.println("limpieza solicitada para suite "+numero);  
    }  
    public void solicitarComida(String plato){  
        System.out.println("pedido de "+plato+" en suite "+numero);  
    }  
    public void solicitarLavanderia(int prendas){  
        System.out.println("lavanderia solicitada para "+prendas+" prendas en suite "+numero);  
    }  
}  

public class GestionhotelDIP{  
    public static void main(String[]args){  
        Habitacion h1=new HabitacionSimple(1,50);  
        Habitacion h2=new HabitacionDoble(2,80);  
        Habitacion h3=new HabitacionSuite(3,150);  

        LocalDate i=LocalDate.of(2025,9,2);  
        LocalDate f=LocalDate.of(2025,9,5);  

        if(h2.getGestor().verificarDisponibilidad(i,f)){  
            PoliticaCancelacion politica=new PoliticaCancelacionModerada();  
            Reserva r=new Reserva(h2,i,f,new Cliente("francois","frangames@gmail.pe"),politica);  
            h2.getGestor().agregarReserva(r);  
            h2.marcarReservada();  
            System.out.println("reserva hecha en habitacion: "+h2.tipo);  

            NotificadorReserva notificador=new NotificadorReserva(new EnviadorCorreo());  
            notificador.notificar("reserva confirmada para habitacion "+h2.numero);  

            if(h2 instanceof ServicioLimpieza){  
                ((ServicioLimpieza)h2).solicitarLimpieza();  
            }  
            if(h2 instanceof ServicioComida){  
                ((ServicioComida)h2).solicitarComida("pizza");  
            }  

            LocalDate fechaCancel=LocalDate.of(2025,9,1);  
            boolean cancelada=r.cancelar(fechaCancel);  
            if(cancelada){  
                h2.getGestor().eliminarReserva(r);  
                h2.marcarDisponible();  
                System.out.println("reserva cancelada");  
                notificador.notificar("reserva cancelada para habitacion "+h2.numero);  
            }  
        }else{  
            System.out.println("no hay disponible");  
        }  

        if(h3 instanceof ServicioLavanderia){  
            ((ServicioLavanderia)h3).solicitarLavanderia(5);  
        }  
    }  
}  