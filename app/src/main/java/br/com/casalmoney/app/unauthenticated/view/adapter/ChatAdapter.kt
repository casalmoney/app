package br.com.casalmoney.app.unauthenticated.view.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.casalmoney.app.R
import br.com.casalmoney.app.unauthenticated.domain.Message
import kotlinx.android.synthetic.main.message.view.*
import kotlin.collections.ArrayList

class ChatAdapter(
    private val activity: Activity,
    messages: ArrayList<Message>) : BaseAdapter() {

    private var messagesList = ArrayList<Message>()
    private val inflater : LayoutInflater = LayoutInflater.from(activity)

    init {
        messagesList = messages
    }

    override fun getCount(): Int {
        return messagesList.size
    }

    override fun getItem(item: Int): Any {
        return item
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("ViewHolder", "InflateParams")
    override fun getView(index: Int, convertView: View?, parent: ViewGroup?): View? {

        val view: View?
        val viewHolder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.message, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = view.tag as ViewHolder
        }

        viewHolder.bindView(messagesList.get(index))

        return view
    }
}

private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val messageText: TextView = itemView.message_text
    val time: TextView = itemView.message_time
    val user: TextView = itemView.message_user

    fun bindView(message: Message) {
        messageText.text = message.text
        time.text = message.time
        user.text =  ""//messagesList[index].isUser.toString()

        messageText.gravity = if (message.isUser) Gravity.END else Gravity.START
    }
}