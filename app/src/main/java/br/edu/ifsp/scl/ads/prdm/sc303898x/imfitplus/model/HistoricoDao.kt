package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model

interface HistoricoDao {
    fun criarHistorico(dadosPessoais: DadosPessoais): Long
    fun buscarHistorico(id: Int): DadosPessoais
    fun buscarHistoricos(): MutableList<DadosPessoais>
    fun atualizarHistorico(dadosPessoais: DadosPessoais): Int
    fun deleteHistorico(id: Int): Int
}