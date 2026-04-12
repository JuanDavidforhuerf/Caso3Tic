package threads;
import Main.Buzon;
import Main.Evento;
import java.util.Random;

public class ThreadBroker extends Thread{
    private Buzon buzonEntrada;
    private Buzon buzonAlertas;
    private Buzon buzonClasificacion;
    private int totalEventos; 
    private Random random = new Random();

    public ThreadBroker(Buzon buzonEntrada, Buzon buzonAlertas, Buzon buzonClasificacion, int totalEventos){
        this.buzonEntrada = buzonEntrada;
        this.buzonAlertas = buzonAlertas;
        this.buzonClasificacion = buzonClasificacion;
        this.totalEventos = totalEventos;
    }

    @Override
    public void run(){
        try{
            for(int i = 0; i<totalEventos; i++){
                 Evento evento = buzonEntrada.extraerEventoPasiva();
                 int numero = random.nextInt(201);
                 if (numero % 8 == 0) {  // evento anomalo
                    buzonAlertas.insertarEventoSemiactiva(evento);
                    System.out.println("Broker: evento " + evento.getId() + " sospechoso y enviado al buzon de alertas");
                }
                 else {
                    buzonClasificacion.insertarEventoSemiactiva(evento);
                    System.out.println("Broker: evento " + evento.getId() + " normal -> buzonClasificacion");
                }
            }
            buzonAlertas.insertarEventoSemiactiva(new Evento()); //evento FIN
            System.out.println("Broker: treabajo terminado, se envió evento de fin al Administrador");


            
        }
        catch(Exception ex){ex.printStackTrace();}
    }
}


