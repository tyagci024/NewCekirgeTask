
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newcekirge.FavClickInterface
import com.example.newcekirge.R
import com.example.newcekirge.adapter.UniversityAdapter
import com.example.newcekirge.databinding.ItemCityBinding
import com.example.newcekirge.model.City
import com.example.newcekirge.model.University
import com.example.newcekirge.room.UniDao
import kotlinx.coroutines.CoroutineScope

class CityAdapter(private var cities: List<City>, private val favClickInterface: FavClickInterface) : RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    inner class CityViewHolder(private val binding: ItemCityBinding,) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.imageViewToggleDetails.setOnClickListener {
                val isExpanded = expandedMap[adapterPosition] ?: false
                expandedMap[adapterPosition] = !isExpanded
                notifyItemChanged(adapterPosition)
            }
        }

        fun bind(city: City) {
            binding.textViewCityName.text = city.province

            val isExpanded = expandedMap[adapterPosition] ?: false
            binding.imageViewToggleDetails.setImageResource(if (isExpanded) R.drawable.minus else R.drawable.plus)

            binding.recyclerViewUniversities.apply {
                layoutManager = LinearLayoutManager(context)
                adapter = UniversityAdapter(city.universities,favClickInterface)
                visibility = if (isExpanded) View.VISIBLE else View.GONE

                if (city.universities.size!=0) {
                    binding.recyclerViewUniversities.apply {
                        layoutManager = LinearLayoutManager(context)
                        adapter = UniversityAdapter(city.universities, favClickInterface)
                        visibility = if (isExpanded) View.VISIBLE else View.GONE
                    }
                } else {
                    binding.recyclerViewUniversities.visibility = View.GONE
                    binding.imageViewToggleDetails.visibility=View.GONE

                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val binding = ItemCityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(cities[position])
    }

    override fun getItemCount(): Int {
        return cities.size
    }
    fun collapseAllLists() {
        expandedMap.clear()
        notifyDataSetChanged()
    }
    fun updateCities(newCities: List<City>) {
        cities = newCities
        notifyDataSetChanged()
    }

    companion object {
        private val expandedMap = HashMap<Int, Boolean>()
    }
}