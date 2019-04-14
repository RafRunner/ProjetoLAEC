package Controllers

import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Instrucao
import Files.Logger
import groovy.transform.CompileStatic

@CompileStatic
class ControllerFase {

    JanelaPrincipalController janelePrincipalController
    ConfiguracaoGeral configuracaoGeral
    Logger logger
    List<Classe> classes
    ArrayList<Instrucao> instrucoes

    long tempo

    ControllerFase(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        this.configuracaoGeral = configuracaoGeral
        this.janelePrincipalController = janalePrincipalController1
        this.classes = configuracaoGeral.classes
        this.logger = logger
        this.tempo = 0
    }

    void iniciar() {
    }
}
