package Controllers

import Dominio.ConfiguracaoGeral
import Files.Logger
import groovy.transform.CompileStatic

@CompileStatic
class Teste1Controller extends ControllerFase {

    Teste1Controller(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        super(janalePrincipalController1, configuracaoGeral, logger)
    }
}
