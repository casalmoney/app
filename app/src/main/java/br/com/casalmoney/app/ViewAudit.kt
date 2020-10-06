package br.com.casalmoney.app

class ViewAudit(private val prefix: String) : Audit {
    override fun auditObj(obj: Any) {
        println("$prefix $obj")
    }

    override fun auditString(str: String) {
        println("$prefix $str")
    }
}