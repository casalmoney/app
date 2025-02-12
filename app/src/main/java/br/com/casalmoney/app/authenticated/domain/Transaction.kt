package br.com.casalmoney.app.authenticated.domain

data class Transaction(
    var id: Int,
    var title: String,
    var explanation: String,
    var amount: String,
    var date: String,
    var location: Location? = null
)