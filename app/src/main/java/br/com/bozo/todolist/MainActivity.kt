package br.com.bozo.todolist

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.activityUiThreadWithContext
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_CADASTRO: Int = 1
        private const val DO: String = "ToDo"
        private const val TODOLIST: String = "ToDoList"
    }

    var toDoList: MutableList<ToDo> = mutableListOf()
    var indexLista: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnAdd.setOnClickListener{
            val addToDo = Intent(this,AddActivity::class.java)
            startActivity(addToDo)
        }
    }

    override fun onResume() {
        super.onResume()
        carregaLista()
    }

    /*override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == REQUEST_CADASTRO && resultCode == Activity.RESULT_OK){
            val toDo: ToDo = data!!.getSerializableExtra(DO) as ToDo
            if(indexLista >= 0){
                toDoList[indexLista] = toDo
                indexLista = -1
            }else {
                toDoList.add(toDo)
            }
        }

    }*/

    private fun carregaLista() {
        val toDoDao = AppDatabase.getInstance(this).toDoDAO()

        doAsync {
            toDoList = toDoDao.getAll() as MutableList<ToDo>

            activityUiThreadWithContext {
                val adapter = ToDoAdapter(this@MainActivity, toDoList)

                adapter.setOnItemClickListener { toDo, indexLista ->
                    this@MainActivity.indexLista = indexLista
                    val editaToDo = Intent(this@MainActivity, AddActivity::class.java)
                    editaToDo.putExtra(DO, toDo)
                    this@MainActivity.startActivityForResult(editaToDo, REQUEST_CADASTRO)
                }

                adapter.setOnItemClickListenerDone { toDo ->
                    doAsync {
                        Log.d("Meu Deus", indexLista.toString())
                        //if(toDoList[indexLista].data )
                        toDoDao.delete(toDo)
                        uiThread {
                            carregaLista()
                        }
                    }
                }

            val layoutManager = LinearLayoutManager(this)

            rvToDo.adapter = adapter
            rvToDo.layoutManager = layoutManager

            }
        }
    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)

        outState?.putSerializable(TODOLIST, toDoList as ArrayList<String>)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)

        if(savedInstanceState != null)
            toDoList= savedInstanceState.getSerializable(TODOLIST) as MutableList<ToDo>
    }


}
