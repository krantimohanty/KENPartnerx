package com.kencloud.partner.user;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kencloud.partner.user.helpers.Constants;
import com.kencloud.partner.user.helpers.HttpService;


public class AddTaskActivity extends Activity {

    EditText textField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        textField = (EditText) findViewById(R.id.taskToAdd);
        Button button = (Button) findViewById(R.id.postTaskbutton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (textField.getText().toString() != null
                        && !textField.getText().toString().trim().isEmpty()
                        && Constants.CURRENT_RESULT != null) {

                    HttpService service = new HttpService();
                    try {
                        service.addItem(textField.getText().toString(), Constants.CURRENT_RESULT.getToken());
                    } catch (Exception e) {
                        SimpleAlertDialog.showAlertDialog(getApplicationContext(), "Exception caught", e.getMessage());
                    }
                    finish();
                }
            }
        });
    }

}
