/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.io.IOException;

/**
 *
 * @author Nader
 */
public class ViewManager {
    public static ScenesSwitcher switcher;
    private ViewManager(){
        
    }
    
    public static void openSwitcher() throws IOException{
        if (switcher == null){
            switcher = new ScenesSwitcher();
            switcher.openLogin();
        }
        switcher.show();
}
    
    public static void closeSwitcher(){
        if (switcher != null){
            switcher.close();
        }
    }
}
