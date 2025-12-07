package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.controller

import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model.DadosPessoais
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model.HistoricoDao
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model.HistoricoSqlite
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.ui.HistoricoActivity

class HistoricoController(historicoActivity: HistoricoActivity) {
    private val historicoDao: HistoricoDao = HistoricoSqlite(historicoActivity)

    fun insertHistorico(dadosPessoais: DadosPessoais) = historicoDao.criarHistorico(dadosPessoais)
}