package threads;
import Main.Buzon;
import Main.Evento;
import java.util.Random;

public class ThreadSensor extends Thread{
    private int id;
    private int numEventos;
    private int ns;
    private Buzon buzonEntrada;

    public ThreadSensor(int id, int numEventos, int ns, Buzon buzon){
        this.id = id;
        this.numEventos = numEventos;
        this.ns = ns;
        this.buzonEntrada = buzon;
    }

    @Override
    public void run() {
        try{
            Random random = new Random();
            for(int i = 1; i<= numEventos; i++){
                String idEvento = id + "-" + i;
                int tipoServidor = random.nextInt(1,ns+1);
                Evento eventoNuevo = new Evento(idEvento, tipoServidor);
                while(!buzonEntrada.puedeInsertar()){
                   Thread.yield(); 
                }
                buzonEntrada.insertarEventoSemiactiva(eventoNuevo);
                System.out.println("sensor: "+ id + "genero  el evento " + eventoNuevo.getId());
            }
        }

        catch(Exception ex){ ex.printStackTrace();}




        
        System.out.println("Sensor " + id+ ": Terminó la creacion de sus eventos.");
    }
}
