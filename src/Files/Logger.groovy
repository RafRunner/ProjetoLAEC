package Files

import Dominio.ConfiguracaoGeral
import Dominio.Enums.Sexo
import Dominio.Exceptions.EntradaInvalidaException
import Services.LoggerService
import groovy.transform.CompileStatic

import java.text.SimpleDateFormat

@CompileStatic
class Logger {

    String nomeExperimentador
    String nomeParticipante
    Sexo sexoParticipante
    int idadeParticipante

    ConfiguracaoGeral configuracaoUsada
    String nomeArquivo

    private StringBuilder log

    private static SimpleDateFormat formatoData = new SimpleDateFormat("yyyy-MM-dd")
    private static SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm:ss")
    private static SimpleDateFormat formatoCompleto = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    private Date inicioExperimento
    private Date fimExperimento

    private LoggerService loggerService = LoggerService.instancia

    Logger(String nomeExperimentador, String nomeParticipante, String sexoParticipante, int idadeParticipante, ConfiguracaoGeral configuracaoUsada) {
        if (!nomeExperimentador || !nomeParticipante || !sexoParticipante || !configuracaoUsada || idadeParticipante <= 0) {
            throw new EntradaInvalidaException('Para iniciar o experimento todas as informações devem ser preenchidas de forma válida!')
        }

        this.sexoParticipante =  Sexo.values().find { Sexo sexo -> sexo.extenso.toLowerCase() == sexoParticipante.toLowerCase() || sexo.abreviacao.toLowerCase() == sexoParticipante.toLowerCase() }
        if (!this.sexoParticipante) {
            throw new EntradaInvalidaException('Sexo inválido!')
        }

        this.nomeExperimentador = nomeExperimentador
        this.nomeParticipante = nomeParticipante
        this.idadeParticipante = idadeParticipante
        this.configuracaoUsada = configuracaoUsada
        this.inicioExperimento = new Date()

        this.log = new StringBuilder()
        this.nomeArquivo = montaNomeArquivo()
        loggerService.atualizaNomeArquivoCasoJaExista(this)
    }

    String criaArquivoResultado() {
        StringBuilder resultado = new StringBuilder()
        fimExperimento = new Date()

        resultado.append("Nome Experimentador: ${nomeExperimentador}\n\n")
        resultado.append("Grupo do participante: ${configuracaoUsada.ordem.nomeGrupo}\n")
        resultado.append("Ordem das fases: ${configuracaoUsada.ordem.ordemFases}\n")
        resultado.append("Nome Participante: ${nomeParticipante}\n")
        resultado.append("Sexo Participante: ${sexoParticipante.extenso}\n")
        resultado.append("Idade Participante: ${idadeParticipante}\n\n")
        resultado.append("Inicio Experimento: ${formatoCompleto.format(inicioExperimento)}\n")
        resultado.append("Fim Experimento: ${formatoCompleto.format(fimExperimento)}\n\n")
        resultado.append("Configuracao Usada:\n${configuracaoUsada.toJson()}\n\n")
        resultado.append("Resultados:\n")

        return resultado.toString()
    }

    private montaNomeArquivo() {
        return configuracaoUsada.tituloConfiguracao + '_' + nomeExperimentador + '_' + nomeParticipante + '_' + formatoData.format(inicioExperimento) + '.txt'
    }

    void log(String mensagem, String prefixos = null) {
        String linhaCompleta = "\t${formatoHora.format(new Date())}: ${mensagem}\n"
        if (prefixos) {
            linhaCompleta = prefixos + linhaCompleta
        }
        log.append(linhaCompleta)
    }

    void limpaLog() {
        log = new StringBuilder()
    }

    String getLog() {
        return log.toString()
    }
}
