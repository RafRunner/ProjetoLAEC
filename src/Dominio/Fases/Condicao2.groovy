package Dominio.Fases

import Dominio.Classe
import Dominio.Enums.ModoCondicao2
import Dominio.Exceptions.EntradaInvalidaException
import Dominio.Instrucao
import Dominio.Jsonable
import Utils.TextUtils
import groovy.transform.CompileStatic

@CompileStatic
class Condicao2 implements Jsonable {

    private int acertos = 0
    private int erros = 0
    private int tentativas = 0
    private int condicaoParadaAcerto
    private int condicaoParadaTentativas
    private int acertosConsecutivos = 0

    List<Classe> classes
    List<Instrucao> instrucaoImagem
    List<Instrucao> instrucaoPalavra

    String instrucaoApresetacao
    String instrucaoEscolha

    ModoCondicao2 modoCondicao2

    int numeroRepeticoes
    int tempoLimite

    Condicao2(List<Classe> classes, String nomeModo, List<Instrucao> instrucaoImagem, List<Instrucao> instrucaoPalavra, int condicaoParadaAcerto, int condicaoParadaErro, int repeticoes, int tempoLimite, String instrucaoApresentacao, String instrucaoEscolha) {
        if (!classes || !nomeModo || condicaoParadaAcerto  <= 0 || condicaoParadaErro <= 0 || tempoLimite < 0) {
            throw new EntradaInvalidaException('Parâmetros inválidos para criação de Condicao2!')
        }

        modoCondicao2 = ModoCondicao2.values().find { it.nomeModo == nomeModo }

        if (!modoCondicao2) {
            throw new EntradaInvalidaException('Modo Condição 2 não reconhecido!')
        }

        if ((modoCondicao2 == ModoCondicao2.PRIMEIRO_IMAGEM || modoCondicao2 == ModoCondicao2.PRIMEIRO_PALAVRA) && (!instrucaoPalavra || !instrucaoImagem)) {
            throw new EntradaInvalidaException("O modo '$nomeModo' exige ambas as instruções!")
        }

        if (modoCondicao2 == ModoCondicao2.SOMENTE_IMAGEM && !instrucaoImagem) {
            throw new EntradaInvalidaException("O modo '$nomeModo' exige instrução imagem!")
        }

        if (modoCondicao2 == ModoCondicao2.SOMENTE_PALAVRA && !instrucaoPalavra) {
            throw new EntradaInvalidaException("O modo '$nomeModo' exige instrução palavra!")
        }

        this.instrucaoImagem = instrucaoImagem
        this.instrucaoPalavra = instrucaoPalavra
        this.classes = classes
        this.numeroRepeticoes = repeticoes
        this.condicaoParadaAcerto = condicaoParadaAcerto
        this.condicaoParadaTentativas = condicaoParadaErro
        this.tempoLimite = tempoLimite
        this.instrucaoApresetacao = instrucaoApresentacao
        this.instrucaoEscolha = instrucaoEscolha
    }

    List<Instrucao> getInstrucoes() {
        return (List<Instrucao>) [instrucaoImagem, instrucaoPalavra].flatten()
    }

    void acerto() {
        tentativas++
        acertosConsecutivos++
        acertos++
    }

    void erro() {
        tentativas++
        acertosConsecutivos = 0
        erros++
    }

    List acabou() {
        if (acertosConsecutivos == condicaoParadaAcerto) {
            reset()
            return [true, 'acertos']
        }
        else if (tentativas == condicaoParadaTentativas) {
            reset()
            return [true, 'tentativas']
        }
        return [false, '']
    }

    private void reset() {
        acertos = 0
        erros = 0
        acertosConsecutivos = 0
        tentativas = 0
    }

    @Override
    String toJson() {
        StringBuilder json = new StringBuilder()

        json.append('{')
        json.append("\"modoCondicao2\": \"${modoCondicao2.nomeModo}\",")
        if (instrucaoImagem) {
            json.append("\"instrucaoImagem\": ${TextUtils.listToJsonString(instrucaoImagem.collect { it?.toJson() })},")
        }
        if (instrucaoPalavra) {
            json.append("\"instrucaoPalavra\": ${TextUtils.listToJsonString(instrucaoPalavra.collect { it?.toJson() })},")
        }
        if (instrucaoApresetacao) {
            json.append("\"instrucaoApresetacao\": \"${instrucaoApresetacao}\",")
        }
        if (instrucaoEscolha) {
            json.append("\"instrucaoEscolha\": \"${instrucaoEscolha}\",")
        }
        json.append("\"condicaoParadaAcerto\": \"${condicaoParadaAcerto}\",")
        json.append("\"condicaoParadaTentativas\": \"${condicaoParadaTentativas}\",")
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
