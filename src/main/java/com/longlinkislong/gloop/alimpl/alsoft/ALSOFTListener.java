/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longlinkislong.gloop.alimpl.alsoft;

import com.longlinkislong.gloop.alspi.Listener;

/**
 *
 * @author zmichaels
 */
final class ALSOFTListener implements Listener {
    private ALSOFTListener() {}
    
    static final class Holder {
        static final ALSOFTListener INSTANCE = new ALSOFTListener();
    }
    
    @Override
    public boolean isValid() {        
        return true;
    }

    
}
