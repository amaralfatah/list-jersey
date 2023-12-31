package com.dicoding.todoapp.data

import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery

//TODO 2 : Define data access object (DAO)
@Dao
interface TaskDao {

    @RawQuery(observedEntities = [Task::class])
    fun getTasks(query: SupportSQLiteQuery): DataSource.Factory<Int, Task>

    @Query("SELECT * FROM tasks WHERE tasks.id = :taskId")
    fun getTaskById(taskId: Int): LiveData<Task>

    @Query("SELECT * FROM tasks WHERE completed = 0 ORDER BY dueDateMillis ASC LIMIT 1")
    fun getNearestActiveTask(): Task

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertTask(task: Task): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg tasks: Task)

    @Delete
    suspend fun deleteTask(task: Task)

    @Query("UPDATE tasks SET completed = :completed WHERE id = :taskId")
    suspend fun updateCompleted(taskId: Int, completed: Boolean)

    @Query("SELECT * FROM tasks WHERE namaPelanggan LIKE :query OR alamat LIKE :query OR noHp LIKE :query OR bahan LIKE :query OR note LIKE :query")
    fun searchTasks(query: String): DataSource.Factory<Int, Task>

    @Query("UPDATE tasks SET namaPelanggan = :nama, alamat = :alamat, noHp = :noHp, imagePath = :imagePath, bahan = :bahan, model = :model, jumlah = :jumlah, dueDateMillis = :dueDateMillis, note = :note WHERE id = :taskId")
    suspend fun updateTask(
        taskId: Int,
        nama: String,
        alamat: String,
        noHp: String,
        imagePath: String,
        bahan: String,
        model: String,
        jumlah: Int,
        dueDateMillis: Long,
        note: String
    )
}
