
package controlador;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import modelo.Producto;
import modelo.ProductoDAO;
import vista.Interfaz;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Usuario
 */
public class ControladorProducto implements ActionListener {

    //instancias
    Producto producto = new Producto();
    ProductoDAO productodao = new ProductoDAO();
    Interfaz vista = new Interfaz();
    DefaultTableModel modeloTabla = new DefaultTableModel();

    //variables globales
    private int codigo=0;
    private String nombre;
    private double precio;
    private int inventario;

    public ControladorProducto(Interfaz vista) {
        this.vista = vista;
        vista.setVisible(true);
        AgregarEventos();
        listarTabla();
    }

    private void AgregarEventos() {
        vista.getBtnAgregar().addActionListener(this);
        vista.getBtnActualizar().addActionListener(this);
        vista.getBtnBorrar().addActionListener(this);
        vista.getBtnLimpiar().addActionListener(this);
        vista.getTblTabla().addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                llenarCampos(e);
            }
        });
    }

    private void listarTabla() {
        String titulos[] = new String[]{"codigo", "nombre", "precio", "inventario"};
        modeloTabla = new DefaultTableModel(titulos, 0);
        List<Producto> listaProductos = productodao.listar();
        for (Producto producto : listaProductos) {
            modeloTabla.addRow(new Object[]{producto.getCodigo(), producto.getNombre(), producto.getPrecio(), producto.getInventario()});
        }
        vista.getTblTabla().setModel(modeloTabla);
        vista.getTblTabla().setPreferredSize(new Dimension(350, modeloTabla.getRowCount() * 16));
    }

    private void llenarCampos(MouseEvent e) {
        JTable target = (JTable) e.getSource();
        codigo=(int) vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 0);
        vista.getTxtNombre().setText(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 1).toString());
        vista.getTxtPrecio().setText(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 2).toString());
        vista.getTxtInventario().setText(vista.getTblTabla().getModel().getValueAt(target.getSelectedRow(), 3).toString());
    }

    private boolean validarDatos() {
        if ("".equals(vista.getTxtNombre().getText()) || "".equals(vista.getTxtPrecio().getText()) || "".equals(vista.getTxtInventario().getText())) {
            JOptionPane.showMessageDialog(null, "Los campos no pueden ser vacios", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private boolean cargarDatos() {
        try {
            nombre = vista.getTxtNombre().getText();
            precio = Double.parseDouble(vista.getTxtPrecio().getText());
            inventario = Integer.parseInt(vista.getTxtInventario().getText());
            return true;
        } catch (NumberFormatException e) {
            System.out.println("Error al cargar los datos" + e);
            JOptionPane.showMessageDialog(null, "Los campos precio e inventario deben ser numericos.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    private void limpiarCampos() {
        vista.getTxtNombre().setText("");
        vista.getTxtPrecio().setText("");
        vista.getTxtInventario().setText("");

        codigo = 0;
        nombre = "";
        precio = 0;
        inventario = 0;

    }

    private void agregarProducto() {
        try {
            if (validarDatos()) {
                if (cargarDatos()) {
                    Producto producto = new Producto(nombre, precio, inventario);
                    productodao.agregar(producto);
                    JOptionPane.showMessageDialog(null, "Registro Exitoso!");
                    limpiarCampos();
                }
            }
        } catch (Exception e) {
            System.out.println("Error agregarC" + e);
        } finally {
            listarTabla();
        }

    }
    
    private void actualizaProducto(){
        try {
            if(validarDatos()){
                if(cargarDatos()){
                    Producto producto = new Producto(codigo, nombre, precio, inventario);
                    productodao.actualizar(producto);
                     JOptionPane.showMessageDialog(null, "Registro Actualizado!");
                    limpiarCampos();
                }
            }
        } catch (HeadlessException e) {
            System.out.println("Error al actualizar " +e);
        }finally{
            listarTabla();
        }
          
    }
    
    
    private void borrarProducto(){
        try {
            if(codigo!=0){
                productodao.borrar(codigo);
                  JOptionPane.showMessageDialog(null, "Registro Borrado");
                  limpiarCampos();
            }else{
                JOptionPane.showMessageDialog(null, "Debe seleccionar un prodcuto de la tabla.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (HeadlessException e) {
            
            System.out.println("Error al borrar " +e);
        }finally{
            listarTabla();
        }
    }
    

//dar acciones a los botones
    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == vista.getBtnAgregar()) {
            agregarProducto();

        }
            if (ae.getSource() == vista.getBtnActualizar()){
                actualizaProducto();
            }
            
              if (ae.getSource() == vista.getBtnBorrar()){
                borrarProducto();
            }
              if (ae.getSource() == vista.getBtnLimpiar()){
              limpiarCampos();
            }
    }

}
