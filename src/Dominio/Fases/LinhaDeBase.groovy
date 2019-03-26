package Dominio.Fases

import Dominio.Classe
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Instrucao
import Dominio.Jsonable
import groovy.transform.CompileStatic

@CompileStatic
class LinhaDeBase implements Jsonable {

    Instrucao intrucaoInicial
    Instrucao instrucaoFinal
    List<Classe> classes

    LinhaDeBase(Instrucao instrucaoInicial, Instrucao instrucaoFinal, List<Classe> classes) {
        if (instrucaoInicial && instrucaoFinal && classes) {
            this.intrucaoInicial = instrucaoInicial
            this.instrucaoFinal = instrucaoFinal
            this.classes = classes

        } else {
            throw new EntradaInvalidaException("Informações incompletas ou inválidas para Condicao Um!")
        }
    }

    @Override
    String toJson() {
        return ''
    }

    @Override
    String montaNomeArquivo() {
        return ''
    }
}
