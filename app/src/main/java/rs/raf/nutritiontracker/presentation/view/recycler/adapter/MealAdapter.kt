package rs.raf.nutritiontracker.presentation.view.recycler.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.model.view.MealView

class MealAdapter(
    private val meals: List<MealView>,
    private val onItemClicked: (String) -> Unit
) : RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    class MealViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvMealName: TextView = view.findViewById(R.id.mealNameTextView)
        val ivMealImage: ImageView = view.findViewById(R.id.mealImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_meal, parent, false)
        return MealViewHolder(view)
    }

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            onItemClicked(meals[position].id)
        }
        holder.tvMealName.text = meals[position].name
        Glide.with(holder.itemView.context)
            .load(meals[position].mealThumb)
            .into(holder.ivMealImage)
    }

    override fun getItemCount() = meals.size
}