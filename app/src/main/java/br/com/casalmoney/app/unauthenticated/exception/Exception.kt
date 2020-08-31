package br.com.casalmoney.app.unauthenticated.exception

class SignupException(var code: Int?, override var message: String?): Exception(message)
class LoginException(var code: Int?, override var message: String?): Exception(message)