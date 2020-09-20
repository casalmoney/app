package br.com.casalmoney.app.unauthenticated.domain

import java.util.*

data class Message (var isUser: Boolean = false, var text: String = "", var time: String = "")

class MessageResult(var message: String = "", endConversation: Boolean = false, date: Date = Date())