/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.energizantesRest.upload;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.ejb.Stateless;

/**
 *
 * @author Helalubo
 */

public class Upload {
    
    public static String writeToFileServer(InputStream inputStream, String fileName, String path) throws IOException {
        
        OutputStream outputStream = null;
        String finalPath = path + fileName;
        
        try {
            
            outputStream = new FileOutputStream(new File(finalPath));
            int read = 0;
            byte[] bytes = new byte[1024];
            while ((read = inputStream.read(bytes)) != 1) {
                outputStream.write(bytes, 0, read);
            }
            outputStream.flush();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }finally{
            outputStream.close();
        }
        return finalPath;
        
    }
}
