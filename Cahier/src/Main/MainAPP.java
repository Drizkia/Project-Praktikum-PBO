/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import View.*;
import Controller.*;


/**
 *
 * @author LENOVO
 */
public class MainAPP {
    public static void main(String[] args) {
//        Connector.connect();

//        new ViewCekStok();

//        ViewMenuUtama view = new ViewMenuUtama();
//        new ControllerMenuUtama(view);

        // ViewCekStok view = new ViewCekStok();
        // new ViewCekStok();

    //    ViewMenuUtama view = new ViewMenuUtama();
    //    new ControllerMenuUtama(view);
        
       ViewLogin view = new ViewLogin();
       new ControllerLogin(view);

    }
}
