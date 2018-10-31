package br.com.bozo.todolist

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_add.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.text.SimpleDateFormat


class AddActivity : AppCompatActivity() {

    companion object {
        private const val DO: String = "ToDo"
    }


    var toDo: ToDo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        toDo = intent.getSerializableExtra(DO) as ToDo?
        if(toDo!= null){
            edtToDo.setText(toDo?.toDo)
        }

        btnSalvar.setOnClickListener{
            salvaToDo()
        }
    }


    @SuppressLint("SimpleDateFormat")
    private fun salvaToDo() {

        if(edtToDo.text.isEmpty()){
            edtToDo.requestFocus()
            edtToDo.error = getString(R.string.campo_obriatorio)
            return
        }
        val toDoDao = AppDatabase.getInstance(this).toDoDAO()
        doAsync {
            if(toDo!= null){ //Atualização
                toDo!!.toDo = edtToDo.text.toString()
                toDoDao.insert(toDo!!)
            }else{
                val uDate = java.util.Date()
                val dateFormat = SimpleDateFormat("dd-MM-yyyy")
                val strDate = dateFormat.format(uDate)
                val toDoNovo = ToDo(edtToDo.text.toString(), strDate)
                toDoDao.insert(toDoNovo)
            }
            uiThread {
                finish()
            }
        }
    }

}
