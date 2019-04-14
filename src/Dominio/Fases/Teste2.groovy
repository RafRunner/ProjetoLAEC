package Dominio.Fases

import Dominio.Classe
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Teste2 extends Condicao1 {

    Teste2(List<Classe> classes, List<Instrucao> instrucoes, int numeroRepeticoes, int tempoLimite) {
        super(classes, instrucoes, numeroRepeticoes, tempoLimite)
    }
}
