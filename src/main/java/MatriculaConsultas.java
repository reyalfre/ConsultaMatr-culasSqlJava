import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MatriculaConsultas {

    public static void main(String[] args) {
        // Configuración de la conexión, importante ponerlo como lo tengáis en vuestra base de datos, en mi caso no quise ponerle contraseña y el usuario es root
        String url = "jdbc:mysql://localhost:3306/matricula";
        String usuario = "root";
        String contraseña = "";
        System.out.println("Conectando...");

        try {
            // Establecer conexión
            Connection conexion = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta 1: Nombre de los alumnos matriculados en "AMPLIACIÓN DE SISTEMAS OPERATIVOS"
            System.out.println("Consulta 1: Alumnos matriculados en 'AMPLIACIÓN DE SISTEMAS OPERATIVOS'");
            consultarAlumnosPorAsignatura(conexion, "AMPLIACIÓN DE SISTEMAS OPERATIVOS");

            // Consulta 2: Asignaturas con el mayor número de créditos
            System.out.println("\nConsulta 2: Asignaturas con el mayor número de créditos");
            consultarAsignaturasConMayorCreditos(conexion);

            // Consulta 3: Asignaturas del estudiante cuyo nombre comienza por C
            System.out.println("\nConsulta 3: Asignaturas del estudiante cuyo nombre comienza por C");
            consultarAsignaturasDeEstudiantePorNombre(conexion, "C");

            // Cerrar conexión
            conexion.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Método para consultar alumnos matriculados en una asignatura
     * @param conexion parámetro de la conexión
     * @param nombreAsignatura parámetro de las asignaturas
     * @throws SQLException evita el fallo de SQL
     */
    private static void consultarAlumnosPorAsignatura(Connection conexion, String nombreAsignatura) throws SQLException {
        String consulta = "SELECT a.nombre FROM alumnos a " +
                "JOIN alumnos_asignaturas aa ON a.id_alumno = aa.id_alumno " +
                "JOIN asignaturas asi ON aa.id_asignatura = asi.id_asignatura " +
                "WHERE asi.nombre = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(consulta)) {
            preparedStatement.setString(1, nombreAsignatura);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("nombre"));
            }
        }
    }

    /**
     * Método para consultar asignaturas con el mayor número de créditos
     * @param conexion parámetro de la conexión
     * @throws SQLException evita el fallo de sql
     */
    private static void consultarAsignaturasConMayorCreditos(Connection conexion) throws SQLException {
        String consulta = "SELECT * FROM asignaturas WHERE creditos = (SELECT MAX(creditos) FROM asignaturas)";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(consulta)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("nombre"));
            }
        }
    }

    /**
     * Método para consultar asignaturas de un estudiante cuyo nombre comienza por la letra c.
     * @param conexion parámetro de la conexión
     * @param letraInicioNombre parámetro de la letra inicial que será la c.
     * @throws SQLException evita el fallo de sql.
     */
    private static void consultarAsignaturasDeEstudiantePorNombre(Connection conexion, String letraInicioNombre) throws SQLException {
        String consulta = "SELECT asi.nombre FROM asignaturas asi " +
                "JOIN alumnos_asignaturas aa ON asi.id_asignatura = aa.id_asignatura " +
                "JOIN alumnos a ON aa.id_alumno = a.id_alumno " +
                "WHERE SUBSTRING(a.nombre, 1, 1) = ?";

        try (PreparedStatement preparedStatement = conexion.prepareStatement(consulta)) {
            preparedStatement.setString(1, letraInicioNombre);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("nombre"));
            }
        }
    }
}
