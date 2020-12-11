package br.com.casalmoney.app.utils

class NotificationCenter private constructor() {
    private val registredObjects: HashMap<String, ArrayList<Runnable>> = HashMap()

    @Synchronized
    fun addFunctionForNotification(notificationName: String, r: Runnable) {
        var list = registredObjects[notificationName]
        if (list == null) {
            list = ArrayList()
            registredObjects[notificationName] = list
        }
        list.add(r)
    }

    @Synchronized
    fun removeFunctionForNotification(notificationName: String, r: Runnable) {
        val list = registredObjects[notificationName]
        list?.remove(r)
    }

    @Synchronized
    fun postNotification(notificationName: String) {
        val list = registredObjects[notificationName]
        if (list != null) {
            for (r in list) r.run()
        }
    }

    companion object {
        //static reference for singleton
        private var _instance: NotificationCenter? = null

        //returning the reference
        @Synchronized
        fun defaultCenter(): NotificationCenter? {
            if (_instance == null) _instance = NotificationCenter()
            return _instance
        }
    }

}