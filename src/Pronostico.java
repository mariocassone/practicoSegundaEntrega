import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pronostico {
    String persona;
    List<Partido> partidos;
    int puntosExtras;

    public Pronostico(String persona){
        this.persona = persona;
        partidos = new ArrayList<>();
    }


    public int puntos(List<Partido> resultados) {
        int puntos = 0;
        int contadorRonda = 0;
            for (int i = 0; i < resultados.size(); i++) {
            for (int j = 0; j < partidos.size(); j++) {
                if(resultados.get(i).nomRonda.equals(partidos.get(j).nomRonda)){
                    if(resultados.get(i).equipo1.equals(partidos.get(j).equipo1)
                        && resultados.get(i).equipo2.equals(partidos.get(j).equipo2)) {
                    if (partidos.get(j).prediccion == resultados.get(i).resultadoPartido()) {
                        puntos = puntos + 1;
                        contadorRonda += contadorRonda;
                    }
                }
            }
            }

        }return puntos;
        }

    public int contadorRondas(List<Partido> resultados) {
        int contadorRondas = 0;
        Set<String> rondasAcertadas = new HashSet<String>();
        for (Partido resultado : resultados) {
            boolean acertadosTodos = true;
            for (Partido partido : partidos) {
                if (resultado.nomRonda.equals(partido.nomRonda)
                        && resultado.equipo1.equals(partido.equipo1)
                        && resultado.equipo2.equals(partido.equipo2)) {
                    if (partido.prediccion != resultado.resultadoPartido()) {
                        acertadosTodos = false;
                    }
                }
            }
            if (acertadosTodos && !rondasAcertadas.contains(resultado.nomRonda)) {
                rondasAcertadas.add(resultado.nomRonda);
                contadorRondas++;
            }
        }
        return contadorRondas;
    }

}




