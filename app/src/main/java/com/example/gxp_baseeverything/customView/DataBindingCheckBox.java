package com.example.gxp_baseeverything.customView;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.CompoundButton;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.databinding.BindingAdapter;
import androidx.databinding.InverseBindingAdapter;
import androidx.databinding.InverseBindingListener;

//@InverseBindingMethods({
//        @InverseBindingMethod(
//                type = DataBindingCheckBox.class,
//                attribute = "value",
//                event = "valueAttrChanged",
//                method = "getValue")
//})
public class DataBindingCheckBox extends AppCompatCheckBox {
    private String value;

    public DataBindingCheckBox(Context context) {
        super(context);
    }

    public DataBindingCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DataBindingCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
//    public void setValue(String value) {
//        if (value == null) {
//            if (this.value != null) {
//                this.setChecked(true);
//                this.value = value;
//            }
//            return;
//        }
//        if (value.equals(this.value)) return;
//        this.setChecked(false);
//        this.value = value;
//    }

    @BindingAdapter(value = {"value"}, requireAll = false)
    public static void setValue(DataBindingCheckBox checkBox, String value) {
        Log.e("自定义View双向绑定","setValue ====> "+value);
        if (value == null) {
            if (checkBox.value != null) {
                checkBox.setValue(null);
            }
            checkBox.setChecked(true);
            return;
        }
        if (value.equals(checkBox.value)) return;
        checkBox.setChecked(false);
        checkBox.setValue(value);
    }

    //
    @InverseBindingAdapter(attribute = "value", event = "valueAttrChanged")
    public static String getValue(DataBindingCheckBox checkBox) {
        Log.e("自定义View双向绑定","getValue ====> "+checkBox.getValue());
        return checkBox.getValue();
    }

    @BindingAdapter("valueAttrChanged")
    public static void setListeners(
            DataBindingCheckBox view, final InverseBindingListener attrChange) {
        if (attrChange != null) {
            view.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    attrChange.onChange();
                }
            });
        }
    }
}
