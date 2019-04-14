package Factories

import Dominio.Classe
import Dominio.Fases.LinhaDeBase
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class LinhaDeBaseFactory {

    static LinhaDeBase fromStringMap(Map<String, String> map, List<Classe> classes) {
        List<Instrucao> instrucoes = (map.instrucoes as List<Map<String, String>>).collect { InstrucaoFactory.fromStringMap(it) }
        int numeroRepeticoes = Integer.parseInt(map.numeroRepeticoes)
        int tempoLimite = Integer.parseInt(map.tempoLimite)

        return new LinhaDeBase(classes, instrucoes, numeroRepeticoes, tempoLimite)
    }
}
