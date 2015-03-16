 /*
 * File:    BaseDeDatosJD.java
 * Coms:    Programación de Sistemas Concurrentes y 
 *          Distribuidos Curso 2013-2014.
 *          BaseDeDatos implementa una base de datos
 *          usando estructuras Hash para almacenar 
 *          información de personas, conforme a las 
 * 		   especificaciones  
 *          definidas en el enunciado de la práctica 3.
 */


/**
* @author  PSCD-Unizar
* @since   20/10/14
**/

package practica_3_2;

import java.util.Hashtable;

public class BaseDeDatos{
    private Hashtable tablaDni;
    private Hashtable tablaNombre;
    private Hashtable tablaApellidos;
    private Hashtable tablaDireccion;
    
    /**
	* Constructor de objetos de la clase BaseDeDatos sin parámetros 
	* tablas nombre, apellidos, dni y dirección 
	* se encuentran vacias.
	* @see BaseDeDatos
	**/
    public BaseDeDatos(){
    	//Crea la bbdd 
        this.tablaNombre = new Hashtable();
        this.tablaApellidos = new Hashtable();
        this.tablaDni = new Hashtable();
        this.tablaDireccion = new Hashtable();
    }

    /**
	* Constructor de objetos de la clase BaseDeDatos con parámetros
	* @param nombres tabla hash con los nombres
	* @param apellidos tabla hash con los apellidos
	* @param dnis tabla hash con los DNIs
	* @param direcciones tabla hash con las direcciones
	* @see Hashtable
	**/
    public BaseDeDatos(Hashtable nombres, Hashtable apellidos,
    	               Hashtable dnis, Hashtable direcciones){
        this.tablaNombre = nombres;
        this.tablaApellidos = apellidos;
        this.tablaDni = dnis;
        this.tablaDireccion = direcciones;
    }

    //--------------------------------------------------------------------------------
    // "setters"

    /**
	* Actualiza el nombre correspondiente a una clave
	* @param clave clave del registro a actualizar
	* @param nombre Nuevo nombre para la clave dada
	**/
    public void updateNombre(int clave, String nombre){
        this.tablaNombre.remove(clave);
        this.tablaNombre.put(clave, nombre);
    }

    /**
	* Actualiza los apellidos correspondientes a una clave
	* @param clave clave del registro a actualizar
	* @param apellidos Nuevos apellidos para la clave dada
	**/
    public void updateApellidos(int clave, String apellidos){
        this.tablaApellidos.remove(clave);
        this.tablaApellidos.put(clave, apellidos);
    }

   /**
	* Actualiza la dirección correspondientes a una clave
	* @param clave clave del registro a actualizar
	* @param direccion Nueva dirección para la clave dada
	**/
    public void updateDireccion(int clave, String direccion){
        this.tablaDireccion.remove(clave);
        this.tablaDireccion.put(clave, direccion);
    }

   /**
	* Crea e inserta un nuevo registro en la base de datos
	* @param clave clave del registro a actualizar
	* @param nombre Nombre para la clave dada
	* @param apellidos Apellidos para la clave dada 
	* @param dni DNI para la clave dada 
	* @param direccion Dirección para la clave dada
	**/
    public void insertRecord(int clave, String nombre, String apellidos, 
    	                     String dni, String direccion){
        this.tablaNombre.put(clave, nombre);
        this.tablaApellidos.put(clave, apellidos);
        this.tablaDni.put(clave, dni);
        this.tablaDireccion.put(clave, direccion);
    }

    //--------------------------------------------------------------------------------
    
    // "getters"
    /**
	* Método para recuperar la tabla Hash con la información de 
	* los nombres de los usuarios de la base de datos.
	* El método no tiene parámetros.
	* @return Hahtable
	* @see Hashtable
	**/
    public  Hashtable getTablaNombre(){
        return this.tablaNombre;
    }

    /**
	* Método para recuperar la tabla Hash con la información de 
	* los apellidos de los usuarios de la base de datos.
	* @return Hahtable
	* @see Hashtable
	**/
    public  Hashtable getTablaApellidos(){
        return this.tablaApellidos;
    }

    /**
	* Método para recuperar la tabla Hash con la información de 
	* los DNIs  de los usuarios de la base de datos.
	* @return Hahtable
	* @see Hashtable
	**/
    public  Hashtable getTablaDni(){
        return this.tablaDni;
    }


    /**
	* Método para recuperar la tabla Hash con la información de 
	* las direcciones de los usuarios de la base de datos.
	* @return Hahtable
	* @see Hashtable
	**/
    public  Hashtable getTablaDirecciones(){
        return this.tablaDireccion;
    }

    /**
	* Método para obtener el nombre de usuario a partir de la clave
	* @param clave entero que se corresponde con el número de clave
	* @return  String con el nombre correspondiente a la clave
	* @see String
	**/
    public String getNombre(int clave){
        return (String) this.tablaNombre.get(clave);
    }

     /**
	* Método para obtener los apellidos de usuario a partir de la clave
	* @param clave entero que se corresponde con el número de clave
	* @return  String con los apellidos correspondientes a la clave
	* @see String
	**/
    public String getApellidos(int clave){
        return (String)  this.tablaApellidos.get(clave);
    }

    /**
	* Método para obtener el DNI de usuario a partir de la clave
	* @param clave entero que se corresponde con el número de clave
	* @return  String con el DNI correspondiente a la clave
	* @see String
	**/
    public String getDni(int clave){
        return (String)  this.tablaDni.get(clave);
    }

    /**
	* Método para obtener la dirección a partir de la clave
	* @param clave entero que se corresponde con el número de clave
	* @return  String con la dirección correspondiente a la clave
	* @see String
	**/
    public String getDireccion(int clave){
        return (String) this.tablaDireccion.get(clave);
    }

}
