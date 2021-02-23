package com.peng.gxpbaseeverything.view.custom.view.subjectOption

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.annotation.DrawableRes
import androidx.annotation.StyleRes
import com.peng.gxpbaseeverything.R


class SubjectOptionView : androidx.appcompat.widget.AppCompatTextView {
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initValue(attrs)
    }

    companion object {
        const val DRAWABLE_POSITION_START = 0
        const val DRAWABLE_POSITION_TOP = 1
        const val DRAWABLE_POSITION_END = 2
        const val DRAWABLE_POSITION_BOTTOM = 3

        const val OPTION_NORMAL = 0
        const val OPTION_CORRECT = 1
        const val OPTION_WRONG = 2
    }

    //三种状态的背景
    private var mNormalBackground: Drawable? = null
    private var mCorrectBackground: Drawable? = null
    private var mWrongBackground: Drawable? = null

    //三种状态的Drawable
    private var mNormalDrawable: Drawable? = null
    private var mCorrectDrawable: Drawable? = null
    private var mWrongDrawable: Drawable? = null

    //三种状态的文字Style
    @StyleRes
    private var mNormalTextAppearance: Int? = null

    @StyleRes
    private var mCorrectTextAppearance: Int? = null

    @StyleRes
    private var mWrongTextAppearance: Int? = null

    //Drawable的位置
    private var mDrawablePosition: Int = 0

    //Drawable的尺寸
    private var mDrawableSize: Int = 0

    //当前的选项状态，默认为正常
    private var mOptionStatus = OPTION_NORMAL

    private fun initValue(attrs: AttributeSet?) {
        val ta = context?.obtainStyledAttributes(attrs, R.styleable.SubjectOptionView)
        if (ta != null) {
            mNormalBackground = ta.getDrawable(R.styleable.SubjectOptionView_normalBackground)
            mCorrectBackground = ta.getDrawable(R.styleable.SubjectOptionView_correctBackground)
            mWrongBackground = ta.getDrawable(R.styleable.SubjectOptionView_wrongBackground)

            mNormalDrawable = ta.getDrawable(R.styleable.SubjectOptionView_normalDrawable)
            mCorrectDrawable = ta.getDrawable(R.styleable.SubjectOptionView_correctDrawable)
            mWrongDrawable = ta.getDrawable(R.styleable.SubjectOptionView_wrongDrawable)

            mNormalTextAppearance = ta.getResourceId(
                R.styleable.SubjectOptionView_normalTextAppearance_SubjectOptionView,
                android.R.style.TextAppearance
            )
            mCorrectTextAppearance = ta.getResourceId(
                R.styleable.SubjectOptionView_correctTextAppearance_SubjectOptionView,
                android.R.style.TextAppearance
            )
            mWrongTextAppearance = ta.getResourceId(
                R.styleable.SubjectOptionView_wrongTextAppearance_SubjectOptionView,
                android.R.style.TextAppearance
            )

            mDrawablePosition =
                ta.getInt(R.styleable.SubjectOptionView_drawablePosition, DRAWABLE_POSITION_START)

            mDrawableSize = ta.getDimensionPixelSize(R.styleable.SubjectOptionView_drawableSize, 0)

            ta.recycle()
        }
    }

    fun updateOptionStatus() {
        updateOptionStatus(mOptionStatus)
    }

    /**
     * 更新选项状态
     */
    fun updateOptionStatus(newStatus: Int) {
        when (newStatus) {
            OPTION_NORMAL -> {
                resetToNormal()
                mOptionStatus = newStatus
            }
            OPTION_CORRECT -> {
                resetToCorrect()
                mOptionStatus = newStatus
            }
            OPTION_WRONG -> {
                resetToWrong()
                mOptionStatus = newStatus
            }
            else -> {
                throw IllegalArgumentException("选项状态应为OPTION_NORMAL、OPTION_CORRECT、OPTION_WRONG中之一")
            }
        }
    }

    /**
     * 设置正常的背景
     */
    fun setNormalBackground(@DrawableRes background: Int) {
        setNormalBackground(resources.getDrawable(background, context.theme))
    }

    /**
     * 设置正常的背景
     */
    fun setNormalBackground(background: Drawable) {
        mNormalBackground = background
        updateOptionStatus(mOptionStatus)
    }

    /**
     * 设置正确的背景
     */
    fun setCorrectBackground(@DrawableRes background: Int) {
        setCorrectBackground(resources.getDrawable(background, context.theme))
    }

    /**
     * 设置正确的背景
     */
    fun setCorrectBackground(background: Drawable) {
        mCorrectBackground = background
        updateOptionStatus(mOptionStatus)
    }

    /**
     * 设置错误的背景
     */
    fun setWrongBackground(@DrawableRes background: Int) {
        setWrongBackground(resources.getDrawable(background, context.theme))
    }

    /**
     * 设置错误的背景
     */
    fun setWrongBackground(background: Drawable) {
        mWrongBackground = background
        updateOptionStatus(mOptionStatus)
    }

    /**
     * 设置Drawable位置
     */
    fun setDrawablePosition(position: Int) {
        if (position != DRAWABLE_POSITION_START && position != DRAWABLE_POSITION_TOP && position != DRAWABLE_POSITION_END && position != DRAWABLE_POSITION_BOTTOM) {
            throw IllegalArgumentException("参数必须为DRAWABLE_POSITION_START、DRAWABLE_POSITION_TOP、DRAWABLE_POSITION_END、DRAWABLE_POSITION_BOTTOM之一")
        }
        this.mDrawablePosition = position
        updateOptionStatus(mOptionStatus)
    }

    /**
     * 设置Drawable尺寸
     */
    fun setDrawableSize(size: Int) {
        this.mDrawableSize = size
        updateOptionStatus(mOptionStatus)
    }

    /**
     * 设置正常状态下的文字属性
     */
    fun setNormalTextAppearance(@StyleRes mNormalTextAppearance: Int?) {
        this.mNormalTextAppearance = mNormalTextAppearance
        updateOptionStatus(mOptionStatus)
    }

    /**
     * 设置正确状态下的文字属性
     */
    fun setCorrectTextAppearance(@StyleRes mCorrectTextAppearance: Int?) {
        this.mCorrectTextAppearance = mCorrectTextAppearance
        updateOptionStatus(mOptionStatus)
    }

    /**
     * 设置错误状态下的文字属性
     */
    fun setWrongTextAppearance(@StyleRes mWrongTextAppearance: Int?) {
        this.mWrongTextAppearance = mWrongTextAppearance
        updateOptionStatus(mOptionStatus)
    }

    /**
     * 切换到错误选项状态
     */
    private fun resetToWrong() {
        if (mWrongTextAppearance != null) {
            setTextAppearance(context, mWrongTextAppearance!!)
        }
        if (mWrongBackground != null) {
            background = mWrongBackground
        }
        if (mWrongDrawable != null) {
            mWrongDrawable!!.setBounds(0, 0, mDrawableSize, mDrawableSize)
            when (mDrawablePosition) {
                DRAWABLE_POSITION_START -> {
                    setCompoundDrawables(mWrongDrawable, null, null, null)
                }
                DRAWABLE_POSITION_TOP -> {
                    setCompoundDrawables(null, mWrongDrawable, null, null)
                }
                DRAWABLE_POSITION_END -> {
                    setCompoundDrawables(null, null, mWrongDrawable, null)
                }
                DRAWABLE_POSITION_BOTTOM -> {
                    setCompoundDrawables(null, null, null, mWrongDrawable)
                }
            }
        }
    }

    /**
     * 切换到正确选项状态
     */
    private fun resetToCorrect() {
        if (mCorrectTextAppearance != null) {
            setTextAppearance(context, mCorrectTextAppearance!!)
        }
        if (mCorrectBackground != null) {
            background = mCorrectBackground
        }
        if (mCorrectDrawable != null) {
            mCorrectDrawable!!.setBounds(0, 0, mDrawableSize, mDrawableSize)
            when (mDrawablePosition) {
                DRAWABLE_POSITION_START -> {
                    setCompoundDrawables(mCorrectDrawable, null, null, null)
                }
                DRAWABLE_POSITION_TOP -> {
                    setCompoundDrawables(null, mCorrectDrawable, null, null)
                }
                DRAWABLE_POSITION_END -> {
                    setCompoundDrawables(null, null, mCorrectDrawable, null)
                }
                DRAWABLE_POSITION_BOTTOM -> {
                    setCompoundDrawables(null, null, null, mCorrectDrawable)
                }
            }
        }
    }

    /**
     * 切换到正常状态
     */
    private fun resetToNormal() {
        if (mNormalTextAppearance != null) {
            setTextAppearance(context, mNormalTextAppearance!!)
        }
        if (mNormalBackground != null) {
            background = mNormalBackground
        }
        if (mNormalDrawable != null) {
            mNormalDrawable!!.setBounds(0, 0, mDrawableSize, mDrawableSize)
            when (mDrawablePosition) {
                DRAWABLE_POSITION_START -> {
                    setCompoundDrawables(mNormalDrawable, null, null, null)
                }
                DRAWABLE_POSITION_TOP -> {
                    setCompoundDrawables(null, mNormalDrawable, null, null)
                }
                DRAWABLE_POSITION_END -> {
                    setCompoundDrawables(null, null, mNormalDrawable, null)
                }
                DRAWABLE_POSITION_BOTTOM -> {
                    setCompoundDrawables(null, null, null, mNormalDrawable)
                }
            }
        }

    }
}