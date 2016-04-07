/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.longlinkislong.gloop.alimpl.alsoft;

import com.longlinkislong.gloop.alspi.Filter;

/**
 *
 * @author zmichaels
 */
final class ALSOFTFilter implements Filter {
    int filterId = -1;
    
    @Override
    public boolean isValid() {
        return this.filterId != -1;
    }
}
