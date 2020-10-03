package com.zea7ot.whorepresentsyou.ui.members.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.zea7ot.whorepresentsyou.R
import com.zea7ot.whorepresentsyou.data.entity.ResMember
import com.zea7ot.whorepresentsyou.ui.memberdetails.MemberDetailsActivity

class AdapterMembers(private val context: Context, private val members: ArrayList<ResMember>) :
    BaseAdapter() {


    private val inflater: LayoutInflater =
        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return members.size
    }

    override fun getItem(position: Int): Any {
        return members[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolder

        val member = members[position]

        if (convertView == null) {
            view = inflater.inflate(R.layout.fr_lv_item_member, parent, false)
            viewHolder = ViewHolder()

            viewHolder.tvName = view.findViewById(R.id.tv_name) as TextView

            view.tag = viewHolder
        } else {
            view = convertView
            viewHolder = convertView.tag as ViewHolder
        }

        val tvName = viewHolder.tvName
        tvName.text = member.name

        tvName.setOnClickListener {
            val intent = MemberDetailsActivity.newIntent(context, member)
            context.startActivity(intent)
        }

        return view
    }

    private class ViewHolder {
        lateinit var tvName: TextView
        lateinit var tvParty: TextView
        lateinit var tvState: TextView
        lateinit var tvDistrict: TextView
        lateinit var tvPhone: TextView
        lateinit var tvOffice: TextView
        lateinit var tvLink: TextView
    }
}