package com.hitesh.snohassigment.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hitesh.snohassigment.TheProjectDatabase
import com.hitesh.snohassigment.adapter.TaskAdapter
import com.hitesh.snohassigment.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    lateinit var database: TheProjectDatabase

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        database = TheProjectDatabase.getDatabase(requireActivity())

        lateinit var taskAdapter: TaskAdapter
        val rvTask: RecyclerView = binding.rvTask
        database.theProjectDao().getTask().observe(viewLifecycleOwner) {
            taskAdapter = TaskAdapter(it)
            rvTask.adapter = taskAdapter
        }

        rvTask.layoutManager = LinearLayoutManager(activity)
        rvTask.setHasFixedSize(true)

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}