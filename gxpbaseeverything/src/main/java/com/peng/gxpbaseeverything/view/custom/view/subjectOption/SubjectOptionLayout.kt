package com.peng.gxpbaseeverything.view.custom.view.subjectOption

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.annotation.StyleRes
import androidx.core.view.children
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import com.peng.gxpbaseeverything.R

/**
 * 题目选项View
 */
class SubjectOptionLayout : LinearLayout {
    /**
     * 选项之间的margin
     */
    private var mOptionMargin = 0

    /**
     * 最多可以选择mMaxSelectNum项，默认为单选
     */
    private var mMaxSelectNum = 1

    @LayoutRes
    private var mSubjectOptionViewLayoutId:Int = R.layout.item_subject_option_self

    //三种状态的文字Style
    @StyleRes
    private var mNormalTextAppearance: Int? = null

    @StyleRes
    private var mCorrectTextAppearance: Int? = null

    @StyleRes
    private var mWrongTextAppearance: Int? = null

    private var mAnswerMode = MODE_ANSWER

    companion object {
        const val MODE_ANSWER = 0   //答题模式
        const val MODE_CHECK = 1   //查错
    }

    private val mOnCheckedChangeListener = OnClickListener {
        //查错模式下，不能点击
        if (mAnswerMode == MODE_CHECK) return@OnClickListener

        val position = it.getTag(R.id.subject_option_position) as Int
        val status = it.getTag(R.id.subject_option_status) as Int
        when (status) {
            SubjectOptionView.OPTION_NORMAL -> {
                //如果已经达到了最大选择数，则忽略点击事件
                if (getCorrectCount() >= mMaxSelectNum) {
                    if (mMaxSelectNum != 1) return@OnClickListener

                    //单选情况下，需要自动清除上次选择的标记，然后选择本次
                    clearAllStatus()
                }

                if (it is SubjectOptionView) {
                    it.updateOptionStatus(SubjectOptionView.OPTION_CORRECT)
                    it.setTag(R.id.subject_option_status, SubjectOptionView.OPTION_CORRECT)
                }
            }
            SubjectOptionView.OPTION_CORRECT -> {
                if (it is SubjectOptionView) {
                    it.updateOptionStatus(SubjectOptionView.OPTION_NORMAL)
                    it.setTag(R.id.subject_option_status, SubjectOptionView.OPTION_NORMAL)
                }
            }
        }
    }

