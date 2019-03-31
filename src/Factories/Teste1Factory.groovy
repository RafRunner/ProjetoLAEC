package Factories

import Dominio.Classe
import Dominio.Fases.Condicao1
import Dominio.Fases.Teste1
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Teste1Factory {

    static Teste1 fromJsonMap(Map<String, String> jsonMap, List<Classe> classes) {
        Condicao1 condicao1 = Condicao1Factory.fromJsonMap(jsonMap, classes)
        List<Instrucao> instrucoes = (jsonMap.instrucoes as List<Map<String, String>>).collect { InstrucaoFactory.fromJsonMap(it) }

        return new Teste1(condicao1, instrucoes)
    }
}
