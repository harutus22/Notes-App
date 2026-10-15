package com.example.notesapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.App
import com.example.notesapp.databinding.FragmentMainBinding
import com.example.notesapp.recycler_view.NoteRecyclerViewAdapter
import com.example.notesapp.viewmodel.NoteViewModel
import com.example.notesapp.viewmodel.NoteViewModelFactory

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: NoteRecyclerViewAdapter

    private val viewModel: NoteViewModel by viewModels {
        NoteViewModelFactory(
            (requireActivity().application as App).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        observeViewModel()
        initViews()
    }

    private fun initViews() {
        binding.createNote.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToEditNoteFragment(note = null, isAdd = true)
            findNavController().navigate(action)
        }
    }

    private fun observeViewModel() {
        viewModel.notes.observe(viewLifecycleOwner) { list ->
            adapter.submitList(list)
        }
    }

    private fun initAdapter() {
        adapter = NoteRecyclerViewAdapter { note ->
            val action = MainFragmentDirections.actionMainFragmentToViewNoteFragment(note = note)
            findNavController().navigate(action)
        }

        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}