package Files

import Dominio.Configuracoes.ConfiguracaoGeral
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

    String log

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
    }

    String criaResultado() {
        StringBuilder resultado = new StringBuilder()
        fimExperimento = new Date()

        resultado.append("Nome Experimentador: ${nomeExperimentador}\n")
        resultado.append("Nome Participante: ${nomeParticipante}\n")
        resultado.append("Sexo Participante: ${sexoParticipante.extenso}\n")
        resultado.append("Idade Participante: ${idadeParticipante}\n\n")
        resultado.append("Configuracao Usada:\n${configuracaoUsada.toJson()}\n\n")
        resultado.append("Inicio Experimento: ${formatoCompleto.format(inicioExperimento)}\n")
        resultado.append("Fim Experimento: ${formatoCompleto.format(fimExperimento)}\n")
        formatarLog(resultado)

        return resultado.toString()
    }

    String montaNomeArquivo() {
        StringBuilder nomeResultado = new StringBuilder()
        nomeResultado.append(configuracaoUsada.tituloConfiguracao).append('_' + nomeExperimentador).append('_' + nomeParticipante).append('_' + formatoData.format(inicioExperimento))
        return nomeResultado.toString()
    }

    private String formatarLog(StringBuilder resultado) {
        resultado.append('\nResultados:\n')

        List<String> linhas = log.tokenize('\n')
        for (String linha : linhas) {
            resultado.append('\t').append(linha).append('\n')
        }
        resultado.append('\n')
    }
}
