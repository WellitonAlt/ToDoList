package br.com.bozo.todolist

import android.arch.persistence.room.*


@Dao
interface ToDoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(toDo: ToDo)

    @Query("SELECT * FROM todo")
    fun getAll(): List<ToDo>

    @Delete
    fun delete(toDo: ToDo)

}