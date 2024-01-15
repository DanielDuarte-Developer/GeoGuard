package tvy.danielduarte.elderylocationprogram.classes

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import tvy.danielduarte.elderylocationprogram.ProfileObj
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.io.Serializable

class DataManagerFile (context: Context, filePath: String){
    private val dataFile = filePath
    private var context = context
    fun write(key: Long, value: ProfileObj) {
        val data = readData() ?: mutableMapOf()

        data[key.toString()] = value

        writeData(data)
    }

    fun read(key: Long): ProfileObj? {
        val data = readData()
        return data?.get(key.toString())
    }

    private fun readData(): MutableMap<String, ProfileObj>? {
        return try {
            val fileInputStream = context.openFileInput(dataFile)
            val inputStreamReader = InputStreamReader(fileInputStream)
            val bufferedReader = BufferedReader(inputStreamReader)

            val data = Gson().fromJson<MutableMap<String, ProfileObj>>(
                bufferedReader.readLine(),
                object : TypeToken<MutableMap<String, ProfileObj>>() {}.type
            )

            bufferedReader.close()
            data
        } catch (e: FileNotFoundException) {
            null
        } catch (e: IOException) {
            null
        }
    }

    private fun writeData(data: MutableMap<String, ProfileObj>) {
        try {
            val fileOutputStream = context.openFileOutput(dataFile, Context.MODE_PRIVATE)
            val outputStreamWriter = OutputStreamWriter(fileOutputStream)
            val bufferedWriter = BufferedWriter(outputStreamWriter)

            bufferedWriter.write(Gson().toJson(data))

            bufferedWriter.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun size(): Long {
        val file = File(context.filesDir, dataFile)
        return file.length()
    }
}