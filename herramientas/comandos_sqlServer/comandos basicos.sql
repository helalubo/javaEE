
--NOTA -> ES IMPORTANTE EL GO

--Ver bases de datos
select DB_NAME()
GO

-- listar bases de datos 

select name from sys.databases
GO

--LISTAR BASE DE DATOS CON DETALLES DE PESO
Sp_databases
Go

---VER VERSION DE SQL SERVER

SELECT SERVERPROPERTY('EDITION')
GO

--CREO archivo sql que tenga la consulta
select * helalubo.information_schema.ScriptHelalubo

--ejecutar script y cargar resultado en archivo txt determinado por path
-S DESKTOP-5K4TURF\SQLEXPRESS -E -i c:\sql\ScriptHelalubo.sql -o C:\Users\User\Desktop\java\herramientas\comandos_sqlServer\resultadoScriptHelalubo.txt
GO
--crear base de datos
CREATE DATABASE helalubo
GO

--crear copia de seguridad 

 
BACKUP DATABASE [AdventureWorks2014] TO  DISK = N'C:\SQL\backup.bak'
GO

--cambiar contexto de uso de database determinada
use helalubo

--crear tabla parado en helalubo
CREATE TABLE perfil  
   (id_perfil int PRIMARY KEY NOT NULL,  
   nombre varchar(25) NOT NULL
   )  
GO  

CREATE TABLE rol  
   (id_rol int PRIMARY KEY NOT NULL,  
   nombre varchar(25) NOT NULL
   )  
GO  

--seleccionar perfiles
select * from perfil;
GO

--INSERTAR EN BASE DE DATOS
-- Standard syntax  
INSERT perfil (id_perfil, nombre)  
    VALUES (3, 'invitadp')  
GO

INSERT perfil (id_perfil, nombre)  
    VALUES (2, 'USUARIO')  
GO   

INSERT rol (id_rol, nombre)  
    VALUES (2, 'ADMINISTRADOR')  
GO 
--actualizar contenido en base de datos
UPDATE perfil  
    SET nombre = 'INVITADO'  
    WHERE id_perfil = 3  
GO  
--lectura determinada de bases de datos tabla
-- The basic syntax for reading data from a single table  
SELECT nombre, id_perfil  
    FROM perfil  
GO  

-- verificacion por factor determinante
-- Returns only two of the records in the table  
SELECT ProductID, ProductName, Price, ProductDescription  
    FROM dbo.Products  
    WHERE ProductID < 60  
GO 

--crear vistar  -- las vistas se llaman como tablas (osea que se pueden llamar con select)
CREATE VIEW vw_Names  
   AS  
   SELECT ProductName, Price FROM Products;  
GO    

--ver vista
SELECT * FROM vw_Names;  
GO   

----crear procedimiento (ejemplo de cambio en parte especifica de base de datos)
--Creación de un procedimiento almacenado
--La siguiente instrucción crea un procedimiento almacenado denominado pr_Names,
-- acepta un parámetro de entrada denominado @VarPrice del tipo de datos money. 
-- El procedimiento almacenado imprime la instrucción Products less than concatenada 
-- con el parámetro de entrada que cambia del tipo de datos money a un tipo de datos de carácter varchar(10) 
-- . A continuación, el procedimiento ejecuta una instrucción SELECT en la vista y le pasa el parámetro de entrada como parte de la cláusula WHERE .
-- Esto devuelve todos los productos cuyo costo es menor que el valor del parámetro de entrada.
CREATE PROCEDURE pr_Names @VarPrice money  
   AS  
   BEGIN  
      -- The print statement returns text to the user  
      PRINT 'Products less than ' + CAST(@VarPrice AS varchar(10));  
      -- A second statement starts here  
      SELECT ProductName, Price FROM vw_Names  
            WHERE Price < @varPrice;  
   END  
GO    
--Probar el procedimiento almacenado
EXECUTE pr_Names 10.00;  
GO  