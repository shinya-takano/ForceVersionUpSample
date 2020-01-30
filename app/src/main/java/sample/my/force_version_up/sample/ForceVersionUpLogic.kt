package sample.my.force_version_up.sample

class ForceVersionUpLogic {

    fun checkForceVersionUp(serverVersion: String, currentVersion: String): Boolean {

        val splitServerVersion = serverVersion.split(".")
        val splitCurrentVersion = currentVersion.split(".")

        if (splitServerVersion.size != 3 || splitCurrentVersion.size != 3) {
            return false
        }

        // check all parts is digit
        for (i in 0 until 3) {
            checkNotNull(splitServerVersion[i].toIntOrNull()) { return false }
            checkNotNull(splitCurrentVersion[i].toIntOrNull()) { return false }
        }

        for (i in 0 until 3) {
            val serverVersionPart = splitServerVersion[i].toInt()
            val currentVersionPart = splitCurrentVersion[i].toInt()

            if (serverVersionPart > currentVersionPart) {
                return true
            } else if (serverVersionPart < currentVersionPart) {
                return false
            }
        }
        // version is equal
        return false
    }
}