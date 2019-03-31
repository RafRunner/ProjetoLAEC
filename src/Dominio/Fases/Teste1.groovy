package Dominio.Fases

import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Instrucao
import Dominio.Jsonable
import groovy.transform.CompileStatic

@CompileStatic
class Teste1 implements Jsonable {

    Condicao1 condicao1

    Teste1(Condicao1 condicao1, List<Instrucao> instrucoes) {
        if (!condicao1 || !instrucoes) {
            throw new EntradaInvalidaException('Teste 1 necessita de condicao1 e instruções')
        }

        condicao1.instrucoes = instrucoes
        this.condicao1 = condicao1
    }

    @Override
    String toJson() {
        return condicao1.toJson()
    }

    @Override
    String montaNomeArquivo() {
        return condicao1.montaNomeArquivo()
    }
}
