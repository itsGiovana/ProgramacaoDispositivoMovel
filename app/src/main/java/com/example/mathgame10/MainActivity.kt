package com.example.mathgame10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var vibrator: Vibrator
    private lateinit var num1: TextView
    private lateinit var num2: TextView
    private lateinit var operacao: TextView
    private lateinit var resposta: EditText
    private lateinit var verificar: Button
    private lateinit var limpar: Button
    private lateinit var certo: TextView
    private lateinit var errado: TextView
    private var respC: Int = 0
    private var respE: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        vibrator = getSystemService(VIBRATOR_SERVICE) as Vibrator
        num1 = findViewById(R.id.txt_numero1)
        num2 = findViewById(R.id.txt_numero2)
        operacao = findViewById(R.id.operacao)
        resposta = findViewById(R.id.txt_resposta)
        verificar = findViewById(R.id.btn_verificar)
        limpar = findViewById(R.id.btn_limpar)
        certo = findViewById(R.id.txt_certo)
        errado = findViewById(R.id.txt_errado)

        gerarPergunta()
        verificar.setOnClickListener {
            verificarResposta()
            gerarPergunta()
        }

        limpar.setOnClickListener {
            resposta.text.clear()
        }
    }
    private fun gerarPergunta() {
        val num1Random = Random.nextInt(1, 101)
        val num2Random = Random.nextInt(1, 101)
        val operadorRandom = Random.nextInt(1, 5)

        val operadorString = when (operadorRandom) {
            1 -> "+"
            2 -> "-"
            3 -> "×"
            4 -> "÷"
            else -> ""
        }

        num1.text = num1Random.toString()
        num2.text = num2Random.toString()
        operacao.text = operadorString
    }

    private fun verificarResposta() {
        val respostaUsuario = resposta.text.toString().trim()
        if (respostaUsuario.isNotEmpty()) {
            val num1Value = num1.text.toString().toInt()
            val num2Value = num2.text.toString().toInt()
            val operacaoValue = operacao.text.toString()

            val respostaCorreta = when (operacaoValue) {
                "+" -> num1Value + num2Value
                "-" -> num1Value - num2Value
                "×" -> num1Value * num2Value
                "÷" -> num1Value / num2Value
                else -> 0
            }

            val respostaUsuarioInt = respostaUsuario.toInt()

            if (respostaUsuarioInt == respostaCorreta) {
                vibrar(200)
                respC++
            } else {
                vibrar(100)
                respE++
            }
            certo.text = "Quantidade de acertos: $respC"
            errado.text = "Quantidade de erros: $respE"
        }
    }

    private fun vibrar(durationMillis: Long) {
        if (vibrator.hasVibrator()) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                vibrator.vibrate(
                    VibrationEffect.createOneShot(
                        durationMillis,
                        VibrationEffect.DEFAULT_AMPLITUDE
                    )
                )
            } else {
                vibrator.vibrate(durationMillis)
            }
        }
    }
}