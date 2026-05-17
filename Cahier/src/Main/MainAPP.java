/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import Model.Connector;
import Model.Data.User;
import View.ViewLogin;
import Controller.ControllerLogin;

/**
 *
 * @author LENOVO
 */
public class MainAPP {
    public static void main(String[] args) {
//        Connector.connect();

        ViewLogin view = new ViewLogin();
        new ControllerLogin(view);

    }
}
