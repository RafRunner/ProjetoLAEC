package Controllers

import Dominio.ConfiguracaoGeral

interface CriadorConfiguracao {

    void modificaConfiguracao(ConfiguracaoGeral configuracaoGeral)

    void atualizar()
}