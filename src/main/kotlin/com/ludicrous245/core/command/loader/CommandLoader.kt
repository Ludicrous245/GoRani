package com.ludicrous245.core.command.loader

import java.io.File
import java.util.concurrent.ConcurrentHashMap

class CommandLoader {
    val classes: MutableMap<String, Class<*>?> = ConcurrentHashMap()

    val classLoaders: MutableMap<File, CommandClassLoader> = ConcurrentHashMap()

    @Throws(Throwable::class)
    internal fun load(file: File, description: CommandFileDescription){
        println("민자")
        require(file !in classLoaders) { "Already registered file ${file.name}" }

        val classLoader = CommandClassLoader(this, file, javaClass.classLoader)

        try {
            val commandClass = Class.forName(description.main, true, classLoader)
            val commandKClass = commandClass.kotlin

            println(commandKClass)

        } catch (e: Exception) {
            classLoader.close()
            throw e
        }
    }

    @Throws(ClassNotFoundException::class)
    internal fun findClass(name: String, skip: CommandClassLoader): Class<*> {
        var found = classes[name]

        if (found != null) return found

        for (loader in classLoaders.values) {
            if (loader === skip) continue

            try {
                found = loader.findLocalClass(name)
                classes[name] = found

                return found
            } catch (ignore: ClassNotFoundException) {

            }
        }

        throw ClassNotFoundException(name)
    }

    fun clear() {
        classes.clear()
        classLoaders.run {
            values.forEach(CommandClassLoader::close)
            clear()
        }
    }
}

private fun <T> testCreateInstance(clazz: Class<T>): T {
    try {
        return clazz.getConstructor().newInstance()
    } catch (e: Exception) {
        error("Failed to create instance ${clazz.name}")
    }
}