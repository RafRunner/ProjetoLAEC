package Factories

import Dominio.Classe
import Dominio.Fases.Condicao1
import Dominio.Fases.Teste1
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Teste1Factory {

    static Teste1 fromStringMap(Map<String, String> map, List<Classe> classes) {
        Condicao1 condicao1 = Condicao1Factory.fromStringMap(map, classes)
        List<Instrucao> instrucoes = (map.instrucoes as List<Map<String, String>>).collect { InstrucaoFactory.fromStringMap(it) }

        return new Teste1(condicao1, instrucoes)
    }
}
