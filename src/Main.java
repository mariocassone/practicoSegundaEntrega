import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Ronda> rondas = new ArrayList<>();

        try {
            List<String> archivo = Files.readAllLines(Paths.get("src/Resultado"));
            
            for (int i = 0; i < archivo.size(); i++) {
                String[] linea = archivo.get(i).split(";");

                Partido p1 = new Partido();

                p1.equipo1 = linea[1];
                p1.golesEquipo1 = Integer.parseInt(linea[2]);
                p1.golesEquipo2 = Integer.parseInt(linea[3]);
                p1.equipo2 = linea[4];

                boolean bandera = false;

                for (int j = 0; j < rondas.size(); j++) {
                    if (rondas.get(j).numero.equals(linea[0])) {
                        rondas.get(j).partidos.add(p1);
                        bandera = true;
                    }
                }
                if (!bandera) {
                    Ronda ronda = new Ronda();
                    ronda.numero = linea[0];
                    ronda.partidos.add(p1);
                    rondas.add(ronda);
                }

            }

        } catch (IOException e) {
            System.out.println("Error 1");
        }

        List<Pronostico> pronosticos = new ArrayList<>();


        try {
            List<String> archivo = Files.readAllLines(Paths.get("src/Pronostico"));
            for (int i = 0; i < archivo.size(); i++) {
                String[] linea = archivo.get(i).split(";");

                Partido p1 = new Partido();

                p1.equipo1 = linea[1];
                p1.prediccion = Integer.parseInt(linea[2]);
                p1.equipo2 = linea[3];

                boolean bandera = false;
                for (int j = 0; j < pronosticos.size(); j++) {
                    if (pronosticos.get(j).persona.equals(linea[0])) {
                        pronosticos.get(j).partidos.add(p1);
                        bandera = true;
                    }
                }
                if (!bandera) {
                    Pronostico pronostico = new Pronostico(linea[0]);
                    pronostico.partidos.add(p1);
                    pronosticos.add(pronostico);
                }

            }

        } catch (IOException e) {
            System.out.println("Error 1");
        }

        for (int j = 0; j < pronosticos.size(); j++) {
            int suma = 0;
            for (int k = 0; k < rondas.size(); k++) {
                suma = suma + pronosticos.get(j).puntos(rondas.get(k).partidos);
            }
            System.out.println("Gano " + suma + " " + pronosticos.get(j).persona);
        }
    }
}