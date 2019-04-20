package Dominio.Fases

import Dominio.Classe
import Dominio.Enums.ModoCondicao2
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Instrucao
import Dominio.Jsonable
import groovy.transform.CompileStatic

@CompileStatic
class Condicao2 implements Jsonable {

    int acertos = 0
    int erros = 0
    int tempoLimite
    int condicaoParadaAcerto
    int condicaoParadaErro
    List<Classe> classes

    Instrucao instrucaoImagem
    Instrucao instrucaoPalavra
    int numeroRepeticoes
    ModoCondicao2 modoExibicao

    private int acertosConsecutivos = 0
    private int errosConsecutivos = 0

    Condicao2(List<Classe> classes, Instrucao instrucaoImagem, Instrucao instrucaoPalavra, String nomeModo, int condicaoParadaAcerto, int condicaoParadaErro, int repeticoes, int tempoLimite) {
        if (!classes || condicaoParadaAcerto  <= 0 || condicaoParadaErro <= 0 || tempoLimite <= 0) {
            throw new EntradaInvalidaException('Parâmetros inválidos para criação de Condicao2!')
        }

        this.modoExibicao = ModoCondicao2.values().find { ModoCondicao2 modo -> modo.nomeModo == nomeModo }

        if (!modoExibicao) {
            throw new EntradaInvalidaException("Modo de Apresentação Condição 2 não reconhecido!!")
        }

        this.instrucaoImagem = instrucaoImagem
        this.instrucaoPalavra = instrucaoPalavra
        this.classes = classes
        this.numeroRepeticoes = repeticoes
        this.condicaoParadaAcerto = condicaoParadaAcerto
        this.condicaoParadaErro = condicaoParadaErro
        this.tempoLimite = tempoLimite
    }

    List<Instrucao> getInstrucoes() {
        return [instrucaoImagem, instrucaoPalavra]
    }

    void acerto() {
        acertosConsecutivos++
        errosConsecutivos = 0
        acertos++
    }

    void erro() {
        errosConsecutivos++
        acertosConsecutivos = 0
        erros++
    }

    List acabou() {
        if (acertosConsecutivos == condicaoParadaAcerto) {
            reset()
            return [true, 'acertos']
        }
        else if (errosConsecutivos == condicaoParadaErro) {
            reset()
            return [true, 'erros']
        }
        return [false, '']
    }

    private void reset() {
        acertos = 0
        erros = 0
        acertosConsecutivos = 0
        errosConsecutivos = 0
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"instrucaoImagem\": ${instrucaoImagem?.toJson()},")
        json.append("\"instrucaoPalavra\": ${instrucaoPalavra?.toJson()},")
        json.append("\"modoExibicao\": \"${modoExibicao.nomeModo}\",")
        json.append("\"condicaoParadaAcerto\": \"${condicaoParadaAcerto}\",")
        json.append("\"condicaoParadaErro\": \"${condicaoParadaErro}\",")
        json.append("\"numeroRepeticoes\": \"${numeroRepeticoes}\",")
        json.append("\"tempoLimite\": \"${tempoLimite}\"")
        json.append('}')

        return json.toString()
    }

    @Override
    String montaNomeArquivo() {
        return null
    }
}
