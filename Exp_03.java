interface Arma {
    int danioArma();
    String getNombre();
}

class Espada implements Arma{
    public int danioArma(){
        return 30;
    }
    public String getNombre(){
        return "Espada";
    }
}

class Bufanda implements Arma{
    public int danioArma(){
        return 20;
    }
    public String getNombre(){
        return "Bufanda";
    }
}

class Hacha implements Arma{
    public int danioArma(){
        return 40;
    }
    public String getNombre(){
        return "Hacha";
    }
}

class Personaje {
    private String nombre;
    private int vida;

    public Personaje(String nombre, int vida){
        this.nombre =nombre;
        this.vida=vida;
    }
    public void recibirDanio(int danio){
        vida -=danio;
        if(vida < 0){
            vida = 0;
        } 
        System.out.println(nombre+" recibio "+ danio +" de daño | vida restante: " + vida);
    }
    public void atacar(Personaje enemigo, Arma arma){
        int danio = arma.danioArma();
        System.out.println(nombre + " ataca con " + arma.getNombre()+" hace "+danio+" de daño");
        enemigo.recibirDanio(danio);
    }
}

class Enemigo extends Personaje{
    public Enemigo(String nombre, int vida){
        super(nombre, vida);
    }
}

public class Deltarune{
    public static void main(String[] args) {
        Personaje humano= new Personaje("Kris", 80);
        Personaje monstruo= new Personaje("Susie",100);
        Personaje principe=new Personaje("Ralsei", 65);
        Enemigo jefe = new Enemigo("Roaring Knight", 250);
        Arma espada= new Espada();
        Arma bufanda= new Bufanda();
        Arma hacha=new Hacha();
        humano.atacar(jefe, espada);
        monstruo.atacar(jefe, hacha);
        principe.atacar(jefe, bufanda);
    }
}