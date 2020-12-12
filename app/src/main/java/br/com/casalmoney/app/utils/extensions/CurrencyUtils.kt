package br.com.casalmoney.app.utils.extensions

import java.text.NumberFormat

class CurrencyUtils {

    fun formatToCurrency(p0: CharSequence?): String {
        val cleanString: String = p0.toString().replace("""[R$,./\s/g]""".toRegex(), "")
        val parsed = cleanString.toDouble()
        return NumberFormat.getCurrencyInstance().format((parsed / 100))
    }

    fun getAmountInDouble(amount: String) : Double {
        return amount.replace("""[R$/\s/g.]""".toRegex(), "").replace(",", ".").toDouble()
    }
}