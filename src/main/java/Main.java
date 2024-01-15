import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static Connection ConectarBD(String bd){
        Connection conexion;
        String host="jdbc:mysql://localhost/";
        String user="root";
        String pass="";
       // String bd ="prueba";
        System.out.println("Conectando...");
        try{
            conexion= DriverManager.getConnection(host+bd,user,pass);
            System.out.println("Conexion Exitosa!!");

        }catch (SQLException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
        return conexion;
    }
    public static void Desconexion(Connection cb){
        try {
            cb.close();
            System.out.println("Desconectado!!");
        }catch (SQLException e){
            System.out.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        System.out.println("hola");
        Connection bd = ConectarBD("prueba");
        System.out.println("Consultas terminadas");
        Desconexion(bd);
    }
}
