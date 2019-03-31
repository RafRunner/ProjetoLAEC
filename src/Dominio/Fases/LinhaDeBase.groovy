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

    LinhaDeBase(Instrucao instrucaoImagem, Instrucao instrucaoPalavra, List<Classe> classes, Integer repeticoes, String nomeModo) {
        if (instrucaoImagem && instrucaoPalavra && classes && repeticoes) {
            this.instrucaoImagem = instrucaoImagem
            this.instrucaoPalavra = instrucaoPalavra
            this.classes = classes
            this.repeticoes = repeticoes
            this.modoExibicao = ModoLinhaDeBase.values().find { ModoLinhaDeBase modo -> modo.nomeModo == nomeModo }

            if (!nomeModo) {
                throw new EntradaInvalidaException("Modo de Apresentação Linha de Base não reconhecido!!")
            }

        } else {
            throw new EntradaInvalidaException("Informações incompletas ou inválidas para Condicao Um!")
        }
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"instrucaoImagem\": ${instrucaoImagem.toJson()},")
        json.append("\"instrucaoPalavra\": ${instrucaoPalavra.toJson()},")
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
