package rs.raf.nutritiontracker.presentation.view.recycler.adapter

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import rs.raf.nutritiontracker.R
import rs.raf.nutritiontracker.data.model.api.CategoryApiResponse

class CategoryAdapter(
    private val categories: List<CategoryApiResponse>,
    private val onCategoryClick: (CategoryApiResponse) -> Unit
    ) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvCategoryName: TextView = view.findViewById(R.id.tvCategoryName)
        val ivMore: ImageView = view.findViewById(R.id.ivMore)
        val ivCategoryImage: ImageView = view.findViewById(R.id.ivCategoryImage)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount() = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        setCategoryName(holder, category)
        setMoreClickListener(holder, category)
        loadCategoryImage(holder, category)
        holder.itemView.setOnClickListener {
            onCategoryClick(category)
        }
    }

    private fun setCategoryName(holder: CategoryViewHolder, category: CategoryApiResponse) {
        holder.tvCategoryName.text = category.strCategory
    }

    private fun setMoreClickListener(holder: CategoryViewHolder, category: CategoryApiResponse) {
        holder.ivMore.setOnClickListener {
            AlertDialog.Builder(it.context)
                .setTitle(category.strCategory)
                .setMessage(category.strCategoryDescription)
                .setPositiveButton("OK", null)
                .show()
        }
    }

    private fun loadCategoryImage(holder: CategoryViewHolder, category: CategoryApiResponse) {
        Glide.with(holder.itemView.context)
            .load(category.strCategoryThumb)
            .into(holder.ivCategoryImage)
    }
}