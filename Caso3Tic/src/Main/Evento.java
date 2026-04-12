package Main;

public class Evento {
    private String id;  
    private int tipoServidor;
    private boolean esEventoFin;
    

    public Evento(String id, int tipoServidor){
        this.id = id;
        this.tipoServidor = tipoServidor;
        esEventoFin = false;
    }

    public Evento(){
        this.id = "FIN";
        this.tipoServidor = -1;
        this.esEventoFin = true;
    }

    public String getId(){
        return id;
    }
    public int getNumeroServidor(){
        return tipoServidor;
    }
    public boolean esFinal(){
        return esEventoFin;
    }


}
