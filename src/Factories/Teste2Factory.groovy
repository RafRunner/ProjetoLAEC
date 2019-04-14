package Factories

import Dominio.Classe
import Dominio.Fases.Teste2
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Teste2Factory {

    static Teste2 fromStringMap(Map<String, String> map, List<Classe> classes) {
        List<Instrucao> instrucoes = (map.instrucoes as List<Map<String, String>>).collect { InstrucaoFactory.fromStringMap(it) }
        int numeroRepeticoes = Integer.parseInt(map.numeroRepeticoes)
        int tempoLimite = Integer.parseInt(map.tempoLimite)

        return new Teste2(classes, instrucoes, numeroRepeticoes, tempoLimite)
    }
}
