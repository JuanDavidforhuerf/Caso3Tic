package Main;
import java.io.*;
import java.util.Properties;
import threads.*;

public class Main {
     public static void main(String[] args) throws Exception {

        //extraccion datos del archivo txt
        Properties config = new Properties();
        config.load(new FileInputStream("src/Data/prueba1.txt"));
        int ni = Integer.parseInt(config.getProperty("ni"));
        int numBase = Integer.parseInt(config.getProperty("numBase"));
        int nc = Integer.parseInt(config.getProperty("nc"));
        int ns = Integer.parseInt(config.getProperty("ns"));
        int tam1 = Integer.parseInt(config.getProperty("tam1"));
        int tam2 = Integer.parseInt(config.getProperty("tam2"));
        int totalEventos = 0;
        for (int i = 1; i <= ni; i++) {
            totalEventos += numBase * i;
        }


        Buzon buzonEntrada = new Buzon(-1);
        Buzon buzonAlertas = new Buzon(-1);
        Buzon buzonClasificacion = new Buzon(tam1);
        Buzon[] buzonesServidores = new Buzon[ns];
        for (int i = 0; i < ns; i++) {
            buzonesServidores[i] = new Buzon(tam2);
        }
        //inicio sensores
        ThreadSensor[] sensores = new ThreadSensor[ni];
        for (int i = 0; i < ni; i++) {
            sensores[i] = new ThreadSensor(i + 1, numBase * (i + 1), ns, buzonEntrada);
            sensores[i].start();
        }

        //iniciar broker
        ThreadBroker broker = new ThreadBroker(buzonEntrada, buzonAlertas, buzonClasificacion, totalEventos);
        broker.start();

        //iniciar administrador
        ThreadAdministrator administrador = new ThreadAdministrator(buzonAlertas, buzonClasificacion, nc);
        administrador.start();

        //iniciar clasificadores
        ThreadClasificator[] clasificadores = new ThreadClasificator[nc];
        for (int i = 0; i < nc; i++) {
            clasificadores[i] = new ThreadClasificator(buzonClasificacion, buzonesServidores, ns, nc);
            clasificadores[i].start();
        }

        //iniciar servidores
        ThreadServidor[] servidores = new ThreadServidor[ns];
        for (int i = 0; i < ns; i++) {
            servidores[i] = new ThreadServidor(i + 1, buzonesServidores[i]);
            servidores[i].start();
        }

        for (ThreadSensor s : sensores){ 
            s.join();}
        System.out.println("Todos los sensores terminaron.");

        broker.join();
        System.out.println("Broker terminó.");

        administrador.join();
        System.out.println("Administrador terminó.");

        for (ThreadClasificator c : clasificadores){
            c.join();}
        System.out.println("Todos los clasificadores terminaron.");

        for (ThreadServidor s : servidores) s.join();
        System.out.println("Todos los servidores terminaron.");

        System.out.println("Sistema terminado. Todos los buzones deben estar vacios.");

     }
}
