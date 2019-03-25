package Dominio.Fases

import Dominio.Classe
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Instrucao
import Dominio.Jsonable
import groovy.transform.CompileStatic

@CompileStatic
class Condicao1 implements Jsonable {

    List<Instrucao> instrucoes
    List<Classe> classes
    int numeroRepeticoes

    Condicao1(List<Instrucao> instrucoes, List<Classe> classes, int numeroRepeticoes) {
        if (instrucoes && classes && numeroRepeticoes > 0) {
            this.instrucoes = instrucoes
            this.classes = classes
            this.numeroRepeticoes = numeroRepeticoes

        } else {
            throw new EntradaInvalidaException("Informações incompletas ou inválidas para Condicao Um!")
        }
    }

    @Override
    String toJson() {
        return ''
    }
}
