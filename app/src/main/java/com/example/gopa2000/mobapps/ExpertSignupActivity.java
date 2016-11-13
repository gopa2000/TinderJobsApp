package com.example.gopa2000.mobapps;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ExpertSignupActivity extends AppCompatActivity {

    private Button btn_info_add_line;
    private LinearLayout parent_layout_info;
    private int hint_info=0;
    private ArrayList<EditText> comp_info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expert_signup);

        btn_info_add_line=(Button)findViewById(R.id.btn_info_addline);
        parent_layout_info = (LinearLayout)findViewById(R.id.p_info_layout);

        comp_info = new ArrayList<EditText>();
        comp_info.add((EditText)findViewById(R.id.input_cinfo));

        btn_info_add_line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                createEditTextViewInfo();
            }
        });
    }

    protected void createEditTextViewInfo() {
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams (
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        params.setMargins(0,10,0,10);
        EditText edittTxt = new EditText(this);
        int maxLength = 5;
        hint_info++;
        //edittTxt.setHint("editText"+hint);
        edittTxt.setLayoutParams(params);
        // edtTxt.setBackgroundColor(Color.WHITE);
        edittTxt.setInputType(InputType.TYPE_CLASS_TEXT);
        edittTxt.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);
        edittTxt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        edittTxt.setId(hint_info);
        edittTxt.setTextColor(Color.WHITE);
        InputFilter[] fArray = new InputFilter[1];
        fArray[0] = new InputFilter.LengthFilter(maxLength);
        edittTxt.setFilters(fArray);
        comp_info.add(edittTxt);
        parent_layout_info.addView(edittTxt);
    }
}
