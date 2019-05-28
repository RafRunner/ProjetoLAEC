package Factories

import Dominio.Classe
import Dominio.Fases.LinhaDeBase
import Dominio.Fases.Teste1
import groovy.transform.CompileStatic

@CompileStatic
class Teste1Factory {

    static Teste1 fromStringMap(Map<String, String> map, List<Classe> classes) {
        LinhaDeBase linhaDeBase = LinhaDeBaseFactory.fromStringMap(map, classes)
        return new Teste1(classes, linhaDeBase.instrucaoInicial, linhaDeBase.instrucoes, linhaDeBase.numeroRepeticoes, linhaDeBase.tempoLimite)
    }
}
