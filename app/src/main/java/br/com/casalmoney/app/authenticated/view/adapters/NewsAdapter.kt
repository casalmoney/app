package br.com.casalmoney.app.authenticated.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.domain.News
import br.com.casalmoney.app.databinding.ItemNewsHelpBinding
import coil.load

class NewsAdapter(
    private val newsList: List<News>,
    private val onItemClick: ((News) -> Unit)
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NewsAdapter.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_news_help, parent, false)
        return ViewHolder(view)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        binding.news = newsList[position]
        binding.ivNews.load(newsList[position].image)
        binding.notifyChange()
        binding.executePendingBindings()
    }

    override fun getItemCount() = newsList.size


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemNewsHelpBinding.bind(itemView)

        init {
            itemView.setOnClickListener {
                onItemClick.invoke(newsList[adapterPosition])
            }
        }
    }
}