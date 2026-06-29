package udesc.br.app;

import udesc.br.controller.FrameControlador;
import udesc.br.vision.FramePrincipalVisao;

public class Sistema {
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(FramePrincipalVisao.class.getName());
    
    public static void main(String[] args) {
        try {
            javax.swing.UIManager.setLookAndFeel(new com.formdev.flatlaf.themes.FlatMacLightLaf());
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        FrameControlador frameControlador = new FrameControlador();
    }
}
