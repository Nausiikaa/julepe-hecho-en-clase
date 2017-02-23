import java.util.Random;
import java.util.ArrayList;
/**
 * Write a description of class Jugador here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Jugador
{
    private String nombre;
    private Carta[] cartasQueTieneEnLaMano;
    private int numeroCartasEnLaMano;
    private ArrayList<Baza> bazasGanadas;

    /**
     * Constructor for objects of class Jugador
     */
    public Jugador(String nombreJugador)
    {
        nombre = nombreJugador;
        cartasQueTieneEnLaMano = new Carta[5];
        numeroCartasEnLaMano = 0;   
        bazasGanadas = new ArrayList<Baza>();
    }

    /**
     * Metodo que hace que el jugador reciba una carta
     */
    public void recibirCarta(Carta cartaRecibida)
    {
        if (numeroCartasEnLaMano < 5) {
            cartasQueTieneEnLaMano[numeroCartasEnLaMano] = cartaRecibida;
            numeroCartasEnLaMano++;
        }
    }

    /**
     * Metodo que muestra las cartas del jugador por pantalla
     */
    public void verCartasJugador()
    {
        for (Carta cartaActual : cartasQueTieneEnLaMano) {
            if (cartaActual != null) {
                System.out.println(cartaActual);
            }
        }
    }

    /**
     * Metodo que devuelve el nombre del jugador
     */
    public String getNombre()
    {
        return nombre;
    }

    /**
     * Metodo que devuelve la carta especificada como parametro si
     * el jugador dispone de ella y simula que se lanza a la mesa
     */    
    public Carta tirarCarta(String nombreCarta)
    {
        Carta cartaTirada = null;

        if (numeroCartasEnLaMano > 0) {

            int cartaActual = 0;
            boolean buscando = true;
            while (cartaActual < cartasQueTieneEnLaMano.length && buscando) {
                if (cartasQueTieneEnLaMano[cartaActual] != null) {                                 
                    if (nombreCarta.equals(cartasQueTieneEnLaMano[cartaActual].toString())) {
                        buscando = false;
                        cartaTirada = cartasQueTieneEnLaMano[cartaActual];
                        cartasQueTieneEnLaMano[cartaActual] = null;
                        numeroCartasEnLaMano--;
                        System.out.println(nombre + " ha tirado " + cartaTirada);
                    }
                }
                cartaActual++;
            }

            
        }

        return cartaTirada;
    }

    
    /**
     * Método que tira una carta aleatoria 
     */
    public Carta tirarCartaAleatoria() 
    {
        Carta cartaTirada = null;

        if (numeroCartasEnLaMano > 0) {
            Random aleatorio = new Random();

            boolean elJugadorHaTiradoUnaCarta = false;
            while (elJugadorHaTiradoUnaCarta == false) {
                int posicionAleatoria = aleatorio.nextInt(5);
                if (cartasQueTieneEnLaMano[posicionAleatoria] != null) {
                    cartaTirada = cartasQueTieneEnLaMano[posicionAleatoria];
                    cartasQueTieneEnLaMano[posicionAleatoria] = null;
                    numeroCartasEnLaMano--;
                    System.out.println(nombre + " ha tirado " + cartaTirada);
                    elJugadorHaTiradoUnaCarta = true;
                }
            }

        }

        return cartaTirada;
    }
    
    /**
     * Método que tira una carta "inteligentemente"
     */
    public Carta tirarCartaInteligentemente(Palo paloPrimeraCartaDeLaBaza, 
    Carta cartaQueVaGanando,
    Palo paloQuePinta)
    {
        Carta miCarta  = null;
        boolean gana = false;
        int posicion = 0;
        // Recorremos todas las cartas de la mano
        while(posicion < cartasQueTieneEnLaMano.length && gana == false){
            // Vamos guardando la carta actual que vamos a ver, y comprobamos que haya una carta que ver
            miCarta = cartasQueTieneEnLaMano[posicion];
            if(miCarta != null){
                // Comprobamos que el palo de la carta almacenada coincida con el de la primera carta de la baza(ASISTIR)
                if(miCarta.getPalo() == paloPrimeraCartaDeLaBaza) {
                    // Si asistimos, comprobamos que la carta sea mayor para poder ganarla. En caso contrario lanzariamos la carta para asistir pero sin ganar
                    if(miCarta.getPosicionEscalaTute() > cartaQueVaGanando.getPosicionEscalaTute()){
                        gana = true;
                    } 
                    else{
                        gana = true;
                    }
                }
            }
            posicion++;
        }
        //Si hemos recorrido la mano entera, y no encontramos cartas con las que asistir.
        if(posicion == cartasQueTieneEnLaMano.length){
            // Dado que no podemos asistir comprobaremos si alguna de nuestras cartas vale para ganar la baza, aunque sea fallando
            int index = 0;
            while(index < cartasQueTieneEnLaMano.length && gana == false){
                miCarta = cartasQueTieneEnLaMano[index];
                //Si encontramos un fallo tiraremos esa carta, con la intencion de ganar la baza
                if (miCarta != null && miCarta.ganaA(cartaQueVaGanando, paloQuePinta)) {
                    gana = true;
                }   
                index++;
            }
            //Llegados a este punto, no tenemos ni cartas para asistir ni cartas para fallar asique lanzamos una aleatoria.
            if(gana == false && index == cartasQueTieneEnLaMano.length) {
                miCarta = tirarCartaAleatoria();
            }
        }

        tirarCarta(miCarta.toString());
        return miCarta;

    }

    /**
     * Metodo que hace que jugador recoja una baza ganada
     */
    public void addBaza(Baza bazaGanada)
    {
        bazasGanadas.add(bazaGanada);
    }

    /**
     * Metodo que devuelve el numero de bazas ganadas por el jugador hasta
     * el momento
     */
    public int getNumeroBazasGanadas() 
    {
        return bazasGanadas.size();
    }

    
}














