package Controllers

import Dominio.ConfiguracaoGeral
import Files.Logger
import groovy.transform.CompileStatic

@CompileStatic
class LinhaDeBaseController extends ControllerFase {

    LinhaDeBaseController(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        super(janalePrincipalController1, configuracaoGeral, logger)
    }
}
