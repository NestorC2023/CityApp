
package controlador;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author Usuario
 */
public class ConexionBD {
    Connection con;

        String driver = "com.mysql.cj.jdbc.Driver";
        String dbName= "productos";
        String url= "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false&serverTimezone=UTC";
                
        String usuario= "root";
        String contraseña= "";
                
                public Connection conectarBaseDatos(){
                    try {
                        Class.forName(driver);
                        con=DriverManager.getConnection(url,usuario,contraseña);
                        System.out.println("Conexion exitosa");
                    } catch (Exception e) {
                        System.out.println("Error " +e);
                    }
                return con;
                }
}
