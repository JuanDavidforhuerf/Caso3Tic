package threads;
import Main.Buzon;
import Main.Evento;
import java.util.Random;

public class ThreadAdministrator extends Thread {
    private Buzon buzonAlertas;
    private Buzon buzonClasificacion;
    private int numeroClasificadores;
    private Random random = new Random();

    public ThreadAdministrator(Buzon buzonAlertas, Buzon buzonClasificacion, int nc) {
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
        this.numeroClasificadores = nc;
    }

    @Override
    public void run(){
        try {
            boolean terminar = false;
            while(!terminar){
                Evento evento = buzonAlertas.extraerEventoSemiactiva();
                if (evento.esFinal()) {
                    terminar = true;
                }
                else{
                    int numero = random.nextInt(21); 
                    if (numero % 4 == 0) {// inofensivo
                        buzonClasificacion.insertarEventoSemiactiva(evento);
                        System.out.println("Administrador: evento " + evento.getId() + " inofensivo y enviado al buzon de Clasificacion");
                    } 
                    else {// malicioso
                        System.out.println("Administrador: evento " + evento.getId() + " malicioso y descartado");
                    }
                }
            }
            for (int i = 0; i < numeroClasificadores; i++) {
                buzonClasificacion.insertarEventoSemiactiva(new Evento());
            }
            System.out.println("Administrador: terminó, envió " + numeroClasificadores + " eventos de fin a clasificadores.");
        } 
        catch(Exception ex) { ex.printStackTrace();}
    }
    
}
