package br.com.casalmoney.app.unauthenticated.domain

data class Message (var isUser: Boolean = false, var message: String = "", var time: String = "")