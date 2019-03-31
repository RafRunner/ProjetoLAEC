package Factories

import Dominio.Classe
import Dominio.Fases.Condicao1
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Condicao1Factory {

    static Condicao1 fromJsonMap(Map<String, String> jsonMap, List<Classe> classes) {
        List<Instrucao> instrucoes = (jsonMap.instrucoes as List<Map<String, String>>).collect { InstrucaoFactory.fromJsonMap(it) }
        Integer numeroRepeticoes = Integer.parseInt(jsonMap.numeroRepeticoes)

        return new Condicao1(instrucoes, classes, numeroRepeticoes)
    }
}
