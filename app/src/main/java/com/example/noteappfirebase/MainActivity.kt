package com.example.noteappfirebase

import android.health.connect.datatypes.units.Length
import android.os.Bundle
import android.util.Log
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteappfirebase.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private var firebaseDatabase: FirebaseDatabase?= null
    private var databaseReference: DatabaseReference?= null

    private var adapter: NoteAdapter?=null
    private var list = mutableListOf<Note>()

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        RecyclerView()

        firebaseDatabase = FirebaseDatabase.getInstance()
        databaseReference = firebaseDatabase?.getReference("data")
        getData()

        binding.buttonSave.setOnClickListener {
            saveData()
        }
    }

    private fun RecyclerView() {
        adapter = NoteAdapter()
        binding.recyclerviewNote.layoutManager = LinearLayoutManager(this)
        binding.recyclerviewNote.adapter = adapter
    }

    private fun saveData() {
        val title = binding.editTextTitle.text.toString()
        val description = binding.editTextDescription.text.toString()
        val note = Note(title= title, description = description)

        val NewNoteReference = databaseReference?.push()
        NewNoteReference?.setValue(note)
    }



    private fun getData() {
        databaseReference?.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                list.clear()




                for (ds in snapshot.children){
                    val id = ds.key
                    val title = ds.child("title").value.toString()
                    val description = ds.child("description").value.toString()

                    val Note = Note ( id= id,title= title, description= description )
                    list.add(Note)

                    Log.d("data","size: ${list.size}")
                    adapter?.setData(list)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.d("data","onDataCancelled: ${error.toException()}")
            }
        })
    }
}