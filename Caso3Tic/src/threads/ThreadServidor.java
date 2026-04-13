package threads;
import Main.Buzon;
import Main.Evento;
import java.util.Random;

public class ThreadServidor extends Thread{
    private int id;
    private Buzon buzonConsolidacion;
    private Random random = new Random();


    public ThreadServidor(int id, Buzon buzonConsolidacion) {
        this.id = id;
        this.buzonConsolidacion = buzonConsolidacion;
    }

    @Override
    public void run(){
        try {
            boolean terminar = false;
            while (!terminar) {
                Evento evento = buzonConsolidacion.extraerEventoPasiva();
                if (evento.esFinal()) {
                    terminar = true;
                } else {
                    int tiempoProcesamiento = random.nextInt(901) + 100;
                    System.out.println("Servidor " + id + ": procesando evento " + evento.getId() + " durante " + tiempoProcesamiento + "ms");
                    Thread.sleep(tiempoProcesamiento);
                    System.out.println("Servidor " + id + ": evento " + evento.getId() + " procesado");
                }
            }
            System.out.println("Servidor " + id + ": terminó.");
        } 
        catch(Exception ex) {ex.printStackTrace();
        }
    }

}
