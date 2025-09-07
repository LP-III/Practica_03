import java.util.Scanner;
interface Arma{
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

// Principio SRP -> una clase para magia/acciones especiales
interface Habilidad{
    void usar(Personaje usuario, Personaje objetivo);
    String getNombre();
}

// HealPrayer de Ralsei
class HealPrayer implements Habilidad{
    public void usar(Personaje usuario, Personaje objetivo){
        int curacion = 30;
        objetivo.curar(curacion);
        System.out.println(usuario.getNombre()+" usa Heal Prayer en "+objetivo.getNombre()+" recupera "+curacion+" de vida!!");
    }
    public String getNombre(){
        return "Heal Prayer";
    }
}

// Rude Buster de Susie
class RudeBuster implements Habilidad{
    public void usar(Personaje usuario, Personaje objetivo){
        int danio = 50;
        System.out.println(usuario.getNombre()+" usa Rude Buster y hace "+danio+" de daño!!");
        objetivo.recibirDanio(danio);
    }
    public String getNombre(){
        return "Rude Buster";
    }
}

class Personaje {
    private String nombre;
    private int vida;
    private int vidaMax;

    public Personaje(String nombre, int vida){
        this.nombre =nombre;
        this.vida=vida;
        this.vidaMax=vida;
    }
    public String getNombre(){
        return nombre;
    }
    public int getVida(){
        return vida;
    }
    public boolean estaVivo(){
        return vida > 0;
    }
    public void recibirDanio(int danio){
        vida -=danio;
        if(vida < 0){
            vida = 0;
        }
        System.out.println(nombre+" recibio "+ danio +" de daño | vida restante: " + vida);
    }
    public void curar(int puntos){
        vida += puntos;
        if(vida > vidaMax){
            vida = vidaMax;
        }
        System.out.println(nombre+" recuperó vida | Vida actual: "+vida);
    }
    public void atacar(Personaje enemigo, Arma arma){
        int danio = arma.danioArma();
        System.out.println(nombre + " ataca con " + arma.getNombre()+" hace "+danio+" de daño");
        enemigo.recibirDanio(danio);
    }
    public void magia(Personaje enemigo){
        int danio = 25;
        System.out.println(nombre+" usa magia y hace "+danio+" de daño!!");
        enemigo.recibirDanio(danio);
    }
}
class Enemigo extends Personaje{
    public Enemigo(String nombre, int vida){
        super(nombre, vida);
    }
}

public class Deltarune2ver2{
    public static void main(String[] args){
        Scanner sc=new Scanner(System.in);
        Personaje humano= new Personaje("Kris", 80);
        Personaje monstruo= new Personaje("Susie",100);
        Personaje principe=new Personaje("Ralsei", 65);
        Enemigo jefe = new Enemigo("Roaring Knight", 250);
        Arma espada= new Espada();
        Arma bufanda= new Bufanda();
        Arma hacha=new Hacha();

        // Habilidades especiales
        Habilidad rudeBuster = new RudeBuster();
        Habilidad healPrayer = new HealPrayer();

        char opcion;
        do{
            if(humano.estaVivo()){
                turnoJugador(sc, humano, jefe, espada, null, null);
            }
            if(monstruo.estaVivo()){
                turnoJugador(sc, monstruo, jefe, hacha, rudeBuster, null);
            }
            if(principe.estaVivo()){
                turnoJugador(sc, principe, jefe, bufanda, null, healPrayer);
            }
            if(jefe.estaVivo()){
                System.out.println("\n>>> Turno del Roaring Knight <<<");
                if(humano.estaVivo()){
                    jefe.atacar(humano, espada);
                }else if(monstruo.estaVivo()){
                    jefe.atacar(monstruo, hacha);
                }else if(principe.estaVivo()){
                    jefe.atacar(principe, bufanda);
                }
            }

            if(!jefe.estaVivo()){
                System.out.println("\n Ganaste 200 de oro 850 de experiencia");
                break;
            }
            if(!humano.estaVivo() && !monstruo.estaVivo() && !principe.estaVivo()){
                System.out.println("\nComo perdiste en eso");
                break;
            }
            System.out.println("\n¿Desea continuar? (S)continuar (N) abandonar: ");
            opcion = sc.next().toUpperCase().charAt(0);
        }while(opcion!='N');
        sc.close();
    }
    public static void turnoJugador(Scanner sc, Personaje pj, Enemigo jefe, Arma arma, Habilidad habilidadAtaque, Habilidad habilidadCurar){
        System.out.println("\nTurno de "+pj.getNombre());
        System.out.println("1) Atacar");
        System.out.println("2) Actuar");
        System.out.println("3) Defender");
        System.out.println("4) Escapar");
        System.out.print("Elige: ");
        char op = sc.next().charAt(0);
        switch(op){
            case '1':
                pj.atacar(jefe, arma);
                break;
            case '2':
                if(habilidadAtaque!=null){
                    habilidadAtaque.usar(pj, jefe);
                }else if(habilidadCurar!=null){
                    habilidadCurar.usar(pj, pj);
                }else{
                    pj.magia(jefe);
                }
                break;
            case '3':
                System.out.println(pj.getNombre()+" se defiende, recibe menos daño el próximo turno.");
                break;
            case '4':
                System.out.println(pj.getNombre()+" intenta escapar... pero no puede!");
                break;
            default:
                System.out.println("Opción inválida");
        }
    }
}