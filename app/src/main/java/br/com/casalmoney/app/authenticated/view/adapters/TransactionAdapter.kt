package br.com.casalmoney.app.authenticated.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.casalmoney.app.R
import br.com.casalmoney.app.authenticated.domain.Transaction
import br.com.casalmoney.app.databinding.ItemTransactionBinding

class TransactionAdapter(
    private val transactionList: List<Transaction>,
    private val onItemClick: ((Transaction) -> Unit)
): RecyclerView.Adapter<TransactionAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        binding.transaction = transactionList[position]
        binding.executePendingBindings()
    }

    override fun getItemCount() = transactionList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val binding = ItemTransactionBinding.bind(itemView)

        init {
            itemView.setOnClickListener{
                onItemClick.invoke(transactionList[adapterPosition])
            }
        }
    }
}