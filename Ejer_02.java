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

public class GestionhotelOCP{  
    public static void main(String[]args){  
        Habitacion h1=new Habitacion(1,"simple",50);  
        LocalDate i=LocalDate.of(2025,9,2);  
        LocalDate f=LocalDate.of(2025,9,5);  
        if(h1.getGestor().verificarDisponibilidad(i,f)){  
            PoliticaCancelacion politica=new PoliticaCancelacionModerada();  
            Reserva r=new Reserva(h1,i,f,new Cliente("francois","frangames@gmail.pe"),politica);  
            h1.getGestor().agregarReserva(r);  
            h1.marcarReservada();  
            System.out.println("reserva hecha");  
            LocalDate fechaCancel=LocalDate.of(2025,9,1);  
            boolean cancelada=r.cancelar(fechaCancel);  
            if(cancelada){  
                h1.getGestor().eliminarReserva(r);  
                h1.marcarDisponible();  
                System.out.println("reserva cancelada");  
            }  
        }else{  
            System.out.println("no hay disponible");  
        }  
    }  
}  