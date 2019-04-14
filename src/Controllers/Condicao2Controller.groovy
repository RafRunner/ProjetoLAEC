package Controllers

import Dominio.ConfiguracaoGeral
import Files.Logger
import groovy.transform.CompileStatic

@CompileStatic
class Condicao2Controller extends ControllerFase {

    Condicao2Controller(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        super(janalePrincipalController1, configuracaoGeral, logger)
    }
}
