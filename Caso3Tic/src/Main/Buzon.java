package Main;
import java.util.ArrayList;

public class Buzon {
    private ArrayList<Evento> colaEventos;
    private int capacidad; //se utiliza -1 para representar capacidad ilimitada
    
    public Buzon(int capacidad){
        this.colaEventos = new ArrayList<Evento>();
        this.capacidad = capacidad;
    }

    public synchronized int tamanio(){
        return colaEventos.size();
    } 
    public synchronized boolean estaVacio(){
        return colaEventos.isEmpty();
    }

    public synchronized boolean puedeInsertar(){
        if(capacidad ==-1 ||  colaEventos.size() < capacidad){
            return true;
        }
        else{
            return false;
        }
    }

    //INSERT ESPERA PASIVA
    public synchronized void insertarEventoPasiva(Evento evento)throws InterruptedException{
        while (capacidad != -1 && colaEventos.size() >= capacidad){
            wait();
        }
        colaEventos.add(evento);
        notifyAll();
    }

    // INSERTAR ESPERA SEMI ACTIVA
    public void insertarEventoSemiactiva(Evento evento){
        while (capacidad != -1 && colaEventos.size() >= capacidad){
            Thread.yield();
        }
        synchronized(this){
            colaEventos.add(evento);
            notifyAll();
        }
    }

    //EXTRAER ESPERA PASIVA
    public synchronized Evento extraerEventoPasiva() throws InterruptedException{
        while(colaEventos.isEmpty()){
            wait();
        }
        Evento evento = colaEventos.remove(0);
        notifyAll();
        return evento;
    }

    //EXTRAER ESPERA SEMI ACTIVA
    public Evento extraerEventoSemiactiva() {
        while(colaEventos.isEmpty()){
            Thread.yield();
        }
        synchronized(this){
        Evento evento = colaEventos.remove(0);
        notifyAll();
        return evento;
        }
    }


}
