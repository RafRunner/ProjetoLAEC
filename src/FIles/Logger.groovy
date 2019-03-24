package FIles

import Dominio.Enums.Sexo
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Jsonable
import groovy.json.JsonOutput
import groovy.transform.CompileStatic

@CompileStatic
class Logger implements Jsonable {

    private static String pastaRelatorios = 'Resultados'

    String nomeExperimentador

    String nomeParticipante
    Sexo sexoParticipante
    int idadeParticipante

    int id
    String nomeArquivoResultado
    private String resultado
    private Date inicioExperimento
    private Date fimExperimento

    Logger(String nomeExperimentador, String nomeParticipante, String sexoParticipante, int idadeParticipante) {
        if (!nomeExperimentador || !nomeParticipante || !sexoParticipante || idadeParticipante <= 0) {
            throw new EntradaInvalidaException('Para iniciar o experimento todas as informações devem ser preenchidas de forma válida!')
        }

        this.sexoParticipante =  Sexo.values().find { Sexo sexo -> sexo.extenso.toLowerCase() == sexoParticipante.toLowerCase() || sexo.abreviacao.toLowerCase() == sexoParticipante.toLowerCase() }
        if (!this.sexoParticipante) {
            throw new EntradaInvalidaException('Sexo inválido!')
        }

        this.nomeExperimentador = nomeParticipante
        this.nomeParticipante = nomeParticipante
        this.idadeParticipante = idadeParticipante
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"nomeExperimentador\": \"${nomeExperimentador}\",")
        json.append("\"nomeParticipante\": \"${nomeParticipante}\",")
        json.append("\"sexoParticipante\": \"${sexoParticipante.extenso}\",")
        json.append("\"idadeParticipante\": \"${idadeParticipante}\",")
        json.append('}')

        return JsonOutput.prettyPrint(json.toString())
    }
}
