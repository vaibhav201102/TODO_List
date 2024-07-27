package com.vaibhavjoshi.todolist.view.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vaibhavjoshi.todolist.view.dialog.DialogAddNotes
import com.vaibhavjoshi.todolist.R
import com.vaibhavjoshi.todolist.database.TodoList
import com.vaibhavjoshi.todolist.databinding.ActivityMainBinding
import com.vaibhavjoshi.todolist.view.adapter.NotesAdapter
import com.vaibhavjoshi.todolist.view.viewModels.AddItemViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import java.util.Date

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var hostList: MutableList<TodoList>

    private val addItemViewModel : AddItemViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        bindRecyclerView()
        observeNotes()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_add_notes,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.add_note_menu_item){
            val dialogAddNotes  = DialogAddNotes()
            val args = Bundle()
            args.putBoolean("IsUpdate",false)
            dialogAddNotes.show(supportFragmentManager,"AddCustomerDialog")
            dialogAddNotes.onAddItem = {
                val noteDateAdded = Date()
                addItemViewModel.addListItem(TodoList(noteDateAdded,it))
            }
            dialogAddNotes.arguments = args
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun bindRecyclerView(){
        hostList = mutableListOf()

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)

        val recyclerViewScanItemList =binding.todoListRv
        // pass it to rvLists layoutManager
        recyclerViewScanItemList.layoutManager = layoutManager

        // initialize the adapter,
        // and pass the required argument
        notesAdapter = NotesAdapter(hostList)

        notesAdapter.onItemEditClick = { it ->
            val dialogAddNotes  = DialogAddNotes()
            val args = Bundle()

            val date = it.dateAddedItem
            val text = it.listNoteText
            args.putBoolean("IsUpdate",true)
            args.putString("text",it.listNoteText)
            dialogAddNotes.arguments = args
            dialogAddNotes.show(supportFragmentManager,"AddCustomerDialog")
            dialogAddNotes.onUpdateItem = {

                val updatedText = it.ifEmpty { text }
                val editedNote = TodoList(date, updatedText)

                lifecycleScope.launch {
                    addItemViewModel.updateListItem(editedNote)
                }
            }
        }

        notesAdapter.onItemDeleteClick = { _, position ->
            val notesList = notesAdapter.hostList.toMutableList()
            val noteText = notesList[position].listNoteText
            val noteDateAdded = notesList[position].dateAddedItem
            val removeNote = TodoList(noteDateAdded, noteText)
            lifecycleScope.launch {
                addItemViewModel.deleteListItem(removeNote)
            }
            notesList.removeAt(position)
            notesAdapter.updateList(notesList)
        }

        // attach adapter to the recycler view
        recyclerViewScanItemList.adapter = notesAdapter
    }

    private fun observeNotes() {
        lifecycleScope.launch {
            addItemViewModel.getList().collect { notesList ->
                if (notesList.isNotEmpty()) {
                    notesAdapter.updateList(notesList)
                }
            }
        }
    }
}