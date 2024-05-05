package com.example.cardio_app.data.dataclass

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.cardio_app.R
import java.text.DecimalFormat


class StatisticAdapter (private val context: Context, private val layout: Int, private val dataList: ArrayList<Statistic>) :
    BaseAdapter() {
    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return dataList.size
    }

    override fun getItem(position: Int): Any {
        return dataList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        // Get view for row item
        val rowView = inflater.inflate(R.layout.statistic_list, parent, false).also {
            val viewHolder = ViewHolder(it)
            it.tag = viewHolder
        }
        val data = dataList[position]
        val itemViewHolder = rowView.getTag() as ViewHolder

        itemViewHolder.nameView.text = data.name
        if (position == 2 || position == 3) {
            itemViewHolder.countView.setText("${data.doub}")
        } else {
            itemViewHolder.countView.setText("${data.count}")
        }
        var count = 0
        var doub = 0.0

        /*itemViewHolder.countView.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                itemViewHolder.countView.setText(s)
            }

            override fun afterTextChanged(s: Editable?) {
                if (position == 2 || position == 3) {

                    val df = DecimalFormat("#.#")
                    val roundedNumber = java.lang.Double.valueOf(df.format(data.doub))
                    data.doub = roundedNumber
                    itemViewHolder.countView.setText("${data.doub}")
                } else {

                    itemViewHolder.countView.setText("${data.count}")
                }
            }
        })*/
        itemViewHolder.removeButton.setOnClickListener {
            if (position == 2 || position == 3) {
                doub = data.doub - 0.1
                if (doub < 0) doub = 0.0
                val df = DecimalFormat("#.#")
                val roundedNumber = java.lang.Double.valueOf(df.format(doub))
                data.doub = roundedNumber
                itemViewHolder.countView.setText("${data.doub}")
            } else {
                count = data.count - 1
                if (count < 0) count = 0
                data.count = count
                itemViewHolder.countView.setText("${data.count}")
            }
        }


        itemViewHolder.addButton.setOnClickListener {
            if (position == 2 || position == 3) {
                doub = data.doub + 0.1
                val df = DecimalFormat("#.#")
                val roundedNumber = java.lang.Double.valueOf(df.format(doub))
                data.doub = roundedNumber
                itemViewHolder.countView.setText("${data.doub}")
            } else {
                count = data.count + 1
                data.count = count
                itemViewHolder.countView.setText("${data.count}")
            }
        }

        return rowView
    }
}
    class ViewHolder(view: View) {
        val addButton: Button = view.findViewById(R.id.addButton)
        val removeButton: Button = view.findViewById(R.id.removeButton)
        val nameView: TextView = view.findViewById(R.id.nameView)
        val countView: EditText = view.findViewById(R.id.countView)
    }


