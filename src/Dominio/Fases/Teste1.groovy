package Dominio.Fases

import Dominio.Classe
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Teste1 extends LinhaDeBase {

    Teste1(List<Classe> classes, Instrucao instrucaoInicial, List<Instrucao> instrucoes, int repeticoes, int tempoLimite) {
        super(classes, instrucaoInicial, instrucoes, repeticoes, tempoLimite)
    }
}
