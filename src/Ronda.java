import java.util.ArrayList;
import java.util.List;

public class Ronda {
    String nombreRonda;
    String resultado;
    List<Partido> partidos;
    String fase;
    Ronda ronda;

    public Ronda(Ronda ronda, String resultado, List<Partido> partidos, String fase) {
        this.nombreRonda = ronda.nombreRonda;
        this.resultado = resultado;
        this.partidos = partidos;
        this.fase = fase;
    }

    public Ronda() {
        partidos = new ArrayList<>();
    }


    public String resultadoDelPartido(Partido p1){
        if(p1 != null) {
            if (p1.golesEquipo1 > p1.golesEquipo2) {
                resultado = "1";
            } else if (p1.golesEquipo2 > p1.golesEquipo1) {
                resultado = "2";
            } else {
                resultado = "0";
            }
        }return resultado;
    }
    public String resultadoDelPartido(){
        for(Partido p1 : partidos) {
            if(p1 != null) {
                if (p1.golesEquipo1 > p1.golesEquipo2) {
                    resultado = p1.equipo1;
                } else if (p1.golesEquipo2 > p1.golesEquipo1) {
                    resultado = p1.equipo2;
                } else {
                    resultado = "Empate";
                }
            }
        }return resultado;
    }
}


