package com.ludicrous245.core.data

import com.google.gson.JsonArray
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.ludicrous245.core.data.file.FileManager
import com.ludicrous245.core.data.file.FileParser
import com.ludicrous245.core.io.Console

object DataManager {
    private const val dataDir = "./Data"
    private const val info = "info.json"

    //create
    fun createJsonDB(name: String) {

        val realName = name.replace(".json", "")

            FileManager.createFile("$dataDir/$realName/$realName.json")

            val obj = JsonObject()

            FileManager.writeFile("$dataDir/$realName/$realName.json", obj.toString())

    }

    fun createJsonDB(name: String, location: String) {

        val realName = name.replace(".json", "")

        FileManager.createFile("$dataDir/$location/$realName.json")

        val obj = JsonObject()

        FileManager.writeFile("$dataDir/$location/$realName.json", obj.toString())

    }

    //json data single

    fun addJsonData(name: String, property: String, data: JsonElement) {
        val file = FileParser.readFileAsJSON("$dataDir/$name.json")

        if (file != null) {
            file.add(property, data)
            FileManager.writeFile("$dataDir/$name.json", file.toString())

        } else {

            val aObject = JsonObject()
            aObject.add(property, data)
            FileManager.writeFile("$dataDir/$name.json", aObject.toString())

        }
    }

    fun addJsonData(name: String, property: String, data: String) {
        val file = FileParser.readFileAsJSON("$dataDir/$name.json")

        if (file != null) {
            file.addProperty(property, data)
            FileManager.writeFile("$dataDir/$name.json", file.toString())

        } else {

            val aObject = JsonObject()
            aObject.addProperty(property, data)
            FileManager.writeFile("$dataDir/$name.json", aObject.toString())

        }
    }

    fun addJsonData(name: String, property: String, data: Char) {
        val file = FileParser.readFileAsJSON("$dataDir/$name.json")

        if (file != null) {
            file.addProperty(property, data)
            FileManager.writeFile("$dataDir/$name.json", file.toString())

        } else {

            val aObject = JsonObject()
            aObject.addProperty(property, data)
            FileManager.writeFile("$dataDir/$name.json", aObject.toString())

        }
    }

    fun addJsonData(name: String, property: String, data: Number) {
        val file = FileParser.readFileAsJSON("$dataDir/$name.json")

        if (file != null) {
            file.addProperty(property, data)
            FileManager.writeFile("$dataDir/$name.json", file.toString())

        } else {

            val aObject = JsonObject()
            aObject.addProperty(property, data)
            FileManager.writeFile("$dataDir/$name.json", aObject.toString())

        }
    }

    fun addJsonData(name: String, property: String, data: Boolean) {
        val file = FileParser.readFileAsJSON("$dataDir/$name.json")

        if (file != null) {
            file.addProperty(property, data)
            FileManager.writeFile("$dataDir/$name.json", file.toString())

        } else {

            val aObject = JsonObject()
            aObject.addProperty(property, data)
            FileManager.writeFile("$dataDir/$name.json", aObject.toString())

        }
    }

    //json data single with fileLocation

