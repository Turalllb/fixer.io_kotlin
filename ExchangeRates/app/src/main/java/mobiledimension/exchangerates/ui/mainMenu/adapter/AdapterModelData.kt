package mobiledimension.exchangerates.ui.mainMenu.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import mobiledimension.exchangerates.R

import mobiledimension.exchangerates.data.model.ModelData


/**
 * Created by Турал on 30.11.2017.
 */

internal class AdapterModelData(context: Context, private val layout: Int, private val modelDataList: List<ModelData>) : ArrayAdapter<ModelData>(context, layout, modelDataList) {
    private val inflater: LayoutInflater = LayoutInflater.from(context)


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View = (
            convertView ?: inflater.inflate(layout, parent, false)!!.apply {
                tag = ViewHolder().apply {
                    nameRate = findViewById<View>(R.id.name_rate) as TextView
                    rate = findViewById<View>(R.id.rate) as TextView
                }
            })
            .apply {
                with(tag as ViewHolder) {
                    with(modelDataList[position]) {
                        nameRate.text = name
                        rate.text = value.toString()
                    }
                }
            }


    fun refresh() {
        this.notifyDataSetChanged()
    }

    private inner class ViewHolder {
        internal lateinit var nameRate: TextView
        internal lateinit var rate: TextView
    }


}
