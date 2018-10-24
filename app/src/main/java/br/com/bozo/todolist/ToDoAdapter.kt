package br.com.bozo.todolist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.lista_layout.view.*

class ToDoAdapter(val context: Context, val toDoList: List<String>)
    : RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    var clickListener: ((toDo: String, index: Int) -> Unit)? = null
    var clickListenerDone: ((index: Int) -> Unit)? = null

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

    fun setOnItemClickListener(clique: ((toDo:String, index: Int) -> Unit)){
        this.clickListener = clique
    }

    fun setOnItemClickListenerDone(cliqueDone: ((index: Int) -> Unit)){
        this.clickListenerDone = cliqueDone
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindView(context:Context, toDO: String, clickListener: ((toDO: String, index: Int) -> Unit)?, clickListenerDone: ((index: Int) -> Unit)?) {
            itemView.txtItem.text = toDO

            if(clickListener != null) {
                itemView.setOnClickListener {
                    clickListener.invoke(toDO, adapterPosition)
                }
            }

            if(clickListenerDone != null){
                itemView.btnDone.setOnClickListener{
                    clickListenerDone!!.invoke(adapterPosition)
                }
            }

        }

    }
}