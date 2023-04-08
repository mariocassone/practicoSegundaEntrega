import java.util.ArrayList;
import java.util.List;

public class Pronostico {
    String persona;
    List<Partido> partidos;

    public Pronostico(String persona){
        this.persona = persona;
        partidos = new ArrayList<>();
    }

    public int puntos(List<Partido> resultados){
        int puntos = 0;
        for(int i = 0; i < resultados.size(); i++){
            for(int j = 0; j < partidos.size(); j++){
                if(resultados.get(i).equipo1.equals(partidos.get(j).equipo1)
                && resultados.get(i).equipo2.equals(partidos.get(j).equipo2)){
                    if(partidos.get(j).prediccion == resultados.get(i).resultadoPartido()){
                        puntos = puntos + 1;
                    }
                }
            }
        } return puntos;
    }


}
