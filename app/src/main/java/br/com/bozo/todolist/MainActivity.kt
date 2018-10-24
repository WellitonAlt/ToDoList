package br.com.bozo.todolist

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CADASTRO: Int = 1
        private const val DO: String = "ToDo"
        private const val TODOLIST: String = "ToDoList"
    }

    var toDoList: MutableList<String> = mutableListOf()
    var indexLista: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd.setOnClickListener{
            val addToDo = Intent(this,AddActivity::class.java)
            startActivityForResult(addToDo, REQUEST_CADASTRO)
        }
    }

    override fun onResume() {
        super.onResume()
        carregaLista()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CADASTRO && resultCode == Activity.RESULT_OK){
            val toDo: String = data!!.getStringExtra(DO)
            if(indexLista >= 0){
                toDoList[indexLista] = toDo
                indexLista = -1
            }else {
                toDoList.add(toDo)
            }
        }

    }

    private fun carregaLista() {
        val adapter = ToDoAdapter(this, toDoList)

        adapter.setOnItemClickListener { toDo, indexLista ->
            this.indexLista = indexLista
            val editaToDo = Intent(this, AddActivity::class.java)
            editaToDo.putExtra(DO, toDo)
            this.startActivityForResult(editaToDo, REQUEST_CADASTRO)
        }

        adapter.setOnItemClickListenerDone { indexLista ->
            Log.d("Meu Deus", indexLista.toString())
            if(!toDoList[indexLista].isEmpty())
                toDoList.removeAt(indexLista)
            carregaLista()

        }

        val layoutManager = LinearLayoutManager(this)

        rvToDo.adapter = adapter
        rvToDo.layoutManager = layoutManager
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putStringArrayList(TODOLIST, toDoList as ArrayList<String>)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        if(savedInstanceState != null)
            toDoList= savedInstanceState.getStringArrayList(TODOLIST) as MutableList<String>
    }


}
