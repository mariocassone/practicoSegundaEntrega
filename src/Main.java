import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;


public class Main {
    public static void main(String[] args) {

        List<Ronda> rondas = new ArrayList<>();

        try {
            // List<String> archivo = Files.readAllLines(Paths.get("src/Resultado"));
            List<String[]> resultados = leerResultados();

            for (int i = 0; i < resultados.size(); i++) {
                String[] linea = resultados.get(i);

                Partido p1 = new Partido();
                p1.fase = linea[0];
                p1.nomRonda = linea[1];
                p1.equipo1 = linea[2];
                p1.golesEquipo1 = Integer.parseInt(linea[4]);
                p1.golesEquipo2 = Integer.parseInt(linea[5]);
                p1.equipo2 = linea[3];

                boolean bandera = false;

                for (int j = 0; j < rondas.size(); j++) {
                    if (rondas.get(j).nombreRonda.equals(linea[1])) {
                        rondas.get(j).partidos.add(p1);
                        bandera = true;
                    }
                }
                if (!bandera) {
                    Ronda ronda = new Ronda();
                    ronda.nombreRonda = linea[1];
                    ronda.partidos.add(p1);
                    rondas.add(ronda);
                }

            }

        } catch (Exception e) {
            System.out.println("Error leer partidos");
        }

        List<Pronostico> pronosticos = new ArrayList<>();

        try {
            // List<String> archivo = Files.readAllLines(Paths.get("src/Pronostico"));
            List<String[]> listaPronos = leerPronosticos();


            for (int i = 0; i < listaPronos.size(); i++) {
                String[] linea = listaPronos.get(i);
                Partido p1 = new Partido();

                p1.fase = linea[1];
                p1.nomRonda = linea[2];
                p1.equipo1 = linea[3];
                p1.prediccion = Integer.parseInt(linea[5]);
                p1.equipo2 = linea[4];

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

        } catch (Exception e) {
            System.out.println("Error leer pronosticos");
        }

        int puntoPorGanar = 0;
        int puntosPorRonda = 0;

        try {
            for (String linea : Files.readAllLines(Paths.get("src/puntosExtras"))) {
                if (linea != null) {
                    String[] datos = linea.split(";");
                    puntoPorGanar = Integer.parseInt(datos[0]);
                    puntosPorRonda = Integer.parseInt(datos[1]);
                }
            }

        } catch (Exception e) {
            System.out.println("Error de lectura archivo");
        }


        for (int j = 0; j < pronosticos.size(); j++) {
            int aciertos = 0;
            int suma = 0;
            int total = 0;
            int totalTotal = 0;
            int rondasAcertadas = 0;


            for (int k = 0; k < rondas.size(); k++) {
                int acertadas = pronosticos.get(j).puntos(rondas.get(k).partidos);
                if (acertadas == rondas.get(k).partidos.size()) {
                    rondasAcertadas += 1;
                }
                aciertos = aciertos + pronosticos.get(j).puntos(rondas.get(k).partidos);
                suma = suma + (puntoPorGanar * pronosticos.get(j).puntos(rondas.get(k).partidos));
                total = total + (puntosPorRonda * rondasAcertadas);
                totalTotal = suma + total;
            }
            System.out.println(pronosticos.get(j).persona + ": " + aciertos + " aciertos.");
            System.out.println(pronosticos.get(j).persona + ": " + suma + " puntos por aciertos.");
            System.out.println(pronosticos.get(j).persona + ": " + rondasAcertadas + " rondas acertadas.");
            System.out.println(pronosticos.get(j).persona + ": " + total + " puntos por rondas.");
            System.out.println(pronosticos.get(j).persona + ": " + totalTotal + " puntos totales.\n");

        }


        List<String[]> resultados = leerResultados();
        List<String[]> pronosticos2 = leerPronosticos();

//        System.out.println("Fase\tRonda\tNombre equipo 1\tNombre equipo 2\tGoles equipo 1\tGoles equipo 2");
//        for (String[] esteResultado : resultados) {
//            System.out.println(esteResultado[0] + "\t" + esteResultado[1] + "\t" + esteResultado[2] + "\t" + esteResultado[3] + "\t" + esteResultado[4] + "\t" + esteResultado[5]);
//        }
//
//        System.out.println("\n\nNombre persona\tFase\tRonda\tNombre equipo 1\tNombre equipo 2\tGanador");
//        for (String[] estepronosticos : pronosticos2) {
//            System.out.println(estepronosticos[0] + "\t" + estepronosticos[1] + "\t" + estepronosticos[2] + "\t" + estepronosticos[3] + "\t" + estepronosticos[4] + "\t" + estepronosticos[5]);
//        }

    }


    // Va a devolver una Lista con un arreglo de String que va a contener:
    // Posicion 0: Ronda
    // Posicion 1: Fase
    // Posicion 2: Nombre equipo 1
    // Posicion 3: Nombre equipo 2
    // Posicion 4: Goles equipo 1
    // Posicion 5: Goles equipo 2
    public static List<String[]> leerResultados() {
        List<String[]> resultados = new ArrayList<>();

        // Cargamos el Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error cargando el driver");
        }

        try {
            // Creamos la conexión
            Connection con = DriverManager.getConnection("jdbc:mysql://sql10.freemysqlhosting.net:3306/sql10612293", "sql10612293", "ACwUKDKvbY");
            Statement stmt = con.createStatement();

            // El Query que vamos a correr
            ResultSet rs = stmt.executeQuery("SELECT FASE, RONDA, E1.EQUIPO AS EQUIPO_1, E2.EQUIPO AS EQUIPO_2, GOLES_1, GOLES_2 FROM RESULTADOS R JOIN EQUIPOS E1 on R.ID_EQUIPO_1 = E1.ID_EQUIPO JOIN EQUIPOS E2 on R.ID_EQUIPO_2 = E2.ID_EQUIPO");
            while (rs.next()) {
                String[] fila = new String[6];
                fila[0] = rs.getString("FASE");
                fila[1] = rs.getString("RONDA");
                fila[2] = rs.getString("EQUIPO_1");
                fila[3] = rs.getString("EQUIPO_2");
                fila[4] = rs.getString("GOLES_1");
                fila[5] = rs.getString("GOLES_2");
                resultados.add(fila);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error con SQL");
        }

        return resultados;
    }

    // Va a devolver una Lista con un arreglo de String que va a contener:
    // Posicion 0: Nombre de la persona
    // Posicion 1: Fase
    // Posicion 2: Ronda
    // Posicion 3: Nombre equipo 1
    // Posicion 4: Nombre equipo 2
    // Posicion 5: Ganador
    public static List<String[]> leerPronosticos() {
        List<String[]> pronosticos = new ArrayList<>();

        // Cargamos el Driver
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error cargando el driver");
        }

        try {
            // Creamos la conexión
            Connection con = DriverManager.getConnection("jdbc:mysql://sql10.freemysqlhosting.net:3306/sql10612293", "sql10612293", "ACwUKDKvbY");
            Statement stmt = con.createStatement();

            // El Query que vamos a correr
            ResultSet rs = stmt.executeQuery("SELECT NOMBRE, FASE, RONDA, E1.EQUIPO AS EQUIPO_1, E2.EQUIPO AS EQUIPO_2, GANADOR FROM PRONOSTICOS P JOIN RESULTADOS R on P.ID_RESULTADO = R.ID_RESULTADO JOIN EQUIPOS E1 on R.ID_EQUIPO_1 = E1.ID_EQUIPO JOIN EQUIPOS E2 on R.ID_EQUIPO_2 = E2.ID_EQUIPO");
            while (rs.next()) {
                String[] fila = new String[6];
                fila[0] = rs.getString("NOMBRE");
                fila[1] = rs.getString("FASE");
                fila[2] = rs.getString("RONDA");
                fila[3] = rs.getString("EQUIPO_1");
                fila[4] = rs.getString("EQUIPO_2");
                fila[5] = rs.getString("GANADOR");
                pronosticos.add(fila);
            }
            con.close();
        } catch (SQLException e) {
            System.out.println("Error con SQL");
        }

        return pronosticos;
    }
}
