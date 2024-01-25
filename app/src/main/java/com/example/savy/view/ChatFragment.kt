package com.example.savy.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.savy.R
import com.example.savy.data.Message
import com.example.savy.databinding.BotmainBinding
import com.example.savy.utils.BotResponse
import com.example.savy.utils.Constant
import com.example.savy.utils.Time
import kotlinx.coroutines.*

class ChatFragment : Fragment() {
    var messagesList = mutableListOf<Message>()

    private lateinit var adapter: MessagingAdapter

    private var binding: BotmainBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = BotmainBinding.inflate(inflater, container, false)

        recyclerView()

        clickEvents()

        val random = (0..3).random()
        customBotMessage("Hello! Today you're speaking with SavyBot, how may I help?")


    return binding?.root
}


    private fun clickEvents() {

        //Send a message
        binding?.btnSend?.setOnClickListener {
            sendMessage()
        }

        //Scroll back to correct position when user clicks on text view
        binding?.etMessage?.setOnClickListener {
            GlobalScope.launch {
                delay(100)

                withContext(Dispatchers.Main) {
                   binding?.rvMessages?.scrollToPosition(adapter.itemCount - 1)

                }
            }
        }
    }

    private fun recyclerView() {
        adapter = MessagingAdapter()
        binding?.rvMessages?.adapter = adapter
        binding?.rvMessages?.layoutManager = LinearLayoutManager(context)

    }

    override fun onStart() {
        super.onStart()
        //In case there are messages, scroll to bottom when re-opening app
        GlobalScope.launch {
            delay(100)
            withContext(Dispatchers.Main) {
                binding?.rvMessages?.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }

    private fun sendMessage() {
        val message = binding?.etMessage?.text.toString()
        val timeStamp = Time.timeStamp()

        if (message.isNotEmpty()) {
            //Adds it to our local list
            messagesList.add(Message(message, Constant.SEND_ID, timeStamp))
            binding?.etMessage?.setText("")

            adapter.insertMessage(Message(message, Constant.SEND_ID, timeStamp))
            binding?.rvMessages?.scrollToPosition(adapter.itemCount - 1)

            botResponse(message)
        }
    }

    private fun botResponse(message: String) {
        val timeStamp = Time.timeStamp()

        GlobalScope.launch {
            //Fake response delay
            delay(1000)

            withContext(Dispatchers.Main) {
                //Gets the response
                val response = BotResponse.basicResponses(message)

                //Adds it to our local list
                messagesList.add(Message(response, Constant.RECEIVE_ID, timeStamp))

                //Inserts our message into the adapter
                adapter.insertMessage(Message(response, Constant.RECEIVE_ID, timeStamp))

                //Scrolls us to the position of the latest message
                binding?.rvMessages?.scrollToPosition(adapter.itemCount - 1)

                //Starts Google
                when (response) {
                    Constant.OPEN_GOOGLE -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        site.data = Uri.parse("https://www.google.com/")
                        startActivity(site)
                    }
                    Constant.OPEN_SEARCH -> {
                        val site = Intent(Intent.ACTION_VIEW)
                        val searchTerm: String? = message.substringAfterLast("search")
                        site.data = Uri.parse("https://www.google.com/search?&q=$searchTerm")
                        startActivity(site)
                    }

                }
            }
        }
    }

    private fun customBotMessage(message: String) {

        GlobalScope.launch {
            delay(1000)
            withContext(Dispatchers.Main) {
                val timeStamp = Time.timeStamp()
                messagesList.add(Message(message, Constant.RECEIVE_ID, timeStamp))
                adapter.insertMessage(Message(message, Constant.RECEIVE_ID, timeStamp))

                binding?.rvMessages?.scrollToPosition(adapter.itemCount - 1)
            }
        }
    }
}