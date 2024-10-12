val russianAlphabet = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
val alphabetSize = russianAlphabet.length

fun createStandardTable(): List<String> {
    val table = mutableListOf<String>()
    for (i in 0 until alphabetSize) {
        table.add(russianAlphabet.substring(i) + russianAlphabet.substring(0, i))
    }
    return table
}

fun generateKey(message: String, key: String): String {
    val keyRepeats = (message.length + key.length - 1) / key.length
    return (key.repeat(keyRepeats)).substring(0, message.length).uppercase()
}

fun encrypt(message: String, key: String, table: List<String>): String {
    val encryptedMessage = StringBuilder()
    for (i in message.indices) {
        val messageChar = message[i].uppercaseChar()
        val keyChar = key[i].uppercaseChar()

        val row = russianAlphabet.indexOf(messageChar)
        val col = russianAlphabet.indexOf(keyChar) 

                if (row != -1 && col != -1) {
                    encryptedMessage.append(table[col][row])
                } else {
                    encryptedMessage.append(messageChar)
                }
    }
    return encryptedMessage.toString()
}

fun decrypt(encryptedMessage: String, key: String, table: List<String>): String {
    val decryptedMessage = StringBuilder()
    for (i in encryptedMessage.indices) {
        val encryptedChar = encryptedMessage[i].uppercaseChar()
        val keyChar = key[i].uppercaseChar()

        val col = russianAlphabet.indexOf(keyChar)
        if (col != -1) {
            val row = table[col].indexOf(encryptedChar)
            if (row != -1) {
                decryptedMessage.append(russianAlphabet[row])
            } else {
                decryptedMessage.append(encryptedChar)
            }
        } else {
            decryptedMessage.append(encryptedChar)
        }
    }
    return decryptedMessage.toString()
}

fun main() {
    println("Введите сообщение:")
    val message = readLine()?.uppercase() ?: ""

    println("Введите ключ:")
    val key = readLine()?.uppercase() ?: ""

    val fullKey = generateKey(message, key)

    val vigenereTable = createStandardTable()

    val encryptedMessage = encrypt(message, fullKey, vigenereTable)

    println("\nИсходное сообщение: $message")
    println("Ключ:               $fullKey")
    println("Зашифрованное сообщение: $encryptedMessage")

    val decryptedMessage = decrypt(encryptedMessage, fullKey, vigenereTable)
    println("Расшифрованное сообщение: $decryptedMessage")
}
