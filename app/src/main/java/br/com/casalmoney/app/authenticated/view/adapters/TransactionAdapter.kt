package br.com.casalmoney.app.authenticated.view.adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.domain.Transaction
import kotlinx.android.synthetic.main.transaction_item.view.*

class TransactionAdapter(
    activity: Activity,
    private val transactionList: ArrayList<Transaction>
): RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(activity)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = inflater.inflate(R.layout.transaction_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindView(transactionList[position])
    }

    override fun getItemCount() = transactionList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val title: TextView = itemView.title
        private val description: TextView = itemView.description

        fun bindView(note: Transaction) {
            title.text = note.date
            description.text = note.description
        }
    }
}