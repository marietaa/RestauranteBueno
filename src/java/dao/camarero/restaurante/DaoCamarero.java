package dao.camarero.restaurante;

import entidades.Camarero;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import utilidades.Conexion;
import utilidades.ExcepcionesBD;

public class DaoCamarero {

    public static void insertarCamarero(int idCamarero, String nombre, String apellido, String especialidad) throws SQLException, ClassNotFoundException {

        Connection conexion = Conexion.abrirConexion();
        Statement sentencia = conexion.createStatement();
        String consultaSQL = "insert into camarero (idCamarero,nombre,apellido,especialidad) values ";
        consultaSQL += "('" + idCamarero + "','" + nombre + "','" + apellido + "','" + especialidad + "')";
        int filas = sentencia.executeUpdate(consultaSQL);
        System.out.println("Numero de filas insertadas: " + filas);
        sentencia.close();
        Conexion.cerrarConexion();
        //conexion.close();
    }

    public static ResultSet mostrarCamarero() throws ClassNotFoundException, SQLException {

        Connection conexion = Conexion.abrirConexion();
        Statement sentencia = conexion.createStatement();
        String consultaSQL = "select idCamarero,nombre,apellido,especialidad from camarero";
        ResultSet lista_camareros = sentencia.executeQuery(consultaSQL);
        //Conexion.cerrarConexion();
        return lista_camareros;

    }

    public static void actualizarCamarero(String nombre, String apellido, String especialidad, int idCamarero) throws ClassNotFoundException, SQLException {

        Connection conexion = Conexion.abrirConexion();
        //Statement sentencia = conexion.createStatement();

        String consultaSQL = "update camarero set nombre=?, apellido=?, especialidad=? where idCamarero=?";
        PreparedStatement actualizar = conexion.prepareStatement(consultaSQL);
        //los numeros son la posicion de la consulta SQL
        actualizar.setString(1, nombre);
        actualizar.setString(2, apellido);
        actualizar.setString(3, especialidad);
        actualizar.setInt(4, idCamarero);
        //Execute update devuelve el numero de filas afectadas
        int filasAfectadas = actualizar.executeUpdate();
        System.out.println("Filas afectadas: " + filasAfectadas);
        actualizar.close();
        Conexion.cerrarConexion();

    }
    
    public static void borrarCamarero(int idCamarero) throws ClassNotFoundException, SQLException {

        Connection conexion = Conexion.abrirConexion();
        String consultaSQL = "delete from camarero where idCamarero=?";
        PreparedStatement borrar = conexion.prepareStatement(consultaSQL);
        borrar.setInt(1, idCamarero);
        //Execute update devuelve el numero de filas afectadas
        int filasAfectadas = borrar.executeUpdate();
        System.out.println("Filas afectadas: " + filasAfectadas);
        
    }
    
     public static ArrayList<Camarero> listarCamareros() throws ClassNotFoundException, SQLException {

        Connection conexion = Conexion.abrirConexion();
        String consultaSQL = "select idCamarero,nombre,apellido, especialidad from camarero";
        PreparedStatement sentencia = conexion.prepareStatement(consultaSQL);
        ResultSet rs = sentencia.executeQuery(consultaSQL);
        ArrayList<Camarero> lista_camareros = new ArrayList<Camarero>();
        try {
            while (rs.next()) {
                lista_camareros.add(new Camarero(Integer.parseInt(rs.getString("idCamarero")),
                        rs.getString("nombre"),
                        rs.getString("apellido"),
                        rs.getString("especialidad")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new ExcepcionesBD("error en la insercion de datos");
        }
        return lista_camareros;
    }

}



