package rs.raf.nutritiontracker.presentation.view.recycler.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import rs.raf.nutritiontracker.data.model.view.MealDetailsView

class MealDetailsAdapter(context: Context, mealList: List<MealDetailsView>) :
    ArrayAdapter<MealDetailsView>(context, android.R.layout.simple_dropdown_item_1line, mealList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            val inflater = LayoutInflater.from(context)
            view = inflater.inflate(android.R.layout.simple_dropdown_item_1line, parent, false)
        }

        val textView = view as TextView
        textView.text = getItem(position)?.name

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return getView(position, convertView, parent)
    }
}