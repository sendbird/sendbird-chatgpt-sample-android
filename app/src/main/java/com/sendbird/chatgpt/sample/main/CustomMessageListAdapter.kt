package com.sendbird.chatgpt.sample.main

import android.text.format.DateUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sendbird.android.channel.BaseChannel
import com.sendbird.android.channel.GroupChannel
import com.sendbird.android.message.BaseMessage
import com.sendbird.chatgpt.sample.ChatGPTInfo
import com.sendbird.chatgpt.sample.databinding.ViewChatGptMessageBinding
import com.sendbird.uikit.activities.adapter.MessageListAdapter
import com.sendbird.uikit.activities.viewholder.MessageViewHolder
import com.sendbird.uikit.consts.ClickableViewIdentifier
import com.sendbird.uikit.model.MessageListUIParams

class CustomMessageListAdapter(channel: GroupChannel?, useMessageGroupUI: Boolean) :
    MessageListAdapter(channel, useMessageGroupUI) {

    companion object {
        const val VIEW_TYPE_CHAT_GPT_MESSAGE = 100
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        if (viewType == VIEW_TYPE_CHAT_GPT_MESSAGE) {
            val viewHolder = ChatGPTMessageViewHolder(
                ViewChatGptMessageBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            val views: Map<String, View> = viewHolder.clickableViewMap
            for ((identifier, value) in views) {
                value.setOnLongClickListener { v: View? ->
                    val messagePosition: Int = viewHolder.getBindingAdapterPosition()
                    if (messagePosition != RecyclerView.NO_POSITION) {
                        onListItemLongClickListener?.onIdentifiableItemLongClick(
                            v!!,
                            identifier,
                            messagePosition,
                            getItem(messagePosition)
                        )
                        return@setOnLongClickListener true
                    }
                    false
                }
            }
            return viewHolder
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (ChatGPTInfo.chatGPTIds.contains(item.sender?.userId)) {
            return VIEW_TYPE_CHAT_GPT_MESSAGE
        }
        return super.getItemViewType(position)
    }

    class ChatGPTMessageViewHolder(private val binding: ViewChatGptMessageBinding) : MessageViewHolder(binding.root) {

        override fun bind(channel: BaseChannel, message: BaseMessage, params: MessageListUIParams) {
            binding.tvNickname.text = message.sender?.nickname
            binding.tvSentAt.text = DateUtils.formatDateTime(
                binding.root.context, message.createdAt, DateUtils.FORMAT_SHOW_TIME
            )
            binding.tvMessage.text = message.message
            Glide.with(binding.ivProfile)
                .load(message.sender?.profileUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivProfile)
        }

        override fun getClickableViewMap(): Map<String, View> {
            return mapOf(
                ClickableViewIdentifier.Chat.name to binding.tvMessage
            )
        }
    }
}
