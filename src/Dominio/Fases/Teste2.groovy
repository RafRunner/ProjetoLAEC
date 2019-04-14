package Dominio.Fases

import Dominio.Classe
import Dominio.Instrucao
import groovy.transform.CompileStatic

@CompileStatic
class Teste2 extends Condicao1 {

    Teste2(List<Instrucao> instrucoes, List<Classe> classes, int numeroRepeticoes, int tempoLimite) {
        super(instrucoes, classes, numeroRepeticoes, tempoLimite)
    }
}
