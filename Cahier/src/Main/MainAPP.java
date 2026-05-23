/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Controller.*;
import View.*;

/**
 *
 * @author LENOVO
 */
public class MainAPP {
    public static void main(String[] args) {
//        Connector.connect();

        ViewMenuUtama view = new ViewMenuUtama();
        new ControllerMenuUtama(view);
        
//        ViewLogin view = new ViewLogin();
//        new ControllerLogin(view);

    }
}
