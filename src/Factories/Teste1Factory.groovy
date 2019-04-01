package Factories

import Dominio.Classe
import Dominio.Fases.Teste1
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Teste1Factory {

    static Teste1 fromStringMap(Map<String, String> map, List<Classe> classes) {
        Integer numeroRepeticoes = Integer.parseInt(map.numeroRepeticoes)
        List<Instrucao> instrucoes = (map.instrucoes as List<Map<String, String>>).collect { InstrucaoFactory.fromStringMap(it) }

        return new Teste1(instrucoes, classes, numeroRepeticoes)
    }
}