    /**
     * 清空状态
     */
    private fun clearAllStatus() {
        children.forEach {
            val tag = it.getTag(R.id.subject_option_status)
            if ((tag == SubjectOptionView.OPTION_CORRECT || tag == SubjectOptionView.OPTION_WRONG) && it is SubjectOptionView) {
                it.updateOptionStatus(SubjectOptionView.OPTION_NORMAL)
                it.setTag(R.id.subject_option_status, SubjectOptionView.OPTION_NORMAL)
            }
        }
    }

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initValue(attrs)
        orientation = VERTICAL
    }

    private fun initValue(attrs: AttributeSet?) {
        val ta = context?.obtainStyledAttributes(attrs, R.styleable.SubjectOptionLayout)
        if (ta != null) {
            mMaxSelectNum = ta.getInt(R.styleable.SubjectOptionLayout_maxSelectNum, 1)
            mOptionMargin =
                ta.getDimension(R.styleable.SubjectOptionLayout_optionMargin, 0f).toInt()

            mSubjectOptionViewLayoutId = ta.getResourceId(R.styleable.SubjectOptionLayout_subjectOptionViewLayout,R.layout.item_subject_option_self)

            mNormalTextAppearance = ta.getResourceId(
                R.styleable.SubjectOptionLayout_normalTextAppearance_SubjectOptionLayout,
                android.R.style.TextAppearance_DeviceDefault_Small
            )
            mCorrectTextAppearance = ta.getResourceId(
                R.styleable.SubjectOptionLayout_correctTextAppearance_SubjectOptionLayout,
                android.R.style.TextAppearance_DeviceDefault_Small
            )
            mWrongTextAppearance = ta.getResourceId(
                R.styleable.SubjectOptionLayout_wrongTextAppearance_SubjectOptionLayout,
                android.R.style.TextAppearance_DeviceDefault_Small
            )


            ta.recycle()
        }
    }

    /**
     * 获取正确选项的数量
     */
    private fun getCorrectCount(): Int {
        var count = 0
        children.forEach { view ->
            if (view.getTag(R.id.subject_option_status) == SubjectOptionView.OPTION_CORRECT) count++
        }
        return count
    }

    /**
     * 获取错误选项的数量
     */
    private fun getWrongCount(): Int {
        var count = 0
        children.forEach { view ->
            if (view.getTag(R.id.subject_option_status) == SubjectOptionView.OPTION_WRONG) count++
        }
        return count
    }

    /**
     * 获取正常选项的数量
     */
    private fun getNormalCount(): Int {
        var count = 0
        children.forEach { view ->
            if (view.getTag(R.id.subject_option_status) == SubjectOptionView.OPTION_NORMAL) count++
        }
        return count
    }

    /**
     * 设置多选选项个数
     */
    fun setMaxSelectNum(mMaxSelectNum: Int) {
        this.mMaxSelectNum = mMaxSelectNum
    }

    /**
     * 设置选项
     */
    fun setOptions(options: List<String>) {
        setOptions(options, SubjectOptionView.OPTION_NORMAL)
    }

    /**
     * 设置选项
     */
    fun setOptions(options: List<String>, optionStatus: Int) {
        val statusList = mutableListOf<Int>()
        for (index in options.indices) {
            statusList.add(optionStatus)
        }
        setOptions(options, statusList)
    }

    /**
     * 设置选项
     */
    fun setOptions(options: List<String>, optionStatus: List<Int>) {
        if (options.size != optionStatus.size) {
            throw IllegalArgumentException("对题状态与选项数量不一致")
        }
        removeAllOptions()
        for (index in options.indices) {
            addOption(options[index], optionStatus[index])
        }
    }

    /**
     * 重置状态并删除所有选项
     */
    fun removeAllOptions() {
        removeAllViews()
    }


    /**
     * 添加子View
     */
    fun addOption(itemContent: String) {
        addOption(itemContent, SubjectOptionView.OPTION_NORMAL)
    }


    /**
     * 添加子View,并设置对应对题状态
     */
    fun addOption(itemContent: String, optionStatus: Int) {
        val mItemView =
            LayoutInflater.from(context).inflate(mSubjectOptionViewLayoutId, this, false)
        if (mItemView is SubjectOptionView) {
            mItemView.text = itemContent
            mItemView.setNormalTextAppearance(mNormalTextAppearance)
            mItemView.setCorrectTextAppearance(mCorrectTextAppearance)
            mItemView.setWrongTextAppearance(mWrongTextAppearance)

            mItemView.setTag(R.id.subject_option_position, childCount)
            mItemView.setTag(R.id.subject_option_status, optionStatus)
            mItemView.setTag(R.id.subject_option_text, itemContent)

            mItemView.setOnClickListener(mOnCheckedChangeListener)
        }
        val param = (mItemView.layoutParams as LinearLayout.LayoutParams).apply {
            setMargins(
                marginLeft,
                if (childCount == 0) 0 else mOptionMargin,
                marginRight,
                marginBottom
            )
        }
        addView(
            mItemView, param
        )
    }

    override fun onViewAdded(child: View?) {
        super.onViewAdded(child)
        if (child is SubjectOptionView) {
            child.updateOptionStatus()
        }
    }

    /**
     * 获取所有optionStatus状态的选项下标
     */
    fun getSelectedOptionIndexList(optionStatus: Int): List<Int> {
        val indexList = mutableListOf<Int>()
        children.forEach {
            if (it.getTag(R.id.subject_option_status) == optionStatus) {
                indexList.add(it.getTag(R.id.subject_option_position) as Int)
            }
        }
        return indexList
    }

    /**
     * 获取所有optionStatus状态的选项内容
     */
    fun getSelectedOptions(optionStatus: Int): List<String> {
        val options = mutableListOf<String>()
        children.forEach {
            if (it.getTag(R.id.subject_option_status) == optionStatus) {
                options.add(it.getTag(R.id.subject_option_text) as String)
            }
        }
        return options
    }
}