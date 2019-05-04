package Dominio.Enums

enum ModoCondicao2 {

    SOMENTE_IMAGEM('somente imagem'),
    SOMENTE_PALAVRA('somente palavra'),
    PRIMEIRO_IMAGEM('primeiro imagem'),
    PRIMEIRO_PALAVRA('primeiro palavra')

    String nomeModo

    ModoCondicao2(String nomeModo) {
        this.nomeModo = nomeModo
    }
}