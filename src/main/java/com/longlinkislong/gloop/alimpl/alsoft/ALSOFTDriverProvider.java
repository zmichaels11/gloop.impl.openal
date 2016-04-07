/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longlinkislong.gloop.alimpl.alsoft;

import com.longlinkislong.gloop.alspi.Driver;
import com.longlinkislong.gloop.alspi.DriverProvider;
import java.util.Arrays;
import java.util.Collection;

/**
 *
 * @author zmichaels
 */
public final class ALSOFTDriverProvider implements DriverProvider {

    @Override
    public Collection<? extends String> getDriverDescription() {
        return Arrays.asList("openal", "openalsoft");
    }

    @Override
    public Driver getDriverInstance() {
        return ALSOFTDriver.getInstance();
    }

    @Override
    public String getDriverName() {
        return "ALSOFTDriver";
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
