
package modelo;

import controlador.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Usuario
 */
public class ProductoDAO {

    //intancia de conexionbd
    ConexionBD conexion = new ConexionBD();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public List<Producto> listar() {
        String sql = "SELECT * FROM productos";
        List<Producto> lista = new ArrayList<>();
        try {
            con = conexion.conectarBaseDatos();
            if (con != null) {
                ps = con.prepareStatement(sql);
                rs = ps.executeQuery();

                while (rs.next()) {
                    Producto producto = new Producto();
                    producto.setCodigo(rs.getInt(1));
                    producto.setNombre(rs.getString(2));
                    producto.setPrecio(rs.getDouble(3));
                    producto.setInventario(rs.getInt(4));
                    lista.add(producto);
                }
            } else {
                System.out.println("La conexión es nula");
            }

        } catch (SQLException e) {
            System.out.println("Error al listar productos: " + e.getMessage());
        } finally {
            // Cerrar recursos
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (con != null) {
                    con.close();
                }
            } catch (SQLException e) {
                System.out.println("Error al cerrar recursos: " + e.getMessage());
            }
        }
        return lista;
    }

    //metodo para agregar
    public void agregar(Producto producto) {
        String sql = "insert into productos(nombre, precio,inventario) values (?,?,?)";
        try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getInventario());
            ps.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error en agregar " + e);
        }
    }

    //metodo actualizar
    public void actualizar(Producto producto) {
        String sql = "update productos set nombre= ?, precio=?, inventario=? where codigo=? ";
        try {
            con = conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            ps.setString(1, producto.getNombre());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getInventario());
            ps.setInt(4, producto.getCodigo());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en actualizar " + e);
        }
    }

    //metodo borrar
    public void borrar(int id){
        String sql= "delete from productos where codigo=" +id;
        try {
            con=conexion.conectarBaseDatos();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error en borrar" +e);
        }
           
    }
}
