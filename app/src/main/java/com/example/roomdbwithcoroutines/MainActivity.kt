package com.example.roomdbwithcoroutines

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.room.Room
import com.example.roomdbwithcoroutines.db.User
import com.example.roomdbwithcoroutines.db.UserDatabase
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var db: UserDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        db = Room.databaseBuilder(applicationContext, UserDatabase::class.java, "database-name").build()

        val user1 = User("harshu", "bambure", 23, true)
        val user2 = User("harshita", "xyz", 20, false)
        val user3 = User("minu", "cutu", 10, false)
        val user4 = User("rv", "qwerty", 26, true)

        GlobalScope.launch {
            db.userDao().deleteAll()
            db.userDao().insert(user1,user2,user3,user4)
            displayUsers()
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    private suspend fun displayUsers(){
        val users: List<User> = db.userDao().getAllUsers()
        var displayText = ""
        for (user: User in users){
            displayText += "${user.name} ${user.lastname} - ${user.age}\n"
        }
        txt_display.text= displayText
    }

}