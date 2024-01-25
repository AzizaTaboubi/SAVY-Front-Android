package com.example.savy.utils

import com.example.savy.utils.Constant.OPEN_GOOGLE
import com.example.savy.utils.Constant.OPEN_SEARCH

object BotResponse {
    fun basicResponses(_message: String): String {

        val random = (0..2).random()
        val message =_message.toLowerCase()

        return when {



            //Hello
            message.contains("hello") -> {

                "hey there !\n Tap 1 : Savy, what is it?  \n Tap 2 : What can I use Savy For?  \n Tap 3 : Information  about products \n Tap 4 : More assistance"


            }
            //Hello
            message.contains("hi") -> {

                "hey there !\n Tap 1 : Savy, what is it?  \n Tap 2 : What can I use Savy For?  \n Tap 3 : Information  about products \n Tap 4 : More assistance"


            }
            //Hello
            message.contains("aslema") -> {

                "hey there !\n Tap 1 : Savy, what is it?  \n Tap 2 : What can I use Savy For?  \n Tap 3 : Information  about products \n Tap 4 : More assistance"


            }
            //Hello
            message.contains("hola") -> {

                "hey there !\n Tap 1 : Savy, what is it?  \n Tap 2 : What can I use Savy For?  \n Tap 3 : Information  about products \n Tap 4 : More assistance"


            }
            //Hello
            message.contains("salut") -> {

                "hey there !\n Tap 1 : Savy, what is it?  \n Tap 2 : What can I use Savy For?  \n Tap 3 : Information  about products \n Tap 4 : More assistance"


            }

            //1
            message.contains("1") -> {
                "SAVY is an app that allows you to search between thousands of products and also offers you a space where you can sell your own electronics online."
            }
            //2
            message.contains("2") -> {
                    " You can use Savy to search for a specific product but also the best price to buy it in \n - Search over millions of products in Tunisia. "            }
            //3
            message.contains("3") -> {
                " - Products are from various websites that sell many types of electronics\n - Browse millions of phones, tvs, and  more. And yet sell your own products and make space for new ones! \n - Enjoy Comparing prices and checking out what is new ! "            }

            //4
            message.contains("4") -> {
                "  Contact us :\n 💌 savysy22@gmail.com  \n ☎️ +216 92448126  "}



            //Open Google
            message.contains("open") && message.contains("google")-> {
                OPEN_GOOGLE
            }

            //Search on the internet
            message.contains("search")-> {
                OPEN_SEARCH
            }

            //When the programme doesn't understand...
            else -> {
                when (random) {
                    0 -> "I don't understand..."
                    1 -> "Try asking me something different"
                    2 -> "say hello first PLEASE"
                    3 -> "We are so happy to visit us , Thank you!  "
                    else -> "error"
                }
            }
        }
    }
}