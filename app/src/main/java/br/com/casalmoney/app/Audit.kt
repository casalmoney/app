package br.com.casalmoney.app

interface Audit {
    fun auditObj(obj: Any)
    fun auditString(str: String)
}