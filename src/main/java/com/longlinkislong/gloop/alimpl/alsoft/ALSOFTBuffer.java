/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longlinkislong.gloop.alimpl.alsoft;

import com.longlinkislong.gloop.alimpl.al10.*;
import com.longlinkislong.gloop.alspi.Buffer;

/**
 *
 * @author zmichaels
 */
public final class ALSOFTBuffer implements Buffer {
    int bufferId = -1;
    @Override
    public boolean isValid() {
        return bufferId != -1;
    }
    
    @Override
    public boolean equals(Object other) {
        if(other instanceof ALSOFTBuffer) {
            return bufferId == ((ALSOFTBuffer) other).bufferId;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.bufferId;
        return hash;
    }
}
