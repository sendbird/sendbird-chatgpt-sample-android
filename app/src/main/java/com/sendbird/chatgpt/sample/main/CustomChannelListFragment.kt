package com.sendbird.chatgpt.sample.main

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sendbird.android.channel.GroupChannel
import com.sendbird.android.exception.SendbirdException
import com.sendbird.android.handler.GroupChannelCallbackHandler
import com.sendbird.android.params.GroupChannelCreateParams
import com.sendbird.chatgpt.sample.ChatGPTInfo
import com.sendbird.chatgpt.sample.R
import com.sendbird.chatgpt.sample.databinding.ViewChannelSelectTypeBinding
import com.sendbird.chatgpt.sample.databinding.ViewChannelSelectTypeItemBinding
import com.sendbird.uikit.SendbirdUIKit
import com.sendbird.uikit.activities.ChannelActivity
import com.sendbird.uikit.fragments.ChannelListFragment
import com.sendbird.uikit.interfaces.OnItemClickListener
import com.sendbird.uikit.modules.components.HeaderComponent
import com.sendbird.uikit.vm.ChannelListViewModel

class CustomChannelListFragment : ChannelListFragment() {
    override fun onBindHeaderComponent(headerComponent: HeaderComponent, viewModel: ChannelListViewModel) {
        headerComponent.setOnRightButtonClickListener {
            showChannelTypeSelectDialog()
        }
    }

    private fun showChannelTypeSelectDialog() {
        if (context == null) return
        val binding = ViewChannelSelectTypeBinding.inflate(layoutInflater)
        val channelSelectTypeItems = mutableListOf<ChannelType>()
        channelSelectTypeItems.add(ChannelType.GPTBot)
        channelSelectTypeItems.add(ChannelType.WittyBot)
        channelSelectTypeItems.add(ChannelType.KnowledgeBot)
        val recyclerView = binding.rvChannelType
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        val adapter = ChannelSelectTypeRecyclerViewAdapter().apply {
            items = channelSelectTypeItems
        }
        recyclerView.adapter = adapter

        val builder = AlertDialog.Builder(requireContext(), com.sendbird.uikit.R.style.Sendbird_Dialog)
        builder.setView(binding.root)
        val dialog = builder.create()

        dialog.show()
        dialog.window?.setGravity(Gravity.TOP)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        adapter.onItemClickListener =
            OnItemClickListener<ChannelType> { _, _, item ->
                dialog.dismiss()
                when (item) {
                    ChannelType.GPTBot -> {
                        startWithChatGPTBot(ChatGPTInfo.GPT_BOT_USER_ID)
                    }
                    ChannelType.WittyBot -> {
                        startWithChatGPTBot(ChatGPTInfo.WITTY_BOT_USER_ID)
                    }
                    ChannelType.KnowledgeBot -> {
                        startWithChatGPTBot(ChatGPTInfo.KNOWLEDGE_BOT_USER_ID)
                    }
                }
            }
    }

    private fun startWithChatGPTBot(botUserId: String) {
        if (!isFragmentAlive) return
        val params = GroupChannelCreateParams()
        params.operatorUserIds = listOf(SendbirdUIKit.getAdapter().userInfo.userId)
        params.userIds = listOf(botUserId)
        GroupChannel.createChannel(params, object : GroupChannelCallbackHandler {
            override fun onResult(channel: GroupChannel?, e: SendbirdException?) {
                if (e != null || channel == null) {
                    return
                }
                startActivity(ChannelActivity.newIntent(requireContext(), channel.url))
            }
        })
    }

    class ChannelSelectTypeRecyclerViewAdapter :
        RecyclerView.Adapter<ChannelSelectTypeRecyclerViewAdapter.ChannelSelectTypeViewHolder>() {

        var items: List<ChannelType> = listOf()
            set(value) {
                field = value
                notifyDataSetChanged()
            }
        var onItemClickListener: OnItemClickListener<ChannelType>? = null

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChannelSelectTypeViewHolder {
            val binding = ViewChannelSelectTypeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            return ChannelSelectTypeViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ChannelSelectTypeViewHolder, position: Int) {
            val channelType = items[position]
            holder.bind(channelType)
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(it, position, channelType)
            }
        }

        override fun getItemCount(): Int = items.size

        class ChannelSelectTypeViewHolder(private val binding: ViewChannelSelectTypeItemBinding) :
            RecyclerView.ViewHolder(binding.root) {

            fun bind(channelType: ChannelType) {
                when (channelType) {
                    ChannelType.GPTBot -> {
                        binding.ivIcon.setImageResource(R.drawable.icon_menu_bot)
                        binding.tvName.text = binding.root.context.getString(R.string.chat_gpt_bot)
                    }
                    ChannelType.WittyBot -> {
                        binding.ivIcon.setImageResource(R.drawable.icon_menu_bot)
                        binding.tvName.text = binding.root.context.getString(R.string.chat_witty_bot)
                    }
                    ChannelType.KnowledgeBot -> {
                        binding.ivIcon.setImageResource(R.drawable.icon_menu_bot)
                        binding.tvName.text = binding.root.context.getString(R.string.chat_knowledge_bot)
                    }
                }
            }
        }
    }
}
