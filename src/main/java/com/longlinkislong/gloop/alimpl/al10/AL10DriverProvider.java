/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longlinkislong.gloop.alimpl.al10;

import com.longlinkislong.gloop.alspi.Driver;
import com.longlinkislong.gloop.alspi.DriverProvider;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author zmichaels
 */
public final class AL10DriverProvider implements DriverProvider {

    @Override
    public Collection<? extends String> getDriverDescription() {
        return Arrays.asList("openal");
    }

    @Override
    public Driver getDriverInstance() {
        return AL10Driver.getInstance();
    }

    @Override
    public String getDriverName() {
        return "AL10Driver";
    }

    @Override
    public double getSupportRating() {
        return 1.0;
    }

    @Override
    public boolean isSupported() {
        return true;
    }
    
}
