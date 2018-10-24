package br.com.bozo.todolist

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    companion object {
        private const val DO: String = "ToDo"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        val toDo: String? = intent.getStringExtra(DO)
        if(toDo!= null){
            edtToDo.setText(toDo)
        }

        btnSalvar.setOnClickListener{
            salvaToDo()
        }
    }


    private fun salvaToDo() {

        if(edtToDo.text.isEmpty()){
            edtToDo.requestFocus()
            edtToDo.setError(getString(R.string.campo_obriatorio))
            return
        }

        val toDo = edtToDo.text.toString()

        val abreLista = Intent(this, MainActivity::class.java)
        abreLista.putExtra(DO, toDo)
        setResult(Activity.RESULT_OK, abreLista)
        finish()

    }

}
