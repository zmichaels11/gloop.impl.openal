/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longlinkislong.gloop.alimpl.alsoft;

import com.longlinkislong.gloop.alspi.AuxiliaryEffectSlot;

/**
 *
 * @author zmichaels
 */
final class ALSOFTAuxiliaryEffectSlot implements AuxiliaryEffectSlot {
    int auxEffectSlotId = -1;
    
    @Override
    public boolean isValid() {
        return this.auxEffectSlotId != -1;
    }
    
}
