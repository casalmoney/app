package br.com.casalmoney.app.unauthenticated

import br.com.casalmoney.app.Audit

class BusinessAudit(private val prefix: String) : Audit {
    override fun auditObj(obj: Any) {
        println("$prefix $obj")
    }

    override fun auditString(str: String) {
        println("$prefix $str")
    }
}