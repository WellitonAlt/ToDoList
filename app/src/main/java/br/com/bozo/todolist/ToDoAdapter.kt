package br.com.bozo.todolist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.lista_layout.view.*

class ToDoAdapter(val context: Context, val toDoList: MutableList<ToDo>)
    : RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    var clickListener: ((toDo: ToDo, index: Int) -> Unit)? = null
    var clickListenerDone: ((toDo: ToDo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return toDoList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(context, toDoList[position], clickListener, clickListenerDone)
    }

    fun setOnItemClickListener(clique: ((toDo: ToDo, index: Int) -> Unit)){
        this.clickListener = clique
    }

    fun setOnItemClickListenerDone(cliqueDone: ((toDo: ToDo) -> Unit)){
        this.clickListenerDone = cliqueDone
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(context:Context, toDO: ToDo, clickListener: ((toDO: ToDo, index: Int) -> Unit)?, clickListenerDone: ((toDo: ToDo) -> Unit)?) {
            itemView.txtItem.text = toDO.toDo
            itemView.txtData.text = toDO.data

            if(clickListener != null) {
                itemView.setOnClickListener {
                    clickListener.invoke(toDO, adapterPosition)
                }
            }

            if(clickListenerDone != null){
                itemView.btnDone.setOnClickListener{
                    clickListenerDone.invoke(toDO)
                }
            }

        }

    }
}