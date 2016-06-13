/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longlinkislong.gloop.alimpl.alsoft;

import com.longlinkislong.gloop.alspi.Device;
import org.lwjgl.openal.ALCCapabilities;
import org.lwjgl.openal.ALCapabilities;

/**
 *
 * @author zmichaels
 */
final class ALSOFTDevice implements Device {
    long deviceId = -1;
    long contextId = -1;
    ALCCapabilities alcCaps = null;
    ALCapabilities alCaps = null;
    
    @Override
    public boolean isValid() {
        return deviceId != -1 && contextId != -1;
    }
    
}
