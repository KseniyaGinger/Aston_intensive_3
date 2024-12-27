package ru.aston.aston_intensive_3

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import ru.aston.aston_intensive_3.databinding.ItemContactBinding


class PersonAdapter(val contactActionListener: ContactActionListener):
    RecyclerView.Adapter<PersonAdapter.PersonViewHolder>(), View.OnClickListener {

    var data: List<Person> = emptyList()
        @SuppressLint("NotifyDataSetChanged")
        set(newValue) {
            field = newValue
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemContactBinding.inflate(inflater, parent, false)

        binding.root.setOnClickListener(this)
        binding.more.setOnClickListener(this)

        return PersonViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = data[position]

        with(holder.binding) {

            holder.itemView.tag = person
            more.tag = person

            nameTextView.text = person.name
            numberTextView.text = person.phoneNumber
        }
    }

    class PersonViewHolder(val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onClick(view: View) {
        val person: Person = view.tag as Person

        when (view.id) {
            R.id.more -> showPopupMenu(view)
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(view.context, view)
        val person = view.tag as Person
        val position = data.indexOfFirst { it.id == person.id }

        popupMenu.menu.add(0, ID_REMOVE, Menu.NONE, "Remove")

        popupMenu.setOnMenuItemClickListener {
            when (it.itemId) {
                ID_REMOVE -> contactActionListener.onPersonRemove(person)
            }
            return@setOnMenuItemClickListener true
        }

        popupMenu.show()
    }

    companion object {
        private const val ID_REMOVE = 3
    }
}