package com.example.notesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notesapp.App
import com.example.notesapp.R
import com.example.notesapp.databinding.FragmentAddEditNoteBinding
import com.example.notesapp.room.Note
import com.example.notesapp.utils.CustomFirebaseLogger
import com.example.notesapp.utils.SpecificLocalDateTimeConverter
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory

class AddEditNoteFragment : Fragment() {

    private var _binding: FragmentAddEditNoteBinding? = null
    private val binding get() = _binding!!
    private val args: AddEditNoteFragmentArgs by navArgs()
    private val viewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(
            (requireActivity().application as App).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddEditNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isAdd = args.isAdd
        (activity as? AppCompatActivity)?.supportActionBar?.title =
            if (isAdd) getString(R.string.add_note)
            else getString(R.string.edit_note)

        initViews(isAdd)
    }

    private fun initViews(add: Boolean) {
        val note: Note? = args.note
        if (!add && note != null) {
            binding.title.setText(note.name)
            binding.title.isEnabled = false
            binding.description.setText(note.description)
        }
        binding.save.setOnClickListener {
            saveNote(add, note)
        }
    }

    private fun saveNote(isAdd: Boolean, note: Note?) {
        val titleText = binding.title.text.toString()
        val descText = binding.description.text.toString()

        val newNote = if (isAdd || note == null) {
            val note = Note(
                name = titleText,
                description = descText,
                createdAt = SpecificLocalDateTimeConverter.getDate()
            )
            CustomFirebaseLogger.logCustomEvent(CustomFirebaseLogger.Action.ADD_NOTE, note)
            note
        } else {
            note.copy(
                name = titleText,
                description = descText,
                createdAt = SpecificLocalDateTimeConverter.getDate()
            )
        }
        viewModel.insertNote(note = newNote)
        val action = AddEditNoteFragmentDirections.actionAddEditNoteFragmentToMainFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}