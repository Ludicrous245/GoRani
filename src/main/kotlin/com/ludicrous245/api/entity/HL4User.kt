package com.ludicrous245.api.entity

import dev.kord.core.entity.User

abstract class HL4User {
    abstract val user:User
    abstract val id:Long
    abstract val tag:String
}