    fun addJsonData(name: String, fileLocation: String, property: String, data: JsonElement) {
        val file = FileParser.readFileAsJSON("$dataDir/$fileLocation/$name.json")

        if (file != null) {
            file.add(property, data)
            FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())

        } else {

            val aObject = JsonObject()
            aObject.add(property, data)
            FileManager.writeFile("$dataDir/$fileLocation/$name.json", aObject.toString())

        }
    }

    fun addJsonData(name: String, fileLocation: String, property: String, data: String) {
        val file = FileParser.readFileAsJSON("$dataDir/$fileLocation/$name.json")

        if (file != null) {
            file.addProperty(property, data)
            FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())

        } else {

            val aObject = JsonObject()
            aObject.addProperty(property, data)
            FileManager.writeFile("$dataDir/$fileLocation/$name.json", aObject.toString())

        }
    }

    fun addJsonData(name: String, fileLocation: String, property: String, data: Char) {
        val file = FileParser.readFileAsJSON("$dataDir/$fileLocation/$name.json")

        if (file != null) {
            file.addProperty(property, data)
            FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())

        } else {

            val aObject = JsonObject()
            aObject.addProperty(property, data)
            FileManager.writeFile("$dataDir/$fileLocation/$name.json", aObject.toString())

        }
    }

    fun addJsonData(name: String, fileLocation: String, property: String, data: Number) {
        val file = FileParser.readFileAsJSON("$dataDir/$fileLocation/$name.json")

        if (file != null) {
            file.addProperty(property, data)
            FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())

        } else {

            val aObject = JsonObject()
            aObject.addProperty(property, data)
            FileManager.writeFile("$dataDir/$fileLocation/$name.json", aObject.toString())

        }
    }

    fun addJsonData(name: String, fileLocation: String, property: String, data: Boolean) {
        val file = FileParser.readFileAsJSON("$dataDir/$fileLocation/$name.json")

        if (file != null) {
            file.addProperty(property, data)
            FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())

        } else {

            val aObject = JsonObject()
            aObject.addProperty(property, data)
            FileManager.writeFile("$dataDir/$fileLocation/$name.json", aObject.toString())

        }
    }

    //json data with location
    fun addJsonDataTo(name: String, location: String, property: String, data: JsonElement) {
        val file = FileParser.readFileAsJSON("$dataDir/$name.json")

        if (file != null) {
            val where = file.get(location)

            if (where.isJsonObject) {
                val obj = where as JsonObject

                obj.add(property, data)
                FileManager.writeFile("$dataDir/$name.json", where.toString())
            } else if (where.isJsonArray) {
                val array = where as JsonArray

                array.add(data)
                FileManager.writeFile("$dataDir/$name.json", file.toString())
            }
        } else {

            Console.printDebugMessage("data file $name is null")

        }
    }

    fun addJsonDataTo(name: String, location: String, property: String, data: String) {
        val file = FileParser.readFileAsJSON("$dataDir/$name.json")

        if (file != null) {
            val where = file.get(location)

            if (where.isJsonObject) {
                val obj = where as JsonObject

                obj.addProperty(property, data)
                FileManager.writeFile("$dataDir/$name.json", file.toString())
            } else if (where.isJsonArray) {
                val array = where as JsonArray

                array.add(data)
                FileManager.writeFile("$dataDir/$name.json", file.toString())
            }

        } else {

            Console.printDebugMessage("data file $name is null")

        }
    }

    fun addJsonDataTo(name: String, location: String, property: String, data: Char) {
        val file = FileParser.readFileAsJSON("$dataDir/$name.json")

        if (file != null) {
            val where = file.get(location)

            if (where.isJsonObject) {
                val obj = where as JsonObject

                obj.addProperty(property, data)
                FileManager.writeFile("$dataDir/$name.json", file.toString())
            } else if (where.isJsonArray) {
                val array = where as JsonArray

                array.add(data)
                FileManager.writeFile("$dataDir/$name.json", file.toString())
            }
        } else {

            Console.printDebugMessage("data file $name is null")

        }
    }

    fun addJsonDataTo(name: String, location: String, property: String, data: Number) {
        val file = FileParser.readFileAsJSON("$dataDir/$name.json")

        if (file != null) {
            val where = file.get(location)

            if (where.isJsonObject) {
                val obj = where as JsonObject

                obj.addProperty(property, data)
                FileManager.writeFile("$dataDir/$name.json", file.toString())
            } else if (where.isJsonArray) {
                val array = where as JsonArray

                array.add(data)
                FileManager.writeFile("$dataDir/$name.json", file.toString())
            }
        } else {

            Console.printDebugMessage("data file $name is null")

        }
    }

    fun addJsonDataTo(name: String, location: String, property: String, data: Boolean) {
        val file = FileParser.readFileAsJSON("$dataDir/$name.json")

        if (file != null) {
            val where = file.get(location)

            if (where.isJsonObject) {
                val obj = where as JsonObject

                obj.addProperty(property, data)
                FileManager.writeFile("$dataDir/$name.json", file.toString())
            } else if (where.isJsonArray) {
                val array = where as JsonArray

                array.add(data)
                FileManager.writeFile("$dataDir/$name.json", file.toString())
            }
        } else {

            Console.printDebugMessage("data file $name is null")

        }
    }

    //json data with fileLocation and location
    fun addJsonDataTo(name: String, fileLocation: String, location: String, property: String, data: JsonElement) {
        val file = FileParser.readFileAsJSON("$dataDir/$fileLocation/$name.json")

        if (file != null) {
            val where = file.get(location)

            if (where.isJsonObject) {
                val obj = where as JsonObject

                obj.add(property, data)
                FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())
            } else if (where.isJsonArray) {
                val array = where as JsonArray

                array.add(data)
                FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())
            }
        } else {

            Console.printDebugMessage("data file $name is null")

        }
    }

    fun addJsonDataTo(name: String, fileLocation: String, location: String, property: String, data: String) {
        val file = FileParser.readFileAsJSON("$dataDir/$fileLocation/$name.json")

        if (file != null) {
            val where = file.get(location)

            if (where.isJsonObject) {
                val obj = where as JsonObject

                obj.addProperty(property, data)
                FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())
            } else if (where.isJsonArray) {
                val array = where as JsonArray

                array.add(data)
                FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())
            }

        } else {

            Console.printDebugMessage("data file $name is null")

        }
    }

    fun addJsonDataTo(name: String, fileLocation: String, location: String, property: String, data: Char) {
        val file = FileParser.readFileAsJSON("$dataDir/$fileLocation/$name.json")

        if (file != null) {
            val where = file.get(location)

            if (where.isJsonObject) {
                val obj = where as JsonObject

                obj.addProperty(property, data)
                FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())
            } else if (where.isJsonArray) {
                val array = where as JsonArray

                array.add(data)
                FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())
            }
        } else {

            Console.printDebugMessage("data file $name is null")

        }
    }

    fun addJsonDataTo(name: String, fileLocation: String, location: String, property: String, data: Number) {
        val file = FileParser.readFileAsJSON("$dataDir/$fileLocation/$name.json")

        println(file.toString())

        if (file != null) {
            val where = file.get(location)

            if (where.isJsonObject) {
                val obj = where as JsonObject

                obj.addProperty(property, data)
                FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())
            } else if (where.isJsonArray) {
                val array = where as JsonArray

                array.add(data)
                FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())
            }
        } else {

            Console.printDebugMessage("data file $name is null")

        }
    }

    fun addJsonDataTo(name: String, fileLocation: String, location: String, property: String, data: Boolean) {
        val file = FileParser.readFileAsJSON("$dataDir/$fileLocation/$name.json")

        if (file != null) {
            val where = file.get(location)

            if (where.isJsonObject) {
                val obj = where as JsonObject

                obj.addProperty(property, data)
                FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())
            } else if (where.isJsonArray) {
                val array = where as JsonArray

                array.add(data)
                FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())
            }
        } else {

            Console.printDebugMessage("data file $name is null")

        }
    }

    //removeData

    fun removeJsonData(name: String, property: String) {
        val file = FileParser.readFileAsJSON("$dataDir/$name.json")

        if (file != null) {
            file.remove(property)
            FileManager.writeFile("$dataDir/$name.json", file.toString())
        }
    }

    fun removeJsonData(name: String, fileLocation: String, property: String) {
        val file = FileParser.readFileAsJSON("$dataDir/$fileLocation/$name.json")

        if (file != null) {
            file.remove(property)
            FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())
        }
    }

    //removeDataIn

    fun removeJsonDataOn(name: String, location: String, property: String) {
        val file = FileParser.readFileAsJSON("$dataDir/$name.json")

        if (file != null) {
            val where = file.get(location)

            if (where != null) {

                if (where.isJsonObject) {
                    val obj = where as JsonObject

                    obj.remove(property)
                }
                FileManager.writeFile("$dataDir/$name.json", file.toString())
            }
        }
    }

    fun removeJsonDataOn(name: String, fileLocation: String, location: String, property: String) {
        val file = FileParser.readFileAsJSON("$dataDir/$fileLocation/$name.json")

        if (file != null) {
            val where = file.get(location)

            if (where != null) {

                if (where.isJsonObject) {
                    val obj = where as JsonObject

                    obj.remove(property)
                }
                FileManager.writeFile("$dataDir/$fileLocation/$name.json", file.toString())
            }
        }
    }
}