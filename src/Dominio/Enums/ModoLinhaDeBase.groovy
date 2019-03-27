package Dominio.Enums

enum ModoLinhaDeBase {

    MODO_IMAGEM('modo imagem'),
    MODO_PALAVRA('modo palavra'),
    MODO_AMBOS('modo ambos')

    String nomeModo

    ModoLinhaDeBase(String nomeModo) {
        this.nomeModo = nomeModo
    }
}