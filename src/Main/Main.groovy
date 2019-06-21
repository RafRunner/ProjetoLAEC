package Main

import Controllers.MenuIniciar
import View.Condicao2ApresentacaoView
import View.Condicao2View
import View.ViewUtils

import javax.swing.JFrame
import javax.swing.JOptionPane
import java.awt.Color
import java.awt.Dimension
import java.awt.Toolkit

class Main {

    static void main(String[] args) {
        new MenuIniciar()

        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
            void uncaughtException(Thread t, Throwable e) {
                String mensagemErro = e.toString().find(/(?<=.EntradaInvalidaException: ).+!/) ?: e.toString()
                JOptionPane.showMessageDialog(null, mensagemErro, 'Erro!', JOptionPane.ERROR_MESSAGE)
            }
        })
//        teste()
    }

    static void teste() {
        JFrame janela = new JFrame()
        Condicao2ApresentacaoView condicao2ApresentacaoView = new Condicao2ApresentacaoView(Color.YELLOW, 'Teste', 'instrução teste', new Object())
        Condicao2View condicao2View = new Condicao2View(['Ola'], Color.PINK, 'Teste', null, new Object())
        janela.add(condicao2View)
        Dimension tamanhoTela = Toolkit.defaultToolkit.screenSize
        ViewUtils.configuraJFrame(janela, tamanhoTela, 'teste')
    }
}
