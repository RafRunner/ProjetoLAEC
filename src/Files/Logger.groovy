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
    private static String lineSeparator = System.getProperty('line.separator')
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

        resultado.append("Nome Experimentador: ${nomeExperimentador}$lineSeparator$lineSeparator")
        resultado.append("Grupo do participante: ${configuracaoUsada.ordem.nomeGrupo}$lineSeparator")
        resultado.append("Ordem das fases: ${configuracaoUsada.ordem.ordemFases}$lineSeparator")
        resultado.append("Nome Participante: ${nomeParticipante}$lineSeparator")
        resultado.append("Sexo Participante: ${sexoParticipante.extenso}$lineSeparator")
        resultado.append("Idade Participante: ${idadeParticipante}$lineSeparator$lineSeparator")
        resultado.append("Inicio Experimento: ${formatoCompleto.format(inicioExperimento)}$lineSeparator")
        String configuracaoFormatada = configuracaoUsada.toJson().replaceAll('\n', lineSeparator)
        resultado.append("Configuracao Usada:$lineSeparator${configuracaoFormatada}$lineSeparator")
        resultado.append("Resultados:$lineSeparator")

        return resultado.toString()
    }

    private montaNomeArquivo() {
        return configuracaoUsada.tituloConfiguracao + '_' + nomeExperimentador + '_' + nomeParticipante + '_' + formatoData.format(inicioExperimento) + '.txt'
    }

    void log(String mensagem, String prefixos = "") {
        mensagem = mensagem.replaceAll('\n', lineSeparator)
        String linhaCompleta = prefixos + "\t${formatoHora.format(new Date())}: ${mensagem}$lineSeparator"
        log.append(linhaCompleta)
    }

    void limpaLog() {
        log = new StringBuilder()
    }

    void registraFimExperimento() {
        log.append("$lineSeparator${lineSeparator}Fim do Experimento: ${formatoCompleto.format(new Date())}$lineSeparator")
    }

    String getLog() {
        return log.toString()
    }
}
