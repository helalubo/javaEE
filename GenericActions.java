package com.closeup.mstr.controller;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.ho.yaml.Yaml;

import com.closeup.mstr.model.ConfigBase;

public abstract class GenericActions {

    static Service sesion;
    static java.sql.Connection conexion;
    static ConfigBase cb;
    
    private static final Logger LOG = Logger.getLogger(GenericActions.class);

    public ConfigBase obtenerConfig() throws FileNotFoundException {
    	ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
    	InputStream input = classLoader.getResourceAsStream("base.yml");
    	ConfigBase config = Yaml.loadType(input, ConfigBase.class);
    	return config;
    }

    public Service obtenerSesion() throws FileNotFoundException {
        if (sesion == null) {
        	ConfigBase base = obtenerConfig();
            sesion = new Service(base.getMstrIP(), base.getMstrPuerto(), base.getMstrProyecto(), base.getMstrUsuario(), base.getMstrClave());
        }
        return sesion;
    }

    public java.sql.Connection obtenerConexion() throws SQLException, ClassNotFoundException, FileNotFoundException {
        if (conexion == null) {
        	ConfigBase base = obtenerConfig();
            Class <?> class1 = Class.forName("oracle.jdbc.driver.OracleDriver");
            conexion = DriverManager.getConnection("jdbc:oracle:thin:@"+base.getDbIP()+":"+base.getDbPuerto()+"/"+base.getDbSID()+"", base.getDbUsuario(), base.getDbClave());
        }
        return conexion;
    }

}
