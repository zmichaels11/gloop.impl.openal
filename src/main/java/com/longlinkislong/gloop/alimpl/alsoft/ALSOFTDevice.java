/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longlinkislong.gloop.alimpl.alsoft;

import com.longlinkislong.gloop.alspi.Device;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALContext;
import org.lwjgl.openal.ALDevice;

/**
 *
 * @author zmichaels
 */
final class ALSOFTDevice implements Device {
    ALDevice handle;
    ALCCapabilities caps;
    ALContext context;
    
    @Override
    public boolean isValid() {
        return handle != null;
    }
    
}
