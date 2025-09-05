class Vehiculo{
    private String color;
    private  int anioFabricacion;
    public String getColor(){
         return color;
    }
    public void setColor(String color){
        this.color=color;
    }
    public int getFabricacion(){
        return anioFabricacion;
    }
    public void setFabricacion(int fabricacion){
        this.anioFabricacion = fabricacion;
    }
    public Vehiculo(String color, int fabricacion){
        super();
        this.color=color;
        this.anioFabricacion=fabricacion;
    }
    public void acelerar(){
        System.out.println("acelerando");
    }
}

class Bicicleta extends Vehiculo{
    private double peso;
    public Bicicleta(String color, int anioFabricacion, double peso){
        super(color, anioFabricacion);
        this.peso=peso;
    } 
    public double getPeso(){
        return peso;
    }
    public void setPeso(double peso){
        this.peso= peso;
    }
    public void acelerar(){
        System.out.println("comienza a pedalear");
    }
}

class Coche extends Vehiculo{
    private String modelo;
    public Coche(String color, int anioFabricacion, String modelo){
        super(color, anioFabricacion);
        this.modelo=modelo;
    }
    public String getModelo(){
        return modelo;
    }
    public void setModelo(String modelo){
        this.modelo=modelo;
    }
    public void acelerar(){
        System.out.println("el motor esta funcionnando");
    }
}

public class Exp_01{
        public static void main(String[]args){
            Coche coche1=new Coche("rojo", 2015, "RJ45");
            Bicicleta bici1=new Bicicleta("amarillo", 2020, 12.7);
            bici1.acelerar();
            coche1.acelerar();
        }
    }