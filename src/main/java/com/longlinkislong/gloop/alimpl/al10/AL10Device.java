/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longlinkislong.gloop.alimpl.al10;

import com.longlinkislong.gloop.alspi.Device;

/**
 *
 * @author zmichaels
 */
final class AL10Device implements Device {
    long deviceId = -1;
    long contextId = -1;
    
    @Override
    public boolean isValid() {
        return deviceId != -1 && contextId != -1;
    }
    
}
