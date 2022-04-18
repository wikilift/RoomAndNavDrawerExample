package com.wikilift.tfg.ui.creationpet.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.wikilift.tfg.R

/*
class CustomDropDownAdapter(val context: Context, var arrayOfTypes: List<String>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view: View
        val vh: ItemHolder
        if (convertView == null) {
            view = inflater.inflate(R.layout.custom_drop_down_adapter, parent, false)
            vh = ItemHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemHolder
        }
        vh.label.text = arrayOfTypes[position]
        when(arrayOfTypes[position]){
            "Tipo de animal"->{
                Glide.with(context).load(R.drawable.ic_baseline_arrow_drop_down_24).centerCrop().into(vh.img)
            }
            "Perro"->{
                Glide.with(context).load(R.drawable.ic_bulldog_1456110).centerCrop().into(vh.img)
            }
            "Gato"->{
                Glide.with(context).load(R.drawable.cat).centerCrop().into(vh.img)
            }
            "Conejo"->{
                Glide.with(context).load(R.drawable.ic_rabbit).centerCrop().into(vh.img)
            }
            "Pez"->{
                Glide.with(context).load(R.drawable.fish).centerCrop().into(vh.img)
            }
            "Tortuga"->{  Glide.with(context).load(R.drawable.turtle).centerCrop().into(vh.img)}
            "Serpiente"->{
                Glide.with(context).load(R.drawable.ic_dragon_2023884).centerCrop().into(vh.img)
            }
            "Especial"->{
                Glide.with(context).load(R.drawable.ic_be_amazed_5062138).centerCrop().into(vh.img)
            }

        }


        return view
    }

    override fun getItem(position: Int): Any? {
        return arrayOfTypes[position];
    }

    override fun getCount(): Int {
        return arrayOfTypes.size;
    }

    override fun getItemId(position: Int): Long {
        return position.toLong();
    }

    private class ItemHolder(row: View?) {
        val label: TextView
        val img: ImageView

        init {
            label = row?.findViewById(R.id.text) as TextView
            img = row?.findViewById(R.id.img) as ImageView
        }
    }

}

 */