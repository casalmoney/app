package br.com.casalmoney.app.unauthenticated.view.adapter

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import br.com.casalmoney.app.R
import br.com.casalmoney.app.unauthenticated.domain.Message

class ChatAdapter(private val activity: Activity, messages: ArrayList<Message>) : BaseAdapter() {

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

        viewHolder.message.text = messagesList[index].message
        viewHolder.time.text = messagesList[index].time
        viewHolder.user.text = messagesList[index].isUser.toString()

        return view
    }
}

private class ViewHolder(view: View?) {
    val message: TextView
    val time: TextView
    val user: TextView

    init {
        this.message = view?.findViewById<TextView>(R.id.message_text)!!
        this.time = view?.findViewById<TextView>(R.id.message_time)!!
        this.user = view?.findViewById<TextView>(R.id.message_user)!!
    }
}