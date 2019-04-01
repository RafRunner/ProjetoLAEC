package Dominio.Fases

import Dominio.Classe
import Dominio.Enums.ModoLinhaDeBase
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Instrucao
import Dominio.Jsonable
import groovy.transform.CompileStatic

@CompileStatic
class LinhaDeBase implements Jsonable {

    Instrucao instrucaoImagem
    Instrucao instrucaoPalavra
    int repeticoes
    ModoLinhaDeBase modoExibicao
    List<Classe> classes

    LinhaDeBase(Instrucao instrucaoImagem, Instrucao instrucaoPalavra, List<Classe> classes, int repeticoes, String nomeModo) {
        if (!classes || repeticoes <= 0) {
            throw new EntradaInvalidaException("Informações incompletas ou inválidas para Linha de Base!")
        }

        this.instrucaoImagem = instrucaoImagem
        this.instrucaoPalavra = instrucaoPalavra
        this.classes = classes
        this.repeticoes = repeticoes
        this.modoExibicao = ModoLinhaDeBase.values().find { ModoLinhaDeBase modo -> modo.nomeModo == nomeModo }

        if (!modoExibicao) {
            throw new EntradaInvalidaException("Modo de Apresentação Linha de Base não reconhecido!!")
        }

        if (!instrucaoPalavra && !instrucaoImagem) {
            throw new EntradaInvalidaException('Linha de Base necessita pelo menos uma instrução!')
        }

        if (modoExibicao == ModoLinhaDeBase.MODO_IMAGEM && !instrucaoImagem) {
            throw new EntradaInvalidaException('Linha de Base no modo imagem necessita de instrução imagem!!')
        }

        if (modoExibicao == ModoLinhaDeBase.MODO_PALAVRA && !instrucaoPalavra) {
            throw new EntradaInvalidaException('Linha de Base no modo palavra necessita de instrução palavra!!')
        }
    }

    List<Instrucao> getInstrucoes() {
        return [instrucaoImagem, instrucaoPalavra]
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"instrucaoImagem\": ${instrucaoImagem?.toJson()},")
        json.append("\"instrucaoPalavra\": ${instrucaoPalavra?.toJson()},")
        json.append("\"repeticoes\": \"${repeticoes}\",")
        json.append("\"modoExibicao\": \"${modoExibicao.nomeModo}\"")
        json.append('}')

        return json.toString()
    }

    @Override
    String montaNomeArquivo() {
        return null
    }
}
