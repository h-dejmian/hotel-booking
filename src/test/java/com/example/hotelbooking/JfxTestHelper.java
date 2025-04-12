package com.example.hotelbooking;

import javafx.application.Application;

public class JfxTestHelper extends javafx.application.Application {
    private static final Object LOCK = new Object();
    private static boolean started = false;

    public static void initJavaFx() {
        if (!started) {
            synchronized (LOCK) {
                if (!started) {
                    new Thread(() -> Application.launch(JfxTestHelper.class)).start();
                    started = true;
                }
            }
        }
    }

    @Override
    public void start(javafx.stage.Stage primaryStage) {

    }
}
