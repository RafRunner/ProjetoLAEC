package Dominio.Fases

import Dominio.Enums.ModoLinhaDeBase
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Instrucao
import Dominio.Jsonable
import groovy.transform.CompileStatic

@CompileStatic
class Teste2 implements Jsonable {

    LinhaDeBase linhaDeBase

    Teste2(LinhaDeBase linhaDeBase, Instrucao instrucaoImagem, Instrucao instrucaoPalavra) {
        if (!linhaDeBase) {
            throw new EntradaInvalidaException('Teste 2 necessita de linha de base')
        }

        if (!instrucaoPalavra || !instrucaoImagem) {
            throw new EntradaInvalidaException('Teste 2 necessita pelo menos uma instrução!')
        }

        if (linhaDeBase.modoExibicao == ModoLinhaDeBase.MODO_IMAGEM && !instrucaoImagem) {
            throw new EntradaInvalidaException('Teste 2 no modo imagem necessita de instrução imagem!!')
        }

        if (linhaDeBase.modoExibicao == ModoLinhaDeBase.MODO_PALAVRA && !instrucaoPalavra) {
            throw new EntradaInvalidaException('Teste 2 no modo palavra necessita de instrução palavra!!')
        }

        linhaDeBase.instrucaoImagem = instrucaoImagem
        linhaDeBase.instrucaoPalavra = instrucaoPalavra
        this.linhaDeBase = linhaDeBase
    }

    @Override
    String toJson() {
        return linhaDeBase.toJson()
    }

    @Override
    String montaNomeArquivo() {
        return linhaDeBase.montaNomeArquivo()
    }
}
