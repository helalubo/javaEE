/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.helalubo.energizantesRest;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.glassfish.jersey.media.multipart.MultiPartFeature;

/**
 *
 * @author Helalubo
 */
@ApplicationPath("api")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        resources.add(MultiPartFeature.class);

        return resources;

    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(com.helalubo.energizantesRest.mook.energizante.application.REST.EnergizanteRestService.class);
        resources.add(com.helalubo.energizantesRest.resources.JavaEE8Resource.class);
        resources.add(com.helalubo.energizantesRest.web.CorsFilter.class);
    }

}
