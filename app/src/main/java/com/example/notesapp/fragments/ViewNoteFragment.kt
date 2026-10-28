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
import com.example.notesapp.databinding.FragmentViewNoteBinding
import com.example.notesapp.room.Note
import com.example.notesapp.utils.CustomFirebaseLogger
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory

class ViewNoteFragment : Fragment() {

    private var _binding: FragmentViewNoteBinding? = null
    private val binding get() = _binding!!
    private val args: ViewNoteFragmentArgs by navArgs()
    private val viewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(
            (requireActivity().application as App).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as? AppCompatActivity)?.supportActionBar?.title = getString(R.string.view_note)
        val note: Note = args.note
        initViews(note)
    }

    private fun initViews(note: Note) {
        binding.title.text = note.name
        binding.description.text = note.description
        binding.edit.setOnClickListener {
            val action = ViewNoteFragmentDirections.actionViewNoteFragmentToAddEditNoteFragment(
                note = note,
                isAdd = false
            )
            findNavController().navigate(action)
        }
        binding.delete.setOnClickListener {
            CustomFirebaseLogger.logCustomEvent(CustomFirebaseLogger.Action.REMOVE_NOTE, note)
            viewModel.deleteNote(note)
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}