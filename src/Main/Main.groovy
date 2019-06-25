package Main

import Controllers.MenuIniciar

import javax.swing.JOptionPane

class Main {

    static void main(String[] args) {
        new MenuIniciar()

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            void uncaughtException(Thread t, Throwable e) {
                String mensagemErro = e.toString().find(/(?<=.EntradaInvalidaException: ).+!/) ?: e.toString()
                JOptionPane.showMessageDialog(null, mensagemErro, 'Erro!', JOptionPane.ERROR_MESSAGE)
            }
        })
    }
}
