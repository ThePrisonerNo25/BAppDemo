package com.example.yangchaoming.bappdemo.action_library.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.example.yangchaoming.bappdemo.R
import kotlinx.android.synthetic.main.camerax.*
import android.util.TypedValue





class DialogAddCustomAction : DialogFragment() {
    lateinit var etName:EditText
    lateinit var iv_1:ImageView
    lateinit var iv_2:ImageView
    lateinit var iv_11:ImageView
    lateinit var iv_22:ImageView
    lateinit var tvUnitTimes:TextView
    lateinit var tvUnitSecond:TextView
    lateinit var tvUnit:TextView
    lateinit var etActionNum:EditText
    lateinit var etActionGroup:EditText
    lateinit var btnConfirm:Button

    var seletedUnit:Int=UNIT_TIMES
    companion object{
        val UNIT_TIMES=0
        val UNIT_SECOND=1
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_add_custom_action,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        etName=view.findViewById(R.id.et_name)
        iv_1=view.findViewById(R.id.iv_1)
        iv_2=view.findViewById(R.id.iv_2)
        iv_11=view.findViewById(R.id.iv_11)
        iv_22=view.findViewById(R.id.iv_22)
        tvUnitTimes=view.findViewById(R.id.tv_unit_times)
        tvUnitSecond=view.findViewById(R.id.tv_unit_second)
        tvUnit=view.findViewById(R.id.tv_unit)
        etActionNum=view.findViewById(R.id.et_action_num)
        etActionGroup=view.findViewById(R.id.et_action_group)
        btnConfirm=view.findViewById(R.id.btn_confirm)

        selectedUnit(seletedUnit)

        btnConfirm.setOnClickListener {
            val actionNum = etActionNum.text
            val actionGroupNum = etActionGroup.text
            val actionName = etName.text
            if(actionName.isNullOrEmpty()||actionNum.isNullOrEmpty()||actionGroupNum.isNullOrEmpty()){
                Toast.makeText(context,"请完善所有信息",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            listener?.addAction(actionName.toString(),actionNum.toString(),actionGroupNum.toString(),seletedUnit)
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        resetState()

    }

    private fun resetState(){
        seletedUnit=UNIT_TIMES
        etActionNum.text=null
        etActionGroup.text=null
        etName.text=null
        selectedUnit(seletedUnit)

    }

    var listener:AddCustomActionListener?=null

    interface AddCustomActionListener{
        fun addAction(actionName:String,actionNum:String,actionGroupNum:String,unit: Int)
    }

    override fun onStart() {
        super.onStart()
        val params = dialog!!.window!!.attributes
        params.width = dp2px(280f)
        params.height = ViewGroup.LayoutParams.MATCH_PARENT

        dialog!!.window!!.attributes = params as android.view.WindowManager.LayoutParams
    }

    private  fun selectedUnit(unit:Int){
        when(unit){
            UNIT_TIMES ->{
                iv_1.isSelected=true
                iv_11.isSelected=true
                tvUnitTimes.isSelected=true
                iv_2.isSelected=false
                iv_22.isSelected=false
                tvUnitSecond.isSelected=false
                tvUnit.text="次"
            }

            UNIT_SECOND ->{
                iv_1.isSelected=false
                iv_11.isSelected=false
                tvUnitTimes.isSelected=false
                iv_2.isSelected=true
                iv_22.isSelected=true
                tvUnitSecond.isSelected=true
                tvUnit.text="秒"
            }
        }
    }

    fun dp2px(vaule: Float):Int{
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                vaule, context!!.resources.displayMetrics).toInt()
    }
}