package Controllers

import Dominio.Classe
import Dominio.ConfiguracaoGeral
import Dominio.Instrucao
import Files.Logger
import Services.LoggerService
import groovy.transform.CompileStatic

@CompileStatic
abstract class ControllerFase {

    protected JanelaPrincipalController janelePrincipalController
    protected ConfiguracaoGeral configuracaoGeral
    protected Logger logger
    protected List<Classe> classes
    protected ArrayList<Instrucao> instrucoes

    protected LoggerService loggerService = LoggerService.instancia

    protected long tempo = 0
    protected long tempoLimite = 0

    ControllerFase(JanelaPrincipalController janalePrincipalController1, ConfiguracaoGeral configuracaoGeral, Logger logger) {
        this.configuracaoGeral = configuracaoGeral
        this.janelePrincipalController = janalePrincipalController1
        this.classes = configuracaoGeral.classes
        this.logger = logger
    }

    abstract void iniciar();

    void toqueEstimulo(String palavraTocada) {}

    protected void verificarTempo() {
        if (tempoLimite >= 0) {
            new Thread() {

                void run() {
                    while (tempo <= tempoLimite) {
                        sleep(1000)
                        tempo++
                    }
                    logger.log("Tempo máximo estourado ($tempoLimite s)! Passando para a próxima fase...\n", '\n')
                    loggerService.registraLog(logger)
                    janelePrincipalController.passarParaProximaFase()
                }
            }.start()
        }
    }
}
