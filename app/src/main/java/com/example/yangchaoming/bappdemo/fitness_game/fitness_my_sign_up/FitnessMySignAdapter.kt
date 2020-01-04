package com.example.yangchaoming.bappdemo.fitness_game.fitness_my_sign_up

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.yangchaoming.bappdemo.R
import com.example.yangchaoming.bappdemo.fitness_game.bean.FitnessSignBean
import kotlinx.android.synthetic.main.item_fitness_sign.view.*

data class FitnessMySignAdapter(val context: Context, var list:ArrayList<FitnessSignBean>): RecyclerView.Adapter<FitnessMySignAdapter.Holder>(){

    fun resetData(_list:ArrayList<FitnessSignBean>){
        list = _list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_fitness_sign,parent,false)
        return Holder(view)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val bean = list[position]
        holder.tvFitnessName.text = bean.promotionName
//        holder.tvSignNum.text = "报名人数：${bean.promotionName}人"
        holder.tvSignAddress.text = "赛区：${bean.provinceName}"
        holder.tvSignTime.text = "比赛时间：${bean.endTime}"
        holder.tvSignFee.text = "费用：${bean.payAmt}"


        when(bean.status){
            0 ->{//0 待生效（未支付）
                holder.tvSignStatus.text = "未支付"
                holder.tvSignStatus.setTextColor(ContextCompat.getColor(context,R.color.red))
            }

            1 ->{// 1 生效（等待 教练提交资质审核资料）
                holder.tvSignStatus.text = "待提交报名信息"
                holder.tvSignStatus.setTextColor(ContextCompat.getColor(context,R.color.color_main))
            }

            2 ->{// 2 退出比赛 -- 用户 自己操作退出。
                holder.tvSignStatus.text = "退出比赛"
                holder.tvSignStatus.setTextColor(ContextCompat.getColor(context,R.color.color_txt_main2))
            }

            3 ->{//  3 取消（如：用户取消--未支付的情况）
                holder.tvSignStatus.text = "取消"
                holder.tvSignStatus.setTextColor(ContextCompat.getColor(context,R.color.color_txt_main2))
            }

            100 ->{//  100 组队成功！ 等待赛事开始。
                holder.tvSignStatus.text = "组队成功"
                holder.tvSignStatus.setTextColor(ContextCompat.getColor(context,R.color.color_main))
            }

            101 ->{//  101 已提交教练资料，审核中
                holder.tvSignStatus.text = "审核中"
                holder.tvSignStatus.setTextColor(ContextCompat.getColor(context,R.color.color_txt_main2))
            }

            102 ->{//  102 资质审核不通过
                holder.tvSignStatus.text = "资质审核不通过"
                holder.tvSignStatus.setTextColor(ContextCompat.getColor(context,R.color.red))
            }

            201 ->{//  201 等待组队 教练资料审核通过 还没邀约 任何人
                holder.tvSignStatus.text = "等待组队"
                holder.tvSignStatus.setTextColor(ContextCompat.getColor(context,R.color.color_txt_main2))
            }

            202 ->{//  202 组队中。。
                holder.tvSignStatus.text = "组队中"
                holder.tvSignStatus.setTextColor(ContextCompat.getColor(context,R.color.color_txt_main2))
            }

            203 ->{//   203 组队失败 学员拒绝。 可以重新 再次发起邀约。状态变成
                holder.tvSignStatus.text = "组队失败 学员拒绝"
                holder.tvSignStatus.setTextColor(ContextCompat.getColor(context,R.color.red))
            }

            204 ->{//  201 204 取消邀请
                holder.tvSignStatus.text = "取消邀请"
                holder.tvSignStatus.setTextColor(ContextCompat.getColor(context,R.color.color_txt_main2))
            }

            701 ->{// 701 退款 这种情况就是属于 赛事取消
                holder.tvSignStatus.text = "退款(赛事取消)"
                holder.tvSignStatus.setTextColor(ContextCompat.getColor(context,R.color.red))
            }

            702 ->{// 702 退款中
                holder.tvSignStatus.text = "退款中"
                holder.tvSignStatus.setTextColor(ContextCompat.getColor(context,R.color.red))
            }
        }
//        赛事状态： 0 待生效（未支付） 1 生效（等待 教练提交资质审核资料）
//        2 退出比赛 -- 用户 自己操作退出。 用户退出之后 就可以报名其他的赛区了
//        3 取消（如：用户取消--未支付的情况）
//        100 组队成功！ 等待赛事开始。
//        101 已提交教练资料，审核中
//                102 资质审核不通过
//                201 等待组队 教练资料审核通过 还没邀约 任何人
//        202 组队中。。
//        203 组队失败 学员拒绝。 可以重新 再次发起邀约。状态变成
//        201 204 取消邀请
//701 退款 这种情况就是属于 赛事取消 702 退款中
    }

    inner class Holder(itemView: View): RecyclerView.ViewHolder(itemView) {
            val tvSignStatus = itemView.tv_sign_status
            val tvFitnessName = itemView.tv_fitness_name
            val tvSignNum = itemView.tv_sign_num
            val tvSignAddress = itemView.tv_sign_address
            val tvSignTime = itemView.tv_sign_time
            val tvSignFee = itemView.tv_sign_fee
            val btnCommit = itemView.btn_commit
            val btnViewDivision = itemView.btn_view_division
            val ivCancel = itemView.iv_cancel
    }

}