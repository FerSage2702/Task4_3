// Уникальное название для алфавита
val russianAlphabet = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ"
val alphabetSize = russianAlphabet.length // Размер алфавита

// Функция для создания стандартной таблицы Виженера
fun createStandardTable(): List<String> {
    val table = mutableListOf<String>()
    for (i in 0 until alphabetSize) {
        table.add(russianAlphabet.substring(i) + russianAlphabet.substring(0, i))
    }
    return table
}

// Функция для генерации повторяющегося ключа
fun generateKey(message: String, key: String): String {
    val keyRepeats = (message.length + key.length - 1) / key.length // Количество повторов ключа
    return (key.repeat(keyRepeats)).substring(0, message.length).uppercase()
}

// Функция для шифрования
fun encrypt(message: String, key: String, table: List<String>): String {
    val encryptedMessage = StringBuilder()
    for (i in message.indices) {
        val messageChar = message[i].uppercaseChar()
        val keyChar = key[i].uppercaseChar()

        val row = russianAlphabet.indexOf(messageChar) // Находим строку по сообщению
        val col = russianAlphabet.indexOf(keyChar) // Находим столбец по ключу

        if (row != -1 && col != -1) {
            encryptedMessage.append(table[col][row]) // Пересечение строки и столбца
        } else {
            encryptedMessage.append(messageChar) // Если символ не в алфавите
        }
    }
    return encryptedMessage.toString()
}

// Функция для расшифровки
fun decrypt(encryptedMessage: String, key: String, table: List<String>): String {
    val decryptedMessage = StringBuilder()
    for (i in encryptedMessage.indices) {
        val encryptedChar = encryptedMessage[i].uppercaseChar()
        val keyChar = key[i].uppercaseChar()

        val col = russianAlphabet.indexOf(keyChar) // Находим столбец по ключу
        if (col != -1) {
            // Находим строку, в которой находится зашифрованная буква
            val row = table[col].indexOf(encryptedChar)
            if (row != -1) {
                decryptedMessage.append(russianAlphabet[row]) // Восстанавливаем оригинальную букву по строке
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

    // Генерация ключа нужной длины
    val fullKey = generateKey(message, key)

    // Создание стандартной таблицы Виженера
    val vigenereTable = createStandardTable()

    // Шифрование
    val encryptedMessage = encrypt(message, fullKey, vigenereTable)

    // Вывод результата
    println("\nИсходное сообщение: $message")
    println("Ключ:               $fullKey")
    println("Зашифрованное сообщение: $encryptedMessage")

    // Расшифровка
    val decryptedMessage = decrypt(encryptedMessage, fullKey, vigenereTable)
    println("Расшифрованное сообщение: $decryptedMessage")
}
