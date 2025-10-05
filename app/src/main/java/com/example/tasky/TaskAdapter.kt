package com.example.tasky

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tasky.databinding.ItemTaskBinding

class TaskAdapter(private val tasks: List<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.binding.tvTaskTitle.text = task.title
        holder.binding.tvTaskDate.text = task.date

        //  Reflect current state
        holder.binding.cbDone.isChecked = task.isDone
        applyStrikeThrough(holder, task.isDone)

        //  Update when user clicks checkbox
        holder.binding.cbDone.setOnCheckedChangeListener { _, isChecked ->
            task.isDone = isChecked
            applyStrikeThrough(holder, isChecked)
        }
    }

    override fun getItemCount() = tasks.size

    // Helper function → adds/removes strikethrough
    private fun applyStrikeThrough(holder: TaskViewHolder, isDone: Boolean) {
        if (isDone) {
            holder.binding.tvTaskTitle.paintFlags =
                holder.binding.tvTaskTitle.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            holder.binding.tvTaskTitle.paintFlags =
                holder.binding.tvTaskTitle.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}
