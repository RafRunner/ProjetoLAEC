package Factories

import Dominio.Classe
import Dominio.Fases.Teste2
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Teste2Factory {

    static Teste2 fromStringMap(Map<String, String> map, List<Classe> classes) {
        Integer numeroRepeticoes = Integer.parseInt(map.numeroRepeticoes)
        List<Instrucao> instrucoes = (map.instrucoes as List<Map<String, String>>).collect { InstrucaoFactory.fromStringMap(it) }

        return new Teste2(instrucoes, classes, numeroRepeticoes)
    }
}
