package com.sendbird.chatgpt.sample

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.sendbird.chatgpt.sample.main.CustomChannelFragment
import com.sendbird.chatgpt.sample.main.CustomChannelListFragment
import com.sendbird.chatgpt.sample.main.CustomChannelSettingsFragment
import com.sendbird.chatgpt.sample.main.CustomMemberListAdapter
import com.sendbird.chatgpt.sample.main.CustomMessageListAdapter
import com.sendbird.uikit.fragments.ChannelFragment
import com.sendbird.uikit.fragments.ChannelListFragment
import com.sendbird.uikit.fragments.ChannelSettingsFragment
import com.sendbird.uikit.fragments.MemberListFragment
import com.sendbird.uikit.fragments.UIKitFragmentFactory

class UIKitCustomFragmentFactory : UIKitFragmentFactory() {
    override fun newChannelListFragment(args: Bundle): Fragment {
        return ChannelListFragment.Builder()
            .withArguments(args)
            .setUseHeader(true)
            .setCustomFragment(CustomChannelListFragment())
            .build()
    }

    override fun newChannelFragment(channelUrl: String, args: Bundle): Fragment {
        return ChannelFragment.Builder(channelUrl)
            .withArguments(args)
            .setUseHeader(true)
            .setMessageListAdapter(CustomMessageListAdapter(null, false))
            .setCustomFragment(CustomChannelFragment())
            .build()
    }

    override fun newChannelSettingsFragment(channelUrl: String, args: Bundle): Fragment {
        return ChannelSettingsFragment.Builder(channelUrl)
            .withArguments(args)
            .setUseHeader(true)
            .setCustomFragment(CustomChannelSettingsFragment())
            .build()
    }

    override fun newMemberListFragment(channelUrl: String, args: Bundle): Fragment {
        return MemberListFragment.Builder(channelUrl)
            .withArguments(args)
            .setUseHeader(true)
            .setUseHeaderRightButton(false)
            .setMemberListAdapter(CustomMemberListAdapter())
            .build()
    }
}
