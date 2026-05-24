/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Main;

import View.*;
import Controller.*;
import Model.*;

/**
 *
 * @author LENOVO
 */
public class MainAPP {
    public static int loggedInUserId;
    public static String loggedInUserNama;

    public static void main(String[] args) {
        // MULTITHREADING
        System.out.println("[Main Thread] Aplikasi Kasir Dimulai.");

        Thread dbCheckThread = new Thread(new Runnable() {
            @Override
            public void run() {
                // Kode di dalam method run() ini akan dieksekusi secara asinkron di thread
                // terpisah
                System.out.println("[Background Thread] Memulai pengecekan koneksi database di background...");
                try {
                    // Simulasi delay pengerjaan background task (1 detik)
                    Thread.sleep(1000);

                    // Mencoba menghubungkan database secara asinkron agar tidak membebani pemuatan
                    // form login grafis
                    Model.Connector.connect();

                    System.out.println("[Background Thread] Koneksi database sukses diverifikasi secara paralel!");
                } catch (InterruptedException e) {
                    System.out.println("[Background Thread] Koneksi background terganggu: " + e.getMessage());
                }
            }
        });

        // Memulai eksekusi background thread
        dbCheckThread.start();

        // MAIN THREAD
        System.out.println("[Main Thread] Meluncurkan form login grafis...");
        ViewLogin viewLogin = new ViewLogin();
        new ControllerLogin(viewLogin);
    }
    // Connector.connect();

    // new ViewCekStok();

    // ViewMenuUtama view = new ViewMenuUtama();
    // new ControllerMenuUtama(view);

    // ViewCekStok view = new ViewCekStok();
    // new ViewCekStok();

    // ViewMenuUtama view = new ViewMenuUtama();
    // new ControllerMenuUtama(view);

    // ViewLogin view = new ViewLogin();
    // new ControllerLogin(view);

    // new CheckDB();

}
