package com.sendbird.chatgpt.sample.main

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.sendbird.android.user.Member
import com.sendbird.chatgpt.sample.ChatGPTInfo
import com.sendbird.chatgpt.sample.databinding.ViewChatGptMemberBinding
import com.sendbird.uikit.activities.adapter.MemberListAdapter
import com.sendbird.uikit.activities.viewholder.BaseViewHolder

class CustomMemberListAdapter : MemberListAdapter() {
    companion object {
        const val VIEW_TYPE_CHAT_GPT_MEMBER = 100
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<Member> {
        if (viewType == VIEW_TYPE_CHAT_GPT_MEMBER) {
            return ChatGPTMemberHolder(
                ViewChatGptMemberBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
        return super.onCreateViewHolder(parent, viewType)
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (ChatGPTInfo.chatGPTIds.contains(item.userId)) {
            return VIEW_TYPE_CHAT_GPT_MEMBER
        }
        return super.getItemViewType(position)
    }

    class ChatGPTMemberHolder(private val binding: ViewChatGptMemberBinding) : BaseViewHolder<Member>(binding.root) {
        override fun bind(item: Member) {
            binding.tvNickname.text = item.nickname
            Glide.with(binding.ivProfile)
                .load(item.profileUrl)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .apply(RequestOptions.circleCropTransform())
                .into(binding.ivProfile)
        }
    }
}
