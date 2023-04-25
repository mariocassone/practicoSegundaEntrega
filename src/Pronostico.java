import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pronostico {
    String persona;
    List<Partido> partidos;
    int puntosExtras;


    public Pronostico(String persona) {
        this.persona = persona;
        partidos = new ArrayList<>();
    }


    public int puntos(List<Partido> resultados) {

        int puntos = 0;

        for (int i = 0; i < resultados.size(); i++) {
            for (int j = 0; j < partidos.size(); j++)
                if (resultados.get(i).equipo1.equals(partidos.get(j).equipo1)
                        && resultados.get(i).equipo2.equals(partidos.get(j).equipo2)) {
                    if (partidos.get(j).prediccion == resultados.get(i).resultadoPartido()) {
                        puntos = puntos + 1;
                    }
                }
        }
        return puntos;
    }
//    boolean acerto = true;
//
//    public boolean acertoTodos(List<Partido> rondas) {
//        for (int i = 0; i < rondas.size(); i++) {
//            for (int j = 0; j < partidos.size(); j++)
//                if (rondas.get(i).equipo1.equals(partidos.get(j).equipo1)
//                        && rondas.get(i).equipo2.equals(partidos.get(j).equipo2)){
//                    if(partidos.get(j).prediccion == rondas.get(i).resultadoPartido()){
//                        acerto = true;
//                    } else {
//                        acerto = false;
//                    }
//                }
//            } return acerto;
    //}
}



