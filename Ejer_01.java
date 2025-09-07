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

class Reserva{  
    Habitacion habitacion;  
    LocalDate inicio;  
    LocalDate fin;  
    Cliente cliente;  
    Reserva(Habitacion habitacion,LocalDate inicio,LocalDate fin,Cliente cliente){  
        this.habitacion=habitacion;  
        this.inicio=inicio;  
        this.fin=fin;  
        this.cliente=cliente;  
     }  
     boolean fechasValida(LocalDate i,LocalDate f){  
          if(f.isBefore(inicio)||i.isAfter(fin)){  
              return false;  
          }else{  
              return true;  
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

public class GestionhotelSRP{  
    public static void main(String[]args){  
        Habitacion h1=new Habitacion(1,"simple",50);  
        LocalDate i=LocalDate.of(2025,9,2);  
        LocalDate f=LocalDate.of(2025,9,5);  
        if(h1.getGestor().verificarDisponibilidad(i,f)){  
            Reserva r=new Reserva(h1,i,f,new Cliente("francois","frangames@gmail.pe"));  
            h1.getGestor().agregarReserva(r);  
            h1.marcarReservada();  
            System.out.println("reserva hecha");  
        }else{  
            System.out.println("no hay disponible");  
        }  
    }  
}