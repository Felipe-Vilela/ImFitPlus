package br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.model

import android.content.ContentValues
import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import br.edu.ifsp.scl.ads.prdm.sc303898x.imfitplus.R
import java.sql.SQLException

class HistoricoSqlite(context: Context): HistoricoDao {
    companion object{
        private val HISTORICO_DATABASE_FILE = "historicoList"
        private val HISTORICO_TABLE = "historico"
        private val ID_COLUMN = "id"
        private val NOME_COLUMN = "nome"
        private val IDADE_COLUMN = "idade"
        private val SEXO_COLUMN = "sexo"
        private val ALTURA_COLUMN = "altura"
        private val PESO_COLUMN = "peso"
        private val NIVEL_AT_COLUMN = "nivelAt"
        private val IMC_COLUMN = "imc"
        private val TMB_COLUMN = "tmb"
        private val GASTO_CALORICO_COLUMN = "gastoCalorico"
        private val PESO_IDEAL_COLUMN = "pesoIdeal"
        private val CATEGORIA_COLUMN = "categoria"
        private val RECOMENDACAO_AGUA_COLUMN = "recomendacaoAgua"

        val CREATE_HISTORICO_TABLE_STATEMENT = "CREATE TABLE IF NOT EXISTS $HISTORICO_TABLE (" +
                "$ID_COLUMN INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$NOME_COLUMN TEXT NOT NULL, " +
                "$IDADE_COLUMN INTEGER NOT NULL, " +
                "$SEXO_COLUMN TEXT NOT NULL, " +
                "$ALTURA_COLUMN REAL NOT NULL, " +
                "$PESO_COLUMN REAL NOT NULL, " +
                "$NIVEL_AT_COLUMN TEXT NOT NULL, " +
                "$IMC_COLUMN REAL, " +
                "$TMB_COLUMN REAL, " +
                "$GASTO_CALORICO_COLUMN REAL, " +
                "$PESO_IDEAL_COLUMN REAL, " +
                "$CATEGORIA_COLUMN TEXT, " +
                "$RECOMENDACAO_AGUA_COLUMN REAL );"
    }

    private val historicoDataBase: SQLiteDatabase = context.openOrCreateDatabase(
        HISTORICO_DATABASE_FILE,
        MODE_PRIVATE,
        null
    )

    init {
        try {
            historicoDataBase.execSQL(CREATE_HISTORICO_TABLE_STATEMENT)
        } catch (se: SQLException){
            Log.e(context.getString(R.string.app_name), se.message.toString())
        }
    }

    override fun criarHistorico(dadosPessoais: DadosPessoais): Long =
       historicoDataBase.insert(HISTORICO_TABLE, null, dadosPessoais.toContentValues())

    override fun buscarHistorico(id: Int): DadosPessoais {
        val cursor = historicoDataBase.query(
            HISTORICO_TABLE,
            null,
            "$ID_COLUMN = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        return if (cursor.moveToFirst()){
            cursor.toDadosPessoais()
        }else{
            DadosPessoais()
        }
    }

    override fun buscarHistoricos(): MutableList<DadosPessoais> {
        val historicoList: MutableList<DadosPessoais> = mutableListOf()
        val cursor = historicoDataBase.rawQuery("SELECT * FROM $HISTORICO_TABLE;", null)

        while (cursor.moveToNext()){
            historicoList.add(cursor.toDadosPessoais())
        }

        return historicoList
    }

    override fun atualizarHistorico(dadosPessoais: DadosPessoais): Int = historicoDataBase.update(
        HISTORICO_TABLE,
        dadosPessoais.toContentValues(),
        "$ID_COLUMN = ?",
        arrayOf(dadosPessoais.id.toString()))

    override fun deleteHistorico(id: Int): Int = historicoDataBase.delete(
        HISTORICO_TABLE,
        "$ID_COLUMN = ?",
        arrayOf(id.toString())
    )

    private fun DadosPessoais.toContentValues() = ContentValues().apply {
        put(NOME_COLUMN, nome)
        put(IDADE_COLUMN, idade)
        put(SEXO_COLUMN, sexo)
        put(ALTURA_COLUMN, altura)
        put(PESO_COLUMN, peso)
        put(NIVEL_AT_COLUMN, nivelAtividade)
        put(IMC_COLUMN, imc)
        put(TMB_COLUMN, tmb)
        put(GASTO_CALORICO_COLUMN, gastoCalorico)
        put(PESO_IDEAL_COLUMN, pesoIdeal)
        put(CATEGORIA_COLUMN, categoriaImc)
        put(RECOMENDACAO_AGUA_COLUMN, recomendacaoAgua)
    }

    private fun Cursor.toDadosPessoais() = DadosPessoais(
        getInt(getColumnIndexOrThrow(ID_COLUMN)),
        getString(getColumnIndexOrThrow(NOME_COLUMN)),
        getInt(getColumnIndexOrThrow(IDADE_COLUMN)),
        getString(getColumnIndexOrThrow(SEXO_COLUMN)),
        getDouble(getColumnIndexOrThrow(ALTURA_COLUMN)),
        getDouble(getColumnIndexOrThrow(PESO_COLUMN)),
        getString(getColumnIndexOrThrow(NIVEL_AT_COLUMN)),
        getDouble(getColumnIndexOrThrow(IMC_COLUMN)),
        getDouble(getColumnIndexOrThrow(TMB_COLUMN)),
        getDouble(getColumnIndexOrThrow(GASTO_CALORICO_COLUMN)),
        getDouble(getColumnIndexOrThrow(PESO_IDEAL_COLUMN)),
        getString(getColumnIndexOrThrow(CATEGORIA_COLUMN)),
        getDouble(getColumnIndexOrThrow(RECOMENDACAO_AGUA_COLUMN)),
    )
}