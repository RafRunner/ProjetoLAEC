package Dominio.Fases

import Dominio.Classe
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Instrucao
import Dominio.Jsonable
import Utils.TextUtils
import groovy.transform.CompileStatic

@CompileStatic
class Condicao1 implements Jsonable {

    List<Instrucao> instrucoes
    List<Classe> classes
    int numeroRepeticoes
    int tempoLimite

    Condicao1(List<Classe> classes, List<Instrucao> instrucoes, int numeroRepeticoes, int tempoLimite) {
        if (instrucoes && classes && numeroRepeticoes > 0 && tempoLimite >= 0) {
            this.instrucoes = instrucoes
            this.classes = classes
            this.numeroRepeticoes = numeroRepeticoes
            this.tempoLimite = tempoLimite

        } else {
            throw new EntradaInvalidaException("Informações incompletas ou inválidas para Condicao Um!")
        }
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"instrucoes\": ${TextUtils.listToJsonString(instrucoes.collect { it?.toJson() })}, ")
        json.append("\"numeroRepeticoes\": \"${numeroRepeticoes}\",")
        json.append("\"tempoLimite\": \"${tempoLimite}\"")
        json.append('}')

        return json.toString()
    }

    @Override
    String montaNomeArquivo() {
        return null
    }
}
