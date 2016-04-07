/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longlinkislong.gloop.alimpl.alsoft;

import com.longlinkislong.gloop.alspi.Source;

/**
 *
 * @author zmichaels
 */
final class ALSOFTSource implements Source{
    int sourceId = -1;
    
    @Override
    public boolean isValid() {
        return sourceId != -1;
    }
    
    @Override
    public boolean equals(Object other) {
        if(other instanceof ALSOFTSource) {
            return this.sourceId == ((ALSOFTSource) other).sourceId;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.sourceId;
        return hash;
    }
}
