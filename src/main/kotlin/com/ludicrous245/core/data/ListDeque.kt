package com.ludicrous245.core.data

class ListDeque<T> {
    private val queue:ArrayList<T> = ArrayList()

    fun element():T{
        return queue[0]
    }

    fun poll(): T {
        return queue.removeAt(0)
    }

    fun offer(o: T){
        queue.add(o)
    }

    fun offerAll(c: Collection<T>){
        for(e in c) queue.add(e)
    }

    fun remove(o: T):Boolean{
        return queue.remove(o)
    }

    fun removeAt(i: Int):T{
        return queue.removeAt(i)
    }

    fun clear(){
        queue.clear()
    }

    fun elements():Collection<T>{
        return queue
    }

    fun isEmpty():Boolean{
        return queue.isEmpty()
    }

    fun getSize(): Int{
        return queue.size
    }

}