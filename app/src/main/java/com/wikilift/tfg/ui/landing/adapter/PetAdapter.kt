package com.wikilift.tfg.ui.landing.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.wikilift.tfg.core.extensions.XX
import com.wikilift.tfg.core.uiutils.BaseViewHolder
import com.wikilift.tfg.data.model.room.entity.PetBase
import com.wikilift.tfg.databinding.PetItemRowBinding
import java.lang.Exception

class PetAdapter(private val listOfOrders:MutableList<PetBase>, private val onClickListener: (View, PetBase, Int) -> Unit)
    : RecyclerView.Adapter<BaseViewHolder<*>>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemViewBinding= PetItemRowBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return  PetAdapterViewHolder(itemViewBinding,parent.context)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is PetAdapterViewHolder->{holder.bind(listOfOrders[position],position)}
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun addItemAt(f: PetBase, int: Int) {
        listOfOrders.add(int,f)
        notifyDataSetChanged()

    }
    @SuppressLint("NotifyDataSetChanged")
    fun removeAt(position: Int) {
        listOfOrders.removeAt(position)
        notifyDataSetChanged()
    }

    fun returnOrder(pos:Int): PetBase {
        return listOfOrders[pos]
    }
    override fun getItemCount(): Int =listOfOrders.size



    private inner class PetAdapterViewHolder(val binding: PetItemRowBinding, val context: Context):
        BaseViewHolder<PetBase>(binding.root){

        override fun bind(item: PetBase, position: Int) {

            try {
                binding.name.text=item.name
                binding.breed.text=item.race
                Glide.with(context).load(item.imgPath).centerCrop().into(binding.imgPet)
                binding.root.setOnClickListener{
                    val positionOfList=listOfOrders[position]
                    onClickListener.invoke(it,positionOfList,position)
                }
            }catch (e:Exception){
                Log.d(XX,e.toString())
            }



        }

    }
}