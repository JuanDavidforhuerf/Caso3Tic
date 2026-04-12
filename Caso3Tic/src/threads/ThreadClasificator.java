package threads;
import Main.Buzon;
import Main.Evento;

public class ThreadClasificator extends Thread{
    private Buzon buzonClasificacion;
    private Buzon[] buzonesServidores; // un buzon por servidor
    private int numeroServidores;
    private static int clasificadoresActivos;

    public ThreadClasificator(Buzon buzonClasificacion, Buzon[] buzonesConsolidacion, int ns, int nc) {
        this.buzonClasificacion = buzonClasificacion;
        this.buzonesServidores = buzonesConsolidacion;
        this.numeroServidores = ns;
        clasificadoresActivos = nc;
    }

    @Override
    public void run(){
        try{
            boolean terminar = false;
            while(!terminar){
                Evento evento = buzonClasificacion.extraerEventoPasiva();
                if (evento.esFinal()) {
                    terminar = true;
                }
                else {
                    int destino = evento.getNumeroServidor() - 1;
                    buzonesServidores[destino].insertarEventoPasiva(evento);
                    System.out.println("Clasificador: evento " + evento.getId() + " enviado al servidor " + (destino + 1));
                }
            }
            synchronized (ThreadClasificator.class) {
                clasificadoresActivos--;
                 if (clasificadoresActivos == 0) {
                    for (int i = 0; i < numeroServidores; i++) {
                        buzonesServidores[i].insertarEventoPasiva(new Evento());
                    }
                    System.out.println("Ultimo clasificador: envió " + numeroServidores + " eventos de fin a los servidores.");
                }
            }
            System.out.println("Clasificador terminó.");
        }
        catch(Exception ex){ex.printStackTrace();}

    }

}
