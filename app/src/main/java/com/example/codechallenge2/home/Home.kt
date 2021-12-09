package com.example.codechallenge2.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.codechallenge2.R
import com.example.codechallenge2.databinding.CardItemBinding
import com.example.codechallenge2.databinding.HomeFragmentBinding
import com.example.codechallenge2.extensions.FragmentHelper
import com.example.codechallenge2.model.Card
import com.example.codechallenge2.model.DisplayCard

class Home : Fragment() {
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = HomeFragmentBinding.inflate(inflater)

        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val recyclerView = binding.photoRecyclerView

        viewModel.hideKeyboard.observeEvent(viewLifecycleOwner) {
            FragmentHelper().hideKeyboard(this)
        }

        viewModel.cards.observe(viewLifecycleOwner) { cards ->
            recyclerView.adapter = CardItemAdapter(cards, viewLifecycleOwner)
        }

        return binding.root
    }
}

class MyViewHolder(private val binding: CardItemBinding, private val lifecycleOwner: LifecycleOwner) : RecyclerView.ViewHolder(binding.getRoot()) {

    fun bind(item: DisplayCard) {
        binding.lifecycleOwner = lifecycleOwner
        binding.card = item
        binding.executePendingBindings()
    }
}

class CardItemAdapter(private val items: List<DisplayCard>, private val lifecycleOwner: LifecycleOwner) : RecyclerView.Adapter<MyViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemBinding = DataBindingUtil.inflate<CardItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.card_item,
            parent,
            false
        )
        return MyViewHolder(itemBinding, lifecycleOwner)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount() = items.size
}