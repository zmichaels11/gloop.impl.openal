/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longlinkislong.gloop.alimpl.al10;

import com.longlinkislong.gloop.alspi.Listener;

/**
 *
 * @author zmichaels
 */
final class AL10Listener implements Listener {
    private AL10Listener() {}
    
    static final class Holder {
        static final AL10Listener INSTANCE = new AL10Listener();
    }
    
    @Override
    public boolean isValid() {        
        return true;
    }

    
}
